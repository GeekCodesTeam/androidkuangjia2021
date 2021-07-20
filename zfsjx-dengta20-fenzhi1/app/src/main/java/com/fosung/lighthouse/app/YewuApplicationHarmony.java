/**
 * Copyright 2016,Smart Haier.All rights reserved
 */
package com.fosung.lighthouse.app;

import android.os.Handler;
import android.os.Looper;

import com.example.libbase.HarmonyApplication2;
import com.geek.libutils.app.AppUtils;
import com.pgyer.pgyersdk.PgyerSDKManager;
import com.zcolin.frame.BanbenCommonUtils;

/**
 * <p class="note">File Note</p>
 * created by geek at 2017/6/6
 */
public class YewuApplicationHarmony extends HarmonyApplication2 {

    @Override
    public void onCreate() {
        super.onCreate();
        if (!AppUtils.isProcessAs(getPackageName(), this)) {
            return;
        }
        //TODO commonbufen
        configBugly(BanbenCommonUtils.banben_comm, "3aeeb18e5e");
        configHios();
        configmmkv();
        configShipei();
        configRetrofitNet();
        //TODO 业务bufen
        // 升级
        new PgyerSDKManager.Init()
                .setContext(this) //设置上下问对象
                .start();
        others();

    }

    @Override
    public void onHomePressed() {
        super.onHomePressed();
//        AddressSaver.addr = null;
    }
}
