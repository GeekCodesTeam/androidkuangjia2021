package com.fosung.lighthouse.manage.myapi.presenter;

import com.alibaba.fastjson.JSONObject;
import com.fosung.lighthouse.app.BuildConfigyewu;
import com.fosung.lighthouse.manage.myapi.api.AccoutEduResApi;
import com.fosung.lighthouse.manage.myapi.bean.AccountActivitionBean;
import com.fosung.lighthouse.manage.myapi.bean.UserLoginBean;
import com.fosung.lighthouse.manage.myapi.http.HttpUrlMy;
import com.fosung.lighthouse.manage.myapi.view.EduResAccountTwoView;
import com.fosung.lighthouse.manage.myapi.view.EduResAccountView;
import com.fosung.lighthouse.manage.myapi.view.EduResLogouttView;
import com.geek.libmvp.Presenter;
import com.haier.cellarette.libretrofit.common.BanbenUtils;
import com.haier.cellarette.libretrofit.common.ResponseSlbBean1;
import com.haier.cellarette.libretrofit.common.RetrofitNetNew;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountPresenter extends Presenter<EduResAccountView> {

    private EduResAccountTwoView viewTwo;
    private EduResLogouttView viewLogout;

    public AccountPresenter() {
    }

    public void setViewTwo(EduResAccountTwoView viewTwo) {
        this.viewTwo = viewTwo;
    }

    public void setViewLogout(EduResLogouttView viewLogout) {
        this.viewLogout = viewLogout;
    }

    public AccountPresenter(EduResAccountView view, EduResAccountTwoView viewTwo) {
        this.onCreate(view);
        this.viewTwo = viewTwo;
    }

    public AccountPresenter(EduResAccountView view) {
        this.onCreate(view);
    }

    //激活前校验
    public void checkAccountActivition(String telePhone, String idCard, String name, String authorizationCode, String verifyCode) {
        JSONObject requestData = new JSONObject();
        requestData.put("telePhone", telePhone);
        requestData.put("idCard", idCard);
        requestData.put("name", name);
        requestData.put("authorizationCode", authorizationCode);
        requestData.put("verifyCode", verifyCode);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), requestData.toString());
        RetrofitNetNew.build(AccoutEduResApi.class, getIdentifier()).accountActivation(HttpUrlMy.Companion.getACCOUNT_CHECK(), requestBody)
                .enqueue(new Callback<AccountActivitionBean>() {
                    @Override
                    public void onResponse(Call<AccountActivitionBean> call, Response<AccountActivitionBean> response) {

                        if (!hasView()) {
                            return;
                        }
                        if (response.body() == null) {
                            getView().OnEduResCommonFail("服务器异常");
                            return;
                        }
                        if (!response.body().isSuccess()) {
                            getView().OnEduResCommonFail(response.body().getMessage());
                            return;
                        }
                        getView().OnEduResCommonSuccess(response.body());
                        call.cancel();
                    }

                    @Override
                    public void onFailure(Call<AccountActivitionBean> call, Throwable t) {
                        if (!hasView()) {
                            return;
                        }
                        String string = BanbenUtils.getInstance().net_tips;
                        getView().OnEduResCommonFail(string);
                        call.cancel();
                    }
                });

    }

    public void accountActivition(String telePhone, String validateCode, String idCard, String name, String authorizationCode, String verifyCode, String password, String vpnpwd) {
        JSONObject requestData = new JSONObject();
        requestData.put("telePhone", telePhone);
        requestData.put("validateCode", validateCode);
        requestData.put("verifyCode", verifyCode);
        requestData.put("authorizationCode", authorizationCode);
        requestData.put("password", password);
        requestData.put("vpnpwd", vpnpwd);
        requestData.put("name", name);
        requestData.put("idCard", idCard);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), requestData.toString());
        RetrofitNetNew.build(AccoutEduResApi.class, getIdentifier()).accountActivation(HttpUrlMy.Companion.getACCOUNT_ACTIVITYTION(), requestBody)
                .enqueue(new Callback<AccountActivitionBean>() {
                    @Override
                    public void onResponse(Call<AccountActivitionBean> call, Response<AccountActivitionBean> response) {

                        if (!hasView()) {
                            return;
                        }
                        if (response.body() == null) {
                            getView().OnEduResCommonFail("服务器异常");
                            return;
                        }
                        if (!response.body().isSuccess()) {
                            getView().OnEduResCommonFail(response.body().getMessage());
                            return;
                        }
                        getView().OnEduResCommonSuccess(response.body());
                        call.cancel();
                    }

                    @Override
                    public void onFailure(Call<AccountActivitionBean> call, Throwable t) {
                        if (!hasView()) {
                            return;
                        }
                        String string = BanbenUtils.getInstance().net_tips;
                        getView().OnEduResCommonFail(string);
                        call.cancel();
                    }
                });
    }

    //绑定校验
    public void checkVpnBind(String vpn, String validateCode) {
        JSONObject requestData = new JSONObject();
        requestData.put("vpn", vpn);
        requestData.put("validateCode", validateCode);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), requestData.toString());
        RetrofitNetNew.build(AccoutEduResApi.class, getIdentifier()).checkVpnBind(HttpUrlMy.Companion.getCHECK_VPN_BIND(), requestBody)
                .enqueue(new Callback<ResponseSlbBean1<Object>>() {
                    @Override
                    public void onResponse(Call<ResponseSlbBean1<Object>> call, Response<ResponseSlbBean1<Object>> response) {

                        if (!hasView()) {
                            return;
                        }
                        if (response.body() == null) {
                            getView().OnEduResCommonFail("服务器异常");
                            return;
                        }
                        if (!response.body().isSuccess()) {
                            getView().OnEduResCommonFail(response.body().getMessage());
                            return;
                        }
                        getView().OnEduResCommonSuccess(response.body());
                        call.cancel();
                    }

                    @Override
                    public void onFailure(Call<ResponseSlbBean1<Object>> call, Throwable t) {
                        if (!hasView()) {
                            return;
                        }
                        String string = BanbenUtils.getInstance().net_tips;
                        getView().OnEduResCommonFail(string);
                        call.cancel();
                    }
                });
    }


    //绑定账号
    public void vpnBindAccount(String vpn, String vpnPas, String userName, String password) {
        JSONObject requestData = new JSONObject();
        requestData.put("vpn", vpn);
        requestData.put("vpnPassword", vpnPas);
        requestData.put("userName", userName);
        requestData.put("password", password);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), requestData.toString());
        RetrofitNetNew.build(AccoutEduResApi.class, getIdentifier()).login(HttpUrlMy.Companion.getVPN_BING_USER_INFO(), requestBody)
                .enqueue(new Callback<UserLoginBean>() {
                    @Override
                    public void onResponse(Call<UserLoginBean> call, Response<UserLoginBean> response) {

                        if (viewTwo == null) {
                            return;
                        }
                        if (response.body() == null) {
                            viewTwo.onEduResVpnUserInfoFail("服务器异常");
                            return;
                        }
                        if (!response.body().isSuccess()) {
                            viewTwo.onEduResVpnUserInfoFail(response.body().getMessage());
                            return;
                        }
                        viewTwo.onEduResVpnUserInfoSuccess(response.body());
                        call.cancel();
                    }

                    @Override
                    public void onFailure(Call<UserLoginBean> call, Throwable t) {
                        if (!hasView()) {
                            return;
                        }
                        String string = BanbenUtils.getInstance().net_tips;
                        viewTwo.onEduResVpnUserInfoFail(string);
                        call.cancel();
                    }
                });
    }

    //通过绑定获取用户信息
    public void vpnBindUserInfo(String vpn, String vpnPas) {
        JSONObject requestData = new JSONObject();
        requestData.put("vpn", vpn);
        requestData.put("vpnPassword", vpnPas);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), requestData.toString());
        RetrofitNetNew.build(AccoutEduResApi.class, getIdentifier()).login(HttpUrlMy.Companion.getVPN_BING_USER_INFO(), requestBody)
                .enqueue(new Callback<UserLoginBean>() {
                    @Override
                    public void onResponse(Call<UserLoginBean> call, Response<UserLoginBean> response) {

                        if (viewTwo == null) {
                            return;
                        }
                        if (response.body() == null) {
                            viewTwo.onEduResVpnUserInfoFail("服务器异常");
                            return;
                        }
                        if (!response.body().isSuccess()) {
                            viewTwo.onEduResVpnUserInfoFail(response.body().getMessage());
                            return;
                        }
                        viewTwo.onEduResVpnUserInfoSuccess(response.body());
                        call.cancel();
                    }

                    @Override
                    public void onFailure(Call<UserLoginBean> call, Throwable t) {
                        if (!hasView()) {
                            return;
                        }
                        String string = BanbenUtils.getInstance().net_tips;
                        viewTwo.onEduResVpnUserInfoFail(string);
                        call.cancel();
                    }
                });
    }


    //用户登录
    public void login(String userName, String password) {
        JSONObject requestData = new JSONObject();
        requestData.put("userName", userName);
        requestData.put("password", password);
        requestData.put("vpn", "370125199010027037");

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), requestData.toString());
        RetrofitNetNew.build(AccoutEduResApi.class, getIdentifier()).login(HttpUrlMy.Companion.getUSER_LOGIN(), requestBody)
                .enqueue(new Callback<UserLoginBean>() {
                    @Override
                    public void onResponse(Call<UserLoginBean> call, Response<UserLoginBean> response) {

                        if (!hasView()) {
                            return;
                        }
                        if (response.body() == null) {
                            getView().OnEduResCommonFail("服务器异常");
                            return;
                        }
                        if (!response.body().isSuccess()) {
                            getView().OnEduResCommonFail(response.body().getMessage());
                            return;
                        }
                        getView().OnEduResCommonSuccess(response.body());
                        call.cancel();
                    }

                    @Override
                    public void onFailure(Call<UserLoginBean> call, Throwable t) {
                        if (!hasView()) {
                            return;
                        }
                        String string = BanbenUtils.getInstance().net_tips;
                        getView().OnEduResCommonFail(string);
                        call.cancel();
                    }
                });
    }

    //修改手机号
    public void changePhone(String verifyCode, String oldMobile, String newMobile) {
        JSONObject requestData = new JSONObject();
        requestData.put("verifyCode", verifyCode);
        requestData.put("oldMobile", oldMobile);
        requestData.put("name", newMobile);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), requestData.toString());

        RetrofitNetNew.build(AccoutEduResApi.class, getIdentifier()).changePhone(HttpUrlMy.Companion.getCHANGE_PHONE(), requestBody)
                .enqueue(new Callback<ResponseSlbBean1<Object>>() {
                    @Override
                    public void onResponse(Call<ResponseSlbBean1<Object>> call, Response<ResponseSlbBean1<Object>> response) {
                        if (!hasView()) {
                            return;
                        }
                        if (response.body() == null) {
                            getView().OnEduResCommonFail("服务器异常");
                            return;
                        }
                        if (!response.body().isSuccess()) {
                            getView().OnEduResCommonFail(response.body().getMessage());
                            return;
                        }
                        getView().OnEduResCommonSuccess(response.body());
                        call.cancel();
                    }

                    @Override
                    public void onFailure(Call<ResponseSlbBean1<Object>> call, Throwable t) {
                        if (!hasView()) {
                            return;
                        }
                        String string = BanbenUtils.getInstance().net_tips;
                        getView().OnEduResCommonFail(string);
                        call.cancel();
                    }
                });

    }

    //修改管理员密码
    public void changePass(String newPassword, String rnewpassword, String oldPassword) {
        JSONObject requestData = new JSONObject();
        requestData.put("newPassword", newPassword);
        requestData.put("rnewpassword", rnewpassword);
        requestData.put("oldPassword", oldPassword);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), requestData.toString());

        RetrofitNetNew.build(AccoutEduResApi.class, getIdentifier()).changePass(HttpUrlMy.Companion.getCHANGE_LOGIN_PASS(), requestBody)
                .enqueue(new Callback<ResponseSlbBean1<Object>>() {
                    @Override
                    public void onResponse(Call<ResponseSlbBean1<Object>> call, Response<ResponseSlbBean1<Object>> response) {
                        if (!hasView()) {
                            return;
                        }
                        if (response.body() == null) {
                            getView().OnEduResCommonFail("服务器异常");
                            return;
                        }
                        if (!response.body().isSuccess()) {
                            getView().OnEduResCommonFail(response.body().getMessage());
                            return;
                        }
                        getView().OnEduResCommonSuccess(response.body());
                        call.cancel();
                    }

                    @Override
                    public void onFailure(Call<ResponseSlbBean1<Object>> call, Throwable t) {
                        if (!hasView()) {
                            return;
                        }
                        String string = BanbenUtils.getInstance().net_tips;
                        getView().OnEduResCommonFail(string);
                        call.cancel();
                    }
                });

    }

    //修改vpn密码
    public void changeVpnPass(String newPassword, String rnewpassword, String vpn) {
        JSONObject requestData = new JSONObject();
        requestData.put("newPassword", newPassword);
        requestData.put("rnewpassword", rnewpassword);
        requestData.put("vpn", vpn);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), requestData.toString());

        RetrofitNetNew.build(AccoutEduResApi.class, getIdentifier()).changeVpnPass(HttpUrlMy.Companion.getCHANGE_VPN_PASS(), requestBody)
                .enqueue(new Callback<ResponseSlbBean1<Object>>() {
                    @Override
                    public void onResponse(Call<ResponseSlbBean1<Object>> call, Response<ResponseSlbBean1<Object>> response) {
                        if (!hasView()) {
                            return;
                        }
                        if (response.body() == null) {
                            getView().OnEduResCommonFail("服务器异常");
                            return;
                        }
                        if (!response.body().isSuccess()) {
                            getView().OnEduResCommonFail(response.body().getMessage());
                            return;
                        }
                        getView().OnEduResCommonSuccess(response.body());
                        call.cancel();
                    }

                    @Override
                    public void onFailure(Call<ResponseSlbBean1<Object>> call, Throwable t) {
                        if (!hasView()) {
                            return;
                        }
                        String string = BanbenUtils.getInstance().net_tips;
                        getView().OnEduResCommonFail(string);
                        call.cancel();
                    }
                });
    }

    //退出登录
    public void logout() {
        JSONObject requestData = new JSONObject();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), requestData.toString());

        RetrofitNetNew.build(AccoutEduResApi.class, getIdentifier()).changeVpnPass(HttpUrlMy.Companion.getUSER_LOGOUT(), requestBody)
                .enqueue(new Callback<ResponseSlbBean1<Object>>() {
                    @Override
                    public void onResponse(Call<ResponseSlbBean1<Object>> call, Response<ResponseSlbBean1<Object>> response) {
                        if (viewLogout == null) {
                            return;
                        }
                        if (response.body() == null) {
                            viewLogout.OnEduResLogoutFail("服务器异常");
                            return;
                        }
                        if (!response.body().isSuccess()) {
                            viewLogout.OnEduResLogoutFail(response.body().getMessage());
                            return;
                        }
                        viewLogout.onEduResLogoutSuccess(response.body());
                        call.cancel();
                    }

                    @Override
                    public void onFailure(Call<ResponseSlbBean1<Object>> call, Throwable t) {
                        if (!hasView()) {
                            return;
                        }
                        String string = BanbenUtils.getInstance().net_tips;
                        viewLogout.OnEduResLogoutFail(string);
                        call.cancel();
                    }
                });
    }

}
