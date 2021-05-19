package com.haier.cellarette.baselibrary.zothers;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.lang.reflect.Method;

public class NavigationBarUtil {
    public static void initActivity(View content) {
        new NavigationBarUtil(content);
    }

    private View mObserved;//被监听的视图
    private int usableHeightView;//视图变化前的可用高度
    private ViewGroup.LayoutParams layoutParams;

    private NavigationBarUtil(View content) {
        mObserved = content;
        //给View添加全局的布局监听器监听视图的变化
        mObserved.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                resetViewHeight();
            }
        });
        layoutParams = mObserved.getLayoutParams();
    }

    /**
     * 重置视图的高度，使不被底部虚拟键遮挡
     */
    private void resetViewHeight() {
        int usableHeightViewNow = CalculateAvailableHeight();
        //比较布局变化前后的View的可用高度
        if (usableHeightViewNow != usableHeightView) {
            //如果两次高度不一致
            //将当前的View的可用高度设置成View的实际高度
            layoutParams.height = usableHeightViewNow;
            mObserved.requestLayout();//请求重新布局
            usableHeightView = usableHeightViewNow;
        }
    }

    /**
     * 计算试图高度
     *
     * @return
     */
    private int CalculateAvailableHeight() {
        Rect r = new Rect();
        mObserved.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);//如果不是沉浸状态栏，需要减去顶部高度
//        return (r.bottom );//如果是沉浸状态栏
    }

    /**
     * 判断底部是否有虚拟键
     *
     * @param context
     * @return
     */
    public static boolean hasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;

    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    public static void hideBottomUIMenu(Activity activity) {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = activity.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = activity.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    /**
     * 全屏显示，隐藏虚拟按钮
     *
     * @param view
     */
    public static void fullScreenImmersive(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            view.setSystemUiVisibility(uiOptions);
        }
    }

}