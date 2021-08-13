/*
 * *********************************************************
 *   author   colin
 *   company  fosung
 *   email    wanglin2046@126.com
 *   date     17-10-26 上午10:07
 * ********************************************************
 */

package com.fosung.xuanchuanlan.common.util;

import android.net.TrafficStats;

import com.fosung.xuanchuanlan.common.app.ConfApplication;


/**
 * 流量获取工具类
 */
public class TrafficUtil {

    private static double[] TRAFFIC_START;

    /**
     * 在application的ocCreate中调用，记录启动时的流量
     */
    public static void init() {
        TRAFFIC_START = getTotalTrafficInfo();
    }

    /**
     * 获取程序开启到现在的使用流量
     */
    public static double[] getAppLifCycleTraffic() {
        double[] real = new double[3];
        if (TRAFFIC_START == null) {
            init();
        } else {
            double[] trafficEnd = getTotalTrafficInfo();
            real[0] = trafficEnd[0] - TRAFFIC_START[0];
            real[1] = trafficEnd[1] - TRAFFIC_START[1];
            real[2] = trafficEnd[2] - TRAFFIC_START[2];
            TRAFFIC_START = trafficEnd;
        }
        return real;
    }

    /**
     * 返回所有的有互联网访问权限的应用程序的流量信息。
     * 此方法获取的是手机开机之后的流量数据
     */
    public static double[] getTotalTrafficInfo() {
        long tx = TrafficStats.getUidTxBytes(ConfApplication.APP_CONTEXT.getApplicationInfo().uid);//发送的 上传的流量byte
        long rx = TrafficStats.getUidRxBytes(ConfApplication.APP_CONTEXT.getApplicationInfo().uid);//下载的流量 byte

        double[] arr = new double[3];
        arr[0] = tx == -1 ? 0 : byteToMB(tx);
        arr[1] = rx == -1 ? 0 : byteToMB(rx);
        arr[2] = System.currentTimeMillis();
        return arr;
    }

    //将字节数转化为kB
    private static double byteToMB(long size) {
        return size / 1024d;
    }
}
