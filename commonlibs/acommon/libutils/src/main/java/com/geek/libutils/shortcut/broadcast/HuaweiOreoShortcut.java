package com.geek.libutils.shortcut.broadcast;

import android.content.Context;
import android.content.IntentSender;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.content.pm.ShortcutManagerCompat;

import com.geek.libutils.app.MyLogUtil;
import com.geek.libutils.shortcut.core.DefaultExecutor;
import com.geek.libutils.shortcut.core.Executor;
import com.geek.libutils.shortcut.core.ShortcutAction;
import com.geek.libutils.shortcut.core.ShortcutCore;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public final class HuaweiOreoShortcut extends ShortcutCore {

    public boolean isShortcutExit(Context context, String id, CharSequence label) {
        if (Build.VERSION.SDK_INT >= 25) {
            ShortcutManager var10000 = (ShortcutManager) context.getSystemService(ShortcutManager.class);
            if (var10000 == null) {
                return false;
            }

            ShortcutManager mShortcutManager = var10000;
            boolean withSameName = false;
            List var9 = mShortcutManager.getPinnedShortcuts();
            List pinnedShortcuts = var9;
            Iterator var8 = pinnedShortcuts.iterator();

            while (var8.hasNext()) {
                ShortcutInfo pinnedShortcut = (ShortcutInfo) var8.next();
                if (TextUtils.equals(pinnedShortcut.getId(), id)) {
                    return true;
                }

                if (TextUtils.equals(label, pinnedShortcut.getShortLabel())) {
                    withSameName = true;
                }
            }

            if (withSameName && this.needCheckSameName()) {
                try {
                    throw (Throwable) (new RuntimeException("huawei shortcut exit with same label"));
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }

        return false;
    }

    private final boolean needCheckSameName() {
        String var10000 = Build.MANUFACTURER;
        String var1 = var10000;
        Locale var6 = Locale.getDefault();
        Locale var2 = var6;
        boolean var3 = false;
        boolean var5 = false;
        if (var1 == null) {
            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
        } else {
            var10000 = var1.toLowerCase(var2);
            return TextUtils.equals(var10000, "huawei") && Build.VERSION.SDK_INT >= 26 && Build.VERSION.SDK_INT <= 27;
        }
    }

    public void createShortcut(Context context, ShortcutInfoCompat shortcutInfoCompat,
                               boolean updateIfExit, ShortcutAction shortcutAction, int check) {
        try {
            String var10002 = shortcutInfoCompat.getId();
            CharSequence var10003 = shortcutInfoCompat.getShortLabel();
            this.isShortcutExit(context, var10002, var10003);
            super.createShortcut(context, shortcutInfoCompat, updateIfExit, shortcutAction, check);
        } catch (Exception var10) {
            MyLogUtil.e("Shortcut", "huawei create shortcut error, " + var10);
            Bundle bundle = new Bundle();
            bundle.putString("extra_id", shortcutInfoCompat.getId());
            bundle.putString("extra_label", shortcutInfoCompat.getShortLabel().toString());
            IntentSender defaultIntentSender = IntentSenderHelper.INSTANCE.getDefaultIntentSender(context, "com.shortcut.core.auto_create", AutoCreateBroadcastReceiver.class, bundle);

            try {
                Field declaredField = shortcutInfoCompat.getClass().getDeclaredField("mLabel");
                declaredField.setAccessible(true);
                declaredField.set(shortcutInfoCompat, shortcutInfoCompat.getShortLabel().toString() + ".");
            } catch (Exception var9) {
                MyLogUtil.e("Shortcut", "huawei create shortcut error, " + var9);
            }

            boolean requestPinShortcut = ShortcutManagerCompat.requestPinShortcut(context, shortcutInfoCompat, defaultIntentSender);
            shortcutAction.onCreateAction(requestPinShortcut, check, (Executor) (new DefaultExecutor(context)));
        }
    }
}