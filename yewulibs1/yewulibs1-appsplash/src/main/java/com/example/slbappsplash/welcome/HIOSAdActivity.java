package com.example.slbappsplash.welcome;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.example.bizyewu1.bean.VersionInfoBean;
import com.example.bizyewu1.presenter.CheckverionPresenter;
import com.example.bizyewu1.view.CheckverionView;
import com.example.libbase.utils.CommonUtils;
import com.example.libbase.widgets.NavigationBarUtil;
import com.example.slbappsplash.R;
import com.geek.libutils.SlbLoginUtil;
import com.geek.libutils.data.MmkvUtils;
import com.haier.cellarette.libwebview.base.WebViewActivity;
import com.haier.cellarette.libwebview.hois2.HiosHelper;

import java.net.URLDecoder;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HIOSAdActivity extends WebViewActivity implements CheckverionView {

    private TextView tv_adJump;
    private ScheduledExecutorService mExecutorService;
    int time = 5;
    private String url;
    private CheckverionPresenter hSettingPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        ScreenUtils.setNonFullScreen(this);
//        BarUtils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.web_white));
//        if (BarUtils.isStatusBarLightMode(this)) {
//            BarUtils.setStatusBarLightMode(this, false);
//        } else {
//            BarUtils.setStatusBarLightMode(this, true);
//        }
        //虚拟键
        if (NavigationBarUtil.hasNavigationBar(this)) {
//            NavigationBarUtil.initActivity(getWindow().getDecorView());
            NavigationBarUtil.hideBottomUIMenu(this);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hios_adactivity);
        findview();
        onclickListener();
        setupWebView();
        tv_adJump = findViewById(R.id.tv_adJumps);
        tv_adJump.setVisibility(View.GONE);
        tv_adJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExecutorService.shutdown();
                jump();
            }
        });
        // 接口部分
        hSettingPresenter = new CheckverionPresenter();
        hSettingPresenter.onCreate(this);
        hSettingPresenter.checkVerion("android");
    }

    private void setTime() {
        tv_adJump.setVisibility(View.VISIBLE);
        mExecutorService = Executors.newScheduledThreadPool(1);
        mExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                if (time == 0) {
                    mExecutorService.shutdown();
                    jump();
                }

                tv_adJump.post(new Runnable() {
                    @Override
                    public void run() {
                        tv_adJump.setText("跳过 ( " + time-- + " )");
                    }
                });

            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void jump() {
        //            if (TextUtils.equals(hSettingBean.getForceLogin(), "1")) { // 强制登录bufen
        if (true) {
            Intent loginIntent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.SlbLoginActivity");
//            loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(loginIntent);
            finish();
        } else {
            // 根据业务跳转
            Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ShouyeActivity");
            // 根据HIOS协议三方平台跳转
            // ATTENTION: This was auto-generated to handle app links.
            Intent appLinkIntent = getIntent();
            if (appLinkIntent != null) {
                String appLinkAction = appLinkIntent.getAction();
                if (appLinkAction != null) {
                    Uri appLinkData = appLinkIntent.getData();
                    if (appLinkData != null) {
                        aaaa = appLinkData.getQueryParameter("query1");
                        bbbb = appLinkData.getQueryParameter("query2");
                        cccc = appLinkData.getQueryParameter("query3");
                        // 业务逻辑
                        if (TextUtils.equals(aaaa, "shouye")) {
                            // 跳转到首页
                            Intent intent1 = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ShouyeActivity");
                            intent1.putExtra("query1", bbbb);
                            startActivity(intent1);
                            finish();
                            return;
                        } else if (TextUtils.equals(aaaa, "classdetail")) {
                            SlbLoginUtil.get().loginTowhere(HIOSAdActivity.this, new Runnable() {
                                @Override
                                public void run() {
                                    // 跳转到我的课程—课程详情页
                                    Intent intent1 = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ClassDetailActivity");
                                    intent1.putExtra("query1", bbbb);
                                    intent1.putExtra("query2 ", cccc);
                                    startActivity(intent1);
                                    finish();
                                    return;
                                }
                            });
                        } else if (TextUtils.equals(aaaa, "webview")) {
                            SlbLoginUtil.get().loginTowhere(HIOSAdActivity.this, new Runnable() {
                                @Override
                                public void run() {
                                    // 跳转到学习报告详情页
                                    HiosHelper.resolveAd(HIOSAdActivity.this,
                                            HIOSAdActivity.this,
                                            URLDecoder.decode(bbbb)
                                                    + MmkvUtils.getInstance().get_common(CommonUtils.MMKV_TOKEN));
                                    finish();
                                    return;
                                }
                            });
                        } else if (TextUtils.equals(aaaa, "myorder")) {
                            // 跳转到我的—我的订单
                            SlbLoginUtil.get().loginTowhere(HIOSAdActivity.this, new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.OrderAct"));
                                    finish();
                                    return;
                                }
                            });
                        } else {
                            startActivity(intent);
                            finish();
                        }
                        return;
                    }
                }
            }
            startActivity(intent);
            finish();
        }
    }

    /**
     * --------------------------------业务逻辑bufen-------------------------------
     */
    private String aaaa;
    private String bbbb;
    private String cccc;

    @Override
    public void OnUpdateVersionSuccess(VersionInfoBean versionInfoBean) {
//        MmkvUtils.getInstance().set_common(CommonUtils.MMKV_forceLogin, hSettingBean.getForceLogin());
//        MmkvUtils.getInstance().set_common(CommonUtils.MMKV_serviceProtocol, hSettingBean.getServiceProtocol());
//        MmkvUtils.getInstance().set_common(CommonUtils.MMKV_privacyPolicy, hSettingBean.getPrivacyPolicy());
        // 加载webview 开始倒计时bufen
        url = "https://blog.51cto.com/liangxiao";
        loadUrl(url);
        setTime();
    }

    @Override
    public void OnUpdateVersionNodata(String bean) {
        startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ShouyeActivity"));
        finish();
    }

    @Override
    public void OnUpdateVersionFail(String msg) {
//        startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ShouyeActivity"));
//        finish();
        // 加载webview 开始倒计时bufen
//        url = "https://syzs.qq.com/campaign/3977.html";
//        url = "file:///android_asset/html/test.html";
        url = "https://blog.51cto.com/liangxiao";
        loadUrl(url);
        setTime();
    }

    @Override
    public String getIdentifier() {
        return System.currentTimeMillis() + "";
    }


    @Override
    protected void onDestroy() {
        set_destory();
        if (mExecutorService != null) {
            mExecutorService.shutdown();
        }
        if (hSettingPresenter != null) {
            hSettingPresenter.onDestory();
        }
        super.onDestroy();
    }
}
