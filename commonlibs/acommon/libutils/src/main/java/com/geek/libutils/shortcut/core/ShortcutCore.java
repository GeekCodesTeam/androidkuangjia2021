package com.geek.libutils.shortcut.core;

import android.content.Context;
import android.content.IntentSender;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.content.pm.ShortcutManagerCompat;

import com.geek.libutils.shortcut.broadcast.IntentSenderHelper;
import com.geek.libutils.shortcut.broadcast.NormalCreateBroadcastReceiver;

import java.util.Iterator;
import java.util.List;

import kotlin.collections.CollectionsKt;


public class ShortcutCore {
   public boolean isShortcutExit( Context context,  String id,  CharSequence label) {
      if (Build.VERSION.SDK_INT >= 25) {
         ShortcutManager var10000 = (ShortcutManager)context.getSystemService(ShortcutManager.class);
         if (var10000 == null) {
            return false;
         }

         ShortcutManager mShortcutManager = var10000;
         List var8 = mShortcutManager.getPinnedShortcuts();
         List pinnedShortcuts = var8;
         Iterator var7 = pinnedShortcuts.iterator();

         while(var7.hasNext()) {
            ShortcutInfo pinnedShortcut = (ShortcutInfo)var7.next();
            if (TextUtils.equals(pinnedShortcut.getId(), id)) {
               return true;
            }
         }
      }

      return false;
   }

   public final ShortcutInfo fetchExitShortcut( Context context,  String id) {
      if (Build.VERSION.SDK_INT >= 25) {
         ShortcutManager var10000 = (ShortcutManager)context.getSystemService(ShortcutManager.class);
         if (var10000 == null) {
            return null;
         }

         ShortcutManager mShortcutManager = var10000;
         List var7 = mShortcutManager.getPinnedShortcuts();
         List pinnedShortcuts = var7;
         Iterator var6 = pinnedShortcuts.iterator();

         while(var6.hasNext()) {
            ShortcutInfo pinnedShortcut = (ShortcutInfo)var6.next();
            if (TextUtils.equals(pinnedShortcut.getId(), id)) {
               return pinnedShortcut;
            }
         }
      }

      return null;
   }

   public final boolean updatePinShortcut( Context context,  ShortcutInfo info) {
      if (Build.VERSION.SDK_INT >= 25) {
         ShortcutManager var10000 = (ShortcutManager)context.getSystemService(ShortcutManager.class);
         if (var10000 != null) {
            ShortcutManager mShortcutManager = var10000;
            return mShortcutManager.updateShortcuts(CollectionsKt.listOf(info));
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public final boolean updatePinShortcut( Context context,  ShortcutInfoCompat info) {
      boolean var10000;
      if (Build.VERSION.SDK_INT >= 25) {
         ShortcutInfo var10002 = info.toShortcutInfo();
         var10000 = this.updatePinShortcut(context, var10002);
      } else {
         var10000 = false;
      }

      return var10000;
   }

   public void createShortcut( Context context,  ShortcutInfoCompat shortcutInfoCompat, boolean updateIfExit,  ShortcutAction shortcutAction, int check) {
      String var10002 = shortcutInfoCompat.getId();
      CharSequence var10003 = shortcutInfoCompat.getShortLabel();
      boolean shortcutExit = this.isShortcutExit(context, var10002, var10003);
      if (shortcutExit && updateIfExit) {
         boolean updatePinShortcut = this.updatePinShortcut(context, shortcutInfoCompat);
         shortcutAction.onUpdateAction(updatePinShortcut);
      } else {
         Bundle bundle = new Bundle();
         bundle.putString("extra_id", shortcutInfoCompat.getId());
         bundle.putString("extra_label", shortcutInfoCompat.getShortLabel().toString());
         IntentSender defaultIntentSender = IntentSenderHelper.INSTANCE.getDefaultIntentSender(context, "com.shortcut.core.normal_create", NormalCreateBroadcastReceiver.class, bundle);
         boolean requestPinShortcut = ShortcutManagerCompat.requestPinShortcut(context, shortcutInfoCompat, defaultIntentSender);
         shortcutAction.onCreateAction(requestPinShortcut, check, (Executor)(new DefaultExecutor(context)));
      }

   }
}