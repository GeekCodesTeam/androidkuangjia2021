package com.example.libbase.baserecycleview;

import android.content.Context;

public class SlbBaseViewPageBroadcastUtils {
    private static volatile SlbBaseViewPageBroadcastUtils instance;
    private Context mContext;

    private SlbBaseViewPageBroadcastUtils(Context context) {
        this.mContext = context;
    }

    public static SlbBaseViewPageBroadcastUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (SlbBaseViewPageBroadcastUtils.class) {
                instance = new SlbBaseViewPageBroadcastUtils(context);
            }
        }
        return instance;
    }


}
