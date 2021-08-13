package com.fosung.xuanchuanlan.xuanchuanlan.localprofile.http;

import com.fosung.xuanchuanlan.mian.http.AppsBaseReplyBean;

import java.util.List;

public class ServiceTeamListReply extends AppsBaseReplyBean {

    public List<DatalistBean> datalist;


    public class DatalistBean {
         /**
          "img": "http://s.dtdjzx.gov.cn/cloud/oss/download/ebox/20191015/3373946766670954921.jpg",
          "tell": "0531-459987521",
          "num": 8,
          "orgId": null,
          "createDatetime": "2019-09-02 19:43:59",
          "lastUpdateDatetime": "2019-10-15 13:11:29",
          "orgCode": "000002000008000001000011000001000002000302000017",
          "name": "单晓兵",
          "position": "支部书记、董事长",
          "id": "3366018781749984930",
          "introduction": "主持支部党建工作，全面负责公司业务。"
         */

        public String id;
        public String introduction;
        public String createDatetime;
        public String lastUpdateDatetime;
        public String title;
        public String tell;
        public String position;
        public int num;
        public String img;
        public String orgCode;
        public String name;

    }
}
