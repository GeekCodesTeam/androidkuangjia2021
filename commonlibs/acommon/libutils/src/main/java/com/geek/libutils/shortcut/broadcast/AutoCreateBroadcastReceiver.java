package com.geek.libutils.shortcut.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.text.TextUtils;

import com.geek.libutils.app.MyLogUtil;
import com.geek.libutils.shortcut.core.Shortcut;
import com.geek.libutils.shortcut.core.ShortcutCore;

import java.lang.reflect.Field;

public final class AutoCreateBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        MyLogUtil.e("Shortcut", "onReceive: " + action);
        if (TextUtils.equals("com.shortcut.core.auto_create", action)) {
            String id = intent.getStringExtra("extra_id");
            String label = intent.getStringExtra("extra_label");
            MyLogUtil.e("Shortcut", "Shortcut auto create callback, " + "id = " + id + ", label = " + label);
            if (id != null && label != null) {
                ShortcutInfo fetchExitShortcut = (new ShortcutCore()).fetchExitShortcut(context, id);
                if (fetchExitShortcut != null) {
                    boolean var8 = false;
                    boolean var9 = false;
                    ShortcutInfo it = fetchExitShortcut;
                    boolean var11 = false;

                    try {
                        Field declaredField = it.getClass().getDeclaredField("mTitle");
                        declaredField.setAccessible(true);
                        declaredField.set(it, label);
                        boolean updatePinShortcut = (new ShortcutCore()).updatePinShortcut(context, it);
                        if (updatePinShortcut) {
                            Shortcut.getSingleInstance().notifyCreate(id, label);
                        }
                    } catch (Exception var14) {
                        MyLogUtil.e("Shortcut", "receive error, " + var14);
                    }
                }
            }
        }
    }
}