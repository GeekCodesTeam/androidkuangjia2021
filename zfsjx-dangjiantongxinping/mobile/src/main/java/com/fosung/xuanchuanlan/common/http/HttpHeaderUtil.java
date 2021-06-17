/*
 * *********************************************************
 *   author   colin
 *   company  fosung
 *   email    wanglin2046@126.com
 *   date     17-10-9 下午12:45
 * ********************************************************
 */

package com.fosung.xuanchuanlan.common.http;

import com.fosung.frameutils.http.ZHttp;
import com.fosung.frameutils.http.ZReply;
import com.fosung.frameutils.http.response.ZResponse;
import com.fosung.frameutils.http.response.ZStringResponse;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 带有通用Header的HttpUtil的代理，此类主要是给主报文使用
 */
public class HttpHeaderUtil {
    /**
     * 普通get请求，回调返回字符串
     */
    public static String get(String url, ZStringResponse response) {
        return ZHttp.getWithHeader(url, getCommonHeader(), response);
    }

    /**
     * 带有参数的普通get请求，回调返回字符串
     */
    public static String get(String url, Map<String, String> contentParams, ZStringResponse response) {
        return ZHttp.getWithHeader(url, getCommonHeader(), contentParams, response);
    }

    /**
     * 带有参数的普通get请求，回调返回字符串
     */
    public static String get(String url, Object contentParams, ZStringResponse response) {
        return ZHttp.getWithHeader(url, getCommonHeader(), contentParams, response);
    }

    /**
     * 普通get请求，回调返回字符串
     */
    public static <T extends ZReply> String get(String url, ZResponse<T> response) {
        return ZHttp.getWithHeader(url, getCommonHeader(), null, response);
    }

    /**
     * 带有参数的普通get请求，回调返回字符串
     */
    public static <T extends ZReply> String get(String url, Map<String, String> contentParams, ZResponse<T> response) {
        return ZHttp.getWithHeader(url, getCommonHeader(), contentParams, response);
    }

    /**
     * 带有参数的普通get请求，回调返回字符串
     */
    public static <T extends ZReply> String get(String url, Object contentParams, ZResponse<T> response) {
        return ZHttp.getWithHeader(url, getCommonHeader(), contentParams, response);
    }

    /**
     * 带有参数的普通post请求，回调返回字符串
     */
    public static String post(String url, Map<String, String> contentParams, ZStringResponse response) {
        return ZHttp.postWithHeader(url, getCommonHeader(), contentParams, response);
    }

    /**
     * 带有参数的普通post请求，回调返回字符串
     */
    public static String post(String url, Object contentParams, ZStringResponse response) {
        return ZHttp.postWithHeader(url, getCommonHeader(), contentParams, response);
    }

    /**
     * 带有参数的普通post请求，回调返回字符串
     */
    public static <T extends ZReply> String post(String url, Map<String, String> contentParams, ZResponse<T> response) {
        return ZHttp.postWithHeader(url, getCommonHeader(), contentParams, response);
    }

    /**
     * 带有参数的普通post请求，回调返回字符串
     */
    public static <T extends ZReply> String postJson(String url, Map<String, String> obj, ZResponse<T> response) {
        return ZHttp.postJsonWithHeader(url, getCommonHeader(), obj, response);
    }


    /**
     * 带有参数的普通post请求，回调返回字符串
     */
    public static <T extends ZReply> String post(String url, Object contentParams, ZResponse<T> response) {
        return ZHttp.postWithHeader(url, getCommonHeader(), contentParams, response);
    }

    public static <T extends ZReply> String uploadFile(String url, String key, File file, ZResponse<T> response) {
        Map<String, File> map = new HashMap<>();
        map.put(key, file);
        return ZHttp.uploadFileWithHeadr(url, getCommonHeader(), map, response);
    }

    /**
     * 上传多文件
     */
    public static <T extends ZReply> String uploadFiles(String url, Map<String, String> contentParams, String fileKey, File[] fileValue,
            ZResponse<T> response) {
        return ZHttp.uploadFile(url, contentParams, fileKey, fileValue, response);
    }

    /**
     * 上传多文件
     */
    public static <T extends ZReply> String uploadFiles(String url, Map<String, File> map, Map<String, String> map1, ZResponse<T> response) {
        return ZHttp.uploadFileWithHeadr(url, getCommonHeader(), map1, map, response);
    }

    private static LinkedHashMap<String, String> getCommonHeader() {
        LinkedHashMap<String, String> mapHeader = null;
/*        String hash = UserMgr.getHash();
        String strToken = UserMgr.getTokenFromeFile();
        String idCardHash = UserMgr.getIdCardHash();
        String strAccessToken = UserMgr.getSSOAccessTokenFromeFile();
        String strRefreshToken = UserMgr.getSSORefreshTokenFromeFile();
        if (strRefreshToken != null) {
            mapHeader = new LinkedHashMap<>();
            mapHeader.put("HASH", hash);
            mapHeader.put("X-Fs-Token", strToken);
            if (idCardHash != null) {
                mapHeader.put("idCardHash", idCardHash);
            }
            mapHeader.put("X-Fs-SSO-Access-Token", strAccessToken);
            mapHeader.put("X-Fs-SSO-Refresh-Token", strRefreshToken);
        }*/
        return mapHeader;
    }
}
