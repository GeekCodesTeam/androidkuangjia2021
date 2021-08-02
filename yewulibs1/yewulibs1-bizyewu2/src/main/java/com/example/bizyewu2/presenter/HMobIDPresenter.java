package com.example.bizyewu2.presenter;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.SPUtils;
import com.example.bizyewu2.api.Bizyewu2Api;
import com.example.bizyewu2.view.HMobIDView;
import com.haier.cellarette.libmvp.mvp.Presenter;
import com.haier.cellarette.libretrofit.common.BanbenUtils;
import com.haier.cellarette.libretrofit.common.RetrofitNetNew;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HMobIDPresenter extends Presenter<HMobIDView> {

    public void get_mob_id() {
        JSONObject requestData = new JSONObject();
        requestData.put("userName", "小明");//
        requestData.put("userId", "1");//
        requestData.put("terminalCode", "APP_MOB");//
        requestData.put("terminalUniqueId", SPUtils.getInstance().getString("MOBID"));//
        requestData.put("terminalBrand", "HUAIWEI");//
        requestData.put("terminalModel", "P30");//
        requestData.put("orgId", "1");//
        requestData.put("orgCode", "aaa");//
        requestData.put("orgName", "测试组织名称");//
        requestData.put("channelCode", "APP_MOB");//MOBPUSH JPUSH
        requestData.put("appKey", "ak");//
        requestData.put("appSecret", "as");//
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), requestData.toString());
        RetrofitNetNew.build(Bizyewu2Api.class, getIdentifier()).get_mob_id("http://t-ums.ireign.cn:8380/api/ums/terminal/bind", requestBody).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (!hasView()) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
//                if (response.body().getCode() != 0) {
//                    getView().OnMobIDNodata(response.body().getMsg());
//                    return;
//                }
                getView().OnMobIDSuccess(response.body().toString());
                call.cancel();
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                if (!hasView()) {
                    return;
                }
//                String string = t.toString();
                String string = BanbenUtils.getInstance().net_tips;
                getView().OnMobIDFail(string);
                call.cancel();
            }
        });

    }

}
