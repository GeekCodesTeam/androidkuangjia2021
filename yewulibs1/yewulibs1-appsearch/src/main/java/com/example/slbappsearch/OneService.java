package com.example.slbappsearch;

import android.content.Intent;
import android.util.Log;

import com.geek.libbase.plugins.plugins.PluginBaseService;

/**
 * @author: 王硕风
 * @date: 2021.6.10 22:09
 * @Description:
 */
public class OneService extends PluginBaseService {
    private static final String TAG = "OneService";
    private int i = 0;
    private int data;

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    Log.i(TAG, "run: " + (i++));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        data = intent.getIntExtra("form", 0);
        Log.d(TAG, "data: " + data);
        return super.onStartCommand(intent, flags, startId);
    }
}
