package com.geek.libutils.shortcut.broadcast;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;

public final class IntentSenderHelper {

    public static final IntentSenderHelper INSTANCE;

    public final IntentSender getDefaultIntentSender(Context context, String action) {
        PendingIntent var10000 = PendingIntent.getBroadcast(context, 0, new Intent(action), PendingIntent.FLAG_ONE_SHOT);
        IntentSender var3 = var10000.getIntentSender();
        return var3;
    }

    public final IntentSender getDefaultIntentSender(Context context, String action, Class clz, Bundle bundle) {
        Intent intent = new Intent(action);
        intent.setComponent(new ComponentName(context, clz));
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        PendingIntent var10000 = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        IntentSender var6 = var10000.getIntentSender();
        return var6;
    }

    private IntentSenderHelper() {
    }

    static {
        IntentSenderHelper var0 = new IntentSenderHelper();
        INSTANCE = var0;
    }
}