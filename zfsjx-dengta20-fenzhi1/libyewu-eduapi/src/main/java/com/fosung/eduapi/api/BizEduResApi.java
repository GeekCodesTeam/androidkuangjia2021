package com.fosung.eduapi.api;

import com.fosung.eduapi.bean.EduResAuditRecordBean;
import com.fosung.eduapi.bean.EduResCompanyExecutorReplyBean;
import com.fosung.eduapi.bean.EduResExecutorReplyBean;
import com.fosung.eduapi.bean.EduResInfoBean;
import com.fosung.eduapi.bean.EduResourceDetailBean;
import com.fosung.eduapi.bean.EduResourceExamTypeBean;
import com.fosung.eduapi.bean.EduResourceListBean;
import com.haier.cellarette.libretrofit.common.ResponseSlbBean1;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface BizEduResApi {

    // 资源库列表
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST()
    Call<EduResourceListBean> getEduReslist(@Url String path, @Body RequestBody body);

    // 资源库详情
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST()
    Call<EduResourceDetailBean> getEduResDetail(@Url String path, @Body RequestBody body);

    // 资源库详情 --审核页面
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST()
    Call<EduResourceExamTypeBean> getEduResExamType(@Url String path, @Body RequestBody body);

    // 资源库详情 --锁定审核
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST()
    Call<ResponseSlbBean1> lockAuditEduRes(@Url String path, @Body RequestBody body);

    // 资源库详情 --取消审核
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST()
    Call<ResponseSlbBean1> cancelLockEduRes(@Url String path, @Body RequestBody body);

    // 资源库报审记录
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST()
    Call<EduResAuditRecordBean> getEduResAuditRecord(@Url String path, @Body RequestBody body);

    // 资源库详情 --提交审核
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST()
    Call<ResponseSlbBean1> submitAuditEduRes(@Url String path, @Body RequestBody body);

    // 资源库详情 --扩展信息
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST()
    Call<EduResInfoBean> getEduResExtendInfo(@Url String path, @Body RequestBody body);

    // 资源库 --报送终端
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST()
    Call<EduResExecutorReplyBean> getEduResExtendTerminal(@Url String path, @Body RequestBody body);

    // 资源库 --报送单位
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST()
    Call<EduResCompanyExecutorReplyBean> getEduResExtendCompany(@Url String path, @Body RequestBody body);

}
