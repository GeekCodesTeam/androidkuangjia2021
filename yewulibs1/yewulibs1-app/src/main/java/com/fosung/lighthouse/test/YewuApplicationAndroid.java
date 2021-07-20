/**
 * Copyright 2016,Smart Haier.All rights reserved
 */
package com.fosung.lighthouse.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.LocaleList;
import android.os.Looper;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SPUtils;
import com.example.gsyvideoplayer.exosource.GSYExoHttpDataSourceFactory;
import com.example.libbase.AndroidApplication;
import com.example.slbappcomm.uploadimg2.GlideImageLoader2;
import com.example.slbappcomm.utils.BanbenCommonUtils;
import com.example.slbappindex.services.MOBIDservices;
import com.geek.libutils.app.AppUtils;
import com.geek.libutils.app.BaseApp;
import com.geek.libutils.app.MyLogUtil;
import com.geek.libutils.data.MmkvUtils;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.meituan.android.walle.WalleChannelReader;
import com.mob.MobSDK;
import com.mob.OperationCallback;
import com.mob.PrivacyPolicy;
import com.mob.pushsdk.MobPush;
import com.mob.pushsdk.MobPushCallback;
import com.pgyer.pgyersdk.PgyerSDKManager;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.qcloud.tim.demo.helper.ConfigHelper;
import com.tencent.qcloud.tim.demo.signature.GenerateTestUserSig;
import com.tencent.qcloud.tim.demo.thirdpush.HUAWEIHmsMessageService;
import com.tencent.qcloud.tim.demo.utils.DemoLog;
import com.tencent.qcloud.tim.demo.utils.MessageNotification;
import com.tencent.qcloud.tim.demo.utils.PrivateConstants;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.base.IMEventListener;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationManagerKit;
import com.tencent.rtmp.TXLiveBase;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;

import tv.danmaku.ijk.media.exo2.ExoMediaSourceInterceptListener;
import tv.danmaku.ijk.media.exo2.ExoSourceManager;

/**
 * <p class="note">File Note</p>
 * created by geek at 2017/6/6
 */
public class YewuApplicationAndroid extends AndroidApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        if (!AppUtils.isProcessAs(getPackageName(), this)) {
            return;
        }
        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                //TODO commonbufen
                configBugly(BanbenCommonUtils.banben_comm, "3aeeb18e5e");
                configHios();
                configmmkv();
                configShipei();
                configRetrofitNet();
                //TODO 业务bufen
                //初始化极光分享
//        configShare();
                //初始化极光统计
//        configTongji();
                //初始化极光推送
//        configTuisong();
                // 播放听书
//        startService(new Intent(BaseApp.get(), ListenMusicPlayerService.class));
                // 电话监听
//        Intent intent = new Intent(this, PhoneService.class);
//        startService(intent);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            startForegroundService(intent);
//        } else {
//            startService(intent);
//        }
                //初始化mob
                configMob();
                //初始化Umeng统计
                configUmengTongji();
                // ndk
                configNDK();
                //在app切到后台,activity被后台回收的场景下,需要主动初始化下
//        GenseeLive.initConfiguration(getApplicationContext());
                // 业务-> 上传多张图片
                initImagePicker();
                // 环信IM
                initHx();
                // TencentIM
                initTencentIM();
                // GSY
                initGSY();
                // pgyer
                initpgyer();
                // 组件快捷方式bufen
                MmkvUtils.getInstance().set_xiancheng("App.isLogined", false);
            }
        }, 100);
        others();
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

    private void initGSY() {
        ExoSourceManager.setExoMediaSourceInterceptListener(new ExoMediaSourceInterceptListener() {
            @Override
            public MediaSource getMediaSource(String dataSource, boolean preview, boolean cacheEnable, boolean isLooping, File cacheDir) {
                //如果返回 null，就使用默认的
                return null;
            }

            /**
             * 通过自定义的 HttpDataSource ，可以设置自签证书或者忽略证书
             * demo 里的 GSYExoHttpDataSourceFactory 使用的是忽略证书
             * */
            @Override
            public DataSource.Factory getHttpDataSourceFactory(String userAgent, @Nullable TransferListener listener, int connectTimeoutMillis, int readTimeoutMillis,
                                                               Map<String, String> mapHeadData, boolean allowCrossProtocolRedirects) {
                //如果返回 null，就使用默认的
                GSYExoHttpDataSourceFactory factory = new GSYExoHttpDataSourceFactory(userAgent, listener,
                        connectTimeoutMillis,
                        readTimeoutMillis, allowCrossProtocolRedirects);
                factory.setDefaultRequestProperties(mapHeadData);
                return factory;
            }
        });
    }

    private void initTencentIM() {
        // bugly上报
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
        strategy.setAppVersion(V2TIMManager.getInstance().getVersion());
        CrashReport.initCrashReport(getApplicationContext(), PrivateConstants.BUGLY_APPID, true, strategy);
        TXLiveBase.getInstance().setLicence(this, "licenceUrl", "licenseKey");
        /**
         * TUIKit的初始化函数
         *
         * @param context  应用的上下文，一般为对应应用的ApplicationContext
         * @param sdkAppID 您在腾讯云注册应用时分配的sdkAppID
         * @param configs  TUIKit的相关配置项，一般使用默认即可，需特殊配置参考API文档
         */
        TUIKit.init(this, GenerateTestUserSig.SDKAPPID, new ConfigHelper().getConfigs());
        // oppo
//        HeytapPushManager.init(this, true);
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            private int foregroundActivities = 0;
            private boolean isChangingConfiguration;
            private IMEventListener mIMEventListener = new IMEventListener() {
                @Override
                public void onNewMessage(V2TIMMessage msg) {
                    MessageNotification notification = MessageNotification.getInstance();
                    notification.notify(msg);
                }
            };

            private ConversationManagerKit.MessageUnreadWatcher mUnreadWatcher = new ConversationManagerKit.MessageUnreadWatcher() {
                @Override
                public void updateUnread(int count) {
                    // 华为离线推送角标
                    HUAWEIHmsMessageService.updateBadge(BaseApp.get(), count);
                }
            };

            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                DemoLog.i("TUIKit", "onActivityCreated bundle: " + bundle);
                if (bundle != null) { // 若bundle不为空则程序异常结束
                    // 重启整个程序
//                    Intent intent = new Intent(activity, SplashActivity.class);
                    Intent intent = new Intent(com.blankj.utilcode.util.AppUtils.getAppPackageName() + ".hs.act.slbapp.WelComeActivity");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {
                foregroundActivities++;
                if (foregroundActivities == 1 && !isChangingConfiguration) {
                    // 应用切到前台
                    DemoLog.i("TUIKit", "application enter foreground");
                    V2TIMManager.getOfflinePushManager().doForeground(new V2TIMCallback() {
                        @Override
                        public void onError(int code, String desc) {
                            DemoLog.e("TUIKit", "doForeground err = " + code + ", desc = " + desc);
                        }

                        @Override
                        public void onSuccess() {
                            DemoLog.i("TUIKit", "doForeground success");
                        }
                    });
                    TUIKit.removeIMEventListener(mIMEventListener);
                    ConversationManagerKit.getInstance().removeUnreadWatcher(mUnreadWatcher);
                    MessageNotification.getInstance().cancelTimeout();
                }
                isChangingConfiguration = false;
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                foregroundActivities--;
                if (foregroundActivities == 0) {
                    // 应用切到后台
                    DemoLog.i("", "application enter background");
                    int unReadCount = ConversationManagerKit.getInstance().getUnreadTotal();
                    V2TIMManager.getOfflinePushManager().doBackground(unReadCount, new V2TIMCallback() {
                        @Override
                        public void onError(int code, String desc) {
                            DemoLog.e("TUIKit", "doBackground err = " + code + ", desc = " + desc);
                        }

                        @Override
                        public void onSuccess() {
                            DemoLog.i("TUIKit", "doBackground success");
                        }
                    });
                    // 应用退到后台，消息转化为系统通知
                    TUIKit.addIMEventListener(mIMEventListener);
                    ConversationManagerKit.getInstance().addUnreadWatcher(mUnreadWatcher);
                }
                isChangingConfiguration = activity.isChangingConfigurations();
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    private void initHx() {
//        // 初始化PreferenceManager
//        PreferenceManager.init(this);
//        // init hx sdk
//        if (DemoHelper.getInstance().getAutoLogin()) {
//            MyLogUtil.i("DemoApplication", "application initHx");
//            DemoHelper.getInstance().init(this);
//        }
        initThrowableHandler();
        closeAndroidPDialog();
    }

    private void initThrowableHandler() {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
                MyLogUtil.e("demoApp", e.getMessage());
            }
        });
    }

    /**
     * 解决androidP 第一次打开程序出现莫名弹窗
     * 弹窗内容“detected problems with api ”
     */
    private void closeAndroidPDialog() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.P) {
            try {
                Class aClass = Class.forName("android.content.pm.PackageParser$Package");
                Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
                declaredConstructor.setAccessible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Class cls = Class.forName("android.app.ActivityThread");
                Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
                declaredMethod.setAccessible(true);
                Object activityThread = declaredMethod.invoke(null);
                Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
                mHiddenApiWarningShown.setAccessible(true);
                mHiddenApiWarningShown.setBoolean(activityThread, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    private void configNDK() {
        JNIUtils jniUtils = new JNIUtils();
        MyLogUtil.e("--JNIUtils--", jniUtils.stringFromJNI());
    }

    private void configUmengTongji() {
        /**
         * 设置walle当前渠道
         */
        String channel = WalleChannelReader.getChannel(this);
//        String channel = AppUtils.getAppVersionName();
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
                startService(new Intent(BaseApp.get(), MOBIDservices.class));
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
        if (android.os.Build.VERSION.SDK_INT >= 24) {
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

    @Override
    public void onHomePressed() {
        super.onHomePressed();
//        AddressSaver.addr = null;
    }
}
