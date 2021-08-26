/**
 * Copyright 2016,Smart Haier.All rights reserved
 */
package com.github.geekcodesteam.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.LocaleList;
import android.os.Looper;
import android.text.TextUtils;

import androidx.multidex.MultiDex;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.geek.libutils.app.AppUtils;
import com.geek.libutils.app.LocalManageUtil;
import com.geek.libutils.app.MyLogUtil;
import com.geek.libutils.data.MmkvUtils;
import com.haier.cellarette.libretrofit.common.RetrofitNetNew;
import com.haier.cellarette.libwebview.hois2.HiosHelper;
import com.mob.MobSDK;
import com.mob.OperationCallback;
import com.mob.PrivacyPolicy;
import com.mob.pushsdk.MobPush;
import com.mob.pushsdk.MobPushCallback;
import com.pgyer.pgyersdk.PgyerSDKManager;
import com.tencent.bugly.Bugly;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Locale;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.unit.Subunits;
import ohos.abilityshell.HarmonyApplication;

/**
 * <p class="note">File Note</p>
 * created by geek at 2017/6/6
 */
public class YewuApplicationHarmony extends HarmonyApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        if (!AppUtils.isProcessAs(getPackageName(), this)) {
            return;
        }
        //TODO commonbufen
        configBugly(BuildConfigyewu.versionNameConfig, "4d63611bdc");
        configHios();
        configmmkv();
        configShipei();
        configRetrofitNet();
        //TODO 业务bufen
        //初始化mob
        configMob();
        // ndk
        configNDK();
        // pgyer
        initpgyer();
        // 组件快捷方式bufen
        MmkvUtils.getInstance().set_xiancheng("App.isLogined", false);
        others();

    }

    public static final String DIR_PROJECT = "/geekandroid/app/";
    public static final String DIR_CACHE = DIR_PROJECT + "cache/"; // 网页缓存路径
    public static final String IMG_CACHE = DIR_PROJECT + "image/"; // image缓存路径
    public static final String VIDEO_CACHE = DIR_PROJECT + "video/"; // video缓存路径
    public static final String MUSIC_CACHE = DIR_PROJECT + "music/"; // music缓存路径

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

    protected void configBugly(String banben_comm, String buglykey) {
        // 多渠道需求塞入
//         String channel = WalleChannelReader.getChannel(this);
//         Bugly.set(getApplicationContext(), channel);
        if (TextUtils.equals(banben_comm, "测试")) {
            MyLogUtil.on(true);
            //TODO test
//            if (LeakCanary.isInAnalyzerProcess(this)) {
//                // This process is dedicated to LeakCanary for heap analysis.
//                // You should not init your app in this process.
//                return;
//            }
//            LeakCanary.install(this);
        } else if (TextUtils.equals(banben_comm, "预生产")) {
            MyLogUtil.on(true);
        } else if (TextUtils.equals(banben_comm, "线上")) {
            MyLogUtil.on(true);
        }
        Bugly.init(getApplicationContext(), buglykey, true);
    }

    /**
     * 忽略https的证书校验
     * 避免Glide加载https图片报错：
     * javax.net.ssl.SSLHandshakeException: java.security.cert.CertPathValidatorException: Trust anchor for certification path not found.
     */
    protected void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
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

    protected void configHios() {
//        HiosRegister.load();// 静态注册部分 已弃用
        HiosHelper.config(com.blankj.utilcode.util.AppUtils.getAppPackageName() + ".ad.web.page", com.blankj.utilcode.util.AppUtils.getAppPackageName() + ".web.page");
    }

    protected void others() {
        Utils.init(this);// com.blankj:utilcode:1.17.3
        // 为了横屏需求的toast
        com.hjq.toast.ToastUtils.init(this);
        // 语言切换
        LocalManageUtil.setApplicationLanguage(this);
        handleSSLHandshake();
        regActivityLife();
    }

    protected void configmmkv() {
        MmkvUtils.getInstance().get("");
        MmkvUtils.getInstance().get_demo();
    }

    protected void configShipei() {
        AutoSizeConfig.getInstance().getUnitsManager()
                .setSupportDP(true)
                .setSupportSubunits(Subunits.MM);
        AutoSize.initCompatMultiProcess(this);
    }

    protected int mFinalCount;

    protected void regActivityLife() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            this.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
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
                        ToastUtils.showLong(com.blankj.utilcode.util.AppUtils.getAppName() + "已进入后台运行");
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

    protected void configRetrofitNet() {
        String cacheDir = getExternalFilesDir(null) + DIR_CACHE;
        // https://api-cn.faceplusplus.com/
//        RetrofitNet.config();
        RetrofitNetNew.config();
    }


    private void initpgyer() {
        new PgyerSDKManager.Init()
                .setContext(this) //设置上下问对象
                .start();
        // 上报异常bufen
//        try {
//
//        } catch (Exception e) {
//            PgyerSDKManager.reportException(e);
//        }
        // 手动更新
//        PgyerSDKManager.checkVersionUpdate((Activity) getApplicationContext(), new CheckoutCallBack() {
//            @Override
//            public void onNewVersionExist(CheckSoftModel model) {
//                //检查版本成功（有新版本）
//
//                /**
//                 *   CheckSoftModel 参数介绍
//                 *
//                 *    private int buildBuildVersion;//蒲公英生成的用于区分历史版本的build号
//                 *     private String forceUpdateVersion;//强制更新版本号（未设置强置更新默认为空）
//                 *     private String forceUpdateVersionNo;//强制更新的版本编号
//                 *     private boolean needForceUpdate;//	是否强制更新
//                 *     private boolean buildHaveNewVersion;//是否有新版本
//                 *     private String downloadURL;//应用安装地址
//                 *     private String buildVersionNo;//上传包的版本编号，默认为1 (即编译的版本号，一般来说，编译一次会
//                 *    变动一次这个版本号, 在 Android 上叫 Version Code。对于 iOS 来说，是字符串类型；对于 Android 来
//                 *    说是一个整数。例如：1001，28等。)
//                 *     private String buildVersion;//版本号, 默认为1.0 (是应用向用户宣传时候用到的标识，例如：1.1、8.2.1等。)
//                 *     private String buildShortcutUrl;//	应用短链接
//                 *     private String buildUpdateDescription;//	应用更新说明
//                 */
//
//            }
//
//            @Override
//            public void onNonentityVersionExist(String string) {
//                //无新版本
//            }
//
//            @Override
//            public void onFail(String error) {
//                //请求异常
//            }
//        });
        // 用户自定义数据(上传数据必须是JSON字符串格式 如：{"userId":"ceshi_001","userName":"ceshi"})
        PgyerSDKManager.setUserData("{\"userId\":\"ceshi_001\",\"userName\":\"ceshi\"}");
    }

    private void configNDK() {
        JNIUtils jniUtils = new JNIUtils();
        MyLogUtil.e("--JNIUtils--", jniUtils.stringFromJNI());
    }

    private Handler handler;

    private void configMob() {
        MobSDK.init(this);
        //
//        MobPush.setDeviceTokenByUser(DeviceUtils.getAndroidID());
        //防止多进程注册多次  可以在MainActivity或者其他页面注册MobPushReceiver
        MobPush.getRegistrationId(new MobPushCallback<String>() {

            @Override
            public void onCallback(String rid) {
                MyLogUtil.e("MobPush", "RegistrationId:" + rid);
                SPUtils.getInstance().put("MOBID", rid);
                //TODO MOBID TEST
//                startService(new Intent(BaseApp.get(), MOBIDservices.class));
            }
        });
        MobPush.getDeviceToken(new MobPushCallback<String>() {
            @Override
            public void onCallback(String s) {
                MyLogUtil.e("MobPush----getDeviceToken--", s);
            }
        });
        //隐私协议
        mob_privacy();
//        MobSDK.init(this);
//        //防止多进程注册多次  可以在MainActivity或者其他页面注册MobPushReceiver
//        String processName = getProcessName(this);
//        MobPush.getRegistrationId(new MobPushCallback<String>() {
//
//            @Override
//            public void onCallback(String rid) {
//                System.out.println("MobPush->RegistrationId:" + rid);
//                SPUtils.getInstance().put("MOBID", rid);
//            }
//        });
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

    public void mob_privacy() {
        // 指定固定Locale
// Locale locale = Locale.CHINA;
// 指定当前系统locale
        Locale locale = null;
        if (Build.VERSION.SDK_INT >= 24) {
            LocaleList localeList = getApplicationContext().getResources().getConfiguration().getLocales();
            if (localeList != null && !localeList.isEmpty()) {
                locale = localeList.get(0);
            }
        } else {
            locale = getApplicationContext().getResources().getConfiguration().locale;
        }

// 同步方法查询隐私,locale可以为null或不设置，默认使用当前系统语言
//        PrivacyPolicy policyUrl = MobSDK.getPrivacyPolicy(MobSDK.POLICY_TYPE_URL, locale);
//        String url = policyUrl.getContent();

// 异步方法查询隐私,locale可以为null或不设置，默认使用当前系统语言
        MobSDK.getPrivacyPolicyAsync(MobSDK.POLICY_TYPE_URL, new PrivacyPolicy.OnPolicyListener() {
            @Override
            public void onComplete(PrivacyPolicy data) {
                if (data != null) {
                    // 富文本内容
                    String text = data.getContent();
                    MyLogUtil.e("MobPush", "隐私协议地址->" + text);
                    MobSDK.submitPolicyGrantResult(!TextUtils.isEmpty(text), new OperationCallback<Void>() {
                        @Override
                        public void onComplete(Void data) {
                            MyLogUtil.e("MobPush", "隐私协议授权结果提交：成功");
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            MyLogUtil.e("MobPush", "隐私协议授权结果提交：失败");
                        }
                    });
                }
            }

            @Override
            public void onFailure(Throwable t) {
                // 请求失败
            }
        });

    }

    private void configTuisong() {
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);
//        MyLogUtil.e("jiguang->", JPushInterface.getRegistrationID(this));
//        SPUtils.getInstance().put("MOBID", JPushInterface.getRegistrationID(this));
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

}
