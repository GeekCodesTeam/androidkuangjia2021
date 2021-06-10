package com.example.bizyewu2.view;

import com.haier.cellarette.libmvp.mvp.IView;

public interface HPolyvUserOutView extends IView {

    void OnPolyvUserOutSuccess(String bean);

    void OnPolyvUserOutNodata(String bean);

    void OnPolyvUserOutFail(String msg);

}
