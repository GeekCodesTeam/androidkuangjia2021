package com.haiersmart.rommanagelib;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class ShellServiceForRomManage extends Service {

    public static final String EXTRA_NOTIFICATION_ID = "extra_notification_id_for_rom";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(intent.getIntExtra(EXTRA_NOTIFICATION_ID, 0), new Notification());

        stopForeground(true);
        stopSelf();

        return super.onStartCommand(intent, flags, startId);
    }
}
