/**
 * Copyright 2016,Smart Haier.All rights reserved
 */
package com.example.libbase.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.view.LayoutInflaterCompat;
import androidx.core.view.LayoutInflaterFactory;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.example.libbase.R;
import com.example.libbase.netstate.NetState;
import com.example.libbase.netstate.NetconListener;
import com.example.libbase.widgets.IBaseAction;
import com.example.swipebacklayout.SwipeBack;
import com.example.swipebacklayout.SwipeBackLayout;
import com.example.swipebacklayout.SwipeBackUtil;
import com.example.swipebacklayout.activity.SwipeBackActivityBase;
import com.example.swipebacklayout.activity.SwipeBackActivityHelper;
import com.geek.libutils.SlbLoginUtil;
import com.geek.libutils.app.BaseAppManager;
import com.geek.libutils.app.BaseViewHelper;

import me.jessyan.autosize.AutoSizeCompat;

public abstract class SlbBaseActivity extends AppCompatActivity implements SwipeBackActivityBase,
        IBaseAction, NetconListener {

    public static final String REQUEST_CODE = "request_code";

    private long mCurrentMs = System.currentTimeMillis();
    //    private Handler handler;
    protected NetState netState;
    protected Typeface tfLight;
    protected Typeface tfLight2;
    //    private JPluginPlatformInterface jPluginPlatformInterface;
    private SwipeBackActivityHelper mHelper;
    protected boolean enableSwipeBack;

    @Override
    public Resources getResources() {
        //需要升级到 v1.1.2 及以上版本才能使用 AutoSizeCompat
        AutoSizeCompat.autoConvertDensityOfGlobal((super.getResources()));//如果没有自定义需求用这个方法
        AutoSizeCompat.autoConvertDensity((super.getResources()), 667, false);//如果有自定义需求就用这个方法
        return super.getResources();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
//        ScreenUtils.setNonFullScreen(this);
//        BarUtils.setStatusBarLightMode(this, false);
//        BarUtils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.black_000));

//        if (BarUtils.isStatusBarLightMode(this)) {
//            BarUtils.setStatusBarLightMode(this, false);
//        } else {
//            BarUtils.setStatusBarLightMode(this, true);
//        }
        // 截屏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        // 另一种写法
//        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.web_white), 0);
//        StatusBarUtil.setLightMode(this);
        // 另一种写法
//        WebStatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.web_white), 0);
//        WebStatusBarUtil.setLightMode(this);
        //
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // old
        super.onCreate(savedInstanceState);
        // 加载注解bufen
        SwipeBack swipeBack = getClass().getAnnotation(SwipeBack.class);
        if (swipeBack != null) {
            enableSwipeBack = swipeBack.value();
        }
        if (enableSwipeBack) mHelper = new SwipeBackActivityHelper(this);
        try {
            if (enableSwipeBack) {
                mHelper.onActivityCreate();
                setSwipeBackEnable(enableSwipeBack);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //
        BaseAppManager.getInstance().add(this);
        setContentView(getLayoutId());
        BarUtils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.transparent));
        BarUtils.setStatusBarLightMode(this, false);
        setup(savedInstanceState);
        //网络监听
        netState = new NetState();
        netState.setNetStateListener(this, this);
        // 听书监听
//        set_lb_floatbutton_init();
        // 字体
        tfLight = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Light.ttf");
        tfLight2 = Typeface.createFromAsset(getAssets(), "fonts/DINCond-Regular.ttf");
        //
//        jPluginPlatformInterface = new JPluginPlatformInterface(this);

    }

    private void interceptCreateView() {
        LayoutInflaterCompat.setFactory(LayoutInflater.from(this), new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                AppCompatDelegate delegate = getDelegate();
                View view = delegate.createView(parent, name, context, attrs);
                if (view != null && view instanceof EditText) {
                    Log.d("***", "IME_FLAG_NO_EXTRACT_UI");
                    EditText et = (EditText) view;
                    et.setImeOptions(et.getImeOptions() | EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                    return et;
                }
                return view;
            }
        });
    }

    private long lastTime;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus) { HookUtil.hookClick(this);}

    }

    public abstract class OnMultiClickListener implements View.OnClickListener {
        // 两次点击按钮之间的点击间隔不能少于1000毫秒
        private final int MIN_CLICK_DELAY_TIME = 1500;
        private long lastClickTime;

        public abstract void onMultiClick(View v);

        @Override
        public void onClick(View v) {
            long curClickTime = System.currentTimeMillis();
            if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
                // 超过点击间隔后再将lastClickTime重置为当前点击时间
                lastClickTime = curClickTime;
                onMultiClick(v);
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
//            MobEventHelper.onEvent(this, "effective_click");
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        JPushInterface.onResume(this);
//        MobEvent.onResume(this);
//        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        JPushInterface.onPause(this);
//        MobclickAgent.onPause(this);
//        MobEvent.onPause(this);
        AppUtils.registerAppStatusChangedListener(baseAppStatusChangedListener);
    }

    protected Utils.OnAppStatusChangedListener baseAppStatusChangedListener = new Utils.OnAppStatusChangedListener() {

        @Override
        public void onForeground(Activity activity) {
            // 前台
            // 护眼模式bufen
//            if (SPUtils.getInstance().getBoolean("huyan", false)) {
//                startService(new Intent(SlbBaseActivity.this, Huyanservices.class));
//            }
        }

        @Override
        public void onBackground(Activity activity) {
            // 后台
//            if (SPUtils.getInstance().getBoolean("huyan", false)) {
//                stopService(new Intent(SlbBaseActivity.this, Huyanservices.class));
//            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
//        jPluginPlatformInterface.onStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        jPluginPlatformInterface.onStop(this);
    }

    protected abstract int getLayoutId();

    protected void setup(@Nullable Bundle savedInstanceState) {

    }


    @Override
    public void net_con_none() {
        ToastUtils.showLong("网络异常，请检查网络连接！");
    }

    @Override
    public void net_con_success() {
    }

    @Override
    public void showNetPopup() {
    }

    protected final <T extends View> T f(@IdRes int resId) {
        return BaseViewHelper.f(this, resId);
    }

    public void startActivity(Class<? extends Activity> activity) {
        startActivity(new Intent(this, activity));
    }

    public void startActivity(String action) {
        startActivity(new Intent(action));
    }

    public void startActivityForResult(Class<? extends Activity> activity, int requestCode) {
        startActivityForResult(new Intent(this, activity), requestCode);
    }

    public void startActivityForResult(String action, int requestCode) {
        startActivityForResult(new Intent(action), requestCode);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (intent.resolveActivity(getPackageManager()) == null) {
            Log.e("Activity", "No Activity found to handle intent " + intent);
            return;
        }

        if (requestCode != -1 && intent.getIntExtra(REQUEST_CODE, -1) == -1) {
            intent.putExtra(REQUEST_CODE, requestCode);
        }

        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public final void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //登录和未登录成功状态
        if (SlbLoginUtil.get().login_activity_result(requestCode, resultCode, data)) {
            if (isIs_finish_login()) {
                finish();
                setIs_finish_login(false);
            } else {
            }
            return;
        }
        //正常状态
        onActResult(requestCode, resultCode, data);
    }

    public boolean is_finish_login;

    public boolean isIs_finish_login() {
        return is_finish_login;
    }

    public void setIs_finish_login(boolean is_finish_login) {
        this.is_finish_login = is_finish_login;
    }

    protected void onUserLogined(String userId) {
    }

    protected void onActResult(int requestCode, int resultCode, Intent data) {
    }

    @Override
    public void finish() {
//        MyLogUtil.e("ssssssss", !TextUtils.equals(ActivityUtils.getTopActivity().getClass().getName(), "com.example.slbappindex.index.ShouyeActivity") + "");
//        MyLogUtil.e("ssssssss", !TextUtils.equals(ActivityUtils.getTopActivity().getClass().getName(), "com.example.slbappsplash.welcome.WelComeActivity") + "");
//        if (ActivityUtils.getActivityList().size() == 1
//                && !TextUtils.equals(ActivityUtils.getTopActivity().getClass().getName(), "com.example.slbappindex.index.ShouyeActivity")
//                && !TextUtils.equals(ActivityUtils.getTopActivity().getClass().getName(), "com.example.slbappsplash.welcome.WelComeActivity")) {
//            MyLogUtil.e("ssssssss", "推送来的~");
//            Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ShouyeActivity");
//            startActivity(intent);
////            finish();
//        }
//        ShowLoadingUtil.onDestory();
        super.finish();
//        BaseAppManager.getInstance().remove(this);
//        overridePendingTransition(R.anim.open_main, R.anim.close_next);
        // 听书悬浮框bufen
//        LocalBroadcastManagers.getInstance(BaseApp.get()).unregisterReceiver(floatButtonReceiverListenBooks);
    }

    @Override
    protected void onDestroy() {
        BaseAppManager.getInstance().remove(this);
        if (netState != null) {
            netState.unregisterReceiver();
        }
        hideSoftKeyboard();
//        ShowLoadingUtil.onDestory();
        AppUtils.unregisterAppStatusChangedListener(baseAppStatusChangedListener);
        super.onDestroy();

    }

    //以下不用管系列————
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (enableSwipeBack) mHelper.onPostCreate();
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return enableSwipeBack ? mHelper.getSwipeBackLayout() : null;
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        SwipeBackUtil.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onHomePressed() {
//        finish();
//        while (!BaseAppManager.getInstance().getAll().isEmpty()) {
//            BaseAppManager.getInstance().top().finish();
//        }

//        Stack<Activity> all = BaseAppManager.getInstance().getAll();
//        for (Iterator<Activity> iterator = all.iterator(); iterator.hasNext();) {
//            iterator.next().finish();
//        }

        BaseAppManager.getInstance().clear();
        Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ShouyeActivity");
        startActivity(intent);
        finish();

//        Application app = BaseApp.get();
//        if (app instanceof AndroidApplication) {
//            ((AndroidApplication) app).onHomePressed();
//        }
    }

    /**
     * 隐藏软键盘
     */
    protected void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public Activity who() {
        return this;
    }

    public String getIdentifier() {
        return getClass().getName() + mCurrentMs;
    }

    public void back(View v) {
        finish();
    }
}
