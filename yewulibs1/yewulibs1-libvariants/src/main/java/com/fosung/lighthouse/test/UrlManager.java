package com.fosung.lighthouse.test;

import com.blankj.utilcode.util.SPUtils;

public class UrlManager {
    public static String default_url1key = "版本地址1";
    public static String default_url2key = "版本地址2";
    //测试
    public static String TEST_SERVER_ISERVICE_NEW1 = SPUtils.getInstance().getString(default_url1key, "https://test1");
    public static String TEST_SERVER_ISERVICE_NEW2 = SPUtils.getInstance().getString(default_url2key, "https://test2");
    public static final String CCC = "测试";
//    public static String TEST_SERVER_ISERVICE_NEW1 = "http://dtdjzx.fosung.com";
//    public static String TEST_SERVER_ISERVICE_NEW2 = "http://49.4.7.45:7799";
    //预生产
    public static String PREPROD_SERVER_ISERVICE_NEW1 = SPUtils.getInstance().getString(default_url1key, "https://yushengchan1");
    public static String PREPROD_SERVER_ISERVICE_NEW2 = SPUtils.getInstance().getString(default_url2key, "https://yushengchan2");
    public static final String YYY = "预生产";
//    public static String PREPROD_SERVER_ISERVICE_NEW1 = "http://dtdjzx.fosung.com";
//    public static String PREPROD_SERVER_ISERVICE_NEW2 = "http://49.4.1.81:7799";
    //线上
    public static String OL_SERVER_ISERVICE_NEW1 = SPUtils.getInstance().getString(default_url1key, "http://10.254.23.64/apiLive");
    public static String OL_SERVER_ISERVICE_NEW2 = SPUtils.getInstance().getString(default_url2key, "https://xianshang2");
    public static final String OOO = "线上";
//    public static String OL_SERVER_ISERVICE_NEW1 = "https://app.dtdjzx.gov.cn";
//    public static String OL_SERVER_ISERVICE_NEW2 = "http://doc.znclass.com";

}
