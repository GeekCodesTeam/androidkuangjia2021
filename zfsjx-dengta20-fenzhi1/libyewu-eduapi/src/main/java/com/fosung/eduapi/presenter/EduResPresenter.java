package com.fosung.eduapi.presenter;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.fosung.eduapi.api.BizEduResApi;
import com.fosung.eduapi.bean.EduResourceListBean;
import com.fosung.eduapi.view.EduResListView;
import com.fosung.lighthouse.app.BuildConfigyewu;
import com.haier.cellarette.libmvp.mvp.Presenter;
import com.haier.cellarette.libretrofit.common.BanbenUtils;
import com.haier.cellarette.libretrofit.common.RetrofitNetNew;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EduResPresenter extends Presenter<EduResListView> {

    public static final String GET_EDU_LIST  = "/api/audit/auditinfo/queryPage";

    /**
     * {
     *     "auditStatus": "TO_AUDIT",
     *     "orgId": "030b9e46-b8ea-47ec-9feb-fb8c3eead801",
     *     "pageNum": 0,
     *     "pageSize": 15,
     *     "terminalIdList": [],
     *     "sign": "6330562072407953408",
     *     "owner": "6330562072407953408",
     *     "type": "VIDEO"
     * }
     * @param
     */
    public void getEduReListRequest(String auditStatus, String orgId, int pageNum, int pageSize, List<String> terminalIdList, String type, String sign, String owner) {
        JSONObject requestData = new JSONObject();
        requestData.put("auditStatus", auditStatus);
        requestData.put("orgId", orgId);
        requestData.put("pageNum", pageNum);
        requestData.put("pageSize", pageSize);
        requestData.put("terminalIdList", terminalIdList);
        requestData.put("type", type);
        requestData.put("sign", sign);
        requestData.put("owner", owner);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), requestData.toString());

        RetrofitNetNew.build(BizEduResApi.class, getIdentifier()).getEduReslist(BuildConfigyewu.SERVER_ISERVICE_NEW1 + GET_EDU_LIST,  requestBody)
                .enqueue(new Callback<EduResourceListBean>() {
            @Override
            public void onResponse(Call<EduResourceListBean> call, Response<EduResourceListBean> response) {
                Log.d("请求数据",response.body() + "");

                if (!hasView()) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (!response.body().isSuccess()) {
                    getView().OnEduResListFail(response.body().getMsg());
                    return;
                }
                getView().OnEduResListSuccess(response.body());
                call.cancel();
            }

            @Override
            public void onFailure(Call<EduResourceListBean> call, Throwable t) {
                if (!hasView()) {
                    return;
                }
//                String string = t.toString();
                String string = BanbenUtils.getInstance().net_tips;
                Log.d("请求数据出错",string);
                getView().OnEduResListFail(string);
                call.cancel();
            }
        });

    }

}
