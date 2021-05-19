package com.example.slbappcomm.viewpager2.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.slbappcomm.R;
import com.example.slbappcomm.viewpager2.adapter.ImageAdapter;
import com.example.slbappcomm.viewpager2.bean.DataBean;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

public class GalleryActivity extends AppCompatActivity {

    Banner mBanner1;
    Banner mBanner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunbo_gallery);
        mBanner1 = findViewById(R.id.banner1);
        mBanner2 = findViewById(R.id.banner2);

        /**
         * 画廊效果
         */

        mBanner1.setAdapter(new ImageAdapter(DataBean.getTestData2()));
        mBanner1.setIndicator(new CircleIndicator(this));
        //添加画廊效果
        mBanner1.setBannerGalleryEffect(18, 10);
        //(可以和其他PageTransformer组合使用，比如AlphaPageTransformer，注意但和其他带有缩放的PageTransformer会显示冲突)
        //添加透明效果(画廊配合透明效果更棒)
//        mBanner1.addPageTransformer(new AlphaPageTransformer());


        /**
         * 魅族效果
         */

        mBanner2.setAdapter(new ImageAdapter(DataBean.getTestData()));
        mBanner2.setIndicator(new CircleIndicator(this));
        //添加魅族效果
        mBanner2.setBannerGalleryMZ(20);


    }


}