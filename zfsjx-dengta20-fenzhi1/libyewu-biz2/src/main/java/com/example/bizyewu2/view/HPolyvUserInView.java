package com.example.bizyewu2.view;

import com.haier.cellarette.libmvp.mvp.IView;

public interface HPolyvUserInView extends IView {

    void OnPolyvUserInSuccess(String bean);

    void OnPolyvUserInNodata(String bean);

    void OnPolyvUserInFail(String msg);

}
