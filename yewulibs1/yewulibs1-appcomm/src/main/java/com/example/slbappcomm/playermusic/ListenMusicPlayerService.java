//package com.example.slbappcomm.playermusic;
//
//import android.app.Service;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.ServiceConnection;
//import android.content.res.AssetFileDescriptor;
//import android.content.res.AssetManager;
//import android.media.MediaPlayer;
//import android.os.Binder;
//import android.os.Build;
//import android.os.Environment;
//import android.os.Handler;
//import android.os.IBinder;
//import android.os.Message;
//import android.os.SystemClock;
//import android.text.TextUtils;
//import android.util.Log;
//
//import com.blankj.utilcode.util.NetworkUtils;
//import com.blankj.utilcode.util.SPUtils;
//import com.example.biz3slbappcomm.bean.SLBThreeMusicUrlBean;
//import com.example.biz3slbappcomm.presenter.SLBThreeMusicUrlPresenter;
//import com.example.biz3slbappcomm.view.SLBThreeMusicUrlView;
//import com.geek.libutils.app.MyLogUtil;
//import com.geek.libutils.app.BaseApp;
//import com.haier.cellarette.baselibrary.toasts2.Toasty;
//import com.haier.cellarette.baselibrary.yanzheng.LocalBroadcastManagers;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Locale;
//
//public class ListenMusicPlayerService extends Service implements MediaPlayer.OnPreparedListener,
//        MediaPlayer.OnCompletionListener, SLBThreeMusicUrlView, MediaPlayer.OnErrorListener {
//
//    public static final String ID_SCTD = "上次听到";//
//    public static final String LB_broadcastreceiver = "LB_broadcastreceiver";// 监听第三方来电音乐变化
//    public static final String LISTENBOOK_TAG1 = "后台播放";//
//    public static final String HUIBEN_IDS_sourceType = "sourceType";
//    public static final String HUIBEN_IDS_ZONG = "bookId";
//    public static final String HUIBEN_IDS = "id";
//    public static final String LMP_ACT = "com.action.playerid";
//    private String TAG = getClass().getSimpleName();
//    private MediaPlayer mPlayer = new MediaPlayer();
//    private String mPath;
//    private ArrayList<SLB4CategoryListDetailBean1Temp> musicList;
//    private ArrayList<String> musicListID;
//    private String EX_ID = "";// 后台的id
//    private String EXTRA_ID_ZONG = "";// 后台的id_zong
//    private ListenBookObservable listenBookObservable;
//
//    public String getEX_ID() {
//        if (musicListID == null || musicListID.size() == 0 || current >= musicListID.size()) {
//            return "";
//        }
//        return musicListID.get(current);
//    }
//
//    public void setEX_ID(String EX_ID) {
//        this.EX_ID = EX_ID;
//    }
//
//    public String getEXTRA_ID_ZONG() {
//        return EXTRA_ID_ZONG;
//    }
//
//    public void setEXTRA_ID_ZONG(String EXTRA_ID_ZONG) {
//        this.EXTRA_ID_ZONG = EXTRA_ID_ZONG;
//    }
//
//    private int current;
//    private int play_mode;// 0 列表播放 1 单曲循环
//    private boolean flag;
//    private boolean is_payfor;// 是否收费
//    private boolean isPrepared;
//    private boolean is_auto;// 是否手动点击
//    private DemoPreparedCallBack mCallBack;
//    private boolean isError;
//    private int errorTimes;
//    private MyBinder myBinder = new MyBinder();
//    private boolean en_houtai;// false 正常 true 后台
//    private int seekbar_progress;// 进度条
//
//    public MediaPlayer getmPlayer() {
//        return mPlayer;
//    }
//
//    // 服务的生命周期中  创建的方法
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        listenBookObservable = new ListenBookObservable();
//        mPlayer.setOnPreparedListener(this);
//        mPlayer.setOnCompletionListener(this);
//        mPlayer.setOnErrorListener(this);
//        current = 0;
//        play_mode = getPlay_mode();
//        flag = true;
//        seekbar_progress = 0;
//        // 统计每本听书的时间bufen
//        last_pId = "";
//        last_ItemId = "";
//        last_path = "";
//        //
//        is_no_get_time = false;
//        SPUtils.getInstance().put(LISTENBOOK_TAG1, false);// 这里初始化的时候要把状态false 保证中断APP的时候可以释放
//        presenter_three_music_url = new SLBThreeMusicUrlPresenter();
//        presenter_three_music_url.onCreate(this);
//        //
//        mMessageReceiver = new MessageMusicReceiverIndex();
//        IntentFilter filter = new IntentFilter();
//        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
//        filter.addAction(LB_broadcastreceiver);
//        LocalBroadcastManagers.getInstance(getApplicationContext()).registerReceiver(mMessageReceiver, filter);
//    }
//
//    /**
//     * 监听音乐变化bufen
//     */
//    private MessageMusicReceiverIndex mMessageReceiver;
//
//    /**
//     * 监听第三方音乐变化bufen
//     */
//    public class MessageMusicReceiverIndex extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            try {
//                if (LB_broadcastreceiver.equals(intent.getAction())) {
//                    // 切换Fragment bufen
////                    set_stopmusic_click();
//                    if (intent.getIntExtra("focusChange", 0) == -2) {
//                        // 暂停
//                        myBinder.musicPause();
//                    } else if (intent.getIntExtra("focusChange", 0) == 1) {
//                        // 开始
//                        myBinder.musicContinue();
//                    }
//                }
//
//            } catch (Exception e) {
//            }
//        }
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        // 显示通知栏
////        startForeground(TINGSHU_MANAGE_NOTIFICATION_ID, ListenMusicPlayerServicesBg.she_notifichanged());
////        startForeground(ListenMusicPlayerServicesBg.TINGSHU_MANAGE_NOTIFICATION_ID, new Notification());// 8.1 27 会崩
//        startForeground(ListenMusicPlayerServicesBg.TINGSHU_MANAGE_NOTIFICATION_ID, ListenMusicPlayerServicesBg.she_notifichanged());
//        Intent it = new Intent(this, ListenMusicPlayerServicesBg.class);
//        it.putExtra(ListenMusicPlayerServicesBg.EXTRA_NOTIFICATION_ID, ListenMusicPlayerServicesBg.TINGSHU_MANAGE_NOTIFICATION_ID);
//        startService(it);
//        if (intent != null && !TextUtils.isEmpty(intent.getAction())) {
//            String action = intent.getAction();
//            MyLogUtil.d(TAG, "alarm set repeat " + Calendar.getInstance().getTime().toString() + "     " + action);
//            if (LMP_ACT.equals(action)) {
//                //业务逻辑bufen
//                // 每次出去的时候都保存id
//                String ids = intent.getStringExtra(HUIBEN_IDS_ZONG);
//                String id = intent.getStringExtra(HUIBEN_IDS);
////                SPUtils.getInstance().put(CommonUtils.LISTENBOOK_TAG21, ids);
////                SPUtils.getInstance().put(CommonUtils.LISTENBOOK_TAG22, id);
//                // 统计每本听书的时间bufen
////                last_pId = "";
////                last_ItemId = "";
////                last_path = "";
//            }
//        }
//        return START_STICKY;
//    }
//
//    //这个方法主要是在用bind的方式开启服务的时候调用
//    @Override
//    public IBinder onBind(Intent intent) {
//        Log.e(TAG, "onBind: 音乐服务绑定成功");
//        return myBinder;
//    }
//
//    @Override
//    public String getIdentifier() {
//        return SystemClock.currentThreadTimeMillis() + "";
//    }
//
//    public class MyBinder extends Binder implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
//
//        public ListenBookObservable getMyObservable() {
//            return listenBookObservable;
//        }
//
//        public ListenMusicPlayerService getService() {
//            return ListenMusicPlayerService.this;
//        }
//
//        public void musicSeekTo(int progress) {
//            try {
//                if (mPlayer != null) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        mPlayer.seekTo(Long.valueOf(progress + ""), MediaPlayer.SEEK_CLOSEST);
//                    } else {
//                        mPlayer.seekTo(progress);
//                    }
//                    if (mPlayer.isPlaying()) {
//                        return;
//                    }
//                    mPlayer.start();
//                }
//            } catch (Exception e) {
//                e.getMessage();
//            }
//        }
//
//        public void musicContinue() {
//            try {
//                if (mPlayer != null) {
//                    mPlayer.start();
//                } else {
//                    mPlayer = new MediaPlayer();
//                    mPlayer.setOnPreparedListener(this);
//                    mPlayer.setOnCompletionListener(this);
//                    set_path(last_path);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        public void musicStart(String url) {
//            if (mPlayer != null) {
//                Log.e(TAG, "musicStart: 开始播放");
//                set_path(url);
//            } else {
//                Log.e(TAG, "musicStart: 创建新的MediaPlayer ---> 开始播放");
//                mPlayer = new MediaPlayer();
//                set_path(url);
//            }
//        }
//
//        public void musicPause() {
//            try {
//                if (mPlayer != null) {
//                    Log.e(TAG, "musicPause: 音乐暂停");
//                    mPlayer.pause();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        public void musicNext(String url) {
//            if (mPlayer != null) {
//                Log.e(TAG, "musicNext: 播放下一曲");
//                try {
//                    mPlayer.stop();
////                mPlayer.pause();
////                mPlayer.seekTo(0);
//                    mPlayer.reset();
//                } catch (Exception e) {
//                    e.getMessage();
//                }
//                set_path(url);
//
//            } else {
//                mPlayer = new MediaPlayer();
//                mPlayer.setOnPreparedListener(this);
//                mPlayer.setOnCompletionListener(this);
//                set_path(url);
//            }
//        }
//
//        public void musicStop() {
//            if (mPlayer != null) {
//                Log.e(TAG, "musicStop: 音乐停止");
//                try {
//                    if (mPlayer.isPlaying()) {
//                        mPlayer.stop();
//                    }
//                } catch (Exception e) {
//                    e.getMessage();
//                }
//            }
//        }
//
//        public void musicDestroy() {
//            Log.e(TAG, "musicDestroy: MediaPlayer销毁");
//            try {
//                if (mPlayer != null) {
//
//                    if (mPlayer.isPlaying()) {
//                        mPlayer.stop();
//                        Log.e(TAG, "musicDestroy: mPlayer.stop()");
//                    }
//                }
//                if (mPlayer != null) {
//                    mPlayer.setOnCompletionListener(null);
//                    mPlayer.setOnPreparedListener(null);
//                    mPlayer.release();
//                    mPlayer = null;
//                    Log.e(TAG, "musicDestroy: MediaPlayer 设置为==== null");
//                }
//            } catch (Exception e) {
//            }
//            // 统计页面开始时间 end next
//            if (current < musicList.size()) {
//                set_updatecommon(musicList.get(current).getpId(), musicList.get(current).getItemId(), UpdataCommonservices.type2);
//            }
//        }
//
//        public boolean isService() {
//            return flag;
//        }
//
//        public boolean isPrepared() {
//            return isPrepared;
//        }
//
//        @Override
//        public void onPrepared(MediaPlayer mp) {
//            Log.d(TAG, "onPrepared: " + "new MediaPlayer 准备好了开始播放");
//            mp.start();
//            isPrepared = true;
//            if (mCallBack != null) {
//                mCallBack.isPrepared(true);
//            }
//        }
//
//        @Override
//        public void onCompletion(MediaPlayer mp) {
//            Log.e(TAG, "musicEnd: binder播放结束");
//            if (mp != null && mp.isPlaying()) {
//                mp.stop();
//                mp.release();
//                mPlayer = null;
//            }
//            isPrepared = false;
//            if (mCallBack != null) {
//                mCallBack.isPrepared(false);
//            }
//            //处理错误
////            if (doErrorThings()) {
////                return;
////            }
//            // 这里目前没发现怎么合并在一起
//            set_BackDataListener();// ListenMusicActivity.class
////            if (BaseAppManager.getInstance().top()) {
////            }
//        }
//    }
//
//    // 准备好了的监听
//    @Override
//    public void onPrepared(MediaPlayer mp) {
//        Log.d(TAG, "onPrepared: " + "准备好了开始播放");
//        mp.start();
//        isPrepared = true;
//        if (!SPUtils.getInstance().getBoolean(LISTENBOOK_TAG1, false)) {
//            if (mCallBack != null) {
//                mCallBack.isPrepared(true);
//            }
//        }
//    }
//
//    @Override
//    public void onCompletion(MediaPlayer mp) {
//        //        当前音乐正在播放 切换音乐时需要重置一下
//        Log.e(TAG, "musicEnd: 外部播放结束");
//        if (mp != null && mp.isPlaying()) {
//            mp.stop();
//            mp.release();
//            mPlayer = null;
//        }
//        isPrepared = false;
//        if (mCallBack != null) {
//            mCallBack.isPrepared(false);
//        }
//        //处理错误
////        if (doErrorThings()) {
////            MyLogUtil.e(TAG, "播放错误1");
////            return;
////        }
//        set_BackDataListener();
//    }
//
//    public boolean isIs_auto() {
//        return is_auto;
//    }
//
//    public void setIs_auto(boolean is_auto) {
//        this.is_auto = is_auto;
//    }
//
//    // 设置回调数据信息的状态bufen
//    private void set_BackDataListener() {
//        if (getPlay_mode() == 0) {
//            if (isIs_auto()) {
//                current = current;
//                setIs_auto(false);
//            } else {
//                current++;
//            }
//        } else if (getPlay_mode() == 1) {
//            current = current;
//        }
//        if (current >= musicList.size()) {
//            if (getPlay_mode() == 0) {
//                current = 0;
//                // 循环播放bufen
//                if (isIs_payfor()) {
//                    // 收费就停止
//                    play_finish_callback();
//                } else {
//                    play_next_setdata();
//                }
//                return;
//            } else if (getPlay_mode() == 1) {
//                current = current;
//            }
//            // 列表播放完成
//            play_finish_callback();
//            return;
//        }
//        // 顺序播放bufen
//        play_next_setdata();
//    }
//
//    private void play_finish_callback() {
//        // 列表播放完成
//        if (!SPUtils.getInstance().getBoolean(LISTENBOOK_TAG1, false)) {
//            if (playFinishCallBack != null) {
//                MyLogUtil.e(TAG, "播放完成S");
//                playFinishCallBack.isFinish(true, current);
//                // 统计页面开始时间 end next
//                current = musicList.size() - 1;
//                set_updatecommon(musicList.get(current).getpId(), musicList.get(current).getItemId(), UpdataCommonservices.type2);
//            }
//        } else {
//            SPUtils.getInstance().put(LISTENBOOK_TAG1, false);
//        }
//    }
//
//    private void play_next_setdata() {
//        MyLogUtil.e(TAG, "播放继续S");
////        SPUtils.getInstance().put("float_id_zong", musicList.get(current).getpId());
////        SPUtils.getInstance().put("float_id", musicList.get(current).getItemId());
////        SPUtils.getInstance().put("float_name", musicList.get(current).getTitle());
//        if (getMusicListID() != null && getMusicListID().size() > 0) {
//            listenBookObservable.setData(getMusicListID().get(current));
//        }
//        String path = musicList.get(current).getAudioUrl();
//        if (TextUtils.isEmpty(path)) {
//            presenter_three_music_url.getLBThreeMusicUrlData(musicList.get(current).getThirdItemId(), musicList.get(current).getThirdPid(), musicList.get(current).getSource());
//        } else {
//            set_play_path(path);
//        }
//    }
//
//    private void set_play_path(String path1) {
//        // 列表播放完成
//        if (!SPUtils.getInstance().getBoolean(LISTENBOOK_TAG1, false)) {
//            if (playFinishCallBack != null) {
//                playFinishCallBack.isFinish(false, current);
//            }
//        }
//        if (mPlayer != null) {
//            mPlayer.stop();
//            mPlayer.reset();
//            set_path(path1);
//        }
//////			EventBus.getDefault().post(new ChooseEvent(current));
//    }
//
//    private SLBThreeMusicUrlPresenter presenter_three_music_url;
//
//    @Override
//    public void OnLBThreeMusicUrlSuccess(SLBThreeMusicUrlBean slbThreeMusicUrlBean) {
//        set_play_path(slbThreeMusicUrlBean.getThirdUrl());
//    }
//
//    @Override
//    public void OnLBThreeMusicUrlNodata(String s) {
//        set_play_path("https://www.baidu.com/");
//    }
//
//    @Override
//    public void OnLBThreeMusicUrlFail(String s) {
//        set_play_path("https://www.baidu.com/");
//    }
//
//
//    private boolean doErrorThings() {
//        //超过十次停止
//        if (12 > errorTimes && errorTimes > 10) {
////			EventBus.getDefault().post(new MessageEvent(false));
//            if (mPlayer != null) {
//                if (mPlayer.isPlaying()) {
//                    mPlayer.stop();
//                }
//                // 释放流 释放资源
//                mPlayer.release();
//            }
//            stopSelf();
//        }
//        //回调错误 返回
//        if (isError) {
//            errorTimes++;
//            return true;
//        }
//        return false;
//    }
//
//    // 在视频或者音频播放的是出错了  回调的方法
//    @Override
//    public boolean onError(MediaPlayer mp, int what, int extra) {
//        //一般的情况就会在这里让用户从新加载（dialog Toast）
//        MyLogUtil.e(TAG, "播放错误" + "onError: what= " + what + " ,extra=" + extra);
//        Log.e(TAG, "onError: what= " + what + " ,extra=" + extra);
//        isError = true;
//        return false;
//    }
//
//    @Override
//    public boolean stopService(Intent name) {
//        Log.e(TAG, "stopService: 执行停止服务");
//        return super.stopService(name);
//    }
//
//    @Override
//    public boolean onUnbind(Intent intent) {
//        Log.e(TAG, "onUnbind: 解绑服务");
////        flag = false;
////        mPath = "";
////        try {
////            if (mPlayer != null) {
////                if (mPlayer.isPlaying()) {
////                    mPlayer.stop();
////                }
////                // 释放流 释放资源
////                mPlayer.release();
////            }
////        } catch (Exception e) {
////        }
//        //
//        // 统计页面开始时间 end now
//        if (!is_no_get_time) {
//            set_updatecommon(last_pId, last_ItemId, UpdataCommonservices.type2);
//        }
//        return super.onUnbind(intent);
//    }
//
//    private boolean is_no_get_time;//
//
//    public boolean isIs_no_get_time() {
//        return is_no_get_time;
//    }
//
//    public void setIs_no_get_time(boolean is_no_get_time) {
//        this.is_no_get_time = is_no_get_time;
//    }
//
//    @Override
//    public void unbindService(ServiceConnection conn) {
//        super.unbindService(conn);
//        flag = false;
//        Log.d(TAG, "unbindService: 执行解绑服务");
//        try {
//            if (mPlayer != null) {
//                if (mPlayer.isPlaying()) {
//                    mPlayer.stop();
//                }
//                // 释放流 释放资源
//                mPlayer.release();
//            }
//        } catch (Exception e) {
//        }
//    }
//
//    //销毁
//    @Override
//    public void onDestroy() {
//        MyLogUtil.d(TAG, "onDestroy .......................................");
//        presenter_three_music_url.onDestory();
//        // 暂停第三方音乐bufen
//        LocalBroadcastManagers.getInstance(getApplicationContext()).unregisterReceiver(mMessageReceiver);
//        super.onDestroy();
//        //在Activity 和其他有生命周期的一些控件里面发出网络请求，
//        // 务必要在 这个 Activity或者控件销毁的时候 取消网络请求
//        // 让后释放各种资源  释放资源的时候可以利用try catch 捕捉 程序所有异常
//        flag = false;
//        try {
//            if (mPlayer != null) {
//                if (mPlayer.isPlaying()) {
//                    mPlayer.pause();
//                }
//                // 释放流 释放资源
//                mPlayer.release();
//                mPlayer = null;
//            }
//        } catch (Exception e) {
//            MyLogUtil.e(TAG, e.getMessage() + "");
//        }
//    }
//
//    // 获取正在播放的时间
//    public String get_time() {
//        int time = 0;
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss", Locale.getDefault());
//        if (mPlayer != null) {
//            time = mPlayer.getCurrentPosition();
//        }
//        MyLogUtil.e("--geek1--", time + "");
//        return simpleDateFormat.format(time);
//    }
//
//    // 获取正在播放的时间2
//    public int get_time2() {
//        int time = 0;
//        if (mPlayer != null) {
//            time = mPlayer.getCurrentPosition();
//        }
//        MyLogUtil.e("--geek1--", time + "");
//        return time;
//    }
//
//    // 获取总时长
//    public String get_maxtime() {
//        int time = 0;
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss", Locale.getDefault());
//        if (mPlayer != null) {
//            time = mPlayer.getDuration();
//        }
//        MyLogUtil.e("--geek2--", time + "");
//        return simpleDateFormat.format(time);
//    }
//
//    // 是否在播放
//    public boolean is_playing() {
//        return myBinder != null && getmPlayer() != null && getmPlayer().isPlaying();
//    }
//
//    private String last_pId;
//    private String last_ItemId;
//    private String last_path;
//
//    // 设置播放路径
//    public void set_path(final String url) {
//        set_updatecomm_services(url);
//        try {
//            if (url.contains("http") || url.contains(getExternalFilesDir(null).getAbsolutePath())) {
//                mPlayer.setDataSource(url);
//            } else {
//                AssetManager assetManager = BaseApp.get().getAssets();
//                AssetFileDescriptor fileDescriptor = assetManager.openFd(url);
//                mPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());// 设置数据源
//
//            }
//            if (!NetworkUtils.isConnected()) {
//                badNet();
//            } else {
//                mPlayer.prepareAsync();
//                isPrepared = false;
//                prepareStartTime = System.currentTimeMillis();
//                prepareUsedTime = 0;
//                musicHandler.sendEmptyMessage(0x2);
//            }
//        } catch (Exception e) {
//            MyLogUtil.d(TAG, "Exception1........" + e.toString());
//            badNet();
//            e.printStackTrace();
//        }
//    }
//
//    private void badNet() {
//        MyLogUtil.d(TAG, "bad net...............");
//        //将当前mplayer销毁，可能not prepare、illeagestate
//        try {
//            if (mPlayer != null) {
//                // 释放流 释放资源
//                mPlayer.release();
//                mPlayer = null;
//            }
//        } catch (Exception e) {
//            MyLogUtil.d(TAG, "bad net......Exceptioninside........." + e.toString());
//            e.printStackTrace();
//        }
//        Toasty.normal(getApplicationContext(), "网络异常，请检查网络连接！").show();
////        if (BaseAppManager.getInstance().top().getClass().getName().equals("com.example.slbapplistenbook.actvity.ListenMusicActivity")) {
//        // 暂停
//        Intent msgIntent = new Intent();
//        msgIntent.putExtra("focusChange", -2);
//        SPUtils.getInstance().put("NetChange", true);
//        msgIntent.setAction(LB_broadcastreceiver);
//        LocalBroadcastManagers.getInstance(getApplicationContext()).sendBroadcast(msgIntent);
////        }
//    }
//
//    private long prepareStartTime;
//    private long prepareUsedTime;
//
//    private Handler musicHandler = new Handler(new Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message msg) {
//            switch (msg.what) {
//                case 0x1:
//                    // 统计页面开始时间 star next
//                    set_updatecommon(musicList.get(current).getpId(), musicList.get(current).getItemId(), UpdataCommonservices.type1);
//                    //
//                    last_pId = musicList.get(current).getpId();
//                    last_ItemId = musicList.get(current).getItemId();
//                    last_path = (String) msg.obj;
//                    break;
//                case 0x2://开始prepare，取isprepare结果，超过3秒为prepare失败。
//                    MyLogUtil.d(TAG, "preparing2..." + isPrepared);
//                    MyLogUtil.d(TAG, "prepareUsedTime..." + prepareUsedTime);
//                    if (prepareUsedTime > 1000 * 5) {
//                        MyLogUtil.d(TAG, "Exception2.....prepareUsedTime>5...");
//                        if (mPlayer != null) {
//                            badNet();
//                        }
//                        break;
//                    }
//                    if (!isPrepared) {
//                        musicHandler.sendEmptyMessageDelayed(0x3, 50);
//                    }
//                    break;
//                case 0x3:
//                    MyLogUtil.d(TAG, "preparing3..." + isPrepared);
//                    if (!isPrepared) {
//                        musicHandler.sendEmptyMessage(0x2);
//                        prepareUsedTime = System.currentTimeMillis() - prepareStartTime;
//                    }
//                    break;
//            }
//            return false;
//        }
//    });
//
//
//    public void set_updatecomm_services(final String url) {
//        SPUtils.getInstance().put("float_id_zong", musicList.get(current).getpId());
//        SPUtils.getInstance().put("float_id", musicList.get(current).getItemId());
//        SPUtils.getInstance().put("float_name", musicList.get(current).getTitle());
//        SPUtils.getInstance().put(ID_SCTD, musicList.get(current).getItemId());// 上次听到
//        if (TextUtils.isEmpty(last_path)) {
//            // 第一次进来start now
//            last_pId = musicList.get(current).getpId();
//            last_ItemId = musicList.get(current).getItemId();
//            last_path = url;
//            set_updatecommon(last_pId, last_ItemId, UpdataCommonservices.type1);
//        } else {
//            // 第二次进来
//            if (!TextUtils.equals(last_path, url)) {
//                // 统计页面开始时间 end now
//                set_updatecommon(last_pId, last_ItemId, UpdataCommonservices.type2);
//                Message message = new Message();
//                message.what = 0x1;
//                message.obj = url;
//                musicHandler.sendMessageDelayed(message, 1000);
//            }
//        }
//    }
//
//    private void set_updatecommon(String pId, String ItemId, String url) {
//        Intent it = new Intent(BaseApp.get(), UpdataCommonservices.class);
//        it.setAction(UpdataCommonservices.HUIBEN_READINGTIME_ACTION);
//        it.putExtra(UpdataCommonservices.id_zong, pId);
//        it.putExtra(UpdataCommonservices.id, ItemId);
//        it.putExtra(UpdataCommonservices.type, url);
//        it.putExtra(UpdataCommonservices.sourceType, UpdataCommonservices.sourceType1);
//        it.putExtra(UpdataCommonservices.time, System.currentTimeMillis() + "");
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
////            startForegroundService(it);
////        } else {
////            // Pre-O behavior.
////            startService(it);
////        }
//        startService(it);
//    }
//
//    public interface DemoPreparedCallBack {
//        void isPrepared(boolean prepared);
//    }
//
//    public void setOnPrepared(DemoPreparedCallBack callBack) {
//        this.mCallBack = callBack;
//    }
//
//    public int getCurrent() {
//        return current;
//    }
//
//    public void setCurrent(int current) {
//        this.current = current;
//    }
//
//    public int getPlay_mode() {
//        return SPUtils.getInstance().getInt("play_mode", 0);
//    }
//
//    public void setPlay_mode(int play_mode) {
//        SPUtils.getInstance().put("play_mode", play_mode);
//    }
//
//    public boolean isEn_houtai() {
//        return en_houtai;
//    }
//
//    public void setEn_houtai(boolean en_houtai) {
//        this.en_houtai = en_houtai;
//    }
//
//    public boolean isIs_payfor() {
//        return is_payfor;
//    }
//
//    public void setIs_payfor(boolean is_payfor) {
//        this.is_payfor = is_payfor;
//    }
//
//    public int getSeekbar_progress() {
//        return seekbar_progress;
//    }
//
//    public void setSeekbar_progress(int seekbar_progress) {
//        this.seekbar_progress = seekbar_progress;
//    }
//
//    public ArrayList<SLB4CategoryListDetailBean1Temp> getMusicList() {
//        return musicList;
//    }
//
//    public void setMusicList(ArrayList<SLB4CategoryListDetailBean1Temp> musicList) {
//        this.musicList = musicList;
//    }
//
//    public ArrayList<String> getMusicListID() {
//        return musicListID;
//    }
//
//    public void setMusicListID(ArrayList<String> musicListID) {
//        this.musicListID = musicListID;
//    }
//
//    private PlayFinishCallBack playFinishCallBack;
//
//    public interface PlayFinishCallBack {
//        void isFinish(boolean isfinish, int path);
//    }
//
//    public void setOnPlayFinishCallBack(PlayFinishCallBack playFinishCallBack) {
//        this.playFinishCallBack = playFinishCallBack;
//    }
//
//    /**
//     * 监听听书悬浮框变化bufen
//     */
//    private FloatButtonReceiverListenBooks floatButtonReceiverListenBooks;
//    public static final String listen_action = "听书floatbuttn";//
//    public static final String HUIBEN_TITLES = "name";
//    public static final String HUIBEN_XMLY = "喜马拉雅听书数据";
//
//    private void set_lb_floatbutton_init() {
//        // 听书部分
//        floatButtonReceiverListenBooks = new FloatButtonReceiverListenBooks();
//        IntentFilter filter_lb = new IntentFilter();
//        filter_lb.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
//        filter_lb.addAction(listen_action);
//        LocalBroadcastManagers.getInstance(getApplicationContext()).registerReceiver(floatButtonReceiverListenBooks, filter_lb);
//        //
//        if (TextUtils.equals("com.example.slbappindex.order.OrderPayActivity", this.getClass().getName()) ||
//                TextUtils.equals("com.example.slbapplistenbook.actvity.ListenMusicActivity", this.getClass().getName())) {
//            return;
//        }
////        DragFloatActionInject.inject(BaseApp.get());
//    }
//
//    /**
//     * 监听本地图片变化bufen
//     */
//    public class FloatButtonReceiverListenBooks extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            try {
//                if (listen_action.equals(intent.getAction())) {
//                    // 播放听书
//                    String float_id_zong = intent.getStringExtra(HUIBEN_IDS_ZONG);
//                    String float_id = intent.getStringExtra(HUIBEN_IDS);
//                    String float_name = intent.getStringExtra(HUIBEN_TITLES);
//                    ArrayList<String> float_xmly = intent.getStringArrayListExtra(HUIBEN_XMLY);
//                    // 听书悬浮框监听
////                    DragFloatActionInject.inject((Activity) context).setFloat_action("hs.act.slbapp.ListenMusicActivity");
////                    DragFloatActionInject.inject((Activity) context).setFloat_id_zong(float_id_zong);
////                    DragFloatActionInject.inject((Activity) context).setFloat_id(float_id);
////                    DragFloatActionInject.inject((Activity) context).setFloat_name(float_name);
////                    DragFloatActionInject.inject((Activity) context).setFloat_xmly(float_xmly);
//                }
//            } catch (Exception e) {
//            }
//        }
//    }
//}
//
//
