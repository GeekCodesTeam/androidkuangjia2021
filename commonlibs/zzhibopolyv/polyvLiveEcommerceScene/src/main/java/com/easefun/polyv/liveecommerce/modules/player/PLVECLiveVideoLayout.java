package com.easefun.polyv.liveecommerce.modules.player;

import androidx.lifecycle.LiveData;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easefun.polyv.businesssdk.api.auxiliary.PolyvAuxiliaryVideoview;
import com.easefun.polyv.businesssdk.api.common.meidacontrol.IPolyvMediaController;
import com.easefun.polyv.businesssdk.api.common.player.PolyvPlayError;
import com.easefun.polyv.businesssdk.api.common.player.PolyvPlayerScreenRatio;
import com.easefun.polyv.businesssdk.model.video.PolyvDefinitionVO;
import com.easefun.polyv.businesssdk.model.video.PolyvMediaPlayMode;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.modules.player.PLVEmptyMediaController;
import com.easefun.polyv.livecommon.module.modules.player.PLVPlayerState;
import com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract;
import com.easefun.polyv.livecommon.module.modules.player.live.presenter.PLVLivePlayerPresenter;
import com.easefun.polyv.livecommon.module.modules.player.live.view.PLVAbsLivePlayerView;
import com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.data.PLVPlayInfoVO;
import com.easefun.polyv.livecommon.module.utils.PLVVideoSizeUtils;
import com.easefun.polyv.livecommon.ui.widget.PLVPlayerLogoView;
import com.easefun.polyv.liveecommerce.R;
import com.easefun.polyv.liveecommerce.modules.player.constant.PLVECFitMode;
import com.easefun.polyv.livescenes.video.PolyvLiveVideoView;
import com.easefun.polyv.livescenes.video.api.IPolyvLiveAudioModeView;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;
import com.plv.thirdpart.blankj.utilcode.util.ToastUtils;

import java.util.List;

/**
 * date: 2020-04-29
 * author: hwj
 * description:?????????????????????
 */
public class PLVECLiveVideoLayout extends FrameLayout implements IPLVECVideoLayout, View.OnClickListener {
    private static final String TAG = "PLVECLiveVideoLayout";
    // <editor-fold defaultstate="collapsed" desc="??????">

    //??????????????????????????????
    private boolean isAllowOpenAdHead = true;
    //??????????????????????????????????????????????????????????????????
    private Rect videoViewRect;
    //????????????????????????
    private IPLVLiveRoomDataManager liveRoomDataManager;
    //????????????????????????view
    private PolyvLiveVideoView videoView;
    //????????????????????????view
    private PolyvAuxiliaryVideoview subVideoView;
    //??????????????????????????????view
    private TextView tvCountDown;
    private LinearLayout llAuxiliaryCountDown;
    //???????????????????????????
    private boolean isShowingAdHeadCountDown = true;
    //????????????view
    private IPolyvLiveAudioModeView audioModeView;
    //?????????
    private IPolyvMediaController mediaController;
    //????????????????????????view
    private View loadingView;

    //??????????????????
    private ImageView playCenterView;

    //???????????????????????????view
    private View nostreamView;
    //??????????????????
    private ImageView closeFloatingView;
    //?????????presenter
    private IPLVLivePlayerContract.ILivePlayerPresenter livePlayerPresenter;

    //logo view
    private PLVPlayerLogoView logoView;
    //?????????????????????????????????
    private ViewGroup videoViewParentParent;
    //???????????????????????????????????????????????????
    private int videoViewParentIndexInParent;
    //????????????????????????????????????VideoLayout???????????????
    private boolean isVideoViewParentDetachVideoLayout;
    //????????????????????????VideoLayout????????????????????????
    private int fitMode = PLVECFitMode.FIT_NONE;

    private ViewTreeObserver.OnGlobalLayoutListener onSubVideoViewLayoutListener;

    //Listener
    private IPLVECVideoLayout.OnViewActionListener onViewActionListener;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="?????????">
    public PLVECLiveVideoLayout(@NonNull Context context) {
        this(context, null);
    }

    public PLVECLiveVideoLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PLVECLiveVideoLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="?????????view">
    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.plvec_live_player_layout, this, true);
        videoView = findViewById(R.id.plvec_live_video_view);

        playCenterView = findViewById(R.id.play_center);
        hidePlayCenterView();
        playCenterView.setOnClickListener(this);

        subVideoView = findViewById(R.id.sub_video_view);
        tvCountDown = findViewById(R.id.auxiliary_tv_count_down);
        llAuxiliaryCountDown = findViewById(R.id.polyv_auxiliary_controller_ll_tips);
        llAuxiliaryCountDown.setVisibility(GONE);
        audioModeView = findViewById(R.id.audio_mode_ly);
        loadingView = findViewById(R.id.loading_pb);
        nostreamView = findViewById(R.id.nostream_ly);
        closeFloatingView = findViewById(R.id.close_floating_iv);
        closeFloatingView.setOnClickListener(this);
        mediaController = new PLVEmptyMediaController();
        videoViewParentParent = (ViewGroup) videoView.getParent().getParent();
        videoViewParentIndexInParent = videoViewParentParent.indexOfChild((View) videoView.getParent());

        logoView = findViewById(R.id.logo_view);

        initVideoView();
        initSubVideoViewChangeListener();
    }

    private void initVideoView() {
        videoView.setSubVideoView(subVideoView);
        videoView.setAudioModeView(audioModeView);
        videoView.setPlayerBufferingIndicator(loadingView);
        videoView.setNoStreamIndicator(nostreamView);
        videoView.setMediaController(mediaController);
    }

    private void initSubVideoViewChangeListener() {
        onSubVideoViewLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!isShowingAdHeadCountDown) {
                    llAuxiliaryCountDown.setVisibility(GONE);
                    return;
                }
                if (subVideoView == null || !subVideoView.isShow()) {
                    return;
                }
                if (subVideoView.getAdHeadImage() != null) {
                    llAuxiliaryCountDown.setY(subVideoView.getAdHeadImage().getY() + ConvertUtils.dp2px(15));
                } else {
                    float y = subVideoView.getY();
                    int viewHeight = PLVVideoSizeUtils.getVideoWH(subVideoView)[1];
                    if (subVideoView.getAspectRatio() == PolyvPlayerScreenRatio.AR_ASPECT_FIT_PARENT) {
                        int surHeight = subVideoView.getHeight();
                        if (viewHeight == 0 || surHeight == 0) {
                            return;
                        }
                        y = y + (float) ((surHeight - viewHeight) >> 1);
                    } else {
                        y = ConvertUtils.dp2px(112);
                    }
                    llAuxiliaryCountDown.setY(y);
                }
            }
        };
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="??????API- ??????IPLVECVideoLayout?????????common??????">
    @Override
    public void init(IPLVLiveRoomDataManager liveRoomDataManager) {
        this.liveRoomDataManager = liveRoomDataManager;

        livePlayerPresenter = new PLVLivePlayerPresenter(liveRoomDataManager);
        livePlayerPresenter.registerView(livePlayerView);
        livePlayerPresenter.init();
        livePlayerPresenter.setAllowOpenAdHead(isAllowOpenAdHead);
    }

    @Override
    public void startPlay() {
        livePlayerPresenter.startPlay();
    }

    @Override
    public void pause() {
        livePlayerPresenter.pause();
    }

    @Override
    public void resume() {
        livePlayerPresenter.resume();
    }

    @Override
    public boolean isInPlaybackState() {
        return livePlayerPresenter.isInPlaybackState();
    }

    @Override
    public boolean isPlaying() {
        return livePlayerPresenter.isPlaying();
    }

    @Override
    public boolean isSubVideoViewShow() {
        return livePlayerPresenter.isSubVideoViewShow();
    }

    @Override
    public String getSubVideoViewHerf() {
        return livePlayerPresenter.getSubVideoViewHerf();
    }

    @Override
    public void setPlayerVolume(int volume) {
        livePlayerPresenter.setPlayerVolume(volume);
    }

    @Override
    public LiveData<PLVPlayerState> getPlayerState() {
        return livePlayerPresenter.getData().getPlayerState();
    }

    @Override
    public void setOnViewActionListener(OnViewActionListener listener) {
        this.onViewActionListener = listener;
    }

    @Override
    public void setFloatingWindow(boolean isFloating) {
        isShowingAdHeadCountDown = !isFloating;
    }

    @Override
    public View detachVideoViewParent() {
        //??????????????????????????????????????????
        PLVVideoSizeUtils.fitVideoRect(true, videoView.getParent(), videoViewRect);
        videoView.setAspectRatio(PolyvPlayerScreenRatio.AR_ASPECT_FIT_PARENT);
        subVideoView.setAspectRatio(PolyvPlayerScreenRatio.AR_ASPECT_FIT_PARENT);
        //??????????????????
        videoView.setNeedGestureDetector(false);
        subVideoView.setNeedGestureDetector(false);
        //???????????????
        videoView.setBackgroundColor(Color.parseColor("#6F6F6F"));
        //???????????????????????????????????????????????????????????????????????????
        subVideoView.setBackgroundColor(Color.parseColor("#6F6F6F"));

        videoViewParentParent.removeView((View) videoView.getParent());
        closeFloatingView.setVisibility(View.VISIBLE);
        isVideoViewParentDetachVideoLayout = true;
        return (View) videoView.getParent();
    }

    @Override
    public void attachVideoViewParent(View view) {
        //??????view?????????????????????
        videoViewParentParent.addView(view, videoViewParentIndexInParent);

        //??????????????????????????????????????????
        fitVideoRatioAndRect();
        //??????????????????
        videoView.setNeedGestureDetector(true);
        subVideoView.setNeedGestureDetector(true);
        //???????????????
        videoView.setBackgroundColor(Color.TRANSPARENT);
        subVideoView.setBackgroundColor(Color.TRANSPARENT);

        closeFloatingView.setVisibility(View.GONE);
        isVideoViewParentDetachVideoLayout = false;
    }

    @Override
    public void destroy() {
        if (audioModeView != null) {
            audioModeView.onHide();
        }
        if (livePlayerPresenter != null) {
            livePlayerPresenter.destroy();
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="??????API- ??????IPLVECVideoLayout?????????live??????">
    @Override
    public void setVideoViewRect(Rect videoViewRect) {
        this.videoViewRect = videoViewRect;
        if (!isVideoViewParentDetachVideoLayout) {
            fitVideoRatioAndRect();
        }
    }

    @Override
    public int getLinesPos() {
        return livePlayerPresenter.getLinesPos();
    }

    @Override
    public int getLinesCount() {
        return livePlayerPresenter.getLinesCount();
    }

    @Override
    public void changeLines(int linesPos) {
        livePlayerPresenter.changeLines(linesPos);
    }

    @Override
    public int getBitratePos() {
        return livePlayerPresenter.getBitratePos();
    }

    @Override
    public List<PolyvDefinitionVO> getBitrateVO() {
        return livePlayerPresenter.getBitrateVO();
    }

    @Override
    public void changeBitRate(int bitratePos) {
        livePlayerPresenter.changeBitRate(bitratePos);
    }

    @Override
    public int getMediaPlayMode() {
        return livePlayerPresenter.getMediaPlayMode();
    }

    @Override
    public void changeMediaPlayMode(int mediaPlayMode) {
        livePlayerPresenter.changeMediaPlayMode(mediaPlayMode);
    }

    @Override
    public LiveData<com.easefun.polyv.livecommon.module.modules.player.live.presenter.data.PLVPlayInfoVO> getLivePlayInfoVO() {
        return livePlayerPresenter.getData().getPlayInfoVO();
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="??????API- ??????IPLVECVideoLayout?????????playback??????????????????">

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    public void seekTo(int progress, int max) {
        PLVCommonLog.d(TAG,"live video cannot seek");
    }

    @Override
    public void setSpeed(float speed) {
        PLVCommonLog.d(TAG,"live video cannot set Speed");
    }

    @Override
    public float getSpeed() {
        return 0;
    }

    @Override
    public LiveData<PLVPlayInfoVO> getPlaybackPlayInfoVO() {
        return null;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????? - MVP?????????view?????????">
    private IPLVLivePlayerContract.ILivePlayerView livePlayerView = new PLVAbsLivePlayerView() {
        @Override
        public void setPresenter(@NonNull IPLVLivePlayerContract.ILivePlayerPresenter presenter) {
            super.setPresenter(presenter);
            livePlayerPresenter = presenter;
        }

        @Override
        public PolyvLiveVideoView getLiveVideoView() {
            return videoView;
        }

        @Override
        public PolyvAuxiliaryVideoview getSubVideoView() {
            return subVideoView;
        }

        @Override
        public View getBufferingIndicator() {
            return loadingView;
        }

        @Override
        public View getNoStreamIndicator() {
            return nostreamView;
        }

        @Override
        public PLVPlayerLogoView getLogo() {
            return logoView;
        }

        @Override
        public void onSubVideoViewPlay(boolean isFirst) {
            super.onSubVideoViewPlay(isFirst);
            fitMode = PLVECFitMode.FIT_VIDEO_RATIO_AND_RECT_SUB_VIDEOVIEW;
            if (!isVideoViewParentDetachVideoLayout) {
                PLVVideoSizeUtils.fitVideoRatioAndRect(subVideoView, videoView.getParent(), videoViewRect);//???????????????viewParent
            }
        }

        @Override
        public void onSubVideoViewCountDown(boolean isOpenAdHead, int totalTime, int remainTime, int adStage) {
            if (!isShowingAdHeadCountDown) {
                llAuxiliaryCountDown.setVisibility(GONE);
                return;
            }
            if (isOpenAdHead) {
                llAuxiliaryCountDown.setVisibility(VISIBLE);
                tvCountDown.setText("?????? ???" + remainTime + "s");
            }
        }

        @Override
        public void onSubVideoViewVisiblityChanged(boolean isOpenAdHead, boolean isShow) {
            if (isOpenAdHead) {
                if (!isShow) {
                    llAuxiliaryCountDown.setVisibility(GONE);
                    subVideoView.getViewTreeObserver().removeOnGlobalLayoutListener(onSubVideoViewLayoutListener);
                } else {
                    subVideoView.getViewTreeObserver().addOnGlobalLayoutListener(onSubVideoViewLayoutListener);
                }
            } else {
                llAuxiliaryCountDown.setVisibility(GONE);
            }
        }

        @Override
        public void onPlayError(PolyvPlayError error, String tips) {
            super.onPlayError(error, tips);
            ToastUtils.showLong(tips);
            fitMode = PLVECFitMode.FIT_VIDEO_RECT_FALSE;
            if (!isVideoViewParentDetachVideoLayout) {
                PLVVideoSizeUtils.fitVideoRect(false, videoView.getParent(), videoViewRect);
            }
        }

        @Override
        public void onNoLiveAtPresent() {
            super.onNoLiveAtPresent();
            fitMode = PLVECFitMode.FIT_VIDEO_RECT_FALSE;
            if (!isVideoViewParentDetachVideoLayout) {
                PLVVideoSizeUtils.fitVideoRect(false, videoView.getParent(), videoViewRect);
            }
            ToastUtils.showShort(R.string.plv_player_toast_no_live);
            hidePlayCenterView();
        }

        @Override
        public void onLiveStop() {
            super.onLiveStop();
            fitMode = PLVECFitMode.FIT_VIDEO_RECT_FALSE;
            if (!isVideoViewParentDetachVideoLayout) {
                PLVVideoSizeUtils.fitVideoRect(false, videoView.getParent(), videoViewRect);
            }
            hidePlayCenterView();
        }

        @Override
        public void onLiveEnd() {
            super.onLiveEnd();
            ToastUtils.showShort(R.string.plv_player_toast_live_end);
            hidePlayCenterView();
        }

        @Override
        public void onPrepared(int mediaPlayMode) {
            super.onPrepared(mediaPlayMode);
            if (mediaPlayMode == PolyvMediaPlayMode.MODE_VIDEO) {
                fitMode = PLVECFitMode.FIT_VIDEO_RATIO_AND_RECT_VIDEOVIEW;
                if (!isVideoViewParentDetachVideoLayout) {
                    PLVVideoSizeUtils.fitVideoRatioAndRect(videoView, videoView.getParent(), videoViewRect);

                }
            } else if (mediaPlayMode == PolyvMediaPlayMode.MODE_AUDIO) {
                fitMode = PLVECFitMode.FIT_VIDEO_RECT_FALSE;
                if (!isVideoViewParentDetachVideoLayout) {
                    PLVVideoSizeUtils.fitVideoRect(false, videoView.getParent(), videoViewRect);
                }
            }
        }

        @Override
        public void onLinesChanged(int linesPos) {
            super.onLinesChanged(linesPos);
        }

        @Override
        public void updatePlayInfo(com.easefun.polyv.livecommon.module.modules.player.live.presenter.data.PLVPlayInfoVO playInfoVO) {
            if (playInfoVO != null && isInPlaybackState()
                    && !playInfoVO.isPlaying()) {
                showPlayCenterView();
            } else {
                hidePlayCenterView();
            }
        }
    };
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????? - ????????????????????????????????????">
    private void hidePlayCenterView() {
        playCenterView.setVisibility(GONE);
    }

    private void showPlayCenterView() {
        if (!isSubVideoViewShow()) {
            playCenterView.setVisibility(VISIBLE);
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????? - ????????????????????????????????????????????????">
    private void fitVideoRatioAndRect() {
        if (fitMode == PLVECFitMode.FIT_VIDEO_RATIO_AND_RECT_SUB_VIDEOVIEW) {
            PLVVideoSizeUtils.fitVideoRatioAndRect(subVideoView, videoView.getParent(), videoViewRect);//???????????????viewParent
        } else if (fitMode == PLVECFitMode.FIT_VIDEO_RATIO_AND_RECT_VIDEOVIEW) {
            PLVVideoSizeUtils.fitVideoRatioAndRect(videoView, videoView.getParent(), videoViewRect);
        } else if (fitMode == PLVECFitMode.FIT_VIDEO_RECT_FALSE) {
            PLVVideoSizeUtils.fitVideoRect(false, videoView.getParent(), videoViewRect);
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????????">
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.close_floating_iv) {
            if (onViewActionListener != null) {
                onViewActionListener.onCloseFloatingAction();
            }
        } else if (v.getId() == R.id.play_center) {
            resume();
        }
    }
    // </editor-fold>
}
