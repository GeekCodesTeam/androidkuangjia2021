package com.tencent.qcloud.tim.demo.helper;

import com.tencent.qcloud.tim.demo.utils.TencentApp;

import com.tencent.qcloud.tim.demo.R;
import com.tencent.qcloud.tim.uikit.utils.TUIKitConstants;

/**
 * 自定义消息的bean实体，用来与json的相互转化
 */
public class CustomHelloMessage {
    String businessID = TUIKitConstants.BUSINESS_ID_CUSTOM_HELLO;
    String text = TencentApp.get().getString(R.string.welcome_tip);
    String link = "https://cloud.tencent.com/document/product/269/3794";

    int version = TUIKitConstants.JSON_VERSION_UNKNOWN;
}
