package com.fosung.eduapi.presenter;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.fosung.eduapi.api.BizEduResApi;
import com.fosung.eduapi.view.EduResCancelLockView;
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

public class EduResCancelLockPresenter extends Presenter<EduResCancelLockView> {

    public static final String GET_EDU_DETAIL  = "/app/audit/auditinfo/auditCancelClaim/";

    /**
     * @param
     */
    public void cancelLockEduResRequest(String id,String signName, String sign) {
        JSONObject requestData = new JSONObject();
        requestData.put("signName", signName);
        requestData.put("id", id);
        requestData.put("sign", sign);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), requestData.toString());

        RetrofitNetNew.build(BizEduResApi.class, getIdentifier()).cancelLockEduRes(BuildConfigyewu.SERVER_ISERVICE_NEW2 + GET_EDU_DETAIL + id,  requestBody)
                .enqueue(new Callback<ResponseSlbBean1>() {
            @Override
            public void onResponse(Call<ResponseSlbBean1> call, Response<ResponseSlbBean1> response) {
                Log.d("请求数据",response.body() + "");

                if (!hasView()) {
                    return;
                }
                if (response.body() == null) {
                    getView().OnEduResCancelLockFail("请求数据出错");
                    return;
                }
                if (!response.body().isSuccess()) {
                    getView().OnEduResCancelLockFail(response.body().getMessage());
                    return;
                }
                getView().OnEduResCancelLockSuccess(response.body());
                call.cancel();
            }

            @Override
            public void onFailure(Call<ResponseSlbBean1> call, Throwable t) {
                if (!hasView()) {
                    return;
                }
//                String string = t.toString();
                String string = BanbenUtils.getInstance().net_tips;
                Log.d("请求数据出错",string);
                getView().OnEduResCancelLockFail(string);
                call.cancel();
            }
        });

    }

}
