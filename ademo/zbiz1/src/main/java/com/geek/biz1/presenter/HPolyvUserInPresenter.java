package com.geek.biz1.presenter;

import com.alibaba.fastjson.JSONObject;
import com.geek.biz1.api.Biz1Api;
import com.geek.biz1.view.HPolyvUserInView;
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

public class HPolyvUserInPresenter extends Presenter<HPolyvUserInView> {

    public void get_PolyvUserIn(String id,
                                String userId,
                                String userName,
                                String liveRecordId,
                                String startTime,
                                String orgCode,
                                String orgId,
                                String orgName,
                                String playbackVideoId) {
        JSONObject requestData = new JSONObject();
        requestData.put("userId", userId);//
        requestData.put("userName", userName);//
        requestData.put("liveRecordId", liveRecordId);//
        requestData.put("startTime", startTime);//
        requestData.put("orgCode", orgCode);//
        requestData.put("orgId", orgId);//
        requestData.put("orgName", orgName);//
        requestData.put("playbackVideoId", playbackVideoId);//
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), requestData.toString());
        RetrofitNetNew.build(Biz1Api.class, getIdentifier()).get_PolyvUserIn(
                BuildConfigyewu.SERVER_ISERVICE_NEW1 + "/api/live/viewer/statistics/watchStart",
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
                    getView().OnPolyvUserInNodata(response.body().getMsg());
                    return;
                }
                getView().OnPolyvUserInSuccess(response.body().getMsg());
                call.cancel();
            }

            @Override
            public void onFailure(Call<ResponseSlbBean<Object>> call, Throwable t) {
                if (!hasView()) {
                    return;
                }
//                String string = t.toString();
                String string = BanbenUtils.getInstance().net_tips;
                getView().OnPolyvUserInFail(string);
                call.cancel();
            }
        });

    }

}
