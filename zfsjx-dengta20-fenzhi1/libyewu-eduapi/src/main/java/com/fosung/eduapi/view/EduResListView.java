package com.fosung.eduapi.view;

import com.fosung.eduapi.bean.EduResourceListBean;
import com.geek.libmvp.IView;

public interface EduResListView extends IView {

    void OnEduResListSuccess(EduResourceListBean bean);
    void OnEduResListFail(String msg);

}
