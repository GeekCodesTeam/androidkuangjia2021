package com.example.slbappcomm.saoma.demo1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import com.example.slbappcomm.R;
import com.king.zxing.CaptureActivity;
import com.king.zxing.Intents;
import com.king.zxing.util.CodeUtils;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class SaomaAct extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private TextView tv1;


    public static final String KEY_TITLE = "key_title";
    public static final String KEY_IS_QR_CODE = "key_code";
    public static final String KEY_IS_CONTINUOUS = "key_continuous_scan";
    public static final int REQUEST_CODE_SCAN = 0X01;
    public static final int REQUEST_CODE_PHOTO = 0X02;

    public static final int RC_CAMERA = 0X01;

    public static final int RC_READ_PHOTO = 0X02;


    private Class<?> cls;
    private String title;
    private boolean isContinuousScan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saoma1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case REQUEST_CODE_SCAN:
                    String result = data.getStringExtra(Intents.Scan.RESULT);
                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                    break;
                case REQUEST_CODE_PHOTO:
                    parsePhoto(data);
                    break;
            }

        }
    }

    private void parsePhoto(Intent data) {
        final String path = UriUtilsSaoma.getImagePath(this, data);
        Log.d("Jenly", "path:" + path);
        if (TextUtils.isEmpty(path)) {
            return;
        }
        //异步解析
        asyncThread(new Runnable() {
            @Override
            public void run() {
                final String result = CodeUtils.parseCode(path);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("Jenly", "result:" + result);
                        Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    private void asyncThread(Runnable runnable) {
        new Thread(runnable).start();
    }

    private Context getContext() {
        return this;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Some permissions have been granted

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Some permissions have been denied
        // ...
    }

    /**
     * 检测拍摄权限
     */
    @AfterPermissionGranted(RC_CAMERA)
    private void checkCameraPermissions() {
        String[] perms = {Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {//有权限
            startScan(cls, title);
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.permission_camera),
                    RC_CAMERA, perms);
        }
    }

    /**
     * 扫码
     *
     * @param cls
     * @param title
     */
    private void startScan(Class<?> cls, String title) {
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeCustomAnimation(this, R.anim.in, R.anim.out);
        Intent intent = new Intent(this, cls);
        intent.putExtra(KEY_TITLE, title);
        intent.putExtra(KEY_IS_CONTINUOUS, isContinuousScan);
        ActivityCompat.startActivityForResult(this, intent, REQUEST_CODE_SCAN, optionsCompat.toBundle());
    }

    /**
     * 生成二维码/条形码
     *
     * @param isQRCode
     */
    private void startCode(boolean isQRCode) {
        Intent intent = new Intent(this, CodeActivity.class);
        intent.putExtra(KEY_IS_QR_CODE, isQRCode);
        intent.putExtra(KEY_TITLE, isQRCode ? getString(R.string.qr_code) : getString(R.string.bar_code));
        startActivity(intent);
    }

    private void startPhotoCode() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(pickIntent, REQUEST_CODE_PHOTO);
    }

    @AfterPermissionGranted(RC_READ_PHOTO)
    private void checkExternalStoragePermissions() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {//有权限
            startPhotoCode();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.permission_external_storage),
                    RC_READ_PHOTO, perms);
        }
    }

    public void onClick(View v) {
        isContinuousScan = false;
        int id = v.getId();
        if (id == R.id.btn0) {
            this.cls = CaptureActivity.class;
            this.title = ((Button) v).getText().toString();
            checkCameraPermissions();
        } else if (id == R.id.btn1) {
            this.cls = CustomCaptureActivity.class;
            this.title = ((Button) v).getText().toString();
            isContinuousScan = true;
            checkCameraPermissions();
        } else if (id == R.id.btn2) {
            this.cls = CaptureFragmentActivity.class;
            this.title = ((Button) v).getText().toString();
            checkCameraPermissions();
        } else if (id == R.id.btn3) {
            this.cls = EasyCaptureActivity.class;
            this.title = ((Button) v).getText().toString();
            checkCameraPermissions();
        } else if (id == R.id.btn4) {
            this.cls = CustomActivity.class;
            this.title = ((Button) v).getText().toString();
            checkCameraPermissions();
        } else if (id == R.id.btn5) {
            startCode(false);
        } else if (id == R.id.btn6) {
            startCode(true);
        } else if (id == R.id.btn7) {
            checkExternalStoragePermissions();
        }

    }
}
