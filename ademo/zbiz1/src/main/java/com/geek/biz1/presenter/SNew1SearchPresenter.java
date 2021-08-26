package com.geek.biz1.presenter;

import com.alibaba.fastjson.JSONObject;
import com.geek.biz1.api.Biz1Api;
import com.geek.biz1.bean.SNew1SearchBean;
import com.geek.biz1.view.SNew1SearchView;
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

public class SNew1SearchPresenter extends Presenter<SNew1SearchView> {

    public void getNew1SearchData(String keyword, String limit, final int which) {
        JSONObject requestData = new JSONObject();
        requestData.put("keyword", keyword);
        requestData.put("limit", limit);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), requestData.toString());
        RetrofitNetNew.build(Biz1Api.class, getIdentifier()).get_my_search_new1(BuildConfigyewu.SERVER_ISERVICE_NEW1 + "liveApp/login",
                BanbenUtils.getInstance().getVersion(),
                BanbenUtils.getInstance().getImei(),
                "", requestBody).enqueue(new Callback<ResponseSlbBean<SNew1SearchBean>>() {
            @Override
            public void onResponse(Call<ResponseSlbBean<SNew1SearchBean>> call, Response<ResponseSlbBean<SNew1SearchBean>> response) {
                if (!hasView()) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() != 0) {
                    getView().OnNew1SearchNodata(response.body().getMsg(), which);
                    return;
                }
                getView().OnNew1SearchSuccess(response.body().getData(), which);
                call.cancel();
            }

            @Override
            public void onFailure(Call<ResponseSlbBean<SNew1SearchBean>> call, Throwable t) {
                if (!hasView()) {
                    return;
                }
//                String string = t.toString();
                String string = BanbenUtils.getInstance().net_tips;
                getView().OnNew1SearchFail(string, which);
                call.cancel();
            }
        });

    }
}
