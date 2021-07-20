package net.ossrs.yasea;

import android.media.AudioRecord;
import android.media.audiofx.AcousticEchoCanceler;
import android.media.audiofx.AutomaticGainControl;
import android.media.projection.MediaProjection;

import com.github.faucamp.simplertmp.RtmpHandler;
import com.seu.magicfilter.utils.MagicFilterType;

import java.io.File;

/**
 * Created by Leo Ma on 2016/7/25.
 */
public class SrsPublisher {

    private static AudioRecord mic;
    private static AcousticEchoCanceler aec;
    private static AutomaticGainControl agc;
    private byte[] mPcmBuffer = new byte[4096];
    private Thread aworker;

    private SrsCameraView mCameraView;

    private boolean sendVideoOnly = false;
    private boolean sendAudioOnly = false;
    private int videoFrameCount;
    private long lastTimeMillis;
    private double mSamplingFps;

    private SrsFlvMuxer mFlvMuxer;
    private SrsMp4Muxer mMp4Muxer;
    private SrsEncoder mEncoder;

    public SrsPublisher(SimpleRtmpListener listener, MediaProjection mediaProjection) {
        setEncodeHandler(new SrsEncodeHandler(listener), mediaProjection);
        setRtmpHandler(new RtmpHandler(listener));
        setRecordHandler(new SrsRecordHandler(listener));
    }

    public SrsPublisher(SrsCameraView view, SimpleRtmpListener listener) {
        mCameraView = view;
        mCameraView.setPreviewCallback(new SrsCameraView.PreviewCallback() {
            @Override
            public void onGetRgbaFrame(byte[] data, int width, int height) {
                calcSamplingFps();
                if (!sendAudioOnly) {
                    mEncoder.onGetRgbaFrame(data, width, height);
                }
            }
        });
        setEncodeHandler(new SrsEncodeHandler(listener));
        setRtmpHandler(new RtmpHandler(listener));
        setRecordHandler(new SrsRecordHandler(listener));
    }

    private void calcSamplingFps() {
        // Calculate sampling FPS
        if (videoFrameCount == 0) {
            lastTimeMillis = System.nanoTime() / 1000000;
            videoFrameCount++;
        } else {
            if (++videoFrameCount >= SrsEncoder.VGOP) {
                long diffTimeMillis = System.nanoTime() / 1000000 - lastTimeMillis;
                mSamplingFps = (double) videoFrameCount * 1000 / diffTimeMillis;
                videoFrameCount = 0;
            }
        }
    }

    public void startCamera() {
        if (mCameraView != null) {
            mCameraView.startCamera();
        }
    }

    public void stopCamera() {
        if (mCameraView != null) {
            mCameraView.stopCamera();
        }
    }

    public void startAudio() {
        mic = mEncoder.chooseAudioRecord();
        if (mic == null) {
            return;
        }

        if (AcousticEchoCanceler.isAvailable()) {
            aec = AcousticEchoCanceler.create(mic.getAudioSessionId());
            if (aec != null) {
                aec.setEnabled(true);
            }
        }

        if (AutomaticGainControl.isAvailable()) {
            agc = AutomaticGainControl.create(mic.getAudioSessionId());
            if (agc != null) {
                agc.setEnabled(true);
            }
        }

        aworker = new Thread(new Runnable() {
            @Override
            public void run() {
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_AUDIO);
                mic.startRecording();
                try {
                    while (!Thread.interrupted()) {
                        if (sendVideoOnly) {
                            mEncoder.onGetPcmFrame(mPcmBuffer, mPcmBuffer.length);
                            try {
                                // This is trivial...
                                Thread.sleep(20);
                            } catch (InterruptedException e) {
                                break;
                            }
                        } else {
                            int size = mic.read(mPcmBuffer, 0, mPcmBuffer.length);
                            if (size > 0) {
                                mEncoder.onGetPcmFrame(mPcmBuffer, size);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        aworker.start();
    }

    public void stopAudio() {
        if (aworker != null) {
            aworker.interrupt();
            try {
                aworker.join();
            } catch (InterruptedException e) {
                aworker.interrupt();
            }
            aworker = null;
        }

        if (mic != null) {
            mic.setRecordPositionUpdateListener(null);
            mic.stop();
            mic.release();
            mic = null;
        }

        if (aec != null) {
            aec.setEnabled(false);
            aec.release();
            aec = null;
        }

        if (agc != null) {
            agc.setEnabled(false);
            agc.release();
            agc = null;
        }
    }

    public void startEncode() {
        if (!mEncoder.start()) {
            return;
        }

        if (mCameraView != null) {
            mCameraView.enableEncoding();
        }

        startAudio();
    }

    public void stopEncode() {
        stopAudio();
        stopCamera();
        mEncoder.stop();
    }

    public void startPublish(String rtmpUrl) {
        if (mFlvMuxer != null) {
            mFlvMuxer.start(rtmpUrl);
            mFlvMuxer.setVideoResolution(mEncoder.getOutputWidth(), mEncoder.getOutputHeight());
            startEncode();
        }
    }

    public void stopPublish() {
        if (mFlvMuxer != null) {
            stopEncode();
            mFlvMuxer.stop();
        }
    }

    public void sendMetaData(int orientation, boolean isStop) {
        if (mFlvMuxer != null) {
            mFlvMuxer.sendMetaData(orientation, isStop);
        }
    }

    public boolean startRecord(String recPath) {
        return mMp4Muxer != null && mMp4Muxer.record(new File(recPath));
    }

    public void stopRecord() {
        if (mMp4Muxer != null) {
            mMp4Muxer.stop();
        }
    }

    public void pauseRecord() {
        if (mMp4Muxer != null) {
            mMp4Muxer.pause();
        }
    }

    public void resumeRecord() {
        if (mMp4Muxer != null) {
            mMp4Muxer.resume();
        }
    }

    public void switchToSoftEncoder() {
        mEncoder.switchToSoftEncoder();
    }

    public void switchToHardEncoder() {
        mEncoder.switchToHardEncoder();
    }

    public boolean isSoftEncoder() {
        return mEncoder.isSoftEncoder();
    }

    public int getPreviewWidth() {
        return mEncoder.getPreviewWidth();
    }

    public int getPreviewHeight() {
        return mEncoder.getPreviewHeight();
    }

    public double getmSamplingFps() {
        return mSamplingFps;
    }

    public int getCamraId() {
        if (mCameraView != null) {
            return mCameraView.getCameraId();
        } else {
            return -1;
        }
    }

    public int[] setPreviewResolution(int width, int height) {
        if (mCameraView != null) {
            int[] resolution = mCameraView.setPreviewResolution(width, height);
            mEncoder.setPreviewResolution(resolution[0], resolution[1]);
            return resolution;
        }
        return new int[]{0, 0};
    }

    public void setOutputResolution(int width, int height) {
        if (width <= height) {
            mEncoder.setPortraitResolution(width, height);
        } else {
            mEncoder.setLandscapeResolution(width, height);
        }
    }

    public void setScreenOrientation(int orientation) {
        if (mCameraView != null) {
            mCameraView.setPreviewOrientation(orientation);
            mEncoder.setScreenOrientation(orientation);
        }
    }

    public void setVideoHDMode() {
        mEncoder.setVideoHDMode();
    }

    public void setVideoSmoothMode() {
        mEncoder.setVideoSmoothMode();
    }

    public void setSendVideoOnly(boolean flag) {
        if (mic != null) {
            if (flag) {
                mic.stop();
                mPcmBuffer = new byte[4096];
            } else {
                mic.startRecording();
            }
        }
        sendVideoOnly = flag;
    }

    public void setSendAudioOnly(boolean flag) {
        sendAudioOnly = flag;
    }

    public boolean switchCameraFilter(MagicFilterType type) {
        if (mCameraView != null) {
            return mCameraView.setFilter(type);
        } else {
            return false;
        }
    }

    public void switchCameraFace(int id) {
        if (mCameraView != null) {
            mCameraView.stopCamera();
            mCameraView.setCameraId(id);
            if (id == 0) {
                mEncoder.setCameraBackFace();
            } else {
                mEncoder.setCameraFrontFace();
            }
            if (mEncoder != null && mEncoder.isEnabled()) {
                mCameraView.enableEncoding();
            }
            mCameraView.startCamera();
        }
    }

    public void setRtmpHandler(RtmpHandler handler) {
        mFlvMuxer = new SrsFlvMuxer(handler);
        if (mEncoder != null) {
            mEncoder.setFlvMuxer(mFlvMuxer);
        }
    }

    public void setRecordHandler(SrsRecordHandler handler) {
        mMp4Muxer = new SrsMp4Muxer(handler);
        if (mEncoder != null) {
            mEncoder.setMp4Muxer(mMp4Muxer);
        }
    }

    /**
     * 摄像头
     *
     * @param handler
     */
    public void setEncodeHandler(SrsEncodeHandler handler) {
        mEncoder = new SrsEncoder(handler);
        if (mFlvMuxer != null) {
            mEncoder.setFlvMuxer(mFlvMuxer);
        }
        if (mMp4Muxer != null) {
            mEncoder.setMp4Muxer(mMp4Muxer);
        }
    }

    /**
     * 屏幕
     *
     * @param handler
     * @param mp
     */
    public void setEncodeHandler(SrsEncodeHandler handler, MediaProjection mp) {
        mEncoder = new SrsEncoder(handler, mp);
        if (mFlvMuxer != null) {
            mEncoder.setFlvMuxer(mFlvMuxer);
        }
        if (mMp4Muxer != null) {
            mEncoder.setMp4Muxer(mMp4Muxer);
        }
    }
}
