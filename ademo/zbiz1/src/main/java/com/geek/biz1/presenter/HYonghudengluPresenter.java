package com.geek.biz1.presenter;

import com.alibaba.fastjson.JSONObject;
import com.geek.biz1.api.Biz1Api;
import com.geek.biz1.bean.HLoginBean;
import com.geek.biz1.view.HYonghudengluView;
import com.github.geekcodesteam.app.BuildConfigyewu;
import com.geek.libmvp.Presenter;
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
        RetrofitNetNew.build(Biz1Api.class, getIdentifier()).get_yonghudenglu(BuildConfigyewu.SERVER_ISERVICE_NEW1 + "liveApp/login",
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
