package com.fosung.eduapi.view;

import com.fosung.eduapi.bean.EduResInfoBean;
import com.geek.libmvp.IView;
import com.haier.cellarette.libretrofit.common.ResponseSlbBean1;

public interface EduResExtendInfoView extends IView {

    void OnEduResExtendInfoSuccess(EduResInfoBean bean);
    void OnEduResExtendInfoFail(String msg);

}
