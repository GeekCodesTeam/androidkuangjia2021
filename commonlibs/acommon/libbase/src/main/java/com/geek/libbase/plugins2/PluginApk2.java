package com.geek.libbase.plugins2;

import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;

import dalvik.system.DexClassLoader;

public class PluginApk2 {

    //插件APK的实体对象
    private DexClassLoader mClassLoader;
    private Resources mResource;
    private AssetManager mAssetManager;
    private PackageInfo mPackageInfo;

    public PluginApk2(DexClassLoader mClassLoader, Resources mResource, PackageInfo mPackageInfo, AssetManager mAssetManager){
        this.mClassLoader = mClassLoader;
        this.mResource = mResource;
        this.mPackageInfo = mPackageInfo;
        this.mAssetManager = mAssetManager;
    }

    public DexClassLoader getClassLoader() {
        return mClassLoader;
    }

    public void setClassLoader(DexClassLoader mClassLoader) {
        this.mClassLoader = mClassLoader;
    }

    public Resources getResource() {
        return mResource;
    }

    public void setResource(Resources mResource) {
        this.mResource = mResource;
    }

    public PackageInfo getPackageInfo() {
        return mPackageInfo;
    }

    public void setPackageInfo(PackageInfo mPackageInfo) {
        this.mPackageInfo = mPackageInfo;
    }

    public AssetManager getAssetManager() {
        return mAssetManager;
    }

    public void setAssetManager(AssetManager mAssetManager) {
        this.mAssetManager = mAssetManager;
    }
}
