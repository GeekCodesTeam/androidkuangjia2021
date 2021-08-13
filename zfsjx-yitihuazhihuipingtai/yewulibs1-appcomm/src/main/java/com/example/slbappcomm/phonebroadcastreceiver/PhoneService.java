package com.example.slbappcomm.phonebroadcastreceiver;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.blankj.utilcode.util.ServiceUtils;
import com.example.slbappcomm.utils.CommonUtils;
import com.geek.libutils.app.LocalBroadcastManagers;

/**
 * Description:
 * Author     : qcl
 * Date       : 17/11/9
 */

public class PhoneService extends Service {
    private boolean isChangeToPause = false;
    // 电话管理器
    private TelephonyManager tm;
    // 监听器对象
    private MyListener listener;
    private AudioManager ams = null;//音频管理器
    public static final int UPDATA_MANAGE_NOTIFICATION_ID = 1001611;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        startForeground(UPDATA_MANAGE_NOTIFICATION_ID, PhoneServiceBg.she_notifichanged());
//
        Intent it = new Intent(this, PhoneServiceBg.class);
        it.putExtra(PhoneServiceBg.EXTRA_NOTIFICATION_ID, UPDATA_MANAGE_NOTIFICATION_ID);
        startService(it);
        return START_STICKY;
    }

    /**
     * 服务创建的时候调用的方法
     */
    @Override
    public void onCreate() {
        // 后台监听电话的呼叫状态。
        // 得到电话管理器
        tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        listener = new MyListener();
        tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
        initAudio();
        super.onCreate();
    }

    //微信，qq通话监听
    private void initAudio() {
        ams = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        ams.getMode();//这里getmode返回值为3时代表，接通qq或者微信电话
        ams.requestAudioFocus(mAudioFocusListener, 1, 1);
    }

    private class MyListener extends PhoneStateListener {

        // 当电话的呼叫状态发生变化的时候调用的方法
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            Log.d("qcl111", "state" + state);
            try {
                switch (state) {
                    case TelephonyManager.CALL_STATE_IDLE://空闲状态。
                        //继续播放音乐
                        Log.v("myService", "空闲状态");
//                        setplay_pause_music();
                        break;
                    case TelephonyManager.CALL_STATE_RINGING://铃响状态。
                        //暂停播放音乐
                        Log.v("myService", "铃响状态");
//                        setplay_pause_music();
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK://通话状态

                        Log.v("myService", "通话状态");
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setplay_pause_music(int focusChange) {
        if (ServiceUtils.isServiceRunning("com.example.slbappreadbook.RbMusicPlayerService")) {
            Intent msgIntent = new Intent();
            msgIntent.putExtra("focusChange",focusChange);
            msgIntent.setAction(CommonUtils.RB_broadcastreceiver);
            LocalBroadcastManagers.getInstance(getApplicationContext()).sendBroadcast(msgIntent);
        }
        if (ServiceUtils.isServiceRunning("com.example.slbappcomm.playermusic.ListenMusicPlayerService")) {
            Intent msgIntent = new Intent();
            msgIntent.putExtra("focusChange",focusChange);
            msgIntent.setAction(CommonUtils.LB_broadcastreceiver);
            LocalBroadcastManagers.getInstance(getApplicationContext()).sendBroadcast(msgIntent);

        }
    }

    private AudioManager.OnAudioFocusChangeListener mAudioFocusListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            Log.d("qcl111", "focusChange----------" + focusChange);// -2 暂停  1 继续
//            setplay_pause_music();
            if (focusChange == 1) {//视频语音挂断状态
                if (isChangeToPause) {
//                    GlobalAudioManager.getInstance().playResume();
                    setplay_pause_music(focusChange);
                    Log.d("qcl111", "playResume()" + focusChange);
                }
            } else {//微信或者qq语音视频接通状态
//                if (GlobalAudioManager.getInstance().isPlaying()) {
                isChangeToPause = true;
                setplay_pause_music(focusChange);
//                    GlobalAudioManager.getInstance().playPause();
                Log.d("qcl111", "playPause()" + focusChange);
//                }
            }
        }
    };

    /**
     * 服务销毁的时候调用的方法
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        // 取消电话的监听,采取线程守护的方法，当一个服务关闭后，开启另外一个服务，除非你很快把两个服务同时关闭才能完成
//        Intent i = new Intent(this, PhoneService2.class);
//        startService(i);
        tm.listen(listener, PhoneStateListener.LISTEN_NONE);
        listener = null;
        ams.abandonAudioFocus(mAudioFocusListener);
    }

}