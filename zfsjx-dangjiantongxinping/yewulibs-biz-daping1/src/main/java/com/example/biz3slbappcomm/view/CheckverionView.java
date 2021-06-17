package com.example.biz3slbappcomm.view;

import com.example.biz3slbappcomm.bean.VersionInfoBean;
import com.haier.cellarette.libmvp.mvp.IView;

public interface CheckverionView extends IView {
    void OnUpdateVersionSuccess(VersionInfoBean versionInfoBean);
    void OnUpdateVersionNodata(String bean);
    void OnUpdateVersionFail(String msg);
}
