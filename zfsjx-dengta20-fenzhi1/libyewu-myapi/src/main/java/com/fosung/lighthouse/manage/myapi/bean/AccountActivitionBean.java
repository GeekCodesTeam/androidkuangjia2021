package com.fosung.lighthouse.manage.myapi.bean;

import com.haier.cellarette.libretrofit.common.ResponseSlbBean1;

import java.io.Serializable;

/**
 * @author: lhw
 * @date: 2021/8/5
 * @desc 账号激活
 */
public class AccountActivitionBean extends ResponseSlbBean1 implements Serializable {

    public DataBean data;

    public static class DataBean implements Serializable {
        public String telePhoneFlag;
        public String idCardFlag;
        public String authorizationCodeFlag;
        public String nameFlag;
        public String verifyCodeFlag;
    }
}
