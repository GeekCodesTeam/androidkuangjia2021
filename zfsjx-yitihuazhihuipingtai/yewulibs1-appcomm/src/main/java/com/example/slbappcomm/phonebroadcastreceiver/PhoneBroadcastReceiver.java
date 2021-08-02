package com.example.slbappcomm.phonebroadcastreceiver;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import com.geek.libutils.app.MyLogUtil;

public class PhoneBroadcastReceiver extends BroadcastReceiver {

    private String mIncomingNumber;

    @Override
    public void onReceive(Context context, Intent intent) {
        MyLogUtil.e("收到电话广播:{}", intent == null ? "null" : intent.getAction());
        if (intent == null || intent.getAction() == null) {
            return;
        }
        // 如果是拨打电话
        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            //拨打电话会优先,收到此广播. 再收到 android.intent.action.PHONE_STATE 的 TelephonyManager.CALL_STATE_OFFHOOK 状态广播;

            String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            MyLogUtil.e("call OUT:{}", phoneNumber);//获取拨打的手机号码
        } else {
            // 如果是来电
            TelephonyManager tManager = (TelephonyManager) context
                    .getSystemService(Service.TELEPHONY_SERVICE);
            //电话的状态
            switch (tManager.getCallState()) {
                case TelephonyManager.CALL_STATE_RINGING:
                    //等待接听状态
                    mIncomingNumber = intent.getStringExtra("incoming_number");
                    MyLogUtil.e("RINGING :" + mIncomingNumber);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    //接听状态
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    //挂断状态
                    break;
            }
        }
    }
}
