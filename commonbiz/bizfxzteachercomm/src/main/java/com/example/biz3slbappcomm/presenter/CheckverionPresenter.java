/*
package com.example.biz3slbappcomm.presenter;

import com.example.app3libvariants.bean.VersionInfoBean;
import com.example.app3libvariants.network.api.AccountService;
import com.example.biz3slbappcomm.api.SCommonApi;
import com.example.biz3slbappcomm.view.CheckverionView;
import com.haier.cellarette.libmvp.mvp.Presenter;
import com.haier.cellarette.libretrofit.common.BanbenUtils;
import com.haier.cellarette.libretrofit.common.ResponseSlbBean;
import com.haier.cellarette.libretrofit.common.RetrofitNetNew;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

*/
/**
 * 描述：
 * -
 * 创建人：baoshengxiang
 * 创建时间：2017/6/26
 *//*

public class CheckverionPresenter extends Presenter<CheckverionView> {

    public void checkVerion() {
        RetrofitNetNew.build(SCommonApi.class, getIdentifier())
                .queryVersion(1)
                .enqueue(new Callback<ResponseSlbBean<VersionInfoBean>>() {
                    @Override
                    public void onResponse(Call<ResponseSlbBean<VersionInfoBean>> call, Response<ResponseSlbBean<VersionInfoBean>> response) {
                        if (!hasView()) {
                            return;
                        }
                        if (response.body() == null) {
                            return;
                        }
                        if (response.body().getCode() != 0) {
                            getView().OnUpdateVersionNodata(response.body().getMsg());
                            return;
                        }
                        getView().OnUpdateVersionSuccess(response.body().getData());
                        call.cancel();
                    }

                    @Override
                    public void onFailure(Call<ResponseSlbBean<VersionInfoBean>> call, Throwable t) {
                        if (!hasView()) {
                            return;
                        }
                        String string = BanbenUtils.getInstance().error_tips;
                        getView().OnUpdateVersionFail(string);
                        t.printStackTrace();
                        call.cancel();
                    }
                });
    }
}
*/
