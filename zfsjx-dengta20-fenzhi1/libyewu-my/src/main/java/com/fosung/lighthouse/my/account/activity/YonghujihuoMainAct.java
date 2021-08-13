package com.fosung.lighthouse.my.account.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.fosung.lighthouse.manage.myapi.bean.AccountActivitionBean;
import com.fosung.lighthouse.manage.myapi.common.MyApiConstant;
import com.fosung.lighthouse.manage.myapi.presenter.AccountPresenter;
import com.fosung.lighthouse.manage.myapi.presenter.VercodePresenter;
import com.fosung.lighthouse.manage.myapi.view.EduResAccountView;
import com.fosung.lighthouse.manage.myapi.view.EduResVerCodeView;
import com.fosung.lighthouse.my.R;
import com.fosung.lighthouse.my.widget.MyPicVerCodeDialog;
import com.hjq.toast.ToastUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.zcolin.frame.app.BaseActivity;


public class YonghujihuoMainAct extends BaseActivity implements View.OnClickListener, EduResAccountView<AccountActivitionBean>, EduResVerCodeView, MyPicVerCodeDialog.PicVerCodeInterface {

    private Button btnNext;
    private TextView edtUsername, edtIdcard, edtPhone, edtVerCode, edtAuthorizationCode, tvGetVercode;
    private CountDownTimer timer = new MyCountDownTimer(60_000, 1000);

    private MyPicVerCodeDialog picVerCodeDialog;

    private AccountPresenter mPresenter;
    private VercodePresenter mVercodePresenter;

    private LoadingPopupView loadingPopup;

    private String phone;
    private String idcard;
    private String picCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_yonghujihuoact);
        setToolbarTitle("用户激活");

        findview();
        onclick();
        load();
    }

    private void findview() {
        edtUsername = findViewById(R.id.edt_yhjh_username);
        edtIdcard = findViewById(R.id.edt_yhjh_idcard);
        edtPhone = findViewById(R.id.edt_yhjh_phone);
        edtVerCode = findViewById(R.id.edt_ver_code);
        edtAuthorizationCode = findViewById(R.id.edt_yhjh_authorization_code);
        tvGetVercode = findViewById(R.id.tv_yhjh_getvercode);
        btnNext = findViewById(R.id.btn_yhjh_next);
    }

    private void onclick() {
        tvGetVercode.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }

    private void load() {
        mPresenter = new AccountPresenter(this);
        mVercodePresenter = new VercodePresenter(this);

        loadingPopup = (LoadingPopupView) new XPopup.Builder(this)
                .dismissOnBackPressed(false)
                .asLoading("验证码获取中");

        picVerCodeDialog = (MyPicVerCodeDialog) new XPopup.Builder(this)
                .enableDrag(false)
                .dismissOnTouchOutside(false)
                .asCustom(new MyPicVerCodeDialog(this, this));
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_yhjh_next) {
            String name = edtUsername.getText().toString();
            String verCode = edtVerCode.getText().toString();
            String authorizationCode = edtAuthorizationCode.getText().toString();
            if (TextUtils.isEmpty(name)) {
                ToastUtils.show("请输入姓名");
            } else if (TextUtils.isEmpty(verCode)) {
                ToastUtils.show("请输入验证码");
            } else if (TextUtils.isEmpty(authorizationCode)) {
                ToastUtils.show("请输入授权码");
            } else {
                loadingPopup.setTitle("验证中");
                loadingPopup.show();
                mPresenter.checkAccountActivition(phone, idcard, name, authorizationCode, verCode);
            }

        } else if (v.getId() == R.id.tv_yhjh_getvercode) {
            phone = edtPhone.getText().toString();
            idcard = edtIdcard.getText().toString();
            if (TextUtils.isEmpty(phone)) {
                ToastUtils.show("请输入手机号");
            } else if (TextUtils.isEmpty(idcard)) {
                ToastUtils.show("请输入身份证号");
            } else {
                picVerCodeDialog.show();
            }
        }
    }


    /**
     * 获取验证码按钮在发送60秒之后可以重新获取
     */
    @SuppressLint("HandlerLeak")
    class MyCountDownTimer extends CountDownTimer {

        MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tvGetVercode.setEnabled(false);
            tvGetVercode.setText(millisUntilFinished / 1000 + "S");
            tvGetVercode.setTextColor(getResources().getColor(R.color.gray_mid));
        }

        @Override
        public void onFinish() {
            tvGetVercode.setEnabled(true);
            tvGetVercode.setText("重新获取");
            tvGetVercode.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    @Override
    public void picverCode(@NonNull String picVerCode) {
        picCode = picVerCode;
        loadingPopup.setTitle("验证码获取中");
        loadingPopup.show();
        mVercodePresenter.getVerCode(MyApiConstant.GET_CODE_TYPE_REGISTER, picVerCode, edtPhone.getText().toString(), edtIdcard.getText().toString());
    }

    @Override
    public void OnEduResVerCodeSuccess() {
        loadingPopup.dismiss();
        timer.start();
    }

    @Override
    public void OnEduResVerCodeFail(String msg) {
        ToastUtils.show(msg);
    }

    @Override
    public void OnEduResCommonSuccess(AccountActivitionBean bean) {
        loadingPopup.dismiss();
        Intent intent = new Intent(YonghujihuoMainAct.this, YonghuJihuoTwoAct.class);
        intent.putExtra("name", edtUsername.getText().toString());
        intent.putExtra("picVerCode", picCode);
        intent.putExtra("idcard", idcard);
        intent.putExtra("phone", phone);
        intent.putExtra("verCode", edtVerCode.getText().toString());
        intent.putExtra("authorizationCode", edtAuthorizationCode.getText().toString());
        startActivity(intent);
    }

    @Override
    public void OnEduResCommonFail(String msg) {
        loadingPopup.dismiss();
        ToastUtils.show(msg);
    }

}