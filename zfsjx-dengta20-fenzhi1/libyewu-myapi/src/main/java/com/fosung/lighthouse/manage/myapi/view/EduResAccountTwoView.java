package com.fosung.lighthouse.manage.myapi.view;

import com.fosung.lighthouse.manage.myapi.bean.UserLoginBean;

public interface EduResAccountTwoView {

    void onEduResVpnUserInfoSuccess(UserLoginBean bean);

    void onEduResVpnUserInfoFail(String msg);

}
