package com.example.bizyewu2.view;

import com.geek.libmvp.IView;

public interface HErweimaView extends IView {

    void OnErweimaSuccess(String bean);
    void OnErweimaNodata(String bean);
    void OnErweimaFail(String msg);

}
