package com.fosung.xuanchuanlan.common.app;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.blankj.utilcode.util.AppUtils;
import com.fosung.frameutils.http.okhttp.OkHttpUtils;
import com.fosung.frameutils.http.okhttp.cookie.CookieJarImpl;
import com.fosung.frameutils.http.okhttp.cookie.store.MemoryCookieStore;
import com.fosung.frameutils.http.okhttp.https.HttpsUtils;
import com.haier.cellarette.libwebview.hois2.HiosHelper;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;
//import com.yuntongxun.plugin.fullconf.wrap.WrapManager;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.unit.Subunits;
import okhttp3.OkHttpClient;

/**
 * Created by WJ on 2016/10/18.
 */
public class ConfApplication extends MultiDexApplication {

    private static final String TAG = ConfApplication.class.getSimpleName();

    private static ConfApplication instance;
    public static final String WX_APP_ID = "wx609ffabe65a10c8f";
    public static  Context APP_CONTEXT;

    public static ConfApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        APP_CONTEXT = this;
        instance = this;
        initHttpOptions();
        initX5();
//        WrapManager.getInstance().app_init(this);
        HiosHelper.config(AppUtils.getAppPackageName() + ".ad.web.page", AppUtils.getAppPackageName() + ".web.page");
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void initHttpOptions() {
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        CookieJarImpl cookieJar1 = new CookieJarImpl(new MemoryCookieStore());
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //facebook调试工具
        //        if (BuildConfig.DEBUG) {
        //            builder.addNetworkInterceptor(new StethoInterceptor());
        //        }
        OkHttpClient okHttpClient = builder.connectTimeout(30000L, TimeUnit.MILLISECONDS)
                .readTimeout(30000L, TimeUnit.MILLISECONDS)
                .cookieJar(cookieJar1)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    /**
     * 初始化腾讯x5
     */
    private void initX5() {
        final QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("x5", arg0 ? "成功" : "失败");
//              AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//              builder.setMessage(arg0 ? "成功" : "失败");
//              AlertDialog alert = builder.create();
//              alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//              alert.show();
            }

            @Override
            public void onCoreInitFinished() {

            }
        };

        //非wifi情况下，主动下载x5内核
        QbSdk.setDownloadWithoutWifi(true);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            QbSdk.setOnlyDownload(true);
        }
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。

//        QbSdk.setTbsListener(new TbsListener() {
//            @Override
//            public void onDownloadFinish(int i) {
//                Log.d("tbsx50进度：", "下载完成" + i);
//            }
//
//            @Override
//            public void onInstallFinish(int i) {
//                Log.d("tbsx50进度：", "安装完成" + i);
//                //x5内核初始化接口
//
//            }
//
//            @Override
//            public void onDownloadProgress(int i) {
//                Log.d("tbsx50进度：", i + "");
//
//            }
//        });


        //优化初始化速度
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        QbSdk.initTbsSettings(map);
        QbSdk.initX5Environment(getApplicationContext(), cb);

    }
}


