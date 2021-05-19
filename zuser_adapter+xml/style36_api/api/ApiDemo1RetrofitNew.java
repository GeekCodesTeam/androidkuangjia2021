package com.haier.newnetdemo.api;


import com.haier.newnetdemo.bean.DemoNewModel;
import com.haier.newnetdemo.bean.DemoNewModelAddr;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by shining on 2017/9/1.
 * VersionConfig.BASE_URL SERVICE_HOST PAY_URL
 */

public interface ApiDemo1RetrofitNew {

    @GET("http://v.juhe.cn/toutiao/index")
    Call<DemoNewModel> get_request_get(@Query("type") String type, @Query("key") String key);

    @FormUrlEncoded
    @POST("http://v.juhe.cn/toutiao/index")
    Call<DemoNewModel> get_request_post(@Field("type") String type, @Field("key") String key);

    //JSON传参方法
    @POST("http://v.juhe.cn/toutiao/index")
    Call<DemoNewModel> get_request_json(@Body RequestBody route);


    @FormUrlEncoded
    @POST("http://apis.juhe.cn/ip/ip2addr")
    Call<DemoNewModelAddr> get_request_post_addr(@Field("ip") String type, @Field("dtype") String dtype, @Field("key") String key);


    // 上传多个文件
    @Multipart
    @POST("http://japi.juhe.cn/voice_words/getWords")
    Call<DemoNewModel> uploadFile(/*@Part("description") RequestBody description,*/
                                  @Part("rate") RequestBody rate,
                                  @Part("pname") RequestBody pname,
                                  @Part("device_id") RequestBody device_id,
                                  @Part MultipartBody.Part file);

}
