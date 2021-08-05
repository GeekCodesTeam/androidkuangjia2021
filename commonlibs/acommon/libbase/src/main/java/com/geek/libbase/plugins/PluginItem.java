package com.geek.libbase.plugins;

import android.content.res.Resources;

/**
 * FileName：PluginItem
 * Create By：liumengqiang
 * Description：用于保存已加载的插件的相关信息
 */
public class PluginItem {
    private String dexPath; // 插件的DexPath

    private ClassLoader classLoader; // 加载插件的ClassLoader

    private Resources resources; // 插件中的Resources

    public PluginItem(String dexPath, ClassLoader classLoader, Resources resources) {
        this.dexPath = dexPath;
        this.classLoader = classLoader;
        this.resources = resources;
    }

    public String getDexPath() {
        return dexPath;
    }

    public void setDexPath(String dexPath) {
        this.dexPath = dexPath;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public Resources getResources() {
        return resources;
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }
}
