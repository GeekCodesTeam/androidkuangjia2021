package com.fosung.eduapi.api;

import com.fosung.eduapi.bean.EduResourceDetailBean;
import com.fosung.eduapi.bean.EduResourceListBean;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface BizEduResApi {

    // 资源库列表
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST()
    Call<EduResourceListBean> getEduReslist(@Url String path, @Body RequestBody body);

    // 资源库详情
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST()
    Call<EduResourceDetailBean> getEduResDetail(@Url String path, @Body RequestBody body);
}
