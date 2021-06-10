/**
 * Copyright 2016,Smart Haier.All rights reserved
 */
package com.easefun.polyv.livedemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.easefun.polyv.livecloudclass.scenes.PLVLCCloudClassActivity;
import com.easefun.polyv.livecommon.module.config.PLVLiveChannelConfigFiller;
import com.easefun.polyv.livecommon.module.config.PLVLiveSDKConfig;
import com.easefun.polyv.livecommon.module.config.PLVLiveScene;
import com.easefun.polyv.livecommon.module.utils.result.PLVLaunchResult;
import com.easefun.polyv.livescenes.config.PolyvLiveChannelType;
import com.easefun.polyv.livescenes.feature.login.IPLVSceneLoginManager;
import com.easefun.polyv.livescenes.feature.login.PLVSceneLoginManager;
import com.easefun.polyv.livescenes.feature.login.PolyvLiveLoginResult;
import com.easefun.polyv.livescenes.feature.login.PolyvPlaybackLoginResult;
import com.easefun.polyv.livescenes.playback.video.PolyvPlaybackListType;
import com.geek.libutils.app.BaseApp;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * <p class="note">used as getApplication</p>
 */

public class PolyvUtils {

    private static final String TAG = PolyvUtils.class.getName();
    private String appId = "fdhjtb97mj";
    private String appSecret = "1ec091fc12c94109afbd8ed47c81f78c";
    private String userId = "496bff1348";
    private String channelId = "";// 直播
    private String vid = "";// 回放
    //manager
    private IPLVSceneLoginManager loginManager;
    //View
    private ProgressDialog loginProgressDialog;

    public void init(Activity activity) {
        PLVLiveSDKConfig.init(
                new PLVLiveSDKConfig.Parameter(BaseApp.get())
                        .isOpenDebugLog(true)
                        .isEnableHttpDns(true)
        );
        //初始化登录管理器
        loginManager = new PLVSceneLoginManager();
        //
        initDialog(activity);
    }

    public void init(Activity activity, String appId, String appSecret, String userId) {
        PLVLiveSDKConfig.init(
                new PLVLiveSDKConfig.Parameter(BaseApp.get())
                        .isOpenDebugLog(true)
                        .isEnableHttpDns(true)
        );
        this.appId = appId;
        this.appSecret = appSecret;
        this.userId = userId;
        //初始化登录管理器
        loginManager = new PLVSceneLoginManager();
        //
        initDialog(activity);
    }

    private void initDialog(Activity activity) {
        loginProgressDialog = new ProgressDialog(activity);
        loginProgressDialog.setMessage("正在登录中，请稍等...");
        loginProgressDialog.setCanceledOnTouchOutside(false);
        loginProgressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                loginManager.destroy();
            }
        });
    }

    //TODO 进入保利威直播间bufen
    public void into_polyv(Activity activity, String channelId) {
        loginProgressDialog.show();
        loginManager.loginLive(appId, appSecret, userId, channelId,
                new IPLVSceneLoginManager.OnLoginListener<PolyvLiveLoginResult>() {
                    @Override
                    public void onLoginSuccess(PolyvLiveLoginResult polyvLiveLoginResult) {
                        loginProgressDialog.dismiss();
                        PLVLiveChannelConfigFiller.setupAccount(userId, appId, appSecret);
                        PolyvLiveChannelType channelType = polyvLiveLoginResult.getChannelType();
                        if (PLVLiveScene.isCloudClassSceneSupportType(channelType)) {
                            PLVLaunchResult launchResult = PLVLCCloudClassActivity.launchLive(activity,
                                    channelId,
                                    channelType,
                                    getViewerId(),
                                    getViewerName(),
                                    getViewerAvatar());
                            if (!launchResult.isSuccess()) {
                                ToastUtils.showShort(launchResult.getErrorMessage());
                            }
                        } else {
//                            ToastUtils.showShort(com.easefun.polyv.livedemo.R.string.plv_scene_login_toast_cloudclass_no_support_type);
                            ToastUtils.showShort("云课堂场景仅支持三分屏和纯视频频道类型");
                        }
                    }

                    @Override
                    public void onLoginFailed(String msg, Throwable throwable) {
                        loginProgressDialog.dismiss();
                        ToastUtils.showShort(msg);
                        PLVCommonLog.e(TAG, "loginLive onLoginFailed:" + throwable.getMessage());
                    }
                });
    }

    //TODO 进入保利威直播回放bufen
    public void into_polyv_liveback(Activity activity, String channelId, String videoId) {
        loginProgressDialog.show();
        loginManager.loginPlayback(appId, appSecret, userId, channelId, videoId, new IPLVSceneLoginManager.OnLoginListener<PolyvPlaybackLoginResult>() {
            @Override
            public void onLoginSuccess(PolyvPlaybackLoginResult polyvPlaybackLoginResult) {
                loginProgressDialog.dismiss();
                PLVLiveChannelConfigFiller.setupAccount(userId, appId, appSecret);
                PolyvLiveChannelType channelType = polyvPlaybackLoginResult.getChannelType();
                if (PLVLiveScene.isCloudClassSceneSupportType(channelType)) {
                    PLVLaunchResult launchResult = PLVLCCloudClassActivity.launchPlayback(activity,
                            channelId,
                            channelType,
                            videoId,
                            getViewerId(),
                            getViewerName(),
                            getViewerAvatar(),
                            PolyvPlaybackListType.PLAYBACK
                    );
                    // PolyvPlaybackListType.VOD : PolyvPlaybackListType.PLAYBACK
                    if (!launchResult.isSuccess()) {
                        ToastUtils.showShort(launchResult.getErrorMessage());
                    }
                } else {
//                    ToastUtils.showShort(com.easefun.polyv.livedemo.R.string.plv_scene_login_toast_cloudclass_no_support_type);
                    ToastUtils.showShort("云课堂场景仅支持三分屏和纯视频频道类型");
                }
            }

            @Override
            public void onLoginFailed(String msg, Throwable throwable) {
                loginProgressDialog.dismiss();
                ToastUtils.showShort(msg);
                PLVCommonLog.e(TAG, "loginPlayback onLoginFailed:" + throwable.getMessage());
            }
        });
    }

    //TODO 进入本地直播回放bufen
    public void into_local_liveback(Activity activity) {
        loginProgressDialog.show();
        // 接口bufen
        Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.SimpleDetailActivityMode1");
        String url = "https://media6.smartstudy.com/ae/07/3997/2/dest.m3u8";
        intent.putExtra("url", url);
        activity.startActivity(intent);
        loginProgressDialog.dismiss();
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="获取登录参数">

    private String getViewerId() {
        /**
         *  todo 请务必在这里替换为你的学员(用户)ID，设置学员(用户)ID的意义详细可以查看：https://github.com/polyv/polyv-android-cloudClass-sdk-demo/wiki/6-%E8%AE%BE%E7%BD%AE%E5%AD%A6%E5%91%98%E5%94%AF%E4%B8%80%E6%A0%87%E8%AF%86%E7%9A%84%E6%84%8F%E4%B9%89
         */
        return PLVUtils.getAndroidId(BaseApp.get()) + "";
    }

    private String getViewerName() {
        /**
         * todo 请务必在这里替换为你的学员(用户)昵称
         */
        return "观众" + getViewerId();
    }

    private String getViewerAvatar() {
        //todo 在这里可替换为你的学员(用户)头像地址
        return "https://s2.51cto.com//wyfs02/M01/89/BA/wKioL1ga-u7QnnVnAAAfrCiGnBQ946_middle.jpg";
    }

//    //TODO 回放详情2  2087241 75f9747396 75f974739672d279c984b7d2d0c280e2_7 true PolyvPlaybackListType.PLAYBACK
//    public static void set_huifang2(Activity activity, VideoInfoBean bean) {
//        Intent intent = new Intent(get().getPackageName() + ".hs.act.VideoDetailActivity");
//        intent.putExtra(PolyvLiveInfoFragment.ARGUMENT_CLASS_DETAIL2, bean);
//        activity.startActivity(intent);
//    }
//
//    public static void setData(VideoInfoBean bean1) {
//        PolyvCloudClassHomeActivityBeifen.bean = bean1;
//    }

    public String timeStamp2Date(String time) {
        Long timeLong = Long.parseLong(time);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//要转换的时间格式
        Date date;
        try {
            date = sdf.parse(sdf.format(timeLong));
            return sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
