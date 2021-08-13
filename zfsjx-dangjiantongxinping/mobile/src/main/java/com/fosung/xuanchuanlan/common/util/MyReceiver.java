package com.fosung.xuanchuanlan.common.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.fosung.xuanchuanlan.mian.activity.InitActivity;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Intent i = new Intent(context, InitActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}