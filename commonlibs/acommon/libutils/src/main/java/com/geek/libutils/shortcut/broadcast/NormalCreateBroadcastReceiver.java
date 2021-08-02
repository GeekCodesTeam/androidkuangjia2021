package com.geek.libutils.shortcut.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.geek.libutils.app.MyLogUtil;
import com.geek.libutils.shortcut.core.Shortcut;

public final class NormalCreateBroadcastReceiver extends BroadcastReceiver {

    public static final String ACTION = "com.shortcut.core.normal_create";

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        MyLogUtil.e("Shortcut", "onReceive: " + action);
        if (TextUtils.equals("com.shortcut.core.normal_create", action)) {
            String id = intent.getStringExtra("extra_id");
            String label = intent.getStringExtra("extra_label");
            MyLogUtil.e("Shortcut", "Shortcut normal create callback," + " id = " + id + ", label = " + label);
            if (id != null && label != null) {
                Shortcut.getSingleInstance().notifyCreate(id, label);
            }
        }

    }
}