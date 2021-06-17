package com.example.libappservices;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.text.TextUtils;

import com.geek.libutils.app.MyLogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MobUpdateServices extends Service {

    public static final int UPDATA_MANAGE_NOTIFICATION_ID = 1001611;
    public static final String time = "Updataservices_time";
    public static final String ActYewu1 = "刷新滚动广播Activity";//

    @Override
    public IBinder onBind(Intent intent) {
        return new MsgBinder();
    }

    public class MsgBinder extends Binder {
        public MobUpdateServices getService() {
            return MobUpdateServices.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        mMessageReceiver = new MessageReceiverIndex();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(ActYewu1);
        MobLocalBroadcastManagers.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    //
    private MessageReceiverIndex mMessageReceiver;

    public static class MessageReceiverIndex extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (ActYewu1.equals(intent.getAction())) {
                    if (intent != null && !intent.getStringExtra(time).isEmpty()) {

                    }
                }
            } catch (Exception ignored) {
            }
        }
    }

    // 传值给后台统计绘本阅读时间
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void updateDate(final Object content) {
        if (content != null && TextUtils.isEmpty((CharSequence) content)) {
            // 接口bufen
//            end_video(UserMgr.getUserId(), timer.getChannelId(), timer.getSessionId(), timeStamp2Date(System.currentTimeMillis() + ""));
            MyLogUtil.e("ssssssss", System.currentTimeMillis() + "");
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        startForeground(UPDATA_MANAGE_NOTIFICATION_ID, new Notification());
//        startForeground(UPDATA_MANAGE_NOTIFICATION_ID, UpdataCommonservicesBg.she_notifichanged());
        Intent it = new Intent(this, MobUpdateServicesBg.class);
        it.putExtra(MobUpdateServicesBg.EXTRA_NOTIFICATION_ID, UPDATA_MANAGE_NOTIFICATION_ID);
        startService(it);

        if (intent != null && !intent.getStringExtra(time).isEmpty()) {

        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        MobLocalBroadcastManagers.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();

    }

}
