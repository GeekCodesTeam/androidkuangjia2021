package com.example.gsydemo.banner;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gsyvideoplayer.R;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.youth.banner.Banner;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.listener.OnPageChangeListener;

/**
 * 仿淘宝商品详情，banner第一个放视频,然后首尾不能自己滑动，加上自定义数字指示器
 * 视频地址：
 * https://www.jianshu.com/p/9db0fd1579b6
 */
public class VideoActivity extends AppCompatActivity {

    Banner banner;
    StandardGSYVideoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunbo_video);
        banner = findViewById(R.id.banner);

        banner.addBannerLifecycleObserver(this)
                .setAdapter(new MultipleTypesAdapter(this, DataBean.getTestDataVideo()))
                .setIndicator(new NumIndicator(this))
                .setIndicatorGravity(IndicatorConfig.Direction.RIGHT)
                .addOnPageChangeListener(new OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        RecyclerView.ViewHolder viewHolder = ((MultipleTypesAdapter) banner.getAdapter()).getVHMap().get(position);
                        Log.e("--", "position:" + position + "VH Type is " + viewHolder.getClass().getCanonicalName());
                        if (player == null) {
                            if (viewHolder instanceof VideoHolder) {
                                VideoHolder holder = (VideoHolder) viewHolder;
                                player = holder.player;
                            }
                            return;
                        }
                        if (position != 0) {
                            player.onVideoReset();
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null)
            player.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null)
            player.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        //释放所有
        if (player != null)
            player.setVideoAllCallBack(null);
        super.onBackPressed();
    }

}
