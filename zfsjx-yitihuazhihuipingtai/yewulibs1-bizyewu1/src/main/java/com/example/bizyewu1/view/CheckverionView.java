package com.example.bizyewu1.view;

import com.example.bizyewu1.bean.VersionInfoBean;
import com.geek.libmvp.IView;

public interface CheckverionView extends IView {
    void OnUpdateVersionSuccess(VersionInfoBean versionInfoBean);
    void OnUpdateVersionNodata(String bean);
    void OnUpdateVersionFail(String msg);
}
