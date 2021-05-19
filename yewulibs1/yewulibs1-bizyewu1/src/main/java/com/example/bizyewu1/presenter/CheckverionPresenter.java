package com.example.bizyewu1.presenter;

import com.alibaba.fastjson.JSONObject;
import com.example.bizyewu1.api.SCommonApi;
import com.example.bizyewu1.bean.VersionInfoBean;
import com.example.bizyewu1.view.CheckverionView;
import com.fosung.lighthouse.test.BuildConfigApp;
import com.haier.cellarette.libmvp.mvp.Presenter;
import com.haier.cellarette.libretrofit.common.BanbenUtils;
import com.haier.cellarette.libretrofit.common.ResponseSlbBean;
import com.haier.cellarette.libretrofit.common.RetrofitNetNew;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
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
                .get_version1(BuildConfigApp.SERVER_ISERVICE_NEW1 + "picbook/audio/app/updateReadTime",
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
                        getView().OnUpdateVersionFail(BuildConfigApp.SERVER_ISERVICE_NEW1);
                        t.printStackTrace();
                        call.cancel();
                    }
                });
    }

    // Base64上传bufen
    public void checkVerion2(String base64) {
        JSONObject requestData = new JSONObject();
        requestData.put("base64", base64);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), requestData.toString());
        RetrofitNetNew.build(SCommonApi.class, getIdentifier())
                .get_version2(BuildConfigApp.SERVER_ISERVICE_NEW1 + "picbook/audio/app/updateReadTime",
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
                        getView().OnUpdateVersionFail(BuildConfigApp.SERVER_ISERVICE_NEW1);
                        t.printStackTrace();
                        call.cancel();
                    }
                });
    }

    // file方式上传bufen
    public void checkVerion3(File imgfile) {
        MultipartBody.Part requestBody = MultipartBody.Part.createFormData("file", imgfile.getName(), RequestBody.create(MediaType.parse("image/*"), imgfile));
        RetrofitNetNew.build(SCommonApi.class, getIdentifier())
                .get_version3(BuildConfigApp.SERVER_ISERVICE_NEW1 + "picbook/audio/app/updateReadTime",
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
                        getView().OnUpdateVersionFail(BuildConfigApp.SERVER_ISERVICE_NEW1);
                        t.printStackTrace();
                        call.cancel();
                    }
                });
    }

    // 多file混合方式上传bufen
    public void checkVerion4(List<File> fileList) {
        Map<String, RequestBody> params = new HashMap<>();
        //以下参数是，参数需要换成自己服务器支持的
        params.put("uid", convertToRequestBody("1701"));
        params.put("token", convertToRequestBody("E6C4C1B581A506F2F4D6748B3649AD3C"));
        params.put("source", convertToRequestBody("android"));
        params.put("appVersion", convertToRequestBody("101"));

//        JSONObject requestData = new JSONObject();
//        requestData.put("nickName", "");
//        RequestBody requestBody1 = RequestBody.create(MediaType.parse("text/plain"), requestData.toString());
        List<MultipartBody.Part> partList = filesToMultipartBodyParts(fileList);
        RetrofitNetNew.build(SCommonApi.class, getIdentifier())
                .get_version4(BuildConfigApp.SERVER_ISERVICE_NEW1 + "picbook/audio/app/updateReadTime",
                        BanbenUtils.getInstance().getVersion(),
                        BanbenUtils.getInstance().getImei(),
                        "",
                        params,
                        partList)
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
                        getView().OnUpdateVersionFail(BuildConfigApp.SERVER_ISERVICE_NEW1);
                        t.printStackTrace();
                        call.cancel();
                    }
                });

    }

    private RequestBody convertToRequestBody(String param) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), param);
        return requestBody;
    }


    private List<MultipartBody.Part> filesToMultipartBodyParts(List<File> files) {
        List<MultipartBody.Part> parts = new ArrayList<>(files.size());
        for (File file : files) {
            //设置文件的类型
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            //file就是上传文件的参数类型,后面的file.getName()就是你上传的文件,首先要拿到文件的地址
            MultipartBody.Part part = MultipartBody.Part.createFormData("files", file.getName(), requestBody);
            parts.add(part);
        }
        return parts;
    }


    public void checkVerion4(String pageNum, String pageSize, String searchKey) {
        RetrofitNetNew.build(SCommonApi.class, getIdentifier()).get_version5(pageNum, pageSize, searchKey)
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
                        getView().OnUpdateVersionFail(BuildConfigApp.SERVER_ISERVICE_NEW1);
                        t.printStackTrace();
                        call.cancel();
                    }
                });
    }


}
