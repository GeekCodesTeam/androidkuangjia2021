package com.example.libbase.plugins2;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class PluginManager2 {

    private static PluginManager2 instance;
    private Context mContext;
    private PluginApk2 mPluginApk;

    public static PluginManager2 getInstance(Context context){
        if(instance == null){
            instance = new PluginManager2(context);
        }
        return instance;
    }

    //加载APK文件
    public void loadApk(String apkPath){
        if(TextUtils.isEmpty(apkPath)){
            return;
        }
        PackageInfo packageInfo = mContext.getPackageManager().getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES);
        if(packageInfo == null){
            return;
        }
        //创建dexClassLoader
        DexClassLoader classLoader = createDexClassLoader(apkPath);
        //创建Resource
        AssetManager am = createAssetManager(apkPath);
        Resources resources = createResource(am);
        mPluginApk = new PluginApk2(classLoader, resources, packageInfo, am);
    }

    private PluginManager2(Context context){
        this.mContext = context;
    }


    public PluginApk2 getPluginApk(){
        return mPluginApk;
    }

    private Resources createResource(AssetManager am) {
        Resources res = mContext.getResources();
        return new Resources(am, res.getDisplayMetrics(), res.getConfiguration());
    }

    private AssetManager createAssetManager(String apkPath) {
        try {
            AssetManager am = AssetManager.class.newInstance();
            Method method = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            method.invoke(am, apkPath);
            return am;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取插件dex的DexClassLoader，用来加载插件dex的任何一个类
    private DexClassLoader createDexClassLoader(String apkPath) {
        File file = mContext.getDir("dex", Context.MODE_PRIVATE);
        return new DexClassLoader(apkPath, file.getAbsolutePath(), null, mContext.getClassLoader());
    }
}
