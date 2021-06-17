package com.fosung.xuanchuanlan.xuanchuanlan.personalcenter.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.fosung.frameutils.util.NetworkUtil;
import com.fosung.xuanchuanlan.R;
import com.fosung.xuanchuanlan.common.base.ActivityParam;
import com.fosung.xuanchuanlan.common.base.BaseActivity;
import com.fosung.xuanchuanlan.common.app.ConfApplication;
import com.fosung.xuanchuanlan.common.util.NetStatusMangerUtils;


@ActivityParam(isShowToolBar = false)
public class PersonalCenterActivity extends BaseActivity {

    private TextView wifiText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xcl_personalcenter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshNetInfo();

        if (logined()){
            //每次页面唤醒时刷新用户信息
            refreshUserInfo();
        }
    }


    //登录状态
    private boolean logined(){

        SharedPreferences sp = ConfApplication.APP_CONTEXT.getSharedPreferences("SystemInfo", Context.MODE_PRIVATE);
        String token = sp.getString("access_token",null);
        return token != null;

    }

    public static void userLogout(){
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
    }

    //弹窗退出登录
    private void logout(){
        final AlertDialog.Builder alterDiaglog = new AlertDialog.Builder(this);
        alterDiaglog.setTitle("退出登录");
        alterDiaglog.setMessage("是否退出登录");
        alterDiaglog.setPositiveButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userLogout();
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


        TextView text_name = (TextView) findViewById(R.id.titleTextView1);
        text_name.setText(name.toString());
//        TextView text_tel = (TextView) findViewById(R.id.text_org);
//        text_tel.setText(telAndOrg.toString());

//        LinearLayout userLayout = (LinearLayout) findViewById(R.id.layout_user);
//        userLayout.setClickable(false);

        TextView text_login = (TextView) findViewById(R.id.detailTextView1);
        ImageView arrow_login = (ImageView) findViewById(R.id.arrowImageView1);
        if (logined()){
            text_login.setVisibility(View.GONE);
            arrow_login.setVisibility(View.INVISIBLE);
        }else {
            text_login.setVisibility(View.VISIBLE);
            arrow_login.setVisibility(View.VISIBLE);
        }
    }


    public void onItemClick(View view) {
        switch (view.getId()){
            case R.id.RL1:{
                if (logined()){
                    logout();
                }else{
                    Intent intent = new Intent(PersonalCenterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }


                break;
            }
            case R.id.RL2:{
                startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
                break;
            }
            case R.id.RL3:{
                break;
            }
        }

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
                wifiStateStr = NetStatusMangerUtils.getWifiName(PersonalCenterActivity.this);
            }
            break;
            case 2:
                break;
            case 3:
                break;
        }

        if (NetStatusMangerUtils.getEthernetConnectState(PersonalCenterActivity.this) == NetworkInfo.DetailedState.CONNECTED){
            netStateStr = "已连接";
        }

        TextView tx_wifi = (TextView) findViewById(R.id.tx_wifi);
        tx_wifi.setText(wifiStateStr);
        TextView tx_net = (TextView) findViewById(R.id.tx_net);
        tx_net.setText(netStateStr);
    }

}
