package com.example.bizyewu2.view;

import com.example.bizyewu2.bean.SNew1SearchBean;
import com.haier.cellarette.libmvp.mvp.IView;

public interface SNew1SearchView extends IView {

    void OnNew1SearchSuccess(SNew1SearchBean bean, int which);
    void OnNew1SearchNodata(String bean, int which);
    void OnNew1SearchFail(String msg, int which);

}
