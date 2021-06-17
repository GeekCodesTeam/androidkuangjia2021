package com.fosung.xuanchuanlan.xuanchuanlan.main.http.entity;

import com.fosung.xuanchuanlan.mian.http.AppsBaseReplyBean;

import java.util.List;

public class FeatureTravel extends AppsBaseReplyBean {

    public List<DatalistBean> datalist;

    public static class DatalistBean {
        /**
         "createDatetime": "2019-09-16 15:51:14",
         "imgurl": "http://s.dtdjzx.gov.cn/cloud/oss/download/ebox-test/20190916/3368586385492555684.jpg",
         "lastUpdateDatetime": "2019-09-16 15:51:14",
         "id": "3368586388344680948"
         */

        public String createDatetime;
        public String lastUpdateDatetime;
        public String id;
        public String remark;
        public String addr;
    }
}
