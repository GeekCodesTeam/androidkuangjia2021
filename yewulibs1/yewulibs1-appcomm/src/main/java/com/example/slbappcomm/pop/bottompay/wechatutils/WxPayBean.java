package com.example.slbappcomm.pop.bottompay.wechatutils;

import android.content.Context;

import com.example.slbapppay.wx.Constants;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WxPayBean {
    private IWXAPI api;
    private PopWeChatBean bean;

    public WxPayBean(Context context, PopWeChatBean bean) {
        this.bean = bean;
        api = WXAPIFactory.createWXAPI(context, Constants.APP_ID);
    }

    public void pay() {
        // 本地逻辑
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
        // 接口逻辑
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
    }

}
