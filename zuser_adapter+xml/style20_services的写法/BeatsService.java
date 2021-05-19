package com.haiersmart.sfnation.service;


import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import com.haiersmart.sfnation.application.FridgeApplication;
import com.haiersmart.sfnation.bizutils.DataProvider;
import com.smart.haier.haierservice.IHaierService;

public class BeatsService extends Service {
    private static final String TAG = "WatchdogService";
    private static final long INTER_TIME = 20; //20s一次喂数据
    private PowerManager.WakeLock mWakeLock = null;
    private long mBeats;
    HandlerThread mHandlerThread;
    Handler mHandler;

    public BeatsService() {
    }

    private void acquireWakelock() {
        if (mWakeLock == null) {
            PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "WatchdogService");
            if (mWakeLock != null) {
                mWakeLock.acquire();
            }
        }
    }

    private void releaseWakelock() {
        if (mWakeLock != null) {
            mWakeLock.release();
            mWakeLock = null;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        acquireWakelock();
        /* Init Watchdog */
        getSystemModel();
        enableWatchDog();
        initBeats();
    }

    private class BeatTask implements Runnable {
        @Override
        public void run() {
            sendBeat();
            mHandler.postDelayed(this, 1000 * INTER_TIME);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int ret;
        ret = super.onStartCommand(intent, flags, startId);
        mHandlerThread = new HandlerThread("sendBeatThread");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
        mHandler.post(new BeatTask());
        return ret;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseWakelock();
    }

    private void getSystemModel() {
        ServiceConnection connHaierService = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TAG, "ServiceConnection！");
                IHaierService iService;
                iService = IHaierService.Stub.asInterface(service);

                //OS信息
                String systemModel = "none";
                try {
                    systemModel = iService.getSystemModel();//系统型号
                } catch (Exception e) {
                    systemModel = "000";
                }
                DataProvider.setFridge_mode(systemModel);
                Log.e(TAG, "系统型号：" + systemModel);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG, "onServiceDisconnected！");
            }
        };
        Intent serviceIntent = new Intent();
        serviceIntent.setComponent(new ComponentName("com.smart.haier.haierservice", "com.smart.haier.haierservice.MyService"));
        FridgeApplication.mContext.bindService(serviceIntent, connHaierService, Context.BIND_AUTO_CREATE);
        FridgeApplication.mContext.unbindService(connHaierService);
    }

    private void enableWatchDog() {

        String mFridgeModel = DataProvider.getFridge_mode();
        if (mFridgeModel.equalsIgnoreCase("251UG1") || mFridgeModel.equalsIgnoreCase("251UG2") || mFridgeModel.equalsIgnoreCase("251UG3") || mFridgeModel.equalsIgnoreCase("401UG1") || mFridgeModel.equalsIgnoreCase("401UG2") || mFridgeModel.equalsIgnoreCase("401UG3") || mFridgeModel.equalsIgnoreCase("256UG1") || mFridgeModel.equalsIgnoreCase("256UG2") || mFridgeModel.equalsIgnoreCase("476UG1")) {
            Intent intent = new Intent("com.smarthaier.fridge.action.dameon.heartbeat.enable");
            sendBroadcast(intent);
        }
    }

    public void disableWatchDog() {
        Intent intent = new Intent("com.smarthaier.fridge.action.dameon.heartbeat.disable");
        sendBroadcast(intent);
    }

    public void sendBeat() {
        String mFridgeModel = DataProvider.getFridge_mode();
        if (mFridgeModel.equalsIgnoreCase("251UG1") || mFridgeModel.equalsIgnoreCase("251UG2") || mFridgeModel.equalsIgnoreCase("251UG3") || mFridgeModel.equalsIgnoreCase("401UG1") || mFridgeModel.equalsIgnoreCase("401UG2") || mFridgeModel.equalsIgnoreCase("401UG3") || mFridgeModel.equalsIgnoreCase("256UG1") || mFridgeModel.equalsIgnoreCase("256UG2") || mFridgeModel.equalsIgnoreCase("476UG1")) {
            Intent intentBeat = new Intent("com.smarthaier.fridge.action.dameon.heartbeat");
            intentBeat.putExtra("beats", getBeats());
            intentBeat.putExtra("package", getPackageName());
            sendBroadcast(intentBeat);
            addBeats();
        }
    }

    public void initBeats() {
        mBeats = 0;
    }

    public void addBeats() {
        mBeats++;
    }

    public long getBeats() {
        return mBeats;
    }

}
