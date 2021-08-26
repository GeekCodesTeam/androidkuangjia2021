package com.example.slbappsearch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.geek.libbase.base.SlbPluginBaseActivity;


/**
 * @author: 王硕风
 * @date: 2021.6.10 0:28
 * @Description:
 */
public class SecondActivity extends SlbPluginBaseActivity {

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_second);
//    }


    @Override
    protected void setup(@Nullable Bundle savedInstanceState) {
        super.setup(savedInstanceState);
        //开启插件服务
        Intent serviceIntent = new Intent(that, OneService.class);
        startService(serviceIntent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_second;
    }

}
