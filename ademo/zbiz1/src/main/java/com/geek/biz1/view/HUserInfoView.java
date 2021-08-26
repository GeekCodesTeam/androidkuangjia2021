package com.geek.biz1.view;

import com.geek.biz1.bean.HUserInfoBean;
import com.geek.libmvp.IView;

public interface HUserInfoView extends IView {

    void OnUserInfoSuccess(HUserInfoBean bean);

    void OnUserInfoNodata(String bean);

    void OnUserInfoFail(String msg);

}
