package com.example.slbappcomm.viewpager.ui;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.viewpager.widget.ViewPager;

import com.example.slbappcomm.R;
import com.example.slbappcomm.viewpager.LxCoolViewPager;
import com.example.slbappcomm.viewpager.transformer.VerticalAccordionTransformer;
import com.example.slbappcomm.viewpager.transformer.VerticalDepthPageTransformer;
import com.example.slbappcomm.viewpager.transformer.VerticalRotateDownTransformer;
import com.example.slbappcomm.viewpager.transformer.VerticalRotateTransformer;
import com.example.slbappcomm.viewpager.transformer.VerticalZoomInTransformer;

import java.util.ArrayList;
import java.util.List;

public class ActivityPageTransformer extends BaseActivityLxCoolvg {
    private LxCoolViewPager vp;
    ActivityOrientation.MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_page_transformercoolvg);
//        getSupportActionBar().hide();
        initViews();
    }

    private void initViews() {
        vp = findViewById(R.id.vp);
        initData();
    }

    private void initData() {
        List<View> items = new ArrayList<>();
        items.add(createImageView(R.drawable.img1));
        items.add(createImageView(R.drawable.img2));
        items.add(createImageView(R.drawable.img03));
        items.add(createImageView(R.drawable.img00));
        adapter = new ActivityOrientation.MyAdapter(items);
        vp.setAdapter(adapter);
    }

    private int currIndex = -1;
    private ViewPager.PageTransformer[] verticals = new ViewPager.PageTransformer[]{
            new VerticalAccordionTransformer(),
            new VerticalRotateTransformer(),
            new VerticalDepthPageTransformer(),
            new VerticalRotateDownTransformer(),
            new VerticalZoomInTransformer()
    };

    public void buttonClick(View view) {
        initData();
        currIndex = (++currIndex) % verticals.length;
        vp.setPageTransformer(false, verticals[currIndex]);
    }
}
