package com.fosung.lighthouse.jiaoyuziyuan.util;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

public class EduResActivitysUtil extends Application {
    private List<Activity> mList = new LinkedList<Activity>();
    private static EduResActivitysUtil instance;

    private EduResActivitysUtil() {
    }
    public synchronized static EduResActivitysUtil getInstance() {
        if (null == instance) {
            instance = new EduResActivitysUtil();
        }
        return instance;
    }

    // add Activity
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null) {
                    activity.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //注意：次方法用于垃圾回收，如果手机内存小，或使用虚拟机测试，一定要注释掉这段代码
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }
}
