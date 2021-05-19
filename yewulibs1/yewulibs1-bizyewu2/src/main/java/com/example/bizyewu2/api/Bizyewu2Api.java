package com.example.bizyewu2.api;

import com.example.bizyewu2.bean.HLoginBean;
import com.example.bizyewu2.bean.HMobid2Bean;
import com.example.bizyewu2.bean.HUserInfoBean;
import com.example.bizyewu2.bean.SNew1SearchBean;
import com.haier.cellarette.libretrofit.common.ResponseSlbBean;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface Bizyewu2Api {

    // 用户登录
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST()
    Call<ResponseSlbBean<HLoginBean>> get_yonghudenglu(@Url String path, @Header("version") String version,
                                                       @Header("imei") String imei, @Header("token") String token, @Body RequestBody body);

    // 退出登录
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST()
    Call<ResponseSlbBean<Object>> get_tuichudenglu(@Url String path, @Header("version") String version,
                                                   @Header("imei") String imei, @Header("token") String token);

    // 获取个人资料信息
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST()
    Call<ResponseSlbBean<HUserInfoBean>> get_userinfo(@Url String path, @Header("version") String version,
                                                      @Header("imei") String imei, @Header("token") String token);

    // 获取二维码
    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST(NetConfig.SERVER_ISERVICE + "liveApp/sendloginVCode2")
    @POST()
    Call<ResponseSlbBean<Object>> get_erweima(@Url String path, @Header("version") String version,
                                              @Header("imei") String imei, @Header("token") String token, @Body RequestBody body);

    // ceshi mob tuisong id
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST()
    Call<Object> get_mob_id(@Url String path, @Body RequestBody body);


    // ceshi mob tuisong id
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST()
    Call<HMobid2Bean> get_mob_id2(@Url String path, @Body RequestBody body);

    // 搜索new1
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST()
    Call<ResponseSlbBean<SNew1SearchBean>> get_my_search_new1(@Url String path, @Header("version") String version,
                                                              @Header("imei") String imei, @Header("token") String token, @Body RequestBody body);

}
