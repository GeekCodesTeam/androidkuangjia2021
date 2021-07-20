package com.example.slbappsplash.welcome;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.libbase.base.SlbBaseActivity;
import com.example.libbase.nativendk.JNIUtils2;
import com.example.libbase.nativendk.KeyReflectUtils;
import com.example.libbase.utils.CommonUtils;
import com.example.slbappsplash.R;
import com.geek.libutils.SlbLoginUtil;
import com.geek.libutils.app.MobileUtils;
import com.geek.libutils.app.MyLogUtil;
import com.geek.libutils.data.MmkvUtils;
import com.haier.cellarette.libwebview.hois2.HiosHelper;
import com.mob.pushsdk.MobPushUtils;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class WelComeActivity extends SlbBaseActivity {

    private int key_token;
    private SplashView splashView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //虚拟键
//        if (NavigationBarUtil.hasNavigationBar(this)) {
////            NavigationBarUtil.initActivity(getWindow().getDecorView());
//            NavigationBarUtil.hideBottomUIMenu(this);
//        }
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
//            finish();
//            return;
//        }
//        MyLogUtil.e("ssssssssss", DeviceUtils.getAndroidID());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void setup(@Nullable Bundle savedInstanceState) {
        super.setup(savedInstanceState);
        // 业务bufen
        key_token = (int) SPUtils.getInstance().getInt("key_token", -1);
        // test
        configNDK();
        /** 调用此方法可以在任何地方更新闪屏视图数据 */
        /** 在 setContentView(R.layout.activity_sample) 后调用 */
        splashView = new SplashView(this);
        splashView.showSplashView(this, 3, R.drawable.default_img, new SplashView.OnSplashViewActionListener() {
            @Override
            public void onSplashImageClick(String actionUrl) {
                MyLogUtil.e("SplashView", "img clicked. actionUrl: " + actionUrl);
                HiosHelper.resolveAd(WelComeActivity.this, WelComeActivity.this, actionUrl);
                finish();
            }

            @Override
            public void onSplashViewDismiss(boolean initiativeDismiss) {
                MyLogUtil.e("SplashView", "dismissed, initiativeDismiss: " + initiativeDismiss);
                if (key_token == -1) {
                    SPUtils.getInstance().put("key_token", 0);
                    startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.SplshActivity"));
                    finish();
                } else {
                    if (!MobileUtils.isNetworkConnected(WelComeActivity.this)) {
                        startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ShouyeActivity"));// ShouyeActivity
                    } else {
                        jump();
                    }
                    finish();
                }
            }
        });
        splashView.updateSplashData(this, URL, "http://liangxiao.blog.51cto.com/");
//        jump();
//        finish();
    }

    private String aaaa;
    private String bbbb;
    private String cccc;

    private void jump() {
        //            if (TextUtils.equals(hSettingBean.getForceLogin(), "1")) { // 强制登录bufen
        if (false) {
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
//                if (appLinkAction != null) {
                Uri appLinkData = appLinkIntent.getData();
                if (appLinkData != null) {
                    aaaa = appLinkData.getQueryParameter("query1");
                    bbbb = appLinkData.getQueryParameter("query2");
                    cccc = appLinkData.getQueryParameter("query3");
                    ToastUtils.showLong("query1->" + aaaa + ",query2->" + bbbb + ",query3->" + cccc);
                    // 业务逻辑
                    if (TextUtils.equals(aaaa, "shouye")) {
                        // 跳转到首页
                        Intent intent1 = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ShouyeActivity");
                        intent1.putExtra("query1", bbbb);
                        startActivity(intent1);
                        finish();
                        return;
                    } else if (TextUtils.equals(aaaa, "classdetail")) {
                        SlbLoginUtil.get().loginTowhere(WelComeActivity.this, new Runnable() {
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
                        SlbLoginUtil.get().loginTowhere(WelComeActivity.this, new Runnable() {
                            @Override
                            public void run() {
                                // 跳转到学习报告详情页
                                try {
//                                        HiosHelper.resolveAd(WelComeActivity.this, WelComeActivity.this, URLDecoder.decode(bbbb, "UTF-8") + MmkvUtils.getInstance().get_common(CommonUtils.MMKV_TOKEN));
                                    HiosHelper.resolveAd(WelComeActivity.this, WelComeActivity.this, URLDecoder.decode(bbbb, "UTF-8") + MmkvUtils.getInstance().get_common(CommonUtils.MMKV_TOKEN));
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                finish();
                                return;
                            }
                        });
                    } else if (TextUtils.equals(aaaa, "myorder")) {
                        // 跳转到我的—我的订单
                        SlbLoginUtil.get().loginTowhere(WelComeActivity.this, new Runnable() {
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
                } else {
                    // TODO mob
                    // activity获取信息
                    Intent intent2 = getIntent();
                    Uri uri = intent2.getData();
                    if (intent2 != null) {
                        System.out.println("MobPush linkone intent---" + intent2.toUri(Intent.URI_INTENT_SCHEME));
                    }
                    StringBuilder sb = new StringBuilder();
                    if (uri != null) {
                        sb.append(" scheme:" + uri.getScheme() + "\n");
                        sb.append(" host:" + uri.getHost() + "\n");
                        sb.append(" port:" + uri.getPort() + "\n");
                        sb.append(" query:" + uri.getQuery() + "\n");
                    }

                    //获取link界面传输的数据
                    JSONArray jsonArray = MobPushUtils.parseSchemePluginPushIntent(intent2);
                    if (jsonArray != null) {
                        sb.append("\n" + "bundle toString :" + jsonArray.toString());
                    }
                    // 通过scheme跳转详情页面可选择此方式
                    JSONArray var = new JSONArray();
                    var = MobPushUtils.parseSchemePluginPushIntent(intent2);
                    MyLogUtil.e("MobPushsssssss", var.toString());
                    //跳转首页可选择此方式
                    JSONArray var2 = new JSONArray();
                    var2 = MobPushUtils.parseMainPluginPushIntent(intent2);
                    MyLogUtil.e("MobPush---获取link界面传输的数据--", sb.toString());
                    MyLogUtil.e("MobPush---通过scheme跳转详情页面可选择此方式---", var.toString());
                    MyLogUtil.e("MobPush---跳转首页可选择此方式---", var2.toString());
                }
//                }
            }
            startActivity(intent);
            finish();
        }
    }

    private String URL = "http://ww2.sinaimg.cn/large/72f96cbagw1f5mxjtl6htj20g00sg0vn.jpg";


    private void configNDK() {
        // 跳转test
//        handler.postDelayed(runnable, 0);
//        JumpWhere();
        // 欢迎页声音
//        GetAssetsFileMP3TXTJSONAPKUtil.getInstance(this).playMusic(App.get(), true, "android.resource://" + getPackageName() + "/" + R.raw.demo);
        //
        try {
//            String aa = (String) KeyReflectUtils.getInstance().invokeMethod("com.sairobo.alty.JNIUtils", "stringFromJNI",
            String aa = (String) KeyReflectUtils.getInstance().invokeMethod(AppUtils.getAppPackageName() + ".JNIUtils", "stringFromJNI",
                    new Object[]{});
            MyLogUtil.e("--JNIUtils-反射-", aa);
        } catch (Exception e) {
            e.printStackTrace();
            MyLogUtil.e("--JNIUtils-反射-", e.getMessage());
        }
        //
        JNIUtils2 jniUtils = new JNIUtils2();
        MyLogUtil.e("--JNIUtils2--", jniUtils.stringFromJNI());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void finish() {
//        overridePendingTransition(0, 0);
        super.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}