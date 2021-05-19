package com.haier.newmvp.api;

import com.haier.banben.VersionConfig;
import com.haier.newmvp.bean.DeskGoodsBean;
import com.haier.online.shop.newshop.bean.NewWineDetailBean;
import com.haier.order.bean.ParseBaseBean;
import com.haier.wine.dialogactivity.QrCodeBean;
import com.haier.wine_commen.html.ServiceAddr;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by LRXx on 2018/1/10.
 */

public interface IPayApi {
    @GET(VersionConfig.PAY_URL + "mobile/index.php?act=wine_order&op=pay_qrcode&version=2")
    Call<QrCodeBean> getQrCode(@Query("alias") String mac,@Query("goods_id") String goods_id);

    @FormUrlEncoded
    @POST(VersionConfig.BASE_URL+ ServiceAddr.STORE_PAY_LIST)
    Call<DeskGoodsBean> getDeskList(@Field("alias") String mac, @Field("goods_info") String goods_info);
}
