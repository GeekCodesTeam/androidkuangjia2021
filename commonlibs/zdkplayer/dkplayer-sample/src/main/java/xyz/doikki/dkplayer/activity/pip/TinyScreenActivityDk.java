package xyz.doikki.dkplayer.activity.pip;

import android.content.pm.ActivityInfo;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import xyz.doikki.dkplayer.R;
import xyz.doikki.dkplayer.activity.BaseActivityDk;
import xyz.doikki.dkplayer.adapter.VideoRecyclerViewAdapterDk;
import xyz.doikki.dkplayer.adapter.listener.OnItemChildClickListenerDk;
import xyz.doikki.dkplayer.bean.VideoBeanDk;
import xyz.doikki.dkplayer.util.DataUtilDk;
import xyz.doikki.dkplayer.util.UtilsDk;
import xyz.doikki.videocontroller.StandardVideoController;
import xyz.doikki.videocontroller.component.CompleteView;
import xyz.doikki.videocontroller.component.ErrorView;
import xyz.doikki.videocontroller.component.GestureView;
import xyz.doikki.videocontroller.component.TitleView;
import xyz.doikki.videocontroller.component.VodControlView;
import xyz.doikki.videoplayer.player.VideoView;

import java.util.List;

/**
 * 小窗播放
 * Created by Doikki on 2017/5/31.
 */
public class TinyScreenActivityDk extends BaseActivityDk<VideoView> implements OnItemChildClickListenerDk {

    private StandardVideoController mController;
    private List<VideoBeanDk> mVideos;
    private LinearLayoutManager mLinearLayoutManager;
    private TitleView mTitleView;
    private int mCurPos = -1;

    @Override
    protected int getTitleResId() {
        return R.string.str_tiny_screen;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_recycler_viewdk;
    }

    @Override
    protected void initView() {
        mVideoView = new VideoView(this);
        mVideoView.setOnStateChangeListener(new VideoView.SimpleOnStateChangeListener() {
            @Override
            public void onPlayStateChanged(int playState) {
                if (playState == VideoView.STATE_PLAYBACK_COMPLETED) {
                    if (mVideoView.isTinyScreen()) {
                        mVideoView.stopTinyScreen();
                        releaseVideoView();
                    }
                }
            }
        });
        mController = new StandardVideoController(this);
        addControlComponent();

        initRecyclerView();
    }

    private void addControlComponent() {
        CompleteView completeView = new CompleteView(this);
        ErrorView errorView = new ErrorView(this);
        mTitleView = new TitleView(this);
        mController.addControlComponent(completeView, errorView, mTitleView);
        mController.addControlComponent(new VodControlView(this));
        mController.addControlComponent(new GestureView(this));
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.rv);
        mLinearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        mVideos = DataUtilDk.getVideoList();
        VideoRecyclerViewAdapterDk adapter = new VideoRecyclerViewAdapterDk(mVideos);
        adapter.setOnItemChildClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {
                VideoRecyclerViewAdapterDk.VideoHolder holder = (VideoRecyclerViewAdapterDk.VideoHolder) view.getTag();
                int position = holder.mPosition;
                if (position == mCurPos) {
                    startPlay(position, false);
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
                VideoRecyclerViewAdapterDk.VideoHolder holder = (VideoRecyclerViewAdapterDk.VideoHolder) view.getTag();
                int position = holder.mPosition;
                if (position == mCurPos && !mVideoView.isFullScreen()) {
                    mVideoView.startTinyScreen();
                    mVideoView.setVideoController(null);
                    mController.setPlayState(VideoView.STATE_IDLE);
                }
            }
        });
    }

    @Override
    public void onItemChildClick(int position) {
        startPlay(position, true);
    }

    /**
     * 开始播放
     *
     * @param position 列表位置
     */
    protected void startPlay(int position, boolean isRelease) {
        if (mVideoView.isTinyScreen()) {
            mVideoView.stopTinyScreen();
        }
        if (mCurPos != -1 && isRelease) {
            releaseVideoView();
        }
        VideoBeanDk videoBean = mVideos.get(position);
        mVideoView.setUrl(videoBean.getUrl());
        mTitleView.setTitle(videoBean.getTitle());
        View itemView = mLinearLayoutManager.findViewByPosition(position);
        if (itemView == null) {
            return;
        }
        //注意：要先设置控制才能去设置控制器的状态。
        mVideoView.setVideoController(mController);
        mController.setPlayState(mVideoView.getCurrentPlayState());

        VideoRecyclerViewAdapterDk.VideoHolder viewHolder = (VideoRecyclerViewAdapterDk.VideoHolder) itemView.getTag();
        //把列表中预置的PrepareView添加到控制器中，注意isPrivate此处只能为true。
        mController.addControlComponent(viewHolder.mPrepareView, true);
        UtilsDk.removeViewFormParent(mVideoView);
        viewHolder.mPlayerContainer.addView(mVideoView, 0);
        mVideoView.start();
        mCurPos = position;
    }

    private void releaseVideoView() {
        mVideoView.release();
        if (mVideoView.isFullScreen()) {
            mVideoView.stopFullScreen();
        }
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        mCurPos = -1;
    }
}
