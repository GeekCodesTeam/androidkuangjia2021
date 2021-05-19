package com.example.slbappjpush.othermsg;

import android.content.Intent;
import android.util.Log;

import com.blankj.utilcode.util.AppUtils;
import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.RemoteMessage;

import cn.jpush.android.service.PluginHuaweiPlatformsService;

public class MyHWPushService extends HmsMessageService {
    private static final String TAG = MyHWPushService.class.toString();

    final PluginHuaweiPlatformsService service = new PluginHuaweiPlatformsService();

    @Override
    public void onNewToken(String s) {
        service.onNewToken(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage message) {
        service.onMessageReceived(message);
        Log.i(TAG, "onMessageReceived is called");

        // 判断消息是否为空
        if (message == null) {
            Log.e(TAG, "Received message entity is null!");
            return;
        }
        //打开自定义的Activity
        Intent i = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ActYewuList1");
//        i.putExtras(bundle);
        //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        // 获取消息内容
        Log.i(TAG, "get Data: " + message.getData()
                + "\n getFrom: " + message.getFrom()
                + "\n getTo: " + message.getTo()
                + "\n getMessageId: " + message.getMessageId()
                + "\n getSendTime: " + message.getSentTime()
                + "\n getDataMap: " + message.getDataOfMap()
                + "\n getMessageType: " + message.getMessageType()
                + "\n getTtl: " + message.getTtl()
                + "\n getToken: " + message.getToken());

        Boolean judgeWhetherIn10s = false;
        // 如果消息在10秒内没有处理，需要您自己创建新任务处理
        if (judgeWhetherIn10s) {
            startWorkManagerJob(message);
        } else {
            // 10秒内处理消息
            processWithin10s(message);
        }
    }

    private void startWorkManagerJob(RemoteMessage message) {
        Log.d(TAG, "Start new job processing.");
    }
    private void processWithin10s(RemoteMessage message) {
        Log.d(TAG, "Processing now.");
    }

    @Override
    public void onMessageSent(String s) {
        service.onMessageSent(s);
    }

    @Override
    public void onSendError(String s, Exception e) {
        service.onSendError(s, e);
    }

    @Override
    public void onDeletedMessages() {
        service.onDeletedMessages();
    }
}