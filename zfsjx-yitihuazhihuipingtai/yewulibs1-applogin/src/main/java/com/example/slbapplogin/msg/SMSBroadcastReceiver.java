package com.example.slbapplogin.msg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.geek.libutils.app.MyLogUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 程龙 on 2018/9/27.
 */

public class SMSBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "SMSBroadcastReceiver";
    private OnReceiveSMSListener mOnReceiveSMSListener;
    public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage msg = null;
        String smsContent = "";
        if (null != bundle) {
            Object[] smsObj = (Object[]) bundle.get("pdus");
            String regEx = "[^0-9]";
            Pattern p = Pattern.compile(regEx);
            for (Object object : smsObj) {
                msg = SmsMessage.createFromPdu((byte[]) object);
                Date date = new Date(msg.getTimestampMillis());// 时间
                SimpleDateFormat format = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");
                String receiveTime = format.format(date);
                MyLogUtil.i("接收sms", "address:" + msg.getOriginatingAddress()
                        + "   body:" + msg.getDisplayMessageBody() + "  time:"
                        + msg.getTimestampMillis());
                Matcher m = p.matcher(msg.getDisplayMessageBody());
                smsContent = m.replaceAll("").trim();
//                textView.append("接收到短信来自：\n"+msg.getOriginatingAddress()+"\n内容：\n"+msg.getDisplayMessageBody()+"\n时间：\n+"+ receiveTime);
            }
            if (mOnReceiveSMSListener != null) {
                mOnReceiveSMSListener.onReceived(smsContent);
            }
        }

    }

    /**
     * 回调接口
     */
    public interface OnReceiveSMSListener {
        void onReceived(String message);
    }


    public void setOnReceiveSMSListener(OnReceiveSMSListener onReceiveSMSListener) {
        mOnReceiveSMSListener = onReceiveSMSListener;
    }
}