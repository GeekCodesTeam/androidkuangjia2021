package com.example.bizyewu2.presenter;

import android.text.TextUtils;

import com.example.bizyewu2.api.Bizyewu2Api;
import com.example.bizyewu2.bean.HLogin1111Bean;
import com.example.bizyewu2.view.HLogin1111View;
import com.fosung.lighthouse1.BuildConfigApp;
import com.geek.libutils.data.MmkvUtils;
import com.geek.libmvp.Presenter;
import com.haier.cellarette.libretrofit.common.BanbenUtils;
import com.haier.cellarette.libretrofit.common.ResponseSlbBean2;
import com.haier.cellarette.libretrofit.common.RetrofitNetNew;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HLogin1111Presenter extends Presenter<HLogin1111View> {

    public void get_login1111(String phone, String vcode) {
//        JSONObject requestData = new JSONObject();
//        requestData.put("phone", phone);//
//        requestData.put("vcode", vcode);//
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), requestData.toString());
        RetrofitNetNew.build(Bizyewu2Api.class, getIdentifier()).get_login1111(
                BuildConfigApp.SERVER_ISERVICE_NEW1 + "/login",
                BanbenUtils.getInstance().getVersion(),
                BanbenUtils.getInstance().getImei(),
                MmkvUtils.getInstance().get_common("token"), phone, vcode).enqueue(new Callback<ResponseSlbBean2<HLogin1111Bean>>() {
            @Override
            public void onResponse(Call<ResponseSlbBean2<HLogin1111Bean>> call, Response<ResponseSlbBean2<HLogin1111Bean>> response) {
                if (!hasView()) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (!TextUtils.isEmpty(response.body().getCode())) {
                    getView().OnLogin1111Nodata(response.body().getMsg());
                    return;
                }
                getView().OnLogin1111Success(response.body().getData());
                call.cancel();
            }

            @Override
            public void onFailure(Call<ResponseSlbBean2<HLogin1111Bean>> call, Throwable t) {
                if (!hasView()) {
                    return;
                }
//                String string = t.toString();
                String string = BanbenUtils.getInstance().net_tips;
                getView().OnLogin1111Fail(string);
                call.cancel();
            }
        });

    }

}
