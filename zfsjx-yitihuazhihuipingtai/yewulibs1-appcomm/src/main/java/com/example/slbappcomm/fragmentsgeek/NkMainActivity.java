package com.example.slbappcomm.fragmentsgeek;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.slbappcomm.R;
import com.example.slbappcomm.fragmentsgeek.demo1.MkDemo1Activity;
import com.example.slbappcomm.fragmentsgeek.demo2.MkDemo2Activity;
import com.example.slbappcomm.fragmentsgeek.demo3.MkDemo3Activity;
import com.example.slbappcomm.fragmentsgeek.demo4.MkDemo4Activity;
import com.example.slbappcomm.fragmentsgeek.demo5.MkDemo5Activity;

public class NkMainActivity extends AppCompatActivity implements OnClickListener {

    private TextView tv_demo1;
    private TextView tv_demo2;
    private TextView tv_demo3;
    private TextView tv_demo4;
    private TextView tv_demo5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mk_main);
        findview();
        onclickListener();
        doNetWork();
    }

    private void doNetWork() {

    }

    private void onclickListener() {
        tv_demo1.setOnClickListener(this);
        tv_demo2.setOnClickListener(this);
        tv_demo3.setOnClickListener(this);
        tv_demo4.setOnClickListener(this);
        tv_demo5.setOnClickListener(this);
    }

    private void findview() {
        tv_demo1 = (TextView) findViewById(R.id.tv_demo1);
        tv_demo2 = (TextView) findViewById(R.id.tv_demo2);
        tv_demo3 = (TextView) findViewById(R.id.tv_demo3);
        tv_demo4 = (TextView) findViewById(R.id.tv_demo4);
        tv_demo5 = (TextView) findViewById(R.id.tv_demo5);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_demo1) {//TODO 单Activity页面多模块单版本
            startActivity(new Intent(this, MkDemo1Activity.class));
        } else if (i == R.id.tv_demo2) {//TODO 单Activity页面多模块多版本
            startActivity(new Intent(this, MkDemo2Activity.class));
//                String a = "DemoAPK2_D_1_0002";
//                String currentMode = a.split("_")[3];
//                ToastUtil.showToastLong(currentMode);
        } else if (i == R.id.tv_demo3) {//TODO 单Activity页面多模块单版本两个Viewpager
            startActivity(new Intent(this, MkDemo3Activity.class));
        } else if (i == R.id.tv_demo4) {//TODO 单Activity页面多模块多版本三个Viewpager
            startActivity(new Intent(this, MkDemo4Activity.class));
        } else if (i == R.id.tv_demo5) {//TODO 单Activity页面多模块单版本Fragment+TabLayout
            startActivity(new Intent(this, MkDemo5Activity.class));
        }
    }
}
