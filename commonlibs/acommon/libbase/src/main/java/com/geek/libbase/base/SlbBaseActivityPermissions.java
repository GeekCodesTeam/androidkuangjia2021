package com.geek.libbase.base;

import android.Manifest;


public class SlbBaseActivityPermissions extends SlbBaseActivityPermissionsBase {
    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.REQUEST_INSTALL_PACKAGES,
            Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO
    };

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected String[] YouNeedPermissions() {
        return needPermissions;
    }

    @Override
    protected boolean is_android10() {
        return false;
    }

}
