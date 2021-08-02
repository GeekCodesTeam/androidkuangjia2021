package com.fosung.eduapi.view;

import com.fosung.eduapi.bean.EduResourceListBean;
import com.haier.cellarette.libmvp.mvp.IView;

public interface EduResListView extends IView {

    void OnEduResListSuccess(EduResourceListBean bean);
    void OnEduResListFail(String msg);

}
