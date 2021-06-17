package com.fosung.xuanchuanlan.setting.http;


import com.fosung.frameutils.http.ZReply;

import java.util.List;

public class UserInfoReply implements ZReply {
    public String    id;
    public String name;
    public String telephone;
    public List<OrgBean> orgs;
    public List<AuthBean> auths;

    public  static  class AuthBean {
        public String roleCode;
        public String roleName;
        public String orgId;
        public String orgCode;
        public String orgSource;
        public String orgName;
    }

    public  static  class OrgBean {
        public String name;
        public String code;
        public String source;
        public String outId;
        public String id;
    }


    @Override
    public boolean isSuccess() {
        return id!=null;
    }

    @Override
    public int getReplyCode() {
        return 0;
    }

    @Override
    public String getErrorMessage() {
        return null;
    }
}
