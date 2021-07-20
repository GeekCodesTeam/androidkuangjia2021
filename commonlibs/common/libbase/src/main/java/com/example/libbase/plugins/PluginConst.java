package com.example.libbase.plugins;

/**
 * FileName：PluginConst
 * Create By：liumengqiang
 * Description：TODO
 */
public interface PluginConst {

    String query1 = "query1";
    String DEX_PATH = "dex_path";

    String LAUNCH_MODEL = "launch_model";

    String REALLY_ACTIVITY_NAME = "reallyActivityName";

    String isPlugin = "isPlugin";

    String Plugin_1_ApkDex = "/sdcard/pluginmodule-debug.apk";
    //
    String Plugin_2_ApkDex = "/sdcard/pluginmodule2-debug.apk";


    /**
     * 四种启动模式
     */
    interface LaunchModel {
        int STANDARD = 0;

        int SINGLE_TOP = 1;

        int SINGLE_TASK = 2;

        int SINGLE_INSTANCE = 3;
    }
}
