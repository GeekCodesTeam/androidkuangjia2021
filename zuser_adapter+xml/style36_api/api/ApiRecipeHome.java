package com.haier.newmvp.api;

import com.haier.banben.VersionConfig;
import com.haier.newmvp.bean.NewRecipeBean;
import com.haier.newmvp.bean.NewRecipeClassBean;
import com.haier.newmvp.bean.NewRecipeRequestBody;
import com.haier.wine_commen.html.ServiceAddr;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Author: YuanFan
 * E-mail: yuanfan@smart-haier.com
 * Date: on 2018/06/11.
 * Description:
 */
public interface ApiRecipeHome {
    /**
     * 获取菜谱分类列表
     * @param accessToken
     * @return
     */
    @POST(VersionConfig.SERVICE_HOST + ServiceAddr.RECIPE_CATEGORY_LIST)
    Call<NewRecipeClassBean> getRecipeGatecoryList(@Header("accessToken") String accessToken);

    /**
     * 获取菜品列表
     * @param accessToken
     * @param body
     * @return
     */
    @POST(VersionConfig.SERVICE_HOST + ServiceAddr.RECIPE_NEW_LIST)
    Call<NewRecipeBean> getRecipeList(@Header("accessToken") String accessToken, @Body NewRecipeRequestBody body);
}
