package com.example.biz3slbappcomm.api;

import com.example.biz3slbappcomm.bean.VersionInfoBean;
import com.haier.cellarette.libretrofit.common.ResponseSlbBean;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface SCommonApi {

    // 更新版本1
    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST(BuildConfigCommon.SERVER_ISERVICE_NEW1 + "picbook/audio/app/updateReadTime")
    @POST()
    Call<ResponseSlbBean<VersionInfoBean>> get_version1(@Url String path, @Header("hxAppVersion") String version,
                                                        @Header("imei") String imei, @Header("token") String token, @Body RequestBody body);



}
