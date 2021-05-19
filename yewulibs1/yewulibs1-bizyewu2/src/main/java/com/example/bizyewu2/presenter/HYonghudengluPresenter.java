package com.example.bizyewu2.presenter;

import com.alibaba.fastjson.JSONObject;
import com.example.bizyewu2.api.Bizyewu2Api;
import com.example.bizyewu2.bean.HLoginBean;
import com.example.bizyewu2.view.HYonghudengluView;
import com.fosung.lighthouse.test.BuildConfigApp;
import com.haier.cellarette.libmvp.mvp.Presenter;
import com.haier.cellarette.libretrofit.common.BanbenUtils;
import com.haier.cellarette.libretrofit.common.ResponseSlbBean;
import com.haier.cellarette.libretrofit.common.RetrofitNetNew;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HYonghudengluPresenter extends Presenter<HYonghudengluView> {

    public void get_yonghudenglu(String from, String phone, String vcode) {
        JSONObject requestData = new JSONObject();
        requestData.put("from", from);//
        requestData.put("phone", phone);//
        requestData.put("vcode", vcode);//
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), requestData.toString());
        RetrofitNetNew.build(Bizyewu2Api.class, getIdentifier()).get_yonghudenglu(BuildConfigApp.SERVER_ISERVICE_NEW1 + "liveApp/login",
                BanbenUtils.getInstance().getVersion(),
                BanbenUtils.getInstance().getImei(),
                "",requestBody).enqueue(new Callback<ResponseSlbBean<HLoginBean>>() {
            @Override
            public void onResponse(Call<ResponseSlbBean<HLoginBean>> call, Response<ResponseSlbBean<HLoginBean>> response) {
                if (!hasView()) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() != 0) {
                    getView().OnYonghudengluNodata(response.body().getMsg());
                    return;
                }
                getView().OnYonghudengluSuccess(response.body().getData());
                call.cancel();
            }

            @Override
            public void onFailure(Call<ResponseSlbBean<HLoginBean>> call, Throwable t) {
                if (!hasView()) {
                    return;
                }
//                String string = t.toString();
                String string = BanbenUtils.getInstance().net_tips;
                getView().OnYonghudengluFail(string);
                call.cancel();
            }
        });

    }

}
