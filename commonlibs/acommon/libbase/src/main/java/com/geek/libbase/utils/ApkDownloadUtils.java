package com.geek.libbase.utils;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.blankj.utilcode.util.Utils;
import com.geek.libutils.app.BaseApp;
import com.geek.libutils.app.MyLogUtil;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadLargeFileListener;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.Proxy;

import static android.os.Build.VERSION.SDK_INT;

public class ApkDownloadUtils {

    private static ApkDownloadUtils sInstance;
    private static final Object lock = new Object();

    public int MAX_RETRY_TIME = 180;
    public int retry_time = 0;
    public final long retry_delay = 1000;
    public String url;
    public String savepath;
    public String pkgname;
    public String pkgname_jump;


    public ApkDownloadUtils() {
    }

    public static ApkDownloadUtils get() {
        if (sInstance == null) {
            synchronized (lock) {
                sInstance = new ApkDownloadUtils();
            }
        }
        return sInstance;
    }

    public void os_setup() {
        if (SDK_INT < Build.VERSION_CODES.P) {
            return;
        }
        try {
            Method forName = Class.class.getDeclaredMethod("forName", String.class);
            Method getDeclaredMethod = Class.class.getDeclaredMethod("getDeclaredMethod", String.class, Class[].class);
            Class<?> vmRuntimeClass = (Class<?>) forName.invoke(null, "dalvik.system.VMRuntime");
            Method getRuntime = (Method) getDeclaredMethod.invoke(vmRuntimeClass, "getRuntime", null);
            Method setHiddenApiExemptions = (Method) getDeclaredMethod.invoke(vmRuntimeClass, "setHiddenApiExemptions", new Class[]{String[].class});
            Object sVmRuntime = getRuntime.invoke(null);
            setHiddenApiExemptions.invoke(sVmRuntime, new Object[]{new String[]{"L"}});
        } catch (Throwable e) {
            Log.e("[error]", "reflect bootstrap failed:" + e.toString());
        }
    }

    public boolean silentInstall(PackageManager packageManager, String apkPath) {
        os_setup();
        Class<?> pmClz = packageManager.getClass();
        try {
            if (SDK_INT >= 21) {
                Class<?> aClass = Class.forName("android.app.PackageInstallObserver");
                Constructor<?> constructor = aClass.getDeclaredConstructor();
                constructor.setAccessible(true);
                Object installObserver = constructor.newInstance();
                Method method = pmClz.getDeclaredMethod("installPackage", Uri.class, aClass, int.class, String.class);
                method.setAccessible(true);
                method.invoke(packageManager, Uri.fromFile(new File(apkPath)), installObserver, 2, null);
            } else {
                Method method = pmClz.getDeclaredMethod("installPackage", Uri.class, Class.forName("android.content.pm.IPackageInstallObserver"), int.class, String.class);
                method.setAccessible(true);
                method.invoke(packageManager, Uri.fromFile(new File(apkPath)), null, 2, null);
            }
            return true;
        } catch (Exception e) {
            Log.e("TAG", e.toString());
        }
        return false;
    }

    /**
     * 初始化文件下载
     */
    public void initFileDownLoader() {
        FileDownloader.setupOnApplicationOnCreate(BaseApp.get())
                .connectionCreator(new FileDownloadUrlConnection
                        .Creator(new FileDownloadUrlConnection.Configuration()
                        .connectTimeout(60_000) // set connection timeout.
                        .readTimeout(60_000) // set read timeout.
                        .proxy(Proxy.NO_PROXY) // set proxy
                )).commit();
        FileDownloadUtils.setDefaultSaveRootPath(get_file_url() + File.separator + "ota/download");
    }

    /**
     * 获取assets路径bufen
     *
     * @return
     */
    public String get_file_url() {
        String file_apk_url;
        File file_apks = BaseApp.get().getExternalCacheDir();
        if (file_apks != null) {
            file_apk_url = file_apks.getAbsolutePath();
        } else {
            file_apk_url = Utils.getApp().getExternalFilesDir(null).getAbsolutePath();
        }
        return file_apk_url;
    }

    public void zujian_loading(String url, String savepath, String pkgname, String pkgname_jump, OnLoadingStatus onLoadingStatus) {
        this.url = url;
        this.savepath = savepath;
        this.pkgname = pkgname;
        this.pkgname_jump = pkgname_jump;
        this.onLoadingStatus = onLoadingStatus;
        //开始执行下载任务
        FileDownloader.getImpl().create(url).setPath(savepath).setListener(queueTarget).start();
    }

    /**
     * 下载工具对象
     */
    final FileDownloadListener queueTarget = new DFListener();

    class DFListener extends FileDownloadLargeFileListener {
        @Override
        protected void pending(BaseDownloadTask task, long soFarBytes, long totalBytes) {
            MyLogUtil.e("TAG", "准备中");
            if (onLoadingStatus != null) {
                onLoadingStatus.pending(task, soFarBytes, totalBytes);
            }
        }

        @Override
        protected void progress(BaseDownloadTask task, long soFarBytes, long totalBytes) {
            //下载中
            MyLogUtil.e("TAG", "下载文件中  下载速度：" + task.getSpeed() + "KB/s" + "  文件总大小:" + totalBytes / 1024 + "Bytes " + " 剩余:" + (totalBytes - soFarBytes) / 1024 + "Bytes未下载");
            if (onLoadingStatus != null) {
                onLoadingStatus.progress(task, soFarBytes, totalBytes);
            }
        }

        @Override
        protected void completed(BaseDownloadTask task) {
            MyLogUtil.e("TAG", "完成");
            if (onLoadingStatus != null) {
                onLoadingStatus.completed(task);
            }
        }

        @Override
        protected void paused(BaseDownloadTask task, long soFarBytes, long totalBytes) {
            MyLogUtil.e("TAG", "下载暂停 " + task.getUrl());
            if (onLoadingStatus != null) {
                onLoadingStatus.paused(task, soFarBytes, totalBytes);
            }
        }

        @Override
        protected void error(BaseDownloadTask task, Throwable e) {
            MyLogUtil.e("TAG", "下载失败 " + task.getUrl());
            if (onLoadingStatus != null) {
                onLoadingStatus.error(task, e);
            }
            if (retry_time < 3) {
                MyLogUtil.e("TAG", "下载失败 " + retry_delay / 1000 + "秒后自动重试第" + retry_time + "次");
                new Handler(Looper.myLooper()).postDelayed(() -> {
                    FileDownloader.getImpl().create(url).setPath(savepath).setListener(queueTarget).start();
                    retry_time++;
                }, retry_delay);
            } else {
                MyLogUtil.e("TAG", "下载失败 " + "重试次数过多");
            }
        }

        @Override
        protected void started(BaseDownloadTask task) {
            super.started(task);
        }

        @Override
        protected void warn(BaseDownloadTask task) {
            started(task);
        }
    }

    public OnLoadingStatus onLoadingStatus;

    public void setOnLoadingStatus(OnLoadingStatus onLoadingStatus) {
        this.onLoadingStatus = onLoadingStatus;
    }

    public interface OnLoadingStatus {
        void pending(BaseDownloadTask task, long soFarBytes, long totalBytes);

        void progress(BaseDownloadTask task, long soFarBytes, long totalBytes);

        void completed(BaseDownloadTask task);

        void paused(BaseDownloadTask task, long soFarBytes, long totalBytes);

        void error(BaseDownloadTask task, Throwable e);
    }

    public void onDestroy() {
        FileDownloader.getImpl().pause(queueTarget);
    }
}
