package com.fosung.xuanchuanlan.mian.http.entity;

import com.fosung.xuanchuanlan.mian.http.AppsBaseReplyBean;

/**
 * 录入校验身份证号，返回的用户信息
 */
public class InputUserInfoReply extends AppsBaseReplyBean {


    public DataBean data;

    public static class DataBean {

        public String userIdentityId;//身份证号
        public String userName;//姓名
        public String orgId;
        public String orgName;//党组织名称
        public boolean result;
    }
}
