/*
 * *********************************************************
 *   author   colin
 *   company  fosung
 *   email    wanglin2046@126.com
 *   date     17-10-9 下午1:08
 * ********************************************************
 */

package com.fosung.xuanchuanlan.common.util;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.fosung.frameutils.util.ScreenUtil;


/**
 * 解决webView键盘遮挡问题的类
 */
public class KeyBoardListener {
    private Activity activity;
    private View mChildOfContent;
    private int                                     usableHeightPrevious;
    private FrameLayout.LayoutParams                frameLayoutParams;
    private ViewTreeObserver.OnGlobalLayoutListener observer;
    private int                                     screenHeight;

    public KeyBoardListener(Activity activity) {
        super();
        this.activity = activity;
        screenHeight = ScreenUtil.getScreenHeight(activity);
    }

    public void init() {
        FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
        mChildOfContent = content.getChildAt(0);
        observer = new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                possiblyResizeChildOfContent();
            }
        };
        mChildOfContent.getViewTreeObserver()
                       .addOnGlobalLayoutListener(observer);
        frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
    }

    private void possiblyResizeChildOfContent() {
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {
            int usableHeightSansKeyboard = mChildOfContent.getRootView()
                                                          .getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            if (heightDifference > (usableHeightSansKeyboard / 4)) {
                frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
            } else {
                frameLayoutParams.height = screenHeight;
            }
            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }


    private int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);
    }
} 