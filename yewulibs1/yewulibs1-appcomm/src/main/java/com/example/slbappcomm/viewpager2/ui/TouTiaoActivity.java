package com.example.slbappcomm.viewpager2.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.slbappcomm.R;
import com.example.slbappcomm.viewpager2.adapter.TopLineAdapter;
import com.example.slbappcomm.viewpager2.bean.DataBean;
import com.google.android.material.snackbar.Snackbar;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.ZoomOutPageTransformer;
import com.youth.banner.util.LogUtils;

public class TouTiaoActivity extends AppCompatActivity {

    Banner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunbo_tou_tiao);
        banner = findViewById(R.id.banner);

        //实现1号店和淘宝头条类似的效果
        banner.setAdapter(new TopLineAdapter(DataBean.getTestData2()))
                .setOrientation(Banner.VERTICAL)
                .setPageTransformer(new ZoomOutPageTransformer())
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(Object data, int position) {
                        Snackbar.make(banner, ((DataBean) data).title, Snackbar.LENGTH_SHORT).show();
                        LogUtils.d("position：" + position);
                    }
                });

    }
}