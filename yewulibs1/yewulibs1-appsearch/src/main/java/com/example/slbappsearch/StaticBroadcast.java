package com.example.slbappsearch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * @author: 王硕风
 * @date: 2021.6.11 0:27
 * @Description:
 */
public class StaticBroadcast extends BroadcastReceiver {
    private final static String ACTION = "com.example.receiver.action";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "我是插件，收到宿主消息，静态广播", Toast.LENGTH_SHORT).show();

        context.sendBroadcast(new Intent(ACTION));
    }
}
