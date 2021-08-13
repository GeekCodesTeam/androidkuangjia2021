package com.example.bizyewu1.view;

import com.example.bizyewu1.bean.SbbdBean;
import com.geek.libmvp.IView;

public interface SbbdView extends IView {
    void OnSbbdSuccess(SbbdBean versionInfoBean);

    void OnSbbdNodata(String bean);

    void OnSbbdFail(String msg);
}
