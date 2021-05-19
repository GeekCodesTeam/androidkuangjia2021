package com.haier.newmvp.api;

import com.haier.banben.VersionConfig;
import com.haier.cellarette.bean.MatchWineResult;
import com.haier.newmvp.bean.ActivateResultEntity;
import com.haier.newmvp.bean.CodeAndNickNameBean;
import com.haier.newmvp.bean.ModuleConfigBean;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * <p>
 * 激活页面api
 * </p>
 */
public interface ModuleConfigApi {


    /**
     * 获取APP功能模块配置
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST(VersionConfig.SERVICE_HOST + "tft/config/authCom/new/getAppModuleConfig")
    Call<ModuleConfigBean> getModuleConfig(@Header("accessToken") String token);

}
