package com.fosung.lighthouse.my.account.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fosung.lighthouse.my.R;
import com.zcolin.frame.app.BaseActivity;


public class YonghujihuoMainAct extends BaseActivity implements View.OnClickListener {

    private Button btnNext;
    private TextView edtUsername, edtIdcard, edtPhone, edtVerCode, edtAuthorizationCode, tvGetVercode;
    private CountDownTimer timer = new MyCountDownTimer(60_000, 1000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_yonghujihuoact);
        findview();
        onclick();
        donetwork();
    }

    private void findview() {
        setToolbarTitle("用户激活");

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

    private void donetwork() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_yhjh_next) {
//            Intent intent = new Intent(Intent.ACTION_VIEW,
//                    Uri.parse("dataability://cs.znclass.com/" +
//                            "com.fosung.lighthouse.yonghujihuo.hs.act.slbapp.YonghuJihuoTwoAct?query3=" + "ssss"));
//            startActivity(intent);
            startActivity(new Intent(YonghujihuoMainAct.this,UserLoginAct.class));
        } else if (v.getId() == R.id.tv_yhjh_getvercode) {
            timer.start();
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
}