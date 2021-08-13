package com.example.bizyewu2.view;

import com.geek.libmvp.IView;

public interface HMobIDView extends IView {

    void OnMobIDSuccess(String bean);
    void OnMobIDNodata(String bean);
    void OnMobIDFail(String msg);

}
