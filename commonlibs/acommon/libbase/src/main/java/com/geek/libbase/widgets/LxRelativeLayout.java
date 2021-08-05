package com.geek.libbase.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.geek.libbase.R;

public class LxRelativeLayout extends RelativeLayout {

    private boolean itouch = false;

    public LxRelativeLayout(Context context) {
        super(context);
    }

    public LxRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LxRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LxLayout, defStyleAttr, 0);
        itouch = array.getBoolean(R.styleable.LxLayout_is_touch, false);
        array.recycle();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return itouch;
    }

    public void setTouch(boolean is) {
        itouch = is;
    }
}
