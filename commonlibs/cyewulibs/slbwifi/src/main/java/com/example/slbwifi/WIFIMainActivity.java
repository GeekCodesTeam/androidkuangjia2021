package com.example.slbwifi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.AppUtils;
import com.example.slbwifi.util.MmkvUtilsWifi;
import com.example.slbwifi.util.WifiUtil;

import org.loader.opendroid.db.OpenDroidUtil;

public class WIFIMainActivity extends AppCompatActivity {

    public static final int GET_LOCATION_INFO = 0X12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_wifi);
        OpenDroidUtil.setup(this);
        MmkvUtilsWifi.getInstance().get("");
//        Bugly.init(getApplicationContext(), "973ebfde25", true);
        WifiUtil.autoSwitchWifi(WIFIMainActivity.this);//请求升级数据失败，可能是没有网络，重启WIFI模块
//        WifiUtil.autoSwitchWifi(MainActivity.this);//请求升级数据失败，可能是没有网络，重启WIFI模块
        findViewById(R.id.tv_content).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (WIFIMainActivity.this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, GET_LOCATION_INFO);
                } else {
//                    startActivity(new Intent(WIFIMainActivity.this, WifiSettingActivity.class));
                    startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.WifiSettingActivity"));
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case GET_LOCATION_INFO:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
//                    startActivity(new Intent(WIFIMainActivity.this, WifiSettingActivity.class));
                    startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.WifiSettingActivity"));
                } else {
                    // 没有获取到权限，做特殊处理

                }
                break;
            default:
                break;
        }
    }

}
