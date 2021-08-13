package com.fosung.eduapi.presenter;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.fosung.eduapi.api.BizEduResApi;
import com.fosung.eduapi.bean.EduResExecutorReplyBean;
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

public class EduResTerminalPresenter extends Presenter<EduResTerminalView> {

    public static final String GET_EDU_DETAIL  = "/app/base/terminal/lazyTerminal";

    /**
     * @param
     */
    public void getEduResTerminalRequest(String typeFlag, String parentId,String orgId, String names) {
        JSONObject requestData = new JSONObject();
        requestData.put("typeFlag", typeFlag);
        requestData.put("orgId", orgId);
        requestData.put("parentId", parentId);
        requestData.put("names", names);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), requestData.toString());

        RetrofitNetNew.build(BizEduResApi.class, getIdentifier()).getEduResExtendTerminal(BuildConfigyewu.SERVER_ISERVICE_NEW2 + GET_EDU_DETAIL,  requestBody)
                .enqueue(new Callback<EduResExecutorReplyBean>() {
            @Override
            public void onResponse(Call<EduResExecutorReplyBean> call, Response<EduResExecutorReplyBean> response) {
                Log.d("请求数据",response.body() + "");

                if (!hasView()) {
                    return;
                }
                if (response.body() == null) {
                    getView().OnEduResTerminalFail("请求数据出错");
                    return;
                }
                if (!response.body().isSuccess()) {
                    getView().OnEduResTerminalFail(response.body().getMessage());
                    return;
                }
                getView().OnEduResTerminalSuccess(response.body());
                call.cancel();
            }

            @Override
            public void onFailure(Call<EduResExecutorReplyBean> call, Throwable t) {
                if (!hasView()) {
                    return;
                }
//                String string = t.toString();
                String string = BanbenUtils.getInstance().net_tips;
                Log.d("请求数据出错",string);
                getView().OnEduResTerminalFail(string);
                call.cancel();
            }
        });

    }

}
