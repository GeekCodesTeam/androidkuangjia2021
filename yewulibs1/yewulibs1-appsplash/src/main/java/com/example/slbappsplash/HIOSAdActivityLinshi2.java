package com.example.slbappsplash;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.haier.cellarette.baselibrary.zothers.NavigationBarUtil;
import com.haier.cellarette.libwebview.base.WebViewActivity;

public class HIOSAdActivityLinshi2 extends WebViewActivity implements View.OnClickListener {

    public static final String TAG = HIOSAdActivityLinshi2.class.getSimpleName();
    private String url;
    private ImageView ic_back;
    private ImageView close;
    private TextView title;
    private TextView title2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hios_adactivitylinshi2);

        getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);// topbar

        findview();
        onclickListener();
        setupWebView();
        url = getIntent().getExtras().getString("linshihuiben2");
        loadUrl(url);
        //
        ic_back = findViewById(R.id.ic_back);
        close = findViewById(R.id.close);
        title = findViewById(R.id.title);
        title2 = findViewById(R.id.title2);
        title.setVisibility(View.GONE);
        close.setVisibility(View.INVISIBLE);
        title2.setText("合象思维");
        Log.e("gongshi", (Math.sqrt(Math.pow(667, 2) + (Math.pow(375, 2))) / 25.4 + ""));
        //虚拟键
        if (NavigationBarUtil.hasNavigationBar(this)) {
//            NavigationBarUtil.initActivity(getWindow().getDecorView());
            NavigationBarUtil.hideBottomUIMenu(this);
        }
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
        set_destory();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ic_back || id == R.id.back) {
            onBackPressed();
            return;
        }

        if (id == R.id.close) {
            finish();
        }
    }

}
