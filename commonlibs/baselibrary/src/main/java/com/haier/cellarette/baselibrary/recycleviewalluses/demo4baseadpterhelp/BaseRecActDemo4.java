package com.haier.cellarette.baselibrary.recycleviewalluses.demo4baseadpterhelp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.blankj.utilcode.util.AppUtils;
import com.haier.cellarette.baselibrary.R;

public class BaseRecActDemo4 extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleviewallsuses_demo4);
        findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".com.BaseRecActDemo41.act"));
            }
        });
        findViewById(R.id.tv2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".com.BaseRecActDemo42.act"));
            }
        });
    }


}
