package com.geek.biz1.view;

import com.geek.biz1.bean.HLoginBean;
import com.geek.libmvp.IView;

public interface HYonghudengluView extends IView {

    void OnYonghudengluSuccess(HLoginBean bean);

    void OnYonghudengluNodata(String bean);

    void OnYonghudengluFail(String msg);

}
