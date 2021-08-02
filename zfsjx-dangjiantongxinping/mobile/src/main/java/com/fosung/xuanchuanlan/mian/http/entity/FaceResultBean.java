package com.fosung.xuanchuanlan.mian.http.entity;

import java.util.List;

public class FaceResultBean extends FaceBaseBean {
    public List<ResultBean> result;



    public static class ResultBean {
        /**
         * groupId :
         * userId : Unknown
         * userInfo :
         * userName : Unknown
         */
        public String groupId;
        public String userId;
        public String userInfo;
        public String userName;
    }

    public static class UserInfo {
        /**
         * userId : 6365702234688638976
         * userName : 刘*
         * phone : 18668951108
         * orgId : 226834
         * orgCode : 000002000008000001000011000001000002000302000017
         * orgName : 中共山东福生佳信科技股份有限公司支部委员会
         */

        public String userId;
        public String userName;
        public String phone;
        public String orgId;
        public String orgCode;
        public String orgName;
    }
}
