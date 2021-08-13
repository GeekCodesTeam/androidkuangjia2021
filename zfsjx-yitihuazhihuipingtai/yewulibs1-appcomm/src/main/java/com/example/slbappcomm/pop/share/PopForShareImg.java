//package com.example.slbappcomm.pop.share;
//
//import android.app.Activity;
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Build;
//import android.os.SystemClock;
//import android.text.Html;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.PopupWindow;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
//import com.example.biz3slbappcomm.bean.SMyMedalDetailShareBean;
//import com.example.biz3slbappcomm.presenter.SMyMedalDetailSharePresenter;
//import com.example.biz3slbappcomm.view.SMyMedalDetailShareView;
//import com.example.slbappcomm.R;
//import com.example.slbappcomm.jiechangtu.DownloadShareImgUtil;
//import com.example.slbappcomm.jiechangtu.ShareBitmapUtils;
//import com.example.slbappcomm.jiechangtu.ShareImgCutUtil;
//import com.example.slbappshare.fenxiang.JPushShareUtils;
//import com.example.slbappshare.fenxiang.OnShareResultInfoLitener;
//import com.example.slbappshare.fenxiang.beans.WeixinBeanParam;
//import com.geek.libglide47.base.CircleImageView;
//import com.geek.libutils.ConstantsUtils;
//import com.google.zxing.WriterException;
//import com.haier.cellarette.baselibrary.qcode.QrCodeUtil;
//import com.haier.cellarette.baselibrary.toasts2.Toasty;
//import com.haier.cellarette.baselibrary.zothers.NavigationBarUtil;
//
//import cn.jiguang.share.android.api.JShareInterface;
//import cn.jiguang.share.wechat.Wechat;
//import cn.jiguang.share.wechat.WechatMoments;
//
//// 分享图片弹窗
//public class PopForShareImg extends PopupWindow implements OnShareResultInfoLitener, View.OnClickListener, SMyMedalDetailShareView {
//    private Activity activity;
//    private View mMenuView;
//    private JPushShareUtils jPushShareUtils;
//    private DownloadShareImgUtil downloadShareImgUtil;
//
//    private RelativeLayout cl_content;
//    private CircleImageView headIv;
//    private TextView nameTv;
//    private TextView detailTv;
//    private CircleImageView centerMedalIv;//勋章图
//    private ImageView qrcodeIv;//二维码
//
//    private LinearLayout ll_shareWxFriend;
//    private LinearLayout ll_shareWxCircle;
//    private LinearLayout ll_savePic;
//    private TextView cancelTv;
//    private String medalId;
//    private SMyMedalDetailSharePresenter sMyMedalDetailSharePresenter;
//
//    public PopForShareImg(final Activity activity, String medalId, Bitmap bitmap, final OnFinishResultClickListener mListener) {
//        super(activity);
//        this.activity = activity;
//        this.medalId = medalId;
//        jPushShareUtils = new JPushShareUtils(this);
//        downloadShareImgUtil = new DownloadShareImgUtil();
//
//        LayoutInflater inflater = (LayoutInflater) activity
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        mMenuView = inflater.inflate(R.layout.pop_share_img2, null);
//        headIv = mMenuView.findViewById(R.id.headIv);
//        nameTv = mMenuView.findViewById(R.id.nameTv);
//        detailTv = mMenuView.findViewById(R.id.detailTv);
//        qrcodeIv = mMenuView.findViewById(R.id.qrcodeIv);
//        ll_shareWxFriend = mMenuView.findViewById(R.id.ll_shareWxFriend);
//        ll_shareWxCircle = mMenuView.findViewById(R.id.ll_shareWxCircle);
//        ll_savePic = mMenuView.findViewById(R.id.ll_savePic);
//        cancelTv = mMenuView.findViewById(R.id.cancelTv);
//        cl_content = mMenuView.findViewById(R.id.cl_content);
//        centerMedalIv = mMenuView.findViewById(R.id.centerMedalIv);
//        setListener();
//
//        // 设置SelectPicPopupWindow的View
//        this.setContentView(mMenuView);
//        // 设置SelectPicPopupWindow弹出窗体的宽
//        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//        // 设置SelectPicPopupWindow弹出窗体的高
//        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//        // 设置SelectPicPopupWindow弹出窗体可点击
//        this.setFocusable(true);
//        // 设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.AnimBottom);
//        // 实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(0xb0000000);
//////        // 设置SelectPicPopupWindow弹出窗体的背景
//        this.setBackgroundDrawable(dw);
//        sMyMedalDetailSharePresenter = new SMyMedalDetailSharePresenter();
//        sMyMedalDetailSharePresenter.onCreate(this);
//
//        setOnDismissListener(new OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                jPushShareUtils.ondes();
//                sMyMedalDetailSharePresenter.onDestory();
//            }
//        });
//        showPopupWindow();
//
//        donetwork();
//    }
//
//    private void donetwork() {
//        sMyMedalDetailSharePresenter.getMyMedalDetailShareData(medalId);
//    }
//
//    private void setListener() {
//        ll_shareWxFriend.setOnClickListener(this);
//        ll_shareWxCircle.setOnClickListener(this);
//        ll_savePic.setOnClickListener(this);
//        cancelTv.setOnClickListener(this);
//        cl_content.setOnClickListener(this);
//        mMenuView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
////        ll_animation_view1.setOnClickListener(this);
//    }
//
//    public void showPopupWindow() {
////        showAtLocation(activity.getWindow().getDecorView(),
////                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL
////                , 0, 0); // 设置layout在PopupWindow中显示的位置
//        setFocusable(false);
//        update();
//        showAtLocation(activity.getWindow().getDecorView(), Gravity.NO_GRAVITY, 0, 0);
//        NavigationBarUtil.fullScreenImmersive(getContentView());
//        update();
//    }
//
//    public void set_wechat(String name, Bitmap bitmap) {
//        // 极光分享用bitmap失败改用url
////        downloadShareImgUtil.downloadPicture(false, activity, bitmap, CommonUtils.img_file_url, CommonUtils.img_file_name2);
////        JShareInterface.share(name, WeixinBeanParam.share_img1(CommonUtils.img_file_url + CommonUtils.img_file_name2), jPushShareUtils.mShareListener1);
//        // new
//        String aaaa = SystemClock.currentThreadTimeMillis() + ".jpg";
//        downloadShareImgUtil.downloadPicture(false, activity, bitmap, ConstantsUtils.file_url_img, aaaa);
//        JShareInterface.share(name, WeixinBeanParam.share_img1(ConstantsUtils.file_url_img + aaaa), jPushShareUtils.mShareListener1);
//    }
//
//    @Override
//    public void onResults(String platform, String toastMsg, String data) {
//        Toasty.normal(activity, toastMsg).show();
//        jPushShareUtils.ondes();
//        dismiss();
//    }
//
//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        if (id == R.id.ll_shareWxFriend) {
//            Bitmap bitmap = ShareBitmapUtils.getViewBitmap(cl_content);
//            if (bitmap != null)
//                set_wechat(Wechat.Name, bitmap);
//        } else if (id == R.id.ll_shareWxCircle) {
//            Bitmap bitmap = ShareImgCutUtil.getRelativeLayoutBitmap(cl_content);
//            if (bitmap != null)
//                set_wechat(WechatMoments.Name, bitmap);
//        } else if (id == R.id.ll_savePic) {
//            Bitmap bitmap = ShareImgCutUtil.getRelativeLayoutBitmap(cl_content);
//            if (bitmap == null) {
//                return;
//            }
////            downloadShareImgUtil.downloadPicture(true, activity, bitmap, CommonUtils.img_file_url, CommonUtils.img_file_name2);// 保存最近
//            downloadShareImgUtil.downloadPicture(true, activity, bitmap, ConstantsUtils.file_url_img, SystemClock.currentThreadTimeMillis() + ".jpg");// 保存多张
//            dismiss();
//        } else if (id == R.id.cancelTv) {
//            dismiss();
//        }
//    }
//
//    @Override
//    public void OnMyMedalDetailShareSuccess(SMyMedalDetailShareBean sMyMedalDetailShareBean) {
//        RequestOptions options = new RequestOptions()
//                .placeholder(R.drawable.head_moren3)
//                .error(R.drawable.head_moren3)
//                .fallback(R.drawable.head_moren3); //url为空的时候,显示的图片;
//        Glide.with(activity).load(sMyMedalDetailShareBean.getDetails().getAvatar()).apply(options).into(headIv);
//        nameTv.setText(sMyMedalDetailShareBean.getDetails().getNickName());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            detailTv.setText(Html.fromHtml(sMyMedalDetailShareBean.getDetails().getTips(), Html.FROM_HTML_MODE_COMPACT));
//        } else {
//            detailTv.setText(Html.fromHtml(sMyMedalDetailShareBean.getDetails().getTips()));
//        }
////        detailTv.setText(sMyMedalDetailShareBean.getDetails().getTips());
//        Glide.with(activity).load(sMyMedalDetailShareBean.getDetails().getImgUrl()).into(centerMedalIv);
//        try {
//            Bitmap qrCodeBmp = QrCodeUtil.createQRImage(sMyMedalDetailShareBean.getDetails().getShareUrl(), 200, 200, BitmapFactory.decodeResource(activity.getResources(), R.drawable.slb_logo3));
//            qrcodeIv.setImageBitmap(qrCodeBmp);
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void OnMyMedalDetailShareNodata(String s) {
//
//    }
//
//    @Override
//    public void OnMyMedalDetailShareFail(String s) {
//
//    }
//
//    @Override
//    public String getIdentifier() {
//        return SystemClock.currentThreadTimeMillis() + "";
//    }
//
//    public interface OnFinishResultClickListener {
//        void onWeChat1();
//
//        void onWeChat2();
//    }
//
//}
