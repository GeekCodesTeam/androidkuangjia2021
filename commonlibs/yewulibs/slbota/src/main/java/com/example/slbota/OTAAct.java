package com.example.slbota;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SPUtils;
import com.geek.libutils.app.BaseApp;
import com.geek.libutils.app.MyLogUtil;
import com.geek.libutils.data.MmkvUtils;
import com.haier.cellarette.libretrofit.common.RetrofitNetNew;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.io.File;
import java.net.Proxy;
import java.util.Objects;

//import com.example.biz3slbappcomm.presenter.SIndexAdvertisingPresenter;
//import com.example.biz3slbappcomm.presenter.SPaySuccessPresenter;

public class OTAAct extends AppCompatActivity /*implements SPaySuccessView, SIndexAdvertisingView*/ {

    private TextView codeText;//当前版本号
    private TextView codeNewText;//新版本号
    private TextView versionText;//版本说明
    private TextView romVersionText;  //ROM版本
    private TextView apkLineName;  //apk渠道名称
    private TextView findNew;  //apk渠道名称
    private Button btn_check_update;
    private Button btn_check_update2;
    private boolean ischeck = false;//默认不升级

    private String apkUrl = "";//下载地址
    private String versionCode = "";//APK版本号
    private String customNumber = "";//APK定制号
    private String versionName = "";//APK版本名称
    private String serverCustomNumber = "";//服务端APK定制号
    private String apkUrlDiff = "";
    private String apkUrlDiffMd5 = "";
    private String apkUrlFull = "";
    private String apkUrlFullMd5 = "";

//    private SPaySuccessPresenter OTAPresenter;
//    private SIndexAdvertisingPresenter APKPresenter;


    @Override
    protected void onDestroy() {
//        OTAPresenter.onDestory();
//        APKPresenter.onDestory();
        unregisterReceiver(romUpdataBroadcastReceiver);
        unregisterReceiver(apkUpdataBroadcastReceiver);
        super.onDestroy();
    }

    public static final String DIR_PROJECT = "/alty/app/";
    public static final String DIR_CACHE = DIR_PROJECT + "cache/"; // 网页缓存路径

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_ota);
        chushihua_setting();
        //
        findview();
//        OTAPresenter = new SPaySuccessPresenter();
//        OTAPresenter.onCreate(this);
//        APKPresenter = new SIndexAdvertisingPresenter();
//        APKPresenter.onCreate(this);
        //注册ROM升级广播
        IntentFilter ormIntentFilter = new IntentFilter();
        ormIntentFilter.addAction(OTASendUtils.ACTION_OTA_ROM_UPDATA);
        registerReceiver(romUpdataBroadcastReceiver, ormIntentFilter);

        //注册APK升级广播
        IntentFilter apkIntentFilter = new IntentFilter();
        apkIntentFilter.addAction(OTASendUtils.ACTION_OTA_APK_UPDATA);
        registerReceiver(apkUpdataBroadcastReceiver, apkIntentFilter);
//        donetwork1();
//        donetwork2();

    }

    private void chushihua_setting() {
        //
        String cacheDir = getExternalFilesDir(null) + DIR_CACHE;
        // https://api-cn.faceplusplus.com/
//        RetrofitNet.config();
        RetrofitNetNew.config();
        MmkvUtils.getInstance().get("");
        MmkvUtils.getInstance().get_demo();
        initFileDownLoader();
    }

    /**
     * 初始化文件下载
     */
    private void initFileDownLoader() {
        FileDownloader.setupOnApplicationOnCreate(BaseApp.get())
                .connectionCreator(new FileDownloadUrlConnection
                        .Creator(new FileDownloadUrlConnection.Configuration()
                        .connectTimeout(60_000) // set connection timeout.
                        .readTimeout(60_000) // set read timeout.
                        .proxy(Proxy.NO_PROXY) // set proxy
                )).commit();
//        FileDownloadUtils.setDefaultSaveRootPath(getExternalFilesDir(null).getAbsolutePath() + File.separator + "haier/download");
        FileDownloadUtils.setDefaultSaveRootPath(get_file_url(this) + File.separator + "ota/download");
    }

    /**
     * 获取assets路径bufen
     *
     * @return
     */
    public String get_file_url(Context context) {
        String file_apk_url;
        File file_apks = context.getExternalCacheDir();
        if (file_apks != null) {
            file_apk_url = file_apks.getAbsolutePath();
        } else {
            file_apk_url = Objects.requireNonNull(getExternalFilesDir(null)).getAbsolutePath();
        }
        return file_apk_url;
    }


    private void donetwork1() {
//        OTAPresenter.getPaySuccessData();
    }

    private void donetwork2() {
//        APKPresenter.getIndexAdvertisingData();
    }

    /**
     * ROM强制升级更新广播
     */
    private BroadcastReceiver romUpdataBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String mUpdateFlag = intent.getStringExtra("msg");
            MyLogUtil.d("OTA_ROM_FORCE_UPDATA", "---------极光推送部分------开启强制升级OS---------");
            MyLogUtil.d("OTA_ROM_FORCE_UPDATA", "Push message：osId= " + mUpdateFlag);
            // TODO test
            // OTA
            //保存上一个版本OS版本名称，OS升级完重启检测apk升级
            SPUtils.getInstance().put(OTASendUtils.OS_VERSION_NAME_OLD, Build.DISPLAY);
            //进行下载rom操作
            Intent intentOTA = new Intent(OTAAct.this, OtaRomDownloadService.class);
            intentOTA.putExtra("romurl", "http://cdn.haier-jiuzhidao.com/MILHADE-TOTALE.zip");
            intentOTA.putExtra("rommd5", "http://cdn.haier-jiuzhidao.com/MILHADE-TOTALE.zip");
            startService(intentOTA);
            //ROM升级
            donetwork1();
        }
    };

    /**
     * OS+APK升级更新广播
     */
    private BroadcastReceiver apkUpdataBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String mUpdateFlag = intent.getStringExtra("msg");
            MyLogUtil.d("OTA_ROM_APK_UPDATA", "---------极光推送成功-------开启升级OS+APK---------");
            MyLogUtil.d("OTA_ROM_APK_UPDATA", "Push message：osId = " + mUpdateFlag);
            // TODO test
            //TODO APK升级部分
            Intent intentApk = new Intent(OTAAct.this, OtaApkDownloadService.class);
            intentApk.putExtra("apkurl", "https://ip3501727548.mobgslb.tbcache.com/fs08/2020/12/20/8/110_9735b48a09cbfe495f138201aa78e3ec.apk?fname=%E8%85%BE%E8%AE%AF%E6%96%B0%E9%97%BB&productid=2011&packageid=400964468&pkg=com.tencent.news&vcode=6361&yingid=wdj_web&pos=detail-ndownload-com.tencent.news&appid=280347&apprd=280347&iconUrl=http%3A%2F%2Fandroid-artworks.25pp.com%2Ffs08%2F2020%2F12%2F21%2F1%2F110_205b543f1a7b8f019e240f0f841bec73_con.png&did=19faf86fa212b232c23791f221a215e2&md5=030693066c95ced4f805edb7911bd916&ali_redirect_domain=alissl.ucdl.pp.uc.cn&ali_redirect_ex_ftag=fa35a7a50589ab0834939893ee826c42fa4f4851c60badda&ali_redirect_ex_tmining_ts=1608532468&ali_redirect_ex_tmining_expire=3600&ali_redirect_ex_hot=100");
            startService(intentApk);
            //OS+APK升级，先检查OS,在检查APK
            donetwork2();
        }
    };

    private void findview() {
        codeText = findViewById(R.id.set_item_title2);
        codeNewText = findViewById(R.id.set_item_title_text);
        versionText = findViewById(R.id.set_item_title_explain);
        apkLineName = findViewById(R.id.tv_line_name);
        findNew = findViewById(R.id.set_item_title_new);
        romVersionText = findViewById(R.id.version_rom_text);
        btn_check_update = findViewById(R.id.check_update);
        btn_check_update2 = findViewById(R.id.check_update2);
        // 版本环境判断给出名字
//        if (ConstantUtil.VERSION_INFO == VersionInfo.NATION) {
//            apkLineName.setText(getResources().getString(R.string.app_jzdsyb));
//        } else if (ConstantUtil.VERSION_INFO == VersionInfo.RESTAURANT) {
//            apkLineName.setText(getResources().getString(R.string.app_jzdctb));
//        }
        apkLineName.setText("OTA-测试版");
        codeText.setText(AppUtils.getAppVersionName().length() == 0 ? "Base1.0" : AppUtils.getAppVersionName());
        romVersionText.setText(Build.DISPLAY);
    }

    /**
     * 关闭
     *
     * @param view
     */
    public void exit(View view) {
        finish();
    }

    /**
     * 检查更新 OTA
     *
     * @param view
     */
    public void check(View view) {
//        donetwork1();
        OTASendUtils.getInstance(this).send_broadcast1("OTA升级");
    }

    /**
     * 检查更新2 APK
     *
     * @param view
     */
    public void check2(View view) {
//        donetwork1();
        OTASendUtils.getInstance(this).send_broadcast2("APK升级");
    }

//
//    @Override
//    public void OnPaySuccessSuccess(SPaySuccessBean sPaySuccessBean) {
//
//    }
//
//    @Override
//    public void OnPaySuccessNodata(String s) {
//
//    }
//
//    @Override
//    public void OnPaySuccessFail(String s) {
//        //TODO OTA升级部分
//        if (true) {
//            // OTA
//            //保存上一个版本OS版本名称，OS升级完重启检测apk升级
//            SPUtils.getInstance().put(OTASendUtils.OS_VERSION_NAME_OLD, Build.DISPLAY);
//            //进行下载rom操作
//            Intent intent = new Intent(this, OtaRomDownloadService.class);
//            intent.putExtra("romurl", "http://cdn.haier-jiuzhidao.com/MILHADE-TOTALE.zip");
//            intent.putExtra("rommd5", "http://cdn.haier-jiuzhidao.com/MILHADE-TOTALE.zip");
//            startService(intent);
//        } else {
//            // APK
//            donetwork2();
//        }
//    }

//    @Override
//    public void OnIndexAdvertisingSuccess(SIndexAdvertisingBean sIndexAdvertisingBean) {
//
//    }
//
//    @Override
//    public void OnIndexAdvertisingNodata(String s) {
//
//    }
//
//    @Override
//    public void OnIndexAdvertisingFail(String s) {
//        //TODO APK升级部分
//        Intent intent = new Intent(this, OtaApkDownloadService.class);
//        intent.putExtra("apkurl", "https://ip3501727548.mobgslb.tbcache.com/fs08/2020/12/20/8/110_9735b48a09cbfe495f138201aa78e3ec.apk?fname=%E8%85%BE%E8%AE%AF%E6%96%B0%E9%97%BB&productid=2011&packageid=400964468&pkg=com.tencent.news&vcode=6361&yingid=wdj_web&pos=detail-ndownload-com.tencent.news&appid=280347&apprd=280347&iconUrl=http%3A%2F%2Fandroid-artworks.25pp.com%2Ffs08%2F2020%2F12%2F21%2F1%2F110_205b543f1a7b8f019e240f0f841bec73_con.png&did=19faf86fa212b232c23791f221a215e2&md5=030693066c95ced4f805edb7911bd916&ali_redirect_domain=alissl.ucdl.pp.uc.cn&ali_redirect_ex_ftag=fa35a7a50589ab0834939893ee826c42fa4f4851c60badda&ali_redirect_ex_tmining_ts=1608532468&ali_redirect_ex_tmining_expire=3600&ali_redirect_ex_hot=100");
//        startService(intent);
//    }

//    @Override
//    public String getIdentifier() {
//        return SystemClock.currentThreadTimeMillis() + "";
//    }
}
