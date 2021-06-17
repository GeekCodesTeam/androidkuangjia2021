package com.fosung.xuanchuanlan.common.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;

import java.util.List;

public class NetStatusMangerUtils {

    /**
     * 判断是否有网络服务
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {

       if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isConnected();
            }
        }
        return false;
    }

    /**
     * 判断是否有网络服务
     * @param context
     * @return
     */
    public static boolean isNetworkAvaliable(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {

        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isConnected();
            }
        }
        return false;
    }

    /**
     * 获取wifi的名字
     * @param context
     * @return
     */
    public static String getWifiName(Context context) {

        return getBaseName(context, ConnectivityManager.TYPE_WIFI);
    }

    /**
     * 获取分配的以太网ip
     * @param context
     * @return
     */
    public static String getEthernetIP(Context context) {

        return getBaseName(context, ConnectivityManager.TYPE_ETHERNET);
    }


    public static String getBaseName(Context context, int type) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo workInfo = mConnectivityManager.getNetworkInfo(type);
            if (workInfo != null&&workInfo.getExtraInfo()!=null) {
                return workInfo.getExtraInfo().replace("\"","");
            }
        }
        return "";
    }

    public static NetworkInfo.DetailedState getBaseState(Context context, int type) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo workInfo = mConnectivityManager.getNetworkInfo(type);
            if (workInfo != null) {
                return workInfo.getDetailedState();
            }
        }
        return NetworkInfo.DetailedState.DISCONNECTED;
    }


    /**
     * 获取wifi的网络状态
     * @param context
     * @return
     */
    public static NetworkInfo.DetailedState getWifiConnectState(Context context) {

        return getBaseState(context, ConnectivityManager.TYPE_WIFI);
    }

    /**
     * 获取以太网的状态
     * @param context
     * @return
     */
    public static NetworkInfo.DetailedState getEthernetConnectState(Context context) {

        return getBaseState(context, ConnectivityManager.TYPE_ETHERNET);
    }



    private static List<ScanResult> resultWifiList;

    public static  void addScanResult(List<ScanResult> resultList){

        if(resultList!=null) {
            if(resultWifiList!=null)
                resultWifiList.clear();
            resultWifiList =resultList;
        }

    }

    public static List<ScanResult> getResultWifiList(){
        return resultWifiList;
    }


}
