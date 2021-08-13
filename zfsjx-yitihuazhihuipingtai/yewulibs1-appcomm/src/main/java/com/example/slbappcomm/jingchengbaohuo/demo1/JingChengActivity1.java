package com.example.slbappcomm.jingchengbaohuo.demo1;

import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.slbappcomm.R;

public class JingChengActivity1 extends AppCompatActivity {

    OnePixelReceiver mOnepxReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jingcheng1);
        //
        //注册监听屏幕的广播
        mOnepxReceiver = new OnePixelReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        registerReceiver(mOnepxReceiver, intentFilter);
    }

}
