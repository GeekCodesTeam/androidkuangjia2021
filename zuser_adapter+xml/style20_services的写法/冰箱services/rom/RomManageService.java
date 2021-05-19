package com.haiersmart.rommanagelib;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;

import com.haiersmart.utilslib.app.MyLogUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RomManageService extends Service {
    public static final int ROM_MANAGE_NOTIFICATION_ID = 1008611;
    private static final long DAY_MILLSECOND = 1000 * 60 * 60 * 24L;
    private static final long RECOVER_SOUND_DELAY = 15 * 1000L;
    private static final String TAG = "RomManageService";
    private Handler mHandler = new Handler();

    private ScheduledExecutorService mExecutorService;
    private static final int LONG_PERIOD = 14;
    private static final int NORMAL_PERIOD = 7;
    private static final int SHORT_PERIOD = 5;
    private static final int MIN_PERIOD = 3;

    private int dayThreshold;  //日期阈值
    private int exeCount;  //执行次数
    private static int dayCount = 0;  //执行的日期数量
    private boolean isNeedToSwitchWIFI = false;
    private Calendar currentCalendar;
    private Calendar startCalendar;
    private LinkedList<Integer> defaultTimeArea = new LinkedList<>();

    private static final String DATE_CHANGE = "android.intent.action.DATE_CHANGED";//日期变更   本广播证实无效
    private static final String TIME_SET = "android.intent.action.TIME_SET";//日期变更


    @Override
    public void onCreate() {
        super.onCreate();
        destroyExecutor();
        init();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                RebootUtil.recoveryVolume(RomManageService.this);//还原音量设置
            }
        }, RECOVER_SOUND_DELAY);
        mExecutorService = Executors.newScheduledThreadPool(1);
        startTask();
    }


    private void startTask() {
        mExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    exeCount++;
                    MyLogUtil.i(TAG, "exeCount:" + exeCount);
                    /*if(exeCount*RomManageConfig.getInstance().getTimeMs()
                            < MIN_PERIOD*24*60*60*1000L){
                        return;
                    }*/
                    currentCalendar = Calendar.getInstance();
                    long diff = currentCalendar.getTimeInMillis() - startCalendar.getTimeInMillis();
                    int dayNewCount = (int) (diff / DAY_MILLSECOND);
                    if ((dayNewCount - dayCount) == 1) {
                        isNeedToSwitchWIFI = true;
                    }
                    if (isNeedToSwitchWIFI) {
                        int currentMin = HaierMathUtil.getDateMinuteCount(new Date());
                        if (defaultTimeArea.contains(Integer.valueOf(currentMin))) {
                            MyLogUtil.e(TAG, "SwitchWifi");
                            RebootUtil.reStartWifi(RomManageService.this);
                            isNeedToSwitchWIFI = false;
                        }
                    }
                    if (exeCount * RomManageConfig.getInstance().getTimeMs()
                            < MIN_PERIOD * DAY_MILLSECOND) {//3*60*1000L
                        MyLogUtil.i(TAG, "未达到最短校验时间，不予执行");
                        return;
                    }
                    dayCount = dayNewCount;
                    MyLogUtil.i(TAG, "dayCount:" + dayCount);
                    MyLogUtil.i(TAG, "dayThreshold:" + dayThreshold);
                    if (dayCount > SHORT_PERIOD && dayCount >= dayThreshold) {//如果时间大于一周且大于日期阈值
                        int currentMin = HaierMathUtil.getDateMinuteCount(new Date());
                        if (defaultTimeArea.contains(Integer.valueOf(currentMin))) {
                            MyLogUtil.e(TAG, "RebootQuietly");
                            RebootUtil.rebootQuietly(RomManageService.this);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, RomManageConfig.getInstance().getTimeMs(), RomManageConfig.getInstance().getTimeMs(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(ROM_MANAGE_NOTIFICATION_ID, new Notification());

        Intent it = new Intent(this, ShellServiceForRomManage.class);
        it.putExtra(ShellServiceForRomManage.EXTRA_NOTIFICATION_ID, ROM_MANAGE_NOTIFICATION_ID);
        startService(it);

        return START_STICKY;
    }

    private void destroyExecutor() {
        if (mExecutorService != null) {
            mExecutorService.shutdownNow();
            mExecutorService = null;
        }
    }

    @Override
    public void onDestroy() {
        destroyExecutor();
        unregisterReceiver(roomMangeReceiver);
        super.onDestroy();
    }

    private void init() {
        registReceiver();
        //将凌晨2-4点作为默认重启时段
        for (int i = 120; i < 240; i++) {
            defaultTimeArea.add(Integer.valueOf(i));
        }
        dayThreshold = NORMAL_PERIOD;

        startCalendar = Calendar.getInstance();
    }

    private void registReceiver() {
        IntentFilter intentFiller = new IntentFilter();
//        intentFiller.addAction(DATE_CHANGE);//日期变更  不好使
        intentFiller.addAction(TIME_SET);//时间变更
        registerReceiver(roomMangeReceiver, intentFiller);
        MyLogUtil.i(TAG, "注册广播接收！");
    }

    private BroadcastReceiver roomMangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            /*if(intent.getAction().equals(DATE_CHANGE)){
                dayCount++;
                MyLogUtil.i(TAG,"日期变更 dayCount:"+dayCount);
            }*/
            if (intent.getAction().equals(TIME_SET)) {
                MyLogUtil.i(TAG, "时间变更!");
                Calendar calendar = Calendar.getInstance();
                long diff = calendar.getTimeInMillis() - startCalendar.getTimeInMillis();
                int monthCount = (int) (diff / (DAY_MILLSECOND * 30L));
                if (monthCount > 3) {
                    Calendar current = Calendar.getInstance();
                    MyLogUtil.i(TAG, "startCalendar变更前" + startCalendar.getTimeInMillis());
                    int second = 0;
                    try {
                        second = (int) (exeCount * RomManageConfig.getInstance().getTimeMs() / 1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    current.add(Calendar.SECOND, -second);
                    MyLogUtil.i(TAG, "差值 second:" + second);
                    startCalendar = current;
                    MyLogUtil.i(TAG, "startCalendar变更后" + startCalendar.getTimeInMillis());
                    MyLogUtil.i(TAG, "时间同步成功!");
                }
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service. 需要向主应用提供可传参的binder
        return null;
    }
}
