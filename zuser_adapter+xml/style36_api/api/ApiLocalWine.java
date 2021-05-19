package com.haier.newmvp.api;

import com.haier.banben.VersionConfig;
import com.haier.newmvp.bean.LocalWineListRequestBody;
import com.haier.newmvp.bean.LocalWineResultBean;
import com.haier.newmvp.bean.WineKindResultBean;
import com.haier.online.shop.online_shop.home.bean.ShopKindResultBean;
import com.haier.wine_commen.html.ServiceAddr;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Author: YuanFan
 * E-mail: yuanfan@smart-haier.com
 * Date: on 2018/1/2/0002.
 * Description:本酒柜酒品API
 */

public interface ApiLocalWine {

    /**
     * 获取酒品产地+类型
     *
     * @return
     */
    @POST(VersionConfig.SERVICE_HOST+ServiceAddr.WINE_TYPE_AREA)
    Call<WineKindResultBean> getWineTypeAndArea(@Header("accessToken") String accessTokeny);
    /**
     * 获取酒品产地+类型
     *
     * @return
     */
    @POST(VersionConfig.SERVICE_HOST+ServiceAddr.V2_WINE_TYPE_AREA)
    Call<WineKindResultBean> getTypeAndArea(@Header("accessToken") String accessTokeny, @Body RequestBody json);

    /**
     * 获取酒品列表
     *
     * @return
     */
    @POST(VersionConfig.SERVICE_HOST+ServiceAddr.SEARCH_TYPE_AREA)
    Call<LocalWineResultBean> getLocalWineList(@Header("accessToken") String accessTokeny, @Body LocalWineListRequestBody body);
}
