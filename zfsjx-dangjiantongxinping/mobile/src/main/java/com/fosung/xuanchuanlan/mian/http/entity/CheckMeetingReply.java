package com.fosung.xuanchuanlan.mian.http.entity;

import com.fosung.xuanchuanlan.mian.http.AppsBaseReplyBean;
import com.google.gson.annotations.SerializedName;

public class CheckMeetingReply extends AppsBaseReplyBean{


    /**
     * data : {"videoNo":"103929","roleId":"2","ifJoin":"true","meetingInfo":{"id":"3361350416439708279","createUserId":null,"createDatetime":"2019-08-08 15:52:42","lastUpdateUserId":null,"lastUpdateDatetime":"2019-08-08 15:52:42","userId":"111111","videoSub":"测试主题333","videoNo":"103929","userCount":100,"orgId":"1111","orgCode":"22222","orgName":"测试","status":"NOTSTART","hopeStartDate":"2019-08-08 16:08:04","hopeEndDate":"2019-08-08 17:08:04","realStartDate":null,"realEndDate":null,"remark":"备注","onlineCount":2,"roomType":"HOPE","ifSubOrg":"NOT","subOrgId":null,"subOrgCode":null,"ifOis":"AGREE","ifMute":"AGREE","ifMicrophone":"NOT","ifNo":"AGREE","ifVideo":"NOT","ifScreenshot":"NOT","ifPeopleNum":"NOT","ifSign":"AGREE","sourceSystem":null},"message":"成功"}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * videoNo : 103929
         * roleId : 2
         * ifJoin : true
         * meetingInfo : {"id":"3361350416439708279","createUserId":null,"createDatetime":"2019-08-08 15:52:42","lastUpdateUserId":null,"lastUpdateDatetime":"2019-08-08 15:52:42","userId":"111111","videoSub":"测试主题333","videoNo":"103929","userCount":100,"orgId":"1111","orgCode":"22222","orgName":"测试","status":"NOTSTART","hopeStartDate":"2019-08-08 16:08:04","hopeEndDate":"2019-08-08 17:08:04","realStartDate":null,"realEndDate":null,"remark":"备注","onlineCount":2,"roomType":"HOPE","ifSubOrg":"NOT","subOrgId":null,"subOrgCode":null,"ifOis":"AGREE","ifMute":"AGREE","ifMicrophone":"NOT","ifNo":"AGREE","ifVideo":"NOT","ifScreenshot":"NOT","ifPeopleNum":"NOT","ifSign":"AGREE","sourceSystem":null}
         * message : 成功
         */

        public String videoNo;
        public String roleId;
        public String ifJoin;
        public MeetingInfoBean meetingInfo;
        @SerializedName("message")
        public String messageX;

        public static class MeetingInfoBean {
            /**
             * id : 3361350416439708279
             * createUserId : null
             * createDatetime : 2019-08-08 15:52:42
             * lastUpdateUserId : null
             * lastUpdateDatetime : 2019-08-08 15:52:42
             * userId : 111111
             * videoSub : 测试主题333
             * videoNo : 103929
             * userCount : 100
             * orgId : 1111
             * orgCode : 22222
             * orgName : 测试
             * status : NOTSTART
             * hopeStartDate : 2019-08-08 16:08:04
             * hopeEndDate : 2019-08-08 17:08:04
             * realStartDate : null
             * realEndDate : null
             * remark : 备注
             * onlineCount : 2
             * roomType : HOPE
             * ifSubOrg : NOT
             * subOrgId : null
             * subOrgCode : null
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
            public Object createUserId;
            public String createDatetime;
            public Object lastUpdateUserId;
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
            public Object realStartDate;
            public Object realEndDate;
            public String remark;
            public int onlineCount;
            public String roomType;
            public String ifSubOrg;
            public Object subOrgId;
            public Object subOrgCode;
            public String ifOis;
            public String ifMute;
            public String ifMicrophone;
            public String ifNo;
            public String ifVideo;
            public String ifScreenshot;
            public String ifPeopleNum;
            public String ifSign;
            public Object sourceSystem;
        }
    }
}
