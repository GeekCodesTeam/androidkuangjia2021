package com.fosung.lighthouse.manage.myapi.presenter

import com.fosung.lighthouse.app.BuildConfigyewu
import com.fosung.lighthouse.manage.myapi.api.AccoutEduResApi
import com.fosung.lighthouse.manage.myapi.bean.PicVerCodeBean
import com.fosung.lighthouse.manage.myapi.view.EdcResPicVerCodeView
import com.geek.libmvp.Presenter
import com.haier.cellarette.libretrofit.common.BanbenUtils
import com.haier.cellarette.libretrofit.common.RetrofitNetNew
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Headers

/**
 *@author: lhw
 *@date: 2021/8/6
 *@desc
 */
class PicVerCodePresenter constructor(view: EdcResPicVerCodeView) :
    Presenter<EdcResPicVerCodeView>() {

    init {
        super.onCreate(view)
    }

    private val PIC_VER_CODE = "/sso/app/getAppValidateCode"

    fun getPicVerCode() {

        RetrofitNetNew.build(AccoutEduResApi::class.java, identifier)
            .getPicVerCode(
                BuildConfigyewu.SERVER_ISERVICE_NEW1 + PIC_VER_CODE,
                BanbenUtils.getInstance().getImei()
            )
            ?.enqueue(object : Callback<PicVerCodeBean?> {
                override fun onResponse(
                    call: Call<PicVerCodeBean?>, response: Response<PicVerCodeBean?>
                ) {
                    if (!hasView()) {
                        return
                    }
                    if (response.body() == null) {
                        return
                    }
                    if (response.body()!!.isSuccess) {
                        view.OnEduResPicVerSuccess(response.body())
                        return
                    }
                    view.OnEduResPicVerFail(response.body()!!.message)
                    call.cancel()
                }

                override fun onFailure(call: Call<PicVerCodeBean?>, t: Throwable) {
                    if (!hasView()) {
                        return
                    }
                    val string = BanbenUtils.getInstance().net_tips
                    view.OnEduResPicVerFail(string)
                    call.cancel()
                }
            })
    }


}