package com.haier.banben;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.example.shining.libutils.utilslib.app.App;

/**
 * Created by shining on 2017/12/8.
 */

public class TestVersion {

    public static final String ST_DATA = "ST_DATA";
    /**
     * 在程序入口出打上log，用来输出key的值bufen
     */
    public static void jpush_key_manifest_xml_string() {
        String jpush_appkey;
        try {
            ApplicationInfo appInfo = App.get().getPackageManager()
                    .getApplicationInfo(App.get().getPackageName(),
                            PackageManager.GET_META_DATA);

            jpush_appkey = appInfo.metaData.getString(ST_DATA);

            Log.e("ST_DATA", "ST_DATA=" + jpush_appkey);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }
    }

}
