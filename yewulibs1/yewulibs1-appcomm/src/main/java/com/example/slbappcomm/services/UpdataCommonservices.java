//package com.example.slbappcomm.services;
//
//import android.app.Service;
//import android.content.Intent;
//import android.os.Binder;
//import android.os.IBinder;
//import android.text.TextUtils;
//import androidx.annotation.Nullable;
//import com.blankj.utilcode.util.SPUtils;
//import com.blankj.utilcode.util.ServiceUtils;
//import com.example.biz3slbappcomm.bean.SIndexAdvertisingBean;
//import com.example.biz3slbappcomm.bean.SMyMedalDetailBean;
//import com.example.biz3slbappcomm.presenter.SIndexAdvertisingPresenter;
//import com.example.biz3slbappcomm.presenter.SMyMedalDetailCommPresenter;
//import com.example.biz3slbappcomm.presenter.SUpdateTimePresenter;
//import com.example.biz3slbappcomm.view.SIndexAdvertisingView;
//import com.example.biz3slbappcomm.view.SMyMedalDetailCommView;
//import com.example.biz3slbappcomm.view.SUpdateTimeView;
//import com.geek.libutils.app.MyLogUtil;
//import com.geek.libutils.app.BaseAppManager;
//import com.haier.cellarette.baselibrary.yanzheng.LocalBroadcastManagers;
//
//public class UpdataCommonservices extends Service implements SUpdateTimeView, SMyMedalDetailCommView, SIndexAdvertisingView {
//
//    // 统计页面
//    public static final String TAG_ADCOMMON_ID = "id1";// 广告id
//    public static final String LB_broadcastreceiver = "LB_broadcastreceiver";// 监听第三方来电音乐变化
//    public static final String TAG_MEDALID1 = "MedalId";// 勋章id
//    public static final String HUIBEN_READINGTIME_ACTION = "HUIBEN_READINGTIME_ACTION";
//    public static final String LATEST_MEDAL_ACTION = "LATEST_MEDAL_ACTION";
//    public static final String HUIBEN_READINGTIME = "统计绘本阅读页面时长";
//    public static final String id_zong = "Updataservices_id_zong";
//    public static final String id = "Updataservices_id";
//    public static final String type = "Updataservices_type";
//    public static final String type1 = "start";
//    public static final String type2 = "end";
//    public static final String sourceType = "Updataservices_sourcetype";//
//    public static final String sourceType1 = "audioItem";// 听书
//    public static final String sourceType2 = "bookItem";// 绘本
//    public static final String time = "Updataservices_time";
//    public static final int UPDATA_MANAGE_NOTIFICATION_ID = 1001611;
//    private SUpdateTimePresenter presenter;
//    private SMyMedalDetailCommPresenter getLatestMedalPresenter;
//    private SIndexAdvertisingPresenter sIndexAdvertisingPresenter;
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return new MsgBinder();
//    }
//
//    @Override
//    public void OnUpdateTimeSuccess(String s) {
//        MyLogUtil.e("OnReadTimeSuccess", s);
////        Toasty.normal(BaseApp.get(), "").show();
//    }
//
//    @Override
//    public void OnUpdateTimeNodata(String s) {
//        MyLogUtil.e("OnReadTimeNodata", s);
////        Toasty.normal(BaseApp.get(), s).show();
//    }
//
//    @Override
//    public void OnUpdateTimeFail(String s) {
//        MyLogUtil.e("OnReadTimeFail", s);
////        Toasty.normal(BaseApp.get(), s).show();
//    }
//
//    @Override
//    public String getIdentifier() {
//        return System.currentTimeMillis() + "";
//    }
//
//    @Override
//    public void OnMyMedalDetailCommSuccess(SMyMedalDetailBean sMyMedalDetailBean) {
////        Class<? extends Activity> klass = null;
////        try {
////            klass = (Class<? extends Activity>) Class.forName("com.example.slbappindex.IndexMainActivity");
////        } catch (ClassNotFoundException e) {
////            e.printStackTrace();
////        }
//        if (!TextUtils.isEmpty(sMyMedalDetailBean.getDetails().getMedalId()) && !TextUtils.isEmpty(sMyMedalDetailBean.getDetails().getTitle())) {
//            // 如果在听书先关了
//            if (ServiceUtils.isServiceRunning("com.example.slbappcomm.playermusic.ListenMusicPlayerService")) {
//                // old
////                SPUtils.getInstance().put(CommonUtils.LISTENBOOK_TAG1, false);
////                stopService(new Intent(this, ListenMusicPlayerService.class));
//                // new
//                Intent msgIntent = new Intent();
//                msgIntent.putExtra("focusChange", -2);
//                msgIntent.setAction(LB_broadcastreceiver);
//                LocalBroadcastManagers.getInstance(getApplicationContext()).sendBroadcast(msgIntent);
//            }
//            if (BaseAppManager.getInstance().top() != null) {
//                if (TextUtils.equals("com.example.slbappindex.IndexMainActivity", BaseAppManager.getInstance().top().getClass().getName())) {
//                    Intent intent = new Intent("hs.act.slbapp.MedalPopActivity");
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////            intent.putExtra(CommonUtils.TAG_MEDALBEAN1, sMyMedalDetailBean.getDetails());
//                    intent.putExtra(TAG_MEDALID1, sMyMedalDetailBean.getDetails().getMedalId());
//                    startActivity(intent);
//                }
//            }
//        }
//    }
//
//    @Override
//    public void OnMyMedalDetailCommNodata(String s) {
//    }
//
//    @Override
//    public void OnMyMedalDetailCommFail(String s) {
//    }
//
//    @Override
//    public void OnIndexAdvertisingSuccess(SIndexAdvertisingBean sIndexAdvertisingBean) {
//        // 广告位
////        if (sIndexAdvertisingBean == null) {
////            return;
////        }
//        if (BaseAppManager.getInstance().top() != null) {
//            if (TextUtils.equals("com.example.slbappindex.IndexMainActivity", BaseAppManager.getInstance().top().getClass().getName())) {
//                String id1 = sIndexAdvertisingBean.getMiniActivityView().getId();
//                String url1 = sIndexAdvertisingBean.getMiniActivityView().getHios();
//                String img1 = sIndexAdvertisingBean.getMiniActivityView().getBgImg();
//                if (TextUtils.isEmpty(id1)) {
//                    getLatestMedalPresenter.getMyMedalDetailCommData();
//                    return;
//                }
//                if (TextUtils.equals(SPUtils.getInstance().getString(CommonUtils.TAG_ADCOMMON_ID, ""), id1)) {
//                    getLatestMedalPresenter.getMyMedalDetailCommData();
//                    return;
//                }
//                Intent intent = new Intent("hs.act.slbapp.AdCommImgActivity");
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("id1", id1);
//                intent.putExtra("url1", url1);
//                intent.putExtra("img1", img1);
//                startActivity(intent);
//                SPUtils.getInstance().put(TAG_ADCOMMON_ID, id1);
//                getLatestMedalPresenter.getMyMedalDetailCommData();
//
//            }
//        }
//    }
//
//    @Override
//    public void OnIndexAdvertisingNodata(String s) {
//        getLatestMedalPresenter.getMyMedalDetailCommData();
//
//    }
//
//    @Override
//    public void OnIndexAdvertisingFail(String s) {
//        getLatestMedalPresenter.getMyMedalDetailCommData();
//
//    }
//
//    public class MsgBinder extends Binder {
//        public UpdataCommonservices getService() {
//            return UpdataCommonservices.this;
//        }
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        presenter = new SUpdateTimePresenter();
//        presenter.onCreate(this);
//
//        getLatestMedalPresenter = new SMyMedalDetailCommPresenter();
//        getLatestMedalPresenter.onCreate(this);
//        sIndexAdvertisingPresenter = new SIndexAdvertisingPresenter();
//        sIndexAdvertisingPresenter.onCreate(this);
//
//    }
//
//    // 传值给后台统计绘本阅读时间
//    private void set_timerTo(String itemId, String pId, String type, String sourceType, String time) {
//        presenter.getUpdateTimeData(itemId, pId, type, sourceType, time);
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
////        startForeground(UPDATA_MANAGE_NOTIFICATION_ID, new Notification());
////        startForeground(UPDATA_MANAGE_NOTIFICATION_ID, UpdataCommonservicesBg.she_notifichanged());
////        Intent it = new Intent(this, UpdataCommonservicesBg.class);
////        it.putExtra(UpdataCommonservicesBg.EXTRA_NOTIFICATION_ID, UPDATA_MANAGE_NOTIFICATION_ID);
////        startService(it);
//
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
//
//        return START_STICKY;
//    }
//
//    @Override
//    public void onDestroy() {
//        presenter.onDestory();
//        getLatestMedalPresenter.onDestory();
//        sIndexAdvertisingPresenter.onDestory();
//        super.onDestroy();
//
//    }
//
//}
