package com.example.slbappcomm.huyan;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
//import android.support.annotation.ColorInt;
//
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.example.slbappcomm.R;
import com.geek.libutils.app.BaseApp;

public class Huyanservices extends Service {

    public static final int HUYAN_MANAGE_NOTIFICATION_ID = 1001611;
    private WindowManager mWindowManager;
    private View view;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MsgBinder();
    }

    public class MsgBinder extends Binder {
        public Huyanservices getService() {
            return Huyanservices.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();

    }

    private void init() {
        mWindowManager = (WindowManager) BaseApp.get().getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                        | WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.RGBA_8888);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//8.0+
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
//        params.format = PixelFormat.RGBA_8888;
//        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        Display display = mWindowManager.getDefaultDisplay();
        Point p = new Point();
        display.getRealSize(p);
//        params.width = (int) (p.x * 1.0);
//        params.height = (int) (p.y * 1.0);

//        params.width = WindowManager.LayoutParams.MATCH_PARENT;
//        params.height = WindowManager.LayoutParams.MATCH_PARENT;

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.y = 0;
        params.x = 0;


        view = LayoutInflater.from(BaseApp.get()).inflate(R.layout.activity_huyan, null);
        view.setBackgroundColor(getColors(30));
//        view.setBackgroundResource(R.color.transparent60);//c62
//        view.setFocusableInTouchMode(true);
//        view.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                return false;
//            }
//        });
        mWindowManager.addView(view, params);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        startForeground(HUYAN_MANAGE_NOTIFICATION_ID, new Notification());

        Intent it = new Intent(this, HuyanservicesBg.class);
        it.putExtra(HuyanservicesBg.EXTRA_NOTIFICATION_ID, HUYAN_MANAGE_NOTIFICATION_ID);
        startService(it);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
//        if (view.getParent() != null) {
//            mWindowManager.removeView(view);//移除窗口
//        }
        mWindowManager.removeView(view);//移除窗口
        super.onDestroy();

    }

    /**
     * 过滤蓝光
     *
     * @param blueFilterPercent 蓝光过滤比例[10-80]
     */
    public static @ColorInt
    int getColors(int blueFilterPercent) {
        int realFilter = blueFilterPercent;
        if (realFilter < 10) {
            realFilter = 10;
        } else if (realFilter > 80) {
            realFilter = 80;
        }
        int a = (int) (realFilter / 80f * 180);
        int r = (int) (200 - (realFilter / 80f) * 190);
        int g = (int) (180 - (realFilter / 80f) * 170);
        int b = (int) (60 - realFilter / 80f * 60);
        return Color.argb(a, r, g, b);
    }


}
