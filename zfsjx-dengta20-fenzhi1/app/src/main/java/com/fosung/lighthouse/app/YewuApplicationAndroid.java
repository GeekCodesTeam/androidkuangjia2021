/**
 * Copyright 2016,Smart Haier.All rights reserved
 */
package com.fosung.lighthouse.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.LocaleList;
import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;

import com.fosung.lighthouse.manage.index.services.MOBIDservices;
import com.geek.libbase.AndroidApplication;
import com.geek.libutils.app.AppUtils;
import com.geek.libutils.app.BaseApp;
import com.geek.libutils.app.MyLogUtil;
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
import com.zcolin.frame.BanbenCommonUtils;

import java.util.Locale;

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
        configBugly(BanbenCommonUtils.banben_comm, "3aeeb18e5e");
        configHios();
        configmmkv();
        configShipei();
        configRetrofitNet();
        //TODO 业务bufen
        // 升级
        new PgyerSDKManager.Init()
                .setContext(this) //设置上下问对象
                .start();
        //初始化mob
        configMob();
        initTencentIM();
        others();
    }

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

    private void initTencentIM() {
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

    @Override
    public void onHomePressed() {
        super.onHomePressed();
//        AddressSaver.addr = null;
    }
}
