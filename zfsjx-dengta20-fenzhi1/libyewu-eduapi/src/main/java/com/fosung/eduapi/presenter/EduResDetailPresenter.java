package com.fosung.eduapi.presenter;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.fosung.eduapi.api.BizEduResApi;
import com.fosung.eduapi.bean.EduResourceDetailBean;
import com.fosung.eduapi.view.EduResDetailView;
import com.fosung.lighthouse.app.BuildConfigyewu;
import com.geek.libmvp.Presenter;
import com.haier.cellarette.libretrofit.common.BanbenUtils;
import com.haier.cellarette.libretrofit.common.RetrofitNetNew;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EduResDetailPresenter extends Presenter<EduResDetailView> {

    public static final String GET_EDU_DETAIL  = "/app/resource/av/get";

    /**
     * @param
     */
    public void getEduResDetailRequest(String id, String orgId) {
        JSONObject requestData = new JSONObject();
        requestData.put("id", id);
        requestData.put("orgId", orgId);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), requestData.toString());

        RetrofitNetNew.build(BizEduResApi.class, getIdentifier()).getEduResDetail(BuildConfigyewu.SERVER_ISERVICE_NEW2 + GET_EDU_DETAIL,  requestBody)
                .enqueue(new Callback<EduResourceDetailBean>() {
            @Override
            public void onResponse(Call<EduResourceDetailBean> call, Response<EduResourceDetailBean> response) {
                Log.d("请求数据",response.body() + "");

                if (!hasView()) {
                    return;
                }
                if (response.body() == null) {
                    getView().OnEduResDetailFail("请求数据出错");
                    return;
                }
                if (!response.body().isSuccess()) {
                    getView().OnEduResDetailFail(response.body().getMessage());
                    return;
                }
                getView().OnEduResDetailSuccess(response.body());
                call.cancel();
            }

            @Override
            public void onFailure(Call<EduResourceDetailBean> call, Throwable t) {
                if (!hasView()) {
                    return;
                }
//                String string = t.toString();
                String string = BanbenUtils.getInstance().net_tips;
                Log.d("请求数据出错",string);
                getView().OnEduResDetailFail(string);
                call.cancel();
            }
        });

    }

}
