package com.fosung.xuanchuanlan.xuanchuanlan.main.http;

import android.content.Context;
import android.content.SharedPreferences;


import com.fosung.frameutils.http.ZHttp;
import com.fosung.frameutils.http.ZReply;
import com.fosung.frameutils.http.response.ZResponse;
import com.fosung.frameutils.util.GsonUtil;
import com.fosung.xuanchuanlan.common.app.ConfApplication;

import java.util.LinkedHashMap;
import java.util.Map;

public class XCLHttp {

    public static <T extends ZReply> String post(String url, Map<String, String> contentParams, ZResponse<T> response) {
        return ZHttp.postWithHeader(url, getCommonHeader(), contentParams, response);
    }

    public static <T extends ZReply> String get(String url, Map<String, String> contentParams, ZResponse<T> response) {
        return ZHttp.getWithHeader(url, getCommonHeader(), contentParams, response);
    }

    public static <T extends ZReply> String postJson(String url, String json, ZResponse<T> response) {
        return ZHttp.postJsonWithHeader(url, getCommonHeader(), json, response);
    }

    public static <T extends ZReply> String postJson(String url, Object jsonObj, ZResponse<T> response) {
        return ZHttp.postJsonWithHeader(url, getCommonHeader(), GsonUtil.beanToString(jsonObj), response);
    }

    /**
     * 取消请求
     */
    public static void cancelRequest(String... tag) {
         ZHttp.cancelRequest(tag);
    }


    private static LinkedHashMap<String, String> getCommonHeader() {
        LinkedHashMap<String, String> mapHeader = null;

        SharedPreferences sp = ConfApplication.APP_CONTEXT.getSharedPreferences("SystemInfo", Context.MODE_PRIVATE);
        String machineMac = sp.getString("machineMac", "");//客户端唯一标识

        SharedPreferences userSp = ConfApplication.APP_CONTEXT.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String orgCode = userSp.getString("user_orgCode", "");//用户组织

        String maId = sp.getString("machineId", "");//客户端id


        if (machineMac != null) {
            mapHeader = new LinkedHashMap<>();
            mapHeader.put("mac", machineMac);
            mapHeader.put("orgCode",orgCode);
            mapHeader.put("maId",maId);
        }
        return mapHeader;
    }
}
