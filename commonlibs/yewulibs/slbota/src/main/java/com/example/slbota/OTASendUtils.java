package com.example.slbota;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.StatFs;

import androidx.annotation.RequiresApi;

import com.blankj.utilcode.util.Utils;
import com.geek.libutils.app.MyLogUtil;

import java.io.File;

public class OTASendUtils {

    private static volatile OTASendUtils instance;
    private Context mContext;
    public static final String OS_VERSION_NAME_OLD = "os_version_old";
    public static final String ACTION_OTA_ROM_UPDATA = "com.haier.cellarette.rom";
    public static final String ACTION_OTA_APK_UPDATA = "com.haier.cellarette.apk";
    public static final String DIR_PROJECT = "/ota/";
    public static final String DIR_DOWNLOAD = DIR_PROJECT + "download";

    //第一代主板设备型号（4.4的系统rk3288，5.1的系统xindan）
    //第一代主板老型号（4.4系统）
    public static final String TAG_FIRST_MAINBOARD_OLD = "rk3288";
    //第一代主板(5.1)
    public static final String TAG_FIRST_MAINBOARD = "xindan";

    private OTASendUtils(Context context) {
        this.mContext = context;
    }

    public static OTASendUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (OTASendUtils.class) {
                instance = new OTASendUtils(context);
            }
        }
        return instance;
    }

    //TODO 发OTA升级广播
    public void send_broadcast1(String msg) {
        Intent intent = new Intent();
        intent.putExtra("msg", msg);
        intent.setAction(OTASendUtils.ACTION_OTA_ROM_UPDATA);
        mContext.sendBroadcast(intent);
    }

    //TODO 发APK升级广播
    public void send_broadcast2(String msg) {
        Intent intent = new Intent();
        intent.putExtra("msg", msg);
        intent.setAction(OTASendUtils.ACTION_OTA_APK_UPDATA);
        mContext.sendBroadcast(intent);
    }


    /**
     * 下载文件前清理磁盘并返回磁盘是否可用
     *
     * @param path     需要下载文件的存放目录
     * @param fileSize 需要下载的文件大小  单位byte
     * @return true可用  false 不可用
     */
    public void checkDirAvailable(final String path, final long fileSize, final OnSuccessListener li) {
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void run() {
                File dir = new File(path);
                long avaliaSize = getAvailableSizeOfFilePath(dir);
                MyLogUtil.i("FileUtil", "avaliaSize:" + avaliaSize + " fileSize:" + fileSize);
                if (avaliaSize < fileSize) {
                    resetDir(DIR_DOWNLOAD);//清空下载文件夹
                    avaliaSize = getAvailableSizeOfFilePath(dir);
                    if (avaliaSize < fileSize) {
//                        resetDir(DIR_CACHE);//清空缓存
//                        resetDir(DIR_LOADER);//清空图片缓存
//                        GlideUtil.clearDiskCache(MyApplication.getInstance());//清空图片缓存
                        avaliaSize = getAvailableSizeOfFilePath(dir);
                        if (avaliaSize < fileSize) {
                            resetDir(DIR_PROJECT);//清空项目文件夹
                            avaliaSize = getAvailableSizeOfFilePath(dir);
                            if (avaliaSize < fileSize) {
                                if (li != null) {
                                    li.callback(false);
                                }
                            }
                        }
                    }
                }
                if (li != null) {
                    li.callback(true);
                }
            }
        }).start();
    }

    /**
     * 删除文件或文件夹
     */
    public void deleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
                file.delete();
            }
        } else {
            MyLogUtil.e("所删除的文件不存在！" + '\n');
        }
    }

    /**
     * 清空文件夹
     */
    public void resetDir(File file) {
        if (file.exists()) {
            deleteFile(file);
            file.mkdirs();
        }
    }

    /**
     * 清空文件夹
     */
    public void resetDir(String path) {
//        File mExternalStorage = getExternalFilesDir(null);
        File mExternalStorage = Utils.getApp().getExternalFilesDir(null);
        File file = new File(mExternalStorage + path);
        if (file.exists()) {
            deleteFile(file);
            file.mkdirs();
        }
    }

    /**
     * 获取目录剩余可用空间 返回bytes
     */
    public long getAvailableSizeOfFilePath(File path) {
        if (path.isFile())
            return path.length();
        StatFs sf = new StatFs(path.getPath());

        //文件系统中总的空闲字节数，包括保留的存储区块（不能被普通应用程序使用）
//		sf.getFreeBytes()
        //获取block的SIZE
//		long blocSize=sf.getBlockSizeLong();
        //获取BLOCK数量
//		long totalBlocks=sf.getBlockCountLong();
        //己使用的Block的数量
//		long availaBlock=sf.getAvailableBlocksLong();

        long total = sf.getAvailableBytes();
        return total;
    }

    public interface OnSuccessListener {
        void callback(boolean success);
    }

}
