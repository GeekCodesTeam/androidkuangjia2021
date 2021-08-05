package com.geek.libbase.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.lang.reflect.Method;

public class LoadUtil {
    public static String PLUGIN_APK_PATH = "";
//    public static final String PLUGIN_APK_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "hmphone.apk";
//    public static final String PLUGIN_APK_PATH =  FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "Cellarette" + ".apk";

    private static Resources mResources;

    public static Resources getResource(Context context, String savepath) {
        PLUGIN_APK_PATH = savepath;
        if (mResources == null) {
            mResources = loadResource(context);
        }
        return mResources;
    }

    private static Resources loadResource(Context context) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPathMethod = assetManager.getClass().getDeclaredMethod("addAssetPath", String.class);
            addAssetPathMethod.setAccessible(true);
            // 参数就是插件的资源路径
            addAssetPathMethod.invoke(assetManager, PLUGIN_APK_PATH);
            Resources resources = context.getResources();
            return new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}