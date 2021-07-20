package com.example.libbase.viewpager2.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.example.libbase.R;
import com.example.libbase.viewpager2.adapter.ImageTitleAdapter;
import com.example.libbase.viewpager2.bean.DataBean;
import com.youth.banner.Banner;
import com.youth.banner.config.BannerConfig;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.util.BannerUtils;

public class ConstraintLayoutBannerActivity extends AppCompatActivity {
    private static final String TAG = "banner_log";
    Banner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunbo_constraint_layout_banner);
        banner = findViewById(R.id.banner);
        banner.setAdapter(new ImageTitleAdapter(DataBean.getTestData()));
        banner.setIndicator(new CircleIndicator(this));
        banner.setIndicatorGravity(IndicatorConfig.Direction.RIGHT);
        banner.setIndicatorMargins(new IndicatorConfig.Margins(0, 0,
                BannerConfig.INDICATOR_MARGIN, (int) BannerUtils.dp2px(12)));
        banner.addBannerLifecycleObserver(this);
    }

}
