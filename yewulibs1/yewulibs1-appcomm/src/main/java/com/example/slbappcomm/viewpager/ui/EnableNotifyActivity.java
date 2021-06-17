package com.example.slbappcomm.viewpager.ui;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.slbappcomm.R;
import com.example.slbappcomm.viewpager.LxCoolViewPager;

import java.util.ArrayList;
import java.util.List;

public class EnableNotifyActivity extends BaseActivityLxCoolvg {
    private LxCoolViewPager vp;
    ActivityOrientation.MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_enable_notifycoolvg);
//        getSupportActionBar().hide();
        initViews();
    }

    private List<View> items;

    private void initViews() {
        items = new ArrayList<>();
        items.add(createImageView(R.drawable.img1));
        items.add(createImageView(R.drawable.img2));
        items.add(createImageView(R.drawable.img03));
        items.add(createImageView(R.drawable.img00));
        adapter = new ActivityOrientation.MyAdapter(items);
        //
        vp = findViewById(R.id.vp);
        vp.setScrollMode(LxCoolViewPager.ScrollMode.HORIZONTAL);
        vp.setAdapter(adapter);
    }

    public void buttonClick(View view) {
        items.clear();
        items.add(createImageView(R.drawable.img00));
        items.add(createImageView(R.drawable.img2));
        items.add(createImageView(R.drawable.img03));
        items.add(createImageView(R.drawable.img1));
        vp.notifyDataSetChanged();
    }
}
