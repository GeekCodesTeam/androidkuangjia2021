package com.example.slbjiaozvideonew.CustomMedia;

import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.Surface;

import androidx.annotation.RequiresApi;

import cn.jzvd.JZMediaInterface;
import cn.jzvd.Jzvd;

/**
 * Created by Nathen on 2017/11/8.
 * 实现系统的播放引擎
 */
public class JZMediaSystemAssertFolder extends JZMediaInterface implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener, MediaPlayer.OnVideoSizeChangedListener {

    public MediaPlayer mediaPlayer;

    public JZMediaSystemAssertFolder(Jzvd jzvd) {
        super(jzvd);
    }

    @Override
    public void prepare() {
        release();
        mMediaHandlerThread = new HandlerThread("JZVD");
        mMediaHandlerThread.start();
        mMediaHandler = new Handler(mMediaHandlerThread.getLooper());//主线程还是非主线程，就在这里
        handler = new Handler();

        mMediaHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setLooping(jzvd.jzDataSource.looping);
                    mediaPlayer.setOnPreparedListener(JZMediaSystemAssertFolder.this);
                    mediaPlayer.setOnCompletionListener(JZMediaSystemAssertFolder.this);
                    mediaPlayer.setOnBufferingUpdateListener(JZMediaSystemAssertFolder.this);
                    mediaPlayer.setScreenOnWhilePlaying(true);
                    mediaPlayer.setOnSeekCompleteListener(JZMediaSystemAssertFolder.this);
                    mediaPlayer.setOnErrorListener(JZMediaSystemAssertFolder.this);
                    mediaPlayer.setOnInfoListener(JZMediaSystemAssertFolder.this);
                    mediaPlayer.setOnVideoSizeChangedListener(JZMediaSystemAssertFolder.this);

                    //two lines are different
                    AssetFileDescriptor assetFileDescriptor = jzvd.getContext().getAssets().openFd(jzvd.jzDataSource.getCurrentUrl().toString());
                    mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());

                    mediaPlayer.prepareAsync();
                    mediaPlayer.setSurface(new Surface(jzvd.textureView.getSurfaceTexture()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void start() {
        mMediaHandler.post(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.start();
            }
        });
    }

    @Override
    public void pause() {
        mMediaHandler.post(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.pause();
            }
        });
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public void seekTo(final long time) {
        mMediaHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    mediaPlayer.seekTo((int) time);
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void release() {
        if (mMediaHandler != null && mMediaHandlerThread != null && mediaPlayer != null) {//不知道有没有妖孽
            final HandlerThread tmpHandlerThread = mMediaHandlerThread;
            final MediaPlayer tmpMediaPlayer = mediaPlayer;
            JZMediaInterface.SAVED_SURFACE = null;

            mMediaHandler.post(new Runnable() {
                @Override
                public void run() {
                    tmpMediaPlayer.release();//release就不能放到主线程里，界面会卡顿
                    tmpHandlerThread.quit();
                }
            });
            mediaPlayer = null;
        }
    }

    //TODO 测试这种问题是否在threadHandler中是否正常，所有的操作mediaplayer是否不需要thread，挨个测试，是否有问题
    @Override
    public long getCurrentPosition() {
        if (mediaPlayer != null) {
            return mediaPlayer.getCurrentPosition();
        } else {
            return 0;
        }
    }

    @Override
    public long getDuration() {
        if (mediaPlayer != null) {
            return mediaPlayer.getDuration();
        } else {
            return 0;
        }
    }

    @Override
    public void setVolume(final float leftVolume, final float rightVolume) {
        if (mMediaHandler == null) return;
        mMediaHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) mediaPlayer.setVolume(leftVolume, rightVolume);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void setSpeed(float speed) {
        PlaybackParams pp = mediaPlayer.getPlaybackParams();
        pp.setSpeed(speed);
        mediaPlayer.setPlaybackParams(pp);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                jzvd.onPrepared();
            }
        });//如果是mp3音频，走这里
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                jzvd.onCompletion();
            }
        });
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, final int percent) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                jzvd.setBufferProgress(percent);
            }
        });
    }

    @Override
    public void onSeekComplete(MediaPlayer mediaPlayer) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                jzvd.onSeekComplete();
            }
        });
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, final int what, final int extra) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                jzvd.onError(what, extra);
            }
        });
        return true;
    }

    @Override
    public boolean onInfo(MediaPlayer mediaPlayer, final int what, final int extra) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                    if (jzvd.state == Jzvd.STATE_PREPARING
                            || jzvd.state == Jzvd.STATE_PREPARING_CHANGE_URL) {
                        jzvd.onPrepared();
                    }
                } else {
                    jzvd.onInfo(what, extra);
                }
            }
        });
        return false;
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mediaPlayer, final int width, final int height) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                jzvd.onVideoSizeChanged(width, height);
            }
        });
    }

    @Override
    public void setSurface(Surface surface) {
        mediaPlayer.setSurface(surface);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        Log.i("GD", "======= onSurfaceTextureAvailable =======");
        if (SAVED_SURFACE == null) {
            SAVED_SURFACE = surface;
            prepare();
        } else {
            jzvd.textureView.setSurfaceTexture(SAVED_SURFACE);
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}
