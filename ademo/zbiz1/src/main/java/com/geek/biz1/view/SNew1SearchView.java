package com.geek.biz1.view;

import com.geek.biz1.bean.SNew1SearchBean;
import com.geek.libmvp.IView;

public interface SNew1SearchView extends IView {

    void OnNew1SearchSuccess(SNew1SearchBean bean, int which);
    void OnNew1SearchNodata(String bean, int which);
    void OnNew1SearchFail(String msg, int which);

}
