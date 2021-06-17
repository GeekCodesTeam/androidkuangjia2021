package com.fosung.xuanchuanlan.setting.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.fosung.frameutils.http.ZHttp;
import com.fosung.frameutils.http.response.ZResponse;
import com.fosung.frameutils.util.NetworkUtil;
import com.fosung.frameutils.util.ToastUtil;
import com.fosung.xuanchuanlan.R;
import com.fosung.xuanchuanlan.common.app.ConfApplication;
import com.fosung.xuanchuanlan.mian.http.HttpUrlMaster;
import com.fosung.xuanchuanlan.mian.http.entity.LogoBean;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;


public class UserCenterActivity extends AppCompatActivity {

    private  ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);

        getlogo();

        Button back = (Button) findViewById(R.id.goback);
        back.setOnClickListener(onClick);

        LinearLayout userLayout = (LinearLayout) findViewById(R.id.layout_user);
        userLayout.setOnClickListener(onClick);

        LinearLayout wifiLayout = (LinearLayout) findViewById(R.id.wifi);
        wifiLayout.setOnClickListener(onClick);


    }

    private View.OnClickListener onClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.goback)
            {
                finish();
            }
            if (v.getId() == R.id.layout_user){
                if (logged()){
                    gotoLogout();

                }else{
                    Intent intent = new Intent(UserCenterActivity.this,LogoActivity.class);
                    startActivity(intent);
                }

            }

            if (v.getId() == R.id.wifi){
                startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
            }
        }
    };


    @Override
    protected void onResume() {
        super.onResume();

        //每次页面唤醒的时候刷新网络状况
        refreshNetInfo();

        if (logged()){
            //每次页面唤醒时刷新用户信息
            refreshUserInfo();
        }
    }

    //登录状态
    private boolean logged(){

        SharedPreferences sp = ConfApplication.APP_CONTEXT.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String userId = sp.getString("user_id",null);
        return userId != null;

    }

    //弹窗退出登录
    private void gotoLogout(){
        final AlertDialog.Builder alterDiaglog = new AlertDialog.Builder(UserCenterActivity.this);
        alterDiaglog.setTitle("退出登录");
        alterDiaglog.setMessage("是否退出登录");
        alterDiaglog.setPositiveButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences systemInfo = ConfApplication.APP_CONTEXT.getSharedPreferences("SystemInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor_systemInfo = systemInfo.edit();
                SharedPreferences userInfo = ConfApplication.APP_CONTEXT.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor_userInfo = userInfo.edit();
                editor_userInfo.clear();
                editor_systemInfo.remove("access_token");
                editor_systemInfo.remove("refresh_token");
                editor_systemInfo.remove("token_type");
                editor_systemInfo.commit();
                editor_userInfo.commit();
                refreshUserInfo();
            }
        });
        //消极的选择
        alterDiaglog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
//        //中立的选择
//        alterDiaglog.setNeutralButton("不生不死", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(MainActivity.this,"点击了不生不死",Toast.LENGTH_SHORT).show();
//            }
//        });

        //显示
        alterDiaglog.show();



    }


    //刷新界面
    private void refreshUserInfo(){
        SharedPreferences sp = ConfApplication.APP_CONTEXT.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String name = sp.getString("user_name",null);
        String orgName = sp.getString("user_orgName",null);
        String tel = sp.getString("user_tel",null);


        name = name != null ? name : "账户管理";
        orgName = orgName != null ? orgName : "";
        tel = tel != null ? tel : "登录后可使用相关业务模块";
        String telAndOrg = tel+"\n"+orgName;


        TextView text_name = (TextView) findViewById(R.id.text_name);
        TextView text_tel = (TextView) findViewById(R.id.text_org);
        text_name.setText(name.toString());
        text_tel.setText(telAndOrg.toString());

        LinearLayout userLayout = (LinearLayout) findViewById(R.id.layout_user);
//        userLayout.setClickable(false);

        TextView text_login = (TextView) findViewById(R.id.text_login);
        ImageView arrow_login = (ImageView) findViewById(R.id.arrow_login);
        text_login.setVisibility(View.INVISIBLE);
        arrow_login.setVisibility(View.INVISIBLE);

    }

    private void refreshNetInfo(){
        int netState = NetworkUtil.getConnectedType(this);
        String netStateStr = "未连接";
        String wifiStateStr = "未连接";
        switch (netState){
            case -1:
                break;
            case 0:
                break;
            case 1:

            {
                WifiManager wifiMgr = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
                int wifiState = wifiMgr.getWifiState();
                WifiInfo info = wifiMgr.getConnectionInfo();
                 wifiStateStr = info != null ? info.getSSID() : null;
            }
                break;
            case 2:
                break;
            case 3:
                netStateStr = "已连接";
                break;
        }
        TextView tx_wifi = (TextView) findViewById(R.id.tx_wifi);
        tx_wifi.setText(wifiStateStr);
        TextView tx_net = (TextView) findViewById(R.id.tx_net);
        tx_net.setText(netStateStr);
    }

    //获取左上角logo
    private void getlogo() {
        logo = (ImageView) findViewById(R.id.logo);

        SharedPreferences usersp = ConfApplication.APP_CONTEXT.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences sp = ConfApplication.APP_CONTEXT.getSharedPreferences("SystemInfo", Context.MODE_PRIVATE);
        String user_id = usersp.getString("user_id", "");//yong户唯一标识
        String orgCode = usersp.getString("user_orgCode", "");//yong户唯一标识
        String machineMac = sp.getString("machineMac", "");//yong户唯一标识
        String machineId = sp.getString("machineId", "");//盒子ID
        Map<String, String> logomap = new HashMap<String, String>();
        logomap.put("machineId", machineId);
        logomap.put("machineCode", "12345678");
        logomap.put("machineMac", machineMac);
        logomap.put("orgCode", orgCode);
        ZHttp.post(HttpUrlMaster.GRANTLOGO, logomap, new ZResponse<LogoBean>(LogoBean.class) {
            @Override
            public void onSuccess(Response response, LogoBean resObj) {
                if (resObj != null && resObj.data != null) {
                    if (!TextUtils.isEmpty(resObj.data.get(0).imagUrl)) {
                        Glide.with(UserCenterActivity.this)
                                .load(resObj.data.get(0).imagUrl)
                                .placeholder(R.drawable.logo) //设置资源加载过程中的占位符
                                .error(R.drawable.logo)
                                .into(logo);
                    }
                }
            }

            @Override
            public void onError(int code, String error) {
                super.onError(code, error);
                ToastUtil.toastLong("失败"+error);
            }
        });
    }


}