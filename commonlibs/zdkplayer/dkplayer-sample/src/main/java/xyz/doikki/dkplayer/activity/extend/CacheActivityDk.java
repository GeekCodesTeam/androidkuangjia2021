package xyz.doikki.dkplayer.activity.extend;

import com.danikula.videocachedk.HttpProxyCacheServer;

import xyz.doikki.dkplayer.R;
import xyz.doikki.dkplayer.activity.BaseActivityDk;
import xyz.doikki.dkplayer.util.DataUtilDk;
import xyz.doikki.dkplayer.util.cache.ProxyVideoCacheManagerDk;
import xyz.doikki.videocontroller.StandardVideoController;
import xyz.doikki.videoplayer.player.VideoView;

public class CacheActivityDk extends BaseActivityDk<VideoView> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_layout_commondk;
    }

    @Override
    protected int getTitleResId() {
        return R.string.str_cache;
    }

    @Override
    protected void initView() {
        super.initView();
        mVideoView = findViewById(R.id.video_view);
        HttpProxyCacheServer cacheServer = ProxyVideoCacheManagerDk.getProxy(this);
        String proxyUrl = cacheServer.getProxyUrl(DataUtilDk.SAMPLE_URL);
        mVideoView.setUrl(proxyUrl);
        StandardVideoController controller = new StandardVideoController(this);
        controller.addDefaultControlComponent(getString(R.string.str_cache), false);
        mVideoView.setVideoController(controller);
        mVideoView.start();

        //删除url对应默认缓存文件
//        ProxyVideoCacheManager.clearDefaultCache(this, URL);
        //清除缓存文件中的所有缓存
//        ProxyVideoCacheManager.clearAllCache(this);
    }
}
