package com.example.shining.baselibrary.service;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.example.shining.libutils.utilslib.app.MyLogUtil;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SystemTimeRomManageService extends Service {
    public static final int ROM_MANAGE_NOTIFICATION_ID = 1008611112;
    private static final long DAY_MILLSECOND = 1000 * 60 * 60 * 24L;//一天
    private static final long RECOVER_SOUND_DELAY = 15 * 1000L;
    private static final long TOTAL_TIME = 3700000;//60分钟
    private static final long CURRENT_TIME = 1200000;//20分钟执行一次

    private static final String TAG = "SystemTimeRomManageService";
    private Handler mHandler = new Handler();

    private ScheduledExecutorService mExecutorService;
    private static final int LONG_PERIOD = 14;
    private static final int NORMAL_PERIOD = 7;//7
    private static final int SHORT_PERIOD = 5;//5
    private static final int MIN_PERIOD = 3;//3
    int testCount = 1;
    int testCount_2 = 1;

    private int dayThreshold;  //日期阈值
    private int exeCount;  //执行次数
    private static int dayCount = 0;  //执行的日期数量
    private boolean isNeedToSwitchWIFI = false;
    private Calendar currentCalendar;
    private Calendar startCalendar;
    private LinkedList<Integer> defaultTimeArea = new LinkedList<>();
    private boolean isfinish = false;
    //    private static final String DATE_CHANGE = "android.intent.action.DATE_CHANGED";//日期变更   本广播证实无效
//    private static final String TIME_SET = "android.intent.action.TIME_SET";//日期变更
    private static final String TIME_SET = "android.intent.action.TimeUpdate";//日期变更  智能电子
    private static final String TIME_SUCCESS = "android.intent.action.NTP_OK";//日期变更 成功
    private static final String TIME_FAIL = "android.intent.action.NTP_FAIL";//日期变更  失败
    private static final String TIME_TEST = "time.test";//日期变更  失败
    private CountDownTimer countDownTimer;

    @Override
    public void onCreate() {
        super.onCreate();
        destroyExecutor();
        init();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SystemTimeRebootUtil.recoveryVolume(SystemTimeRomManageService.this);//还原音量设置
                MyLogUtil.d(TAG, "还原音量设置");
            }
        }, RECOVER_SOUND_DELAY);
        mExecutorService = Executors.newScheduledThreadPool(1);
        startTask();
    }


    private void startTask() {
        MyLogUtil.d(TAG, "startTask");
        mExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    exeCount++;
                    MyLogUtil.d(TAG, "exeCount:" + exeCount);
                    currentCalendar = Calendar.getInstance();
                    long diff = currentCalendar.getTimeInMillis() - startCalendar.getTimeInMillis();

                    int dayNewCount = (int) (diff / DAY_MILLSECOND);
                    if ((dayNewCount - dayCount) == 1) {
                        isNeedToSwitchWIFI = true;
                    }
                    if (isNeedToSwitchWIFI) {
                        int currentMin = SystemTimeHaierMathUtil.getDateMinuteCount(new Date());
                        if (defaultTimeArea.contains(Integer.valueOf(currentMin))) {
                            MyLogUtil.d(TAG, "SwitchWifi");
                            SystemTimeRebootUtil.reStartWifi(SystemTimeRomManageService.this);
                            isNeedToSwitchWIFI = false;
                        }
                    }

                    if (exeCount * SystemTimeRomManageConfig.getInstance().getTimeMs()
                            < NORMAL_PERIOD * DAY_MILLSECOND) {// 毫秒小与七天的毫秒值
                        ToastMessage("未达到最短校验时间" + ",当前时长----:" + dayNewCount + "---分钟");
                        return;
                    }
//                    if (exeCount * SystemTimeRomManageConfig.getInstance().getTimeMs()
//                            < MIN_PERIOD * DAY_MILLSECOND) {//3*60*1000L
//                        ToastMessage("未达到最短校验时间" + ",当前时长----:" + dayNewCount + "---分钟");
//                        return;
//                    }

                    dayCount = dayNewCount;
                    MyLogUtil.i(TAG, "dayCount:" + dayCount);
                    MyLogUtil.i(TAG, "dayThreshold:" + dayThreshold);
                    if (dayCount > NORMAL_PERIOD) {//7天一个周期
                        ToastMessage("周期校验执行第-----:" + testCount_2++ + "----次");
//                    if (dayCount > SHORT_PERIOD && dayCount >= dayThreshold) {//如果时间大于一周且大于日期阈值
//                        sendBroadcast(new Intent(TIME_TEST));

                        int currentMin = SystemTimeHaierMathUtil.getDateMinuteCount(new Date());
                        MyLogUtil.i(TAG, "currentMin:" + currentMin);
                        if (defaultTimeArea.contains(Integer.valueOf(currentMin))) {//在夜间校验

                            sendBroadcast(new Intent(TIME_SET));
                            exeCount = 0;
                            isfinish = false;
                            startCalendar = Calendar.getInstance();

//                            SystemTimeRebootUtil.rebootQuietly(SystemTimeRomManageService.this);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, SystemTimeRomManageConfig.getInstance().getTimeMs(), SystemTimeRomManageConfig.getInstance().getTimeMs(), TimeUnit.MILLISECONDS);
    }

    private void ToastMessage(String s) {
        Log.e("shijianjiaoyan:", s);
//        mHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                MyLogUtil.e("shijianjiaoyan:",s);
////                ToastUtil.showToastCenterBig(s, 30);
//            }
//        });
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MyLogUtil.d(TAG, "onStartCommand");
        startForeground(ROM_MANAGE_NOTIFICATION_ID, new Notification());

        Intent it = new Intent(this, SystemTimeShellServiceForRomManage.class);
        it.putExtra(SystemTimeShellServiceForRomManage.EXTRA_NOTIFICATION_ID, ROM_MANAGE_NOTIFICATION_ID);
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
        if (null != countDownTimer) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        stopSelf(ROM_MANAGE_NOTIFICATION_ID);
        destroyExecutor();
        unregisterReceiver(roomMangeReceiver);
        super.onDestroy();
    }

    private void init() {
        registReceiver();
        //将凌晨2-4点作为默认重启时段
        for (int i = 60; i < 180; i++) {
            defaultTimeArea.add(Integer.valueOf(i));
        }
        dayThreshold = NORMAL_PERIOD;
        startCalendar = Calendar.getInstance();
        ToastMessage("开始");
    }

    private void registReceiver() {
        IntentFilter intentFiller = new IntentFilter();
//        intentFiller.addAction(DATE_CHANGE);//日期变更  不好使
        intentFiller.addAction(TIME_SET);
        intentFiller.addAction(TIME_FAIL);
        intentFiller.addAction(TIME_SUCCESS);
        intentFiller.addAction(TIME_TEST);

        registerReceiver(roomMangeReceiver, intentFiller);
        MyLogUtil.i(TAG, "注册广播接收sssss！");
    }

    private BroadcastReceiver roomMangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(TIME_SUCCESS)) {
                if (null != countDownTimer) {
                    countDownTimer.cancel();
                    countDownTimer = null;
                }
                ToastMessage("时间变更成功!");
            } else if (intent.getAction().equals(TIME_FAIL)) {
//            } else if (intent.getAction().equals(TIME_TEST)) {
                if (!isfinish) {
                    changeCountdownInterval(CURRENT_TIME);
                    changeMillisInFuture(TOTAL_TIME);
                    if (null != countDownTimer) {
                        countDownTimer.start();
                    } else {
                        countDownTimer = new CountDownTimer(TOTAL_TIME, CURRENT_TIME) {
                            @Override
                            public void onTick(long l) {
                                ToastMessage("失败重连第------:" + testCount++ + "----次校验");
                                sendBroadcast(new Intent(TIME_SET));
                            }

                            @Override
                            public void onFinish() {
                                testCount = 1;
                                if (null != countDownTimer) {
                                    countDownTimer.cancel();
                                    countDownTimer = null;
                                }
                            }
                        };
                    }

                    isfinish = !isfinish;
                }
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service. 需要向主应用提供可传参的binder
        return null;
    }

    //利用反射动态地改变CountDownTimer类的私有字段mCountdownInterval
    private void changeCountdownInterval(long time) {
        try {
            // 反射父类CountDownTimer的mCountdownInterval字段，动态改变回调频率
            Class clazz = Class.forName("android.os.CountDownTimer");
            Field field = clazz.getDeclaredField("mCountdownInterval");
            //从Toast对象中获得mTN变量
            field.setAccessible(true);
            field.set(countDownTimer, time);
        } catch (Exception e) {
            Log.e("Ye", "反射类android.os.CountDownTimer.mCountdownInterval失败：" + e);
        }
    }

    //利用反射动态地改变CountDownTimer类的私有字段mMillisInFuture
    private void changeMillisInFuture(long time) {
        try {
            // 反射父类CountDownTimer的mMillisInFuture字段，动态改变定时总时间
            Class clazz = Class.forName("android.os.CountDownTimer");
            Field field = clazz.getDeclaredField("mMillisInFuture");
            field.setAccessible(true);
            field.set(countDownTimer, time);
        } catch (Exception e) {
            Log.e("Ye", "反射类android.os.CountDownTimer.mMillisInFuture失败： " + e);
        }
    }
}


