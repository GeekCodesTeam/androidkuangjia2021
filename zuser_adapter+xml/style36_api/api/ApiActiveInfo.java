package com.haier.newmvp.api;

import com.haier.banben.VersionConfig;
import com.haier.cellarette.bean.CodeAndNickNameBean;
import com.haier.wine_commen.html.ServiceAddr;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Author: YuanFan
 * E-mail: yuanfan@smart-haier.com
 * Date: on 2018/1/4/0004.
 * Description:获取酒柜激活信息API
 */

public interface ApiActiveInfo {
    /**
     * 获取商用设备激活码和设备归属人
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST(VersionConfig.SERVICE_HOST+ ServiceAddr.ACTIVE_INFO)
    Call<CodeAndNickNameBean> getActiveInfo(@Body RequestBody json);
}
