package com.haier.cellarette.libwebview.base;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.haier.cellarette.libwebview.R;

public class AdCommPart1Activity extends WebViewActivity {

    public static final String TAG = AdCommPart1Activity.class.getSimpleName();
    private String url1;
    private String id1;
    private TextView tv_adJumps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adcommpart1);
//        //虚拟键
//        if (NavigationBarUtil.hasNavigationBar(this)) {
////            NavigationBarUtil.initActivity(getWindow().getDecorView());
//            NavigationBarUtil.hideBottomUIMenu(this);
//        }
//        getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);// topbar
        //
        setUp();

        id1 = getIntent().getExtras().getString("id1");
        url1 = getIntent().getExtras().getString("url1");
        loadUrl(url1);
        //
        tv_adJumps = findViewById(R.id.tv_adJumps3);
        tv_adJumps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SPUtils.getInstance().put("id1", id1);
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
