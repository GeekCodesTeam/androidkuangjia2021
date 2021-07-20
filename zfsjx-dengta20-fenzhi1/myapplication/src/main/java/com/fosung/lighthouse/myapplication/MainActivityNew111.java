package com.fosung.lighthouse.myapplication;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.libbase.plugins.LoadUtils;
import com.example.libbase.plugins.PluginConst;
import com.example.libbase.plugins.PluginManager;


public class MainActivityNew111 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main22222);
        // old
//        findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String apkPath = null;
//                try {
//                    apkPath = LoadUtils.copyAssetAndWrite(MainActivityNew111.this, "neplugin-bxn_nation-release.apk");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                PluginManager.getInstance(MainActivityNew111.this).loadApk(apkPath);
//                Intent intent = new Intent(MainActivityNew111.this, ProxyActivity.class);
//                intent.putExtra("className_ProxyActivity", "com.example.neplugin.MyPluginActivity");
//                startActivity(intent);
//            }
//        });
        String path = LoadUtils.setpath1(MainActivityNew111.this,"neplugin-bxn_nation-release.apk");
        PluginManager.getInstance().init(MainActivityNew111.this);
        PluginManager.getInstance().loadPluginApk(path);
        // new
        findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
//        bundle.putString(PluginConst.DEX_PATH, PluginConst.Plugin_1_ApkDex);
                bundle.putString(PluginConst.DEX_PATH, path);
                bundle.putString(PluginConst.REALLY_ACTIVITY_NAME, "com.example.neplugin.MyPluginActivity");
                bundle.putInt(PluginConst.LAUNCH_MODEL, PluginConst.LaunchModel.SINGLE_TASK);
                PluginManager.getInstance().startActivity(MainActivityNew111.this, bundle);

            }
        });
    }
}