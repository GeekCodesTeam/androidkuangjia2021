package com.example.biz3slbappcomm.presenter;

import com.alibaba.fastjson.JSONObject;
import com.example.biz3slbappcomm.api.SCommonApi;
import com.example.biz3slbappcomm.bean.VersionInfoBean;
import com.example.biz3slbappcomm.view.CheckverionView;

import com.fosung.lighthouse.app.BuildConfigyewu;
import com.geek.libmvp.Presenter;
import com.haier.cellarette.libretrofit.common.BanbenUtils;
import com.haier.cellarette.libretrofit.common.ResponseSlbBean;
import com.haier.cellarette.libretrofit.common.RetrofitNetNew;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CheckverionPresenter extends Presenter<CheckverionView> {

    public void checkVerion(String aaa) {
        JSONObject requestData = new JSONObject();
        requestData.put("id", aaa);//
//        requestData.put("thirdPid", thirdPid);//
//        requestData.put("source", source);//
//        requestData.put("token", CommonUtil.getEncryptToken(id, url_me, date, ((JSONObject) JSON.toJSON(params)).toJSONString()));
//        requestData.put("params", JSON.toJSON(params));
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), requestData.toString());
        RetrofitNetNew.build(SCommonApi.class, getIdentifier())
                .get_version1(BuildConfigyewu.SERVER_ISERVICE_NEW1 + "picbook/audio/app/updateReadTime",
                        BanbenUtils.getInstance().getVersion(),
                        BanbenUtils.getInstance().getImei(),
                        "",
                        requestBody)
                .enqueue(new Callback<ResponseSlbBean<VersionInfoBean>>() {
                    @Override
                    public void onResponse(Call<ResponseSlbBean<VersionInfoBean>> call, Response<ResponseSlbBean<VersionInfoBean>> response) {
                        if (!hasView()) {
                            return;
                        }
                        if (response.body() == null) {
                            return;
                        }
                        if (response.body().getCode() != 0) {
                            getView().OnUpdateVersionNodata(response.body().getMsg());
                            return;
                        }
                        getView().OnUpdateVersionSuccess(response.body().getData());
                        call.cancel();
                    }

                    @Override
                    public void onFailure(Call<ResponseSlbBean<VersionInfoBean>> call, Throwable t) {
                        if (!hasView()) {
                            return;
                        }
                        String string = BanbenUtils.getInstance().error_tips;
                        getView().OnUpdateVersionFail(string);
                        t.printStackTrace();
                        call.cancel();
                    }
                });
    }
}
