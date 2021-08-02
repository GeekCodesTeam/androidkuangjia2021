package com.example.slbota;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class OtaRomDownloadService extends Service {

    private String TAG = "OTA_ROM_UPDATE";
    public static String llsRomFilePath;
    private String romurl;
    private String rommd5;
    private File saveFile;
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
            handlerOs.sendEmptyMessage(0);
        }
    };
    /**
     * 亮屏代码执行handler
     */
    private Handler handlerOs = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            handlerOs.postDelayed(lightUpRun, delay_time);
            return false;
        }
    });
    /**
     * 下载工具对象
     */
    final FileDownloadListener queueTarget = new DFListener();

    class DFListener extends FileDownloadListener {
        @Override
        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            Log.e(TAG, "准备中");
            //检查磁盘剩余空间大小  匹配fileSize如果磁盘剩余空间不足 则清理磁盘
//            String newFileDir = getExternalFilesDir(null) + OTASendUtils.DIR_DOWNLOAD;
            String newFileDir = Objects.requireNonNull(getExternalFilesDir(OTASendUtils.DIR_DOWNLOAD)).getAbsolutePath();
            //检查磁盘空间是否充足
            OTASendUtils.getInstance(OtaRomDownloadService.this).
                    checkDirAvailable(newFileDir, totalBytes - soFarBytes, new OTASendUtils.OnSuccessListener() {
                        @Override
                        public void callback(final boolean success) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (success) {
                                        Log.e(TAG, "开始下载文件");
                                    } else {
                                        //TODO
                                        Log.e(TAG, "磁盘空间不足");
                                        FileDownloader.getImpl().pause(DFListener.this);
                                    }
                                }
                            });
                        }
                    });
        }

        @Override
        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            //下载中
            Log.e(TAG, "下载文件中  下载速度：" + task.getSpeed() + "KB/s" + "  文件总大小:" + totalBytes / 1024 + "Bytes " + " 剩余:" + (totalBytes - soFarBytes) / 1024 + "Bytes未下载");
        }

        @Override
        protected void completed(BaseDownloadTask task) {
            //TODO　ROM下载成功的情况
            updataRom();
            //下载完成
            Log.e(TAG, "下载成功 onFileDownloadStatusCompleted");
            Log.e(TAG, "FilePath() " + task.getTargetFilePath());
            Log.e(TAG, "FileName() " + task.getFilename());
        }

        @Override
        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            Log.e(TAG, "下载暂停 " + task.getUrl());
        }

        @Override
        protected void error(BaseDownloadTask task, Throwable e) {
            Log.e(TAG, "下载失败 " + task.getUrl());
            if (retry_time < MAX_RETRY_TIME) {
                Log.e(TAG, "下载失败 " + retry_delay / 1000 + "秒后自动重试第" + retry_time + "次");
                handlerOs.postDelayed(retryRun, retry_delay);
            } else {
                Log.e(TAG, "下载失败 " + "重试次数过多");
            }
        }

        @Override
        protected void warn(BaseDownloadTask task) {
        }

    }

    ;

    private synchronized void retryWithoutDisconnect() {
        FileDownloader.getImpl().create(romurl).setPath(llsRomFilePath).setListener(queueTarget).start();
        retry_time++;
    }

    Runnable retryRun = new Runnable() {
        @Override
        public void run() {
            retryWithoutDisconnect();
        }
    };


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && !intent.getStringExtra("romurl").isEmpty() && !intent.getStringExtra("rommd5").isEmpty()) {
            romurl = intent.getStringExtra("romurl").toString();
            rommd5 = intent.getStringExtra("rommd5").toString().toLowerCase();
            Log.d(TAG, "start download rom_url =" + romurl);
            Log.d(TAG, "download  rom md5  rommd5 =" + rommd5);
            llsRomFilePath = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "rom.zip";
            netAccessHelper.resetRetry();
            //删除已经下载的ROM文件
            new Thread(new Runnable() {
                @Override
                public void run() {
                    File file = new File(llsRomFilePath);
                    if (file.exists()) {
                        file.delete();
                        Log.d(TAG, "del rom success");
                    }
                    //开始执行下载任务
                    FileDownloader.getImpl().create(romurl).setPath(llsRomFilePath).setListener(queueTarget).start();
                }
            }).start();
        } else {
            Log.d(TAG, "download rom_url  is null");
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

    public void updataRom() {
        //隐藏取酒图标
//        if (com.haier.banben.ConstantUtil.VERSION_INFO == VersionInfo.NATION) {
//            MsgViewManager.destory();
//        }
//        MsgViewManager.destory();

        saveFile = new File(llsRomFilePath);
        Log.d(TAG, " ROM download save path path =" + saveFile.toString());
        Log.d(TAG, " ROM download save path path =" + saveFile.getAbsolutePath());
        String strRomMD5 = "";
        try {
            strRomMD5 = Md5Utils.getFileMD5String(saveFile);
            Log.d("strRomMD5:", strRomMD5);
            Log.d("rommd5_:", rommd5);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (strRomMD5.equals(rommd5)) {
            Log.d(TAG, "ROM verify md5 success");
            if (!Build.DEVICE.equals(OTASendUtils.TAG_FIRST_MAINBOARD)) {//BOE ROM升级
                Intent intent = new Intent();
                intent.setAction("android.intent.action.runbin");
                intent.putExtra("bin", "cp -f " + saveFile.toString() + " /cache/update.zip");
                sendBroadcast(intent);
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.runbin");
                        intent.putExtra("bin", "sync");
                        sendBroadcast(intent);
                    }
                }, 1000);
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent();
                        intent.setAction("com.haier.gradevin.action.dameon.updatefile");
                        intent.putExtra("zipfile", " ");
                        sendBroadcast(intent);
                        Log.d(TAG, "sTAG_SECOND_MAINBOARD");
                        Log.d(TAG, "send upgrade ROM broadcast success");
                        Log.d(TAG, "start upgrade ROM now");
                    }
                }, 20000);
            } else {//新旦ROM二代/三代升级/智能电子四代ROM
//                Intent intent = new Intent();
//                intent.setAction("com.haier.gradevin.action.dameon.updatefile");
//                intent.putExtra("zipfile", saveFile.toString());
//                sendBroadcast(intent);
//                Logger.d(TAG, "TAG_SECOND_MAINBOARD || TAG_THIRD_MAINBOARD");
//                MyLog.d(TAG, "TAG_SECOND_MAINBOARD || TAG_THIRD_MAINBOARD");
//                Logger.d(TAG, "send upgrade ROM broadcast success");
//                MyLog.d(TAG, "send upgrade ROM broadcast success");
//                Logger.d(TAG, "start install ROM now");
//                MyLog.d(TAG, "start install ROM now");
            }
        } else {
            Log.d(TAG, " ROM verify md5 error");
        }
    }

}
