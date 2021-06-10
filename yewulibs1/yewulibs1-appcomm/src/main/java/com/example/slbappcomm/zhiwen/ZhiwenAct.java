package com.example.slbappcomm.zhiwen;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.example.slbappcomm.R;

public class ZhiwenAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhiwen);

        if (isFingerprintAuthAvailable()) {
            fingerPrintCheck();
        }
    }

    /**
     * 判断是否具有指纹识别功能和是否已经设置指纹
     */
    public boolean isFingerprintAuthAvailable() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//判断当前手机版本
            KeyguardManager mKeyguardManager = (KeyguardManager) getSystemService(Activity.KEYGUARD_SERVICE);//获得密码管理器
            if (!mKeyguardManager.isKeyguardSecure()) {//判断是否具有锁屏密码
                return false;
            }
            if (checkSelfPermission(Manifest.permission.USE_FINGERPRINT) == PackageManager.PERMISSION_GRANTED) {//判断APP是否具有指纹识别权限
                FingerprintManager manager = (FingerprintManager) getSystemService(Activity.FINGERPRINT_SERVICE); //获得指纹识别管理器对象
                CancellationSignal mCancellationSignal = new CancellationSignal();
                //判断是否具有指纹识别的硬件设施和是否已经录好指纹
                return manager.isHardwareDetected() && manager.hasEnrolledFingerprints();
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 开始指纹校验
     */
    @SuppressLint("NewApi")
    private void startFingerPrintListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.USE_FINGERPRINT) == PackageManager.PERMISSION_GRANTED) {
                FingerprintManager manager = (FingerprintManager) getSystemService(Activity.FINGERPRINT_SERVICE);
                CancellationSignal mCancellationSignal = new CancellationSignal();
                //指纹检验
                manager.authenticate(null, mCancellationSignal, 0, new MyAuthenticationCallback(), null);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public class MyAuthenticationCallback extends FingerprintManager.AuthenticationCallback {
        private static final String TAG = "MainActivity";

        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            Log.e(TAG, "onAuthenticationSucceeded: " + "校验成功");
            ToastUtils.showLong("指纹识别成功");
        }

        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
            Log.e(TAG, "onAuthenticationFailed: " + "校验失败");
            ToastUtils.showLong("指纹识别失败");
        }

        @Override
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
            super.onAuthenticationHelp(helpCode, helpString);
            Log.e(TAG, "onAuthenticationHelp: " + helpString);
        }

        @Override
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            super.onAuthenticationError(errorCode, errString);
            Log.e(TAG, "onAuthenticationError: " + errString);
        }
    }

    /**
     * 检验指纹功能
     */
    private void fingerPrintCheck() {
        // 判断是否具有指纹识别功能
        if (!isFingerprintAuthAvailable()) {
            return;
        } else {
            startFingerPrintListener();
        }
    }

}