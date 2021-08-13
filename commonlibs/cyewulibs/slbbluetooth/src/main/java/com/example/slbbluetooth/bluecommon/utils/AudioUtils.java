package com.example.slbbluetooth.bluecommon.utils;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.slbbluetooth.R;

import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public enum AudioUtils {
    //
    INSTANCE;
    //
    private MediaPlayer mediaPlayer;
    //定时器任务
    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;
    //
    private TimerTask mTimerTask;

    public void playMedai(Context activity) {
//        mediaPlayer= MediaPlayer.create(activity, R.raw.yiqianlengyiye);
        mediaPlayer = MediaPlayer.create(activity, R.raw.zxl_beep);
        scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
        scheduledThreadPoolExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

            }
        }, 0, 2000, TimeUnit.SECONDS);
        mediaPlayer.start();
    }

    public void stopPlay() {
        mediaPlayer.stop();
    }
}
