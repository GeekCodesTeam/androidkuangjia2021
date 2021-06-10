package com.example.bizyewu2.presenter;

import com.alibaba.fastjson.JSONObject;
import com.example.bizyewu2.api.Bizyewu2Api;
import com.example.bizyewu2.view.HPolyvUserHeartView;
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

public class HPolyvHeartPresenter extends Presenter<HPolyvUserHeartView> {

    public void get_PolyvUserOut(String id,
                                 String userId,
                                 String userName,
                                 String liveRecordId,
                                 String orgCode,
                                 String orgId,
                                 String orgName,
                                 String startTime,
                                 String requestTime,
                                 String playbackVideoId) {
        JSONObject requestData = new JSONObject();
        requestData.put("userId", userId);//
        requestData.put("userName", userName);//
        requestData.put("liveRecordId", liveRecordId);//
        requestData.put("orgCode", orgCode);//
        requestData.put("orgId", orgId);//
        requestData.put("orgName", orgName);//
        requestData.put("startTime", startTime);//
        requestData.put("requestTime", requestTime);//
        requestData.put("playbackVideoId", playbackVideoId);//
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), requestData.toString());
        RetrofitNetNew.build(Bizyewu2Api.class, getIdentifier()).get_PolyvUserHeart(
                BuildConfigApp.SERVER_ISERVICE_NEW1 + "/api/live/viewer/statistics/watchUserInfo",
                BanbenUtils.getInstance().getVersion(),
                BanbenUtils.getInstance().getImei(),
                "",
                "",
                "", requestBody).enqueue(new Callback<ResponseSlbBean<Object>>() {
            @Override
            public void onResponse(Call<ResponseSlbBean<Object>> call,
                                   Response<ResponseSlbBean<Object>> response) {
                if (!hasView()) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (!response.body().isSuccess()) {
                    getView().OnPolyvUserHeartNodata(response.body().getMsg());
                    return;
                }
                getView().OnPolyvUserHeartSuccess(response.body().getMsg());
                call.cancel();
            }

            @Override
            public void onFailure(Call<ResponseSlbBean<Object>> call, Throwable t) {
                if (!hasView()) {
                    return;
                }
//                String string = t.toString();
                String string = BanbenUtils.getInstance().net_tips;
                getView().OnPolyvUserHeartFail(string);
                call.cancel();
            }
        });

    }

}
