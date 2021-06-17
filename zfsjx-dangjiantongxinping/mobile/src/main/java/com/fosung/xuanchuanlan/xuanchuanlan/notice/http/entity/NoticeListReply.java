package com.fosung.xuanchuanlan.xuanchuanlan.notice.http.entity;

import com.fosung.xuanchuanlan.mian.http.AppsBaseReplyBean;

import java.util.List;

public class NoticeListReply extends AppsBaseReplyBean {

    public List<DatalistBean> datalist;


    public class DatalistBean {
         /**
          "id": "3366018135357393107",
          "createUserId": "6365702234688638976",
          "createDatetime": "2019-09-02 19:38:58",
          "lastUpdateUserId": "6365702234688638976",
          "lastUpdateDatetime": "2019-09-02 19:49:08",
          "title": "关于召开全体党员会议的通知",
          "type": "组织生活",
          "content": "全体党员:\n\n经党支部研究决定于3月4日（星期五）下午1:20第一节课召开全体党员大会，请与会人员妥善安排好课务并请准时参加！另请党员同志带好笔和党费（党费有调整）。\n\n党员会议内容：1、选举参加镇党委换届选举的代表；\n\n              2、推荐2010年度优秀党员；\n\n              3、缴纳党费。\n\n \n\n         常州市武进区湟里初级中学党支部\n\n           2011年3月1日",
          "status": "VALID",
          "recomStatus": "UNRECOM",
          "introduction": "此次直播活动的成功举行，得益于强有力的技术保障，支撑“灯塔大课堂”的正是7月1日当天正式开通的“灯塔党建云”。“灯塔党建云”由山东福生佳信科技股份有限公司作为云服务商搭建，是以“灯塔-党建在线”网络平台为基础，整合利用“灯塔-党建在线”硬件网络、软件系统，以及云计算、数据存储、灾备中心等资源，进行标准化、模块化配置，依托山东省电子政务云搭建的党建专有云平台。",
          "pubDatetime": "2019-09-02 19:49:08"
         */

        public String id;
        public String introduction;
        public String createDatetime;
        public String lastUpdateDatetime;
        public String title;
        public String content;
        public String status;
        public String recomStatus;
        public String pubDatetime;
        public String type;
        public int num;
        public String img;
        public String imgname;
        public String orgCode;
        public String name;
        public String imgurl;

    }
}
