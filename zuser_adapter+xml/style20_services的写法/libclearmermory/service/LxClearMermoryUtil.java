package com.example.shining.libclearmermory.service;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import com.example.shining.libutils.utilslib.app.MyLogUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;


public class LxClearMermoryUtil {

    private static final String TAG = "LxClearMermoryService";
    private static final int maxLength = 10;
    private static Queue<Long> queue = new LinkedBlockingQueue<Long>(maxLength);

    //获取可用内存大小
    public static long getAvailMemory(Context context) {
        // 获取android当前可用内存大小
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        //mi.availMem; 当前系统的可用内存
        //return Formatter.formatFileSize(context, mi.availMem);// 将获取的内存大小规格化
        MyLogUtil.d(TAG, "可用内存---->>>" + mi.availMem / (1024 * 1024));
        return mi.availMem / (1024 * 1024);
    }

    public static synchronized long getMaxNumberInQueue(long temp) {
        long max = 0;
        while (queue.size() >= maxLength) {
            queue.poll();
        }
        queue.add(temp);

        if (queue.size() == maxLength) {
            for (long element : queue) {
                if (element > max) {
                    max = element;
                }
            }
        }

        return max;
    }

    /**
     * 根据滑动窗体算法获取当前内存的阈值bufen 暂定如果大于当前总内存的90%通知重启
     *
     * @param ll
     */
    public static void getMaxclearmermory(long ll) {
        //滑动窗体算法取最高值bufen
        long max = getMaxNumberInQueue(ll);
        //通知重启bufen
        long a = getTotalMemory() * 90;
        long b = max;
        MyLogUtil.d(TAG, max + "");
        if (b > a) {
//            ShellExe.RootCCTCommand("reboot");
        }
    }

    /**
     * 获取系统总内存bufen
     */
    public static long getTotalMemory() {
        String str1 = "/proc/meminfo";
        String str2 = "";
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            while ((str2 = localBufferedReader.readLine()) != null) {
                Log.i(TAG, "---" + str2);
                str2 = str2.replaceAll("[^0-9]", "");
//                arrayOfString = str2.split("//s+");
//                for (String num : arrayOfString) {
//                    Log.i(str2, num + "/t");
//                }
                initial_memory = Long.valueOf(str2) / 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
                localBufferedReader.close();
            }
        } catch (IOException e) {
        }
        return initial_memory;
    }

    /**
     * 1024 = 1KB
     *
     * @param size
     * @return
     */
    public String formatSize(long size) {
        String suffix = null;
        float fSize = 0;

        if (size >= 1024) {
            suffix = "KB";
            fSize = size / 1024;
            if (fSize >= 1024) {
                suffix = "MB";
                fSize /= 1024;
            }
            if (fSize >= 1024) {
                suffix = "GB";
                fSize /= 1024;
            }
        } else {
            fSize = size;
        }
        java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");
        StringBuilder resultBuffer = new StringBuilder(df.format(fSize));
        if (suffix != null)
            resultBuffer.append(suffix);
        return resultBuffer.toString();
    }

    /**
     * 获取当前SD卡的总大小和可用大小bufen
     *
     * @return
     */
    public static long[] getSDCardMemory() {
        long[] sdCardInfo = new long[2];
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long bSize = sf.getBlockSize();
            long bCount = sf.getBlockCount();
            long availBlocks = sf.getAvailableBlocks();

            sdCardInfo[0] = bSize * bCount;//总大小
            sdCardInfo[1] = bSize * availBlocks;//可用大小
        }
        return sdCardInfo;
    }

    /**
     * 获取当前ROM的总大小和可用大小bufen
     *
     * @return
     */
    public static long[] getRomMemroy() {
        long[] romInfo = new long[2];
        //Total rom memory
        romInfo[0] = getTotalInternalMemorySize();
        //Available rom memory
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        romInfo[1] = blockSize * availableBlocks;
        return romInfo;
    }

    public static long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    /**
     * 获取CPU使用率
     *
     * @return
     */
    public static float getProcessCpuRate() {

        float totalCpuTime1 = getTotalCpuTime();
        float processCpuTime1 = getAppCpuTime();
        try {
            Thread.sleep(360);
        } catch (Exception e) {
        }

        float totalCpuTime2 = getTotalCpuTime();
        float processCpuTime2 = getAppCpuTime();

        float cpuRate = 100 * (processCpuTime2 - processCpuTime1)
                / (totalCpuTime2 - totalCpuTime1);

        return cpuRate;
    }

    public static long getTotalCpuTime() {   // 获取系统总CPU使用时间
        String[] cpuInfos = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("/proc/stat")), 1000);
            String load = reader.readLine();
            reader.close();
            cpuInfos = load.split(" ");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        long totalCpu = Long.parseLong(cpuInfos[2])
                + Long.parseLong(cpuInfos[3]) + Long.parseLong(cpuInfos[4])
                + Long.parseLong(cpuInfos[6]) + Long.parseLong(cpuInfos[5])
                + Long.parseLong(cpuInfos[7]) + Long.parseLong(cpuInfos[8]);
        return totalCpu;
    }

    public static long getAppCpuTime() {   // 获取应用占用的CPU时间
        String[] cpuInfos = null;
        try {
            int pid = android.os.Process.myPid();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("/proc/" + pid + "/stat")), 1000);
            String load = reader.readLine();
            reader.close();
            cpuInfos = load.split(" ");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        long appCpuTime = Long.parseLong(cpuInfos[13])
                + Long.parseLong(cpuInfos[14]) + Long.parseLong(cpuInfos[15])
                + Long.parseLong(cpuInfos[16]);
        return appCpuTime;
    }


}