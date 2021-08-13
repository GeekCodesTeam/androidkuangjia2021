package com.fosung.eduapi.presenter;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.fosung.eduapi.api.BizEduResApi;
import com.fosung.eduapi.bean.EduResourceExamTypeBean;
import com.fosung.eduapi.view.EduResExamTypeView;
import com.fosung.lighthouse.app.BuildConfigyewu;
import com.geek.libmvp.Presenter;
import com.haier.cellarette.libretrofit.common.BanbenUtils;
import com.haier.cellarette.libretrofit.common.RetrofitNetNew;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EduResExamTypePresenter extends Presenter<EduResExamTypeView> {

    public static final String GET_EDU_DETAIL  = "/app/audit/auditinfo/queryAuditSelect";

    /**
     * @param
     */
    public void getEduResExamTypeRequest(String id) {
        JSONObject requestData = new JSONObject();
        requestData.put("resourceId", id);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), requestData.toString());

        RetrofitNetNew.build(BizEduResApi.class, getIdentifier()).getEduResExamType(BuildConfigyewu.SERVER_ISERVICE_NEW2 + GET_EDU_DETAIL,  requestBody)
                .enqueue(new Callback<EduResourceExamTypeBean>() {
            @Override
            public void onResponse(Call<EduResourceExamTypeBean> call, Response<EduResourceExamTypeBean> response) {
                Log.d("请求数据",response.body() + "");

                if (!hasView()) {
                    return;
                }
                if (response.body() == null) {
                    getView().OnEduResExamTypeFail("请求数据出错");
                    return;
                }
                if (!response.body().isSuccess()) {
                    getView().OnEduResExamTypeFail(response.body().getMessage());
                    return;
                }
                getView().OnEduResExamTypeSuccess(response.body());
                call.cancel();
            }

            @Override
            public void onFailure(Call<EduResourceExamTypeBean> call, Throwable t) {
                if (!hasView()) {
                    return;
                }
//                String string = t.toString();
                String string = BanbenUtils.getInstance().net_tips;
                Log.d("请求数据出错",string);
                getView().OnEduResExamTypeFail(string);
                call.cancel();
            }
        });

    }

}
