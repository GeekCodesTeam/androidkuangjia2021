package com.fosung.xuanchuanlan.xuanchuanlan.personalcenter.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.fosung.frameutils.http.ZHttp;
import com.fosung.frameutils.http.response.ZResponse;
import com.fosung.frameutils.util.ToastUtil;
import com.fosung.xuanchuanlan.R;
import com.fosung.xuanchuanlan.common.base.ActivityParam;
import com.fosung.xuanchuanlan.common.base.BaseActivity;
import android.widget.Toast;
import com.fosung.xuanchuanlan.mian.http.HttpUrlMaster;
import com.fosung.xuanchuanlan.setting.http.LoginTokenReply;
import com.fosung.xuanchuanlan.setting.http.UserInfoReply;
import com.fosung.xuanchuanlan.setting.http.entity.LoginApply;
import com.fosung.xuanchuanlan.common.app.ConfApplication;

import java.util.LinkedHashMap;

import okhttp3.Response;

@ActivityParam(isShowToolBar = false)
public class LoginActivity extends BaseActivity {

    private ImageView visImageView;
    private EditText pwdText;
    private EditText accountText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xcl_login);
        accountText = (EditText) findViewById(R.id.usernameEditText);
        pwdText = (EditText) findViewById(R.id.passwordEditText);
        pwdText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);//设置为密码样式
        visImageView = (ImageView) findViewById(R.id.visImageView);
        visImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pwdText.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD) && pwdText.length() > 0) {
                    pwdText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    pwdText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });


    }

    public void loginBtnClick(View btn){
        String account = accountText.getText()
                .toString()
                .trim();
        String password = pwdText.getText()
                .toString()
                .trim();

        if (TextUtils.isEmpty(account)) {
            Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        login(account, password);
    }

    public void cancelBtnClick(View btn){
        this.finish();
    }


    /**
     * 获取登录token
     */
    private void login(final String account, final String password) {


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

//                SharedPreferences userinfoSP = ConfApplication.APP_CONTEXT.getSharedPreferences("DTDJUserInfo", Context.MODE_PRIVATE);
//                SharedPreferences.Editor userinfoEditor = userinfoSP.edit();
//                userinfoEditor.putString("dtdj_username",account);
//                userinfoEditor.putString("dtdj_password",password);
//                userinfoEditor.commit();


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
//                app_Login(resObj.id, resObj.name);
                finish();
            }

            @Override
            public void onError(int code, String error) {
                ToastUtil.toastShort("未获取到用户信息"
                );
            }
        });

    }

}
