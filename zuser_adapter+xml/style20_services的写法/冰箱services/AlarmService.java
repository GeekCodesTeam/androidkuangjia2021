package com.haiersmart.sfnation.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.haiersmart.sfnation.receiver.AlarmManagerHelper;
import com.haiersmart.sfnation.ui.mine.AlarmScreen;


/**
 * @function: 闹钟service 跳转到alarmscreen
 * @description:
 * @history: 1.  date:2016/2/16 14:07
 * author:PengLiang
 * modification:
 */
public class AlarmService extends Service {

    public static String TAG = AlarmService.class.getSimpleName();
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "AlarmService  onStartCommand");
        if(intent!=null){
            Intent alarmIntent = new Intent(getBaseContext(), AlarmScreen.class);
            alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            alarmIntent.putExtras(intent);
            startActivity(alarmIntent);
            AlarmManagerHelper.setAlarms(this);
        }

        return super.onStartCommand(intent, flags, startId);

    }





}