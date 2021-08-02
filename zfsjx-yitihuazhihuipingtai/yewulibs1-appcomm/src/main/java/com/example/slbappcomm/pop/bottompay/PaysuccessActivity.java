//package com.example.slbappcomm.pop.bottompay;
//
//import android.os.Bundle;
////
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//
//import com.blankj.utilcode.util.SPUtils;
//
//import com.example.biz3slbappcomm.bean.SPaySuccessBean;
//import com.example.biz3slbappcomm.presenter.SPaySuccessPresenter;
//import com.example.biz3slbappcomm.view.SPaySuccessView;
//import com.haier.cellarette.libutils.CommonUtils;
//import com.example.slbappcomm.R;
//import com.example.libbase.base.SlbBaseActivity;
//import com.example.slbappcomm.utils.LoginImgUtils;
//import com.geek.libglide47.base.GlideImageView;
//
//public class PaysuccessActivity extends SlbBaseActivity implements SPaySuccessView {
//
//    private LinearLayout ll1;
//    private GlideImageView iv1;
//    private TextView tv1;
//    private TextView tv2;
//    private ImageView iv2;
//
//    private SPaySuccessPresenter presenter;
//
//    @Override
//    protected void onDestroy() {
//        presenter.onDestory();
//        super.onDestroy();
//    }
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_paysuccess;
//    }
//
//    @Override
//    protected void setup(@Nullable Bundle savedInstanceState) {
//        super.setup(savedInstanceState);
//        findview();
//        onclick();
//        donetwork();
//    }
//
//    private void donetwork() {
//        LoginImgUtils.getInstance(getApplicationContext()).get_head_icon(iv1);
//        tv1.setText(TextUtils.isEmpty(SPUtils.getInstance().getString(CommonUtils.USER_NAME)) ?
//                (TextUtils.isEmpty(SPUtils.getInstance().getString(CommonUtils.USER_TEL)) ? "访客" : SPUtils.getInstance().getString(CommonUtils.USER_TEL)) : SPUtils.getInstance().getString(CommonUtils.USER_NAME));
//        presenter = new SPaySuccessPresenter();
//        presenter.onCreate(this);
//        presenter.getPaySuccessData();
//    }
//
//    private void onclick() {
//        iv2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//    }
//
//    private void findview() {
//        iv1 = findViewById(R.id.iv1);
//        tv1 = findViewById(R.id.tv1);
//        tv2 = findViewById(R.id.tv2);
//        iv2 = findViewById(R.id.iv2);
//
//    }
//
//    @Override
//    public void OnPaySuccessSuccess(SPaySuccessBean sPaySuccessBean) {
//        iv1.loadImage(sPaySuccessBean.getAvatar(), R.drawable.head_moren3);
//        tv1.setText(sPaySuccessBean.getNickName());
//        tv2.setText(sPaySuccessBean.getEndDateString());
//    }
//
//    @Override
//    public void OnPaySuccessNodata(String s) {
//        iv1.loadImage("", R.drawable.head_moren3);
//        tv1.setText("");
//        tv2.setVisibility(View.GONE);
//    }
//
//    @Override
//    public void OnPaySuccessFail(String s) {
//        iv1.loadImage("", R.drawable.head_moren3);
//        tv1.setText("");
//        tv2.setVisibility(View.GONE);
//    }
//}
