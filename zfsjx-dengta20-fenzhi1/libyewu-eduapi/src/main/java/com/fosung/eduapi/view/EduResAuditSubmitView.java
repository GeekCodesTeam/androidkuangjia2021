package com.fosung.eduapi.view;

import com.fosung.eduapi.bean.EduResAuditRecordBean;
import com.geek.libmvp.IView;
import com.haier.cellarette.libretrofit.common.ResponseSlbBean1;

public interface EduResAuditSubmitView extends IView {

    void OnEduResAuditSubmitSuccess(ResponseSlbBean1 bean);
    void OnEduResAuditSubmitFail(String msg);

}
