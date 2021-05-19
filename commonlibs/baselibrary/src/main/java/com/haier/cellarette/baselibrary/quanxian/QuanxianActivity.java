package com.haier.cellarette.baselibrary.quanxian;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.haier.cellarette.baselibrary.R;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

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
        int id = view.getId();
        if (id == R.id.btn_main_request_1) {
            XXPermissions.with(this)
                    .permission(Permission.CAMERA)
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(List<String> permissions, boolean all) {
                            if (all) {
                                toast("获取拍照权限成功");
                            }
                        }

                        @Override
                        public void onDenied(List<String> permissions, boolean never) {
                            if (never) {
                                toast("被永久拒绝授权，请手动授予拍照权限");
                                // 如果是被永久拒绝就跳转到应用权限系统设置页面
                                XXPermissions.startPermissionActivity(QuanxianActivity.this, permissions);
                            } else {
                                toast("获取拍照权限失败");
                            }
                        }
                    });
        } else if (id == R.id.btn_main_request_2) {
            XXPermissions.with(this)
                    .permission(Permission.RECORD_AUDIO)
                    .permission(Permission.Group.CALENDAR)
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(List<String> permissions, boolean all) {
                            if (all) {
                                toast("获取录音和日历权限成功");
                            } else {
                                toast("获取部分权限成功，但是部分权限未正常授予");
                            }
                        }

                        @Override
                        public void onDenied(List<String> permissions, boolean never) {
                            if (never) {
                                toast("被永久拒绝授权，请手动授予录音和日历权限");
                                // 如果是被永久拒绝就跳转到应用权限系统设置页面
                                XXPermissions.startPermissionActivity(QuanxianActivity.this, permissions);
                            } else {
                                toast("获取权限失败");
                            }
                        }
                    });
        } else if (id == R.id.btn_main_request_3) {
            XXPermissions.with(this)
                    .permission(Permission.Group.LOCATION)
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(List<String> permissions, boolean all) {
                            if (all) {
                                toast("获取定位权限成功");
                            }
                        }

                        @Override
                        public void onDenied(List<String> permissions, boolean never) {
                            if (never) {
                                toast("被永久拒绝授权，请手动授予定位权限");
                                // 如果是被永久拒绝就跳转到应用权限系统设置页面
                                XXPermissions.startPermissionActivity(QuanxianActivity.this, permissions);
                                return;
                            }

                            if (permissions.size() == 1 && Permission.ACCESS_BACKGROUND_LOCATION.equals(permissions.get(0))) {
                                toast("没有授予后台定位权限，请您选择\"始终允许\"");
                            } else {
                                toast("获取定位权限失败");
                            }
                        }
                    });
        } else if (id == R.id.btn_main_request_4) {
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

                                @Override
                                public void onDenied(List<String> permissions, boolean never) {
                                    if (never) {
                                        toast("被永久拒绝授权，请手动授予存储权限");
                                        // 如果是被永久拒绝就跳转到应用权限系统设置页面
                                        XXPermissions.startPermissionActivity(QuanxianActivity.this, permissions);
                                    } else {
                                        toast("获取存储权限失败");
                                    }
                                }
                            });
                }
            }, delayMillis);
        } else if (id == R.id.btn_main_request_5) {
            XXPermissions.with(this)
                    .permission(Permission.REQUEST_INSTALL_PACKAGES)
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(List<String> permissions, boolean all) {
                            toast("获取安装包权限成功");
                        }

                        @Override
                        public void onDenied(List<String> permissions, boolean never) {
                            toast("获取安装包权限失败，请手动授予权限");
                        }
                    });
        } else if (id == R.id.btn_main_request_6) {
            XXPermissions.with(this)
                    .permission(Permission.SYSTEM_ALERT_WINDOW)
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(List<String> permissions, boolean all) {
                            toast("获取悬浮窗权限成功");
                        }

                        @Override
                        public void onDenied(List<String> permissions, boolean never) {
                            toast("获取悬浮窗权限失败，请手动授予权限");
                        }
                    });
        } else if (id == R.id.btn_main_request_7) {
            XXPermissions.with(this)
                    .permission(Permission.NOTIFICATION_SERVICE)
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(List<String> permissions, boolean all) {
                            toast("获取通知栏权限成功");
                        }

                        @Override
                        public void onDenied(List<String> permissions, boolean never) {
                            toast("获取通知栏权限失败，请手动授予权限");
                        }
                    });
        } else if (id == R.id.btn_main_request_8) {
            XXPermissions.with(this)
                    .permission(Permission.WRITE_SETTINGS)
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(List<String> permissions, boolean all) {
                            toast("获取系统设置权限成功");
                        }

                        @Override
                        public void onDenied(List<String> permissions, boolean never) {
                            toast("获取系统设置权限失败，请手动授予权限");
                        }
                    });
        } else if (id == R.id.btn_main_app_details) {
            XXPermissions.startApplicationDetails(this);
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
        ToastUtils.showLong(text);
    }
}