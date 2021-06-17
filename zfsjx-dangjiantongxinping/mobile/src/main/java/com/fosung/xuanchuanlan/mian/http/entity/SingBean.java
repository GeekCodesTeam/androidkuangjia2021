package com.fosung.xuanchuanlan.mian.http.entity;

import com.fosung.xuanchuanlan.mian.http.AppsBaseReplyBean;

public class SingBean extends AppsBaseReplyBean{

    /**
     * clientType :
     * clientUserId :
     * createTime :
     * meetingNo :
     * userInfo : [{"orgCode":"","orgId":"","orgName":"","userId":"","userName":""}]
     */

    public String clientType;
    public String clientUserId;
    public String createTime;
    public String meetingNo;
    public String rtnInfo;

    public static class UserInfoBean {
        /**
         * orgCode :
         * orgId :
         * orgName :
         * userId :
         * userName :
         */

        public String orgCode;
        public String orgId;
        public String orgName;
        public String userId;
        public String userName;
    }
}
