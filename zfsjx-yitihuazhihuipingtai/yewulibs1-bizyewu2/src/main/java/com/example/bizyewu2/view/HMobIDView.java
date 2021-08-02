package com.example.bizyewu2.view;

import com.haier.cellarette.libmvp.mvp.IView;

public interface HMobIDView extends IView {

    void OnMobIDSuccess(String bean);
    void OnMobIDNodata(String bean);
    void OnMobIDFail(String msg);

}
