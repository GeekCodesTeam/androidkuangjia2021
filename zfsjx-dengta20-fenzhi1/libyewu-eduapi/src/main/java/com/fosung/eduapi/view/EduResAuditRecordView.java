package com.fosung.eduapi.view;

import com.fosung.eduapi.bean.EduResAuditRecordBean;
import com.geek.libmvp.IView;

public interface EduResAuditRecordView extends IView {

    void OnEduResAuditRecordSuccess(EduResAuditRecordBean bean);
    void OnEduResAuditRecordFail(String msg);

}
