package com.haier.application;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;

import com.danikula.videocache.HttpProxyCacheServer;
import com.danikula.videocache.file.Md5FileNameGenerator;
import com.example.shining.baselibrary.service.SystemTimeRomManageService;
import com.example.shining.libclearmermory.service.LxClearMermoryService;
import com.example.shining.libglide37.glide.GlideOptionsFactory;
import com.example.shining.libretrofit.RetrofitNetNew;
import com.example.shining.libutils.utilslib.app.MyLogUtil;
import com.example.shining.libwebview.hioscommon.HiosRegister;
import com.example.shining.libwebview.hois2.HiosHelper;
import com.haier.banben.ConstantUtil;
import com.haier.cellarette.R;
import com.haier.cellarette.broadcast.LockScreenBroadReceiver;
import com.haier.cellarette.service.StatusService;
import com.haier.cellarette.watchdog.BeatsService;
import com.haier.changelanguage.LocalManageUtil;
import com.haier.controler.serive.ControlerService;
import com.haier.newbase.haieru.u.HaierEventHelper;
import com.haier.rfid.rfidlibrary.RFIDService;
import com.haier.statistics.HaierStatistics;
import com.haier.wifi.MyWifi;
import com.haier.wine_commen.html.CommonConfig;
import com.haier.wine_commen.html.ServiceAddr;
import com.haier.wine_commen.util.GetMac;
import com.haier.wine_commen.util.UserLoginConfig;
import com.haier.wine_commen.util.VideoUtils;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;
import com.zzhoujay.richtext.RichText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.Proxy;

import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import cn.jpush.android.api.JPushInterface;

import static com.haier.wine_commen.util.FileUtil.DIR_CACHE;

//import com.squareup.leakcanary.LeakCanary;
//import com.squareup.leakcanary.RefWatcher;

/**
 * Created by centling on 2016/8/4..
 */
public class MyApplication extends MultiDexApplication {
    private static final String TAG = "HaierWineApplication";

    public LockScreenBroadReceiver lockScreenBroadReceiver = new LockScreenBroadReceiver();
    private int activityAount = 0;
    public static Context mContext;
    public static PowerManager pm;// 屏幕开关
    public static PowerManager.WakeLock mWakeLock;
    private boolean isForeground;
//    private RefWatcher mRefWatcher;
//
//    public static RefWatcher getRefWatcher(Context context) {
//        MyApplication application = (MyApplication) context.getApplicationContext();
//        return application.mRefWatcher;
//    }

//    private void enabledStrictMode() {
//        if (SDK_INT >= GINGERBREAD) {
//            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() //
//                    .detectAll() //
//                    .penaltyLog() //
//                    .penaltyDeath() //
//                    .build());
//        }
//    }

    public static final String ST = "ST_DATA";


    private static MyApplication sInstance = null;
    private String klassName;

    public static MyApplication getInstance() {
        if (sInstance == null) {
            sInstance = new MyApplication();
        }
        return sInstance;
    }

    public void config() {
//        Context ctx = MyApplication.getInstance();
//        ApplicationInfo info = null;
//        try {
//            info = ctx.getPackageManager().getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        if (info == null) {
//            throw new UnsupportedOperationException();
//        }
//        String klassName = info.metaData.getString(ST);
//        MyLogUtil.d("ST_DATA_klassName", klassName);
        try {
            ApplicationInfo appInfo = MyApplication.getInstance().getPackageManager()
                    .getApplicationInfo(MyApplication.getInstance().getPackageName(),
                            PackageManager.GET_META_DATA);
            if (appInfo == null) {
                return;
            }
            klassName = appInfo.metaData.getString(ST);
            if (null == klassName) {
                return;
            }
            MyLogUtil.d("ST_DATA_activity", "ST=" + klassName);
            if (klassName.equals(".ceshi")) {
                ServiceAddr.setIsDebugServer(true);
                CommonConfig.SELECT_MODE = false;
                ServiceAddr.SERVICE_HOST = "http://winetestapi.xcook.cn/linkcook/";//接口服务器地址 TFT
                ServiceAddr.BASE_URL = "http://test1.jiuzhidao.com/";//商城
                ServiceAddr.PAY_URL = "http://test1.jiuzhidao.com/";//二维码支付地址
            } else if (klassName.equals(".yushengchan")) {
                ServiceAddr.setIsDebugServer(true);
                CommonConfig.SELECT_MODE = false;
//                ServiceAddr.SERVICE_HOST = "http://ysc.jiuzhidao.com:8080/linkcook/";//接口服务器地址 TFT
                ServiceAddr.SERVICE_HOST = "http://yscwineapi.xcook.cn:8080/linkcook/";//接口服务器地址 TFT
                ServiceAddr.BASE_URL = "http://ysc.jiuzhidao.com/";//商城
                ServiceAddr.PAY_URL = "http://ysc.jiuzhidao.com/";//二维码支付地址
            } else if (klassName.equals(".xianshang")) {
                ServiceAddr.setIsDebugServer(false);
                CommonConfig.SELECT_MODE = false;
                ServiceAddr.SERVICE_HOST = "http://wineapi.xcook.cn/linkcook/";//接口服务器地址 TFT
                ServiceAddr.BASE_URL = "http://jiuzhidao.com/";//商城
                ServiceAddr.PAY_URL = "http://jiuzhidao.com/";//二维码支付地址
            } else if (klassName.equals("e.ceshi")) {
                ServiceAddr.setIsDebugServer(true);
                CommonConfig.SELECT_MODE = true;
                ServiceAddr.SERVICE_HOST = "http://estest.jiuzhidao.com:6060/linkcook/";//接口服务器地址 TFT
                ServiceAddr.BASE_URL = "http://estest.jiuzhidao.com/";//商城
                ServiceAddr.PAY_URL = "http://estest.jiuzhidao.com/";//二维码
            } else if (klassName.equals("e.yushengchan")) {
                ServiceAddr.setIsDebugServer(true);
                CommonConfig.SELECT_MODE = true;
                ServiceAddr.SERVICE_HOST = "http://estest.jiuzhidao.com:6060/linkcook/";//接口服务器地址 TFT
                ServiceAddr.BASE_URL = "http://estest.jiuzhidao.com/";//商城
                ServiceAddr.PAY_URL = "http://estest.jiuzhidao.com/";//二维码
            } else if (klassName.equals("e.xianshang")) {
                ServiceAddr.setIsDebugServer(false);
                CommonConfig.SELECT_MODE = true;
                ServiceAddr.SERVICE_HOST = "http://es.jiuzhidao.com:6060/linkcook/";//接口服务器地址 TFT
                ServiceAddr.BASE_URL = "http://es.jiuzhidao.com/";//商城
                ServiceAddr.PAY_URL = "http://es.jiuzhidao.com/";//二维码支付地址
            }
            MyLogUtil.d("ST_DATA_activity", "ST_DATA_isIsDebugServer=" + ServiceAddr.isIsDebugServer());
            MyLogUtil.d("ST_DATA_activity", "ST_DATA_IS_DEBUG_SERVER=" + ServiceAddr.IS_DEBUG_SERVER);
//            String aaa = "JCT_310_1157_0000";
//            MyLogUtil.d("ST_DATA", aaa.split("_")[0]);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    //内存低的时候先取消，在注册。
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        unregisterReceiver(lockScreenBroadReceiver);
        registerReceivers();
    }

    /**
     * 注册广播
     */
    public void registerReceivers() {
        //注册锁屏开屏广播
        IntentFilter screenFilter = new IntentFilter();
        screenFilter.addAction(Intent.ACTION_SCREEN_OFF);
        screenFilter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(lockScreenBroadReceiver, screenFilter);

//        //注册初始化广播
//        IntentFilter IntentFilter = new IntentFilter();
//        IntentFilter.addAction(ACTION_RESET);
//        registerReceiver(resetBroadcastReceiver, IntentFilter);
//
//        //注册ROM升级广播
//        IntentFilter ormIntentFilter = new IntentFilter();
//        ormIntentFilter.addAction(ACTION_OTA_ROM_UPDATA);
//        registerReceiver(romUpdataBroadcastReceiver, ormIntentFilter);
//
//        //注册APK升级广播
//        IntentFilter apkIntentFilter = new IntentFilter();
//        apkIntentFilter.addAction(ACTION_OTA_APK_UPDATA);
//        registerReceiver(apkUpdataBroadcastReceiver, apkIntentFilter);
    }

    @Override
    protected void attachBaseContext(Context base) {
        //保存系统选择语言
        LocalManageUtil.saveSystemCurrentLanguage(base);
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //保存系统选择语言
        LocalManageUtil.onConfigurationChanged(getApplicationContext());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mContext = getApplicationContext();

        if (null == getResources()) {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
//        CrashHandler.getInstance().init(mContext, WifiActivity.class, new CrashHandler.AppCrashCallBack() {
//            @Override
//            public void onAppCrashed() {
//                Log.d("1", "");
//            }
//        });

        MyWifi.init(mContext);

        //由于酒柜老板业务问题 只能区分测试和线上 预生产需要手动修改
        config();
        //注册欢迎页广播  防止main ondestory被取消掉
        registerReceivers();


//        enabledStrictMode();
//        mRefWatcher = LeakCanary.install(this);
        /**  leakCanary  start */
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Logger.v("application", "onActivityCreated");
            }

            @Override
            public void onActivityStarted(Activity activity) {
                if (activityAount == 0) {
                    //app回到前台
                    isForeground = true;
                }
                activityAount++;
                Logger.e("onActivityStarted" + activityAount);
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Logger.e("onActivityResumed");
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Logger.e("onActivityPaused");
            }

            @Override
            public void onActivityStopped(Activity activity) {

                activityAount--;
                if (activityAount == 0) {
                    isForeground = false;
                }
                Logger.e("onActivityStopped" + activityAount);
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                Logger.e("onActivitySaveInstanceState");

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Logger.e("onActivitySaveInstanceState");
            }
        });

        initOpenWifiEnable();        //初始化WiFi模块
        initJPush();                 //初始化极光推送
//        initPowerWakeLock();
//        initAlwaysLight();           //设置屏幕常亮的checkbox状态
        initUserLoginConfig();       //初始化用户配置信息
        initFileDownLoader();        //初始化文件下载
        RichText.initCacheDir(this); //初始化富文本加载
        initBugly();                 //初始化Bugly异常上报
        initLogger();                //初始化Logger日志工具
        initHaierEventStatistics();  //海极网统计
        initHaierEventStatistics2();  //海极网统计2
        configRetrofitNet();
//        startService(new Intent(this, MobProtocolService.class));
        if (null != klassName && (klassName.equals(".xianshang") || klassName.equals("e.xianshang"))) {
            //看门狗心跳service
            startService(new Intent(this, BeatsService.class));
        }
        startService(new Intent(this, ControlerService.class));
        startService(new Intent(this, RFIDService.class));
        startService(new Intent(this, StatusService.class));
        //时间同步service
        startService(new Intent(this, SystemTimeRomManageService.class));

    }


    protected void configRetrofitNet() {
        GlideOptionsFactory.init(this, R.drawable.ic_default);
        String cacheDir = Environment.getExternalStorageDirectory() + DIR_CACHE;//DIR_IMAGE
        configHios();
        // https://api-cn.faceplusplus.com/
//        RetrofitNet.config();
        MyLogUtil.on(true);
        RetrofitNetNew.config();
//        startClearService(ServiceAddr.isIsDebugServer());//启动磁盘管理、ROM重启操作
//        startService(new Intent(this, FileSystemService.class));//磁盘管理
//        startService(new Intent(this, RomManageService.class));// 静默重启管理
    }

    private void configHios() {
        HiosRegister.load();
        HiosHelper.config("ad.web.page", "web.page");

        // 接收部分
        // private int mAction; // default 0
        // private String mSkuId; // maybe null
        // private String mCategoryId;
        // mAction = getIntent().getIntExtra("act", 0);
        // mSkuId = getIntent().getStringExtra("sku_id");
        // mCategoryId = getIntent().getStringExtra("category_id");
    }


    /**
     * 初始化UHome事件统计字典
     */
    private void initHaierEventStatistics() {
        if (!isMainProcess()) {
            return;
        }
        HaierEventHelper.init();
    }

    /**
     * 初始化UHome事件统计字典
     */
    private void initHaierEventStatistics2() {
        if (!isMainProcess()) {
            return;
        }
        HaierStatistics.init(this, ConstantUtil.VERSION_INFO.name());
    }

    public void startClearService(boolean isclose_activity) {
        //内存管理bufen
//        Intent intent = new Intent("hs.ac.MermoryActivity");
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//        startActivity(intent);

//        Intent intent = new Intent(this, LxClearMermoryService.class);
//        intent.setAction("LxClearMermoryService_open");
//        startService(intent);

        if (isclose_activity) {
//            Intent intent = new Intent("hs.ac.MermoryActivity");
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//            startActivity(intent);
            Intent intent = new Intent(this, LxClearMermoryService.class);
            intent.setAction("LxClearMermoryService_open");
            startService(intent);
        } else {
            Intent intent = new Intent(this, LxClearMermoryService.class);
            intent.setAction("LxClearMermoryService_open");
            startService(intent);
        }

        if (!isMainProcess()) {
            return;
        }
       /* Intent intent = new Intent(this, ClearMermoryService.class);
        intent.setAction("clear_mermory");
        startService(intent);*/

//        startService(new Intent(this, FileSystemService.class));//磁盘管理
//        startService(new Intent(this, RomManageService.class));// 静默重启管理
    }

    /**
     * 是否为主进程
     *
     * @return
     */
    private boolean isMainProcess() {
        return getApplicationInfo().packageName
                .equals(getCurProcessName(getApplicationContext()));
    }

    private String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    /**
     * 初始化用户配置信息
     */
    private void initUserLoginConfig() {
        try {
            UserLoginConfig.init(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 设置屏幕常亮的checkbox状态

    /**
     * 设置屏幕常亮的checkbox状态
     */
//    private void initAlwaysLight() {
//        int value = SP.get(this, Config.SP_ALWAYS_LIGHT, 0);
//        int time = SP.get(this, Config.SLEEP_TIME, 120000);
//        Intent mIntent = new Intent();
//        mIntent.setAction("android.intent.action.SCREEN_OFF_TIMEOUT");
//        if (value == 1) {
//            mIntent.putExtra("millisecond", Integer.MAX_VALUE);
////            openAlwaysLight();
//        } else {
//            mIntent.putExtra("millisecond", time);
////            closeAlwaysLight();
//        }
//        sendBroadcast(mIntent);
//    }
    private void initPowerWakeLock() {
        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "Haier");
        mWakeLock.setReferenceCounted(false);
    }

    /**
     * 初始化极光推送
     */
    private void initJPush() {
        if (!isMainProcess()) {
            return;
        }
        JPushInterface.init(mContext);         // 初始化 JPush
        JAnalyticsInterface.init(mContext);    //初始化 JAnalytics

        if (ServiceAddr.isIsDebugServer()) {
            //极光推送
            JPushInterface.setDebugMode(true);     // 设置开启日志,发布时请关闭日志
            //极光统计
            JAnalyticsInterface.setDebugMode(true);//设置开启日志,发布时请关闭日志
        } else {
            //极光推送
            JPushInterface.setDebugMode(false);     // 设置开启日志,发布时请关闭日志
            //极光统计
            JAnalyticsInterface.setDebugMode(false);//设置开启日志,发布时请关闭日志
        }
    }

    /**
     * 设置打开wifi模块
     */
    private void initOpenWifiEnable() {
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
    }

    /**
     * 初始化日志工具
     */
    private void initLogger() {
        if (ServiceAddr.isIsDebugServer()) {
            Logger.init().methodCount(3).hideThreadInfo().logLevel(LogLevel.FULL).methodOffset(2);
        } else {
            //上线阶段不输出日志
            Logger.init().methodCount(3).hideThreadInfo().logLevel(LogLevel.NONE).methodOffset(2);
        }
    }


    /**
     * 初始化文件下载
     */
    private void initFileDownLoader() {
        FileDownloader.setupOnApplicationOnCreate(this)
                .connectionCreator(new FileDownloadUrlConnection
                        .Creator(new FileDownloadUrlConnection.Configuration()
                        .connectTimeout(60_000) // set connection timeout.
                        .readTimeout(60_000) // set read timeout.
                        .proxy(Proxy.NO_PROXY) // set proxy
                )).commit();
        FileDownloadUtils.setDefaultSaveRootPath(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "haier/download");
    }

    private WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
    private WindowManager wm;

    @SuppressWarnings("WrongConstant")
    public WindowManager getMyWindowManager() {
        if (wm == null) {
            wm = (WindowManager) getApplicationContext().getSystemService("window");
        }
        return wm;
    }

    public WindowManager.LayoutParams getMywmParams() {
        return wmParams;
    }

    /**
     * Bugly异常上报部分
     */
    private void initBugly() {

        // 获取当前包名
        String packageName = mContext.getPackageName();
        MyLogUtil.d("--packageName--", packageName);
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(mContext);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        strategy.setDeviceID(GetMac.getLocalMacAddress());
        //设置开发测试阶段设备    线上包的时候注释掉即可
//        CrashReport.setIsDevelopmentDevice(mContext, true);
        if (ServiceAddr.isIsDebugServer()) {
            //自测Bugly上报
//            CrashReport.initCrashReport(mContext, "8450182e5e", true, strategy);
            CrashReport.initCrashReport(mContext, "86380c76a9", true, strategy);
        } else {
            //优化后版本Bugly上报
            CrashReport.initCrashReport(mContext, "61b5a3b798", true, strategy);
        }
        // 初始化Bugly
//        //历史已上线Bugly上报
//        CrashReport.initCrashReport(mContext, "9d9d861126", true, strategy);

        CrashReport.setUserId(GetMac.getLocalMacAddress());
    }

    /**
     * 开启屏幕常亮
     */
    public void openAlwaysLight() {
//        if (null == mWakeLock) {
//            pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
//            mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "Haier");
//            mWakeLock.setReferenceCounted(false);
//            mWakeLock.acquire();
//            return;
//        }
//        mWakeLock.acquire();

        // Acquire wakelock
        PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
        if (mWakeLock == null) {
            mWakeLock = pm.newWakeLock((PowerManager.FULL_WAKE_LOCK | PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), TAG);
        }

        if (!mWakeLock.isHeld()) {
            mWakeLock.acquire();
            Log.i(TAG, "Wakelock aquired!!");
        }
//        SP.put(this, Config.SP_ALWAYS_LIGHT, 1);
    }

    /**
     * 关闭屏幕常亮
     */
    public void closeAlwaysLight() {
//        if (null == mWakeLock) {
//            pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
//            mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "Haier");
//            mWakeLock.setReferenceCounted(false);
//            mWakeLock.release();
//            return;
//        }
//        mWakeLock.release();

        if (mWakeLock != null && mWakeLock.isHeld()) {
            mWakeLock.release();
        }
//        SP.put(this, Config.SP_ALWAYS_LIGHT, 0);
    }


//    public static Context getInstance() {
//        return mContext;
//    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 视频缓存部分代码
     */
    private HttpProxyCacheServer proxy;

    public static HttpProxyCacheServer getProxy(Context context) {
        MyApplication app = (MyApplication) context.getApplicationContext();
        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
    }

    private HttpProxyCacheServer newProxy() {
/*
        this.diskUsage = new TotalSizeLruDiskUsage(DEFAULT_MAX_SIZE);
        this.fileNameGenerator = new Md5FileNameGenerator();
*/
        return new HttpProxyCacheServer.Builder(this)
                .cacheDirectory(VideoUtils.getVideoCacheDir(this))
                .maxCacheSize(2048 * 1024 * 1024L)//2GB
                .fileNameGenerator(new Md5FileNameGenerator())
                .build();
    }


}
