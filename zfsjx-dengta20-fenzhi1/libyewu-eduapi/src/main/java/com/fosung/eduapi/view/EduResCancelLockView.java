package com.fosung.eduapi.view;

import com.geek.libmvp.IView;
import com.haier.cellarette.libretrofit.common.ResponseSlbBean1;

public interface EduResCancelLockView extends IView {

    void OnEduResCancelLockSuccess(ResponseSlbBean1 bean);
    void OnEduResCancelLockFail(String msg);

}
