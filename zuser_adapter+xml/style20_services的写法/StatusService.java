package com.haier.cellarette.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import com.example.shining.libutils.utilslib.app.App;
import com.example.shining.libutils.utilslib.app.MyLogUtil;
import com.haier.application.MyApplication;
import com.haier.cellarette.bean.LocationInfoBody;
import com.haier.cellarette.bean.LocationResultBean;
import com.haier.cellarette.bean.StatusRequestBody;
import com.haier.cellarette.bean.StatusResponseBody;
import com.haier.cellarette.model.UploadStatusModel;
import com.haier.cellarette.powerctl.PowerStateUpload;
import com.haier.controler.api.ProxyObject;
import com.haier.controler.bean.ProcessDataBean;
import com.haier.controler.serive.ControlerService;
import com.haier.location.LocationUtil;
import com.haier.location.bean.HaierLocationBean;
import com.haier.wine_commen.util.GetMac;
import com.haier.wine_commen.util.SP;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * 状态上报的服务
 * Created by Shorr on 2017/1/20.
 */

public class StatusService extends Service  {
    private static final String TAG = "StatusService";
    private static final int DEALY_TIME = 20;  //定时延时间隔 20s
//    private static final int DAY_SECOND = 60;  //一天的秒数
    private static final int DAY_SECOND = 86400;  //一天的秒数

    private UploadStatusModel model;
    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;  //定时器任务
    private CompositeSubscription compositeSubscription;  //订阅的集合
    private Subscriber subscriber;
    private StatusRequestBody statusRecordBody;  //状态记录实体
    private boolean uploadSuccess = false;  //上传成功
    private PowerStateUpload stateUpload;
    private int autoUploadDelay = 0;  //状态稳定时上传间隔（单位DEALY_TIME）
    String versionName = "";
    int versionCode = 0;
    Context mContext;
    private String requestId;
    private ProxyObject proxyObject;
    @Override
    public IBinder onBind(Intent intent) {
        return new ServiceBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        model = new UploadStatusModel();
        compositeSubscription = new CompositeSubscription();
        statusRecordBody = new StatusRequestBody();
        stateUpload = PowerStateUpload.getInstance();
        scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
        scheduledThreadPoolExecutor.scheduleAtFixedRate(timerRunnable, DEALY_TIME, DEALY_TIME, TimeUnit.SECONDS);
        MyLogUtil.d(TAG, "开启上传状态服务");

        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = info.versionName;
            versionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        bindService(new Intent(this, ControlerService.class), connection, Context.BIND_AUTO_CREATE);
    }

    ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            proxyObject = ((ControlerService.MyBind) service).getProxy();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            MyLogUtil.d(TAG, "准备上传酒柜状态");
            //自动计时间隔递增
            if (autoUploadDelay < Integer.MAX_VALUE)
                autoUploadDelay++;

            StatusRequestBody body = new StatusRequestBody();
//            WineStatus wineStatus = stateUpload.getmWineStatus();
            ProcessDataBean wineStatus = proxyObject.getWineState();

            boolean door = wineStatus.isColdOn();
            boolean light = wineStatus.isLightOn();
            int innerT = wineStatus.getCenColdShowTemp();
            int outerT = wineStatus.getCenEnvShowTemp();
            int settingT = wineStatus.getCenColdSetTemp();

            HaierLocationBean bean = LocationUtil.getLastLocationBeanAndGoLocation(MyApplication.mContext);

            Boolean tempDoor = (new Boolean(door).equals(statusRecordBody.getDoor())) ? null : new Boolean(door);
            Boolean tempLight = (new Boolean(light).equals(statusRecordBody.getLight())) ? null : new Boolean(light);
            Integer tempInnerT = (new Integer(innerT).equals(statusRecordBody.getInnerT())) ? null : new Integer(innerT);
            Integer tempOuterT = (new Integer(outerT).equals(statusRecordBody.getOuterT())) ? null : new Integer(outerT);

            body.setMac(GetMac.getLocalMacAddress());
            body.setDoor(door);
            body.setLight(light);
            body.setInnerT(innerT);
            body.setOuterT(outerT);
            body.setSettingT(settingT);
//            body.setAddress("--");
            body.setAddress(bean.getAddress());
            body.setSysName(versionName);
            body.setSysCode(versionCode + "");
            body.setRomName(Build.DISPLAY + "");
            body.setAdCode(bean.getAdCode());
            body.setGcjLng(String.valueOf(bean.getLongitude()));
            body.setGcjLat(String.valueOf(bean.getLatitude()));

//            上传推送过来的requestId ,如果没有则不用传
            requestId = SP.get(App.get(), "requestId", "");
            boolean is = !requestId.equals("");
            if (is) {
                MyLogUtil.d(TAG, "requestId----------:"+ requestId);
                body.setRequestId(requestId);
                SP.put(App.get(),"requestId","");
            }

            //每天上传逻辑
            if (tempDoor == null && tempLight == null && tempInnerT == null && tempOuterT == null) {
                //稳定状态超过一天主动上传一次状态
                MyLogUtil.d(TAG, "autoUploadDelay = " + autoUploadDelay);
                if (autoUploadDelay * DEALY_TIME >= DAY_SECOND) {
                    //网络上传状态
                    uploadStatus(body, true);
                    MyLogUtil.d(TAG, "上传酒柜状态");
                } else {
                    MyLogUtil.d(TAG, "上传酒柜状态返回");
                    return;
                }
            }
            //状态变化上传
            if (tempDoor != null && tempLight != null && tempInnerT != null || Math.abs(outerT - statusRecordBody.getOuterT()) >= 3) {
                //网络上传状态
                uploadStatus(body, false);
            }
        }
    };

    /**
     * 上传状态
     */
    private void uploadStatus(final StatusRequestBody requestBody, Boolean flag) {
        //取消之前的网络请求
        if (compositeSubscription != null) {
            compositeSubscription.clear();
        }
//        if (subscriber!=null){
//            subscriber.unsubscribe();
//            subscriber =null;
//        }
        //创建一个观察者
        subscriber = new Subscriber<StatusResponseBody>() {
            @Override
            public void onCompleted() {
                subscriber.unsubscribe();
            }

            @Override
            public void onError(Throwable e) {
                subscriber.unsubscribe();
                uploadSuccess = false;
            }

            @Override
            public void onNext(StatusResponseBody body) {
                String code = body.getCode();
                if (code.equals("00000")) {
                    uploadSuccess = true;
                    //自动计时置0
                    if (flag) {
                        autoUploadDelay = 0;
                    }
                    MyLogUtil.d(TAG, "上传状态成功");

//                    String latitude = SP.get(mContext, Config.DEVICE_LATITUDE, "");
//                    String lontitude = SP.get(mContext, Config.DEVICE_LONTITUDE, "");
//                    String address = SP.get(mContext, Config.DEVICE_ADDRESS, "");
//
//                    //记录当前状态
                    recordCurrentStatusBody(requestBody);
//                    LocationInfoBody location = new LocationInfoBody();
//                    location.setProvinceId("");
//                    location.setCityId("");
//                    location.setDistrictId("");
//                    location.setGcjLat(latitude);
//                    location.setGcjLng(lontitude);
//                    location.setAddress(address);
//                    uploadLocation(location);
                } else {
                    uploadSuccess = false;
                }
            }
        };
        compositeSubscription.add(subscriber);
        model.uploadStatus(requestBody).subscribe(subscriber);
//        model.uploadStatus(requestBody);
        uploadSuccess = false;
    }

    /**
     * 上传地理位置
     */
    private void uploadLocation(final LocationInfoBody requestBody) {
        //取消之前的网络请求
        if (compositeSubscription != null) {
            compositeSubscription.clear();
        }
        //创建一个观察者
        Subscriber subscriber = new Subscriber<LocationResultBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                uploadSuccess = false;
            }

            @Override
            public void onNext(LocationResultBean body) {
                if (body.getCode().equals("00000")) {
                    MyLogUtil.d("地理位置上报成功");
                } else {
                    MyLogUtil.d("地理位置上报失败");
                }
            }
        };
        compositeSubscription.add(subscriber);
        model.uploadStatus(requestBody).subscribe(subscriber);
        uploadSuccess = false;
    }

    /**
     * 记录当前的状态实体
     *
     * @param body
     */
    private void recordCurrentStatusBody(StatusRequestBody body) {
        if (body.getDoor() != null) {
            statusRecordBody.setDoor(body.getDoor());
        }
        if (body.getFan() != null) {
            statusRecordBody.setFan(body.getFan());
        }
        if (body.getLight() != null) {
            statusRecordBody.setLight(body.getLight());
        }
        if (body.getInnerT() != null) {
            statusRecordBody.setInnerT(body.getInnerT());
        }
        if (body.getOuterT() != null) {
            statusRecordBody.setOuterT(body.getOuterT());
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (scheduledThreadPoolExecutor != null) {
            scheduledThreadPoolExecutor.shutdown();
        }
        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
        }
        if (subscriber != null) {
            subscriber.unsubscribe();
            subscriber = null;
        }
    }


    class ServiceBinder extends Binder {

        public StatusService getService() {
            return StatusService.this;
        }
    }
}
