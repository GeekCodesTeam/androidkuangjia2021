//package com.example.slbapplogin.winter;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//
//import com.blankj.utilcode.util.AppUtils;
//import com.blankj.utilcode.util.RegexUtils;
//import com.blankj.utilcode.util.SPUtils;
//import com.example.libbase.base.SlbBaseActivity;
//import com.example.slbapplogin.R;
//import com.example.slbapplogin.msg.YanzhengUtil;
//import com.haier.cellarette.baselibrary.toasts2.Toasty;
//import com.haier.cellarette.baselibrary.zothers.AutoHideInputMethodFrameLayout;
//
//public class SlbLoginTelActivity extends SlbBaseActivity implements View.OnClickListener, SLoginView, SSendVCodeView, SLoginOnlineView {
//
//    private AutoHideInputMethodFrameLayout afl1;
//    private LinearLayout ll1;
//    private TextView tv_right;
//    private TextView tv_center;
//    private EditText edt1;
//    private EditText edt2;
//    private Button tvHqyzm;
//    private Button tv1;
//
//    private SLoginPresenter presenter;
//    private SSendVCodePresenter presentercode;
//    private SLoginOnlinePresenter presenter_loginonline;
//    //
//    private String tempKey;
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_slblogin_tel;
//    }
//
//    @Override
//    protected void setup(@Nullable Bundle savedInstanceState) {
//        super.setup(savedInstanceState);
//        findview();
//        onclick();
//        donetwork();
//    }
//
//    private void donetwork() {
//        tempKey = getIntent().getStringExtra("tempKey");
//        tv_center.setText("绑定手机");
//
//        presenter = new SLoginPresenter();
//        presenter.onCreate(this);
//        presentercode = new SSendVCodePresenter();
//        presentercode.onCreate(this);
//        presenter_loginonline = new SLoginOnlinePresenter();
//        presenter_loginonline.onCreate(this);
//    }
//
//    private void onclick() {
//        tv_right.setOnClickListener(this);
//        tvHqyzm.setOnClickListener(this);
//        tv1.setOnClickListener(new OnMultiClickListener() {
//            @Override
//            public void onMultiClick(View v) {
//                // 登录bufen
//                if (!is_login()) {
//                    return;
//                }
//                String b = edt2.getText().toString().trim();
//                if (TextUtils.isEmpty(b)) {
//                    Toasty.normal(SlbLoginTelActivity.this, "请输入验证码").show();
//                    return;
//                }
//                denglu();
//            }
//        });
//
//    }
//
//    private void findview() {
//        afl1 = findViewById(R.id.afl1);
//        ll1 = findViewById(R.id.ll1);
//        tv_right = findViewById(R.id.tv_right);
//        tv_center = findViewById(R.id.tv_center);
//        edt1 = findViewById(R.id.edt1);
//        edt2 = findViewById(R.id.edt2);
//        tvHqyzm = findViewById(R.id.tv_hqyzm);
//        tv1 = findViewById(R.id.tv1);
//
//    }
//
//    @Override
//    public void onClick(View v) {
//        int i = v.getId();
//        if (i == R.id.tv_right) {
//            // 关闭bufen
//            no_denglu();
//        } else if (i == R.id.tv_hqyzm) {
//            // 获取验证码bufen
//            hqyzm();
//        } else {
//
//        }
//    }
//
//    private void onLoginSuccess() {
//        setResult(SlbLoginUtil2.LOGIN_RESULT_OK);
//        finish();
//    }
//
//    private void onLoginCanceled() {
//        setResult(SlbLoginUtil2.LOGIN_RESULT_CANCELED);
//        finish();
//    }
//
//    /**
//     * 不登录操作
//     */
//    private void donetloginno() {
//        //step 请求服务器成功后清除sp中的数据
////        SpUtils.get(this).get("", "");
//        onLoginCanceled();
//    }
//
//    private boolean is_login() {
//        String a = edt1.getText().toString().trim();
//        if (TextUtils.isEmpty(a)) {
//            Toasty.normal(this, "请输入您的手机号").show();
//            return false;
//        }
//        if (!RegexUtils.isMobileSimple(a)) {
//            Toasty.normal(this, "手机号格式不正确").show();
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * 获取验证码
//     */
//    private void hqyzm() {
//        if (!is_login()) {
//            return;
//        }
//        tv1.setEnabled(true);
////        tv1.setBackgroundResource(R.drawable.slb_btncomm_pressed2);
//        YanzhengUtil.startTime(60 * 1000, tvHqyzm);
//        //接口通了后执行下一步
//        presentercode.getSendVCodeData(edt1.getText().toString().trim());
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        presenter.onDestory();
//        presentercode.onDestory();
//        presenter_loginonline.onDestory();
//        YanzhengUtil.timer_des();
//        super.onDestroy();
//
//    }
//
//    /**
//     *
//     *
//     * ----------------------------------下面为业务逻辑--分割线------------------------------
//     *
//     */
//
//
//    /**
//     * 登录
//     */
//    private void denglu() {
////        Toasty.normal(this, "登录").show();
//        presenter.getLoginData(edt1.getText().toString().trim(), tempKey, edt2.getText().toString().trim());
//
//    }
//
//    /**
//     * 不登录 返回原来的
//     */
//    private void no_denglu() {
////        Toasty.normal(this, "不登录").show();
//        donetloginno();
////        startActivity(new Intent("hs.act.slbapp.index"));
//
//    }
//
//
//    @Override
//    public void OnLoginSuccess(SLoginUserInfoBean sLoginUserInfoBean, String tag) {
//        //step 请求服务器成功后清除sp中的数据
//        SPUtils.getInstance().put(CommonUtils.USER_TEL, sLoginUserInfoBean.getPbUser().getPhone());
//        SPUtils.getInstance().put(CommonUtils.USER_TOKEN, sLoginUserInfoBean.getToken());
//        presenter_loginonline.getLoginOnlineData("login");
//        SPUtils.getInstance().put(CommonUtils.USER_NAME, sLoginUserInfoBean.getPbUser().getNickName());
//        SPUtils.getInstance().put(CommonUtils.USER_IMG, sLoginUserInfoBean.getPbUser().getAvatar());
//        if (!sLoginUserInfoBean.getPbUser().isHasChild()) {
//            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.SlbLoginInfoActivity"));
//            finish();
//        } else {
//            onLoginSuccess();
//        }
//
//    }
//
//    @Override
//    public void OnLoginNodata(String s) {
//        Toasty.normal(this, s).show();
//    }
//
//    @Override
//    public void OnLoginFail(String s) {
//        Toasty.normal(this, s).show();
//    }
//
//    // 获取验证码bufen
//    @Override
//    public void OnSendVCodeSuccess(String s) {
//        Toasty.normal(this, s).show();
//    }
//
//    @Override
//    public void OnSendVCodeNodata(String s) {
//        Toasty.normal(this, s).show();
//    }
//
//    @Override
//    public void OnSendVCodeFail(String s) {
//        Toasty.normal(this, s).show();
//    }
//
//
//    @Override
//    public void OnLoginOnlineSuccess(String s) {
//        MyLogUtil.e("--presenter_loginonline-OnLoginOnlineSuccess-", s);
//
//    }
//
//    @Override
//    public void OnLoginOnlineNodata(String s) {
//        MyLogUtil.e("--presenter_loginonline-OnLoginOnlineNodata-", s);
//
//    }
//
//    @Override
//    public void OnLoginOnlineFail(String s) {
//        MyLogUtil.e("--presenter_loginonline-OnLoginOnlineFail-", s);
//
//    }
//}
