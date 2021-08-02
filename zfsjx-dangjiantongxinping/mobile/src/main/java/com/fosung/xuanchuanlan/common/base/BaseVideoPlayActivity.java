/*
 * *********************************************************
 *   author   colin
 *   company  fosung
 *   email    wanglin2046@126.com
 *   date     17-3-7 下午2:02
 * ********************************************************
 */
package com.fosung.xuanchuanlan.common.base;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.zplayer.library.ZPlayer;


/**
 * 视频播放页面基类
 */
public abstract class BaseVideoPlayActivity extends BaseActivity {

    protected ZPlayer player;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(getContentViewId());

        player = initPlayer();
    }

    //已经做了setContentView，如果客户端再次设置，不在操作
    @Override
    public void setContentView(int layoutResID) {
    }

    /**
     * 实现此函数后子类不要再进行setContentView（）操作
     */
    protected abstract int getContentViewId();

    /**
     * 初始化Player操作
     */
    protected abstract ZPlayer initPlayer();

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            player.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onBackPressed() {
//        if (player != null && player.onBackPressed()) {
//            return;
//        }
        super.onBackPressed();
    }
}
