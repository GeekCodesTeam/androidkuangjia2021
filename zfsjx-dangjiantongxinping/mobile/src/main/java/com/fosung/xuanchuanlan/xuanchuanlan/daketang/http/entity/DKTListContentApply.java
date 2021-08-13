package com.fosung.xuanchuanlan.xuanchuanlan.daketang.http.entity;

import com.fosung.xuanchuanlan.mian.http.AppsBaseReplyBean;

import java.io.Serializable;
import java.util.List;

public class DKTListContentApply extends AppsBaseReplyBean {
    public List<DatalistBean> datalist;

    public static class DatalistBean implements Serializable {
                      /**
                       "id": "3370426038818449131",
                       "createUserId": null,
                       "createDatetime": "2019-09-26 13:48:48",
                       "lastUpdateUserId": null,
                       "lastUpdateDatetime": "2019-09-26 14:18:22",
                       "name": "信念",
                       "type": "3370396332677150213",
                       "counum": null,
                       "imgurl": "http://s.dtdjzx.gov.cn/cloud/oss/download/ebox/20190926/3370427908555280189.jpg",
                       "pubtime": "2019-09-26 14:18:22",
                       "status": "VALID",
                       "num": 99,
                       "selectedcourse": null,
                       "transStatus": "已发布"
                       */

        public String id;
        public String name;
        public String createDatetime;
        public String lastUpdateDatetime;
        public int num;
        public String imgurl;
        public String pubtime;
        public String transStatus;
        public String type;
        public String desc;
    }
}
