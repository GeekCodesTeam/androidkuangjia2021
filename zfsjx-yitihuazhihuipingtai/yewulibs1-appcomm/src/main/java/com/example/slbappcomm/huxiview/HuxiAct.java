package com.example.slbappcomm.huxiview;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.AppUtils;
import com.example.slbappcomm.R;
import com.haier.cellarette.baselibrary.shuaxinviewanimation.BreathingViewHelper;

public class HuxiAct extends AppCompatActivity {

    TextView tv1;
    TextView tv2;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huxi);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        //
        init1(tv1);
        //
        init2();
    }

    private void init2() {
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() +".hs.act.slbapp.Huxi2MainActivity"));
            }
        });
    }

    private void init1(View tv1) {
        //一个简单的小工具类，用来设置警示 View 的呼吸式背景颜色
        BreathingViewHelper.setBreathingBackgroundColor(2000, tv1, Color.parseColor("#f36c60"));
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BreathingViewHelper.stopBreathingBackgroundColor(v);
            }
        });
    }
}
