package com.example.bizyewu2.presenter;

import com.alibaba.fastjson.JSONObject;
import com.example.bizyewu2.api.Bizyewu2Api;
import com.example.bizyewu2.bean.SPolyvList1Bean;
import com.example.bizyewu2.bean.SPolyvList1Detail1Bean1;
import com.example.bizyewu2.view.HPolyvList1Detail1View;
import com.example.bizyewu2.view.HPolyvList1View;
import com.fosung.lighthouse.test.BuildConfigApp;
import com.haier.cellarette.libmvp.mvp.Presenter;
import com.haier.cellarette.libretrofit.common.BanbenUtils;
import com.haier.cellarette.libretrofit.common.ResponseSlbBean;
import com.haier.cellarette.libretrofit.common.ResponseSlbBean1;
import com.haier.cellarette.libretrofit.common.RetrofitNetNew;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HPolyvList1Detail1Presenter extends Presenter<HPolyvList1Detail1View> {

    public void get_polyvList1Detail1(String id,String userId,String playbackVideoId) {
        JSONObject requestData = new JSONObject();
        requestData.put("id", id);//
        requestData.put("userId", userId);//
        requestData.put("playbackVideoId", playbackVideoId);//
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), requestData.toString());
        RetrofitNetNew.build(Bizyewu2Api.class, getIdentifier()).get_polyvList1Detail1(
                BuildConfigApp.SERVER_ISERVICE_NEW1 + "/api/live/viewer/live/record/getViewerLiveDetail",
                BanbenUtils.getInstance().getVersion(),
                BanbenUtils.getInstance().getImei(),
                "", requestBody).enqueue(new Callback<ResponseSlbBean<SPolyvList1Detail1Bean1>>() {
            @Override
            public void onResponse(Call<ResponseSlbBean<SPolyvList1Detail1Bean1>> call,
                                   Response<ResponseSlbBean<SPolyvList1Detail1Bean1>> response) {
                if (!hasView()) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (!response.body().isSuccess()) {
                    getView().OnPolyvList1Detail1Nodata(response.body().getMsg());
                    return;
                }
                getView().OnPolyvList1Detail1Success(response.body().getData());
                call.cancel();
            }

            @Override
            public void onFailure(Call<ResponseSlbBean<SPolyvList1Detail1Bean1>> call, Throwable t) {
                if (!hasView()) {
                    return;
                }
//                String string = t.toString();
                String string = BanbenUtils.getInstance().net_tips;
                getView().OnPolyvList1Detail1Fail(string);
                call.cancel();
            }
        });

    }

}
