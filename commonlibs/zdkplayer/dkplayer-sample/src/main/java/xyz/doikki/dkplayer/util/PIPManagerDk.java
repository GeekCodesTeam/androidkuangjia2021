package xyz.doikki.dkplayer.util;

import android.view.View;

import xyz.doikki.dkplayer.widget.FloatViewDk;
import xyz.doikki.dkplayer.widget.controller.FloatControllerDk;
import xyz.doikki.videoplayer.player.VideoView;
import xyz.doikki.videoplayer.player.VideoViewManager;

/**
 * 悬浮播放
 * Created by Doikki on 2018/3/30.
 */

public class PIPManagerDk {

    private static PIPManagerDk instance;
    private VideoView mVideoView;
    private FloatViewDk mFloatView;
    private FloatControllerDk mFloatController;
    private boolean mIsShowing;
    private int mPlayingPosition = -1;
    private Class mActClass;


    private PIPManagerDk() {
        mVideoView = new VideoView(DKApp.get());
        VideoViewManager.instance().add(mVideoView, TagDk.PIP);
        mFloatController = new FloatControllerDk(DKApp.get());
        mFloatView = new FloatViewDk(DKApp.get(), 0, 0);
    }

    public static PIPManagerDk getInstance() {
        if (instance == null) {
            synchronized (PIPManagerDk.class) {
                if (instance == null) {
                    instance = new PIPManagerDk();
                }
            }
        }
        return instance;
    }

    public void startFloatWindow() {
        if (mIsShowing) {
            return;
        }
        UtilsDk.removeViewFormParent(mVideoView);
        mVideoView.setVideoController(mFloatController);
        mFloatController.setPlayState(mVideoView.getCurrentPlayState());
        mFloatController.setPlayerState(mVideoView.getCurrentPlayerState());
        mFloatView.addView(mVideoView);
        mFloatView.addToWindow();
        mIsShowing = true;
    }

    public void stopFloatWindow() {
        if (!mIsShowing) {
            return;
        }
        mFloatView.removeFromWindow();
        UtilsDk.removeViewFormParent(mVideoView);
        mIsShowing = false;
    }

    public void setPlayingPosition(int position) {
        this.mPlayingPosition = position;
    }

    public int getPlayingPosition() {
        return mPlayingPosition;
    }

    public void pause() {
        if (mIsShowing) {
            return;
        }
        mVideoView.pause();
    }

    public void resume() {
        if (mIsShowing) {
            return;
        }
        mVideoView.resume();
    }

    public void reset() {
        if (mIsShowing) {
            return;
        }
        UtilsDk.removeViewFormParent(mVideoView);
        mVideoView.release();
        mVideoView.setVideoController(null);
        mPlayingPosition = -1;
        mActClass = null;
    }

    public boolean onBackPress() {
        return !mIsShowing && mVideoView.onBackPressed();
    }

    public boolean isStartFloatWindow() {
        return mIsShowing;
    }

    /**
     * 显示悬浮窗
     */
    public void setFloatViewVisible() {
        if (mIsShowing) {
            mVideoView.resume();
            mFloatView.setVisibility(View.VISIBLE);
        }
    }

    public void setActClass(Class cls) {
        this.mActClass = cls;
    }

    public Class getActClass() {
        return mActClass;
    }

}
