package com.example.slbjiaozvideonew.utils;

import android.content.Context;

import com.danikula.videocache.HttpProxyCacheServer;
import com.geek.libutils.app.BaseApp;

/**
 * @author HRR
 * @date 2019/12/19
 */
public class DipAndPx {
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    private static HttpProxyCacheServer proxy;

    public static HttpProxyCacheServer getProxy() {
        return proxy == null ? (proxy = newProxy()) : proxy;
    }

    private static HttpProxyCacheServer newProxy() {
        return new HttpProxyCacheServer(BaseApp.get());
    }
}
