package com.example.bizyewu2.view;

import com.example.bizyewu2.bean.HLoginBean;
import com.haier.cellarette.libmvp.mvp.IView;

public interface HYonghudengluView extends IView {

    void OnYonghudengluSuccess(HLoginBean bean);

    void OnYonghudengluNodata(String bean);

    void OnYonghudengluFail(String msg);

}
