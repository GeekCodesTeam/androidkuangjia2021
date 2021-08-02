package xyz.doikki.dkplayer.activity.extend;

import android.widget.Toast;

import com.danikula.videocachedk.HttpProxyCacheServer;

import xyz.doikki.dkplayer.R;
import xyz.doikki.dkplayer.activity.BaseActivityDk;
import xyz.doikki.dkplayer.util.DataUtilDk;
import xyz.doikki.dkplayer.util.cache.ProxyVideoCacheManagerDk;
import xyz.doikki.dkplayer.widget.component.AdControlViewDk;
import xyz.doikki.videocontroller.StandardVideoController;
import xyz.doikki.videoplayer.player.VideoView;

/**
 * 广告
 * Created by Doikki on 2017/4/7.
 */

public class ADActivityDk extends BaseActivityDk<VideoView> {

    private static final String URL_AD = "https://gslb.miaopai.com/stream/IR3oMYDhrON5huCmf7sHCfnU5YKEkgO2.mp4";

    private StandardVideoController mController;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_layout_commondk;
    }

    @Override
    protected int getTitleResId() {
        return R.string.str_ad;
    }

    @Override
    protected void initView() {
        super.initView();
        mVideoView = findViewById(R.id.video_view);
        mController = new StandardVideoController(this);
        AdControlViewDk adControlView = new AdControlViewDk(this);
        adControlView.setListener(new AdControlViewDk.AdControlListener() {
            @Override
            public void onAdClick() {
                Toast.makeText(ADActivityDk.this, "广告点击跳转", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSkipAd() {
                playVideo();
            }
        });
        mController.addControlComponent(adControlView);

        HttpProxyCacheServer cacheServer = ProxyVideoCacheManagerDk.getProxy(this);
        String proxyUrl = cacheServer.getProxyUrl(URL_AD);
        mVideoView.setUrl(proxyUrl);
        mVideoView.setVideoController(mController);

        //监听播放结束
        mVideoView.addOnStateChangeListener(new VideoView.SimpleOnStateChangeListener() {
            @Override
            public void onPlayStateChanged(int playState) {
                if (playState == VideoView.STATE_PLAYBACK_COMPLETED) {
                    playVideo();
                }
            }
        });

        mVideoView.start();
    }

    /**
     * 播放正片
     */
    private void playVideo() {
        mVideoView.release();
        mController.removeAllControlComponent();
        mController.addDefaultControlComponent("正片", false);
        //重新设置数据
        mVideoView.setUrl(DataUtilDk.SAMPLE_URL);
        //开始播放
        mVideoView.start();
    }
}
