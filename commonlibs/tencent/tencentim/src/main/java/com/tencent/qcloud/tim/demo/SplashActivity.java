package com.tencent.qcloud.tim.demo;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.AppUtils;
import com.geek.libutils.app.BaseApp;
import com.heytap.msp.push.HeytapPushManager;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.qcloud.tim.demo.helper.ConfigHelper;
import com.tencent.qcloud.tim.demo.login.LoginForDevActivity;
import com.tencent.qcloud.tim.demo.login.UserInfo;
import com.tencent.qcloud.tim.demo.main.IMMainActivity;
import com.tencent.qcloud.tim.demo.signature.GenerateTestUserSig;
import com.tencent.qcloud.tim.demo.thirdpush.HUAWEIHmsMessageService;
import com.tencent.qcloud.tim.demo.thirdpush.OfflineMessageDispatcher;
import com.tencent.qcloud.tim.demo.utils.DemoLog;
import com.tencent.qcloud.tim.demo.utils.MessageNotification;
import com.tencent.qcloud.tim.demo.utils.PrivateConstants;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.base.IMEventListener;
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack;
import com.tencent.qcloud.tim.uikit.modules.chat.base.OfflineMessageBean;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationManagerKit;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;
import com.tencent.rtmp.TXLiveBase;

public class SplashActivity extends Activity {

    private static final String TAG = SplashActivity.class.getSimpleName();
    private static final int SPLASH_TIME = 1500;
    private View mFlashView;
    private UserInfo mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //
//        mFlashView = findViewById(R.id.flash_view);
        mUserInfo = UserInfo.getInstance();
        handleData();
        // 放在application里面bufen 不然会崩glide
//        initTencentIM();
    }

    private void initTencentIM() {
//        // bugly上报
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
        HeytapPushManager.init(this, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
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
                    DemoLog.i(TAG, "onActivityCreated bundle: " + bundle);
                    if (bundle != null) { // 若bundle不为空则程序异常结束
                        // 重启整个程序
    //                    Intent intent = new Intent(activity, SplashActivity.class);
                        Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.WelComeActivity");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }

                @Override
                public void onActivityStarted(Activity activity) {
                    foregroundActivities++;
                    if (foregroundActivities == 1 && !isChangingConfiguration) {
                        // 应用切到前台
                        DemoLog.i(TAG, "application enter foreground");
                        V2TIMManager.getOfflinePushManager().doForeground(new V2TIMCallback() {
                            @Override
                            public void onError(int code, String desc) {
                                DemoLog.e(TAG, "doForeground err = " + code + ", desc = " + desc);
                            }

                            @Override
                            public void onSuccess() {
                                DemoLog.i(TAG, "doForeground success");
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
                        DemoLog.i(TAG, "application enter background");
                        int unReadCount = ConversationManagerKit.getInstance().getUnreadTotal();
                        V2TIMManager.getOfflinePushManager().doBackground(unReadCount, new V2TIMCallback() {
                            @Override
                            public void onError(int code, String desc) {
                                DemoLog.e(TAG, "doBackground err = " + code + ", desc = " + desc);
                            }

                            @Override
                            public void onSuccess() {
                                DemoLog.i(TAG, "doBackground success");
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
    }

    private void handleData() {
        if (mUserInfo != null && mUserInfo.isAutoLogin()) {
            login();
        } else {
            getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startLogin();
                }
            }, SPLASH_TIME);
        }
    }

    private void login() {
        TUIKit.login(mUserInfo.getUserId(), mUserInfo.getUserSig(), new IUIKitCallBack() {
            @Override
            public void onError(String module, final int code, final String desc) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        ToastUtil.toastLongMessage(getString(R.string.failed_login_tip) + ", errCode = " + code + ", errInfo = " + desc);
                        startLogin();
                    }
                });
                DemoLog.i(TAG, "imLogin errorCode = " + code + ", errorInfo = " + desc);
            }

            @Override
            public void onSuccess(Object data) {
                startMain();
            }
        });
    }

    private void startLogin() {
        Intent intent = new Intent(SplashActivity.this, LoginForDevActivity.class);
        startActivity(intent);
        finish();
    }

    private void startMain() {
        OfflineMessageBean bean = OfflineMessageDispatcher.parseOfflineMessage(getIntent());
        if (bean != null) {
            OfflineMessageDispatcher.redirect(bean);
            finish();
            return;
        }

        Intent intent = new Intent(SplashActivity.this, IMMainActivity.class);
        startActivity(intent);
        finish();
    }
}
