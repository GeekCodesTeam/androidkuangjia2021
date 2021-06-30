///*
// * 官网地站:http://www.mob.com
// * 技术支持QQ: 4006852216
// * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
// *
// * Copyright (c) 2013年 mob.com. All rights reserved.
// */
//package com.fosung.lighthouse.test.wxapi;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//
//import com.blankj.utilcode.util.ToastUtils;
//import com.tencent.mm.opensdk.constants.ConstantsAPI;
//import com.tencent.mm.opensdk.modelbase.BaseReq;
//import com.tencent.mm.opensdk.modelbase.BaseResp;
//import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
//import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
//
///**
// * 微信客户端分享回调activity示例 WeChatHandleActivity
// */
//public class WXEntryActivitybeifen2 extends Activity implements IWXAPIEventHandler {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//    }
//
//    @Override
//    public void onReq(BaseReq resp) {
//        String aaa = "";
//        Log.e("--WXEntryActivity--", "sailubanoneWXEntryActivityonReq");
//    }
//
//    @Override
//    public void onResp(BaseResp baseResp) {
//        Log.e("--WXEntryActivity--", "sailubanoneWXEntryActivityonResp");
//        if (baseResp.getType() == ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM) {
//            // 小程序
//            // 调用
////            String appId = "wxd930ea5d5a258f4f"; // 填应用AppId
////            IWXAPI api = WXAPIFactory.createWXAPI(context, appId);
////
////            WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
////            req.userName = "gh_d43f693ca31f"; // 填小程序原始id
////            req.path = path;                  //拉起小程序页面的可带参路径，不填默认拉起小程序首页
////            req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;// 可选打开 开发版，体验版和正式版
////            api.sendReq(req);
//            // 回调
//            WXLaunchMiniProgram.Resp launchMiniProResp = (WXLaunchMiniProgram.Resp) baseResp;
//            String extraData = launchMiniProResp.extMsg; //对应小程序组件 <button open-type="launchApp"> 中的 app-parameter 属性
//            ToastUtils.showLong("进入了");
//        }
//    }
//
//}
