package com.fosung.lighthouse.manage.myapi.presenter;

import com.alibaba.fastjson.JSONObject;
import com.fosung.lighthouse.app.BuildConfigyewu;
import com.fosung.lighthouse.manage.myapi.api.AccoutEduResApi;
import com.fosung.lighthouse.manage.myapi.bean.AccountActivitionBean;
import com.fosung.lighthouse.manage.myapi.view.EduResVerCodeView;
import com.geek.libmvp.Presenter;
import com.haier.cellarette.libretrofit.common.BanbenUtils;
import com.haier.cellarette.libretrofit.common.RetrofitNetNew;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VercodePresenter extends Presenter<EduResVerCodeView> {

    public static final String VER_CODE = "/sso/app/register/sendMessage";

    public VercodePresenter(EduResVerCodeView view) {
        super.onCreate(view);
    }

    public void getVerCode(int type, String validateCode, String telePhone, String idCard) {
        JSONObject requestData = new JSONObject();
        requestData.put("type", type);
        requestData.put("validateCode", validateCode);
        requestData.put("telePhone", telePhone);
        requestData.put("idCard", idCard);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), requestData.toString());

        RetrofitNetNew.build(AccoutEduResApi.class, getIdentifier()).accountActivation(BuildConfigyewu.SERVER_ISERVICE_NEW1 + VER_CODE, requestBody)
                .enqueue(new Callback<AccountActivitionBean>() {
                    @Override
                    public void onResponse(Call<AccountActivitionBean> call, Response<AccountActivitionBean> response) {

                        if (!hasView()) {
                            return;
                        }
                        if (response.body() == null) {
                            return;
                        }
                        if (!response.body().isSuccess()) {
                            getView().OnEduResVerCodeFail(response.body().getMessage());
                            return;
                        }
                        getView().OnEduResVerCodeSuccess();
                        call.cancel();
                    }

                    @Override
                    public void onFailure(Call<AccountActivitionBean> call, Throwable t) {
                        if (!hasView()) {
                            return;
                        }
                        String string = BanbenUtils.getInstance().net_tips;
                        getView().OnEduResVerCodeFail(string);
                        call.cancel();
                    }
                });

    }

}
