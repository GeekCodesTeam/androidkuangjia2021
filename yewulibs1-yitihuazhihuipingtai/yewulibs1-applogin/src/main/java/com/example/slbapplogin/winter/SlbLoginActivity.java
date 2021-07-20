package com.example.slbapplogin.winter;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.example.bizyewu2.bean.HLogin1111Bean;
import com.example.bizyewu2.bean.HLoginBean;
import com.example.bizyewu2.bean.HUserInfoBean;
import com.example.bizyewu2.presenter.HErweimaPresenter;
import com.example.bizyewu2.presenter.HLogin1111Presenter;
import com.example.bizyewu2.presenter.HUserInfoPresenter;
import com.example.bizyewu2.presenter.HYonghudengluPresenter;
import com.example.bizyewu2.view.HErweimaView;
import com.example.bizyewu2.view.HLogin1111View;
import com.example.bizyewu2.view.HUserInfoView;
import com.example.bizyewu2.view.HYonghudengluView;
import com.example.libbase.base.SlbBaseActivity;
import com.example.libbase.utils.CommonUtils;
import com.example.liblocations.LocListener;
import com.example.liblocations.LocUtil;
import com.example.liblocations.LocationBean;
import com.example.slbappcomm.utils.BanbenCommonUtils;
import com.example.slbapplogin.R;
import com.example.slbapplogin.msg.AppPermissionUtil;
import com.example.slbapplogin.msg.SMSBroadcastReceiver;
import com.example.slbapplogin.msg.YanzhengUtil;
import com.geek.libutils.SlbLoginUtil;
import com.geek.libutils.app.MyLogUtil;
import com.geek.libutils.data.MmkvUtils;
import com.google.android.material.textfield.TextInputLayout;
import com.haier.cellarette.baselibrary.btnonclick.view.BounceView;
import com.haier.cellarette.baselibrary.loading.ShowLoadingUtil;
import com.haier.cellarette.baselibrary.qcode.ExpandViewRect;
import com.haier.cellarette.baselibrary.widget.AlertView;
import com.haier.cellarette.baselibrary.widget.SmoothCheckBox;
import com.haier.cellarette.baselibrary.zothers.SpannableStringUtils;
import com.haier.cellarette.libwebview.hois2.HiosHelper;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class SlbLoginActivity extends SlbBaseActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks, HUserInfoView, HYonghudengluView, HErweimaView, HLogin1111View, SmoothCheckBox.OnCheckedChangeListener {

    private TextView tv_tips12;
    private ImageView iv1;
    private ImageView iv_bg1;
    private ImageView iv_xx;
    private ImageView iv_wx;
    private TextInputLayout textinputlayout1;
    private TextInputLayout textinputlayout2;
    private AppCompatEditText edt1;
    private AppCompatEditText edt2;
    private Button tv_hqyzm;
    private Button tv1;

    private HYonghudengluPresenter presenter;
    private HErweimaPresenter presentercode;
    private HUserInfoPresenter userInfoPresenter;
    //
//    private JPushDengluUtils jPushDengluUtils;
    private String openid;
    private String unionid;
    private String gender;
    private String nickName;
    private String avatar;
    private String other_login_name = "Wechat";
    private HandlerThread mHandlerThread;
    private Handler mHandler;

    private LinearLayout ll1;
    private LinearLayout ll2;
    private EditText ed1111;
    private EditText ed2222;
    private TextView tv1111;
    private TextView tv11111;
    private TextView tv2222;
    private TextView tv_error;
    private SmoothCheckBox scb_psw;//是否记住密码
    private ImageView iv1111;
    private ImageView iv_close1;
    private HLogin1111Presenter presenter1111;
    private String a;//输入-登录名
    private String b;//输入-密码
    private Boolean pswChecked = true;
    private AppCompatCheckBox cb_psw;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_slblogin2;
    }

    @Override
    protected void setup(@Nullable Bundle savedInstanceState) {
        super.setup(savedInstanceState);
        findview();
        onclick();
        donetwork();
        //
        MobclickAgent.onEvent(this, "Loginpage");
    }

    private void donetwork() {
        //
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            tv_tips12.setText(Html.fromHtml("登录即代表您同意" + "<font color=\"#4EC0FF\">用户协议</font>" + "和" + "<font color=\"#4EC0FF\">隐私政策</font>", Html.FROM_HTML_MODE_COMPACT));
//        } else {
//            tv_tips12.setText(Html.fromHtml("登录即代表您同意" + "<font color=\"#4EC0FF\">用户协议</font>" + "和" + "<font color=\"#4EC0FF\">隐私政策</font>"));
//        }
        tv_tips12.setText(SpannableStringUtils.getInstance(this.getApplicationContext())
                .getBuilder("首次登录将自动注册,且代表您已同意")
                .append("《用户协议》")
                .setClickSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
//                        HiosHelper.resolveAd(SlbLoginActivity.this, SlbLoginActivity.this, MmkvUtils.getInstance().get_common(CommonUtils.MMKV_serviceProtocol));
                        HiosHelper.resolveAd(SlbLoginActivity.this, SlbLoginActivity.this, "https://syzs.qq.com/campaign/3977.html");
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        ds.setColor(Utils.getApp().getResources().getColor(R.color.blue519AF4));
                        ds.setUnderlineText(false);
                    }
                })
                .append("和")
                .append("《隐私政策》")
                .setClickSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
//                Uri url = Uri.parse("http://blog.51cto.com/liangxiao");
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(url);
//                startActivity(intent);
//                        HiosHelper.resolveAd(SlbLoginActivity.this, SlbLoginActivity.this, MmkvUtils.getInstance().get_common(CommonUtils.MMKV_privacyPolicy));
                        HiosHelper.resolveAd(SlbLoginActivity.this, SlbLoginActivity.this, "https://syzs.qq.com/campaign/3977.html");
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        ds.setColor(Utils.getApp().getResources().getColor(R.color.blue519AF4));
                        ds.setUnderlineText(false);
                    }
                })
                .create());
        tv_tips12.setMovementMethod(LinkMovementMethod.getInstance());

        //
        if (TextUtils.equals("1", SPUtils.getInstance().getString(CommonUtils.MMKV_forceLogin))) {
            iv1.setVisibility(View.GONE);
        } else {
            iv1.setVisibility(View.GONE);
        }
        if (TextUtils.equals("YES", MmkvUtils.getInstance().get_common(CommonUtils.MMKV_USER_CHECKED))) {//如果记住密码，获取缓存中的账号密码并填充上，反之不填充
            scb_psw.setChecked(true);
            ed1111.setText(MmkvUtils.getInstance().get_common(CommonUtils.MMKV_USER_NAME));
            ed2222.setText(MmkvUtils.getInstance().get_common(CommonUtils.MMKV_USER_PSW));
        } else {
            scb_psw.setChecked(false);
        }
        presenter = new HYonghudengluPresenter();
        presenter.onCreate(this);
        presentercode = new HErweimaPresenter();
        presentercode.onCreate(this);

        // 获取资料bufen
        userInfoPresenter = new HUserInfoPresenter();
        userInfoPresenter.onCreate(this);
//        jPushDengluUtils.shouquan_cancel(other_login_name);
        set_sms2();
        //
        presenter1111 = new HLogin1111Presenter();
        presenter1111.onCreate(this);


    }

    private void onclick() {
        tv1111.setOnClickListener(this);
        tv2222.setOnClickListener(this);
        iv1111.setOnClickListener(this);
        tv11111.setOnClickListener(this);
        iv_close1.setOnClickListener(this);
//        scb_psw.setOnCheckedChangeListener(this);
        cb_psw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.getId() == R.id.cb_psw) {
                    if (isChecked) {
                        jzmm_xuanzhong();
                    } else {
                        jzmm_quxiao();
                    }
                }
            }
        });
//        scb_psw.setEnabled(true);
        cb_psw.setEnabled(true);
//        scb_psw.setChecked(true);
        cb_psw.setChecked(true);
        iv_xx.setOnClickListener(this);
        iv1.setOnClickListener(this);
        iv_wx.setOnClickListener(this);
        tv11111.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                BanbenCommonUtils.changeUrl2(SlbLoginActivity.this);
                return false;
            }
        });
        tv_hqyzm.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                // 获取验证码bufen  1
                hqyzm();
                // 自动填写短信   2
//                smsTask();
                //   3
//                PermissionsUtil.requestPermission(SlbLoginActivity.this, new com.github.dfqin.grantor.PermissionListener() {
//                    @Override
//                    public void permissionGranted(@NonNull String[] permissions) {
////                        Toast.makeText(MainActivity.this, "访问消息", Toast.LENGTH_LONG).show();
//                        // 获取验证码bufen
//                        hqyzm();
//                    }
//
//                    @Override
//                    public void permissionDenied(@NonNull String[] permissions) {
////                        Toast.makeText(MainActivity.this, "用户拒绝了读取消息权限", Toast.LENGTH_LONG).show();
////                        Toasty.normal(SlbLoginActivity.this, "您阻止了app读取您的短信，您可以自己手动输入验证码").show();
//                        edt2.requestFocus();
//                    }
//                }, new String[]{
//                        Manifest.permission.READ_SMS
//                }, false, null);
//                //  4
//                if (ContextCompat.checkSelfPermission(SlbLoginActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(SlbLoginActivity.this, new String[]{
//                            Manifest.permission.READ_SMS,
//                            Manifest.permission.RECEIVE_SMS
////                            Manifest.permission.READ_CONTACTS,
//                    }, REQUEST_PERMISSION_CODE);
//                } else {
//                    // 获取验证码bufen
//                    String aaaa = "";
//                    hqyzm();
//                }
            }
        });
        tv1.setOnClickListener(this);
//        jPushDengluUtils = new JPushDengluUtils(new OnResultInfoLitener() {
//            @Override
//            public void onResults(String platform, String toastMsg, String data) {
////                Toasty.normal(BaseApp.get(), platform + "---" + toastMsg + "---" + data).show();
//                // 微信登录成功bufen
//                JSONObject jsonObject = JSONObject.parseObject(data);
//                if (jsonObject == null) {
//                    return;
//                }
//                openid = (String) jsonObject.get("openid");
//                nickName = (String) jsonObject.get("nickname");
//                gender = (int) jsonObject.get("sex") + "";
//                avatar = (String) jsonObject.get("headimgurl");
//                unionid = (String) jsonObject.get("unionid");
////                presenter.getWeChatLoginData(openid, unionid, gender, nickName, avatar);
//            }
//        });
        edt1.addTextChangedListener(textWatcher);
        edt2.addTextChangedListener(textWatcher);
        ed1111.addTextChangedListener(textWatcher);
        ed2222.addTextChangedListener(textWatcher);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String a = edt1.getText().toString().trim();
            String b = edt2.getText().toString().trim();
            if (TextUtils.isEmpty(a)) {
                iv_xx.setVisibility(View.GONE);
            } else {
                iv_xx.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(a) && !TextUtils.isEmpty(b) && charSequence.length() > 0) {
                tv1.setEnabled(true);
                tv1.setBackgroundResource(R.drawable.btn_denglu2);
            } else {
                tv1.setEnabled(false);
                tv1.setBackgroundResource(R.drawable.btn_denglu2_enpressed);
            }
            if (TextUtils.isEmpty(ed1111.getText().toString().trim())) {
                ll1.setBackgroundResource(R.drawable.btn_pop_makerlayer11);
                tv_error.setText("");
            } else {
                ll1.setBackgroundResource(R.drawable.btn_pop_makerlayer22);
            }
            if (TextUtils.isEmpty(ed2222.getText().toString().trim())) {
                ll2.setBackgroundResource(R.drawable.btn_pop_makerlayer11);
                tv_error.setText("");
            } else {
                ll2.setBackgroundResource(R.drawable.btn_pop_makerlayer22);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void findview() {
        ll1 = findViewById(R.id.ll1);
        ll2 = findViewById(R.id.ll2);
        ed1111 = findViewById(R.id.ed1111);
        ed2222 = findViewById(R.id.ed2222);
        tv1111 = findViewById(R.id.tv1111);
        tv11111 = findViewById(R.id.tv11111);
        scb_psw = findViewById(R.id.scb_psw);
        cb_psw = findViewById(R.id.cb_psw);
        BounceView.addAnimTo(tv1111);
        tv2222 = findViewById(R.id.tv2222);
        tv_error = findViewById(R.id.tv_error);
        iv1111 = findViewById(R.id.iv1111);
        iv_close1 = findViewById(R.id.iv_close1);
        //
        tv_tips12 = findViewById(R.id.tv_tips12);
        iv1 = findViewById(R.id.iv1);
        iv_bg1 = findViewById(R.id.iv_bg1);
        iv_xx = findViewById(R.id.iv_xx);
        ExpandViewRect.expandViewTouchDelegate(iv_xx, 30, 30, 30, 30);
        iv_wx = findViewById(R.id.iv_wx);
        textinputlayout1 = findViewById(R.id.textinputlayout1);
        textinputlayout2 = findViewById(R.id.textinputlayout2);
        edt1 = findViewById(R.id.edt1);
        edt2 = findViewById(R.id.edt2);
        tv_hqyzm = findViewById(R.id.tv_hqyzm);
        tv1 = findViewById(R.id.tv1);
        tv1.setEnabled(false);
        tv1.setBackgroundResource(R.drawable.btn_denglu2_enpressed);
        //
        XXPermissions.with(this)
                .permission(Permission.ACCESS_COARSE_LOCATION)
                .permission(Permission.ACCESS_FINE_LOCATION)
                .permission(Permission.ACCESS_BACKGROUND_LOCATION)
                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
//                            ToastUtils.showLong("获取定位权限成功");
                            //
                            MyLogUtil.e("LocUtil22", "aaaa=======================");
                            set_location2();
                            MyLogUtil.e("LocUtil22", "bbbb=======================");
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
                            ToastUtils.showLong("被永久拒绝授权，请手动授予定位权限");
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(SlbLoginActivity.this, permissions);
                            return;
                        }

                        if (permissions.size() == 1 && Permission.ACCESS_BACKGROUND_LOCATION.equals(permissions.get(0))) {
                            ToastUtils.showLong("没有授予后台定位权限，请您选择\"始终允许\"");
                        } else {
                            ToastUtils.showLong("获取定位权限失败");
                        }
                    }
                });

    }

    private void set_location2() {
        LocUtil.getLocation(this, new LocListener() {

            @Override
            public void success(LocationBean model) {
                String lastLatitude = String.valueOf(model.getLatitude());
                String lastLongitude = String.valueOf(model.getLongitude());
                String aaaa = lastLatitude + "," + lastLongitude;
                MmkvUtils.getInstance().set_common("经纬度", JSON.toJSONString(model));
                MyLogUtil.e("LocUtil2", aaaa + "详细信息：" + JSON.toJSONString(model));
            }

            @Override
            public void fail(int msg) {
                String aaa = msg + "";
                MyLogUtil.e("LocUtil2", aaa);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private static final int RC_SMS_PERM = 122;

    @AfterPermissionGranted(RC_SMS_PERM)
    private void smsTask() {
        if (EasyPermissions.hasPermissions(SlbLoginActivity.this, Manifest.permission.READ_SMS)) {
            // Have permission, do the thing!
//            Toast.makeText(SlbLoginActivity.this, "TODO: SMS things", Toast.LENGTH_LONG).show();
            // 获取验证码bufen
            hqyzm();
        } else {
            // Request one permission
//            Toasty.normal(SlbLoginActivity.this, "您阻止了app读取您的短信，您可以自己手动输入验证码").show();
//           EasyPermissions.requestPermissions(SlbLoginActivity.this, "合象阅读正在尝试读取短信", RC_SMS_PERM, Manifest.permission.READ_SMS);
            final AlertView dialog = new AlertView(this, "温馨提示", "需要开启读取短信相关的权限",
                    "取消", "确定");
            dialog.show();
            dialog.setClicklistener(new AlertView.ClickListenerInterface() {
                                        @Override
                                        public void doLeft() {
                                            dialog.dismiss();
                                        }

                                        @Override
                                        public void doRight() {
                                            AppPermissionUtil.getInstance(getApplicationContext()).startAppSettings();
                                            dialog.dismiss();

                                        }
                                    }
            );
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        MyLogUtil.d("SMS1", "onPermissionsGranted:" + requestCode + ":" + perms.size());
        // 获取验证码bufen
        hqyzm();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        MyLogUtil.d("SMS1", "onPermissionsDenied:" + requestCode + ":" + perms.size());
//        Toasty.normal(SlbLoginActivity.this, "您阻止了app读取您的短信，您可以自己手动输入验证码").show();
        edt2.requestFocus();
    }

    @Override
    protected void onActResult(int requestCode, int resultCode, Intent data) {
        super.onActResult(requestCode, resultCode, data);
        if (requestCode == 10016) {
            // 获取验证码bufen
            hqyzm();
        }
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv1) {
            // 登录bufen
            //
            MobclickAgent.onEvent(this, "Loginpage_button");
            if (!is_login()) {
                return;
            }
            String b = edt2.getText().toString().trim();
            if (TextUtils.isEmpty(b)) {
                ToastUtils.showLong("请输入验证码");
//                YanzhengUtil.showError(textinputlayout2, "请输入验证码");
                return;
            }
            denglu();
        } else if (i == R.id.iv1) {
            // 关闭bufen
            onBackPressed();
        } else if (i == R.id.iv_wx) {
            // 微信登录
            wxdl();
        } else if (i == R.id.iv_xx) {
            // XX
            edt1.setText("");
        } else if (i == R.id.tv1111) {
            // 登录
            a = ed1111.getText().toString().trim();
            b = ed2222.getText().toString().trim();
            if (TextUtils.isEmpty(a)) {
                ToastUtils.showLong("请输入用户名");
                tv_error.setText("请输入用户名");
                return;
            }
            if (TextUtils.isEmpty(b)) {
                ToastUtils.showLong("请输入密码");
                tv_error.setText("请输入密码");
                return;
            }
            presenter1111.get_login1111(a, b);
        } else if (i == R.id.iv_close1) {
            // 关闭bufen
            System.exit(0);
        } else if (i == R.id.iv1111) {
            YanzhengUtil.set_mima_vis(ed2222, iv1111, R.mipmap.iconlogin6, R.mipmap.iconlogin5);
        } else {

        }
    }

    private void onLoginSuccess() {
        setResult(SlbLoginUtil.LOGIN_RESULT_OK);
        if (ActivityUtils.getActivityList().size() == 1) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ShouyeActivity"));
        }
        finish();
    }

    private void onLoginCanceled() {
        setResult(SlbLoginUtil.LOGIN_RESULT_CANCELED);
        if (ActivityUtils.getActivityList().size() == 1) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ShouyeActivity"));
//            System.exit(0);
        }
        finish();
    }

    /**
     * 不登录操作
     */
    private void donetloginno() {
        //step 请求服务器成功后清除sp中的数据
//        SpUtils.get(this).get("", "");
        onLoginCanceled();
    }

    private boolean is_login() {
        String a = edt1.getText().toString().trim();
        if (TextUtils.isEmpty(a)) {
            ToastUtils.showLong("请输入您的手机号");
//            YanzhengUtil.showError(textinputlayout1, "请输入您的手机号");
            return false;
        }
        if (!RegexUtils.isMobileSimple(a)) {
            ToastUtils.showLong("手机号格式不正确");
//            YanzhengUtil.showError(textinputlayout1, "请输入您的手机号");
            return false;
        }
        return true;
    }

    /**
     * 获取验证码
     */
    private void hqyzm() {
        if (!is_login()) {
            return;
        }
//        tv1.setEnabled(true);
//        tv1.setBackgroundResource(R.drawable.slb_btncomm_pressed2);
        YanzhengUtil.startTime(60 * 1000, tv_hqyzm);
        //接口通了后执行下一步
        presentercode.get_erweima(edt1.getText().toString().trim());

    }

    private SMSBroadcastReceiver mSMSBroadcastReceiver = new SMSBroadcastReceiver();

    private void set_sms2() {
        // 注册广播
        IntentFilter intentFilter = new IntentFilter(SMSBroadcastReceiver.SMS_RECEIVED_ACTION);
        // 设置优先级
        intentFilter.setPriority(Integer.MAX_VALUE);
        registerReceiver(mSMSBroadcastReceiver, intentFilter);
        mSMSBroadcastReceiver.setOnReceiveSMSListener(new SMSBroadcastReceiver.OnReceiveSMSListener() {
            @Override
            public void onReceived(String message) {
                edt2.setText(message + "");
            }
        });
    }

    @Override
    protected void onDestroy() {
        presenter1111.onDestory();
        presenter.onDestory();
        presentercode.onDestory();
        userInfoPresenter.onDestory();
        YanzhengUtil.timer_des();
//        jPushDengluUtils.ondes();
        SPUtils.getInstance().put(CommonUtils.APP_VERSION_CODE, AppUtils.getAppVersionCode());
        if (mHandler != null) {
            mHandlerThread.quit();
            mHandler.removeCallbacksAndMessages(null);
        }
        unregisterReceiver(mSMSBroadcastReceiver);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
//            if (TextUtils.equals(hSettingBean.getForceLogin(), "1")) { // 强制登录bufen
//        MmkvUtils.getInstance().get_common(CommonUtils.MMKV_forceLogin);
//        if (true) {
//            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ShouyeActivity"));
//        }
        MobclickAgent.onEvent(this, "Loginpage_closebutton");
        no_denglu();
        super.onBackPressed();
    }

    /**
     *
     *
     * ----------------------------------下面为业务逻辑--分割线------------------------------
     *
     */


    /**
     * 登录
     */
    private void denglu() {
//        Toasty.normal(this, "登录").show();
        presenter.get_yonghudenglu("android", edt1.getText().toString().trim(), edt2.getText().toString().trim());

    }

    /**
     * 不登录 返回原来的
     */
    private void no_denglu() {
        donetloginno();
//        startActivity(new Intent("hs.act.slbapp.index"));

    }

    // 微信登录
    private void wxdl() {
//        jPushDengluUtils.shouquan(other_login_name);
        ShowLoadingUtil.showLoading(SlbLoginActivity.this, "", null);
//        jPushDengluUtils.shezhi_shouquan_getinfo(other_login_name);
    }

    // 获取验证码bufen
    @Override
    public void OnErweimaSuccess(String s) {
        edt2.requestFocus();
    }

    @Override
    public void OnErweimaNodata(String s) {
        ToastUtils.showLong(s);
    }

    @Override
    public void OnErweimaFail(String s) {
        ToastUtils.showLong(s);
    }

    @Override
    public void OnYonghudengluSuccess(HLoginBean hLoginBean) {
        MmkvUtils.getInstance().set_common(CommonUtils.MMKV_forceLogin, "0");
        MmkvUtils.getInstance().set_common(CommonUtils.MMKV_TOKEN, hLoginBean.getToken());
        userInfoPresenter.get_userinfo();
//        onLoginSuccess();
    }

    @Override
    public void OnYonghudengluNodata(String s) {
        ToastUtils.showLong(s);
//        set_token_out();
    }

    @Override
    public void OnYonghudengluFail(String s) {
        ToastUtils.showLong(s);
        //TODO test
        userInfoPresenter.get_userinfo();
//        set_token_out();
    }

    private void set_token_out() {
        ToastUtils.showLong("登录失效，请重新登录！");
        MmkvUtils.getInstance().remove_common(CommonUtils.MMKV_SEX);
        MmkvUtils.getInstance().remove_common(CommonUtils.MMKV_IMG);
        MmkvUtils.getInstance().remove_common(CommonUtils.MMKV_TEL);
        MmkvUtils.getInstance().remove_common(CommonUtils.MMKV_NAME);
        MmkvUtils.getInstance().remove_common(CommonUtils.MMKV_forceLogin);
        MmkvUtils.getInstance().remove_common(CommonUtils.MMKV_TOKEN);
    }

    @Override
    public void OnUserInfoSuccess(HUserInfoBean bean) {
        MmkvUtils.getInstance().set_common(CommonUtils.MMKV_SEX, bean.getChildGender());
        MmkvUtils.getInstance().set_common(CommonUtils.MMKV_IMG, bean.getChildAvatar());
        MmkvUtils.getInstance().set_common(CommonUtils.MMKV_NAME, bean.getNikeName());
        MmkvUtils.getInstance().set_common(CommonUtils.MMKV_TEL, bean.getPhone());
        onLoginSuccess();
    }

    @Override
    public void OnUserInfoNodata(String s) {
        onLoginSuccess();
    }

    @Override
    public void OnUserInfoFail(String s) {
        onLoginSuccess();
    }

    @Override
    public void OnLogin1111Success(HLogin1111Bean bean) {
        MmkvUtils.getInstance().set_common(CommonUtils.MMKV_TOKEN, bean.getCs());
        if (pswChecked) {
            MmkvUtils.getInstance().set_common(CommonUtils.MMKV_USER_CHECKED, "YES");
            MmkvUtils.getInstance().set_common(CommonUtils.MMKV_USER_NAME, a);
            MmkvUtils.getInstance().set_common(CommonUtils.MMKV_USER_PSW, b);
        } else {
            MmkvUtils.getInstance().set_common(CommonUtils.MMKV_USER_CHECKED, "NO");
        }
        onLoginSuccess();

    }

    @Override
    public void OnLogin1111Nodata(String bean) {
//        onLoginSuccess();
        ToastUtils.showLong(bean);
        tv_error.setText(bean);
    }

    @Override
    public void OnLogin1111Fail(String msg) {
//        onLoginSuccess();
        ToastUtils.showLong(msg);
        tv_error.setText(msg);
    }

    @Override
    public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
        int id = checkBox.getId();
//        if (id == R.id.scb_psw) {
//            if (isChecked) {
//                jzmm_xuanzhong();
//            } else {
//                jzmm_quxiao();
//            }
//        }
        if (id == R.id.cb_psw) {
            if (isChecked) {
                jzmm_xuanzhong();
            } else {
                jzmm_quxiao();
            }
        }
    }

    /**
     * 记住密码 选中业务
     */
    private void jzmm_xuanzhong() {
//        Toasty.normal(this, "scb_cen_degree: checked").show();
        pswChecked = true;

    }

    /**
     * 记住密码 取消业务
     */
    private void jzmm_quxiao() {
//        Toasty.normal(this, "scb_cen_degree: unchecked").show();
        pswChecked = false;
    }

}
