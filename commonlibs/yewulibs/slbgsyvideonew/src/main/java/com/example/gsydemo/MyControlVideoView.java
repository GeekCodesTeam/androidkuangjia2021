package com.example.gsydemo;

import android.content.Context;
import android.util.AttributeSet;

import com.example.gsyvideoplayer.R;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;


public class MyControlVideoView extends StandardGSYVideoPlayer {

    public MyControlVideoView(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public MyControlVideoView(Context context) {
        super(context);
    }

    public MyControlVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_my_control_videoview;
    }
}
