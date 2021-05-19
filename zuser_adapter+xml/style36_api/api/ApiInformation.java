package com.haier.newmvp.api;

import com.haier.banben.VersionConfig;
import com.haier.cellarette.bean.CodeAndNickNameBean;
import com.haier.newmvp.bean.InfoHomeResultBean;
import com.haier.newmvp.bean.InformationResultBean;
import com.haier.wine_commen.html.ServiceAddr;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Author: YuanFan
 * E-mail: yuanfan@smart-haier.com
 * Date: on 2018/1/5/0005.
 * Description:资讯API
 */

public interface ApiInformation {

    /**
     * 资讯一级页面
     * @param accessToken
     * @return
     */
    @POST(VersionConfig.SERVICE_HOST+ ServiceAddr.CONSULT_HOME_URL)
    Call<InfoHomeResultBean> getInfoHome(@Header("accessToken") String accessToken);

    /**
     * 资讯二级页面
     * @param json
     * @return
     */
    @POST(VersionConfig.SERVICE_HOST+ ServiceAddr.CONSULT_LIST_URL)
    Call<InformationResultBean> getInfoScondary(@Body RequestBody json);
}
