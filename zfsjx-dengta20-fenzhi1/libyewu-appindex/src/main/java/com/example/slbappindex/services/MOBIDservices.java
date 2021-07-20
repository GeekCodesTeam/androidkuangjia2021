package com.example.slbappindex.services;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bizyewu2.bean.HMobid2Bean;
import com.example.bizyewu2.presenter.HMobID2Presenter;
import com.example.bizyewu2.presenter.HMobIDPresenter;
import com.example.bizyewu2.view.HMobID2View;
import com.example.bizyewu2.view.HMobIDView;
import com.geek.libutils.app.LocalBroadcastManagers;
import com.geek.libutils.app.MyLogUtil;
import com.mob.pushsdk.MobPush;
import com.mob.pushsdk.MobPushCustomMessage;
import com.mob.pushsdk.MobPushNotifyMessage;
import com.mob.pushsdk.MobPushReceiver;

import java.util.List;

public class MOBIDservices extends Service implements HMobIDView, HMobID2View {

    // test
    private HMobIDPresenter presenter_mobid;
    private HMobID2Presenter presenter_mobid2;
    private Handler handler;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MsgBinder();
    }

    @Override
    public void OnMobIDSuccess(String bean) {
        MyLogUtil.e("MobPush", "后台接口测试推送成功");

    }

    @Override
    public void OnMobIDNodata(String bean) {

    }

    @Override
    public void OnMobIDFail(String msg) {

    }

    @Override
    public void OnMobID2Success(HMobid2Bean bean) {
        MyLogUtil.e("MobPush推送成功2");
        //TODO 发送广播bufen
        Intent msgIntent = new Intent();
        msgIntent.setAction("ShouyeActivity");
        msgIntent.putExtra("mobid", bean.totalCount);
        LocalBroadcastManagers.getInstance(this).sendBroadcast(msgIntent);
    }

    @Override
    public void OnMobID2Nodata(String bean) {

    }

    @Override
    public void OnMobID2Fail(String msg) {

    }

    @Override
    public String getIdentifier() {
        return System.currentTimeMillis() + "";
    }


    public class MsgBinder extends Binder {
        public MOBIDservices getService() {
            return MOBIDservices.this;
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        // test
        presenter_mobid = new HMobIDPresenter();
        presenter_mobid.onCreate(this);
        presenter_mobid2 = new HMobID2Presenter();
        presenter_mobid2.onCreate(this);
//        MobSDK.init(this);
//        //防止多进程注册多次  可以在MainActivity或者其他页面注册MobPushReceiver
//        String processName = getProcessName(this);
//        MobPush.getRegistrationId(new MobPushCallback<String>() {
//
//            @Override
//            public void onCallback(String rid) {
//                System.out.println("MobPush->RegistrationId:" + rid);
//                SPUtils.getInstance().put("MOBID", rid);
//                // test
//                presenter_mobid.get_mob_id();
//            }
//        });
        // test
        presenter_mobid.get_mob_id();
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
                System.out.println("MobPush onNotifyMessageOpenedReceive:" + message.getContent());
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
            public boolean handleMessage(@NonNull Message msg) {
                if (msg.what == 1) {
                    System.out.println("MobPush Callback Data1:" + msg.obj.toString());
                    //
                    presenter_mobid2.get_mob_id2(1, 1, "1", "APP_MOB");
                }
                if (msg.what == 2) {
                    System.out.println("MobPush Callback Data2:" + msg.obj.toString());
//                    Intent intent2 = ActivityUtils.getTopActivity().getIntent();
//                    JSONArray jsonArray =  MobPushUtils.parseMainPluginPushIntent(intent2);
//                    System.out.println("-------MobPush------JsonPushData打印查看："+jsonArray);
//                    try {
//                        JSONObject jsonObject = new JSONObject(msg.obj.toString());
////                        GsonUtils.fromJson();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }

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
        if (presenter_mobid != null) {
            presenter_mobid.onDestory();
        }
        super.onDestroy();

    }

}
