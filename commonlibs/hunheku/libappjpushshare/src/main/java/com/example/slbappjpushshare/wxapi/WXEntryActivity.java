///*
// * 官网地站:http://www.mob.com
// * 技术支持QQ: 4006852216
// * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
// *
// * Copyright (c) 2013年 mob.com. All rights reserved.
// */
//
//package com.example.slbappshare.wxapi;
//
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.tencent.mm.opensdk.constants.ConstantsAPI;
//import com.tencent.mm.opensdk.modelbase.BaseReq;
//import com.tencent.mm.opensdk.modelbase.BaseResp;
//import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
//import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
//
//import cn.jiguang.share.wechat.WeChatHandleActivity;
//
///**
// * 微信客户端回调activity示例
// */
//public class WXEntryActivity extends WeChatHandleActivity implements IWXAPIEventHandler {
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
//        Log.e("--WXEntryActivity--","slbappshareWXEntryActivityononReq");
//    }
//
//    @Override
//    public void onResp(BaseResp baseResp) {
//        Log.e("--WXEntryActivity--","slbappshareWXEntryActivityonResp");
//        if (baseResp.getType() == ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM) {
//            WXLaunchMiniProgram.Resp launchMiniProResp = (WXLaunchMiniProgram.Resp) baseResp;
//            String extraData = launchMiniProResp.extMsg; //对应小程序组件 <button open-type="launchApp"> 中的 app-parameter 属性
////            Toast.makeText(this, extraData, Toast.LENGTH_LONG).show();
//        }
//    }
//
//
//}
