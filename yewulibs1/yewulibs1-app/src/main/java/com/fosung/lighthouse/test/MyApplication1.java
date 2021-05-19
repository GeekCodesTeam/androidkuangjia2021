package com.fosung.lighthouse.test;//package com.sairobo.hexiangketang;

import android.content.Context;
import android.content.res.Configuration;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

public class MyApplication1 extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        MultiDex.install(this);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
//        Beta.unInit();
    }

}
