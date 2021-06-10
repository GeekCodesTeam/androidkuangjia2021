package com.haier.cellarette.baselibrary.quanxian;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.geek.libutils.app.BaseApp;
import com.haier.cellarette.baselibrary.R;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.ToastUtils;
import com.hjq.toast.style.WhiteToastStyle;

import java.util.List;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/XXPermissions
 * time   : 2018/06/15
 * desc   : XXPermissions 权限请求框架使用案例
 */
public class QuanxianActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanxian);
        //
        // 初始化吐司工具类
        ToastUtils.init(BaseApp.get(), new WhiteToastStyle());

        // 设置权限申请拦截器
        XXPermissions.setInterceptor(new PermissionInterceptor());

        // 告诉框架，当前项目已适配分区存储特性
        //XXPermissions.setScopedStorage(true);
        //
        findViewById(R.id.btn_main_request_1).setOnClickListener(this);
        findViewById(R.id.btn_main_request_2).setOnClickListener(this);
        findViewById(R.id.btn_main_request_3).setOnClickListener(this);
        findViewById(R.id.btn_main_request_4).setOnClickListener(this);
        findViewById(R.id.btn_main_request_5).setOnClickListener(this);
        findViewById(R.id.btn_main_request_6).setOnClickListener(this);
        findViewById(R.id.btn_main_request_7).setOnClickListener(this);
        findViewById(R.id.btn_main_request_8).setOnClickListener(this);
        findViewById(R.id.btn_main_app_details).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.btn_main_request_1) {

            XXPermissions.with(this)
                    .permission(Permission.CAMERA)
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(List<String> permissions, boolean all) {
                            if (all) {
                                toast("获取拍照权限成功");
                            }
                        }
                    });

        } else if (viewId == R.id.btn_main_request_2) {

            XXPermissions.with(this)
                    .permission(Permission.RECORD_AUDIO)
                    .permission(Permission.Group.CALENDAR)
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(List<String> permissions, boolean all) {
                            if (all) {
                                toast("获取录音和日历权限成功");
                            }
                        }
                    });

        } else if (viewId == R.id.btn_main_request_3) {

            XXPermissions.with(this)
                    .permission(Permission.ACCESS_COARSE_LOCATION)
                    .permission(Permission.ACCESS_FINE_LOCATION)
                    .permission(Permission.ACCESS_BACKGROUND_LOCATION)
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(List<String> permissions, boolean all) {
                            if (all) {
                                toast("获取定位权限成功");
                            }
                        }
                    });

        } else if (viewId == R.id.btn_main_request_4) {

            long delayMillis = 0;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                delayMillis = 2000;
                toast("当前版本不是 Android 11 以上，会自动变更为旧版的请求方式");
            }

            view.postDelayed(new Runnable() {

                @Override
                public void run() {
                    XXPermissions.with(QuanxianActivity.this)
                            // 不适配 Android 11 可以这样写
                            //.permission(Permission.Group.STORAGE)
                            // 适配 Android 11 需要这样写，这里无需再写 Permission.Group.STORAGE
                            .permission(Permission.MANAGE_EXTERNAL_STORAGE)
                            .request(new OnPermissionCallback() {

                                @Override
                                public void onGranted(List<String> permissions, boolean all) {
                                    if (all) {
                                        toast("获取存储权限成功");
                                    }
                                }
                            });
                }
            }, delayMillis);

        } else if (viewId == R.id.btn_main_request_5) {

            XXPermissions.with(this)
                    .permission(Permission.REQUEST_INSTALL_PACKAGES)
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(List<String> permissions, boolean all) {
                            toast("获取安装包权限成功");
                        }
                    });

        } else if (viewId == R.id.btn_main_request_6) {

            XXPermissions.with(this)
                    .permission(Permission.SYSTEM_ALERT_WINDOW)
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(List<String> permissions, boolean all) {
                            toast("获取悬浮窗权限成功");
                        }
                    });

        } else if (viewId == R.id.btn_main_request_7) {

            XXPermissions.with(this)
                    .permission(Permission.NOTIFICATION_SERVICE)
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(List<String> permissions, boolean all) {
                            toast("获取通知栏权限成功");
                        }
                    });

        } else if (viewId == R.id.btn_main_request_8) {

            XXPermissions.with(this)
                    .permission(Permission.WRITE_SETTINGS)
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(List<String> permissions, boolean all) {
                            toast("获取系统设置权限成功");
                        }
                    });

        } else if (viewId == R.id.btn_main_app_details) {

            XXPermissions.startPermissionActivity(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == XXPermissions.REQUEST_CODE) {
            toast("检测到你刚刚从权限设置界面返回回来");
        }
    }

    public void toast(CharSequence text) {
        ToastUtils.show(text);
    }
}