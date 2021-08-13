package com.fosung.eduapi.view;

import com.fosung.eduapi.bean.EduResourceDetailBean;
import com.geek.libmvp.IView;

public interface EduResDetailView extends IView {

    void OnEduResDetailSuccess(EduResourceDetailBean bean);
    void OnEduResDetailFail(String msg);

}
