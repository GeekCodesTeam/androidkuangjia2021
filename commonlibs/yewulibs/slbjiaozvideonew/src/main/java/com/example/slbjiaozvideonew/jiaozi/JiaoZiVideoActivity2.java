package com.example.slbjiaozvideonew.jiaozi;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.slbjiaozvideonew.R;

import cn.jzvd.JZMediaSystem;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class JiaoZiVideoActivity2 extends AppCompatActivity {

    private String query1;
    private String query2;
    private JzvdStd mJzvdStd;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiaozi1);
        query1 = getIntent().getStringExtra("query1");
        query2 = getIntent().getStringExtra("query2");
        mJzvdStd = findViewById(R.id.jz_video);
        mJzvdStd.setUp(query1, query2, JzvdStd.SCREEN_NORMAL, JZMediaSystem.class);
        Glide.with(this)
                .load("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640")
                .into(mJzvdStd.posterImageView);
        //
        mJzvdStd.startVideo();
//        //
//        mJzvdStd.startPreloading(); //开始预加载，加载完等待播放
//        mJzvdStd.startVideoAfterPreloading(); //如果预加载完会开始播放，如果未加载则开始加载
//        //
//        Jzvd.setTextureViewRotation(90); //0 - 360 旋转的角度
//        //
//        ScreenRotateUtils.getInstance(this.getApplicationContext()).setOrientationChangeListener(new ScreenRotateUtils.OrientationChangeListener() {
//            @Override
//            public void orientationChange(int orientation) {
//                if (Jzvd.CURRENT_JZVD != null
//                        && (mJzvdStd.state == Jzvd.STATE_PLAYING || mJzvdStd.state == Jzvd.STATE_PAUSE)
//                        && mJzvdStd.screen != Jzvd.SCREEN_TINY) {
//                    if (orientation >= 45 && orientation <= 315 && mJzvdStd.screen == Jzvd.SCREEN_NORMAL) {
//                        //从竖屏状态进入横屏
//                        if (mJzvdStd != null && mJzvdStd.screen != Jzvd.SCREEN_FULLSCREEN) {
//                            if ((System.currentTimeMillis() - Jzvd.lastAutoFullscreenTime) > 2000) {
//                                mJzvdStd.autoFullscreen(ScreenRotateUtils.orientationDirection);
//                                Jzvd.lastAutoFullscreenTime = System.currentTimeMillis();
//                            }
//                        }
//                    } else if (((orientation >= 0 && orientation < 45) || orientation > 315) && mJzvdStd.screen == Jzvd.SCREEN_FULLSCREEN) {
//                        //竖屏并退出全屏
//                        if (mJzvdStd != null && mJzvdStd.screen == Jzvd.SCREEN_FULLSCREEN) {
//                            mJzvdStd.autoQuitFullscreen();
//                        }
//                    }
//                }
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        ScreenRotateUtils.getInstance(this.getApplicationContext()).setOrientationChangeListener(null);
    }


    @Override
    protected void onResume() {
        super.onResume();
//        //
//        Jzvd.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
//        Jzvd.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;
//        //
//        ScreenRotateUtils.getInstance(this).start(this);
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
//        //Change these two variables back
//        Jzvd.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;
//        Jzvd.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
//        //
//        ScreenRotateUtils.getInstance(this).stop();
    }
}
