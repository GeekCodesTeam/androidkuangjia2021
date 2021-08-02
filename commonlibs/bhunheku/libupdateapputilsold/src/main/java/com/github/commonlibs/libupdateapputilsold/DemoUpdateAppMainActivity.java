package com.github.commonlibs.libupdateapputilsold;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.github.commonlibs.libupdateapputilsold.customview.ConfirmDialog;
import com.github.commonlibs.libupdateapputilsold.feature.Callback;
import com.github.commonlibs.libupdateapputilsold.util.UpdateAppReceiver;
import com.github.commonlibs.libupdateapputilsold.util.UpdateAppUtils;

public class DemoUpdateAppMainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button BtnNormalUpdate;
    private Button BtnWebDownlaod;
    private Button BtnForceUpdate;
    private Button BtnCheckNameUpdate;


    //服务器apk path,这里放了百度云盘的apk 作为测试
//    String apkPath = "http://issuecdn.baidupcs.com/issue/netdisk/apk/BaiduNetdisk_8.12.9.apk";
//    String apkPath = "http://118.24.148.250:8080/yk/update_signed.apk";
    String apkPath = "https://ip3501727548.mobgslb.tbcache.com/fs08/2020/12/20/8/110_9735b48a09cbfe495f138201aa78e3ec.apk?fname=%E8%85%BE%E8%AE%AF%E6%96%B0%E9%97%BB&productid=2011&packageid=400964468&pkg=com.tencent.news&vcode=6361&yingid=wdj_web&pos=detail-ndownload-com.tencent.news&appid=280347&apprd=280347&iconUrl=http%3A%2F%2Fandroid-artworks.25pp.com%2Ffs08%2F2020%2F12%2F21%2F1%2F110_205b543f1a7b8f019e240f0f841bec73_con.png&did=19faf86fa212b232c23791f221a215e2&md5=030693066c95ced4f805edb7911bd916&ali_redirect_domain=alissl.ucdl.pp.uc.cn&ali_redirect_ex_ftag=fa35a7a50589ab0834939893ee826c42fa4f4851c60badda&ali_redirect_ex_tmining_ts=1608532468&ali_redirect_ex_tmining_expire=3600&ali_redirect_ex_hot=100";
    private int code = 0;
    UpdateAppReceiver updateAppReceiver = new UpdateAppReceiver();

    @Override
    protected void onResume() {
        super.onResume();
        updateAppReceiver.setBr(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        updateAppReceiver.desBr(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app_update);

        findView();
        setListener();
        doNetWork();
    }

    private void findView() {
        BtnNormalUpdate = findViewById(R.id.btn_normalUpdate);
        BtnWebDownlaod = findViewById(R.id.btn_webDownlaod);
        BtnForceUpdate = findViewById(R.id.btn_forceUpdate);
        BtnCheckNameUpdate = findViewById(R.id.btn_checkNameUpdate);
    }

    private void setListener() {
        BtnNormalUpdate.setOnClickListener(this);
        BtnWebDownlaod.setOnClickListener(this);
        BtnForceUpdate.setOnClickListener(this);
        BtnCheckNameUpdate.setOnClickListener(this);
    }

    private void doNetWork() {
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_normalUpdate) {
            checkAndUpdate(1); //基本更新
        } else if (id == R.id.btn_webDownlaod) {
            checkAndUpdate(2); //通过浏览器下载
        } else if (id == R.id.btn_forceUpdate) {
            checkAndUpdate(3); //强制更新
        } else if (id == R.id.btn_checkNameUpdate) {
            checkAndUpdate(4); //根据versionName判断跟新
        }
    }

    private void checkAndUpdate(int code) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            realUpdate(code);
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                realUpdate(code);
            } else {//申请权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    private void realUpdate(int code) {
        this.code = code;
        switch (code) {
            case 1:
                update1();
                break;
            case 2:
                update2();
                break;
            case 3:
                update3();
                break;
            case 4:
                update4();
                break;
        }
    }

    //基本更新（使用本APP进行下载）
    private void update1() {
        UpdateAppUtils.from(this)
                .serverVersionCode(getVersionCode(this) + 1)
                .serverVersionName(getVersionName(this))
                .downloadPath("11111.apk")
                .showProgress(true)
                .apkPath(apkPath)
                .downloadBy(UpdateAppUtils.DOWNLOAD_BY_APP)    //default
                .checkBy(UpdateAppUtils.CHECK_BY_VERSION_CODE) //default
                .updateInfoTitle("新版本已准备好")
                .updateInfo("版本：1.01" + "    " + "大小：2.41M\n" + "1.商户加入群聊，在线沟通更方便\n2.配送费专属优惠，下单更便宜\n3.新客加大福利，更多优惠等你来")
//                .showNotification(false)
//                .needFitAndroidN(false)
                .update();
    }

    //通过浏览器下载
    private void update2() {
        UpdateAppUtils.from(this)
                .serverVersionCode(getVersionCode(this) + 1)
                .serverVersionName(getVersionName(this))
                .apkPath(apkPath)
                .downloadBy(UpdateAppUtils.DOWNLOAD_BY_BROWSER)
                .update();
    }

    //强制更新
    private void update3() {
        UpdateAppUtils.from(this)
                .serverVersionCode(getVersionCode(this) + 1)
                .serverVersionName(getVersionName(this))
                .apkPath(apkPath)
                .isForce(true)
                .update();
    }

    //根据versionName判断更新
    private void update4() {
        UpdateAppUtils.from(this)
                .checkBy(UpdateAppUtils.CHECK_BY_VERSION_NAME)
                .serverVersionCode(getVersionCode(this) + 1)
                .serverVersionName(getVersionName(this) + "diff")
                .apkPath(apkPath)
                .downloadBy(UpdateAppUtils.DOWNLOAD_BY_BROWSER)
                .isForce(true)
                .update();
    }

    //权限请求结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    realUpdate(code);
                } else {
                    new ConfirmDialog(this, new Callback() {
                        @Override
                        public void callback(int position) {
                            if (position == 1) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + getPackageName())); // 根据包名打开对应的设置界面
                                startActivity(intent);
                            }
                        }
                    }).setContent("暂无读写SD卡权限\n是否前往设置？").show();
                }
                break;
        }
    }

    /**
     * 获取版本号
     */
    private String getVersionName(Context ctx) {
        try {
            PackageInfo pi = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    private int getVersionCode(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
