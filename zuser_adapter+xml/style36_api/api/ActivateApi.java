package com.haier.newmvp.api;

import com.haier.banben.VersionConfig;
import com.haier.newmvp.bean.ActivateResultEntity;
import com.haier.newmvp.bean.CodeAndNickNameBean;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * <p>
 * 激活页面api
 * </p>
 */
public interface ActivateApi {
    /**
     * 获取商用设备激活码和设备归属人
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST(VersionConfig.SERVICE_HOST + "tft/devices/findActiveCodeAndNickName")
    Call<CodeAndNickNameBean> getCodeAndNickName(@Body RequestBody body);


    /**
     * 商用激活设备
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST(VersionConfig.SERVICE_HOST + "tft/devices/activeDevice")
    Call<ActivateResultEntity> activateDevice(@Body RequestBody body);
}
