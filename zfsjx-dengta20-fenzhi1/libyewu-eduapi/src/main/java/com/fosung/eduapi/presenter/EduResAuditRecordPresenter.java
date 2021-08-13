package com.fosung.eduapi.presenter;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.fosung.eduapi.api.BizEduResApi;
import com.fosung.eduapi.bean.EduResAuditRecordBean;
import com.fosung.eduapi.view.EduResAuditRecordView;
import com.fosung.lighthouse.app.BuildConfigyewu;
import com.geek.libmvp.Presenter;
import com.haier.cellarette.libretrofit.common.BanbenUtils;
import com.haier.cellarette.libretrofit.common.RetrofitNetNew;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EduResAuditRecordPresenter extends Presenter<EduResAuditRecordView> {

    public static final String GET_EDU_LIST  = "/app/audit/auditlog/queryByParam";

    /**
     *{"onlyLevelOrgId": "030b9e46-b8ea-47ec-9feb-fb8c3eead801", "resourceId": "3495325460659705427"}
     *
     * @param
     */
    public void getEduReAuditRecordRequest(String id, String orgId) {
        JSONObject requestData = new JSONObject();
        requestData.put("resourceId", id);
        requestData.put("onlyLevelOrgId", orgId);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), requestData.toString());

        RetrofitNetNew.build(BizEduResApi.class, getIdentifier()).getEduResAuditRecord(BuildConfigyewu.SERVER_ISERVICE_NEW2 + GET_EDU_LIST,  requestBody)
                .enqueue(new Callback<EduResAuditRecordBean>() {
            @Override
            public void onResponse(Call<EduResAuditRecordBean> call, Response<EduResAuditRecordBean> response) {
                Log.d("请求数据",response.body() + "");

                if (!hasView()) {
                    return;
                }
                if (response.body() == null) {
                    getView().OnEduResAuditRecordFail("请求数据出错");
                    return;
                }
                if (!response.body().isSuccess()) {
                    getView().OnEduResAuditRecordFail(response.body().getMessage());
                    return;
                }
                getView().OnEduResAuditRecordSuccess(response.body());
                call.cancel();
            }

            @Override
            public void onFailure(Call<EduResAuditRecordBean> call, Throwable t) {
                if (!hasView()) {
                    return;
                }
//                String string = t.toString();
                String string = BanbenUtils.getInstance().net_tips;
                Log.d("请求数据出错",string);
                getView().OnEduResAuditRecordFail(string);
                call.cancel();
            }
        });

    }

}
