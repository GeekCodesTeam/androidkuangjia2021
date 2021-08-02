package com.example.gsydemo;

import android.content.Context;
import android.util.AttributeSet;

import com.example.gsyvideoplayer.R;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;


public class MyControlVideoView2 extends StandardGSYVideoPlayer {

    public MyControlVideoView2(Context context, Boolean fullFlag) {
        super(context, fullFlag);
        init();
    }

    public MyControlVideoView2(Context context) {
        super(context);
        init();
    }

    public MyControlVideoView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        mBottomContainer.setVisibility(GONE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_my_control_videoview;
    }
}
