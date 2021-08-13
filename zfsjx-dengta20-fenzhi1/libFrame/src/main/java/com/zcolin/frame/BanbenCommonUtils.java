package com.zcolin.frame;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.SPUtils;
import com.fosung.lighthouse.app.BuildConfigyewu;
import com.fosung.lighthouse.app.UrlManager;

public class BanbenCommonUtils {
    public static String banben_comm = BuildConfigyewu.versionNameConfig;
    public static String dizhi1_comm = BuildConfigyewu.SERVER_ISERVICE_NEW1;
    public static String dizhi2_comm = BuildConfigyewu.SERVER_ISERVICE_NEW2;
    public static StartHiddenManager startHiddenManager;

    //
    public static void changeUrl(final Activity activity, View left, View right, String intent) {
        String aaa = "https";
        if (TextUtils.equals(BanbenCommonUtils.banben_comm, "测试")) {
            aaa = "https://test1";
        } else if (TextUtils.equals(BanbenCommonUtils.banben_comm, "预生产")) {
            aaa = "https://yushengchan1";
        } else if (TextUtils.equals(BanbenCommonUtils.banben_comm, "线上")) {
            aaa = "https://xianshang1";
        }
        final String finalAaa = aaa;
        startHiddenManager = new StartHiddenManager(left, right, intent, new StartHiddenManager.OnClickFinish() {
            @Override
            public void onFinish() {
                if ("text".contains("http")) {
                    String[] content = "text".split(",");
                    SPUtils.getInstance().put(UrlManager.default_url1key, content[0]);
                    SPUtils.getInstance().put(UrlManager.default_url2key, content[1]);
                    BuildConfigyewu.SERVER_ISERVICE_NEW1 = content[0];
                    BuildConfigyewu.SERVER_ISERVICE_NEW2 = content[1];
                    BanbenCommonUtils.dizhi1_comm = content[0];
                    BanbenCommonUtils.dizhi2_comm = content[1];
                }
            }
        });
    }

    public static void changeUrl_ondes() {
        startHiddenManager.onDestory();
    }

}