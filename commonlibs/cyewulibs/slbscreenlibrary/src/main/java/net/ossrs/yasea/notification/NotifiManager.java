package net.ossrs.yasea.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;

import net.ossrs.yasea.R;

/**
 * Created by Administrator on 2017/12/27.
 */

public class NotifiManager {

    private NotificationManager mNotificationManager;
    private Notification mNotification;
    private boolean isRecording = true;

    private NotifiManager() {
    }

    private static class Holder {
        private static NotifiManager instance = new NotifiManager();
    }

    public static NotifiManager getInstance() {
        return Holder.instance;
    }

    public void initNotification(Context context) {
        isRecording = true;
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, null);
        mNotification = builder.setContentTitle("录屏中")
                .setContentText("").setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_LIGHTS).setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher))
                .build();

        mNotificationManager.notify(0, mNotification);
        updateNotification();
    }

    private void updateNotification() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (isRecording) {
                    try {
                        mNotificationManager.notify(0, mNotification);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        }).start();
    }

    public void stop() {
        isRecording = false;
        if (mNotificationManager != null) {
            mNotificationManager.cancel(0);
        }
    }
}
