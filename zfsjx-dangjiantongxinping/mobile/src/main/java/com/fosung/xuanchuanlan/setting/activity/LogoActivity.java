package com.fosung.xuanchuanlan.setting.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fosung.frameutils.http.ZHttp;
import com.fosung.frameutils.http.response.ZResponse;
import com.fosung.frameutils.util.ToastUtil;
import com.fosung.xuanchuanlan.R;
import com.fosung.xuanchuanlan.common.app.ConfApplication;
import com.fosung.xuanchuanlan.common.base.ActivityParam;
import com.fosung.xuanchuanlan.common.base.BaseActivity;
import com.fosung.xuanchuanlan.setting.http.UserInfoReply;
import com.fosung.xuanchuanlan.setting.http.entity.LoginApply;

import com.fosung.xuanchuanlan.mian.http.HttpUrlMaster;
import com.fosung.xuanchuanlan.setting.http.LoginTokenReply;
//import com.yuntongxun.plugin.fullconf.wrap.WrapManager;
//import com.yuntongxun.plugin.okhttp.pbsbase.Profile;
//import com.yuntongxun.plugin.okhttp.pbsbase.YHResponse;


import java.util.LinkedHashMap;

import okhttp3.Response;

@ActivityParam(isShowToolBar = false)

public class LogoActivity extends BaseActivity {

    private InternalReceiver internalReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        final EditText et_account = (EditText) findViewById(R.id.et_account);
        final EditText et_password = (EditText) findViewById(R.id.et_password);
        final View back = findViewById(R.id.weather);
        Button btn_login = (Button) findViewById(R.id.btn_login);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = et_account.getText()
                        .toString()
                        .trim();
                String password = et_password.getText()
                        .toString()
                        .trim();

                if (TextUtils.isEmpty(account)) {
                    Toast.makeText(LogoActivity.this, "请输入账号", Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LogoActivity.this, "请输入密码", Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                login(account, password);

            }
        };
        btn_login.setOnClickListener(listener);

    }

    /**
     * 获取登录token
     */
    private void login(String account, String password) {

        LoginApply apply = new LoginApply();
        apply.client_id = "box_client";
        apply.client_secret = "box_1qaz2wsx!@#";
        apply.grant_type = "password";
        apply.username = account;
        apply.password = password;

        ZHttp.post(HttpUrlMaster.USER_LOGIN, apply, new ZResponse<LoginTokenReply>(LoginTokenReply.class) {
            @Override
            public void onSuccess(Response response, LoginTokenReply resObj) {

                SharedPreferences sp = ConfApplication.APP_CONTEXT.getSharedPreferences("SystemInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("access_token", resObj.access_token);
                editor.putString("refresh_token", resObj.refresh_token);
                editor.putString("token_type", resObj.token_type);
                editor.commit();
                if (!TextUtils.isEmpty(resObj.access_token)) {
                    applyUserInfo(resObj.access_token);
                } else {
                    ToastUtil.toastShort("登录失败");
                }
            }

            @Override
            public void onError(int code, String error) {
                ToastUtil.toastShort("用户名或密码错误，请重试");
            }
        });

    }


    /**
     * 获取登录信息
     */
    private void applyUserInfo(String access_token) {

        LinkedHashMap header = new LinkedHashMap();
        header.put("Authorization", "Bearer " + access_token);

        ZHttp.postWithHeader(HttpUrlMaster.USER_INFO, header, null, new ZResponse<UserInfoReply>(UserInfoReply.class) {
            @Override
            public void onSuccess(Response response, UserInfoReply resObj) {
                // ToastUtil.toastShort("获取成功");
                SharedPreferences sp = ConfApplication.APP_CONTEXT.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("user_name", resObj.name);
                editor.putString("user_id", resObj.id);
                editor.putString("user_tel", resObj.telephone);
                if (resObj.orgs != null && resObj.orgs.size() > 0) {
                    UserInfoReply.OrgBean orgBean = resObj.orgs.get(0);
                    editor.putString("user_orgCode", orgBean.code);
                    editor.putString("user_orgSource", orgBean.source);
                    editor.putString("user_orgName", orgBean.name);
                    editor.putString("user_orgOutId", orgBean.outId);
                    editor.putString("user_orgId", orgBean.id);
                }
                editor.commit();
                app_Login(resObj.id, resObj.name);

            }

            @Override
            public void onError(int code, String error) {
                ToastUtil.toastShort("未获取到用户信息"
                );
            }
        });

    }

    public void app_Login(String userid, String NickName) {
//        YHResponse yhResponse = new YHResponse();
////        环境信息
//        yhResponse.setAppId("8a2af988536458c301537d7197320004");
//        yhResponse.setAppToken("0f26f16e4a8d4680a586c6eb2a9f4e03");
//        yhResponse.setConnectorAddrs(new String[]{"62.234.188.158:8085"});
//        yhResponse.setFileServerAddrs(new String[]{"62.234.188.158:8090"});
//        yhResponse.setLvsAddrs(new String[]{"62.234.188.158:8888"});
//        yhResponse.setFaceAddrs("http://62.234.47.181:8882");
//        yhResponse.setFsAddrs("http://27.221.110.100:9200");
////        用户信息
//        Profile profile = new Profile();
//        profile.setUserId(userid);
//        profile.setNickName(NickName);
//        yhResponse.setProfile(profile);
//        WrapManager.getInstance().app_Login(yhResponse);
//
//        if (internalReceiver == null) {
//            internalReceiver = new InternalReceiver();
//            registerReceiver(internalReceiver, WrapManager.getInstance().getIntentFilter());
//        }
    }

    private class InternalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null || intent.getAction() == null) {
                LogoActivity.this.finish();
                return;
            }
//            if (intent.getAction().equals(WrapManager.ACTION_SDK_CONNECT)) {
//                if (WrapManager.getConnectState() == WrapManager.CONNECT_SUCCESS) {
//                    LogoActivity.this.finish();
//                    //是否登录成功
//                } else {
//                    LogoActivity.this.finish();
//                }
//            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (internalReceiver != null) {
            unregisterReceiver(internalReceiver);
        }
    }
}
