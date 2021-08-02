package com.fosung.xuanchuanlan.xuanchuanlan.daketang.http.entity;

import com.fosung.xuanchuanlan.mian.http.AppsBaseReplyBean;

import java.util.List;

public class DKTCourseListReply extends AppsBaseReplyBean {

    public List<DKTCourseData> datalist;


    public class DKTCourseData {
//                "id": "1909261000215480006",
//                "createUserId": null,
//                "createDatetime": null,
//                "lastUpdateUserId": null,
//                "lastUpdateDatetime": null,
//                "courseId": "296",
//                "courseName": "信念",
//                "courseHour": "0.5",
//                "createTime": "",
//                "videoAddr": "http://v.dtdjzx.gov.cn/dyjy/video/2016/hjml/党的历史/LS-24信念.mp4",
//                "num": 99,
//                "specialId": "3370426038818449131"

        public String id;
        public String createUserId;
        public String createDatetime;
        public String lastUpdateUserId;
        public String lastUpdateDatetime;
        public String courseId;
        public String courseName;
        public String courseHour;
        public String createTime;
        public String videoAddr;
        public String picAddr;
        public int num;
        public String specialId;

    }
}
