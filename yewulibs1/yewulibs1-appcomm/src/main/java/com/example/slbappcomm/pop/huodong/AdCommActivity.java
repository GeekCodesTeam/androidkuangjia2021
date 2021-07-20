package com.example.slbappcomm.pop.huodong;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.slbappcomm.R;
import com.example.libbase.widgets.NavigationBarUtil;
import com.haier.cellarette.libwebview.base.WebViewActivity;

import me.jessyan.autosize.AutoSizeCompat;

public class AdCommActivity extends WebViewActivity implements View.OnClickListener {

    public static final String TAG = AdCommActivity.class.getSimpleName();
    public static final String TAG_ADCOMMON_ID = "id1";// 广告id
    private String url1;
    private String id1;
    private TextView tv_adJumps;

    @Override
    public Resources getResources() {
        //需要升级到 v1.1.2 及以上版本才能使用 AutoSizeCompat
        AutoSizeCompat.autoConvertDensityOfGlobal((super.getResources()));//如果没有自定义需求用这个方法
        AutoSizeCompat.autoConvertDensity((super.getResources()), 667, false);//如果有自定义需求就用这个方法
        return super.getResources();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hios_adcommactivity);
//        //虚拟键
        if (NavigationBarUtil.hasNavigationBar(this)) {
//            NavigationBarUtil.initActivity(getWindow().getDecorView());
            NavigationBarUtil.hideBottomUIMenu(this);
        }
        getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);// topbar
        //
        setUp();

//        id1 = getIntent().getExtras().getString("id1");
        url1 = getIntent().getExtras().getString("url1");
        loadUrl(url1);
        //
        tv_adJumps = findViewById(R.id.tv_adJumps);
        tv_adJumps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SPUtils.getInstance().put(TAG_ADCOMMON_ID, id1);
                finish();
            }
        });
//        Log.e("gongshi", (Math.sqrt(Math.pow(812, 2) + (Math.pow(375, 2))) / 25.4 + ""));
//        Log.e("gongshi", (Math.sqrt(Math.pow(640, 2) + (Math.pow(360, 2))) / 25.4 + ""));
        Log.e("gongshi", (Math.sqrt(Math.pow(667, 2) + (Math.pow(375, 2))) / 25.4 + ""));

    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
//            this.finish();
//            return true;
//        } else {
////            this.finish();
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        set_destory();
        finish();
    }


}
