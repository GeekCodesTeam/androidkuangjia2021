package net.ossrs.yasea.service;

import android.app.Activity;
import android.content.Context;
import android.media.projection.MediaProjection;
import android.os.Binder;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import net.ossrs.yasea.SimplePublisher;
import net.ossrs.yasea.SimpleRtmpListener;
import net.ossrs.yasea.SrsPublisher;
import net.ossrs.yasea.app.Config;

/**
 * Created by Administrator on 2017/12/27.
 */
public class MyBinder extends Binder {
    private String rtmpAddr;
    private Activity activity;
    public boolean isRecording;
    private boolean isNormal = true;
    private SimpleRtmpListener listener;
    private SrsPublisher mPublisher;
    private boolean isMicOpen;
    private Context context;

    private MediaProjection mediaProjection;

    public MyBinder(Context context) {
        this.context = context;
    }

    /**
     * 启动屏幕采集
     *
     * @param activity
     * @param rtmpAddr
     * @param isMicOpen
     * @param listener
     */
    public void startRecord(Activity activity, String rtmpAddr, boolean isMicOpen, SimpleRtmpListener listener, MediaProjection mediaProjection) {
        this.rtmpAddr = rtmpAddr;
        this.listener = listener;
        this.isMicOpen = isMicOpen;
        this.activity = activity;
        isRecording = true;

        handleRecord(mediaProjection);
    }

    private int[] getOutSize() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getRealMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenheight = dm.heightPixels;
        int outputWidth = screenWidth;
        int outputHeight = screenheight;
        int HDWidth;
        int HDHeight;
        if (screenWidth > screenheight) {
            switch (SimplePublisher.getInstance().getQuality()) {
                case 0:
                    HDWidth = Config.vHDHeight;
                    HDHeight = Config.vHDWidth;
                    break;
                case 1:
                    HDWidth = Config.vHDHeight;
                    HDHeight = Config.vHDWidth;
                    break;
                default:
                    HDWidth = Config.vHDHeight;
                    HDHeight = Config.vHDWidth;
                    break;
            }
        } else {
            switch (SimplePublisher.getInstance().getQuality()) {
                case 0:
                    HDWidth = Config.vHDWidth;
                    HDHeight = Config.vHDHeight;
                    break;
                case 1:
                    HDWidth = Config.vHDWidth;
                    HDHeight = Config.vHDHeight;
                    break;
                default:
                    HDWidth = Config.vHDHeight;
                    HDHeight = Config.vHDWidth;
                    break;
            }
        }
        int smoothWidth;
        int smoothHeight;
        if (screenWidth > screenheight) {
            smoothWidth = Config.vSmoothHeight;
            smoothHeight = Config.vSmoothWidth;
        } else {
            smoothWidth = Config.vSmoothWidth;
            smoothHeight = Config.vSmoothHeight;
        }
        if (screenWidth >= HDWidth && screenheight >= HDHeight) {
            outputWidth = HDWidth;
            outputHeight = HDHeight;
        } else {
            outputWidth = smoothWidth;
            outputHeight = smoothHeight;
        }
        return new int[]{outputWidth, outputHeight};
    }

    /**
     * 处理屏幕数据流
     *
     * @param mediaProjection
     */
    private void handleRecord(MediaProjection mediaProjection) {
        this.mediaProjection = mediaProjection;

        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        // 是否是正常屏幕
        isNormal = rotation == 0;

        int[] outSize = getOutSize();

        startPublish(outSize, mediaProjection);
    }

    private void startPublish(int[] outSize, MediaProjection mediaProjection) {
        mPublisher = new SrsPublisher(listener, mediaProjection);
        switch (SimplePublisher.getInstance().getQuality()) {
            case 0:
                mPublisher.setVideoHDMode();
                break;
            case 1:
                mPublisher.setVideoSmoothMode();
                break;
            default:
                mPublisher.setVideoHDMode();
                break;
        }
        mPublisher.setOutputResolution(outSize[0], outSize[1]);
        mPublisher.setSendVideoOnly(!isMicOpen);
        mPublisher.startPublish(rtmpAddr);
    }

    public void reRecording() {
        int[] outSize = getOutSize();
        reRecording(outSize);
    }

    private void reRecording(int[] outSize) {
        if (mPublisher != null) {
            mPublisher.stopPublish();
        }

        startPublish(outSize, mediaProjection);
    }

    public void stopRecord() {
        if (mPublisher != null) {
            mPublisher.stopPublish();
        }
    }
}
