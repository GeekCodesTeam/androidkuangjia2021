package com.example.slbappcomm.huyan;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.example.slbappcomm.R;
import com.geek.libutils.app.BaseApp;

public class MyView {

    private static WindowManager mWindowManager = null;
    private static MyView linearLayout = null;
    private static View view;
    private static Context mContext;

    //创建view
    public static void createView(Context context) {
        // 获取应用的Context
        mContext = context.getApplicationContext();
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){//6.0+
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }else {
            params.type =  WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        params.format = PixelFormat.TRANSLUCENT;
        params.flags = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;
        Display display = mWindowManager.getDefaultDisplay();
        Point p = new Point();
        display.getRealSize(p);
        params.width = p.x;
        params.height = p.y;

        view = LayoutInflater.from(mContext).inflate(R.layout.activity_huyan, null);
        view.setBackgroundResource(R.color.transparent60);//c62
        view.setFocusableInTouchMode(true);
        mWindowManager.addView(view, params);
    }

    //获得浮动的layout
    public static MyView getLayout() {
        if (linearLayout == null) {
            createView(BaseApp.get());
        }
        return linearLayout;
    }
}
