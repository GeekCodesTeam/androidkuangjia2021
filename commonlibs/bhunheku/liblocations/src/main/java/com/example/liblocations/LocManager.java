package com.example.liblocations;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

public class LocManager implements LocManagerListener {

    private LocListener listener;
    private LocServer server;

//    public static volatile LocManager mInstance;
//
//    public static  LocManager getInstance() {
//        if (mInstance == null) {
//            synchronized (LocManager.class) {
//                if (mInstance == null) {
//                    mInstance = new LocManager();
//                }
//            }
//        }
//        return mInstance;
//    }

    @Override
    public void init(Context context) {
        server = new LocServer(context,aMapLocationListener,getOption());
    }

    private AMapLocationListener aMapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (null!=aMapLocation&&aMapLocation.getErrorCode()==0&&aMapLocation.getLatitude()!=0.0&&aMapLocation.getLongitude()!=0.0){
                if (listener!=null){
                    LocationBean bean = new LocationBean();
                    bean.setAdCode(aMapLocation.getAdCode());
                    bean.setAddress(aMapLocation.getAddress());
                    bean.setAreaInterestName(aMapLocation.getAoiName());
                    bean.setCity(aMapLocation.getCity());
                    bean.setCityCode(aMapLocation.getCityCode());
                    bean.setDistrict(aMapLocation.getDistrict());
                    bean.setLatitude(aMapLocation.getLatitude()+"");
                    bean.setLongitude(aMapLocation.getLongitude()+"");
                    bean.setPointInterestName(aMapLocation.getPoiName());
                    bean.setProvince(aMapLocation.getProvince());
                    bean.setStreet(aMapLocation.getStreet());
                    listener.success(bean);
                }
            }else {
                if (listener!=null){
                    listener.fail(LocationErrorCode.ERROR_UNKNOWN);
                }
            }
        }
    };

//    private AMapLocationClientOption getOption(){
//        return new AMapLocationClientOption().setOnceLocation(true);
//    }

    /**
     * 默认的定位参数
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private AMapLocationClientOption getOption(){
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(true);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }

    @Override
    public void setListener(LocListener listener) {
        this.listener= listener;
    }

    @Override
    public void onStart() {
        server.start();
    }

    @Override
    public boolean enStarting() {
        return server.enStarting();
    }

    @Override
    public void onStop() {
        if (server!=null){
            server.stop();
        }
    }

    @Override
    public void onDestory() {
        if (server!=null){
            server.destory();
        }
    }
}