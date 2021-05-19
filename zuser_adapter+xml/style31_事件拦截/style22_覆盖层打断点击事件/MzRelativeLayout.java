package com.haier.cellarette.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class MzRelativeLayout extends RelativeLayout {

    private boolean itouch=false;

    public MzRelativeLayout(Context context) {
        super(context);
    }

    public MzRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MzRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return itouch;
    }

    public void setTouch(boolean is){
        itouch = is;
    }
}
