package com.geek.libbase.plugins;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class LoadUtils {

    public static String Plugin_1_ApkDex = "/sdcard/pluginmodule-debug.apk";

    public static String setpath1(Activity activity,String fileName) {
        try {
            Plugin_1_ApkDex = LoadUtils.copyAssetAndWrite(activity, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Plugin_1_ApkDex;
    }

    //把asset目录下的插件apk文件下载到指定文件缓存中
    public static String copyAssetAndWrite(Context context, String fileName) throws Exception {
        File cacheDir = context.getCacheDir();
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        File outFile = new File(cacheDir, fileName);
        if (!outFile.exists()) {
            boolean res = outFile.createNewFile();
            if (res) {
                InputStream is = context.getAssets().open(fileName);
                FileOutputStream fos = new FileOutputStream(outFile);
                byte[] buffer = new byte[is.available()];
                int byteCount;
                while ((byteCount = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, byteCount);
                }
                fos.flush();
                ;
                is.close();
                ;
                fos.close();
                Toast.makeText(context, "下载成功", Toast.LENGTH_SHORT).show();
                return outFile.getAbsolutePath();
            }
        } else {
            Toast.makeText(context, "文件已存在", Toast.LENGTH_SHORT).show();
            return outFile.getAbsolutePath();
        }
        return "";
    }
}
