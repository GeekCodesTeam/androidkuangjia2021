package com.github.geekcodesteam.app;

import com.blankj.utilcode.util.SPUtils;

public class UrlManager {
    public static String default_url1key = "版本地址1";  //
    //测试
    public static String TEST_SERVER_ISERVICE_NEW1 = SPUtils.getInstance().getString(default_url1key, "https://test1");
    public static final String CCC = "测试";
    //预生产
    public static String PREPROD_SERVER_ISERVICE_NEW1 = SPUtils.getInstance().getString(default_url1key, "https://yushengchan1");
    public static final String YYY = "预生产";
    //线上
    public static String OL_SERVER_ISERVICE_NEW1 = SPUtils.getInstance().getString(default_url1key, "https://xianshang1");
    public static final String OOO = "线上";

}
