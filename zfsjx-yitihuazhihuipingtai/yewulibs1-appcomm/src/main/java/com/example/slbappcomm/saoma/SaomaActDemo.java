package com.example.slbappcomm.saoma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.AppUtils;
import com.example.slbappcomm.R;

public class SaomaActDemo extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saoma_demo);
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn0) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.SaomaAct"));
        } else if (id == R.id.btn1) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.SaomaAct2"));
        } else if (id == R.id.btn2) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.SaomaAct3"));
//            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.SaomaAct2FragmentActivity"));
        }

    }
}
