package com.example.slbappsplash.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.AppUtils;
import com.geek.libbase.base.SlbBaseActivity;
import com.example.slbappsplash.R;


public class WelComeActivity extends SlbBaseActivity {

    private RelativeLayout rl1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (NavigationBarUtil.hasNavigationBar(this)) {
//            NavigationBarUtil.hideBottomUIMenu(this);
//        }
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        Log.e("gongshi1", (Math.sqrt(Math.pow(1920, 2) + (Math.pow(1200, 2))) / 25.4 + ""));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void setup(@Nullable Bundle savedInstanceState) {
        super.setup(savedInstanceState);
        rl1 = findViewById(R.id.rl1);
        rl1.setBackgroundResource(R.mipmap.iconlogin7);
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
//                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ShouyeActivity"));
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.SlbLoginActivity"));
                finish();
            }
        }, 1000);
    }

    @Override
    public void finish() {
        overridePendingTransition(0, 0);
        super.finish();
    }

}