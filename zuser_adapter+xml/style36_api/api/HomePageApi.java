package com.haier.newmvp.api;

import com.haier.banben.VersionConfig;
import com.haier.cellarette.bean.BannerBean;
import com.haier.cellarette.bean.InformationImageBean;
import com.haier.cellarette.bean.MatchWineResult;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * <p>
 * 首页获取数据API
 * </p>
 */
public interface HomePageApi {

    /**
     * 获取首页bannerview的数据
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST(VersionConfig.SERVICE_HOST + "tft/advert/authCom/findBusinessBanner")
    Call<BannerBean> getBannerData(@Header("accessToken") String accessToken);


    /**
     * 获取搭配鉴酒图片
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST(VersionConfig.SERVICE_HOST + "tft/recommendModel/authCom/findList")
    Call<MatchWineResult> getMatchWinePic(@Header("accessToken") String token, @Body RequestBody json);

    //获取资讯图片数据
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST(VersionConfig.SERVICE_HOST + "tft/information/authCom/findIndex")
    Call<InformationImageBean> getInformationImagess(@Header("accessToken") String accessToken);

}
