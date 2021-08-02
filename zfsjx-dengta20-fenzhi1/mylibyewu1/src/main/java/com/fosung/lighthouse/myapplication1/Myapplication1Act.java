package com.fosung.lighthouse.myapplication1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.example.libbase.utils.IconUtils;

public class Myapplication1Act extends AppCompatActivity {

    private String[] strings = new String[]{
            "com.fosung.lighthouse.myapplication1.DefaultAlias"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 方法1接收
        String aaaa = getIntent().getStringExtra("query1");
        String bbbb = getIntent().getStringExtra("query2");
        String cccc = getIntent().getStringExtra("query3");
        ToastUtils.showLong("aaa->" + aaaa + ",bbbb->" + bbbb + ",cccc->" + cccc);
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
                    ToastUtils.showLong("aaaa1->" + aaaa1 + ",bbbb1->" + bbbb1 + ",cccc1->" + cccc1);
                }
            }
        }
        // TODO test
        findViewById(R.id.tv1).setOnClickListener(v -> {
            // 是否显示launch图标
            IconUtils.set_alias(Myapplication1Act.this, strings, strings[0], false);
        });
        findViewById(R.id.tv2).setOnClickListener(v -> {
            // 是否显示launch图标
            IconUtils.set_alias(Myapplication1Act.this, strings, strings[0], true);
        });
//        DeviceUtil.getUUID();
    }
}