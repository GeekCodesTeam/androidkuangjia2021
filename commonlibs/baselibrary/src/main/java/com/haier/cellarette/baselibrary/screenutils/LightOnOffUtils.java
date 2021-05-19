package com.haier.cellarette.baselibrary.screenutils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;

public class LightOnOffUtils {

    private static volatile LightOnOffUtils instance;
    private static Context mContext;

    private LightOnOffUtils(Context context) {
        mContext = context;
    }

    public static LightOnOffUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (LightOnOffUtils.class) {
                instance = new LightOnOffUtils(context);
            }
        }
        return instance;
    }

    /**
     * 亮屏bufen
     *
     * @param activity
     */
    @SuppressLint("InvalidWakeLockTag")
    public void set_on_light(Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
//
//        if (mWakeLock == null) {
//            mWakeLock = pm.newWakeLock((PowerManager.FULL_WAKE_LOCK | PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "--mWakeLock--");
//        }
//
//        if (!mWakeLock.isHeld()) {
//            mWakeLock.acquire();
//            Log.e("--mWakeLock--", "Wakelock aquired!!");
//        }
    }

    /**
     * 息屏bufen
     *
     * @param activity
     */
    public void set_off_light(Activity activity) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        if (mWakeLock != null && mWakeLock.isHeld()) {
//            mWakeLock.release();
//        }
    }

}
