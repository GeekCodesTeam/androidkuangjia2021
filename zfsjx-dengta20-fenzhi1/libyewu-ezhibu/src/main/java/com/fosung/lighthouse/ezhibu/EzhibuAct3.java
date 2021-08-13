package com.fosung.lighthouse.ezhibu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.geek.libbase.base.SlbBaseActivity;
import com.geek.libbase.utils.IconUtils;

public class EzhibuAct3 extends SlbBaseActivity {

    private String[] strings = new String[]{
            "com.fosung.lighthouse.ezhibu.DefaultAlias"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_ezhibu3;
    }

    @Override
    protected void setup(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.setup(savedInstanceState);
        // 方法1接收
        if (getIntent() != null) {
            String aaaa = getIntent().getStringExtra("query1");
            String bbbb = getIntent().getStringExtra("query2");
            String cccc = getIntent().getStringExtra("query3");
            ToastUtils.showLong("方法1接收:" + "aaa->" + aaaa + ",bbbb->" + bbbb + ",cccc->" + cccc);
        }
        // 方法2接收
        Intent appLinkIntent = getIntent();
        if (appLinkIntent != null) {
            String appLinkAction = appLinkIntent.getAction();
            if (appLinkAction != null) {
                Uri appLinkData = appLinkIntent.getData();
                if (appLinkData != null) {
                    String aaaa1 = appLinkData.getQueryParameter("query1");
                    String bbbb1 = appLinkData.getQueryParameter("query2");
                    String cccc1 = appLinkData.getQueryParameter("query3");
                    ToastUtils.showLong("方法2接收:" + "aaaa1->" + aaaa1 + ",bbbb1->" + bbbb1 + ",cccc1->" + cccc1);
                }
            }
        }
        // TODO test
        findViewById(R.id.tv1).setOnClickListener(v -> {
            // 是否显示launch图标
            IconUtils.set_alias(EzhibuAct3.this, strings, strings[0], false);
        });
        findViewById(R.id.tv2).setOnClickListener(v -> {
            // 是否显示launch图标
            IconUtils.set_alias(EzhibuAct3.this, strings, strings[0], true);
        });
//        DeviceUtil.getUUID();
    }


}