package com.fosung.xuanchuanlan.xuanchuanlan.notice.http.entity;

import com.fosung.xuanchuanlan.mian.http.AppsBaseReplyBean;

import java.util.List;

public class NoticeTypeReply extends AppsBaseReplyBean {

    public List<DatalistBean> datalist;


    public class DatalistBean {
         /**
         "createDatetime": "2019-10-17 11:07:47",
         "lastUpdateDatetime": "2019-10-17 11:07:47",
         "code": "3374301686309861879",
         "num": 99,
         "name": "最新通知",
         "remark": "",
         "id": "3374301686309861879",
         "typename": "最新通知"
         */

        public String id;
        public String code;
        public String createDatetime;
        public String name;
        public String lastUpdateDatetime;
        public String remark;
        public String typename;
        public int num;
    }
}
