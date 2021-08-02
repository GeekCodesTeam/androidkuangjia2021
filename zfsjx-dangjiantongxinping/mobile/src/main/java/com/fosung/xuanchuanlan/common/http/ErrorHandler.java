/*
 * *********************************************************
 *   author   colin
 *   company  fosung
 *   email    wanglin2046@126.com
 *   date     17-10-9 下午12:58
 * ********************************************************
 */

package com.fosung.xuanchuanlan.common.http;

import com.fosung.frameutils.http.response.ZResponse;
import com.fosung.frameutils.util.NetworkUtil;
import com.fosung.xuanchuanlan.common.app.ConfApplication;
import com.google.gson.JsonSyntaxException;

import java.net.SocketTimeoutException;

/**
 * 程序的主报文已经默认实现了此逻辑，但是85的报文如果使用代理返回的话需要手动调用此方法
 */
public class ErrorHandler {
    public static void handleError(Exception ex, int code, ZResponse zResponse) {
        String str;
        if (ex instanceof SocketTimeoutException || code == 0) {
            if (!NetworkUtil.isNetworkAvailable(ConfApplication.APP_CONTEXT)) {
                str = "当前无网络连接，请开启网络";
            } else {
                str = "连接服务器失败, 请检查网络或稍后重试";
            }
            zResponse.onError(0, str);
        } else if (ex instanceof JsonSyntaxException) {
            zResponse.onError(-1, "json conversion failed, code is : -1");
        } else {
            zResponse.onError(code, ex == null ? null : ex.getMessage());
        }
    }
}
