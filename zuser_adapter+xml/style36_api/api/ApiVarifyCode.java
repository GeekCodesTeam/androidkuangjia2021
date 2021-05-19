package com.haier.newmvp.api;
import com.haier.banben.VersionConfig;
import com.haier.mine.wine.bean.PassKeyBean;
import com.haier.mine.wine.bean.UserMobileBean;
import com.haier.wine_commen.html.ServiceAddr;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Author: YuanFan
 * E-mail: yuanfan@smart-haier.com
 * Date: on 2018/1/11/0011.
 * Description:手机验证码
 */

public interface ApiVarifyCode {
    //获取绑定一级代理商手机号
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST(VersionConfig.SERVICE_HOST + ServiceAddr.DEVICE_USER_INFO)
    Call<UserMobileBean> getPhoneNumber(@Body RequestBody body);

    //获取密码锁验证码
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST(VersionConfig.SERVICE_HOST + ServiceAddr.PHONE_VARIFY_CODE)
    Call<PassKeyBean> getVerifyCode(@Body RequestBody route);
}
