package com.haier.cellarette.baselibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.haier.cellarette.baselibrary.R;

public class LxLinearLayout extends LinearLayout {

    private boolean itouch;

    public LxLinearLayout(Context context) {
        super(context);
    }

    public LxLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LxLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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
