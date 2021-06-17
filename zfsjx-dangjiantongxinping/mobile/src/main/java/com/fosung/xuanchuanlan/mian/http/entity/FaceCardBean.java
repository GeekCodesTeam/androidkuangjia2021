package com.fosung.xuanchuanlan.mian.http.entity;

import com.fosung.xuanchuanlan.mian.http.AppsBaseReplyBean;

public class FaceCardBean extends AppsBaseReplyBean{

    /**
     * data : {"idCard":"","name":"","orgId":"","orgCode":"","orgName":""}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * idCard :
         * name :
         * orgId :
         * orgCode :
         * orgName :
         */

        public String idCard;
        public String name;
        public String orgId;
        public String orgCode;
        public String orgName;
    }
}
