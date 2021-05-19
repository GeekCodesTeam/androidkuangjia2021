package com.example.slbappsplash;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.haier.cellarette.baselibrary.zothers.NavigationBarUtil;
import com.haier.cellarette.libwebview.base.WebViewActivity;

public class HIOSAdActivityLinshi extends WebViewActivity implements View.OnClickListener {

    public static final String TAG = HIOSAdActivityLinshi.class.getSimpleName();
    private String url;
    private TextView tv_adJumps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hios_adactivitylinshi);

        getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);// topbar

        findview();
        onclickListener();
        setupWebView();
        url = getIntent().getExtras().getString("linshihuiben");
        loadUrl(url);
        //
        tv_adJumps = findViewById(R.id.tv_adJumps);
        RelativeLayout.LayoutParams ll_param = (RelativeLayout.LayoutParams) tv_adJumps.getLayoutParams();
        Double w = new Double((ScreenUtils.getScreenWidth() * 0.3));
        Double h = new Double((ScreenUtils.getScreenHeight() * 0.3));
//        ll_param.width = w.intValue();
        ll_param.width = 150;
//        ll_param.height = h.intValue();
        ll_param.height = 150;
        ll_param.setMargins(30, 70, 10, 10);
        tv_adJumps.setLayoutParams(ll_param);
        tv_adJumps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        Log.e("gongshi", (Math.sqrt(Math.pow(812, 2) + (Math.pow(375, 2))) / 25.4 + ""));
//        Log.e("gongshi", (Math.sqrt(Math.pow(640, 2) + (Math.pow(360, 2))) / 25.4 + ""));
        Log.e("gongshi", (Math.sqrt(Math.pow(667, 2) + (Math.pow(375, 2))) / 25.4 + ""));
        //虚拟键
        if (NavigationBarUtil.hasNavigationBar(this)) {
//            NavigationBarUtil.initActivity(getWindow().getDecorView());
            NavigationBarUtil.hideBottomUIMenu(this);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            this.finish();
            return true;
        } else {
//            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        set_destory();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_adJumps) {

        } else {

        }
    }


}
