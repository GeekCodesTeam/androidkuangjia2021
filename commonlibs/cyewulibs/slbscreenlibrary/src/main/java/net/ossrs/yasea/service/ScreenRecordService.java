package net.ossrs.yasea.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * 描述：录屏服务
 * -
 * 创建人：wangchunxiao
 * 创建时间：2017/11/22
 */
public class ScreenRecordService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_REDELIVER_INTENT;
    }
}