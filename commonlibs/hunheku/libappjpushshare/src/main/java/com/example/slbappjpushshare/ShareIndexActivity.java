package com.example.slbappjpushshare;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.blankj.utilcode.util.AppUtils;
import com.example.slbappjpushshare.fenxiang.JPushShareUtils;

import cn.jiguang.share.android.api.JShareInterface;
import cn.jiguang.share.android.api.Platform;
import cn.jiguang.share.android.api.PlatformConfig;

//import android.support.annotation.Nullable;

public class ShareIndexActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shareindex);
        JShareInterface.setDebugMode(true);
        PlatformConfig platformConfig = new PlatformConfig()
                .setWechat(JPushShareUtils.APP_ID, JPushShareUtils.APP_KEY)// wxa3fa50c49fcd271c 746c2cd0f414de2c256c4f2095316bd0
                .setQQ("1106011004", "YIbPvONmBQBZUGaN")
                .setSinaWeibo("374535501", "baccd12c166f1df96736b51ffbf600a2", "https://www.jiguang.cn");
        JShareInterface.init(this, platformConfig);// android 10崩溃


        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }
        }
    }


    public void BTN1(View view) {
        //分享弹窗bufen
        Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ShareBottomActivity");
        intent.putExtra("type", Platform.ACTION_SHARE);//授权
        startActivity(intent);
    }

    public void BTN2(View view) {
        Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ShareIndex1Activity");
        intent.putExtra("type", Platform.ACTION_AUTHORIZING);//授权
        startActivity(intent);
    }

    public void BTN3(View view) {
        Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ShareIndex2Activity");
        intent.putExtra("type", Platform.ACTION_USER_INFO);//授权
        startActivity(intent);
        //

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * --------------------------------业务逻辑分割线----------------------------------
     */


}