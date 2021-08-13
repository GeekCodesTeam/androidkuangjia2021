package com.fosung.eduapi.view;

import com.fosung.eduapi.bean.EduResCompanyExecutorReplyBean;
import com.fosung.eduapi.bean.EduResExecutorReplyBean;
import com.geek.libmvp.IView;

public interface EduResCompanyView extends IView {

    void OnEduResCompanySuccess(EduResCompanyExecutorReplyBean bean);
    void OnEduResCompanyFail(String msg);

}
