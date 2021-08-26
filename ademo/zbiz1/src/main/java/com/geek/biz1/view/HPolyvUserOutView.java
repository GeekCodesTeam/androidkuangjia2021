package com.geek.biz1.view;

import com.geek.libmvp.IView;

public interface HPolyvUserOutView extends IView {

    void OnPolyvUserOutSuccess(String bean);

    void OnPolyvUserOutNodata(String bean);

    void OnPolyvUserOutFail(String msg);

}
