package com.fosung.xuanchuanlan.mian.http.entity;

import com.fosung.xuanchuanlan.mian.http.AppsBaseReplyBean;

import java.util.List;

public class VoidListReply extends AppsBaseReplyBean {

    public List<DatalistBean> datalist;

    public static class DatalistBean {
        /**
         * videoNo : 333333
         * realStartDate : 2019-06-25 10:14:04
         * hopeEndDate : 2019-06-25 11:04:04
         * onlineCount : 2
         * orgName : 测试
         * videoSub : 测试主题
         * realEndDate : 2019-06-25 10:15:04
         * remark : 备注
         * orgId : 1111
         * roomType_dict : 固定会议室
         * ifNo : AGREE
         * createDatetime : 2019-06-25 10:17:58
         * lastUpdateDatetime : 2019-06-25 10:17:58
         * userCount : 100
         * ifNo_dict : 是
         * orgCode : 22222
         * status_dict : 未开始
         * id : 3353143412684489828
         * hopeStartDate : 2019-06-25 10:04:04
         * roomType : FIXED
         * status : NOTSTART
         */

        public String videoNo;
        public String realStartDate;
        public String hopeEndDate;
        public int onlineCount;
        public String orgName;
        public String videoSub;
        public String realEndDate;
        public String remark;
        public String orgId;
        public String roomType_dict;
        public String ifNo;
        public String createDatetime;
        public String lastUpdateDatetime;
        public int userCount;
        public String ifNo_dict;
        public String orgCode;
        public String status_dict;
        public String id;
        public String hopeStartDate;
        public String roomType;
        public String status;
        public int attendance;
        public int sum;

    }
}
