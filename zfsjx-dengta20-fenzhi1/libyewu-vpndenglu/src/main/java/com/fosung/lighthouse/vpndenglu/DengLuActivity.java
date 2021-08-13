package com.fosung.lighthouse.vpndenglu;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.AppUtils;
import com.fosung.lighthouse.manage.common.utils.CommonModel;
import com.fosung.lighthouse.manage.myapi.bean.UserLoginBean;
import com.fosung.lighthouse.manage.myapi.presenter.AccountPresenter;
import com.fosung.lighthouse.manage.myapi.view.EduResAccountTwoView;
import com.fosung.lighthouse.manage.myapi.view.EduResAccountView;
import com.fosung.lighthouse.vpndenglu.widget.VpnPicVerCodeDialog;
import com.haier.cellarette.libretrofit.common.ResponseSlbBean1;
import com.hjq.toast.ToastUtils;
import com.lxj.xpopup.XPopup;
import com.zcolin.frame.app.ActivityParam;
import com.zcolin.frame.app.BaseActivity;

/**
 * VPN登录
 */
@ActivityParam(isShowToolBar = false)
public class DengLuActivity extends BaseActivity implements EduResAccountView<ResponseSlbBean1<?>>, VpnPicVerCodeDialog.PicVerCodeInterface, EduResAccountTwoView {
    private AppCompatEditText edt_idcard, edt_pass;
    private Button btn_login;
    private AccountPresenter mPresenter;
    private VpnPicVerCodeDialog mPicverCodeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_vpn_login);
        edt_idcard = findViewById(R.id.edt_idcard);//用户名
        edt_pass = findViewById(R.id.edt_pass);//密码
        btn_login = findViewById(R.id.btn_login);//登录

        edt_idcard.setText(CommonModel.Companion.get().getVpnName());
        edt_pass.setText(CommonModel.Companion.get().getVpnPass());
        //点击登录
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                denglu();
            }
        });

        mPresenter = new AccountPresenter(this, this);
        mPicverCodeDialog = (VpnPicVerCodeDialog) new XPopup.Builder(this)
                .enableDrag(false)
                .dismissOnTouchOutside(false)
                .asCustom(new VpnPicVerCodeDialog(this, this));
    }

    //登录方法
    private void denglu() {
        String s_idcard = edt_idcard.getText().toString();//用户名
        String s_pass = edt_pass.getText().toString();//密码

        if (TextUtils.isEmpty(s_idcard) || TextUtils.isEmpty(s_pass)) {
            ToastUtils.show("请输入账号和密码");
        } else {
            mPicverCodeDialog.show();
        }

    }

    @Override
    public void picverCode(@NonNull String picVerCode) {
        showProgressDialog("登录中...");
        mPresenter.checkVpnBind(edt_idcard.getText().toString(), picVerCode);
    }

    @Override
    public void OnEduResCommonSuccess(ResponseSlbBean1<?> bean) {
        CommonModel.Companion.get().setVpnName(edt_idcard.getText().toString());
        CommonModel.Companion.get().setVpnPass(edt_pass.getText().toString());
        mPresenter.vpnBindUserInfo(edt_idcard.getText().toString(), edt_pass.getText().toString());
    }

    @Override
    public void OnEduResCommonFail(String msg) {
        hideProgressDialog();
        if (msg.equals("设备未绑定")) {
            CommonModel.Companion.get().setVpnName(edt_idcard.getText().toString());
            CommonModel.Companion.get().setVpnPass(edt_pass.getText().toString());
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("dataability://cs.znclass.com/" + AppUtils.getAppPackageName() + ".hs.act.slbapp.UserLoginAct?vpn_account=" + edt_idcard.getText().toString()));
            startActivity(intent);
            finish();
        } else {
            ToastUtils.show(msg);
        }
    }

    @Override
    public void onEduResVpnUserInfoSuccess(UserLoginBean bean) {
        hideProgressDialog();
        CommonModel.Companion.get().setToken(bean.data.accessToken);
        CommonModel.Companion.get().setUsreInfo(bean.data);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("dataability://cs.znclass.com/" + AppUtils.getAppPackageName() + ".hs.act.slbapp.ShouyeActivity"));
        startActivity(intent);
        finish();
    }

    @Override
    public void onEduResVpnUserInfoFail(String msg) {
        hideProgressDialog();
        ToastUtils.show(msg);
    }

    @Override
    public String getIdentifier() {
        return "DengLuActivity";
    }

}