package com.example.libbase.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

public class IconUtils {

    public static void set_alias(Activity activity, String[] strings, String isyou, boolean isLaunch) {
        String brand = Build.BRAND;//手机厂商
        /*if (TextUtils.equals(brand.toLowerCase(), "redmi") || TextUtils.equals(brand.toLowerCase(), "xiaomi")) {

        } else if (TextUtils.equals(brand.toLowerCase(), "meizu")) {

        } else*/
        if (TextUtils.equals(brand.toLowerCase(), "huawei") || TextUtils.equals(brand.toLowerCase(), "honor")) {
            setPrefDbValue(activity.getApplicationContext(), "system", "hw_launcher_hide_apps", isLaunch + "");
        } else {
            PackageManager packageManager = activity.getPackageManager();
            for (int i = 0; i < strings.length; i++) {
                packageManager.setComponentEnabledSetting(new ComponentName(activity, /*getPackageName() +*/
                                strings[i]), PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                        PackageManager.DONT_KILL_APP);
            }
            if (isLaunch) {
                packageManager.setComponentEnabledSetting(new ComponentName(activity, /*getPackageName() +*/
                                isyou), PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                        PackageManager.DONT_KILL_APP);
            }
        }
    }

    public static boolean setPrefDbValue(Context var0, String var1, String var2, String var3) {
        try {
            ContentValues var5 = new ContentValues(2);
            var5.put("name", var2);
            var5.put("value", var3);
            var0.getContentResolver().bulkInsert(Uri.parse("content://settings/system"), new ContentValues[]{var5});
            return true;
        } catch (Exception var4) {
            Log.e("setSystemDbValue", var4.getMessage());
            return false;
        }
    }

    public static void getDbValue(Context var0) {
        try {
            Cursor cursor = var0.getContentResolver().query(Uri.parse("content://settings/system"), null, null, null, null);
            cursor.getCount();
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex("_id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String value = cursor.getString(cursor.getColumnIndex("value"));
                System.out.println("id=" + id + "-----name=" + name + "-----value=" + value);
            }
            cursor.close();
        } catch (Exception var4) {
            Log.e("setSystemDbValue", var4.getMessage());
        }
    }
}
