package com.geek.biz1.view;

import com.geek.biz1.bean.SbbdBean;
import com.geek.libmvp.IView;

public interface SbbdView extends IView {
    void OnSbbdSuccess(SbbdBean versionInfoBean);

    void OnSbbdNodata(String bean);

    void OnSbbdFail(String msg);
}
