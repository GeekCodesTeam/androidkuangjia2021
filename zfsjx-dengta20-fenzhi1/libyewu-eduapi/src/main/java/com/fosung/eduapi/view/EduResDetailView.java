package com.fosung.eduapi.view;

import com.fosung.eduapi.bean.EduResourceDetailBean;
import com.haier.cellarette.libmvp.mvp.IView;

public interface EduResDetailView extends IView {

    void OnEduResDetailSuccess(EduResourceDetailBean bean);
    void OnEduResDetailFail(String msg);

}
