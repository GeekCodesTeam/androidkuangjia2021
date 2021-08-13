package com.fosung.eduapi.presenter;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.fosung.eduapi.api.BizEduResApi;
import com.fosung.eduapi.view.EduResAuditSubmitView;
import com.fosung.eduapi.view.EduResLockAuditView;
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

public class EduResAuditSubmitPresenter extends Presenter<EduResAuditSubmitView> {

    public static final String GET_EDU_DETAIL  = "/app/audit/auditinfo/saveBatchAuditResourceInfo";

    /**
     * @param
     */
    public void submitAuditEduResRequest(String applyUserName, String applyUserId, String status, String auditStatus, String suggestion, String resourceGrade, String orgId, String orgName,String auditId, String resourceId) {
        JSONObject requestData = new JSONObject();
        requestData.put("applyUserName", applyUserName);
        requestData.put("applyUserId", applyUserId);
        requestData.put("status", status);
        requestData.put("auditStatus", auditStatus);
        requestData.put("suggestion", suggestion);
        requestData.put("resourceGrade", resourceGrade);
        requestData.put("orgId", orgId);
        requestData.put("orgName", orgName);

        JSONObject idItem = new JSONObject();
        idItem.put("auditId",auditId);
        idItem.put("resourceId",resourceId);

        requestData.put("resourceAuditParamList",idItem);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), requestData.toString());

        RetrofitNetNew.build(BizEduResApi.class, getIdentifier()).submitAuditEduRes(BuildConfigyewu.SERVER_ISERVICE_NEW2 + GET_EDU_DETAIL,  requestBody)
                .enqueue(new Callback<ResponseSlbBean1>() {
            @Override
            public void onResponse(Call<ResponseSlbBean1> call, Response<ResponseSlbBean1> response) {
                Log.d("请求数据",response.body() + "");

                if (!hasView()) {
                    return;
                }
                if (response.body() == null) {
                    getView().OnEduResAuditSubmitFail("请求数据出错");
                    return;
                }
                if (!response.body().isSuccess()) {
                    getView().OnEduResAuditSubmitFail(response.body().getMessage());
                    return;
                }
                getView().OnEduResAuditSubmitSuccess(response.body());
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
                getView().OnEduResAuditSubmitFail(string);
                call.cancel();
            }
        });

    }

}
