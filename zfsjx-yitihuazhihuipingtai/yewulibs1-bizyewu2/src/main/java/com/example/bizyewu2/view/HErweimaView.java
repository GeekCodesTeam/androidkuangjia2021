package com.example.bizyewu2.view;

import com.haier.cellarette.libmvp.mvp.IView;

public interface HErweimaView extends IView {

    void OnErweimaSuccess(String bean);
    void OnErweimaNodata(String bean);
    void OnErweimaFail(String msg);

}
