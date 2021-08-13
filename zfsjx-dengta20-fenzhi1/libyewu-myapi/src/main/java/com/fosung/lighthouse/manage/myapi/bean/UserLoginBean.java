package com.fosung.lighthouse.manage.myapi.bean;

import com.haier.cellarette.libretrofit.common.ResponseSlbBean1;

import java.io.Serializable;
import java.util.List;

/**
 * @author: lhw
 * @date: 2021/8/9
 * @desc
 */
public class UserLoginBean extends ResponseSlbBean1 implements Serializable {

    public UserInfoBean data;

    public static class UserInfoBean {
        public String userId;
        public String name;
        public String telephone;
        public String logo;
        public String lastLoginDate;
        public Long lastLoginTime;
        public String hash;
        public List<RolesBean> roles;
        public String idCardHash;
        public String accessToken;

        public static class RolesBean {
            public String roleId;
            public String roleName;
            public String roleDescription;
            public String manageId;
            public String manageName;
            public String manageCode;
            public String clientId;
            public Integer num;
            public Object terminalAudit;
            public Object terminalSubmit;
            public List<?> rolePart;
            public List<PermissionDtosBean> permissionDtos;

            public static class PermissionDtosBean {
                public String permissionId;
                public String permissionName;
                public String permissionDescription;
                public String clientId;
            }
        }
    }


}
