package com.fosung.xuanchuanlan.xuanchuanlan.main.activity;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.fosung.xuanchuanlan.R;
import com.zcolin.gui.ZBanner;

import java.util.ArrayList;

public class XCLAdvertActivity extends Service {

    private ZBanner banner;
    private ArrayList<Object> picList = new ArrayList<Object>();

    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private View mView;

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        layoutParams.gravity = Gravity.CENTER;                                       //设置窗口的位置
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;                //窗口的宽
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showFloatingWindow();
        return super.onStartCommand(intent, flags, startId);
    }

    private void showFloatingWindow() {

        mView = LayoutInflater.from(this).inflate(R.layout.activity_xcladvert, null);

        banner = (ZBanner) mView.findViewById(R.id.id_ad_banner);
//        picList.add(R.drawable.banner7);
        picList.add("http://cdn2.cdn.haier-jiuzhidao.com/banner7.jpg");//
        initBanner(picList);

        windowManager.addView(mView, layoutParams);
    }

    /**
     * 初始化轮播图
     */
    private void initBanner(final ArrayList<Object> listPicData) {


        banner.setBannerStyle(ZBanner.NOT_INDICATOR)
                .setIndicatorGravity(ZBanner.CENTER)
                .setDelayTime(4000)
                .setOnBannerClickListener(new ZBanner.OnBannerClickListener() {
                    @Override
                    public void OnBannerClick(View view, int position) {
                        if (windowManager != null) {
                            windowManager.removeView(mView);
                            stopSelf();
                        }
                    }
                })
                .setImages(listPicData)
                .startAutoPlay();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent();
        intent.setAction("serviceStop.reportsucc");
        sendBroadcast(intent);
    }
}
