package com.haier.wine_commen.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.shining.libutils.utilslib.app.App;
import com.haier.wine_commen.R;
import com.haier.wine_commen.util.HandlePushMessage;
import com.haier.wine_commen.util.SP;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by centling on 2016/8/18.
 */
public class MyJPushBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            System.out.println(context.getString(R.string.libraries_wine_commen_jpush_reciver) + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            String msg = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            //处理接收的消息
            HandlePushMessage handlePushMessage = new HandlePushMessage(context);
            handlePushMessage.handleMessage(msg);
            // 自定义消息不会展示在通知栏，完全要开发者写代码去处理            Logger.t("jiguang-->").d(bundle.getString(JPushInterface.EXTRA_MESSAGE));
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            System.out.println(context.getString(R.string.libraries_wine_commen_jpush_reciver_success));
            // 在这里可以做些统计，或者做些其他工作
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
//            System.out.println("用户点击打开了通知");
            // 在这里可以自己写代码去定义用户点击后的行为
            //  Intent i = new Intent(context, JpushTesyt.class);  //自定义打开的界面
            //  i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //context.startActivity(i);
        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.e("setJpush:connected:", connected + "");
            if(connected){
                SP.put(App.get(), "jpushTag", true);
            }else{
                SP.put(App.get(), "jpushTag", false);
            }
        }

    }

}