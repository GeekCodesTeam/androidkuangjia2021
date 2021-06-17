//package com.example.slbwifi;
//
//public class Act {
//}
//
//
//        import android.content.BroadcastReceiver;
//        import android.content.Context;
//        import android.content.Intent;
//        import android.content.IntentFilter;
//        import android.net.NetworkInfo;
//        import android.net.wifi.ScanResult;
//        import android.net.wifi.WifiInfo;
//        import android.net.wifi.WifiManager;
//        import android.os.AsyncTask;
//        import android.os.Bundle;
//        import android.os.Handler;
//        import android.os.Message;
//        import android.os.SystemClock;
//        
//        import android.view.View;
//        import android.widget.AbsListView;
//        import android.widget.AdapterView;
//        import android.widget.CompoundButton;
//        import android.widget.ImageView;
//        import android.widget.LinearLayout;
//        import android.widget.ListView;
//        import android.widget.RelativeLayout;
//        import android.widget.TextView;
//        import android.widget.ToggleButton;
//
//        import com.haiersmart.sfnation.R;
//        import com.haiersmart.sfnation.adapter.ScanResultAdapterNew;
//        import com.haiersmart.sfnation.base.BaseActivity;
//        import com.haiersmart.sfnation.bizutils.WifiDatabaseUtils;
//        import com.haiersmart.sfnation.bizutils.WifiUtil;
//        import com.haiersmart.sfnation.constant.ConstantUtil;
//        import com.haiersmart.sfnation.domain.Wifi;
//        import com.haiersmart.sfnation.widget.SetApPwdDialog;
//        import com.haiersmart.sfnation.widget.ShowWifiInfoDialog;
//        import com.haiersmart.utilslib.app.MyLogUtil;
//        import com.haiersmart.utilslib.data.StringUtil;
//
//        import java.util.ArrayList;
//        import java.util.Collection;
//        import java.util.Collections;
//        import java.util.HashMap;
//        import java.util.List;
//        import java.util.Map;
//
//        import butterknife.BindView;
//
///**
// * geek 2017年3月29日15:16:38
// */
//public class WifiSettingActivity extends BaseActivity implements ShowWifiInfoDialog.IRemoveWifi, View.OnClickListener {
//    private static final String TAG = WifiSettingActivity.class.getSimpleName();
//    private static final long SCANWIFI_DELAY_TIME = 5000;//wifi扫描时间间隔
//    @BindView(R.id.listview_wifi)
//    ListView mListView;//wifi列表
//    //    @BindView(R.id.toggle_wifi)
////    ToggleButton toggle_wifi;//wifi开关
//    @BindView(R.id.iv_switch_wifi)
//    ImageView iv_switch_wifi;//wifi开关
//
//    @BindView(R.id.tv_wifi_tip)
//    TextView tv_wifi_tip;//wifi关闭提示语
//    @BindView(R.id.network_loading2)
//    RelativeLayout network_loading2;//wifi加载界面
//
//    @BindView(R.id.home)
//    LinearLayout home;
//    @BindView(R.id.back)
//    LinearLayout back;
//
//    private ScanResultAdapterNew mWifiAdaper;//wifi列表适配器
//    private WifiManager mWifimaManager;
//    private static final int REFERSH_WIFILIST = 0;
//    private WifiActionReceiver mWifiActionReceiver;
//    private IntentFilter mFilter1;
//    private IntentFilter mFilter2;
//    private List<ScanResult> mScanResults;//wifi扫描数据
//    private List<ScanResult> srs;//adapter数据
//    private ScanResultTask scanResultTask;
//    private Wifi mLastConnectingWifi = new Wifi();
//    private boolean isTouching;
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_wifi_setting_index;
//
//    }
//
//    @Override
//    protected void setup(@Nullable Bundle savedInstanceState) {
//        super.setup(savedInstanceState);
//        home.setOnClickListener(this);
//        back.setOnClickListener(this);
//        mScanResults = new ArrayList<>();
//        srs = new ArrayList<>();
//        mWifiAdaper = new ScanResultAdapterNew(this);
//        mListView.setAdapter(mWifiAdaper);
//        network_loading2.setVisibility(View.VISIBLE);
//        mWifimaManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//        mWifiActionReceiver = new WifiActionReceiver();
//        mFilter1 = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
//        mFilter2 = new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION);
//        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                MyLogUtil.d(TAG, "scrollState::" + scrollState);
//                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
//                    isTouching = false;
//                } else {
//                    isTouching = true;
//                }
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//            }
//        });
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                ScanResult mTemp = (ScanResult) mListView.getAdapter().getItem(position);
//                if (mTemp != null) {
//                    WifiInfo mInfo = WifiUtil.getConnectedWifiInfo(WifiSettingActivity.this);
//                    if (mInfo != null) {
//                        if (mInfo.getSSID() != null && (mInfo.getSSID().equals(mTemp.SSID) || mInfo.getSSID().equals("\"" + mTemp.SSID + "\""))) {
//                            //显示信息详情
//                            long startTime = SystemClock.uptimeMillis();
//                            ShowWifiInfoDialog.show(WifiSettingActivity.this, WifiSettingActivity.this, mInfo, WifiUtil.getEncryptString(mTemp.capabilities));
//                            MyLogUtil.d(TAG + "time", "showTime" + (SystemClock.uptimeMillis() - startTime));
//                        } else {
//                            connect(mTemp);
//                            MyLogUtil.d("connect", mTemp.SSID);
//                        }
//                    } else {
//                        connect(mTemp);
//                        MyLogUtil.d("connect", mTemp.SSID);
//                    }
//                }
//            }
//        });
//
//        if (WifiUtil.isWifiOpen(this)) {
////            toggle_wifi.setChecked(true);
//            iv_switch_wifi.setImageResource(R.mipmap.switch_on2);
//            network_loading2.setVisibility(View.VISIBLE);
//            mListView.setVisibility(View.VISIBLE);
//            tv_wifi_tip.setVisibility(View.GONE);
//        } else {
////            toggle_wifi.setChecked(false);
//            iv_switch_wifi.setImageResource(R.mipmap.switch_close2);
//            mListView.setVisibility(View.GONE);
//            tv_wifi_tip.setVisibility(View.VISIBLE);
//            network_loading2.setVisibility(View.GONE);
//        }
//
////        toggle_wifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
////            @Override
////            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
////                WifiUtil.openWifi(WifiSettingActivity.this, isChecked);
////                if (isChecked) {
////                    network_loading2.setVisibility(View.VISIBLE);
////                    mListView.setVisibility(View.VISIBLE);
////                    tv_wifi_tip.setVisibility(View.GONE);
////                    getScanResult();
////                } else {
////                    mListView.setVisibility(View.GONE);
////                    tv_wifi_tip.setVisibility(View.VISIBLE);
////                    network_loading2.setVisibility(View.GONE);
////                }
////            }
////        });
//
//        iv_switch_wifi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (SystemClock.uptimeMillis() - switchWifiTime > 1000 * 1) {
//                    if (WifiUtil.isWifiOpen(WifiSettingActivity.this)) {
//                        iv_switch_wifi.setImageResource(R.mipmap.switch_close2);
//                        mListView.setVisibility(View.GONE);
//                        tv_wifi_tip.setVisibility(View.VISIBLE);
//                        network_loading2.setVisibility(View.GONE);
//                    } else {
//                        iv_switch_wifi.setImageResource(R.mipmap.switch_on2);
//                        network_loading2.setVisibility(View.VISIBLE);
//                        mListView.setVisibility(View.VISIBLE);
//                        tv_wifi_tip.setVisibility(View.GONE);
//                        getScanResult();
//                    }
//                    WifiUtil.openWifi(WifiSettingActivity.this, !WifiUtil.isWifiOpen(WifiSettingActivity.this));
//                    switchWifiTime = SystemClock.uptimeMillis();
//                }
//            }
//        });
//        mHandler.post(mScanRunnable);
//    }
//
//    private long switchWifiTime;
//
//
//    /**
//     * 链接wifi
//     *
//     * @param res
//     */
//    private void connect(final ScanResult res) {
//        final Wifi saved = WifiDatabaseUtils.find(res.SSID);
//        if (saved != null) {
//            long startTime = SystemClock.uptimeMillis();
//            WifiUtil.addNetWorkThread(WifiUtil.createWifiConfig(saved.getSsid(),
//                    StringUtil.avoidNull(saved.getPassword()),
//                    WifiUtil.getWifiCipher(saved.getCapabilities())), WifiSettingActivity.this);
//            MyLogUtil.d(TAG + "time", "addNetWorkTime1   " + (SystemClock.uptimeMillis() - startTime));
////            updateWifiUI();
//            return;
//        }
//
//        if (WifiUtil.getEncryptString(res.capabilities).equals("OPEN")) {
//            long startTime = SystemClock.uptimeMillis();
//            WifiUtil.addNetWorkThread(WifiUtil.createWifiConfig(res.SSID, "", WifiUtil.getWifiCipher(res.capabilities)), WifiSettingActivity.this);
//            MyLogUtil.d(TAG + "time", "addNetWorkTime2   " + (SystemClock.uptimeMillis() - startTime));
////            updateWifiUI();
//
//        } else {
//            //连接当前ap,弹出密码输入框，设置密码
//            SetApPwdDialog.show(WifiSettingActivity.this, new SetApPwdDialog.IConnectWifi() {
//                @Override
//                public void onConnectClick(final String SSID, final String pwd, final WifiUtil.WifiCipherType mType) {
//                    long startTime = SystemClock.uptimeMillis();
//                    WifiUtil.addNetWorkThread(WifiUtil.createWifiConfig(SSID, pwd, mType), WifiSettingActivity.this);
//                    MyLogUtil.d(TAG + "time", "addNetWorkTime3   " + (SystemClock.uptimeMillis() - startTime));
//                    mLastConnectingWifi = new Wifi();
//                    mLastConnectingWifi.setSsid(SSID);
//                    mLastConnectingWifi.setCapabilities(res.capabilities);
//                    mLastConnectingWifi.setPassword(pwd);
////                    updateWifiUI();
//                }
//            }, res.SSID, WifiUtil.getWifiCipher(res.capabilities), res.capabilities);
//        }
//    }
//
//    /**
//     * 更新wifi列表
//     */
//    private long updateOldTime;
//
//    public void updateWifiUI() {
//        if (SystemClock.uptimeMillis() - updateOldTime > 2 * 1000) {
//            updataUiNew();
//        }
//        updateOldTime = SystemClock.uptimeMillis();
//    }
//
//
//    public void updataUiNew() {
//        MyLogUtil.d(TAG, "updataUiNew");
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
//                try {//避免下一个mScanResults改变，而sort还在执行导致数据IndexOutOfBound
//                    Collections.sort(mScanResults, WifiUtil.comparator);
//                    mHandler.sendEmptyMessage(0x11);
//                } catch (ArrayIndexOutOfBoundsException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }
//
//    private Map<String, ScanResult> maps = new HashMap<String, ScanResult>();
//
//    private void deleteSameSsid() {
//        maps.clear();
//        for (int i = 0; i < mScanResults.size() - 1; i++) {
//            maps.put(mScanResults.get(i).SSID, mScanResults.get(i));
//        }
//        Collection<ScanResult> collections = maps.values();
//        if (collections instanceof List)
//            mScanResults = (List) collections;
//        else
//            mScanResults = new ArrayList(collections);
//    }
//
//    public String getCurrentResultCap(WifiInfo mInfo) {
//        for (ScanResult scanResult : mScanResults) {
//            if ((mInfo.getSSID().replace("\"", "")).equals(scanResult.SSID)) {
//                return scanResult.capabilities;
//            }
//        }
//        return null;
//    }
//
//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case REFERSH_WIFILIST:
////                    new ScanResultTask().execute((Void) null);
//                    break;
//                case 0x11:
//                    if (isTouching) {
//                        return;
//                    }
//                    long startTime = SystemClock.uptimeMillis();
//                    if (mScanResults.size() > 0) {
//                        network_loading2.setVisibility(View.GONE);
//                    }
//                    srs.clear();
//                    srs.addAll(mScanResults);
//                    mWifiAdaper.setmScanResult(srs);
//                    mWifiAdaper.notifyDataSetChanged();
//                    MyLogUtil.d(TAG + "time", "updataUiNew" + (SystemClock.uptimeMillis() - startTime));
//                    break;
//                default:
//                    break;
//            }
//        }
//    };
//
//    public void getScanResult() {
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                List<ScanResult> result = WifiUtil.getWifiScanResult(WifiSettingActivity.this);
//                refreshList(result);
//                updateWifiUI();
//            }
//        }.start();
//
////        mHandler.sendEmptyMessage(REFERSH_WIFILIST);
//    }
//
//    private Runnable mScanRunnable = new Runnable() {
//        @Override
//        public void run() {
//            mWifimaManager.startScan();
//            mHandler.postDelayed(this, SCANWIFI_DELAY_TIME);
////            onNewWifiList();
//        }
//    };
//
//    private void onNewWifiList() {
//        WifiInfo info = WifiUtil.getConnectedWifiInfo(this);
//        if (info != null && info.getNetworkId() != -1) {
//            return;
//        }
//    }
//
//    private void reConnect2OldWifi() {
//        Wifi wifi = WifiDatabaseUtils.getLastWifi();
//        if (wifi == null) {
//            return;
//        }
//        WifiUtil.addNetWorkThread(WifiUtil.createWifiConfig(wifi.getSsid(),
//                StringUtil.avoidNull(wifi.getPassword()),
//                WifiUtil.getWifiCipher(wifi.getCapabilities())), this);
//        updateWifiUI();
//
//    }
//
//    @Override
//    public void onRemoveClick(String ssid, int networkId) {
//        ssid = ssid.replace("\"", "");
//        WifiDatabaseUtils.delete(ssid);
//        WifiUtil.removeWifi(WifiSettingActivity.this, networkId);
//        getScanResult();
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.home:
//                onHomePressed();
//                break;
//            case R.id.back:
//                onBackPressed();
//                break;
//            default:
//                break;
//        }
//    }
//
//    /**
//     * 接收wifi变化广播
//     */
//    class WifiActionReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (intent.getAction() != null && intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
//                MyLogUtil.d(TAG, "SCAN_RESULTS_AVAILABLE_ACTION");
//                getScanResult();
//            }
//            if (intent.getAction() != null && intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
//                MyLogUtil.d(TAG, "NETWORK_STATE_CHANGED_ACTION");
//                NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
//                if (info.getState() == NetworkInfo.State.DISCONNECTED) {
//                    MyLogUtil.d(TAG, "断开连接");
//                } else if (info.getState() == NetworkInfo.State.CONNECTED) {
//                    WifiInfo wifiInfo = WifiUtil.getConnectedWifiInfo(WifiSettingActivity.this);
//                    if (wifiInfo != null) {
//                        if (wifiInfo.getSSID() != null
//                                && !wifiInfo.getSSID().equals(mLastConnectingWifi.getSsid())
//                                && !wifiInfo.getSSID().equals("\"" + mLastConnectingWifi.getSsid() + "\"")) {
//                            updateWifiUI();
//                            return;
//                        }
//                        MyLogUtil.d(TAG, "CONNECTED");
//                        if (mLastConnectingWifi != null && mLastConnectingWifi.getSsid() != null) {
//                            if (WifiDatabaseUtils.find(mLastConnectingWifi.getSsid()) != null) {
//                                return;
//                            }
//                            WifiDatabaseUtils.save(mLastConnectingWifi);
//                        }
//                        updateWifiUI();
//                    }
//                    sendBroadcast(new Intent(ConstantUtil.BROADCAST_NETWORK_CONNECTED));
//                }
//            }
//        }
//    }
//
//    /**
//     * 获取scanresult
//     */
//    class ScanResultTask extends AsyncTask<Void, Void, List<ScanResult>> {
//
//        @Override
//        protected List<ScanResult> doInBackground(Void... params) {
//            return WifiUtil.getWifiScanResult(WifiSettingActivity.this);
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//        }
//
//        @Override
//        protected void onPostExecute(List<ScanResult> result) {
//            if (result != null) {
//                refreshList(result);
//                MyLogUtil.d(TAG, "result:::: " + result.toString());
//                updateWifiUI();
//            }
//            super.onPostExecute(result);
//        }
//    }
//
//    /**
//     * 只添加增多的wifi
//     *
//     * @param mResult
//     */
//    public void refreshList(List<ScanResult> mResult) {
//        if (mScanResults != null && mResult != null) {
//            mScanResults.clear();
//            mScanResults.addAll(mResult);
//            for (int i = 0; i < mScanResults.size(); i++) {
//                for (int j = mScanResults.size() - 1; j > i; j--) {
//                    if (mScanResults.get(i).SSID.equals(mScanResults.get(j).SSID)) {
//                        if (mScanResults.get(i).level > mScanResults.get(j).level) {
//                            mScanResults.remove(j);
//                        } else mScanResults.remove(i);
//                    }
//                }
//            }
//        }
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        getScanResult();
//        if (mWifiActionReceiver != null && mFilter1 != null)
//            registerReceiver(mWifiActionReceiver, mFilter1);
//
//        if (mWifiActionReceiver != null && mFilter2 != null)
//            registerReceiver(mWifiActionReceiver, mFilter2);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (mWifiActionReceiver != null)
//            unregisterReceiver(mWifiActionReceiver);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (mScanRunnable != null) {
//            mHandler.removeCallbacks(mScanRunnable);
//            mHandler.removeMessages(0x11);
//            mHandler.removeMessages(REFERSH_WIFILIST);
//        }
//    }
//}
//
