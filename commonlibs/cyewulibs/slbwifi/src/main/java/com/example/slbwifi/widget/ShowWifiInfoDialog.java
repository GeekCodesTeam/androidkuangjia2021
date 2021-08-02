package com.example.slbwifi.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.wifi.WifiInfo;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.slbwifi.R;
import com.example.slbwifi.util.WifiUtil;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

/**
 * @function:   wifi详细信息弹窗
 * @description:
 * @history: 1.  date:2016/2/16 14:44
 * author:PengLiang
 * modification:
 */
@SuppressLint("NewApi")
public class ShowWifiInfoDialog extends DialogFragment implements View.OnClickListener {
    private FragmentActivity mActivity;
    private static final String TAG = ShowWifiInfoDialog.class.getSimpleName();
    private IRemoveWifi mIRemoveWifi = null;
    private WifiInfo mConnectedInfo;
    private String encrypt;

    public ShowWifiInfoDialog() {

    }

    public interface IRemoveWifi {

        void onRemoveClick(String ssid, int networkId);
    }


    public static ShowWifiInfoDialog newInstance(IRemoveWifi mIRemoveWifi, WifiInfo mConnectedInfo, String encrypt) {
        ShowWifiInfoDialog mFragment = new ShowWifiInfoDialog();

        mFragment.mConnectedInfo = mConnectedInfo;

        mFragment.encrypt = encrypt;

        mFragment.mIRemoveWifi = mIRemoveWifi;

        return mFragment;
    }

    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);

        try {

            mActivity = (FragmentActivity) activity;
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.show_connected_wifi_page, null);

        Button btCancel = (Button) view.findViewById(R.id.button12);
        Button btSure = (Button) view.findViewById(R.id.button13);
        btCancel.setOnClickListener(this);
        btSure.setOnClickListener(this);
        TextView mStateTv = (TextView) view.findViewById(R.id.state_tv);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        int Ip = mConnectedInfo.getIpAddress();
        Log.d(TAG, "ip = " + Ip);
        String strIp = "" + (Ip & 0xFF) + "." + ((Ip >> 8) & 0xFF) + "." + ((Ip >> 16) & 0xFF) + "." + ((Ip >> 24) & 0xFF);

        if (mConnectedInfo.getSSID() != null && mConnectedInfo.getBSSID() != null && !strIp.equals("0.0.0.0")) {

            mStateTv.setText("已连接");
        } else {
            mStateTv.setText("正在连接...");
        }

        TextView mSafetyTv = (TextView) view.findViewById(R.id.safety_tv);
        TextView ssidName = (TextView) view.findViewById(R.id.textView60);

        mSafetyTv.setText(encrypt);
        ssidName.setText(mConnectedInfo.getSSID().replace("\"", ""));
        TextView mLevelTv = (TextView) view.findViewById(R.id.level_tv);

        mLevelTv.setText(mConnectedInfo.getRssi() + "");

        TextView mSpeedTv = (TextView) view.findViewById(R.id.speed_tv);

        mSpeedTv.setText(mConnectedInfo.getLinkSpeed() + " Mbps");

        TextView mIpTv = (TextView) view.findViewById(R.id.ip_tv);

        mIpTv.setText(WifiUtil.long2ip(mConnectedInfo.getIpAddress()));

        try {

            Field mField = mConnectedInfo.getClass().getDeclaredField("mIpAddress");
            mField.setAccessible(true);
            InetAddress mInetAddr = (InetAddress) mField.get(mConnectedInfo);

//	        for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
//                Log.d(ShowWifiInfoDialog.class.getSimpleName(), networkInterface.toString());
//            }

            NetworkInterface mInterface = NetworkInterface.getByInetAddress(mInetAddr);

            byte[] mac = mInterface.getHardwareAddress();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {

                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? ":" : ""));
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button12) {//button cancel
            this.dismiss();
        } else if (id == R.id.button13) {//button 忽略网络
            if (mIRemoveWifi != null) {
                mIRemoveWifi.onRemoveClick(mConnectedInfo.getSSID(), mConnectedInfo.getNetworkId());
            }
            this.dismiss();
        }
    }

    public static void show(FragmentActivity mActivity, IRemoveWifi mIRemoveWifi, WifiInfo mWifiInfo, String encrypt) {

        FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();

        Fragment mBefore = mActivity.getSupportFragmentManager().findFragmentByTag(ShowWifiInfoDialog.class.getSimpleName());

        if (mBefore != null) {

            ((DialogFragment) mBefore).dismiss();

            ft.remove(mBefore);
        }
//        ft.addToBackStack(null);

        DialogFragment mNow = ShowWifiInfoDialog.newInstance(mIRemoveWifi, mWifiInfo, encrypt);

        mNow.show(ft, ShowWifiInfoDialog.class.getSimpleName());
    }
}
