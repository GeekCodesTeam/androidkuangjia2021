package com.example.slbappindex.logindemo;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.slbappindex.R;
import com.geek.libbase.base.SlbBaseActivity;
import com.geek.libbase.utils.CommonUtils;
import com.geek.libutils.SlbLoginUtil;
import com.geek.libutils.data.MmkvUtils;
import com.haier.cellarette.libwebview.hois2.HiosHelper;

public class LoginActDemo extends SlbBaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_loginactdemo;
    }

    @Override
    protected void setup(@Nullable Bundle savedInstanceState) {
        super.setup(savedInstanceState);
        findViewById(R.id.tv0).setOnClickListener(v -> {
            SlbLoginUtil.get().loginOutTowhere(LoginActDemo.this, new Runnable() {
                @Override
                public void run() {
                    // 退出登录可跳转到业务页面
                    ToastUtils.showLong("退出登录成功");
                    //test
                    MmkvUtils.getInstance().set_common(CommonUtils.MMKV_TOKEN, "");
                }
            });
        });
        findViewById(R.id.tv1).setOnClickListener(v -> {
            String url1 = "https://www.baidu.com/";
            HiosHelper.resolveAd(LoginActDemo.this, LoginActDemo.this, url1);
        });
        findViewById(R.id.tv2).setOnClickListener(v -> {
            String url1 = "https://www.baidu.com/?condition=login";
            HiosHelper.resolveAd(LoginActDemo.this, LoginActDemo.this, url1);
        });
        findViewById(R.id.tv3).setOnClickListener(v -> {
            String url1 = AppUtils.getAppPackageName() + ".hs.act.slbapp.AdLoginActDemo";
            if (SlbLoginUtil.get().isUserLogin()) {
                startActivity(new Intent(url1));
            }else {
                ToastUtils.showLong("请登录");
            }
        });
        findViewById(R.id.tv4).setOnClickListener(v -> {
            String url1 = AppUtils.getAppPackageName() + ".hs.act.slbapp.AdLoginActDemo";
            SlbLoginUtil.get().loginTowhere(LoginActDemo.this, new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(url1));
                }
            });
        });
        findViewById(R.id.tv5).setOnClickListener(v -> {
            String url1 = "hios://" + AppUtils.getAppPackageName() + ".hs.act.slbapp.AdLoginActDemo{act}?act={i}1&sku_id={s}2a&category_id={s}3a";
            HiosHelper.resolveAd(LoginActDemo.this, LoginActDemo.this, url1);
        });
        findViewById(R.id.tv6).setOnClickListener(v -> {
            String url1 = "hios://" + AppUtils.getAppPackageName() + ".hs.act.slbapp.AdLoginActDemo{act}?act={i}1&sku_id={s}2a&category_id={s}3a&condition=login";
            HiosHelper.resolveAd(LoginActDemo.this, LoginActDemo.this, url1);

        });
        findViewById(R.id.tv7).setOnClickListener(v -> {
            Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.AdLoginImgActDemo");
            String url1 = "hios://" + AppUtils.getAppPackageName() + ".hs.act.slbapp.AdLoginActDemo{act}?act={i}1&sku_id={s}2a&category_id={s}3a&condition=login";
            intent.putExtra("ces", url1);
            startActivity(intent);
        });
    }
}
