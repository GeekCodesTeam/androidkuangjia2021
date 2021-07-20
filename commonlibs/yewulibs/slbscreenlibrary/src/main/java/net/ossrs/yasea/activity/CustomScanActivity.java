package net.ossrs.yasea.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import net.ossrs.yasea.R;

/**
 * 描述：二维码扫描
 * -
 * 创建人：wangchunxiao
 * 创建时间：2017/11/22
 */
public class CustomScanActivity extends AppCompatActivity implements DecoratedBarcodeView.TorchListener {

    // 添加一个按钮用来控制闪光灯，同时添加两个按钮表示其他功能，先用Toast表示
    Button swichLight;
    DecoratedBarcodeView mDBV;

    private CaptureManager captureManager;
    private boolean isLightOn = false;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
//        setReturnPop(false);
        initView();
        mDBV.setTorchListener(this);

        // 如果没有闪光灯功能，就去掉相关按钮
        if (!hasFlash()) {
            swichLight.setVisibility(View.GONE);
        }

        //重要代码，初始化捕获
        captureManager = new CaptureManager(this, mDBV);
        captureManager.initializeFromIntent(getIntent(), savedInstanceState);
        captureManager.decode();
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        if (appLinkIntent != null) {
            String appLinkAction = appLinkIntent.getAction();
            if (appLinkAction != null) {
                Uri appLinkData = appLinkIntent.getData();
                if (appLinkData != null) {
                    ToastUtils.showLong("进入CustomScanActivity成功");
                }
            }
        }
    }

    public void initView() {
        swichLight = findViewById(R.id.btn_switch);
        mDBV = findViewById(R.id.dbv_custom);

        swichLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLightOn) {
                    mDBV.setTorchOff();
                } else {
                    mDBV.setTorchOn();
                }
            }
        });
    }

    // torch 手电筒
    @Override
    public void onTorchOn() {
        isLightOn = true;
    }

    @Override
    public void onTorchOff() {
        isLightOn = false;
    }

    // 判断是否有闪光灯功能
    private boolean hasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    @Override
    public void onPause() {
        super.onPause();
        captureManager.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        captureManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        captureManager.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        captureManager.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mDBV.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }
}
