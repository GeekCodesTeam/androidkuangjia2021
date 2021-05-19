package com.example.bizyewu2.presenter;

import com.example.bizyewu2.api.Bizyewu2Api;
import com.example.bizyewu2.bean.HUserInfoBean;
import com.example.bizyewu2.view.HUserInfoView;
import com.fosung.lighthouse.test.BuildConfigApp;
import com.haier.cellarette.libmvp.mvp.Presenter;
import com.haier.cellarette.libretrofit.common.BanbenUtils;
import com.haier.cellarette.libretrofit.common.ResponseSlbBean;
import com.haier.cellarette.libretrofit.common.RetrofitNetNew;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HUserInfoPresenter extends Presenter<HUserInfoView> {

    public void get_userinfo() {
        RetrofitNetNew.build(Bizyewu2Api.class, getIdentifier()).get_userinfo(
                BuildConfigApp.SERVER_ISERVICE_NEW1 + "liveApp/getUserInfoNew",
                BanbenUtils.getInstance().getVersion(),
                BanbenUtils.getInstance().getImei(),
                "").enqueue(new Callback<ResponseSlbBean<HUserInfoBean>>() {
            @Override
            public void onResponse(Call<ResponseSlbBean<HUserInfoBean>> call, Response<ResponseSlbBean<HUserInfoBean>> response) {
                if (!hasView()) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() != 0) {
                    getView().OnUserInfoNodata(response.body().getMsg());
                    return;
                }
                getView().OnUserInfoSuccess(response.body().getData());
                call.cancel();
            }

            @Override
            public void onFailure(Call<ResponseSlbBean<HUserInfoBean>> call, Throwable t) {
                if (!hasView()) {
                    return;
                }
//                String string = t.toString();
                String string = BanbenUtils.getInstance().net_tips;
                getView().OnUserInfoFail(string);
                call.cancel();
            }
        });

    }

}
