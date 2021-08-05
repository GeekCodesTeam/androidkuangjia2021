package com.example.slbappcomm.pop.huodong;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.slbappcomm.R;
import com.example.slbappcomm.utils.CommonUtils;
import com.geek.libbase.base.SlbBaseActivity;
import com.geek.libbase.widgets.NavigationBarUtil;
import com.geek.libutils.SlbLoginUtil;
import com.geek.libutils.data.MmkvUtils;
import com.haier.cellarette.libwebview.hois2.HiosHelper;

import me.jessyan.autosize.AutoSizeCompat;

public class AdCommImgActivity extends SlbBaseActivity {

    public static final String TAG_ADCOMMON_ID = "id1";// 广告id
    private String id1;
    private String url1;
    private String img1;
    private TextView tv_adJumps;
    private ImageView iv1;

    @Override
    public Resources getResources() {
        //需要升级到 v1.1.2 及以上版本才能使用 AutoSizeCompat
        AutoSizeCompat.autoConvertDensityOfGlobal((super.getResources()));//如果没有自定义需求用这个方法
        AutoSizeCompat.autoConvertDensity((super.getResources()), 667, false);//如果有自定义需求就用这个方法
        return super.getResources();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hios_adcomm_img_activity;
    }

    @Override
    protected void setup(@Nullable Bundle savedInstanceState) {
        super.setup(savedInstanceState);
        //虚拟键
        if (NavigationBarUtil.hasNavigationBar(this)) {
//            NavigationBarUtil.initActivity(getWindow().getDecorView());
            NavigationBarUtil.hideBottomUIMenu(this);
        }
        getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);// topbar
        //
        tv_adJumps = findViewById(R.id.tv_adJumps);
        iv1 = findViewById(R.id.iv1);
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_def_loading)
                .error(R.drawable.ic_def_loading)
                .fallback(R.drawable.ic_def_loading); //url为空的时候,显示的图片;
        Glide.with(this).load(img1).apply(options).into(iv1);
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPUtils.getInstance().put(TAG_ADCOMMON_ID, id1);
                url1 = getIntent().getStringExtra("ces");
                if (TextUtils.isEmpty(url1)) {
                    // 测试1
                    url1 = "https://www.baidu.com/";
                    // 测试2
                    url1 = "https://www.baidu.com/?condition=login";
                    // 测试3
//                url1 = "hios://" + AppUtils.getAppPackageName() + ".hs.act.slbapp.AdCommLoginAct{act}?act={i}1&sku_id={s}2a&category_id={s}3a";
                    // 测试4
//                url1 = "hios://" + AppUtils.getAppPackageName() + ".hs.act.slbapp.AdCommLoginAct{act}?act={i}1&sku_id={s}2a&category_id={s}3a&condition=login";
                }
                SlbLoginUtil.get().loginTowhere(AdCommImgActivity.this, new Runnable() {
                    @Override
                    public void run() {
                        HiosHelper.resolveAd(AdCommImgActivity.this, AdCommImgActivity.this, url1);
                    }
                });
                set_url_hios_finish(url1);
            }
        });
        tv_adJumps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPUtils.getInstance().put(TAG_ADCOMMON_ID, id1);
                finish();
            }
        });
//        Log.e("gongshi", (Math.sqrt(Math.pow(812, 2) + (Math.pow(375, 2))) / 25.4 + ""));
//        Log.e("gongshi", (Math.sqrt(Math.pow(640, 2) + (Math.pow(360, 2))) / 25.4 + ""));
        Log.e("gongshi", (Math.sqrt(Math.pow(667, 2) + (Math.pow(375, 2))) / 25.4 + ""));

    }

}
