package com.fosung.eduapi.view;

import com.fosung.eduapi.bean.EduResourceExamTypeBean;
import com.geek.libmvp.IView;

public interface EduResExamTypeView extends IView {

    void OnEduResExamTypeSuccess(EduResourceExamTypeBean bean);
    void OnEduResExamTypeFail(String msg);

}
