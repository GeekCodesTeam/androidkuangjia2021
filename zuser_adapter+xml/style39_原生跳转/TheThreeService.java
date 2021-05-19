package com.haiersmart.sfnation.service;

/**
 * Created by shining on 2016/11/22 0022.
 */

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;


import com.haiersmart.sfnation.bizutils.MyWindowManager;
import com.haiersmart.sfnation.fragment.index.TheThreeFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TheThreeService extends Service {
    private static final String TAG = "PACKAGENAME";
    //用于线程中创建或移除悬浮窗。
    private Handler handler = new Handler();
    //定时器，定时进行检测当前应该创建还是移除悬浮
    private Timer timer;
    private RefreshTask refreshTask;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    /**
     * 启动service的时候，onCreate方法只有第一次会调用，onStartCommand和onStart每次都被调用。
     * onStartCommand会告诉系统如何重启服务，如判断是否异常终止后重新启动，在何种情况下异常终止
     * 这个整形可以有四个返回值：start_sticky、start_no_sticky、START_REDELIVER_INTENT、START_STICKY_COMPATIBILITY。
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /**
         * 开启定时器，每隔500ms刷新一次
         */
//        if (timer == null) {
//            timer = new Timer();
//
//            if(refreshTask==null){
//                refreshTask=new RefreshTask();
//                timer.scheduleAtFixedRate(new RefreshTask(), 0, 500);
//            }
//        }
        handler.post(new Runnable() {
            @Override
            public void run() {
//                        Log.e("####", "2. " + MyWindowManager.isWindowShowing() + ", " + isOtherActivity());
                MyWindowManager.createBigWindow(getApplicationContext());
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Service 被终止的同时也停止定时器继续运行
        if (timer!=null){
            timer.cancel();
            timer = null;
        }
    }

    class RefreshTask extends TimerTask {

        @Override
        public void run() {
            Log.e("####", "1. " + MyWindowManager.isWindowShowing() + ", " + isOtherActivity());
            //判断当前界面是第三方APP，且没有显示，则创建
            if (!MyWindowManager.isWindowShowing() && isOtherActivity()) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
//                        Log.e("####", "2. " + MyWindowManager.isWindowShowing() + ", " + isOtherActivity());
                        MyWindowManager.createBigWindow(getApplicationContext());
                    }
                });
            }
            //当前界面是本应用，有显示，则移除部分
            if (MyWindowManager.isWindowShowing() && isSelfActivity()) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
//                        Log.e("####", "3. " + MyWindowManager.isWindowShowing() + ", " + isSelfActivity());
                        MyWindowManager.removeBigWindow(getApplicationContext());
                    }
                });
            }

            //判断当前界面是桌面，且没有悬浮显示，则创建悬浮窗
//            if (isHome() && !MyWindowManager.isWindowShowing()){
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        MyWindowManager.createSmallWindow(getApplicationContext());
//                    }
//                });
//            }
//            //当前界面不是桌面，且有悬浮窗口显示，则移除悬浮窗口
//            else if (!isHome() && MyWindowManager.isWindowShowing()){
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        MyWindowManager.removeBigWindow(getApplicationContext());
//                    }
//                }) ;
//            }
            // 当前界面是桌面，且有悬浮窗显示，则更新内存数据。
//            else if (isHome() && MyWindowManager.isWindowShowing()){
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        MyWindowManager.updateUsedPercent(getApplicationContext());
//                    }
//                });
//            }
        }
    }

    /**
     * 判断当前界面是否桌面
     */
    private boolean isHome() {
        ActivityManager mactivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> rti = mactivityManager.getRunningTasks(1);
        Log.e("包名", rti.get(0).topActivity.getPackageName());
        return getHomes().contains(rti.get(0).topActivity.getPackageName());
    }

    /**
     * 判断当前界面是否为第三方APP
     *
     * @return
     */
    private boolean isSelfActivity() {
        String mPackageName = "";
        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > 20) {
            mPackageName = mActivityManager.getRunningAppProcesses().get(0).processName;
        } else {
            mPackageName = mActivityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
        }

        boolean isTheThreeApp = false;
        if (mPackageName.equals(TheThreeFragment.class.getName())) {
            isTheThreeApp = true;
        } else {
            isTheThreeApp = false;
        }
        return isTheThreeApp;
    }

    /**
     * 判断当前界面是否为第三方APP
     *
     * @return
     */
    private boolean isOtherActivity() {
        String mPackageName = "";
        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        mPackageName = mActivityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
        if (Build.VERSION.SDK_INT > 20) {
            mPackageName = mActivityManager.getRunningAppProcesses().get(0).processName;
            Log.e("#####1",mPackageName);
        } else {
            mPackageName = mActivityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
            Log.e("#####2",mPackageName);
        }
//        CurrentApplicationPackageRetriever clr = new CurrentApplicationPackageRetriever(getApplicationContext());
//        String[] mPackageNames = clr.get();
//
//        for(String str:mPackageNames){
//            Log.e("#####", "---------------------------------------");
//            Log.e("#####", str);
//            Log.e("#####", "---------------------------------------");
//
//        }
        boolean isTheThreeApp = false;
        if (mPackageName.equals(TheThreeFragment.PackgetName_YOUJIA)) {
            isTheThreeApp = true;
        } else {
            isTheThreeApp = false;
        }
        return isTheThreeApp;
    }

    /**
     * 获得属于桌面的应用的应用包名称
     *
     * @return 返回包含所有包名的字符串列表
     */
    private List<String> getHomes() {
        List<String> names = new ArrayList<String>();
        PackageManager packageManager = this.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo ri : resolveInfo) {
            names.add(ri.activityInfo.packageName);
            System.out.println("packageName" + names);
            Log.d(TAG, "tag:" + names);
        }
        return names;
    }


}