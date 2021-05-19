package com.example.bizyewu2.presenter;


import com.example.bizyewu2.api.Bizyewu2Api;
import com.example.bizyewu2.view.HTuichudengluView;
import com.fosung.lighthouse.test.BuildConfigApp;
import com.haier.cellarette.libmvp.mvp.Presenter;
import com.haier.cellarette.libretrofit.common.BanbenUtils;
import com.haier.cellarette.libretrofit.common.ResponseSlbBean;
import com.haier.cellarette.libretrofit.common.RetrofitNetNew;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HTuichudengluPresenter extends Presenter<HTuichudengluView> {

    public void get_tuichudenglu() {
        // NetConfig.SERVER_ISERVICE + "liveApp/logout"
        RetrofitNetNew.build(Bizyewu2Api.class, getIdentifier()).get_tuichudenglu(BuildConfigApp.SERVER_ISERVICE_NEW1 + "liveApp/logout",
                BanbenUtils.getInstance().getVersion(),
                BanbenUtils.getInstance().getImei(),
                "").enqueue(new Callback<ResponseSlbBean<Object>>() {
            @Override
            public void onResponse(Call<ResponseSlbBean<Object>> call, Response<ResponseSlbBean<Object>> response) {
                if (!hasView()) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() != 0) {
                    getView().OnTuichudengluNodata(response.body().getMsg());
                    return;
                }
                getView().OnTuichudengluSuccess(response.body().getMsg());
                call.cancel();
            }

            @Override
            public void onFailure(Call<ResponseSlbBean<Object>> call, Throwable t) {
                if (!hasView()) {
                    return;
                }
//                String string = t.toString();
                String string = BanbenUtils.getInstance().net_tips;
                getView().OnTuichudengluFail(string);
                call.cancel();
            }
        });

    }

}
