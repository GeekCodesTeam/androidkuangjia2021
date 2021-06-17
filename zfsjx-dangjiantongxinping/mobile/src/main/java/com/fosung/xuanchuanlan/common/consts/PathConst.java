/*
 * **********************************************************
 *   author   colin
 *   company  fosung
 *   email    wanglin2046@126.com
 *   date     16-10-8 下午3:55
 * *********************************************************
 */

package com.fosung.xuanchuanlan.common.consts;

import com.fosung.frameutils.util.SDCardUtil;
import com.fosung.xuanchuanlan.common.app.ConfApplication;


import java.io.File;

/**
 * 路径Constant数据
 */
public class PathConst {

    /**
     * SD卡根路径
     */
    public static final String SDCARD;
    /**
     * 缓存路径
     */
    public static final String CACHE;
    /**
     * 缓存路径
     */
    public static final String FILE;
    /**
     * ASSETS路径
     */
    public static final String ASSETS;
    /**
     * 程序根路径 使用需要申请权限
     */
    public static String APP;

    static {
        boolean sdCardExist = SDCardUtil.isSDCardEnable();
        if (sdCardExist) {
            SDCARD = SDCardUtil.getSDCardPath() + File.separator;// 获取SD卡根目录
        } else {
            SDCARD = SDCardUtil.getRootDirectoryPath() + File.separator;// 获取根目录
        }

        APP = SDCARD + "xuanchuanlan/";

        File cacheFile = ConfApplication.APP_CONTEXT.getExternalCacheDir();
        CACHE = cacheFile == null ? ConfApplication.APP_CONTEXT.getCacheDir()
                                                   .getPath() + File.separator : cacheFile.getPath() + File.separator;

        File file = ConfApplication.APP_CONTEXT.getExternalFilesDir(null);
        FILE = file == null ? ConfApplication.APP_CONTEXT.getFilesDir()
                                             .getPath() + File.separator : file.getPath() + File.separator;

        ASSETS = "file:///android_asset/";
    }
}
