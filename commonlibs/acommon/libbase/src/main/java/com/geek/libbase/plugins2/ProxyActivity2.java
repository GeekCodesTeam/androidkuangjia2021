package com.geek.libbase.plugins2;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//代理Activity，管理插件Activity的生命周期
public class ProxyActivity2 extends AppCompatActivity {

    private static final String TAG = "ProxyActivity";

    private String mClassName;
    private PluginApk2 mPluginApk;
    private IPlugin2 mIPlugin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClassName = getIntent().getStringExtra("className_ProxyActivity");
        mPluginApk = PluginManager2.getInstance(this).getPluginApk();
        launchPluginActivity();
    }

    private void launchPluginActivity() {
        if(mPluginApk == null){
            return;
        }
        try {
            Class<?> clazz = mPluginApk.getClassLoader().loadClass(mClassName);
            Object objetc = clazz.newInstance();
            if(objetc instanceof IPlugin2){
                mIPlugin = (IPlugin2) objetc;
                mIPlugin.attach(this);
                Bundle bundle = new Bundle();
                bundle.putInt("FROM_ProxyActivity", IPlugin2.FORM_EXTERNAL);
                mIPlugin.onCreate(bundle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Resources getResources() {
        return mPluginApk != null ? mPluginApk.getResource() : super.getResources();
    }

    @Override
    public AssetManager getAssets() {
        return mPluginApk != null ? mPluginApk.getAssetManager() : super.getAssets();
    }

    @Override
    public ClassLoader getClassLoader() {
        return mPluginApk != null ? mPluginApk.getClassLoader(): super.getClassLoader();
    }
}
