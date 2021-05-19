package com.haier.newmvp.api;

import com.haier.banben.VersionConfig;
import com.haier.cellarette.bean.InformationImageBean;
import com.haier.wine_commen.html.ServiceAddr;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiDownInfor  {

    //获取资讯图片数据
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST(VersionConfig.SERVICE_HOST + ServiceAddr.GET_DOWNINFO)
    Call<InformationImageBean> getDownInfoImage(@Header("accessToken") String accessToken);

}
