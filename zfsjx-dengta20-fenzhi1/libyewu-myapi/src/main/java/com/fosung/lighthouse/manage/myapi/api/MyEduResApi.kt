package com.fosung.lighthouse.manage.myapi.api

import com.fosung.lighthouse.manage.myapi.bean.LoginLogsBean
import com.fosung.lighthouse.manage.myapi.bean.UserLoginBean
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

/**
 *@author: lhw
 *@date: 2021/8/5
 *@desc 灯塔移动管理端我的api
 */
interface MyEduResApi {

    // 获取用户信息
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST
    fun getUserInfo(@Url path: String?,@Body body: RequestBody ): Call<UserLoginBean?>?

    // 登录日志
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST
    fun loginLog(@Url path: String?,@Body body: RequestBody ): Call<LoginLogsBean?>?

}