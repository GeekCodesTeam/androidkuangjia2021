package com.example.slbappcomm.viewpager.ui;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.slbappcomm.R;
import com.example.slbappcomm.viewpager.LxCoolViewPager;

import java.util.ArrayList;
import java.util.List;

public class ActivityAutoScroll extends BaseActivityLxCoolvg {
    private LxCoolViewPager vp;
    ActivityOrientation.MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN|WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_auto_scrollcoolvg);
//        getSupportActionBar().hide();
        initViews();
    }

    private void initViews() {
        List<View> items = new ArrayList<>();
        items.add(createImageView(R.drawable.img1));
        items.add(createImageView(R.drawable.img2));
        items.add(createImageView(R.drawable.img03));
        items.add(createImageView(R.drawable.img00));
        adapter = new ActivityOrientation.MyAdapter(items);
        //
        vp = findViewById(R.id.vp);
        vp.setScrollMode(LxCoolViewPager.ScrollMode.VERTICAL);
        vp.setAutoScroll(true,3000);
        vp.setScrollDuration(true,2000);
        vp.setAdapter(adapter);
    }

    int index = 0;
    public void buttonClick(View view) {
        if(index == 0){
            vp.toggleAutoScrollDirection();
            vp.setScrollDuration(true,1000);
            index = 1;
        }else{
            vp.toggleAutoScrollDirection();
            vp.setScrollDuration(true,500);
            index = 0;
        }
    }
}
