package com.example.slbappindex;

import android.text.TextUtils;

import com.example.slbappcomm.utils.BanbenCommonUtils;

public class Act {
    public void test() {
        //版本判断
        if (TextUtils.equals(BanbenCommonUtils.banben_comm, "测试")) {

        } else if (TextUtils.equals(BanbenCommonUtils.banben_comm, "预生产")) {

        } else if (TextUtils.equals(BanbenCommonUtils.banben_comm, "线上")) {

        }
        //获取版本地址
        String aa1 = BanbenCommonUtils.dizhi1_comm;
        String aa2 = BanbenCommonUtils.dizhi2_comm;
        //修改版本地址
        BanbenCommonUtils.changeUrl(null, null, null, null);
        BanbenCommonUtils.changeUrl_ondes();
    }
}
