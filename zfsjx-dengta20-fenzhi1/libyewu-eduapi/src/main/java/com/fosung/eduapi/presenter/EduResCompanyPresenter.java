package com.fosung.eduapi.presenter;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.fosung.eduapi.api.BizEduResApi;
import com.fosung.eduapi.bean.EduResCompanyExecutorReplyBean;
import com.fosung.eduapi.bean.EduResExecutorReplyBean;
import com.fosung.eduapi.view.EduResCompanyView;
import com.fosung.eduapi.view.EduResTerminalView;
import com.fosung.lighthouse.app.BuildConfigyewu;
import com.geek.libmvp.Presenter;
import com.haier.cellarette.libretrofit.common.BanbenUtils;
import com.haier.cellarette.libretrofit.common.RetrofitNetNew;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EduResCompanyPresenter extends Presenter<EduResCompanyView> {

    public static final String GET_EDU_DETAIL  = "/app/audit/support/querySubOrgs";

    /**
     * @param
     */
    public void getEduResCompanyRequest(String parentId) {
        JSONObject requestData = new JSONObject();
        requestData.put("source", "AREA");
        requestData.put("parentId", parentId);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), requestData.toString());

        RetrofitNetNew.build(BizEduResApi.class, getIdentifier()).getEduResExtendCompany(BuildConfigyewu.SERVER_ISERVICE_NEW2 + GET_EDU_DETAIL,  requestBody)
                .enqueue(new Callback<EduResCompanyExecutorReplyBean>() {
            @Override
            public void onResponse(Call<EduResCompanyExecutorReplyBean> call, Response<EduResCompanyExecutorReplyBean> response) {
                Log.d("请求数据",response.body() + "");

                if (!hasView()) {
                    return;
                }
                if (response.body() == null) {
                    getView().OnEduResCompanyFail("请求数据出错");
                    return;
                }
                if (!response.body().isSuccess()) {
                    getView().OnEduResCompanyFail(response.body().getMessage());
                    return;
                }
                getView().OnEduResCompanySuccess(response.body());
                call.cancel();
            }

            @Override
            public void onFailure(Call<EduResCompanyExecutorReplyBean> call, Throwable t) {
                if (!hasView()) {
                    return;
                }
//                String string = t.toString();
                String string = BanbenUtils.getInstance().net_tips;
                Log.d("请求数据出错",string);
                getView().OnEduResCompanyFail(string);
                call.cancel();
            }
        });

    }

}
