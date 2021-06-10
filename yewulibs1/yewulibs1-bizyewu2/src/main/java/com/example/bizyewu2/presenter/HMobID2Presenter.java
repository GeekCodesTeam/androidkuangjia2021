package com.example.bizyewu2.presenter;

import com.alibaba.fastjson.JSONObject;
import com.example.bizyewu2.api.Bizyewu2Api;
import com.example.bizyewu2.bean.HLoginBean;
import com.example.bizyewu2.bean.HMobid2Bean;
import com.example.bizyewu2.view.HMobID2View;
import com.haier.cellarette.libmvp.mvp.Presenter;
import com.haier.cellarette.libretrofit.common.BanbenUtils;
import com.haier.cellarette.libretrofit.common.ResponseSlbBean;
import com.haier.cellarette.libretrofit.common.RetrofitNetNew;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HMobID2Presenter extends Presenter<HMobID2View> {

    public void get_mob_id2(int pageNum, int pageSize, String  receiveUserId, String code) {
        JSONObject requestData = new JSONObject();
        requestData.put("pageNum", pageNum);//
        requestData.put("pageSize", pageSize);//
        requestData.put("receiveUserId", receiveUserId);//
        requestData.put("code", code);//
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), requestData.toString());
        RetrofitNetNew.build(Bizyewu2Api.class, getIdentifier()).get_mob_id2("http://t-ums.ireign.cn:8380/api/ums/manage/querypage", requestBody).enqueue(new Callback<HMobid2Bean>() {
            @Override
            public void onResponse(Call<HMobid2Bean> call, Response<HMobid2Bean> response) {
                if (!hasView()) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
//                if (response.body().getCode() != 0) {
//                    getView().OnMobID2Nodata(response.body().getMsg());
//                    return;
//                }
                getView().OnMobID2Success(response.body());
                call.cancel();
            }

            @Override
            public void onFailure(Call<HMobid2Bean> call, Throwable t) {
                if (!hasView()) {
                    return;
                }
//                String string = t.toString();
                String string = BanbenUtils.getInstance().net_tips;
                getView().OnMobID2Fail(string);
                call.cancel();
            }
        });

    }

}
