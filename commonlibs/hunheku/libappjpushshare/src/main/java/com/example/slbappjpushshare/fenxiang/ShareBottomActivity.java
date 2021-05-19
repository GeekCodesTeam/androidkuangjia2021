package com.example.slbappjpushshare.fenxiang;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
//import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.slbappjpushshare.R;
import com.example.slbappjpushshare.fenxiang.beans.WeixinBeanParam;

import cn.jiguang.share.android.api.JShareInterface;
import cn.jiguang.share.qqmodel.QQ;
import cn.jiguang.share.qqmodel.QZone;
import cn.jiguang.share.wechat.Wechat;
import cn.jiguang.share.wechat.WechatFavorite;
import cn.jiguang.share.wechat.WechatMoments;

public class ShareBottomActivity extends Activity implements View.OnClickListener, OnShareResultInfoLitener {

    private String TAG = this.getClass().getSimpleName();
    private LinearLayout ll1;
    private LinearLayout ll1a;
    private LinearLayout ll2;
    private LinearLayout ll2a;
    private LinearLayout ll3;
    private LinearLayout ll3a;
    private LinearLayout ll4;
    private LinearLayout ll4a;
    private LinearLayout ll5;
    private LinearLayout ll5a;
    private LinearLayout ll6;
    private LinearLayout ll6a;
    private LinearLayout ll7;
    private LinearLayout ll7a;
    private LinearLayout llMail;
    private JPushShareUtils jPushShareUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_bottom);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        findViewId();

        mHandler = new Handler();
        jPushShareUtils = new JPushShareUtils(this);
    }

    private void findViewId() {
        ll1 = findViewById(R.id.ll1);
        ll1a = findViewById(R.id.ll1a);
        ll2 = findViewById(R.id.ll2);
        ll2a = findViewById(R.id.ll2a);
        ll3 = findViewById(R.id.ll3);
        ll3a = findViewById(R.id.ll3a);
        ll4 = findViewById(R.id.ll4);
        ll4a = findViewById(R.id.ll4a);
        ll5 = findViewById(R.id.ll5);
        ll5a = findViewById(R.id.ll5a);
        ll6 = findViewById(R.id.ll6);
        ll6a = findViewById(R.id.ll6a);
        ll7 = findViewById(R.id.ll7);
        ll7a = findViewById(R.id.ll7a);
        llMail = findViewById(R.id.ll_mail);

        ll1.setOnClickListener(this);
        ll2.setOnClickListener(this);
        ll3.setOnClickListener(this);
        ll4.setOnClickListener(this);
        ll5.setOnClickListener(this);
        ll6.setOnClickListener(this);
        ll7.setOnClickListener(this);

    }

    String name = "";
    private Handler mHandler;

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.ll1) {
            name = Wechat.Name;
        } else if (i == R.id.ll2) {
            name = WechatMoments.Name;
        } else if (i == R.id.ll3) {
            name = WechatFavorite.Name;
        } else if (i == R.id.ll4) {
            name = QQ.Name;
        } else if (i == R.id.ll5) {
            name = QZone.Name;
        } else if (i == R.id.ll6) {
//            name = SinaWeibo.Name;
        } else if (i == R.id.ll7) {
//            name = SinaWeiboMessage.Name;
        }
        //复制assets图片到本地路径bufen
//        ShareFileCopyAssets.fagnfa();
//        JShareInterface.share(name,
//                WeixinBeanParam.share_web(
//                        JPushShareUtils.share_title,
//                        JPushShareUtils.share_text,
//                        JPushShareUtils.share_url,
//                        ShareFileCopyAssets.ImagePath),
//                mShareListener);
        //本地drawable转bitmap
//        JShareInterface.share(name,
//                WeixinBeanParam.share_web2(
//                        JPushShareUtils.share_title,
//                        JPushShareUtils.share_text,
//                        JPushShareUtils.share_url,
//                        JPushShareUtils.drawableToBitmap2(ShareBottomActivity.this, getDrawable(R.drawable.geek_icon))),
//                mShareListener);
        //null转bitmap 默认为icon_launcher
//        JShareInterface.share(name,
//                WeixinBeanParam.share_web2(
//                        JPushShareUtils.share_title,
//                        JPushShareUtils.share_text,
//                        JPushShareUtils.share_url,
//                        null),
//                mShareListener);
        //本地drawable转bitmap
        JShareInterface.share(name,
                WeixinBeanParam.share_web2(
                        JPushShareUtils.share_title,
                        JPushShareUtils.share_text,
                        JPushShareUtils.share_url,
                        JPushShareUtils.drawableToBitmap(ShareBottomActivity.this, getDrawable(R.drawable.geek_icon))),
                jPushShareUtils.mShareListener1);
        //
//        JShareInterface.share(name,
//                WeixinBeanParam.share_wenben(),
//                jPushShareUtils.mShareListener1);
        //网络图片转bitmap
//        JShareInterface.share(name,
//                WeixinBeanParam.share_web2(
//                        JPushShareUtils.share_title,
//                        JPushShareUtils.share_text,
//                        JPushShareUtils.share_url,
//                        JPushShareUtils.returnBitMap(JPushShareUtils.share_imageurl)),
//                mShareListener);
    }

    @Override
    protected void onDestroy() {
        jPushShareUtils.ondes();
        super.onDestroy();

    }


    @Override
    public void onResults(String platform, String toastMsg, String data) {
        Toast.makeText(getApplicationContext(), toastMsg, Toast.LENGTH_LONG).show();
        finish();
    }
}
