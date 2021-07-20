package com.fosung.lighthouse.ezhibu;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.example.libbase.plugins.LoadUtils;
import com.example.libbase.plugins.PluginBaseActivity;
import com.example.libbase.plugins.PluginConst;
import com.example.libbase.utils.LoadUtil;

import java.lang.reflect.Field;

public class EzhibuAct extends PluginBaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        savepath = LoadUtils.setpath1(EzhibuAct.this, "libyewu-ezhibu-bxn_nation-release.apk");
//        Resources resource = LoadUtil.getResource(getApplication(), savepath);
//        mContext = new ContextThemeWrapper(getBaseContext(), 0);
//        Class<? extends Context> clazz = mContext.getClass();
//        try {
//            Field mResourcesField = clazz.getDeclaredField("mResources");
//            mResourcesField.setAccessible(true);
//            mResourcesField.set(mContext, resource);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_main_ezhibu1, null);
//        setContentView(view);
        setContentView(R.layout.activity_main_ezhibu1);
        // 方法1接收
        if (savedInstanceState != null) {
            String aaaa = savedInstanceState.getString(PluginConst.query1);
            ToastUtils.showLong("方法111111接收:" + "aaa->" + aaaa);
        }
        // TODO test
        findViewById(R.id.tv1).setOnClickListener(v -> {
            // 方法1
            Intent intent = new Intent(proxy, EzhibuAct2.class);
            intent.putExtra("query1", "pc");
            setPlugin(false);
            startActivity(intent);
        });
    }

}