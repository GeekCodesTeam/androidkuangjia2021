package com.fosung.eduapi.view;

import com.geek.libmvp.IView;
import com.haier.cellarette.libretrofit.common.ResponseSlbBean1;

public interface EduResLockAuditView extends IView {

    void OnEduResLockAuditSuccess(ResponseSlbBean1 bean);
    void OnEduResLockAuditFail(String msg);

}
