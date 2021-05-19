package com.example.slbappcomm.videoplay.bt;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by mqh on 2018/7/17.
 */

public class BleStateReceiver extends BroadcastReceiver {
    private BleStateCallback bleStateCallback;

    public BleStateReceiver(BleStateCallback bleStateCallback) {
        this.bleStateCallback = bleStateCallback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        //获取蓝牙设备实例【如果无设备链接会返回null，如果在无实例的状态下调用了实例的方法，会报空指针异常】
        //主要与蓝牙设备有关系
//        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        switch (action) {
            case BluetoothAdapter.ACTION_DISCOVERY_STARTED:
                break;
            case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                break;
            //上面的两个链接监听，其实也可以BluetoothAdapter实现，修改状态码即可
            case BluetoothAdapter.ACTION_STATE_CHANGED:
                int blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                if (bleStateCallback != null) {
                    bleStateCallback.stateChange(blueState);
                }
                break;
            default:
                break;
        }
    }
}
