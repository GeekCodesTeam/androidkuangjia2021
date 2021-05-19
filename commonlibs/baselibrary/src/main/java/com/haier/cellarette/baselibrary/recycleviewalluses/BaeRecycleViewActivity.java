package com.haier.cellarette.baselibrary.recycleviewalluses;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.AppUtils;
import com.haier.cellarette.baselibrary.R;

public class BaeRecycleViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleviewalluses);
//recycleview的所有用法参考：
//
//        官方WIKI地址：https://www.jianshu.com/p/b343fcff51b0
//        github：https://github.com/CymChad/BaseRecyclerViewAdapterHelper
//        官网地址：http://www.recyclerview.org/
        findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".com.BaseRecActDemo11.act"));
            }
        });
        findViewById(R.id.tv2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".com.BaseRecActDemo22.act"));
            }
        });
        findViewById(R.id.tv3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".com.BaseRecActDemo33.act"));
            }
        });
        findViewById(R.id.tv4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".com.BaseRecActDemo44.act"));
            }
        });
        findViewById(R.id.tv5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".com.BaseRecActDemo55.act"));
            }
        });
        findViewById(R.id.tv6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".com.BaseRecActDemo66.act"));
            }
        });
        findViewById(R.id.tv7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".com.BaseRecActDemo77.act"));
            }
        });
        findViewById(R.id.tv8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".com.BaseRecActDemo88.act"));
            }
        });
        findViewById(R.id.tv9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".com.BaseRecActDemo99.act"));
            }
        });

    }
}
