package com.example.yewulibs_demo1_appindex.services;

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

import com.example.bizyewu2.presenter.HMobIDPresenter;
import com.example.bizyewu2.view.HMobIDView;
import com.example.slbota.OTAServicesBg;
import com.geek.libutils.app.MyLogUtil;
import com.mob.pushsdk.MobPush;
import com.mob.pushsdk.MobPushCustomMessage;
import com.mob.pushsdk.MobPushNotifyMessage;
import com.mob.pushsdk.MobPushReceiver;

public class MOBIDservices extends Service implements HMobIDView {

    // test
    private HMobIDPresenter presenter_mobid;
    private Handler handler;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MsgBinder();
    }

    @Override
    public void OnMobIDSuccess(String bean) {
        MyLogUtil.e("MobPush推送成功");
    }

    @Override
    public void OnMobIDNodata(String bean) {

    }

    @Override
    public void OnMobIDFail(String msg) {

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
                    System.out.println("MobPush Callback Data:" + msg.obj);
                    presenter_mobid.get_mob_id();
                }
                return false;
            }
        });


    }

    public static final int HUYAN_MANAGE_NOTIFICATION_ID = 1001611;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent it = new Intent(this, MOBIDServicesBg.class);
        it.putExtra(OTAServicesBg.EXTRA_NOTIFICATION_ID, HUYAN_MANAGE_NOTIFICATION_ID);
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
