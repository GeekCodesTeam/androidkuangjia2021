package com.fosung.xuanchuanlan.mian.http.entity;

import com.fosung.xuanchuanlan.mian.http.AppsBaseReplyBean;

public class CreateMeetingReply extends AppsBaseReplyBean{


    /**
     * data : {"id":"3361301924254058465","createUserId":null,"createDatetime":"2019-08-08 09:36:21","lastUpdateUserId":null,"lastUpdateDatetime":"2019-08-08 09:36:21","userId":"6235388959684366336","videoSub":"20190808测试下级组织人员","videoNo":"103897","userCount":100,"orgId":"3351011518316546410","orgCode":"000002000008000001000011000001000002000302000017","orgName":"中共山东福生佳信科技股份有限公司支部委员会","status":"GOING","hopeStartDate":"2019-08-08 09:36:21","hopeEndDate":"2019-08-08 10:36:21","realStartDate":"2019-08-08 09:36:21","realEndDate":null,"remark":null,"onlineCount":null,"roomType":"HOPE","ifSubOrg":"AGREE","subOrgId":"3351011518316546410","subOrgCode":"000002000008000001000011000001000002000302000017","ifOis":"AGREE","ifMute":"AGREE","ifMicrophone":"NOT","ifNo":"AGREE","ifVideo":"NOT","ifScreenshot":"NOT","ifPeopleNum":"NOT","ifSign":"AGREE","sourceSystem":null}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * id : 3361301924254058465
         * createUserId : null
         * createDatetime : 2019-08-08 09:36:21
         * lastUpdateUserId : null
         * lastUpdateDatetime : 2019-08-08 09:36:21
         * userId : 6235388959684366336
         * videoSub : 20190808测试下级组织人员
         * videoNo : 103897
         * userCount : 100
         * orgId : 3351011518316546410
         * orgCode : 000002000008000001000011000001000002000302000017
         * orgName : 中共山东福生佳信科技股份有限公司支部委员会
         * status : GOING
         * hopeStartDate : 2019-08-08 09:36:21
         * hopeEndDate : 2019-08-08 10:36:21
         * realStartDate : 2019-08-08 09:36:21
         * realEndDate : null
         * remark : null
         * onlineCount : null
         * roomType : HOPE
         * ifSubOrg : AGREE
         * subOrgId : 3351011518316546410
         * subOrgCode : 000002000008000001000011000001000002000302000017
         * ifOis : AGREE
         * ifMute : AGREE
         * ifMicrophone : NOT
         * ifNo : AGREE
         * ifVideo : NOT
         * ifScreenshot : NOT
         * ifPeopleNum : NOT
         * ifSign : AGREE
         * sourceSystem : null
         */

        public String id;
        public String createUserId;
        public String createDatetime;
        public String lastUpdateUserId;
        public String lastUpdateDatetime;
        public String userId;
        public String videoSub;
        public String videoNo;
        public int userCount;
        public String orgId;
        public String orgCode;
        public String orgName;
        public String status;
        public String hopeStartDate;
        public String hopeEndDate;
        public String realStartDate;
        public String realEndDate;
        public String remark;
        public String onlineCount;
        public String roomType;
        public String ifSubOrg;
        public String subOrgId;
        public String subOrgCode;
        public String ifOis;
        public String ifMute;
        public String ifMicrophone;
        public String ifNo;
        public String ifVideo;
        public String ifScreenshot;
        public String ifPeopleNum;
        public String ifSign;
        public int screenshotInterval;
        public String sourceSystem;
    }
}
