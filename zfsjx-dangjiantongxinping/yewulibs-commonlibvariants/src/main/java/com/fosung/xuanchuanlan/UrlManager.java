package com.fosung.xuanchuanlan;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UrlManager {
    //测试
//    public static String TEST_SERVER_ISERVICE_NEW1 = SPUtils.getInstance().getString("版本地址1", "https://www.baidu.com");
//    public static String TEST_SERVER_ISERVICE_NEW2 = SPUtils.getInstance().getString("版本地址2", "https://www.baidu.com");
    public static final String CCC = "测试";
    public static final boolean TEST_IS_RELEASE_URL = false;
    public static String TEST_SERVER_ISERVICE_NEW1 = "http://10.254.23.64";
    public static String TEST_SERVER_ISERVICE_NEW2 = "http://49.4.7.45:7799";
    //预生产
//    public static String PREPROD_SERVER_ISERVICE_NEW1 = SPUtils.getInstance().getString("版本地址1", "https://www.baidu.com");
//    public static String PREPROD_SERVER_ISERVICE_NEW2 = SPUtils.getInstance().getString("版本地址2", "https://www.baidu.com");
    public static final String YYY = "预生产";
    public static final boolean PREPROD_IS_RELEASE_URL = true;
    public static String PREPROD_SERVER_ISERVICE_NEW1 = "https://gbwlxy.dtdjzx.gov.cn";
    public static String PREPROD_SERVER_ISERVICE_NEW2 = "http://49.4.1.81:7799";
    //线上
//    public static String OL_SERVER_ISERVICE_NEW1 = SPUtils.getInstance().getString("版本地址1", "https://www.baidu.com");
//    public static String OL_SERVER_ISERVICE_NEW2 = SPUtils.getInstance().getString("版本地址2", "https://www.baidu.com");
    public static final String OOO = "线上";
    public static final boolean OL_IS_RELEASE_URL = true;
    public static String OL_SERVER_ISERVICE_NEW1 = "https://dyjy.dtdjzx.gov.cn/";
    public static String OL_SERVER_ISERVICE_NEW2 = "http://doc.znclass.com";

    public static final int VERSION_CODE3 = getAppVersionCode();
    public static final String VERSION_NAME3 = getAppVersionName(1);

    public static String versionCount() {
        return "01";
    }

    public String getTinkerVersionCode() {
        //每次发新版本/热更新这个值都要增加
        return "1";
    }

    public static String getAppVersionPre() {
        return "v4.1s_";
    }

    // 获取 version name
    public static String getAppVersionName(int type) {
        String ver = getAppVersionPre();
        String today = new SimpleDateFormat("MMdd").format(new Date());
        return ver + today + '_' + versionCount() + '_' + type;
    }

    public static int getAppVersionCode() {
        String today = new SimpleDateFormat("yyyyMMdd").format(new Date()) + versionCount();
        return Integer.parseInt(today);
    }
}
