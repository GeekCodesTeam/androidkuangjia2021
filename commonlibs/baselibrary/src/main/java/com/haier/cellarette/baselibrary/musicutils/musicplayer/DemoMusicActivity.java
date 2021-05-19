package com.haier.cellarette.baselibrary.musicutils.musicplayer;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.haier.cellarette.baselibrary.R;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DemoMusicActivity extends AppCompatActivity {

    private DemoMusicImageView Image;
    private TextView MusicStatus;
    private TextView MusicTime;
    private SeekBar MusicSeekBar;
    private TextView MusicTotal;
    private Button BtnPlayorPause;
    private Button BtnStop;
    private Button BtnQuit;
    //
    private DemoMusicPlayerService.MyBinder mBinder;
    private boolean flag;
    private boolean is_fis = true;
    private boolean is_bind_services = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicdemo1);
        Image = findViewById(R.id.Image);
        Image.setBackgroundResource(R.drawable.musicplayer_img);
        MusicStatus = findViewById(R.id.MusicStatus);
        MusicTime = findViewById(R.id.MusicTime);
        MusicSeekBar = findViewById(R.id.MusicSeekBar);
        MusicTotal = findViewById(R.id.MusicTotal);
        BtnPlayorPause = findViewById(R.id.BtnPlayorPause);
        BtnStop = findViewById(R.id.BtnStop);
        BtnQuit = findViewById(R.id.BtnQuit);

        // 音乐
        Intent intent = new Intent(DemoMusicActivity.this, DemoMusicPlayerService.class);
        intent.putExtra("flag", flag);
        bindService(intent, conn, BIND_AUTO_CREATE);

        BtnPlayorPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // new
                // 动画
                Image.playMusic();
                if (mBinder != null && mBinder.getService() != null && mBinder.getService().getmPlayer() != null && mBinder.getService().getmPlayer().isPlaying()) {
                    BtnPlayorPause.setText("开始");
                    // 暂停
                    mBinder.musicPause();
                } else if (mBinder != null && mBinder.getService() != null && mBinder.getService().getmPlayer() != null && !mBinder.getService().getmPlayer().isPlaying()) {
                    BtnPlayorPause.setText("暂停");
                    if (is_fis) {
                        is_fis = false;
                        // 音乐bufen
                        if (flag) {
                            mBinder.musicStart(DemoMusicActivity.this, "mp3/" + "111.mp3");// 崩溃了
                            set_time_change();
                        }
                    } else {
                        // 开始
                        mBinder.musicContinue();
                    }
                }
                // old
//                if (Image.getState() == Image.STATE_PLAYING) {
//                    BtnPlayorPause.setText("开始");
//                    // 暂停
//                    if (mBinder != null && mBinder.getService() != null && mBinder.getService().getmPlayer().isPlaying()) {
//                        mBinder.musicPause();
//                    }
//                }
//                if (Image.getState() == Image.STATE_STOP ||
//                        Image.getState() == Image.STATE_PAUSE) {
//                    BtnPlayorPause.setText("暂停");
//                    if (is_fis) {
//                        is_fis = false;
//                        // 音乐
//                        Intent intent = new Intent(DemoMusicActivity.this, DemoMusicPlayerService.class);
//                        intent.putExtra("flag", flag);
//                        bindService(intent, conn, BIND_AUTO_CREATE);
//                    } else {
//                        // 开始
//                        if (mBinder != null && mBinder.getService() != null && !mBinder.getService().getmPlayer().isPlaying()) {
//                            mBinder.musicContinue();
//                        }
//                    }
//                }
//                // 动画
//                Image.playMusic();
            }
        });
        BtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Image.stopMusic();
                // 暂停
                if (mBinder != null && mBinder.getService() != null && mBinder.getService().getmPlayer().isPlaying()) {
                    mBinder.musicStop();
                }
            }
        });
        BtnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play_next();
                set_time_change();
            }
        });


    }

    private ScheduledExecutorService mExecutorService;

    private void set_time_change() {
        // 刷新进度条bufen
        if (mExecutorService != null) {
            return;
        }
        mExecutorService = Executors.newScheduledThreadPool(1);
        mExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
//                if (mBinder.getService().get_time().equals(mBinder.getService().get_maxtime())) {
////                    mExecutorService.shutdown();
//
//                }
                // 数据显示bufen
                MusicTotal.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mBinder != null && mBinder.getService() != null && mBinder.getService().getmPlayer() != null) {
                            MusicTotal.setText(mBinder.getService().get_maxtime());
                            MusicSeekBar.setMax(mBinder.getService().getmPlayer().getDuration());
                        }

                    }
                });
                MusicTime.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mBinder != null && mBinder.getService() != null && mBinder.getService().getmPlayer() != null) {
                            MusicTime.setText(mBinder.getService().get_time());
                            MusicSeekBar.setProgress(mBinder.getService().getmPlayer().getCurrentPosition());
                        }
                    }
                });

            }
        }, 1000, 100, TimeUnit.MILLISECONDS);
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (DemoMusicPlayerService.MyBinder) service;
            flag = true;
            mBinder.getService().setOnPrepared(new DemoMusicPlayerService.DemoPreparedCallBack() {
                @Override
                public void isPrepared(boolean prepared) {
                    // 监听音乐播放完成bufen
                    if (is_bind_services) {
                        is_bind_services = false;
                        return;
                    }
                    if (!prepared) {
                        // next
                        play_next();
                    }
                }
            });

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void play_next() {
        Image.setBackgroundResource(R.drawable.ic_zhaoliying);
        BtnPlayorPause.setText("暂停");
        Image.playNextMusic();
        if (mBinder != null) {
            mBinder.musicNext(DemoMusicActivity.this, "mp3/" + "222.mp3");
        }
    }

    @Override
    public void finish() {
        Image.stopMusic();
        if (mExecutorService != null) {
            mExecutorService.shutdown();
        }
        if (mBinder != null && mBinder.isService()) {
            mBinder.musicDestroy();
            mBinder = null;
            unbindService(conn);
            stopService(new Intent(this, DemoMusicPlayerService.class));
//            this.finish();
        }
        super.finish();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }


}
