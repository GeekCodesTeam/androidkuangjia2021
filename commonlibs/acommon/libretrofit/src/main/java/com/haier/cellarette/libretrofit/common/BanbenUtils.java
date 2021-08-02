package com.haier.cellarette.libretrofit.common;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.geek.libutils.data.MmkvUtils;

public class BanbenUtils {

    private static volatile BanbenUtils instance;

    private BanbenUtils() {
    }

    public static BanbenUtils getInstance() {
        if (instance == null) {
            synchronized (BanbenUtils.class) {
                instance = new BanbenUtils();
            }
        }
        return instance;
    }

    //    public static final String banben = NetConfig.versionNameConfig;
    public String net_tips = "网络异常，请检查网络连接！";
    public String error_tips = "请求失败，请稍后重试！";
    public String version = "";
    public String imei = "";
    public String token = "";
    public String platform = "android_phone";
    public String liveClientType = "android";

    public String getVersion() {
        return AppUtils.getAppVersionName(AppUtils.getAppPackageName());
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getImei() {
        return DeviceUtils.getAndroidID();
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getToken() {
        return MmkvUtils.getInstance().get_common("用户token");
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLiveClientType() {
        return liveClientType;
    }

    public void setLiveClientType(String liveClientType) {
        this.liveClientType = liveClientType;
    }
}
