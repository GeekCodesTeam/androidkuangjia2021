package com.fosung.lighthouse.manage.myapi.api

import com.fosung.lighthouse.manage.myapi.bean.AccountActivitionBean
import com.fosung.lighthouse.manage.myapi.bean.PicVerCodeBean
import com.fosung.lighthouse.manage.myapi.bean.UserLoginBean
import com.haier.cellarette.libretrofit.common.ResponseSlbBean1
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

/**
 *@author: lhw
 *@date: 2021/8/5
 *@desc 灯塔移动管理端我的api
 */
interface AccoutEduResApi {

    // 获取图形验证码
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST
    fun getPicVerCode(@Url path: String?, @Header("imei") imei:String ): Call<PicVerCodeBean?>?

    // 获取验证码
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST
    fun getVerCode(@Url path: String?, @Body body: RequestBody?): Call<AccountActivitionBean?>?

    // 账号激活
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST
    fun accountActivation(@Url path: String?, @Body body: RequestBody?): Call<AccountActivitionBean?>?

    // VPN 是否綁定校验
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST
    fun checkVpnBind(@Url path: String?, @Body body: RequestBody?): Call<ResponseSlbBean1<Any>?>?

    // 用户登录
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST
    fun login(@Url path: String?, @Body body: RequestBody?): Call<UserLoginBean?>?

    // 修改手机号
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST
    fun changePhone(@Url path: String?, @Body body: RequestBody?): Call<ResponseSlbBean1<Any>?>?

    // 修改密码
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST
    fun changePass(@Url path: String?, @Body body: RequestBody?): Call<ResponseSlbBean1<Any>?>?

    // 修改vpn密码
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST
    fun changeVpnPass(@Url path: String?, @Body body: RequestBody?): Call<ResponseSlbBean1<Any>?>?

    // 修改vpn密码
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST
    fun vpnGetUserInfo(@Url path: String?, @Body body: RequestBody?): Call<UserLoginBean?>?
}