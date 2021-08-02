package com.example.gsydemo.bt;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.GSYApp;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by mqh on 2018/7/17.
 */

public class BleService extends Service implements BleStateCallback {
    private String TAG = "BleService";
    private BleServiceBinder bleServiceBinder;
    private BleThread bleThread;
    private BleStateReceiver bleStateReceiver;
    private BluetoothAdapter mBluetoothAdapter;
    public static List<Member> inClassMemberList = new ArrayList<>();//当前正在上课的会员
    public static List<String> deviceNumList = new ArrayList<>();//当前正在上课的会员的手环序列号
    private HandlerThread bleDataThread;
    private Handler bleDataHandler;
    private OnReceiveMemberListener onReceiveMemberListener;

    public void setOnReceiveMemberListener(OnReceiveMemberListener onReceiveMemberListener) {
        this.onReceiveMemberListener = onReceiveMemberListener;
    }

    public static final int BT_MANAGE_NOTIFICATION_ID = 1001612;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        startForeground(HUYAN_MANAGE_NOTIFICATION_ID, new Notification());
        startForeground(BT_MANAGE_NOTIFICATION_ID, BleServicesBg.she_notifichanged());

        Intent it = new Intent(this, BleServicesBg.class);
        it.putExtra(BleServicesBg.EXTRA_NOTIFICATION_ID, BT_MANAGE_NOTIFICATION_ID);
        startService(it);

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if (bleServiceBinder == null)
            bleServiceBinder = new BleServiceBinder();
        return bleServiceBinder;
    }

    public class BleServiceBinder extends Binder {
        public BleService getService() {
            return BleService.this;
        }
    }

    public static void setInClassMemberList(List<Member> classMemberList) {
        inClassMemberList = classMemberList;
        List<String> numList = new ArrayList<>();
        if (!ListUtil.isEmpty(inClassMemberList)) {
            for (Member member : inClassMemberList) {
                numList.add(member.getDeviceNum());
            }
        }
        deviceNumList = numList;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("BleService", "onCreate");
//        bleManager = BleManager.getInstance();
        bleStateReceiver = new BleStateReceiver(this);
        registerReceiver(bleStateReceiver, makeBleFilters());
        startLeScanThread();
    }

    //蓝牙监听需要添加的Action
    private IntentFilter makeBleFilters() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        return intentFilter;
    }

    public interface OnReceiveMemberListener {
        void onReceiveMember(Member member);
    }

    private class BleThread extends Thread {
        @Override
        public void run() {
            super.run();
            startLeScan();
        }
    }

    public void startLeScanThread() {
        if (bleThread == null) {
            bleThread = new BleThread();
        }
        bleThread.start();
    }

    //开启蓝牙扫描
    private void startLeScan() {
        if (mBluetoothAdapter == null) {
            BluetoothManager bluetoothManager = (BluetoothManager) GSYApp.get().getSystemService(BLUETOOTH_SERVICE);//这里与标准蓝牙略有不同
            mBluetoothAdapter = bluetoothManager.getAdapter();
        }
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Log.e(TAG, "蓝牙没有连接");
            return;
        }
//        if (bleDataThread == null) {
        bleDataThread = new HandlerThread("handlerBleData");
        bleDataThread.start();
        bleDataHandler = new Handler(bleDataThread.getLooper()) {
            public void handleMessage(android.os.Message msg) {
                try {
//                    Thread.sleep(500);
                    handlerBleData(msg);
                } catch (Exception e) {
                    Log.e(TAG, "bleDataHandler 报错：" + e);
                }
            }
        };
//        }
        mBluetoothAdapter.startLeScan(scanCallback);
    }


    private long lastMills = 0;
    private BluetoothAdapter.LeScanCallback scanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
//          匹配到当前正在上课的会员手环的广播心率数据
            String deviceName = device.getName();
            deviceName = "设备名：" + deviceName;
            String deviceAddr = "Mac地址：" + device.getAddress();
            String broadcastPack = "广播包：" + bytes2HexStr(scanRecord);
            int hr = getHrData(bytes2HexStr(scanRecord));
            String broadcastPack2 = "心跳：" + hr;
//            Log.e(TAG, deviceName + deviceAddr + broadcastPack2 + broadcastPack);
//            Log.E(TAG, "deviceNumList:" + deviceNumList);
//            if (bleDeviceMatchCallbackSet.size() == 0)
//                return;
//            Log.e(TAG, "KKKKKKKKKKKKKKKKKKKKKKKKKK");
            if (ListUtil.isEmpty(deviceNumList) || bleDataHandler == null) {
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putByteArray("scanRecord", scanRecord);
            bundle.putParcelable("device", device);
            Message message = Message.obtain();
            message.setData(bundle);
//            handlerBleData(device, scanRecord);
            bleDataHandler.sendMessage(message);
//            bleDataHandler.sendMessageDelayed(message, 1500);
        }
    };

    @Override
    public void stateChange(int blueState) {
        switch (blueState) {
            case BluetoothAdapter.STATE_OFF:
                Log.e(TAG, "蓝牙关闭");
                break;
            case BluetoothAdapter.STATE_TURNING_OFF:
                Log.e(TAG, "蓝牙正在关闭");
                break;
            case BluetoothAdapter.STATE_ON:
                Log.e(TAG, "蓝牙开启");
                startLeScan();
                break;
            case BluetoothAdapter.STATE_TURNING_ON:
                Log.e(TAG, "蓝牙正在开启");
                break;
        }
    }

    //scanRecords的格式转换
    private final char[] hexArray = "0123456789ABCDEF".toCharArray();

    private String bytes2HexStr(byte[] bs) {
        StringBuilder sb = new StringBuilder();
        int bit;
        int length = bs.length;
        for (int i = 0; i < length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(hexArray[bit]);
            bit = bs[i] & 0x0f;
            sb.append(hexArray[bit]);
            if (i != length - 1)
                sb.append(",");
        }
        return sb.toString();
    }

    private int getHrData(String str) {
        int hr = 0;
        if (TextUtils.isEmpty(str))
            return hr;
        String[] dataArr = str.split(",");
        try {
            List<String> list = Arrays.asList(dataArr);
            if (list != null && list.contains("FF") && list.size() > (list.indexOf("FF") + 6)) {
                String hrHex = list.get(list.indexOf("FF") + 6);
                hr = Integer.parseInt(hrHex, 16);
            }
        } catch (Exception e) {

        }
        return hr;
    }

    /**
     * 计算每分钟的卡路里
     * 男：卡路里 = (年龄 x 0.2017 + 体重 x0.1988 + 心率 x 0.6309 - 55.0969] x 运动时间/ 4.184.
     * 女：卡路里 = (年龄 x 0.074 + 体重 x 0.1263 + 心率x 0.4472 - 20.4022] x 运动时间/ 4.184.
     * 其中体重按公斤算，心率按每小时，运动时间按分钟计。
     *
     * @param hr
     * @return
     */
    private double getCal(Member member, int hr) {
        if (member == null || member.getFirstTimestamp() == 0 || member.getLastTimestamp() == 0) {
            return 0;
        }
        double cal;
        String sex = member.getSex();
        double weight = member.getWeight();
        int lastHr = member.getHr();// 先写死
        double lastCal = member.getCal();
        int heart_rate = (hr + lastHr) / 2;//平均心率
        int age = member.getAge();
        double preTime = (member.getCurrentTimestamp() - member.getLastTimestamp()) / (1000 * 60.00);
        if ("m".equals(sex)) {
            double tmp = (age * 0.2017 + weight * 0.1988 + heart_rate * 0.6309 - 55.0969) / 4.184;
            if (tmp < 0.651) {
                tmp = 0.651;
            }
            cal = tmp * preTime;
        } else {
            double tmp = (age * 0.074 - weight * 0.1263 + heart_rate * 0.4472 - 20.4022) / 4.184;
            if (tmp < 0.55) {
                tmp = 0.55;
            }
            cal = tmp * preTime;
        }
        double nowCal = cal + lastCal;
        member.setCal(nowCal);
        return nowCal;
    }

    /**
     * （1）BMI=体重（公斤）÷（身高×身高）（米）；
     * （2）体脂率：1.2×BMI+0.23×年龄-5.4-10.8×性别（男为1，女为0）。
     *
     * @param member
     * @return
     */
    private double getBMI(Member member) {
        double aaa = 0.0;
        DecimalFormat df = new DecimalFormat("######0.00");
        aaa = member.getWeight() / (Double.valueOf(member.getHeight()) * Double.valueOf(member.getHeight()));
        return Double.valueOf(df.format(aaa));
    }

    /**
     * （1）BMI=体重（公斤）÷（身高×身高）（米）；
     * （2）体脂率：1.2×BMI+0.23×年龄-5.4-10.8×性别（男为1，女为0）。
     *
     * @param member
     * @return
     */
    private double getTizhi(Member member) {
        double aaa = 0.0;
        int bbb = 0;
        DecimalFormat df = new DecimalFormat("######0.00");
        if (member.getSex().equals("m")) {
            bbb = 1;
        } else {
            bbb = 0;
        }
        aaa = 1.2 * getBMI(member) + 0.23 * member.getAge() - 5.4 - 10.8 * bbb;
        return Double.valueOf(df.format(aaa));
    }

    private void handlerBleData(Message message) {
        if (message == null || message.getData() == null)
            return;
        BluetoothDevice device = message.getData().getParcelable("device");
        byte[] scanRecord = message.getData().getByteArray("scanRecord");
        String braceletNo = getBraceletNo(device);
//        Log.e(TAG, "测试测试测试测试测试测试测试测试测试测试");
        //
        long currMills = System.currentTimeMillis();
        if (lastMills == 0) {
            lastMills = currMills;
        }
//        if (!TextUtils.isEmpty(braceletNo) && !ListUtil.isEmpty(deviceNumList) && !ListUtil.isEmpty(inClassMemberList) && deviceNumList.size() == inClassMemberList.size() && deviceNumList.contains(braceletNo) && currMills - lastMills >= 300) {
        // ceshi
        if (!ListUtil.isEmpty(deviceNumList) && !ListUtil.isEmpty(inClassMemberList)
                && deviceNumList.size() == inClassMemberList.size() && currMills - lastMills >= 300) {
            lastMills = currMills;
            int hr = getHrData(bytes2HexStr(scanRecord));
            if (hr >= 0) {
//                Member member = inClassMemberList.get(deviceNumList.indexOf(braceletNo));
                Member member = inClassMemberList.get(0);// ceshi
                long timestamp = System.currentTimeMillis();
                if (member.getFirstTimestamp() == 0) {
                    member.setFirstTimestamp(timestamp);
                    member.setLastTimestamp(timestamp);
                } else {
                    member.setLastTimestamp(member.getCurrentTimestamp());
                }
                member.setLastTimestamp(member.getCurrentTimestamp());
                member.setCurrentTimestamp(timestamp);
                member.setHr(hr);
                member.setCal(getCal(member, hr));
                member.setBmi(getBMI(member));
                member.setTizhi(getTizhi(member));
//                Log.E(TAG, member.toString());
                if (onReceiveMemberListener != null) {
                    onReceiveMemberListener.onReceiveMember(member);
                }
                EventBus.getDefault().post(member);
            }
        }
    }

    //过滤出手环id
    private String getBraceletNo(BluetoothDevice device) {
        String ret = "";
        if (device != null && !TextUtils.isEmpty(device.getName())) {
            String deviceName = device.getName();
            int index1 = deviceName.indexOf("Take-");
            if (index1 != -1) {
                ret = deviceName.substring(index1 + 5);
            }
            if (TextUtils.isEmpty(ret)) {
                int index2 = deviceName.indexOf("TAKE ");
                if (index2 != -1) {
                    ret = deviceName.substring(index2 + 5);
                }
            }
        }
        return ret;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("===================== :onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        System.out.println("===================== :onDestroy");
        bleServiceBinder = null;
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.stopLeScan(scanCallback);
        }
        //释放资源
        if (bleDataHandler != null) {
            bleDataHandler.removeCallbacksAndMessages(null);
            bleDataThread.getLooper().quit();
            bleDataHandler = null;
        }
        if (bleThread != null && bleThread.isAlive()) {
            bleThread.interrupt();
            bleThread = null;
        }
        if (bleStateReceiver != null) {
            unregisterReceiver(bleStateReceiver);
            bleStateReceiver = null;
        }
        inClassMemberList.clear();
        inClassMemberList = null;
        deviceNumList.clear();
        deviceNumList = null;
        stopSelf();
        super.onDestroy();
    }
}
