package com.example.slbota;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.geek.libutils.app.MyLogUtil;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.zhy.base.fileprovider.FileProvider7;

import java.io.File;
import java.util.Objects;


public class OtaApkDownloadService extends Service {
    private String TAG = "OTA_APK_UPDATE";
    public static String llsApkFilePath;
    public static String urlIntent;//接受url地址

    private Handler handler = new Handler();
    /**
     * 最大重试次数  当重试次数超过这个值 则提示用户更新失败
     */
    private final int MAX_RETRY_TIME = 180;
    /**
     * 重试次数
     */
    private int retry_time = 0;
    /**
     * 每次重试之间的延迟时间
     */
    private final long retry_delay = 1000;
    /**
     * 亮屏代码执行频率
     */
    private long delay_time = 15000;
    /**
     * 重试助手
     */
    private NetAccessHelper netAccessHelper = new NetAccessHelper();
    /**
     * 亮屏代码执行线程
     */
    Runnable lightUpRun = new Runnable() {
        @Override
        public void run() {
            handlerApk.sendEmptyMessage(0);
        }
    };
    /**
     * 亮屏代码执行handler
     */
    private Handler handlerApk = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            handlerApk.postDelayed(lightUpRun, delay_time);
            return false;
        }
    });

    /**
     * 下载工具对象
     */
    final FileDownloadListener queueTarget = new OtaApkDownloadService.DFListener();

    class DFListener extends FileDownloadListener {
        @Override
        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            MyLogUtil.e(TAG, "准备中");
            //检查磁盘剩余空间大小  匹配fileSize如果磁盘剩余空间不足 则清理磁盘
//            String newFileDir = getExternalFilesDir(null) + OTASendUtils.DIR_DOWNLOAD;
            String newFileDir = Objects.requireNonNull(getExternalFilesDir(OTASendUtils.DIR_DOWNLOAD)).getAbsolutePath();
            //检查磁盘空间是否充足
            OTASendUtils.getInstance(OtaApkDownloadService.this).
                    checkDirAvailable(newFileDir, totalBytes - soFarBytes, new OTASendUtils.OnSuccessListener() {
                        @Override
                        public void callback(final boolean success) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (success) {
                                        MyLogUtil.e(TAG, "开始下载文件");
                                    } else {
                                        //TODO
                                        MyLogUtil.e(TAG, "磁盘空间不足");
                                        FileDownloader.getImpl().pause(OtaApkDownloadService.DFListener.this);
                                    }
                                }
                            });
                        }
                    });
        }

        @Override
        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            //下载中
            MyLogUtil.e(TAG, "下载文件中  下载速度：" + task.getSpeed() + "KB/s" + "  文件总大小:" + totalBytes / 1024 + "Bytes " + " 剩余:" + (totalBytes - soFarBytes) / 1024 + "Bytes未下载");
        }

        @Override
        protected void completed(BaseDownloadTask task) {
            //TODO　APK下载成功的情况
            installApk();
            //下载完成
            MyLogUtil.e(TAG, "下载成功 onFileDownloadStatusCompleted");
            MyLogUtil.e(TAG, "FilePath() " + task.getTargetFilePath());
            MyLogUtil.e(TAG, "FileName() " + task.getFilename());
        }

        @Override
        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            MyLogUtil.e(TAG, "下载暂停 " + task.getUrl());
        }

        @Override
        protected void error(BaseDownloadTask task, Throwable e) {
            MyLogUtil.e(TAG, "下载失败 " + task.getUrl());
            if (retry_time < MAX_RETRY_TIME) {
                MyLogUtil.e(TAG, "下载失败 " + retry_delay / 1000 + "秒后自动重试第" + retry_time + "次");
                handlerApk.postDelayed(retryRun, retry_delay);
            } else {
                MyLogUtil.e(TAG, "下载失败 " + "重试次数过多");
            }
        }

        @Override
        protected void warn(BaseDownloadTask task) {
        }

    }

    ;

    private synchronized void retryWithoutDisconnect() {
        FileDownloader.getImpl().create(urlIntent).setPath(llsApkFilePath).setListener(queueTarget).start();
        retry_time++;
    }

    Runnable retryRun = new Runnable() {
        @Override
        public void run() {
            retryWithoutDisconnect();
        }
    };

    public static final int HUYAN_MANAGE_NOTIFICATION_ID = 1001611;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent it = new Intent(this, OTAServicesBg.class);
        it.putExtra(OTAServicesBg.EXTRA_NOTIFICATION_ID, HUYAN_MANAGE_NOTIFICATION_ID);
        startService(it);

        if (intent != null && !intent.getStringExtra("apkurl").isEmpty()) {
            urlIntent = intent.getStringExtra("apkurl").toString();
            llsApkFilePath = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "Cellarette" + ".apk";
            netAccessHelper.resetRetry();
            //删除已经下载的APK文件
            new Thread(new Runnable() {
                @Override
                public void run() {
                    File file = new File(llsApkFilePath);
                    if (file.exists()) {
                        file.delete();
                        MyLogUtil.d(TAG, "del apk success");
                    }
                    //开始执行下载任务
                    FileDownloader.getImpl().create(urlIntent).setPath(llsApkFilePath).setListener(queueTarget).start();
                }
            }).start();
        } else {
            MyLogUtil.d(TAG, "download apkurl  is null");
            stopSelf();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FileDownloader.getImpl().pause(queueTarget);
    }

    private BaseDownloadTask createDownloadTask() {
        final String url;
        boolean isDir = false;
        String path;
        url = urlIntent;
        path = llsApkFilePath;

        MyLogUtil.d(TAG, "start download apk url" + urlIntent);
        return FileDownloader.getImpl().create(url)
                .setPath(path, isDir)
                .setCallbackProgressTimes(600)
                .setMinIntervalUpdateSpeed(400)
                .setAutoRetryTimes(60000)
                .setListener(new FileDownloadSampleListener() {

                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.pending(task, soFarBytes, totalBytes);
                    }

                    @Override
                    protected void started(BaseDownloadTask task) {
                        super.started(task);
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.progress(task, soFarBytes, totalBytes);
                        String a = String.format("已下载: %d KB  总大小: %d KB", soFarBytes / 1024, totalBytes / 1024);
                        MyLogUtil.e("test=" + a);
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        super.error(task, e);
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                        super.connected(task, etag, isContinue, soFarBytes, totalBytes);
                    }

                    @Override
                    protected void retry(BaseDownloadTask task, Throwable ex, int retryingTimes, int soFarBytes) {
                        super.retry(task, ex, retryingTimes, soFarBytes);
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.paused(task, soFarBytes, totalBytes);
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        super.completed(task);
                        MyLogUtil.d(TAG, "apk download success");
                        //下载完成安装apk
                        installApk();
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        super.warn(task);
                        started(task);
                    }
                });
    }

    public void installApk() {
        //隐藏取酒图标
//        if (com.haier.banben.ConstantUtil.VERSION_INFO == VersionInfo.NATION) {
//            MsgViewManager.destory();
//        }
//        MsgViewManager.destory();

        File saveFile = new File(llsApkFilePath);
        MyLogUtil.d(TAG, "apk download save path path =" + saveFile.toString());
        String strROM = Build.DISPLAY;
        String versionROM = strROM.substring(strROM.length() - 4, strROM.length());
        int rt = versionROM.compareTo("2.06");
        Intent mIntent = new Intent();
        //测试
//        mIntent.setAction(Intent.ACTION_VIEW);
//        mIntent.setDataAndType(Uri.fromFile(saveFile), "application/vnd.android.package-archive");
//        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(mIntent);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        FileProvider7.setIntentDataAndType(this, i, "application/vnd.android.package-archive", saveFile, true);
        startActivity(i);
        //一代主板ROM2.06以下APK升级安装
//        if (Build.DEVICE.equals(OTASendUtils.TAG_FIRST_MAINBOARD) && rt < 0 || Build.DEVICE.equals(OTASendUtils.TAG_FIRST_MAINBOARD_OLD)) {
//            //V2.06以下的版本走手动人工升级模式 弹出安装界面 手动点击升级
//            mIntent.setAction(Intent.ACTION_VIEW);
//            mIntent.setDataAndType(Uri.fromFile(saveFile), "application/vnd.android.package-archive");
//            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(mIntent);
//            MyLogUtil.d(TAG, "send upgrade apk broadcast success");
//            MyLogUtil.d(TAG, "start install apk now");
//        } else {//1.三代主板APK安装; 2.二代主板；3.一代2.06以上ROM  静默apk升级
//            mIntent.setAction("android.intent.action.SILENCE_INSTALL");
//            mIntent.putExtra("apkPath", saveFile.toString());
//            mIntent.putExtra("activityClass", "com.haier.cellarette/com.haier.cellarette.activity.HomeActivity");
//            sendBroadcast(mIntent);
//            MyLogUtil.d(TAG, "send upgrade apk broadcast success");
//            MyLogUtil.d(TAG, "start install apk now");
//        }
    }
}
