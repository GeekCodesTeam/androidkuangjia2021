package com.geek.biz1.presenter;

import com.geek.biz1.api.Biz1Api;
import com.geek.biz1.bean.SbbdBean;
import com.geek.biz1.view.SbbdView;
import com.geek.libmvp.Presenter;
import com.github.geekcodesteam.app.BuildConfigyewu;
import com.haier.cellarette.libretrofit.common.BanbenUtils;
import com.haier.cellarette.libretrofit.common.ResponseSlbBean;
import com.haier.cellarette.libretrofit.common.RetrofitNetNew;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SbbdPresenter extends Presenter<SbbdView> {

    public void getSbbdPresenter() {
        RetrofitNetNew.build(Biz1Api.class, getIdentifier())
                .get_version6("http://t-ums.ireign.cn/gwapi/ums/api/user/configure")
                .enqueue(new Callback<ResponseSlbBean<SbbdBean>>() {
                    @Override
                    public void onResponse(Call<ResponseSlbBean<SbbdBean>> call, Response<ResponseSlbBean<SbbdBean>> response) {
                        if (!hasView()) {
                            return;
                        }
                        if (response.body() == null) {
                            return;
                        }
                        if (!response.body().isSuccess()) {
                            getView().OnSbbdNodata(response.body().getMsg());
                            return;
                        }
                        getView().OnSbbdSuccess(response.body().getData());
                        call.cancel();
                    }

                    @Override
                    public void onFailure(Call<ResponseSlbBean<SbbdBean>> call, Throwable t) {
                        if (!hasView()) {
                            return;
                        }
                        String string = BanbenUtils.getInstance().error_tips;
                        getView().OnSbbdFail(BuildConfigyewu.SERVER_ISERVICE_NEW1);
                        t.printStackTrace();
                        call.cancel();
                    }
                });
    }
}
