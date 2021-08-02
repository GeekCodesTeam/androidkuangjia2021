package com.example.slbappcomm.pop.bottompay.wechatutils;

//import android.support.annotation.DrawableRes;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class PopWeChatBean {
    private String appid;
    private String partnerid;
    private String noncestr;
    private long timestamp;
    private String prepayid;
    private String sign;

    public PopWeChatBean() {
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
