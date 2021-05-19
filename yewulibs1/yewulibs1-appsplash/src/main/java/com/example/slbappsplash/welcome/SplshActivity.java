package com.example.slbappsplash.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.example.slbappcomm.base.SlbBaseActivityPermissions;
import com.example.slbappsplash.R;
import com.haier.cellarette.baselibrary.splash.AlphaView;

public class SplshActivity extends SlbBaseActivityPermissions implements View.OnClickListener {
    /**
     * 加载图片ViewPager
     */
//    布局设置
    private Integer[] Layouts = {/*R.layout.splash_activity_lay1,*/ R.layout.splash_activity_lay2, R.layout.splash_activity_lay3, R.layout.splash_activity_lay4};
    private Integer[] strings = {/*R.drawable.guid1,*/ R.drawable.guid2, R.drawable.guid3, R.drawable.guid4};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash_index;
    }

    @Override
    protected void setup(@Nullable Bundle savedInstanceState) {
        super.setup(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        ScreenUtils.setNonFullScreen(this);
        BarUtils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.web_white));
        if (BarUtils.isStatusBarLightMode(this)) {
            BarUtils.setStatusBarLightMode(this, false);
        } else {
            BarUtils.setStatusBarLightMode(this, true);
        }
        setContentView(R.layout.activity_splash_index);
        AlphaView alphaview = findViewById(R.id.alphaview);
        alphaview.setPointGravity(Gravity.END);
        alphaview.setPointVisbile(View.GONE);
        alphaview.setData(strings, Layouts);
        alphaview.setPoint(R.drawable.new_press_dian, R.drawable.new_normal_dian, 50, 50, 30, 40, 30, 1);
        alphaview.setSplashItemOnClick(this, R.layout.splash_activity_lay4, R.id.login, R.id.regist);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.login) {
//            startActivity(new Intent("hs.act.slbapp.index"));
//            Toasty.info(this, "注册").show();
            finish();
        } else if (i == R.id.regist) {
            onBackPressed();
            // 设置权限Android8.o
//            setIsjump(true);
        }
    }

    @Override
    protected boolean is_android10() {
        // 初始化权限Android8.0
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.HIOSAdActivity"));
        startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ShouyeActivity"));
        finish();
    }
}