package com.haier.cellarette.baselibrary.bannerviewquan;

import android.content.Context;
//import androidx.core.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Collections;


public class CustomViewPager extends ViewPager {
    private ArrayList<Integer> childCenterXAbs = new ArrayList<>();
    private SparseArray<Integer> childIndex = new SparseArray<>();
    private boolean isScroll;

    public CustomViewPager(Context context) {
        super(context);
        init();
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setClipToPadding(false);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }


    /**
     * @param childCount
     * @param n
     * @return 第n个位置的child 的绘制索引
     */
    @Override
    protected int getChildDrawingOrder(int childCount, int n) {
        if (n == 0 || childIndex.size() != childCount) {
            childCenterXAbs.clear();
            childIndex.clear();
            int viewCenterX = getViewCenterX(this);
            for (int i = 0; i < childCount; ++i) {
                int indexAbs = Math.abs(viewCenterX - getViewCenterX(getChildAt(i)));
                //两个距离相同，后来的那个做自增，从而保持abs不同
                if (childIndex.get(indexAbs) != null) {
                    ++indexAbs;
                }
                childCenterXAbs.add(indexAbs);
                childIndex.append(indexAbs, i);
            }
            Collections.sort(childCenterXAbs);//1,0,2  0,1,2
        }
        //那个item距离中心点远一些，就先draw它。（最近的就是中间放大的item,最后draw）
        return childIndex.get(childCenterXAbs.get(childCount - 1 - n));
    }

    private int getViewCenterX(View view) {
        int[] array = new int[2];
        view.getLocationOnScreen(array);
        return array[0] + view.getWidth() / 2;
    }

    /**
     * 1.dispatchTouchEvent一般情况不做处理
     * ,如果修改了默认的返回值,子孩子都无法收到事件
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);   // return true;不行
    }

    /**
     * 是否拦截
     * 拦截:会走到自己的onTouchEvent方法里面来
     * 不拦截:事件传递给子孩子
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // return false;//可行,不拦截事件,
        // return true;//不行,孩子无法处理事件
        //return super.onInterceptTouchEvent(ev);//不行,会有细微移动
        if (isScroll) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;
        }
    }

    /**
     * 是否消费事件
     * 消费:事件就结束
     * 不消费:往父控件传
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //return false;// 可行,不消费,传给父控件
        //return true;// 可行,消费,拦截事件
        //super.onTouchEvent(ev); //不行,
        //虽然onInterceptTouchEvent中拦截了,
        //但是如果viewpage里面子控件不是viewgroup,还是会调用这个方法.
        if (isScroll) {
            return super.onTouchEvent(ev);
        } else {
            return true;// 可行,消费,拦截事件
        }
    }

    public void setScroll(boolean scroll) {
        isScroll = scroll;
    }
}
