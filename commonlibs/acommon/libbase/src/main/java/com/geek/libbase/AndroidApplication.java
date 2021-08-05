/**
 * Copyright 2016,Smart Haier.All rights reserved
 */
package com.geek.libbase;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.multidex.MultiDex;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.Utils;
import com.geek.liblanguage.MultiLanguages;
import com.geek.liblanguage.OnLanguageListener;
import com.geek.libutils.app.BaseApp;
import com.geek.libutils.app.LocalManageUtil;
import com.geek.libutils.app.MyLogUtil;
import com.geek.libutils.data.MmkvUtils;
import com.haier.cellarette.libretrofit.common.RetrofitNetNew;
import com.haier.cellarette.libwebview.hois2.HiosHelper;
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


/**
 * <p class="note">File Note</p>
 * created by geek at 2017/6/6
 */
public class AndroidApplication extends Application {

    public static final String DIR_PROJECT = "/geekandroid/app/";
    public static final String DIR_CACHE = DIR_PROJECT + "cache/"; // 网页缓存路径
    public static final String IMG_CACHE = DIR_PROJECT + "image/"; // image缓存路径
    public static final String VIDEO_CACHE = DIR_PROJECT + "video/"; // video缓存路径
    public static final String MUSIC_CACHE = DIR_PROJECT + "music/"; // music缓存路径

    @Override
    protected void attachBaseContext(Context base) {
        //保存系统选择语言
        LocalManageUtil.saveSystemCurrentLanguage(base);
        super.attachBaseContext(MultiLanguages.attach(base));
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
            Bugly.init(getApplicationContext(), buglykey, true);
            MyLogUtil.on(true);
            //TODO test
//            if (LeakCanary.isInAnalyzerProcess(this)) {
//                // This process is dedicated to LeakCanary for heap analysis.
//                // You should not init your app in this process.
//                return;
//            }
//            LeakCanary.install(this);
        } else if (TextUtils.equals(banben_comm, "预生产")) {
            Bugly.init(getApplicationContext(), buglykey, true);
            MyLogUtil.on(true);
        } else if (TextUtils.equals(banben_comm, "线上")) {
//            CrashReport.initCrashReport(this, "068e7f32c3", false);// 线上
            Bugly.init(getApplicationContext(), buglykey, true);
            MyLogUtil.on(true);
        }
    }

    /**
     * 忽略https的证书校验
     * 避免Glide加载https图片报错：
     * javax.net.ssl.SSLHandshakeException: java.security.cert.CertPathValidatorException: Trust anchor for certification path not found.
     */
    protected void handleSSLHandshake() {
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

    protected void configHios() {
//        HiosRegister.load();// 静态注册部分 已弃用
        HiosHelper.config(AppUtils.getAppPackageName() + ".ad.web.page", AppUtils.getAppPackageName() + ".web.page");
    }

    protected void others() {
        Utils.init(this);// com.blankj:utilcode:1.17.3
        // 为了横屏需求的toast
        com.hjq.toast.ToastUtils.init(this);
        // 初始化多语种框架
        MultiLanguages.init(this);
        // 设置语种变化监听器
        MultiLanguages.setOnLanguageListener(new OnLanguageListener() {

            @Override
            public void onAppLocaleChange(Locale oldLocale, Locale newLocale) {
                Log.d("MultiLanguages", "监听到应用切换了语种，旧语种：" + oldLocale + "，新语种：" + newLocale);
            }

            @Override
            public void onSystemLocaleChange(Locale oldLocale, Locale newLocale) {
                Log.d("MultiLanguages", "监听到系统切换了语种，旧语种：" + oldLocale + "，新语种：" + newLocale + "，是否跟随系统：" + MultiLanguages.isSystemLanguage());
            }
        });
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
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                MyLogUtil.e("regActivityLife-onActivityCreated", mFinalCount + "");
            }

            @Override
            public void onActivityStarted(Activity activity) {
                mFinalCount++;
                //如果mFinalCount ==1，说明是从后台到前台
                if (mFinalCount == 1) {
                    //说明从后台回到了前台
                }
                MyLogUtil.e("regActivityLife-onActivityStarted", mFinalCount + "");
            }

            @Override
            public void onActivityResumed(Activity activity) {
                MyLogUtil.e("regActivityLife-onActivityResumed", mFinalCount + "");
            }

            @Override
            public void onActivityPaused(Activity activity) {
                MyLogUtil.e("regActivityLife-onActivityPaused", mFinalCount + "");
            }

            @Override
            public void onActivityStopped(Activity activity) {
                mFinalCount--;
                //如果mFinalCount == 0，说明是前台到后台
                if (mFinalCount == 0) {
                    MyLogUtil.e("regActivityLife-onActivityStopped", mFinalCount + "");
                    //说明从前台回到了后台
                    Toast.makeText(BaseApp.get(), AppUtils.getAppName() + "已进入后台运行", Toast.LENGTH_LONG).show();
//                    ToastUtils.showLong(AppUtils.getAppName() + "已进入后台运行");
                }

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                MyLogUtil.e("regActivityLife-onActivitySaveInstanceState", mFinalCount + "");
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                MyLogUtil.e("regActivityLife-onActivityDestroyed", mFinalCount + "");
            }
        });
    }

    protected void configRetrofitNet() {
        String cacheDir = getExternalFilesDir(null) + DIR_CACHE;
        // https://api-cn.faceplusplus.com/
//        RetrofitNet.config();
        RetrofitNetNew.config();
    }


    public void onHomePressed() {
    }
}
