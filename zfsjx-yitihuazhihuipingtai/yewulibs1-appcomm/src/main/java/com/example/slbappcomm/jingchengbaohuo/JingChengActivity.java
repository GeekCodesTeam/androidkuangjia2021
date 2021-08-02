package com.example.slbappcomm.jingchengbaohuo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.slbappcomm.R;
import com.example.slbappcomm.jingchengbaohuo.demo1.JingChengActivity1;
import com.example.slbappcomm.jingchengbaohuo.demo2.JingChengActivity2;

public class JingChengActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jingcheng);

    }

    public void DEMO1(View view) {
        startActivity(new Intent(JingChengActivity.this, JingChengActivity1.class));
    }

    public void DEMO2(View view) {
        startActivity(new Intent(JingChengActivity.this, JingChengActivity2.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        android.os.Process.killProcess(android.os.Process.myPid());
//        System.exit(0);
    }
}
