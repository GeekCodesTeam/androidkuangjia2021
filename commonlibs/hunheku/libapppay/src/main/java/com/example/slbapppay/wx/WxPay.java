//package com.example.slbapppay.wx;
//
//import android.content.Context;
//import android.widget.Toast;
//
//import com.example.slbapppay.Pay;
//import com.haier.jiuzhidao.biz2_phone_pay_wx2.bean.WxBean;
//import com.haier.jiuzhidao.biz2_phone_pay_wx2.presenter.MyWxPresenter;
//import com.haier.jiuzhidao.biz2_phone_pay_wx2.view.GetWxView;
//import com.tencent.mm.opensdk.modelpay.PayReq;
//import com.tencent.mm.opensdk.openapi.IWXAPI;
//import com.tencent.mm.opensdk.openapi.WXAPIFactory;
//
//
//public class WxPay implements Pay<WxBean> {
//    private IWXAPI api;
//    private MyWxPresenter myWxPresenter = new MyWxPresenter();
//    private Context mContext;
//
//    public WxPay(Context context) {
//        api = WXAPIFactory.createWXAPI(context, Constants.APP_ID);
//        mContext = context;
//    }
//
//    @Override
//    public void pay() {
//        myWxPresenter.onCreate((GetWxView) mContext);
//        myWxPresenter.getCode();
//    }
//
//    public void onDes() {
//        myWxPresenter.onDestory();
//    }
//
//    @Override
//    public void success(WxBean bean) {
//        PayReq req = new PayReq();
//        req.appId = bean.getAppid();
//        req.partnerId = bean.getPartnerid();
//        req.prepayId = bean.getPrepayid();
//        req.nonceStr = bean.getNoncestr();
//        req.timeStamp = String.valueOf(bean.getTimestamp());
//        req.packageValue = "Sign=WXPay";
//        req.sign = bean.getSign();
//        req.extData = "app data"; // optional
//        api.sendReq(req);
//    }
//
//    @Override
//    public void error(String s) {
//        Toast.makeText(mContext, s, Toast.LENGTH_LONG).show();
//        PayReq req = new PayReq();
//        req.appId = "wx2421b1c4370ec43b";
//        req.partnerId = "1230000109";
//        req.prepayId = "wx201410272009395522657a690389285100";
//        req.nonceStr = "5K8264ILTKCH16CQ2502SI8ZNMTM67VS";
//        req.timeStamp = String.valueOf(20091225091010L);
//        req.packageValue = "Sign=WXPay";
//        req.sign = "0CB01533B8C1EF103065174F50BCA001";
//        req.extData = "app data"; // optional
//        api.sendReq(req);
//    }
//}
