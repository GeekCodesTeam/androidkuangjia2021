package com.example.slbappcomm.widgets.recyclerviewnice;

import android.content.Context;
import androidx.core.widget.NestedScrollView;
import android.util.AttributeSet;

import com.blankj.utilcode.util.ScreenUtils;

public class MyNestedScrollView extends NestedScrollView {

    private int h = ScreenUtils.getScreenHeight() / 2;

    private OnScrollListener listener;

    public void setOnScrollListener(OnScrollListener listener) {
        this.listener = listener;
    }

    public MyNestedScrollView(Context context) {
        super(context);
    }

    public MyNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface OnScrollListener {
        void onScroll(int l, int t, int oldl, int scrollY);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (listener != null) {
            listener.onScroll(l, t, oldl, oldt);
        }
        //
//        if (l - oldt >= h) {
//            Glide.with(getContext()).resumeRequests();
//        } else {
//            Glide.with(getContext()).pauseRequests();
//        }
        //判断某个控件是否可见
//        Rect scrollBounds = new Rect();
//        this.getHitRect(scrollBounds);
//        if (tvscrollthree.getLocalVisibleRect(scrollBounds)) {//可见
//            MyLogUtil.e("--geek2323-", "onScrollChange:  第3个可见");
//        } else {//完全不可见
//            MyLogUtil.e("--geek2323-", "onScrollChange:  第3个不可见");
//        }

    }
}