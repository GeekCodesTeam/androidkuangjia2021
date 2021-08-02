package com.example.slbappcomm.zuni;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

public class BounceScrollView extends ScrollView {
    private boolean isCalled;
    private Callback mCallback;
    private View mView;
    private Rect mRect = new Rect();
    private int y;
    private boolean isFirst = true;

    public BounceScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        if (getChildCount() > 0)
            mView = getChildAt(0);
        super.onFinishInflate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mView != null) {
            commonOnTouch(ev);
        }
        return super.onTouchEvent(ev);
    }

    private void commonOnTouch(MotionEvent ev) {
        int action = ev.getAction();
        int cy = (int) ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            break;
            case MotionEvent.ACTION_MOVE:
            int dy = cy - y;
            if (isFirst) {
                dy = 0;
                isFirst = false;
            }
            y = cy;
            if (isNeedMove()) {
                if (mRect.isEmpty()) {
                    mRect.set(mView.getLeft(), mView.getTop(),
                            mView.getRight(), mView.getBottom());
                }
                mView.layout(mView.getLeft(), mView.getTop() + 2 * dy / 3,
                        mView.getRight(), mView.getBottom() + 2 * dy / 3);
                if (shouldCallBack(dy)) {
                    if (mCallback != null) {
                        if (!isCalled) {
                            isCalled = true;
                            resetPosition();
                            mCallback.callback();
                        }
                    }
                }
            }
            break;
            case MotionEvent.ACTION_UP:
            if (!mRect.isEmpty()) {
                resetPosition();
            }
            break;
        }
    }

    private boolean shouldCallBack(int dy) {
        return dy > 0 && mView.getTop() > getHeight() / 2;
    }

    private void resetPosition() {

        Animation animation = new TranslateAnimation(0, 0, mView.getTop(),
                mRect.top);
        animation.setDuration(200);
        animation.setFillAfter(true);
        mView.startAnimation(animation);
        mView.layout(mRect.left, mRect.top, mRect.right, mRect.bottom);
        mRect.setEmpty();
        isFirst = true;
        isCalled = false;
    }

    public boolean isNeedMove() {
        int offset = mView.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();
        return scrollY == 0 || scrollY == offset;
    }

    public void setCallBack(Callback callback) {
        mCallback = callback;
    }

    public interface Callback
    {
        void callback();
    }
}
