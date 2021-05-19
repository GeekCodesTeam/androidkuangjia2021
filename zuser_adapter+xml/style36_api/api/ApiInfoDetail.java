package com.haier.newmvp.api;

import com.haier.banben.VersionConfig;
import com.haier.wine.consult.infodetail.bean.ConsultDetailResultBody;
import com.haier.wine_commen.html.ServiceAddr;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by A on 2018/1/12.
 * <P>
 *     资讯三级详情页api
 * </P>
 */

public interface ApiInfoDetail {
    //红酒资讯详情页
    @POST(VersionConfig.SERVICE_HOST + ServiceAddr.CONSULT_DETAIL_URL)
    Call<ConsultDetailResultBody> getDetail(@Header("accessToken") String accessToken, @Body RequestBody body);
}
