package com.fosung.lighthouse.app;

import com.blankj.utilcode.util.SPUtils;

public class UrlManager {
    public static String default_url1key = "版本地址1";
    public static String default_url2key = "版本地址2";
    public static String default_url3key = "版本地址3";
    //测试
    public static String TEST_SERVER_ISERVICE_NEW1 = SPUtils.getInstance().getString(default_url1key, "https://test1");
    public static String TEST_SERVER_ISERVICE_NEW2 = SPUtils.getInstance().getString(default_url2key, "https://test2");
    public static String TEST_SERVER_ISERVICE_NEW3 = SPUtils.getInstance().getString(default_url3key, "https://test3");
    public static final String CCC = "测试";
    //预生产
    public static String PREPROD_SERVER_ISERVICE_NEW1 = SPUtils.getInstance().getString(default_url1key, "https://yushengchan1");
    public static String PREPROD_SERVER_ISERVICE_NEW2 = SPUtils.getInstance().getString(default_url2key, "https://yushengchan2");
    public static String PREPROD_SERVER_ISERVICE_NEW3 = SPUtils.getInstance().getString(default_url3key, "https://yushengchan3");
    public static final String YYY = "预生产";
    //线上
    public static String OL_SERVER_ISERVICE_NEW1 = SPUtils.getInstance().getString(default_url1key, "http://10.254.23.23:8922");
    public static String OL_SERVER_ISERVICE_NEW2 = SPUtils.getInstance().getString(default_url2key, "http://10.243.32.109:7020/__api");
    public static String OL_SERVER_ISERVICE_NEW3 = SPUtils.getInstance().getString(default_url3key, "https://app.dtdjzx.gov.cn");
    public static final String OOO = "线上";

}
