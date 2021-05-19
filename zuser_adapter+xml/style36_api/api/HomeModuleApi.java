package com.haier.newmvp.api;

import com.haier.banben.VersionConfig;
import com.haier.newmvp.bean.HomeModuleBean;
import com.haier.newmvp.bean.HomeTwoModuleBean;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * <p>
 * 首页HIOS和第二页Hios  API
 * </p>
 */
public interface HomeModuleApi {

    /**
     * 获取首页HIOS
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST(VersionConfig.SERVICE_HOST + "tft/hiOs/authCom/new/homeModule")
    Call<HomeModuleBean> getHomeModule(@Header("accessToken") String accessToken);


    /**
     * 获取第二页HISO
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST(VersionConfig.SERVICE_HOST + "tft/hiOs/authCom/new/homeTwoModule")
    Call<HomeTwoModuleBean> getHomeTwoModule(@Header("accessToken") String accessToken);
}
