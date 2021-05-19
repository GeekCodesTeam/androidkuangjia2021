package com.geek.libutils;

import android.os.Environment;

import com.blankj.utilcode.util.Utils;

import java.io.File;

public class ConstantsUtils {
    public static final String gen = "geekmulu";
    public static final String file_url = get_file_url();
    public static final String FILE_SEP = System.getProperty("file.separator");
    public static final String gen_img = gen + FILE_SEP + "img";
    public static final String gen_mp3 = gen + FILE_SEP + "music";
    public static final String gen_mp4 = gen + FILE_SEP + "video";
    public static final String gen_apk = gen + FILE_SEP + "apk";
    public static final String gen_wenjian = gen + FILE_SEP + "wenjian";

    public static final String file_quan = file_url + FILE_SEP + gen + FILE_SEP;//全路径
    public static final String file_url_img = file_url + FILE_SEP + gen_img + FILE_SEP;//图片全路径
    public static final String file_url_img2 = file_url + FILE_SEP + gen_img + FILE_SEP;//图片全路径2
    public static final String file_url_mp3 = file_url + FILE_SEP + gen_mp3 + FILE_SEP;//音乐全路径
    public static final String file_url_mp3_huiben = file_url + FILE_SEP + gen_mp3 + FILE_SEP + "huiben" + FILE_SEP;//音乐全路径2
    public static final String file_url_mp4 = file_url + FILE_SEP + gen_mp4 + FILE_SEP;//视频全路径
    public static final String file_url_apk = file_url + FILE_SEP + gen_apk + FILE_SEP;//下载apk的全路径
    public static final String file_url_wenjian = file_url + FILE_SEP + gen_wenjian + FILE_SEP;//下载文件的全路径


    public static String get_file_url() {
        String file_apk_url;
        File file_apks = Utils.getApp().getExternalCacheDir();
        if (file_apks != null) {
            file_apk_url = file_apks.getAbsolutePath();
        } else {
            file_apk_url = Utils.getApp().getExternalFilesDir(null).getAbsolutePath();
        }
        return file_apk_url;
    }


}
