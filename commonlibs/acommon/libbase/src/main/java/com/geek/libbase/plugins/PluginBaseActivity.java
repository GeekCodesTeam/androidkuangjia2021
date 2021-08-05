package com.geek.libbase.plugins;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.geek.libbase.base.SlbBaseActivity;

/**
 * FileName：PluginBaseActivity
 * Create By：liumengqiang
 * Description：所有的插件Activity都要继承该类
 */
public abstract class PluginBaseActivity extends SlbBaseActivity implements IPluginActivity {

    protected Activity proxy;

    public boolean isPlugin = false; // 是否是插件运行

    public boolean isPlugin() {
        return isPlugin;
    }

    public void setPlugin(boolean plugin) {
        isPlugin = plugin;
    }

    private int launchModel = -1;

    public String paths = "";

    public String getPaths() {
        return paths;
    }

    public void setPaths(String paths) {
        this.paths = paths;
    }

    @Override
    public void attach(Activity activity) {
        proxy = activity;
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        if (saveInstanceState != null) {
            isPlugin = saveInstanceState.getBoolean(PluginConst.isPlugin, false);
        }
        if (!isPlugin) {
            super.onCreate(saveInstanceState);
            proxy = this;
        } else {

        }
    }

    @Override
    protected void onActResult(int requestCode, int resultCode, Intent data) {
        super.onActResult(requestCode, resultCode, data);

    }

    @Override
    public void onStart() {
        if (!isPlugin) {
            super.onStart();
        } else {

        }
    }

    @Override
    public void onResume() {
        if (!isPlugin) {
            super.onResume();
        } else {

        }
    }

    @Override
    public void onRestart() {
        if (!isPlugin) {
            super.onRestart();
        } else {

        }
    }

    @Override
    public void onPause() {
        if (!isPlugin) {
            super.onPause();
        } else {

        }
    }

    @Override
    public void onStop() {
        if (!isPlugin) {
            super.onStop();
        } else {

        }
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    public void onDestroy() {
        if (!isPlugin) {
            super.onDestroy();
        } else {

        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//    }

    //下面是几个生命周期之外的重写函数
    @Override
    public void setContentView(int layoutResID) {//设置contentView分情况
        if (!isPlugin) {
            super.setContentView(layoutResID);
        } else {
            proxy.setContentView(layoutResID);
        }
    }

    @Override
    public View findViewById(int id) {
        if (!isPlugin) {
            return super.findViewById(id);
        } else {
            return proxy.findViewById(id);
        }
    }

    /**
     * 同一个插件之间跳转
     *
     * @param intent
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void startActivity(Intent intent) {
        if (!isPlugin) {
            super.startActivity(intent);
        } else {
//            Bundle bundle = setBundleData(intent.getExtras(), PluginConst.Plugin_1_ApkDex, intent.getComponent().getClassName(), PluginConst.LaunchModel.SINGLE_TASK);
            Bundle bundle = setBundleData(intent.getExtras(), getPaths(), intent.getComponent().getClassName(), PluginConst.LaunchModel.SINGLE_TASK);
            PluginManager.getInstance().startActivity(proxy, bundle);
        }
    }

    /**
     * 一个插件Activity跳转到另一个插件Activity
     *
     * @param bundleParam
     * @param dexPath
     * @param reallyActivityName
     * @param launchModel
     */
    public void startOtherPluginActivity(Bundle bundleParam, String dexPath, String reallyActivityName, int launchModel) {
        if (!isPlugin) {
            Toast.makeText(proxy, "无法找到：" + reallyActivityName, Toast.LENGTH_SHORT).show();
            return;
        }
        Bundle bundle = setBundleData(bundleParam, dexPath, reallyActivityName, launchModel);
        PluginManager.getInstance().startActivity(proxy, bundle);
    }

    /**
     * @param bundleParam
     * @param dexPath
     * @param reallyActivityName
     * @param launchModel
     * @return
     */
    public Bundle setBundleData(Bundle bundleParam, String dexPath, String reallyActivityName, int launchModel) {
        Bundle bundle = bundleParam == null ? new Bundle() : bundleParam;
        bundle.putString(PluginConst.DEX_PATH, dexPath);
        bundle.putString(PluginConst.REALLY_ACTIVITY_NAME, reallyActivityName);
        bundle.putInt(PluginConst.LAUNCH_MODEL, launchModel);
        return bundle;
    }
}
