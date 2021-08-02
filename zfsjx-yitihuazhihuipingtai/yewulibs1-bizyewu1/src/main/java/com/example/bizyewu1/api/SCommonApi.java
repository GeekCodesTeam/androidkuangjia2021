package com.example.bizyewu1.api;

import com.example.bizyewu1.bean.VersionInfoBean;
import com.haier.cellarette.libretrofit.common.ResponseSlbBean;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface SCommonApi {

    // 更新版本1
    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST(BuildConfigCommon.SERVER_ISERVICE_NEW1 + "picbook/audio/app/updateReadTime")
    @POST()
    Call<ResponseSlbBean<VersionInfoBean>> get_version1(@Url String path, @Header("hxAppVersion") String version,
                                                        @Header("imei") String imei, @Header("token") String token, @Body RequestBody body);


    // 上传用户头像信息1->base64
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST()
    Call<ResponseSlbBean<VersionInfoBean>> get_version2(@Url String path, @Header("hxAppVersion") String version,
                                                        @Header("imei") String imei, @Header("token") String token, @Body RequestBody body);

    // 上传用户头像信息2->file
    @Multipart
    @POST()
    Call<ResponseSlbBean<VersionInfoBean>> get_version3(@Url String path, @Header("hxAppVersion") String version,
                                                        @Header("imei") String imei, @Header("token") String token,/*@Part("userinfo") RequestBody body1,*/
                                                        @Part MultipartBody.Part body2);

    // 上传用户头像信息3-MultipartFile
    @Multipart
    @POST()
    Call<ResponseSlbBean<VersionInfoBean>> get_version4(@Url String path, @Header("hxAppVersion") String version,
                                                        @Header("imei") String imei, @Header("token") String token, @PartMap Map<String, RequestBody> map,
                                                        @Part List<MultipartBody.Part> parts);

    // get请求
    @GET()
    Call<ResponseSlbBean<VersionInfoBean>> get_version5(@Query("pageNum") String pageNum, @Query("pageSize") String pageSize, @Query("searchKey") String searchKey);

}
