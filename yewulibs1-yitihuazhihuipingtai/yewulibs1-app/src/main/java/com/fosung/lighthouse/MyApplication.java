package com.fosung.lighthouse;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.example.slbappcomm.phonebroadcastreceiver.PhoneService;
import com.example.slbappcomm.uploadimg2.GlideImageLoader2;
import com.example.slbappcomm.utils.BanbenCommonUtils;
import com.geek.libutils.app.LocalManageUtil;
import com.geek.libutils.app.MyLogUtil;
import com.geek.libutils.data.MmkvUtils;
import com.haier.cellarette.libretrofit.common.RetrofitNetNew;
import com.haier.cellarette.libwebview.hois2.HiosHelper;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.meituan.android.walle.WalleChannelReader;
import com.mob.MobSDK;
import com.mob.pushsdk.MobPush;
import com.mob.pushsdk.MobPushCallback;
import com.tencent.bugly.Bugly;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.unit.Subunits;

//import com.example.slbappjpushshare.fenxiang.JPushShareUtils;
//import cn.jiguang.share.android.api.JShareInterface;
//import cn.jiguang.share.android.api.PlatformConfig;
//import cn.jpush.android.api.JPushInterface;

/**
 * 自定义ApplicationLike类.
 * <p>
 * 注意：这个类是Application的代理类，以前所有在Application的实现必须要全部拷贝到这里<br/>
 *
 * @author wenjiewu
 * @since 2016/11/7
 */
public class MyApplication extends MultiDexApplication {

    public static final String TAG = "Tinker.SampleApplicationLike";
    public static final String DIR_PROJECT = "/geekandroid/app/";//
    public static final String DIR_CACHE = DIR_PROJECT + "cache/"; // 网页缓存路径
    public static final String IMG_CACHE = DIR_PROJECT + "image/"; // image缓存路径
    public static final String VIDEO_CACHE = DIR_PROJECT + "video/"; // video缓存路径
    public static final String MUSIC_CACHE = DIR_PROJECT + "music/"; // music缓存路径
    private int mFinalCount;

    @Override
    public void onCreate() {
        super.onCreate();
        // 腾讯bugly
        configBugly();
        // 语言切换
        LocalManageUtil.setApplicationLanguage(this);
//        GlideOptionsFactory.init(this, R.drawable.ic_def_loading);
        handleSSLHandshake();
        configHios();
        configRetrofitNet();
        Utils.init(this);// com.blankj:utilcode:1.17.3
        //初始化极光分享
//        configShare();
        //初始化极光统计
//        configTongji();
        //初始化极光推送
//        configTuisong();
        //初始化mob
//        configMob();
        // 初始化今日头条适配
        configShipei();
        // 播放听书
//        startService(new Intent(BaseApp.get(), ListenMusicPlayerService.class));
        // 电话监听
//        cofigPhone();
        //初始化Umeng统计
//        configUmengTongji();
        // 为了横屏需求的toast
//        ToastUtils.init(this);
        // ndk
        configNDK();
        // mmkv
        configmmkv();
        //在app切到后台,activity被后台回收的场景下,需要主动初始化下
//        GenseeLive.initConfiguration(getApplicationContext());
        // 业务-> 上传多张图片
        initImagePicker();
        //初始化G直播
//        ApplicationUtil.init(this);
//        监听前后台
        regActivityLife();

    }

    private String getProcessName(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo proInfo : runningApps) {
            if (proInfo.pid == android.os.Process.myPid()) {
                if (proInfo.processName != null) {
                    return proInfo.processName;
                }
            }
        }
        return null;
    }

    private Handler handler;

    private void configMob() {
        MobSDK.init(this);
        //防止多进程注册多次  可以在MainActivity或者其他页面注册MobPushReceiver
        String processName = getProcessName(this);
        MobPush.getRegistrationId(new MobPushCallback<String>() {

            @Override
            public void onCallback(String rid) {
                System.out.println("MobPush->RegistrationId:" + rid);
                SPUtils.getInstance().put("MOBID", rid);
            }
        });
//        if (getPackageName().equals(processName)) {
//            MobPush.addPushReceiver(new MobPushReceiver() {
//                @Override
//                public void onCustomMessageReceive(Context context, MobPushCustomMessage message) {
//                    //接收自定义消息(透传)
//                    System.out.println("MobPush onCustomMessageReceive:" + message.toString());
//                }
//
//                @Override
//                public void onNotifyMessageReceive(Context context, MobPushNotifyMessage message) {
//                    //接收通知消
//                    System.out.println("MobPush onNotifyMessageReceive:" + message.toString());
//                    Message msg = new Message();
//                    msg.what = 1;
//                    msg.obj = "Message Receive:" + message.toString();
//                    handler.sendMessage(msg);
//
//                }
//
//                @Override
//                public void onNotifyMessageOpenedReceive(Context context, MobPushNotifyMessage message) {
//                    //接收通知消息被点击事件
//                    System.out.println("MobPush onNotifyMessageOpenedReceive:" + message.toString());
//                    Message msg = new Message();
//                    msg.what = 1;
//                    msg.obj = "Click Message:" + message.toString();
//                    handler.sendMessage(msg);
//                }
//
//                @Override
//                public void onTagsCallback(Context context, String[] tags, int operation, int errorCode) {
//                    //接收tags的增改删查操作
//                    System.out.println("MobPush onTagsCallback:" + operation + "  " + errorCode);
//                }
//
//                @Override
//                public void onAliasCallback(Context context, String alias, int operation, int errorCode) {
//                    //接收alias的增改删查操作
//                    System.out.println("MobPush onAliasCallback:" + alias + "  " + operation + "  " + errorCode);
//                }
//            });
//
//            handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
//                @Override
//                public boolean handleMessage(@NonNull Message msg) {
//                    if (msg.what == 1) {
//                        System.out.println("MobPush Callback Data:" + msg.obj);
//                    }
//                    return false;
//                }
//            });
//        }
        // activity获取信息
//        Intent intent = getIntent();
//        Uri uri = intent.getData();
//        if (intent != null) {
//            System.out.println("MobPush linkone intent---" + intent.toUri(Intent.URI_INTENT_SCHEME));
//        }
//        StringBuilder sb = new StringBuilder();
//        if (uri != null) {
//            sb.append(" scheme:" + uri.getScheme() + "\n");
//            sb.append(" host:" + uri.getHost() + "\n");
//            sb.append(" port:" + uri.getPort() + "\n");
//            sb.append(" query:" + uri.getQuery() + "\n");
//        }
//
//        //获取link界面传输的数据
//        JSONArray jsonArray = MobPushUtils.parseSchemePluginPushIntent(intent);
//        if (jsonArray != null){
//            sb.append("\n" + "bundle toString :" + jsonArray.toString());
//        }
//        //通过scheme跳转详情页面可选择此方式
//        JSONArray var = new JSONArray();
//        var =  MobPushUtils.parseSchemePluginPushIntent(intent2);
//        //跳转首页可选择此方式
//        JSONArray var2 = new JSONArray();
//        var2 = MobPushUtils.parseMainPluginPushIntent(intent2);
    }

    /**
     * 忽略https的证书校验
     * 避免Glide加载https图片报错：
     * javax.net.ssl.SSLHandshakeException: java.security.cert.CertPathValidatorException: Trust anchor for certification path not found.
     */
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("TLS");
            // trustAllCerts信任所有的证书
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }


    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
//        imagePicker.setImageLoader(new ClassTest1GlideImageLoader());    //设置图片加载器
        imagePicker.setImageLoader(new GlideImageLoader2());    //设置图片加载器
        imagePicker.setShowCamera(true);                       //显示拍照按钮
        imagePicker.setMultiMode(true);                       //是否多选
        imagePicker.setCrop(true);                             //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                    //是否按矩形区域保存
        imagePicker.setSelectLimit(9);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(1000);                        //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(1000);                       //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                          //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                          //保存文件的高度。单位像素
    }

    private void configmmkv() {
        MmkvUtils.getInstance().get("");
        MmkvUtils.getInstance().get_demo();
    }

    private void configNDK() {
        JNIUtils jniUtils = new JNIUtils();
        MyLogUtil.e("--JNIUtils--", jniUtils.stringFromJNI());
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
        LocalManageUtil.onConfigurationChanged(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


    private void configUmengTongji() {
        /**
         * 设置walle当前渠道
         */
        String channel = WalleChannelReader.getChannel(this);
        MyLogUtil.e("--版本--", channel);
        MyLogUtil.e("版本->", BanbenCommonUtils.banben_comm);
        if (TextUtils.equals(BanbenCommonUtils.banben_comm, "测试")) {
            UMConfigure.setLogEnabled(true);
            UMConfigure.init(this, "601a644d6a2a470e8fa120e3", channel, UMConfigure.DEVICE_TYPE_PHONE, null);
        } else if (TextUtils.equals(BanbenCommonUtils.banben_comm, "预生产")) {
            UMConfigure.setLogEnabled(true);
            UMConfigure.init(this, "601a644d6a2a470e8fa120e3", channel, UMConfigure.DEVICE_TYPE_PHONE, null);
        } else if (TextUtils.equals(BanbenCommonUtils.banben_comm, "线上")) {
            UMConfigure.setLogEnabled(false);
            UMConfigure.init(this, "601a644d6a2a470e8fa120e3", channel, UMConfigure.DEVICE_TYPE_PHONE, null);
        }
        //选择AUTO页面采集模式，统计SDK基础指标无需手动埋点可自动采集。
        //建议在宿主App的Application.onCreate函数中调用此函数。
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
    }

    private void cofigPhone() {
        Intent intent = new Intent(this, PhoneService.class);
        startService(intent);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            startForegroundService(intent);
//        } else {
//            startService(intent);
//        }
    }

    private void configBugly() {
        // 设置开发设备，默认为false，上传补丁如果下发范围指定为“开发设备”，需要调用此接口来标识开发设备
//        Bugly.setIsDevelopmentDevice(getApplicationContext(), true);
        // 多渠道需求塞入
//         String channel = WalleChannelReader.getChannel(this);
//         Bugly.setAppChannel(getApplicationContext(), channel);
        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
//        Bugly.init(this, "e0b1ba785f", true);
        if (TextUtils.equals(BanbenCommonUtils.banben_comm, "测试")) {
//            CrashReport.initCrashReport(this, "068e7f32c3", true);// 测试
            Bugly.init(getApplicationContext(), "3aeeb18e5e", true);
            MyLogUtil.on(true);
            //TODO test
//            if (LeakCanary.isInAnalyzerProcess(this)) {
//                // This process is dedicated to LeakCanary for heap analysis.
//                // You should not init your app in this process.
//                return;
//            }
//            LeakCanary.install(this);
        } else if (TextUtils.equals(BanbenCommonUtils.banben_comm, "预生产")) {
//            CrashReport.initCrashReport(this, "068e7f32c3", true);// 预生产
            Bugly.init(getApplicationContext(), "3aeeb18e5e", true);
            MyLogUtil.on(true);
        } else if (TextUtils.equals(BanbenCommonUtils.banben_comm, "线上")) {
//            CrashReport.initCrashReport(this, "068e7f32c3", false);// 线上
            Bugly.init(getApplicationContext(), "3aeeb18e5e", true);
            MyLogUtil.on(true);
        }
    }

    private void configShipei() {
        AutoSizeConfig.getInstance().getUnitsManager()
                .setSupportDP(true)
                .setSupportSubunits(Subunits.MM);
        AutoSize.initCompatMultiProcess(this);
    }

    private void configTuisong() {
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);
    }

    private void configShare() {
//        JShareInterface.setDebugMode(true);
//        PlatformConfig platformConfig = new PlatformConfig()
//                .setWechat(JPushShareUtils.APP_ID, JPushShareUtils.APP_KEY)// wxa3fa50c49fcd271c 746c2cd0f414de2c256c4f2095316bd0
//                .setQQ("1106011004", "YIbPvONmBQBZUGaN")
//                .setSinaWeibo("374535501", "baccd12c166f1df96736b51ffbf600a2", "https://www.jiguang.cn");
//        JShareInterface.init(this, platformConfig);// android 10崩溃
    }

    private void configTongji() {
        // 设置开启日志,发布时请关闭日志
//        JAnalyticsInterface.setDebugMode(true);
//        JAnalyticsInterface.init(this);

    }

    private void configHios() {
//        HiosRegister.load();// 静态注册部分 已弃用
        HiosHelper.config(AppUtils.getAppPackageName() + ".ad.web.page", AppUtils.getAppPackageName() + ".web.page");
    }

    protected void configRetrofitNet() {
        String cacheDir = getExternalFilesDir(null) + DIR_CACHE;
        // https://api-cn.faceplusplus.com/
//        RetrofitNet.config();
        RetrofitNetNew.config();
    }


    private void regActivityLife() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

                }

                @Override
                public void onActivityStarted(Activity activity) {
                    mFinalCount++;
                    //如果mFinalCount ==1，说明是从后台到前台
                    if (mFinalCount == 1) {
                        //说明从后台回到了前台
                    }
                }

                @Override
                public void onActivityResumed(Activity activity) {

                }

                @Override
                public void onActivityPaused(Activity activity) {

                }

                @Override
                public void onActivityStopped(Activity activity) {
                    mFinalCount--;
                    //如果mFinalCount == 0，说明是前台到后台
                    if (mFinalCount == 0) {
                        //说明从前台回到了后台
                        //                    Toast.makeText(MyApplication.this, "合象课堂已进入后台运行", Toast.LENGTH_LONG).show();
//                        ToastUtils.showLong("一体化指挥平台已进入后台运行");
                    }
                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                }

                @Override
                public void onActivityDestroyed(Activity activity) {

                }
            });
        }
    }

}
