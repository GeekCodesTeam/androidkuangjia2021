/**
 * Copyright  2015,  Smart  Haier
 * All  rights  reserved.
 * Description:
 * Author:  geyanyan
 * Date:  2016/12/19
 * FileName:  NetsService.java
 * History:
 * 1.  Date:2016/12/19 14:36
 * Author:geyanyan
 * Modification:
 */
package com.haiersmart.sfnation.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;

import android.text.TextUtils;

import com.haiersmart.commonbizlib.netease.NeteaseNet;
import com.haiersmart.sfnation.api.NeteaseMusicApi;
import com.haiersmart.sfnation.bizutils.MediaControllerUtil;
import com.haiersmart.sfnation.bizutils.MusicController;
import com.haiersmart.sfnation.constant.ConstantUtil;
import com.haiersmart.sfnation.domain.Song;
import com.haiersmart.sfnation.domain.SongPlayInfo;
import com.haiersmart.sfnation.domain.UrlInfoData;
import com.haiersmart.utilslib.app.MyLogUtil;
import com.netease.cloudmusic.utils.NeteaseMusicUtils;

import org.loader.glin.Callback;
import org.loader.glin.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;

public class NetsService extends Service implements MusicController.IMusicController {

    public static final int PUBLISH_TIME = 100;

    private List<Song> musicList = new ArrayList<>(); //音乐列表
    private MediaPlayer mPlayer = new MediaPlayer();

    private int currentPosition = -1;
    private boolean isCurrentDownloaded;
    private boolean isReady2Play;

    private MusicController.PlayModel currentPlayModel = MusicController.PlayModel.LOOP_LIST;

    private Song oldSong;

    private ExecutorService mThreadPool = Executors.newCachedThreadPool();

    private static Handler mHandler = new Handler(Looper.getMainLooper());

    /**
     * 播放歌曲，在子线程调用
     * @param url
     */
    private void playSong(String url, final Song song) {
        MyLogUtil.d("qibin", "icon_class_section_play url: " + url);

        try {
            mPlayer.reset();
            mPlayer.setDataSource(url);
            mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    isCurrentDownloaded = true;
                    startPlay();
                }
            });
            mPlayer.prepareAsync();
            MediaControllerUtil.switchPlayState(MediaControllerUtil.Netease_Player);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MusicController.attachController(this);

        if (mPlayer.isPlaying()) {
            stop();
        }

        mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                return false;
            }
        });

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (currentPosition < 0 || musicList.isEmpty()) { return;}

                oldSong = musicList.get(currentPosition);
                if (currentPlayModel != MusicController.PlayModel.LOOP_SINGLE) {
                    next();
                } else {
                    play(currentPosition);
                }
            }
        });

//        mThreadPool.execute(mProgressPublisher);
        mProgressPublisher.start();
    }

    private final Thread mProgressPublisher = new Thread() {
        @Override
        public void run() {
            for (;;) {
                if (!mPlayer.isPlaying()) {
                    LockSupport.park(this);
                }

                mHandler.post(mPublishRunnable);
                SystemClock.sleep(PUBLISH_TIME);
            }
        }
    };

    private final Runnable mPublishRunnable = new Runnable() {
        @Override
        public void run() {
            MusicController.Callbacks.onPlayProgress(mPlayer.getCurrentPosition(),
                    mPlayer.getDuration());
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        IntentFilter intentFiltervolInput = new IntentFilter();
        intentFiltervolInput.addAction(ConstantUtil.BROADCAST_ACTION_ALARM_START);
        intentFiltervolInput.addAction(ConstantUtil.BROADCAST_ACTION_ALARM_STOP);
        registerReceiver(alarmReceive, intentFiltervolInput);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void playList(List<Song> songs, int position) {
        if (songs == null) {
            musicList.clear();
            currentPosition = 0;
            stop();
        } else {
            musicList.clear();
            musicList.addAll(songs);
            if (songs.size() > position && position >= 0) {
                currentPosition = position;
            } else {
                currentPosition = 0;
            }
            play(currentPosition);
        }
    }

    @Override
    public void play(int index) {
        pause();
        if (index < musicList.size() && index >= 0) {
            currentPosition = index;
            Song song = musicList.get(currentPosition);

            MusicController.Callbacks.onPlayPrepare(currentPosition, song);

            getSongPlayInfo(song);
            MyLogUtil.d("qibin", "icon_class_section_play index");
        }
    }

    @Override
    public void play() {
        if (currentPosition < 0 || currentPosition >= musicList.size()) { return;}

        if (!isCurrentDownloaded) {
            play(currentPosition);
            return;
        }

        MusicController.Callbacks.onPlayStart(currentPosition, musicList.get(currentPosition));

        if (!mPlayer.isPlaying()) {
            startPlay();
            MediaControllerUtil.switchPlayState(MediaControllerUtil.Netease_Player);
        }
    }

    private void startPlay() {
        mPlayer.start();
        LockSupport.unpark(mProgressPublisher);
    }

    private void getSongPlayInfo(final Song song) {
        isCurrentDownloaded = false;
        isReady2Play = true;
        NeteaseNet.getInstance().get().cancel(getClass().getName());

        String params = NeteaseMusicUtils.getUrlParameters("/song/playurl", "{\"songId\":\"" + song.getId() + "\", \"bitrate\":160}");
        NeteaseNet.build(NeteaseMusicApi.class, getClass().getName()).songPlayInfo(params)
                .enqueue(new Callback<SongPlayInfo>() {
                    @Override
                    public void onResponse(Result<SongPlayInfo> result) {
                        if (!result.isOK()) {
                            isReady2Play = false;
                            MyLogUtil.d("qibin", "info failed");
                            MusicController.Callbacks.onError(1);
                            return;
                        }

                        SongPlayInfo info = result.getResult();
                        if (info == null || info.getCode() != 200) {
                            isReady2Play = false;
                            MusicController.Callbacks.onError(1);
                            return;
                        }

                        UrlInfoData url = info.getData();
                        if (url == null || TextUtils.isEmpty(url.getUrl())) {
                            isReady2Play = false;
                            MusicController.Callbacks.onError(1);
                            return;
                        }

                        playByUrl(url.getUrl(), song);
                    }
                });
    }

    private void playByUrl(final String songUrl, final Song song) {
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
//                final String url = proxy.getLocalURL(songUrl);
                MyLogUtil.d("qibin", "url->" + songUrl);
                playSong(songUrl, song);
            }
        });
    }

    @Override
    public void pause() {
        MyLogUtil.d("qibin", "pause");
        NeteaseNet.getInstance().get().cancel(getClass().getName());
        isReady2Play = false;
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
        }
        if (currentPosition < 0 || currentPosition >= musicList.size()) { return;}
        MusicController.Callbacks.onPlayPause(currentPosition, musicList.get(currentPosition));
    }

    @Override
    public void previous() {
        MyLogUtil.d("qibin", "previous");
        if (musicList.size() > 0) {
            switch (currentPlayModel) {
                case LOOP_SINGLE:
                    if (currentPosition == 0){
                        currentPosition = musicList.size();
                    }
                    currentPosition = (currentPosition-1) % musicList.size();
                    break;
                case LOOP_LIST:
                    if (currentPosition == 0){
                        currentPosition = musicList.size();
                    }
                    currentPosition = (currentPosition-1) % musicList.size();
                    break;
                case LOOP_RANDOM:
                    currentPosition = (int) Math.floor(Math.random() * musicList.size());
                    break;
            }
            play(currentPosition);
        }
    }

    @Override
    public void next() {
        MyLogUtil.d("qibin", "next");
        if (musicList.size() > 0) {
            switch (currentPlayModel) {
                case LOOP_SINGLE:
                    currentPosition = (currentPosition + 1) % musicList.size();
                    break;
                case LOOP_LIST:
                    currentPosition = (currentPosition + 1) % musicList.size();
                    break;
                case LOOP_RANDOM:
                    currentPosition = (int) Math.floor(Math.random() * musicList.size());
                    break;
            }
            play(currentPosition);
        }
    }

    @Override
    public void stop() {
        NeteaseNet.getInstance().get().cancel(getClass().getName());
        isReady2Play = false;
        if (currentPosition < 0 || currentPosition >= musicList.size()) { return;}

        if (mPlayer != null) {
            mPlayer.stop();
            MusicController.Callbacks.onPlayStop(currentPosition, musicList.get(currentPosition));

            try {
                mPlayer.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setPlayModel(MusicController.PlayModel model) {
        currentPlayModel = model;
    }

    @Override
    public void changePlayModel() {
        switch (currentPlayModel) {
            case LOOP_RANDOM:
                currentPlayModel = MusicController.PlayModel.LOOP_SINGLE;
                break;
            case LOOP_SINGLE:
                currentPlayModel = MusicController.PlayModel.LOOP_LIST;
                break;
            case LOOP_LIST:
                currentPlayModel = MusicController.PlayModel.LOOP_RANDOM;
                break;
        }
    }

    @Override
    public Song current() {
        if (currentPosition < 0 || currentPosition >= musicList.size()) { return null;}
        return musicList.get(currentPosition);
    }

    @Override
    public void seekTo(int progress) {
        if (mPlayer != null) {
            if (!mPlayer.isPlaying() && currentPosition < musicList.size()) {
                play(currentPosition);
            }

            mPlayer.seekTo(progress);
        }
    }

    @Override
    public MusicController.PlayModel currentPlayModel() {
        return currentPlayModel;
    }

    @Override
    public boolean isPlaying() {
        if (currentPosition < 0 || currentPosition >= musicList.size()) {
            return mPlayer.isPlaying();
        }

        return mPlayer.isPlaying() || isReady2Play;
    }

    @Override
    public int getProgress() {
        return currentPosition < 0 ? 0 : mPlayer.getCurrentPosition();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MusicController.detachController();
    }

    private boolean alarmPlay = false;
    private BroadcastReceiver alarmReceive = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConstantUtil.BROADCAST_ACTION_ALARM_START)) {//闹钟开启
                if (MusicController.isPlaying()){
                    MusicController.pause();
                    alarmPlay = true;
                }

            } else if (intent.getAction().equals(ConstantUtil.BROADCAST_ACTION_ALARM_STOP)) {//闹钟结束
                if (alarmPlay){
                    MusicController.play();
                    alarmPlay = false;
                }
            }
        }
    };

}
