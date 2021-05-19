package com.haier.cellarette.baselibrary.liandong;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.AppUtils;
import com.haier.cellarette.baselibrary.R;

public class LiandongDemoAct extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liandong);
        findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.Liandong1Activity"));
            }
        });
        findViewById(R.id.tv2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.Liandong2Activity"));
            }
        });
        findViewById(R.id.tv3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.Liandong3Activity"));
            }
        });
    }
}
