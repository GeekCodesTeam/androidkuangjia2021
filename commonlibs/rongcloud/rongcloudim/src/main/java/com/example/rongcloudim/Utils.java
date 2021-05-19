package com.example.rongcloudim;

import android.content.Context;

public class Utils {
    private static Context mContext = null;

    //TODO 请替换成您自己申请的AppKey
    public static String appKey = "p5tvi9dspl9d4";

    //TODO 请填写您生成的一个token
    public static final String USER_TOKEN_1 = "1ZbnjyNs94JfOLII+cG9ffrm8tMlz7OCfYrjvsbk5Qg=@s9tq.cn.rongnav.com;s9tq.cn.rongcfg.com";
    //USER_TOKEN_1 对应的userId
    public static final String USER_ID_1 = "1000";

    //TODO 请填写您生成的不同于 token1 的一个token;
    public static final String USER_TOKEN_2 = "/qnXX953nNBfOLII+cG9fakdb0Tt+inqnarN2I8eHhw=@s9tq.cn.rongnav.com;s9tq.cn.rongcfg.com";
    public static final String USER_ID_2 = "1001";

    public static final String USER_TOKEN_3 = "";
    public static final String USER_TOKEN_4 = "";

    public static void init(Context context) {
        Utils.mContext = context.getApplicationContext();
    }

    public static Context getContext() {
        if (null != mContext) {
            return mContext;
        }
        throw new NullPointerException("u should context init first");
    }
}