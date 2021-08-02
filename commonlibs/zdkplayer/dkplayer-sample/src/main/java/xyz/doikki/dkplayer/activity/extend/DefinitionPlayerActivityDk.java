package xyz.doikki.dkplayer.activity.extend;

import xyz.doikki.dkplayer.R;
import xyz.doikki.dkplayer.activity.BaseActivityDk;
import xyz.doikki.dkplayer.widget.component.DefinitionControlViewDk;
import xyz.doikki.videocontroller.StandardVideoController;
import xyz.doikki.videocontroller.component.CompleteView;
import xyz.doikki.videocontroller.component.ErrorView;
import xyz.doikki.videocontroller.component.GestureView;
import xyz.doikki.videocontroller.component.PrepareView;
import xyz.doikki.videocontroller.component.TitleView;
import xyz.doikki.videoplayer.player.VideoView;

import java.util.LinkedHashMap;

/**
 * 清晰度切换
 * Created by Doikki on 2017/4/7.
 */

public class DefinitionPlayerActivityDk extends BaseActivityDk<VideoView> implements DefinitionControlViewDk.OnRateSwitchListener {

    private StandardVideoController mController;
    private DefinitionControlViewDk mDefinitionControlView;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_layout_commondk;
    }

    @Override
    protected int getTitleResId() {
        return R.string.str_definition;
    }

    @Override
    protected void initView() {
        super.initView();
        mVideoView = findViewById(R.id.video_view);

        mController = new StandardVideoController(this);
        addControlComponents();

        LinkedHashMap<String, String> videos = new LinkedHashMap<>();
//        videos.put("标清", "http://34.92.158.191:8080/test-sd.mp4");
//        videos.put("高清", "http://34.92.158.191:8080/test-hd.mp4");
        videos.put("标清", "http://vfx.mtime.cn/Video/2019/03/12/mp4/190312083533415853.mp4");
        videos.put("高清", "http://vfx.mtime.cn/Video/2019/03/13/mp4/190313094901111138.mp4");
        mDefinitionControlView.setData(videos);
        mVideoView.setVideoController(mController);
        mVideoView.setUrl(videos.get("标清"));//默认播放标清
        mVideoView.start();
    }

    private void addControlComponents() {
        CompleteView completeView = new CompleteView(this);
        ErrorView errorView = new ErrorView(this);
        PrepareView prepareView = new PrepareView(this);
        prepareView.setClickStart();
        TitleView titleView = new TitleView(this);
        mDefinitionControlView = new DefinitionControlViewDk(this);
        mDefinitionControlView.setOnRateSwitchListener(this);
        GestureView gestureView = new GestureView(this);
        mController.addControlComponent(completeView, errorView, prepareView, titleView, mDefinitionControlView, gestureView);
    }

    @Override
    public void onRateChange(String url) {
        mVideoView.setUrl(url);
        mVideoView.replay(false);
    }
}
