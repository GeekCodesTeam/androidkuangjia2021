package com.haier.cellarette.baselibrary.networkview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import java.lang.reflect.Method;

public class NetState {
    private boolean isUpdate = false;
    public NetconListener2 mNet_con;
    private Context mContext;
    private NetworkChangeListener networkChangeListener;

    public void setNetStateListener(NetconListener2 listener, Context context) {
        mNet_con = listener;
        if (mNet_con == null || context == null)
            return;
        mContext = context;
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        networkChangeListener = new NetworkChangeListener();
        context.registerReceiver(networkChangeListener, filter);
    }

    private class NetworkChangeListener extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connManager == null)
                    return;

                NetworkInfo wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                NetworkInfo mobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

                if (!isUpdate && ((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected()))) {//有网但没刷新
                    if (mobile != null && mobile.isConnected() && mNet_con != null) {//移动网络
                        //如果点击刷新需要调用setUpdate设置update状态
                        mNet_con.showNetPopup();
                    } else if (mNet_con != null && wifi != null && wifi.isConnected()) {//wifi
                        isUpdate = true;
                        mNet_con.net_con_success();
                    }
                } else if (wifi != null && !wifi.isConnected() && mobile != null && !mobile.isConnected()) {//没有网络
                    isUpdate = false;
                    if (mNet_con != null) {
                        mNet_con.net_con_none();
                    }
                }
            }
        }
    }

    public void unregisterReceiver() {
        if (mContext != null && networkChangeListener != null) {
            mContext.unregisterReceiver(networkChangeListener);
        }
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    public boolean isMobileEnableReflex(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            Method getMobileDataEnabledMethod = ConnectivityManager.class.getDeclaredMethod("getMobileDataEnabled");
            getMobileDataEnabledMethod.setAccessible(true);
            return (Boolean) getMobileDataEnabledMethod.invoke(connectivityManager);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}