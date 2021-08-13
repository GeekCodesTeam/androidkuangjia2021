package com.fosung.lighthouse.manage.myapi.presenter

import com.alibaba.fastjson.JSONObject
import com.fosung.lighthouse.manage.myapi.api.MyEduResApi
import com.fosung.lighthouse.manage.myapi.bean.LoginLogsBean
import com.fosung.lighthouse.manage.myapi.bean.UserLoginBean
import com.fosung.lighthouse.manage.myapi.common.MyApiConstant
import com.fosung.lighthouse.manage.myapi.http.HttpUrlMy
import com.fosung.lighthouse.manage.myapi.view.EdcResMyView
import com.fosung.lighthouse.manage.myapi.view.EduResLoginLogView
import com.geek.libmvp.Presenter
import com.haier.cellarette.libretrofit.common.BanbenUtils
import com.haier.cellarette.libretrofit.common.RetrofitNetNew
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *@author: lhw
 *@date: 2021/8/9
 *@desc
 */
class MyPresenter() : Presenter<EdcResMyView>() {

    private var loginLogView: EduResLoginLogView? = null

    constructor(view: EdcResMyView):this()  {
        super.onCreate(view)
    }

    fun setEduLoginLogView(pView: EduResLoginLogView) {
        loginLogView = pView
    }

    fun getUserInfo() {
        val requestData = JSONObject()

        val requestBody = RequestBody.create(
            MediaType.parse("application/json;charset=utf-8"),
            requestData.toString()
        )

        RetrofitNetNew.build(MyEduResApi::class.java, identifier)
            .getUserInfo(HttpUrlMy.USER_INFO, requestBody)
            ?.enqueue(object : Callback<UserLoginBean?> {
                override fun onResponse(
                    call: Call<UserLoginBean?>, response: Response<UserLoginBean?>
                ) {
                    if (!hasView()) {
                        return
                    }
                    if (response.body() == null) {
                        view.onEduResMyFail("服务器异常")
                        return
                    }

                    if (response.body()!!.isSuccess) {
                        view.onEduResMySuccess(response.body())
                        return
                    }
                    view.onEduResMyFail(response.body()!!.message)
                    call.cancel()
                }

                override fun onFailure(call: Call<UserLoginBean?>, t: Throwable) {
                    if (!hasView()) {
                        return
                    }
                    val string = BanbenUtils.getInstance().net_tips
                    view.onEduResMyFail(string)
                    call.cancel()
                }
            })
    }

    fun getLoginLog(pageNo: Int) {
        val requestData = JSONObject()
        requestData["pageNo"] = pageNo
        requestData["pageSize"] = MyApiConstant.PAGE_SIZE

        val requestBody = RequestBody.create(
            MediaType.parse("application/json;charset=utf-8"),
            requestData.toString()
        )

        RetrofitNetNew.build(MyEduResApi::class.java, identifier)
            .loginLog(HttpUrlMy.LOGIN_LOG, requestBody)
            ?.enqueue(object : Callback<LoginLogsBean?> {
                override fun onResponse(
                    call: Call<LoginLogsBean?>,
                    response: Response<LoginLogsBean?>
                ) {
                    if (loginLogView == null) {
                        return
                    }
                    if (response.body() == null) {
                        loginLogView?.onLoginLogFail("服务器异常")
                        return
                    }

                    if (response.body()!!.isSuccess) {
                        loginLogView?.onLoginLogSuccess(response.body()!!)
                        return
                    }
                    loginLogView?.onLoginLogFail(response.body()!!.message)
                    call.cancel()
                }

                override fun onFailure(call: Call<LoginLogsBean?>, t: Throwable) {
                    if (!hasView()) {
                        return
                    }
                    val string = BanbenUtils.getInstance().net_tips
                    loginLogView?.onLoginLogFail(string)
                    call.cancel()
                }
            })
    }
}