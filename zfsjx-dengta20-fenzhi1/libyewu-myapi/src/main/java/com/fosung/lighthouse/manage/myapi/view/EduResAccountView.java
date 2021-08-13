package com.fosung.lighthouse.manage.myapi.view;

import com.geek.libmvp.IView;
import com.haier.cellarette.libretrofit.common.ResponseSlbBean1;

public interface EduResAccountView<T> extends IView {

    void OnEduResCommonSuccess(T bean);

    void OnEduResCommonFail(String msg);

}