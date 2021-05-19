package com.haier.newmvp.api;

import com.haier.banben.VersionConfig;
import com.haier.newmvp.bean.RankListResult;
import com.haier.newmvp.bean.RecipeRecomListResult;
import com.haier.wine_commen.html.ServiceAddr;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by A on 2018/1/10.
 * <p>
 * 菜谱推荐一级(菜谱推荐列表和排行榜)
 * </P>
 */

public interface ApiRecipeRecomHome {
    /**
     * 获取菜谱推荐列表
     *
     * @param accessToken
     * @return
     */
    @POST(VersionConfig.SERVICE_HOST + ServiceAddr.RECIPE_LIST)
    Call<RecipeRecomListResult> getRecipeRecomList(@Header("accessToken") String accessToken);

    /**
     * 获取排行榜列表
     *
     * @param accessToken
     * @return
     */
    @POST(VersionConfig.SERVICE_HOST + ServiceAddr.RECIPE_RANK_LIST)
    Call<RankListResult> getRankList(@Header("accessToken") String accessToken);
}
