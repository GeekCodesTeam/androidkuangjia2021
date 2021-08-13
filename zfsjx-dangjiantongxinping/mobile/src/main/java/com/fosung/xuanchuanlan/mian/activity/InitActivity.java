package com.fosung.xuanchuanlan.mian.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.fosung.frameutils.http.ZHttp;
import com.fosung.frameutils.http.response.ZResponse;
import com.fosung.frameutils.util.ToastUtil;
import com.fosung.xuanchuanlan.R;
import com.fosung.xuanchuanlan.common.app.ConfApplication;
import com.fosung.xuanchuanlan.common.base.ActivityParam;
import com.fosung.xuanchuanlan.common.base.BaseActivity;
import com.fosung.xuanchuanlan.common.util.NetStatusMangerUtils;
import com.fosung.xuanchuanlan.mian.http.HttpUrlMaster;
import com.fosung.xuanchuanlan.mian.http.entity.Registeredcode;
import com.fosung.xuanchuanlan.xuanchuanlan.main.activity.XCLMainActivity;
import com.fosung.xuanchuanlan.xuanchuanlan.personalcenter.activity.PersonalCenterActivity;
import com.fosung.xuanchuanlan.xuanchuanlan.tools.LongClickUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import okhttp3.Response;

@ActivityParam(isFullScreen = true)
public class InitActivity extends BaseActivity {
    private ImageView init_page;
    private EditText registercode;
    private TextView enter_setting;
    private TextView enter_brower;
    private TextView enter_files;
    private Button comit;
    private LinearLayout layoutregister;
    private String recode, machineMac, machineCode;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!TextUtils.isEmpty(machineMac)) {
                intindate(machineMac);
            } else {
                getDeviceUUIDDefault();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        init_page = (ImageView) findViewById(R.id.init_page);
        registercode = (EditText) findViewById(R.id.registercode);
        layoutregister = (LinearLayout) findViewById(R.id.register);
        enter_setting = (TextView) findViewById(R.id.enter_setting);
        enter_brower = (TextView) findViewById(R.id.enter_brower);
        enter_files = (TextView) findViewById(R.id.enter_files);
        comit = (Button) findViewById(R.id.comit);
        SharedPreferences sp = ConfApplication.APP_CONTEXT.getSharedPreferences("SystemInfo", Context.MODE_PRIVATE);
        machineMac = sp.getString("machineMac", "");//客户端唯一标识


        comit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recode = registercode.getText().toString();
                if (TextUtils.isEmpty(recode)) {
                    ToastUtil.toastLong("注册码不能为空");
                    return;
                }

                register(recode, machineMac);
            }
        });

        LongClickUtils.setLongClick(handler, enter_setting, 3000, new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
                return false;
            }
        });

        LongClickUtils.setLongClick(handler, enter_brower, 3000, new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("https://www.dtdjzx.gov.cn/");
                intent.setData(content_url);
                startActivity(intent);
                return false;
            }
        });

        LongClickUtils.setLongClick(handler, enter_files, 3000, new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("com.android.rockchip", "com.android.rockchip.RockExplorer");
                startActivity(intent);
                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!NetStatusMangerUtils.isNetworkConnected(InitActivity.this)) {
            AlertDialog.Builder alterDialog = new AlertDialog.Builder(this);
            alterDialog.setTitle("设备没有联网！");
            alterDialog.setMessage("设备需要联网使用，是否进入WiFi设置？");
            alterDialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
                }
            });
            //消极的选择
            alterDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
//        //中立的选择
//        alterDialog.setNeutralButton("不生不死", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(MainActivity.this,"点击了不生不死",Toast.LENGTH_SHORT).show();
//            }
//        });
            //显示
            alterDialog.show();
            return;
        }
        if (init_page.getVisibility() == View.VISIBLE) {
            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable, 3000);
        }
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    //检查盒子是否已激活
    private void intindate(String machineMac) {
        Map<String, String> logomap = new HashMap<String, String>();
        logomap.put("machineCode", "");
        logomap.put("machineMac", machineMac);
        LogUtils.e("sssss",machineMac);
        ZHttp.post(HttpUrlMaster.CHECKBOX, logomap, new ZResponse<Registeredcode>(Registeredcode.class) {
            @Override
            public void onSuccess(Response response, Registeredcode resObj) {
                if (resObj != null && resObj.data != null) {
                    if (resObj.data.result) {
                        SharedPreferences sp = ConfApplication.APP_CONTEXT.getSharedPreferences("SystemInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("machineId", resObj.data.id);
                        editor.putString("grantId", resObj.data.grantId);
                        editor.commit();
                        Intent intent = new Intent(InitActivity.this, XCLMainActivity.class);
                        startActivity(intent);
//                        Intent intent = new Intent(InitActivity.this, PreviewActivity.class);
//                        startActivity(intent);
                        InitActivity.this.finish();
                    } else {
                        layoutregister.setVisibility(View.VISIBLE);
                        init_page.setVisibility(View.GONE);
                        SharedPreferences sp = ConfApplication.APP_CONTEXT.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("user_name", "");
                        editor.putString("user_id", "");
                        editor.putString("user_tel", "");
                        editor.putString("user_orgCode", "");
                        editor.putString("user_orgId", "");
                        editor.putString("user_orgName", "");
                        editor.commit();
                    }
                }
            }

            @Override
            public void onError(int code, String error) {
                super.onError(code, error);
//                ToastUtil.toastLong(error + "");
                ToastUtils.showLong(error + "");
            }
        });
    }

    private void register(String code, String machineMac) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("registerCode", code);
        map.put("machineCode", "123456");
        map.put("machineName", "fosungbox");
        map.put("machineMac", machineMac);
        map.put("model", android.os.Build.MODEL);
        ZHttp.post(HttpUrlMaster.REGISTERCODE, map, new ZResponse<Registeredcode>(Registeredcode.class, this) {
            @Override
            public void onSuccess(Response response, Registeredcode resObj) {
                if (resObj != null && resObj.data != null) {
                    if (resObj.data.result) {
                        SharedPreferences sp = ConfApplication.APP_CONTEXT.getSharedPreferences("SystemInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("machineId", resObj.data.id);
                        editor.putString("grantId", resObj.data.grantId);
                        editor.commit();
                        PersonalCenterActivity.userLogout();

                        Intent intent = new Intent(InitActivity.this, XCLMainActivity.class);
                        startActivity(intent);
                        InitActivity.this.finish();
                    } else {
                        ToastUtil.toastLong(resObj.getErrorMessage() + "");
                    }
                }
            }

            @Override
            public void onError(int code, String error) {
                super.onError(code, error);
                ToastUtil.toastLong(error + "");
            }
        });
    }

    public void getDeviceUUIDDefault() {
        String deviceUUID = deviceUUID();
        if (deviceUUID != null) {
            SharedPreferences sp = ConfApplication.APP_CONTEXT.getSharedPreferences("SystemInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();

            editor.putString("machineMac", deviceUUID);
            editor.commit();
            intindate(deviceUUID);
        }


    }


    @Override
    public void onBackPressed() {
        //do nothing
    }


    public String deviceUUID() {


        String mac = "";
        WifiManager wifi = (WifiManager) ConfApplication.APP_CONTEXT.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = null;
        try {
            info = wifi.getConnectionInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        mac = info.getMacAddress();
        mac = DeviceUtils.getMacAddress();
        Log.e("ssssssss111",mac.toUpperCase(Locale.ENGLISH));
        if (TextUtils.isEmpty(mac) || mac.equals("02:00:00:00:00:00")) { //6.0以上mac地址是02:00:00:00:00:00
            mac = android.os.Build.SERIAL;
        }
        if (TextUtils.isEmpty(mac)) {
            mac = Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        }
        if (TextUtils.isEmpty(mac)||TextUtils.equals("unknow",mac)) {
            mac = UUID.randomUUID().toString();
        }
        return mac.toUpperCase(Locale.ENGLISH);
//        return "ZX1G42CPJD";
//        return "3HX0217919003605";//橙子华为
//        return "ZX1G42CPJD";//张明超 电脑模拟器
//        return "3GKL2721RW";//前台宣传栏设备
//        return "3GKL248ESG";//大平板测试设备
//        return "EMULATOR29X2X0X0";//汪凯 android模拟器
    }
}
