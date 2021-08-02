package com.example.libappmob;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

import com.mob.pushsdk.MobPush;
import com.mob.pushsdk.MobPushCustomMessage;
import com.mob.pushsdk.MobPushNotifyMessage;
import com.mob.pushsdk.MobPushReceiver;

import java.util.List;

public class MOBIDservices extends Service {

    private Handler handler;

    @Override
    public IBinder onBind(Intent intent) {
        return new MsgBinder();
    }

    public class MsgBinder extends Binder {
        public MOBIDservices getService() {
            return MOBIDservices.this;
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        MobPush.addPushReceiver(new MobPushReceiver() {
            @Override
            public void onCustomMessageReceive(Context context, MobPushCustomMessage message) {
                //接收自定义消息(透传)
                System.out.println("MobPush onCustomMessageReceive:" + message.toString());
            }

            @Override
            public void onNotifyMessageReceive(Context context, MobPushNotifyMessage message) {
                //接收通知消
                System.out.println("MobPush onNotifyMessageReceive:" + message.toString());
                Message msg = new Message();
                msg.what = 1;
                msg.obj = "Message Receive:" + message.toString();
                handler.sendMessage(msg);

            }

            @Override
            public void onNotifyMessageOpenedReceive(Context context, MobPushNotifyMessage message) {
                //接收通知消息被点击事件
                System.out.println("MobPush onNotifyMessageOpenedReceive:" + message.toString());
                Message msg = new Message();
                msg.what = 2;
                msg.obj = "Click Message:" + message.toString();
                handler.sendMessage(msg);
            }

            @Override
            public void onTagsCallback(Context context, String[] tags, int operation, int errorCode) {
                //接收tags的增改删查操作
                System.out.println("MobPush onTagsCallback:" + operation + "  " + errorCode);
            }

            @Override
            public void onAliasCallback(Context context, String alias, int operation, int errorCode) {
                //接收alias的增改删查操作
                System.out.println("MobPush onAliasCallback:" + alias + "  " + operation + "  " + errorCode);
            }
        });

        handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == 1) {
                    System.out.println("MobPush Callback Data1:" + msg.obj);
//                    presenter_mobid2.get_mob_id2(1, 1, "1", "DT");
                }
                if (msg.what == 2) {
                    System.out.println("MobPush Callback Data2:" + msg.obj);
                }
                // activity获取信息
//                Intent intent2 = ActivityUtils.getTopActivity().getIntent();
//                Uri uri = intent2.getData();
//                if (intent2 != null) {
//                    System.out.println("MobPush linkone intent---" + intent2.toUri(Intent.URI_INTENT_SCHEME));
//                }
//                StringBuilder sb = new StringBuilder();
//                if (uri != null) {
//                    sb.append(" scheme:" + uri.getScheme() + "\n");
//                    sb.append(" host:" + uri.getHost() + "\n");
//                    sb.append(" port:" + uri.getPort() + "\n");
//                    sb.append(" query:" + uri.getQuery() + "\n");
//                }
//
//                //获取link界面传输的数据
//                JSONArray jsonArray = MobPushUtils.parseSchemePluginPushIntent(intent2);
//                if (jsonArray != null) {
//                    sb.append("\n" + "bundle toString :" + jsonArray.toString());
//                }
                //通过scheme跳转详情页面可选择此方式
//                    JSONArray var = new JSONArray();
//                    var = MobPushUtils.parseSchemePluginPushIntent(intent2);
//                    MyLogUtil.e("MobPushsssssss", var.toString());
//                //跳转首页可选择此方式
//                JSONArray var2 = new JSONArray();
//                var2 = MobPushUtils.parseMainPluginPushIntent(intent2);
                return false;
            }
        });

    }

    private String getProcessName(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo proInfo : runningApps) {
            if (proInfo.pid == android.os.Process.myPid()) {
                if (proInfo.processName != null) {
                    return proInfo.processName;
                }
            }
        }
        return null;
    }


    public static final int HUYAN_MANAGE_NOTIFICATION_ID = 1001611;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent it = new Intent(this, MOBIDServicesBg.class);
        it.putExtra(MOBIDServicesBg.EXTRA_NOTIFICATION_ID, HUYAN_MANAGE_NOTIFICATION_ID);
        startService(it);
//        if (intent != null && !TextUtils.isEmpty(intent.getAction())) {
//            String action = intent.getAction();
//            if (action.equals(HUIBEN_READINGTIME_ACTION)) {
//                String id2 = intent.getStringExtra(id_zong);
//                String id1 = intent.getStringExtra(id);
//                String type1 = intent.getStringExtra(type);
//                String sourceType1 = intent.getStringExtra(sourceType);
//                String time1 = intent.getStringExtra(time);
//                set_timerTo(id1, id2, type1, sourceType1, time1);
//            } else if (action.equals(LATEST_MEDAL_ACTION)) {
//                if (BaseAppManager.getInstance().top() != null) {
//                    if (TextUtils.equals("com.example.slbappindex.IndexMainActivity", BaseAppManager.getInstance().top().getClass().getName())) {
////                        getLatestMedalPresenter.getMyMedalDetailCommData();
//                        sIndexAdvertisingPresenter.getIndexAdvertisingData();
//                    }
//                }
//            }
//        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

}
