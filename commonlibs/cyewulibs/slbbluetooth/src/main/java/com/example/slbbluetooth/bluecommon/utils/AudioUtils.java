package com.example.slbbluetooth.bluecommon.utils;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.slbbluetooth.R;

import java.util.Timer;
import java.util.TimerTask;

public enum  AudioUtils {
    INSTANCE;
    private MediaPlayer mediaPlayer;
    private Timer mTimer;
    private TimerTask mTimerTask;

    public void playMedai(Context activity) {
//        mediaPlayer= MediaPlayer.create(activity, R.raw.yiqianlengyiye);
        mediaPlayer= MediaPlayer.create(activity, R.raw.zxl_beep);
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {

            }
        };
        mTimer.schedule(mTimerTask, 0, 10);
        mediaPlayer.start();
    }

    public  void stopPlay (){
        mediaPlayer.stop();
    }
}
