//package com.example.slbappcomm.pop.bottompay;
//
//import android.app.Activity;
//import android.content.Context;
//import android.view.Gravity;
//import android.view.View;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import androidx.fragment.app.FragmentManager;
//
//import com.example.biz3slbappcomm.bean.SPayBean;
//import com.example.biz3slbappcomm.presenter.SPayPresenter;
//import com.example.biz3slbappcomm.view.SPayView;
//import com.example.slbappcomm.R;
//import com.example.slbappcomm.pop.anspay.PopYanzhengPay;
//import com.example.slbapppay.yinlian.YlPay;
//import com.haier.cellarette.baselibrary.qcode.ExpandViewRect;
//import com.haier.cellarette.baselibrary.toasts2.Toasty;
//
//import me.shaohui.bottomdialog.BottomDialog;
//
//import static me.shaohui.bottomdialog.BottomDialog.create;
//
////import androidx.core.app.FragmentManager;
//
///**
// * Created by shaohui on 16/10/11.
// */
//
//public class ShareBottomDialog implements BottomDialog.ViewListener, View.OnClickListener {
//
//    public static final String CHOOSE_TAG1 = "wechat";
//    public static final String CHOOSE_TAG2 = "alipay";
//    private TextView tv_del31;
//    private RelativeLayout rl1;
//    private RelativeLayout rl2;
//    private TextView tv11;
//    private TextView tv1;
//    private TextView tv2;
//    private TextView tv3;
//
//    private Context context;
////    private AliPay aliPay;
////    private WxPay wxPay;
////    private WxPay2 wxPay;
//    private YlPay ylPay;
//    private String tag_choose;// alipay为支付宝，wechat为微信
//    private String prices1_id = "";
//    private String prices1 = "";
//
//    private BottomDialog shareBottomDialog;
//    private SPayPresenter presenter;
//    private SPayBean sPayBean;
//    private PopYanzhengPay pay_pop;
//
//    public ShareBottomDialog(Context context) {
//        this.context = context;
//    }
//
//    public void setDataShow(FragmentManager fragmentManager, String prices1_id, String prices1) {
//        this.prices1_id = prices1_id;
//        this.prices1 = prices1;
//        if (shareBottomDialog != null && shareBottomDialog.isAdded()) {
//            return;
//        }
//        shareBottomDialog = create(fragmentManager);
//        shareBottomDialog.setLayoutRes(R.layout.activity_bottom_pay)
//                .setDimAmount(0.7f)            // Dialog window dim amount(can change window background color）, range：0 to 1，default is : 0.2f
//                .setCancelOutside(false)     // click the external area whether is closed, default is : true
//                .setTag("BottomPayDialog");     // setting the DialogFragment tag;
//        shareBottomDialog.setViewListener(this);
//        shows();
//    }
//
//    @Override
//    public void bindView(View v) {
//        // do any thing you want
//        tv_del31 = v.findViewById(R.id.tv_del31);
//        rl1 = v.findViewById(R.id.rl1);
//        rl2 = v.findViewById(R.id.rl2);
//        tv11 = v.findViewById(R.id.tv11);
//        tv1 = v.findViewById(R.id.tv1);
//        tv2 = v.findViewById(R.id.tv2);
//        tv3 = v.findViewById(R.id.tv3);
//        tv_del31.setOnClickListener(this);
//        rl1.setOnClickListener(this);
//        rl2.setOnClickListener(this);
//        tv3.setOnClickListener(this);
//        ExpandViewRect.expandViewTouchDelegate(tv_del31, 10, 10, 10, 10);
//        tv1.setPressed(true);
//        tv2.setPressed(false);
//        tv11.setText(prices1);
////        tv11.setText(SPUtils.getInstance().getString("dialog_prices"));
//
//    }
//
//
//    @Override
//    public void onClick(View view) {
//        int i = view.getId();
//        if (i == R.id.tv_del31) {
//            //
//            diss();
//        } else if (i == R.id.rl1) {
//            set_wechat_choose();
//        } else if (i == R.id.rl2) {
//            // 选择支付宝bufen
//            set_alipay_choose();
//        } else if (i == R.id.tv3) {
//            // 支付bufen
//            pay_pop = new PopYanzhengPay((Activity) context, new PopYanzhengPay.OnFinishResultClickListener() {
//                @Override
//                public void onSuccess() {
//                    presenter = new SPayPresenter();
//                    presenter.onCreate(new SPayView() {
//                        @Override
//                        public void OnPaySuccess(SPayBean sPayBeans) {
//                            sPayBean = sPayBeans;
//                            if (mListener != null) {
//                                mListener.onOKClick(tag_choose, sPayBean);
//                            }
//                            presenter.onDestory();
//                            pay_pop.dismiss();
//
//                        }
//
//                        @Override
//                        public void OnPayNodata(String s) {
//                            Toasty.normal(context, s).show();
//                            pay_pop.dismiss();
//                        }
//
//                        @Override
//                        public void OnPayFail(String s) {
//                            Toasty.normal(context, s).show();
//                            pay_pop.dismiss();
//                        }
//
//                        @Override
//                        public String getIdentifier() {
//                            return System.currentTimeMillis() + "";
//                        }
//                    });
//                    presenter.getPayData("", "", "1", tag_choose, prices1_id);
//
//                }
//
//                @Override
//                public void onFail() {
//                    // 验证码错误重试
//
//                }
//            });
//            pay_pop.showAtLocation(((Activity) context).getWindow().getDecorView(),
//                    Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL
//                    , 0, 0); // 设置layout在PopupWindow中显示的位置
//            diss();
//        } else {
//
//        }
//    }
//
//    public void shows() {
//        // 接口bufen
//        sPayBean = new SPayBean();
//        tag_choose = CHOOSE_TAG1;
////        aliPay = new AliPay(context);
////        wxPay = new WxPay(context);
//        ylPay = new YlPay(context);
//        if (shareBottomDialog != null && shareBottomDialog.isAdded()) {
//            return;
//        }
////        presenter = new SPayPresenter();
////        presenter.onCreate(this);
////        presenter.getPayData(DeviceUtils.getAndroidID(), SPUtils.getInstance().getString(CommonUtils.USER_TOKEN),
////                "", "", "1", tag_choose, prices1_id);
//        shareBottomDialog.show();
//
//    }
//
//    public void diss() {
////        SPUtils.getInstance().put("dialog_prices", "");
//        shareBottomDialog.dismiss();
//    }
//
//    private OnOKClickListener mListener;
//
//    public void setShareBottomDialogListener(OnOKClickListener mListener) {
//        this.mListener = mListener;
//    }
////
////    @Override
////    public void OnPaySuccess(SPayBean sPayBean) {
////        this.sPayBean = sPayBean;
////        if (mListener != null) {
////            mListener.onOKClick(tag_choose, sPayBean);
////        }
////        pay_pop.dismiss();
////
////    }
////
////    @Override
////    public void OnPayNodata(String s) {
////        Toasty.normal(BaseApp.get(), s).show();
////    }
////
////    @Override
////    public void OnPayFail(String s) {
////        Toasty.normal(BaseApp.get(), s).show();
////    }
////
////    @Override
////    public String getIdentifier() {
////        return System.currentTimeMillis() + "";
////    }
//
//    public interface OnOKClickListener {
//        void onOKClick(String tag_choose, SPayBean bean);
//    }
//
//    //yl 回调
////    @Override
////    public void onActivityResult(int requestCode, int resultCode, Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
////        ylPay.onActResult(requestCode, resultCode, data);
////
////    }
//
//    private void set_wechat_choose() {
//        tv2.setBackgroundResource(R.drawable.choose_no3);
//        tv1.setBackgroundResource(R.drawable.choose_green3);
//        tag_choose = CHOOSE_TAG1;
//    }
//
//    private void set_alipay_choose() {
//        tv1.setBackgroundResource(R.drawable.choose_no3);
//        tv2.setBackgroundResource(R.drawable.choose_green3);
//        tag_choose = CHOOSE_TAG2;
//    }
//
//}