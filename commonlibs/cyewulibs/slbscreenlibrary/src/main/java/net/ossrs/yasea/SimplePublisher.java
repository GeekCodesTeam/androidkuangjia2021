package net.ossrs.yasea;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.projection.MediaProjection;
import android.os.IBinder;

import net.ossrs.yasea.service.MyBinder;
import net.ossrs.yasea.service.ScreenRecordService;
import net.ossrs.yasea.utils.ServiceUtil;

/**
 * Created by Administrator on 2017/12/12.
 */
public class SimplePublisher {

    private Activity activity;

    private ServiceConnection recordConn;

    private MyBinder recordBinder;

    private int quality = 0;

    private SimplePublisher() {
    }

    private static class Holder {
        private static SimplePublisher instance = new SimplePublisher();
    }

    public static SimplePublisher getInstance() {
        return Holder.instance;
    }

    public void start(Activity activity, String rtmpAddr, boolean isMicOpen, SimpleRtmpListener listener, MediaProjection mediaProjection) {
        // 将录屏写在服务里
        this.activity = activity;
        if (recordBinder != null) {
            recordBinder.stopRecord();
            startRecord(activity, rtmpAddr, isMicOpen, listener, mediaProjection);
        } else {
            bindRecordService(activity, rtmpAddr, isMicOpen, listener, mediaProjection);
        }
    }

    private void startRecord(Activity activity, String rtmpAddr, boolean isMicOpen, SimpleRtmpListener listener, MediaProjection mediaProjection) {
        try {
            recordBinder.startRecord(activity, rtmpAddr, isMicOpen, listener, mediaProjection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 绑定服务
     */
    private void bindRecordService(final Activity activity, final String rtmpAddr, final boolean isMicOpen, final SimpleRtmpListener listener, final MediaProjection mediaProjection) {
        if (recordConn != null) {
            return;
        }
        Intent service = new Intent(activity.getApplicationContext(), ScreenRecordService.class);
        recordConn = new ServiceConnection() {
            @Override
            public void onServiceDisconnected(ComponentName name) {
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                recordBinder = (MyBinder) service;
                startRecord(activity, rtmpAddr, isMicOpen, listener, mediaProjection);
            }
        };
        if (!ServiceUtil.isServiceRunning(activity.getApplicationContext(), ScreenRecordService.class.getName())) {
            activity.getApplicationContext().startService(service);
        }
        activity.getApplicationContext().bindService(service, recordConn, Context.BIND_AUTO_CREATE);
    }

    /**
     * 停止录屏服务
     */
    public void stop() {
        // 停止服务
        if (recordBinder != null) {
            recordBinder.isRecording = false;
            recordBinder.stopRecord();
            recordBinder = null;
            activity.getApplicationContext().unbindService(recordConn);
            recordConn = null;
            Intent service = new Intent(activity.getApplicationContext(), ScreenRecordService.class);
            activity.getApplicationContext().stopService(service);
        }
    }

    public boolean isRecording() {
        return recordBinder != null && recordBinder.isRecording;
    }

    public int getQuality() {
        return quality;
    }

    public void setHighQuality(int quality) {
        this.quality = quality;
        if (recordBinder != null) {
            recordBinder.reRecording();
        }
    }
}
