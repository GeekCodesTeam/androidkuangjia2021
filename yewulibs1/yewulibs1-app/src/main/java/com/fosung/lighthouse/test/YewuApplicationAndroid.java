/**
 * Copyright 2016,Smart Haier.All rights reserved
 */
package com.fosung.lighthouse.test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.LocaleList;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SPUtils;
import com.example.gsyvideoplayer.exosource.GSYExoHttpDataSourceFactory;
import com.example.slbappcomm.utils.BanbenCommonUtils;
import com.example.slbappindex.services.MOBIDservices;
import com.geek.libbase.AndroidApplication;
import com.geek.libutils.app.AppUtils;
import com.geek.libutils.app.BaseApp;
import com.geek.libutils.app.MyLogUtil;
import com.geek.libutils.data.MmkvUtils;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.meituan.android.walle.WalleChannelReader;
import com.mob.MobSDK;
import com.mob.OperationCallback;
import com.mob.PrivacyPolicy;
import com.mob.pushsdk.MobPush;
import com.mob.pushsdk.MobPushCallback;
import com.pgyer.pgyersdk.PgyerSDKManager;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.qcloud.tim.demo.helper.ConfigHelper;
import com.tencent.qcloud.tim.demo.signature.GenerateTestUserSig;
import com.tencent.qcloud.tim.demo.thirdpush.HUAWEIHmsMessageService;
import com.tencent.qcloud.tim.demo.utils.DemoLog;
import com.tencent.qcloud.tim.demo.utils.MessageNotification;
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
        //TODO commonbufen
        configBugly(BanbenCommonUtils.banben_comm, "bcc64f431b");
        configHios();
        configmmkv();
        configShipei();
        configRetrofitNet();
        //TODO ??????bufen
        //?????????????????????
//        configShare();
        //?????????????????????
//        configTongji();
        //?????????????????????
//        configTuisong();
        // ????????????
//        startService(new Intent(BaseApp.get(), ListenMusicPlayerService.class));
        // ????????????
//        Intent intent = new Intent(this, PhoneService.class);
//        startService(intent);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            startForegroundService(intent);
//        } else {
//            startService(intent);
//        }
        //?????????mob
        configMob();
        //?????????Umeng??????
        configUmengTongji();
        // ndk
        configNDK();
        //???app????????????,activity???????????????????????????,????????????????????????
//        GenseeLive.initConfiguration(getApplicationContext());
        // ??????IM
        initHx();
        // TencentIM
        initTencentIM();
        // GSY
        initGSY();
        // pgyer
        initpgyer();
        // ??????????????????bufen
        MmkvUtils.getInstance().set_xiancheng("App.isLogined", false);
        others();
    }

    private void initpgyer() {
        new PgyerSDKManager.Init()
                .setContext(this) //?????????????????????
                .start();
        // ????????????bufen
//        try {
//
//        } catch (Exception e) {
//            PgyerSDKManager.reportException(e);
//        }
        // ????????????
//        PgyerSDKManager.checkVersionUpdate((Activity) getApplicationContext(), new CheckoutCallBack() {
//            @Override
//            public void onNewVersionExist(CheckSoftModel model) {
//                //????????????????????????????????????
//
//                /**
//                 *   CheckSoftModel ????????????
//                 *
//                 *    private int buildBuildVersion;//?????????????????????????????????????????????build???
//                 *     private String forceUpdateVersion;//????????????????????????????????????????????????????????????
//                 *     private String forceUpdateVersionNo;//???????????????????????????
//                 *     private boolean needForceUpdate;//	??????????????????
//                 *     private boolean buildHaveNewVersion;//??????????????????
//                 *     private String downloadURL;//??????????????????
//                 *     private String buildVersionNo;//????????????????????????????????????1 (??????????????????????????????????????????????????????
//                 *    ???????????????????????????, ??? Android ?????? Version Code????????? iOS ???????????????????????????????????? Android ???
//                 *    ??????????????????????????????1001???28??????)
//                 *     private String buildVersion;//?????????, ?????????1.0 (?????????????????????????????????????????????????????????1.1???8.2.1??????)
//                 *     private String buildShortcutUrl;//	???????????????
//                 *     private String buildUpdateDescription;//	??????????????????
//                 */
//
//            }
//
//            @Override
//            public void onNonentityVersionExist(String string) {
//                //????????????
//            }
//
//            @Override
//            public void onFail(String error) {
//                //????????????
//            }
//        });
        // ?????????????????????(?????????????????????JSON??????????????? ??????{"userId":"ceshi_001","userName":"ceshi"})
        PgyerSDKManager.setUserData("{\"userId\":\"ceshi_001\",\"userName\":\"ceshi\"}");
    }

    private void initGSY() {
        ExoSourceManager.setExoMediaSourceInterceptListener(new ExoMediaSourceInterceptListener() {
            @Override
            public MediaSource getMediaSource(String dataSource, boolean preview, boolean cacheEnable, boolean isLooping, File cacheDir) {
                //???????????? null?????????????????????
                return null;
            }

            /**
             * ?????????????????? HttpDataSource ?????????????????????????????????????????????
             * demo ?????? GSYExoHttpDataSourceFactory ????????????????????????
             * */
            @Override
            public DataSource.Factory getHttpDataSourceFactory(String userAgent, @Nullable TransferListener listener, int connectTimeoutMillis, int readTimeoutMillis,
                                                               Map<String, String> mapHeadData, boolean allowCrossProtocolRedirects) {
                //???????????? null?????????????????????
                GSYExoHttpDataSourceFactory factory = new GSYExoHttpDataSourceFactory(userAgent, listener,
                        connectTimeoutMillis,
                        readTimeoutMillis, allowCrossProtocolRedirects);
                factory.setDefaultRequestProperties(mapHeadData);
                return factory;
            }
        });
    }

    private void initTencentIM() {
        TXLiveBase.getInstance().setLicence(this, "licenceUrl", "licenseKey");
        /**
         * TUIKit??????????????????
         *
         * @param context  ?????????????????????????????????????????????ApplicationContext
         * @param sdkAppID ???????????????????????????????????????sdkAppID
         * @param configs  TUIKit?????????????????????????????????????????????????????????????????????API??????
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
                    // ????????????????????????
                    HUAWEIHmsMessageService.updateBadge(BaseApp.get(), count);
                }
            };

            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                DemoLog.i("TUIKit", "onActivityCreated bundle: " + bundle);
                if (bundle != null) { // ???bundle??????????????????????????????
                    // ??????????????????
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
                    // ??????????????????
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
                    // ??????????????????
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
                    // ????????????????????????????????????????????????
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
//        // ?????????PreferenceManager
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
     * ??????androidP ???????????????????????????????????????
     * ???????????????detected problems with api ???
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
                @SuppressLint("SoonBlockedPrivateApi") Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
                mHiddenApiWarningShown.setAccessible(true);
                mHiddenApiWarningShown.setBoolean(activityThread, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void configNDK() {
        JNIUtils jniUtils = new JNIUtils();
        MyLogUtil.e("--JNIUtils--", jniUtils.stringFromJNI());
    }

    private void configUmengTongji() {
        /**
         * ??????walle????????????
         */
        String channel = WalleChannelReader.getChannel(this);
//        String channel = AppUtils.getAppVersionName();
        MyLogUtil.e("--??????--", channel);
        MyLogUtil.e("??????->", BanbenCommonUtils.banben_comm);
        if (TextUtils.equals(BanbenCommonUtils.banben_comm, "??????")) {
            UMConfigure.setLogEnabled(true);
            UMConfigure.init(this, "601a644d6a2a470e8fa120e3", channel, UMConfigure.DEVICE_TYPE_PHONE, null);
        } else if (TextUtils.equals(BanbenCommonUtils.banben_comm, "?????????")) {
            UMConfigure.setLogEnabled(true);
            UMConfigure.init(this, "601a644d6a2a470e8fa120e3", channel, UMConfigure.DEVICE_TYPE_PHONE, null);
        } else if (TextUtils.equals(BanbenCommonUtils.banben_comm, "??????")) {
            UMConfigure.setLogEnabled(false);
            UMConfigure.init(this, "601a644d6a2a470e8fa120e3", channel, UMConfigure.DEVICE_TYPE_PHONE, null);
        }
        //??????AUTO???????????????????????????SDK????????????????????????????????????????????????
        //???????????????App???Application.onCreate???????????????????????????
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
    }

    private Handler handler;

    private void configMob() {
        MobSDK.init(this);
        //
//        MobPush.setDeviceTokenByUser(DeviceUtils.getAndroidID());
        //???????????????????????????  ?????????MainActivity????????????????????????MobPushReceiver
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
        //????????????
        mob_privacy();
//        MobSDK.init(this);
//        //???????????????????????????  ?????????MainActivity????????????????????????MobPushReceiver
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
//                    //?????????????????????(??????)
//                    System.out.println("MobPush onCustomMessageReceive:" + message.toString());
//                }
//
//                @Override
//                public void onNotifyMessageReceive(Context context, MobPushNotifyMessage message) {
//                    //???????????????
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
//                    //?????????????????????????????????
//                    System.out.println("MobPush onNotifyMessageOpenedReceive:" + message.toString());
//                    Message msg = new Message();
//                    msg.what = 1;
//                    msg.obj = "Click Message:" + message.toString();
//                    handler.sendMessage(msg);
//                }
//
//                @Override
//                public void onTagsCallback(Context context, String[] tags, int operation, int errorCode) {
//                    //??????tags?????????????????????
//                    System.out.println("MobPush onTagsCallback:" + operation + "  " + errorCode);
//                }
//
//                @Override
//                public void onAliasCallback(Context context, String alias, int operation, int errorCode) {
//                    //??????alias?????????????????????
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
        // activity????????????
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
//        //??????link?????????????????????
//        JSONArray jsonArray = MobPushUtils.parseSchemePluginPushIntent(intent);
//        if (jsonArray != null){
//            sb.append("\n" + "bundle toString :" + jsonArray.toString());
//        }
//        //??????scheme????????????????????????????????????
//        JSONArray var = new JSONArray();
//        var =  MobPushUtils.parseSchemePluginPushIntent(intent2);
//        //??????????????????????????????
//        JSONArray var2 = new JSONArray();
//        var2 = MobPushUtils.parseMainPluginPushIntent(intent2);
    }

    public void mob_privacy() {
        // ????????????Locale
// Locale locale = Locale.CHINA;
// ??????????????????locale
        Locale locale = null;
        if (android.os.Build.VERSION.SDK_INT >= 24) {
            LocaleList localeList = getApplicationContext().getResources().getConfiguration().getLocales();
            if (localeList != null && !localeList.isEmpty()) {
                locale = localeList.get(0);
            }
        } else {
            locale = getApplicationContext().getResources().getConfiguration().locale;
        }

// ????????????????????????,locale?????????null?????????????????????????????????????????????
//        PrivacyPolicy policyUrl = MobSDK.getPrivacyPolicy(MobSDK.POLICY_TYPE_URL, locale);
//        String url = policyUrl.getContent();

// ????????????????????????,locale?????????null?????????????????????????????????????????????
        MobSDK.getPrivacyPolicyAsync(MobSDK.POLICY_TYPE_URL, new PrivacyPolicy.OnPolicyListener() {
            @Override
            public void onComplete(PrivacyPolicy data) {
                if (data != null) {
                    // ???????????????
                    String text = data.getContent();
                    MyLogUtil.e("MobPush", "??????????????????->" + text);
                    MobSDK.submitPolicyGrantResult(!TextUtils.isEmpty(text), new OperationCallback<Void>() {
                        @Override
                        public void onComplete(Void data) {
                            MyLogUtil.e("MobPush", "???????????????????????????????????????");
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            MyLogUtil.e("MobPush", "???????????????????????????????????????");
                        }
                    });
                }
            }

            @Override
            public void onFailure(Throwable t) {
                // ????????????
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
//        JShareInterface.init(this, platformConfig);// android 10??????
    }

    private void configTongji() {
        // ??????????????????,????????????????????????
//        JAnalyticsInterface.setDebugMode(true);
//        JAnalyticsInterface.init(this);
    }

    @Override
    public void onHomePressed() {
        super.onHomePressed();
//        AddressSaver.addr = null;
    }
}
