package com.example.slbappcomm.viewpager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.slbappcomm.R;

public class LXCoolMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN|WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_coolvg);
//        getSupportActionBar().hide();
    }

    public void changeOrientation(View view) {
        startActivity(new Intent(LXCoolMainActivity.this,ActivityOrientation.class));
    }

    public void changeEdgeEffectColor(View view) {
        startActivity(new Intent(LXCoolMainActivity.this,ActivityEdgeEffectColor.class));
    }

    public void enableNotifyDataSetChanged(View view) {
        startActivity(new Intent(LXCoolMainActivity.this,EnableNotifyActivity.class));
    }

    public void changeAutoScroll(View view) {
        startActivity(new Intent(LXCoolMainActivity.this,ActivityAutoScroll.class));
    }

    public void changePageTransformer(View view) {
        startActivity(new Intent(LXCoolMainActivity.this,ActivityPageTransformer.class));
    }

    public void compareWithSupport(View view) {
        startActivity(new Intent(LXCoolMainActivity.this,SupportViewPagerActivity.class));
    }
}
