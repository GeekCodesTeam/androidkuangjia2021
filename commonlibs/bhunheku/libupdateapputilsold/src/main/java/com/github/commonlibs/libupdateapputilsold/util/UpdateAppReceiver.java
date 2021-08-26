package com.github.commonlibs.libupdateapputilsold.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.blankj.utilcode.util.AppUtils;
import com.github.commonlibs.libupdateapputilsold.R;

import java.io.File;


/**
 * Created by Teprinciple on 2017/11/3.
 */

public class UpdateAppReceiver extends BroadcastReceiver {

    public void UpdateAppReceiver() {

    }

    public void setBr(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("teprinciple.updates");
        context.registerReceiver(this, intentFilter);
    }

    public void desBr(Context context) {
        context.unregisterReceiver(this);
    }

    public static final String UpdateAppReceiver_CHANNEL_ID = "UpdateAppReceiver_CHANNEL_ID";
    public static final String UpdateAppReceiver_CHANNEL_NAME = "UpdateAppReceiver_CHANNEL_NAME";

    @Override
    public void onReceive(Context context, Intent intent) {

        int notifyId = 1;
        int progress = intent.getIntExtra("progress", 0);
        String title = intent.getStringExtra("title");

        NotificationManager notificationManager = null;
        if (UpdateAppUtils.showNotification) {
//            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//            builder.setContentTitle("正在下载 " + title);
//            builder.setSmallIcon(android.R.mipmap.sym_def_app_icon);
//            builder.setProgress(100, progress, false);
//
//            Notification notification = builder.build();
//            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//            nm.notify(notifyId, notification);
            //
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(UpdateAppReceiver_CHANNEL_ID, UpdateAppReceiver_CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW);
                notificationManager.createNotificationChannel(mChannel);
                notification = new NotificationCompat.Builder(context, UpdateAppReceiver_CHANNEL_ID)
                        .setChannelId(UpdateAppReceiver_CHANNEL_ID)
                        .setContentTitle(AppUtils.getAppName())
                        .setContentText("正在更新")
                        .setSmallIcon(R.drawable.icon_update)
                        .setProgress(100, progress, false)
                        .build();
            } else {
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, UpdateAppReceiver_CHANNEL_ID)
                        .setOngoing(true)
                        .setSmallIcon(R.drawable.icon_update)
                        .setContentTitle(AppUtils.getAppName())
                        .setContentText("正在更新")
                        .setProgress(100, progress, false)
                        .setChannelId(UpdateAppReceiver_CHANNEL_ID);//无效
                notification = notificationBuilder.build();
            }
            notificationManager.notify(notifyId, notification);
        }

        if (UpdateAppUtils.showProgress) {
            UpdateAppUtils.mProgressDialog.setProgress(progress);
        }

        if (progress == 100) {
            if (notificationManager != null) {
                notificationManager.cancel(notifyId);
            }

            if (UpdateAppUtils.showProgress) {
                UpdateAppUtils.mProgressDialog.dismiss();
            }

                if (DownloadAppUtils.downloadUpdateApkFilePath != null) {
                    File apkFile = new File(DownloadAppUtils.downloadUpdateApkFilePath);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    UpdateOldFileProvider7.setIntentDataAndType(context, i, "application/vnd.android.package-archive", apkFile, true);
                    context.startActivity(i);
                }
        }
    }
}
