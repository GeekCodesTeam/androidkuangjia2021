package com.fosung.eduapi.presenter;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.fosung.eduapi.api.BizEduResApi;
import com.fosung.eduapi.bean.EduResInfoBean;
import com.fosung.eduapi.view.EduResCancelLockView;
import com.fosung.eduapi.view.EduResExtendInfoView;
import com.fosung.lighthouse.app.BuildConfigyewu;
import com.geek.libmvp.Presenter;
import com.haier.cellarette.libretrofit.common.BanbenUtils;
import com.haier.cellarette.libretrofit.common.ResponseSlbBean1;
import com.haier.cellarette.libretrofit.common.RetrofitNetNew;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EduResExtendInfoPresenter extends Presenter<EduResExtendInfoView> {

    public static final String GET_EDU_DETAIL  = "/app/manage/model/findModelBaseInfo";

    /**
     * @param
     */
    public void getEduResExtendInfoRequest(String id) {
        JSONObject requestData = new JSONObject();
        requestData.put("id", id);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), requestData.toString());

        RetrofitNetNew.build(BizEduResApi.class, getIdentifier()).getEduResExtendInfo(BuildConfigyewu.SERVER_ISERVICE_NEW2 + GET_EDU_DETAIL,  requestBody)
                .enqueue(new Callback<EduResInfoBean>() {
            @Override
            public void onResponse(Call<EduResInfoBean> call, Response<EduResInfoBean> response) {
                Log.d("请求数据",response.body() + "");

                if (!hasView()) {
                    return;
                }
                if (response.body() == null) {
                    getView().OnEduResExtendInfoFail("请求数据出错");
                    return;
                }
                if (!response.body().isSuccess()) {
                    getView().OnEduResExtendInfoFail(response.body().getMessage());
                    return;
                }
                getView().OnEduResExtendInfoSuccess(response.body());
                call.cancel();
            }

            @Override
            public void onFailure(Call<EduResInfoBean> call, Throwable t) {
                if (!hasView()) {
                    return;
                }
//                String string = t.toString();
                String string = BanbenUtils.getInstance().net_tips;
                Log.d("请求数据出错",string);
                getView().OnEduResExtendInfoFail(string);
                call.cancel();
            }
        });

    }

}
