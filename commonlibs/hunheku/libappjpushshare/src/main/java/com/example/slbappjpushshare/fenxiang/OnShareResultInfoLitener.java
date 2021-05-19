package com.example.slbappjpushshare.fenxiang;

/**
 * 授权登录的回调接口
 *
 * @author geek
 */
public interface OnShareResultInfoLitener {
    void onResults(String platform, String toastMsg, String data);
}