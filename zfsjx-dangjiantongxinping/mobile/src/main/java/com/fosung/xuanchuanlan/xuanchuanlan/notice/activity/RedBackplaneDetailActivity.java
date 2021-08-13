package com.fosung.xuanchuanlan.xuanchuanlan.notice.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.fosung.frameutils.imageloader.ImageLoaderUtils;
import com.fosung.xuanchuanlan.R;
import com.fosung.xuanchuanlan.common.base.ActivityParam;
import com.fosung.xuanchuanlan.common.base.BaseActivity;

@ActivityParam(isShowToolBar = false)
public class RedBackplaneDetailActivity extends BaseActivity {

    private ImageView redImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_backplane_detail);
        redImageView = (ImageView) findViewById(R.id.id_red_iamgeview);
        String imgURL = getIntent().getStringExtra("url");
        ImageLoaderUtils.displayImage(mActivity, imgURL, redImageView);
        redImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RedBackplaneDetailActivity.this.finish();
            }
        });
    }
}
