package com.example.libbase.plugins;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;

import java.io.Serializable;


/**
 * author: liumengqiang
 * Date : 2019/10/18
 * Description : 调用插件Activity中的具体方法
 */
public class StubActivityImpl implements IPluginActivity {

    private IPluginActivity iPluginActivity;

    private Resources resources;

    private ClassLoader classLoader;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public StubActivityImpl() {
//        handleIntent(intent, activity);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void handleIntent(Intent intent, Activity activity) {
        String reallyActivity = intent.getExtras().getString(PluginConst.REALLY_ACTIVITY_NAME);
        String dexPath = intent.getExtras().getString(PluginConst.DEX_PATH);
        String query1 = intent.getExtras().getString(PluginConst.query1);
        initResources(dexPath);
        ActivityStackManager.getInstance().addActivity(activity, intent.getExtras());
        try {
            Class<?> aClass = PluginManager.getInstance().getPluginItem(dexPath).getClassLoader().loadClass(reallyActivity);
            Object o = aClass.newInstance();
            if(o instanceof IPluginActivity) {
                iPluginActivity = (IPluginActivity) o;
                Bundle bundle = new Bundle();
                //设置是插件跳转
                bundle.putString(PluginConst.query1,query1);
                bundle.putBoolean(PluginConst.isPlugin, true);
                iPluginActivity.attach(activity);
                iPluginActivity.onCreate(bundle);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void initResources(String dexPath) {
        resources = PluginManager.getInstance().getPluginItem(dexPath).getResources();
        classLoader = PluginManager.getInstance().getPluginItem(dexPath).getClassLoader();
    }


    @Override
    public void attach(Activity activity) {

    }

    @Override
    public void onCreate(Bundle saveInstanceState) {

    }

    @Override
    public void onStart() {
        if(iPluginActivity != null) {
            iPluginActivity.onStart();
        }
    }

    @Override
    public void onResume() {
        if(iPluginActivity != null) {
            iPluginActivity.onResume();
        }
    }

    @Override
    public void onRestart() {
        if(iPluginActivity != null) {
            iPluginActivity.onRestart();
        }
    }

    @Override
    public void onPause() {
        if(iPluginActivity != null) {
            iPluginActivity.onPause();
        }
    }

    @Override
    public void onStop() {
        if(iPluginActivity != null) {
            iPluginActivity.onStop();
        }
    }

    @Override
    public void onDestroy() {
        if(iPluginActivity != null) {
            iPluginActivity.onDestroy();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    public Resources getResources() {
        return resources;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }
}
