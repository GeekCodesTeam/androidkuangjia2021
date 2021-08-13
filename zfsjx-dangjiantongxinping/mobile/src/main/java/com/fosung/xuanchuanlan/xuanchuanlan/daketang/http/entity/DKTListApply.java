package com.fosung.xuanchuanlan.xuanchuanlan.daketang.http.entity;

import com.fosung.xuanchuanlan.mian.http.AppsBaseReplyBean;

import java.util.List;

public class DKTListApply extends AppsBaseReplyBean {
    public List<DatalistBean> datalist;

    public static class DatalistBean {
                      /**
                        "createDatetime": "2019-09-26 09:58:15",
                        "lastUpdateDatetime": "2019-09-26 09:58:15",
                        "code": "3370396332677150213",
                        "num": 99,
                        "name": "党的历史",
                        "remark": "",
                        "id": "3370396332677150213",
                        "typename": "党的历史"
                       */

        public String code;
        public String createDatetime;
        public String lastUpdateDatetime;
        public int num;
        public String name;
        public String remark;
        public String id;
        public String typename;
    }
}
