package com.example.bizyewu1.presenter;

import com.alibaba.fastjson.JSONObject;
import com.example.bizyewu1.api.SCommonApi;
import com.example.bizyewu1.bean.SbbdBean;
import com.example.bizyewu1.view.CheckverionView;
import com.example.bizyewu1.view.SbbdView;
import com.fosung.lighthouse.test.BuildConfigApp;
import com.geek.libmvp.Presenter;
import com.haier.cellarette.libretrofit.common.BanbenUtils;
import com.haier.cellarette.libretrofit.common.ResponseSlbBean;
import com.haier.cellarette.libretrofit.common.RetrofitNetNew;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SbbdPresenter extends Presenter<SbbdView> {

    public void getSbbdPresenter() {
        RetrofitNetNew.build(SCommonApi.class, getIdentifier())
                .get_version6("http://t-ums.ireign.cn/gwapi/ums/api/user/configure")
                .enqueue(new Callback<ResponseSlbBean<SbbdBean>>() {
                    @Override
                    public void onResponse(Call<ResponseSlbBean<SbbdBean>> call, Response<ResponseSlbBean<SbbdBean>> response) {
                        if (!hasView()) {
                            return;
                        }
                        if (response.body() == null) {
                            return;
                        }
                        if (!response.body().isSuccess()) {
                            getView().OnSbbdNodata(response.body().getMsg());
                            return;
                        }
                        getView().OnSbbdSuccess(response.body().getData());
                        call.cancel();
                    }

                    @Override
                    public void onFailure(Call<ResponseSlbBean<SbbdBean>> call, Throwable t) {
                        if (!hasView()) {
                            return;
                        }
                        String string = BanbenUtils.getInstance().error_tips;
                        getView().OnSbbdFail(BuildConfigApp.SERVER_ISERVICE_NEW1);
                        t.printStackTrace();
                        call.cancel();
                    }
                });
    }
}
