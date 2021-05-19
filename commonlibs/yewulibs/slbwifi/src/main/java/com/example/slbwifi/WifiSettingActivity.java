package com.example.slbwifi;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.slbwifi.domain.Wifi;
import com.example.slbwifi.util.StringUtil;
import com.example.slbwifi.util.WifiDatabaseUtils;
import com.example.slbwifi.util.WifiUtil;
import com.example.slbwifi.widget.SetApPwdDialog;
import com.example.slbwifi.widget.ShowWifiInfoDialog;
import com.geek.libutils.data.MmkvUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * geek 2017年3月29日15:16:38
 */
public class WifiSettingActivity extends AppCompatActivity implements ShowWifiInfoDialog.IRemoveWifi, View.OnClickListener {
    private static final String TAG = WifiSettingActivity.class.getSimpleName();
    private static final long SCANWIFI_DELAY_TIME = 3000;//wifi扫描时间间隔
    private RecyclerView mListView;//wifi列表
    private ToggleButton toggle_wifi;//wifi开关
    private TextView tv_wifi_tip;//wifi关闭提示语
    private RelativeLayout network_loading2;//wifi加载界面

    private RecycleAdapter mWifiAdaper;//wifi列表适配器
    private WifiManager mWifimaManager;
    private static final int REFERSH_WIFILIST = 0;
    private WifiActionReceiver mWifiActionReceiver;
    private IntentFilter mFilter1;
    private IntentFilter mFilter2;
    private List<ScanResult> mScanResults;//wifi扫描数据
    private List<ScanResult> srs;//adapter数据
    private ScanResultTask scanResultTask;
    private Wifi mLastConnectingWifi = new Wifi();
    private boolean isTouching;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_setting_index);
        finview();
        mScanResults = new ArrayList<>();
        srs = new ArrayList<>();
        mWifiAdaper = new RecycleAdapter(this);
        mListView.setLayoutManager(new LinearLayoutManager(this));
        mListView.setAdapter(mWifiAdaper);
        mWifimaManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        mWifiActionReceiver = new WifiActionReceiver();
        mFilter1 = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        mFilter2 = new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        mListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int scrollState) {
                super.onScrollStateChanged(recyclerView, scrollState);
                Log.d(TAG, "scrollState::" + scrollState);
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    isTouching = false;
                } else {
                    isTouching = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
//        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                Log.d(TAG, "scrollState::" + scrollState);
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
        mWifiAdaper.setOnItemClickLitener(new RecycleAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                ScanResult mTemp = (ScanResult) mWifiAdaper.getItem(position);
                if (mTemp != null) {
                    WifiInfo mInfo = WifiUtil.getConnectedWifiInfo(WifiSettingActivity.this);
                    if (mInfo != null) {
                        if (mInfo.getSSID() != null && (mInfo.getSSID().equals(mTemp.SSID) || mInfo.getSSID().equals("\"" + mTemp.SSID + "\""))) {
                            if (!WifiDatabaseUtils.isExisted(mTemp.SSID)) {
                                connect(mTemp);
                                Log.d("connect", mTemp.SSID);
                                return;
                            }
                            //显示信息详情
                            long startTime = SystemClock.uptimeMillis();
                            ShowWifiInfoDialog.show(WifiSettingActivity.this, WifiSettingActivity.this, mInfo, WifiUtil.getEncryptString(mTemp.capabilities));
                            Log.d(TAG + "time", "showTime" + (SystemClock.uptimeMillis() - startTime));
                        } else {
                            connect(mTemp);
                            Log.d("connect", mTemp.SSID);
                        }
                    } else {
                        connect(mTemp);
                        Log.d("connect", mTemp.SSID);
                    }
                }
            }
        });
        if (WifiUtil.isWifiOpen(this)) {
            toggle_wifi.setChecked(true);
            network_loading2.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.VISIBLE);
            tv_wifi_tip.setVisibility(View.GONE);
        } else {
            toggle_wifi.setChecked(false);
            mListView.setVisibility(View.GONE);
            tv_wifi_tip.setVisibility(View.VISIBLE);
            network_loading2.setVisibility(View.GONE);

        }

        toggle_wifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                WifiUtil.openWifi(WifiSettingActivity.this, isChecked);
                if (isChecked) {
                    network_loading2.setVisibility(View.VISIBLE);
                    mListView.setVisibility(View.VISIBLE);
                    tv_wifi_tip.setVisibility(View.GONE);
                    getScanResult();
                } else {
                    mListView.setVisibility(View.GONE);
                    tv_wifi_tip.setVisibility(View.VISIBLE);
                    network_loading2.setVisibility(View.GONE);
                }
            }
        });
        mHandler.post(mScanRunnable);
    }

    private void finview() {
        findViewById(R.id.tv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mListView = findViewById(R.id.listview_wifi);
        toggle_wifi = (ToggleButton) findViewById(R.id.toggle_wifi);
        tv_wifi_tip = (TextView) findViewById(R.id.tv_wifi_tip);
        network_loading2 = (RelativeLayout) findViewById(R.id.network_loading2);
    }

    /**
     * 链接wifi
     *
     * @param res
     */
    private void connect(final ScanResult res) {
//        final Wifi saved = WifiDatabaseUtils.find(res.SSID);
        final Wifi saved = MmkvUtils.getInstance().get_common_json(res.SSID, Wifi.class);
        if (saved != null) {
            long startTime = SystemClock.uptimeMillis();
            WifiUtil.addNetWorkThread(WifiUtil.createWifiConfig(saved.getSsid(),
                    StringUtil.avoidNull(saved.getPassword()),
                    WifiUtil.getWifiCipher(saved.getCapabilities())), WifiSettingActivity.this);
            Log.d(TAG + "time", "addNetWorkTime1   " + (SystemClock.uptimeMillis() - startTime));
//            updateWifiUI();
            return;
        }

        if (WifiUtil.getEncryptString(res.capabilities).equals("OPEN")) {
            long startTime = SystemClock.uptimeMillis();
            WifiUtil.addNetWorkThread(WifiUtil.createWifiConfig(res.SSID, "", WifiUtil.getWifiCipher(res.capabilities)), WifiSettingActivity.this);
            Log.d(TAG + "time", "addNetWorkTime2   " + (SystemClock.uptimeMillis() - startTime));
//            updateWifiUI();

        } else {
            //连接当前ap,弹出密码输入框，设置密码
            SetApPwdDialog.show(WifiSettingActivity.this, new SetApPwdDialog.IConnectWifi() {
                @Override
                public void onConnectClick(final String SSID, final String pwd, final WifiUtil.WifiCipherType mType) {
                    long startTime = SystemClock.uptimeMillis();
                    WifiUtil.addNetWorkThread(WifiUtil.createWifiConfig(SSID, pwd, mType), WifiSettingActivity.this);
                    Log.d(TAG + "time", "addNetWorkTime3   " + (SystemClock.uptimeMillis() - startTime));
                    mLastConnectingWifi = new Wifi();
                    mLastConnectingWifi.setSsid(SSID);
                    mLastConnectingWifi.setCapabilities(res.capabilities);
                    mLastConnectingWifi.setPassword(pwd);
//                    lianjie_connet = "正在连接...";
//                    mWifiAdaper.setLianjie_content(lianjie_connet);
//                    mWifiAdaper.notifyDataSetChanged();

                }
            }, res.SSID, WifiUtil.getWifiCipher(res.capabilities), res.capabilities);
        }

    }

    /**
     * 更新wifi列表
     */
    private long updateOldTime;

    public void updateWifiUI() {
        if (SystemClock.uptimeMillis() - updateOldTime > 1.0f * 1000) {
            updataUiNew();
        }
        updateOldTime = SystemClock.uptimeMillis();
    }


    public void updataUiNew() {
        Log.d(TAG, "updataUiNew");
        new Thread() {
            @Override
            public void run() {
                super.run();
                System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
                try {//避免下一个mScanResults改变，而sort还在执行导致数据IndexOutOfBound
                    Collections.sort(mScanResults, WifiUtil.comparator);
                    mHandler.sendEmptyMessage(0x11);
                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private Map<String, ScanResult> maps = new HashMap<String, ScanResult>();

    private void deleteSameSsid() {
        maps.clear();
        for (int i = 0; i < mScanResults.size() - 1; i++) {
            maps.put(mScanResults.get(i).SSID, mScanResults.get(i));
        }
        Collection<ScanResult> collections = maps.values();
        if (collections instanceof List)
            mScanResults = (List) collections;
        else
            mScanResults = new ArrayList(collections);
    }

    public String getCurrentResultCap(WifiInfo mInfo) {
        for (ScanResult scanResult : mScanResults) {
            if ((mInfo.getSSID().replace("\"", "")).equals(scanResult.SSID)) {
                return scanResult.capabilities;
            }
        }
        return null;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REFERSH_WIFILIST:
//                    new ScanResultTask().execute((Void) null);
                    break;
                case 0x11:
                    if (isTouching) {
                        return;
                    }
                    long startTime = SystemClock.uptimeMillis();
                    if (mScanResults.size() > 0) {
                        network_loading2.setVisibility(View.GONE);
                    }
                    srs.clear();
                    srs.addAll(mScanResults);
                    mWifiAdaper.setmScanResult(srs);
                    mWifiAdaper.notifyDataSetChanged();
                    Log.d(TAG + "time", "updataUiNew" + (SystemClock.uptimeMillis() - startTime) + ",lianjie_connet=" + lianjie_connet);
                    break;
                default:
                    break;
            }
        }
    };

    public String lianjie_connet = "安全";

    public void getScanResult() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                List<ScanResult> result = WifiUtil.getWifiScanResult(WifiSettingActivity.this);
                refreshList(result);
                updateWifiUI();
            }
        }.start();

//        mHandler.sendEmptyMessage(REFERSH_WIFILIST);
    }

    private Runnable mScanRunnable = new Runnable() {
        @Override
        public void run() {
            mWifimaManager.startScan();
            mHandler.postDelayed(this, SCANWIFI_DELAY_TIME);
//            onNewWifiList();
        }
    };

    private void onNewWifiList() {
        WifiInfo info = WifiUtil.getConnectedWifiInfo(this);
        if (info != null && info.getNetworkId() != -1) {
            return;
        }
    }

    private void reConnect2OldWifi() {
        Wifi wifi = WifiDatabaseUtils.getLastWifi();
        if (wifi == null) {
            return;
        }
        WifiUtil.addNetWorkThread(WifiUtil.createWifiConfig(wifi.getSsid(),
                StringUtil.avoidNull(wifi.getPassword()),
                WifiUtil.getWifiCipher(wifi.getCapabilities())), this);
        updateWifiUI();

    }

    @Override
    public void onRemoveClick(String ssid, int networkId) {
        ssid = ssid.replace("\"", "");
//        WifiDatabaseUtils.delete(ssid);
        MmkvUtils.getInstance().remove_common(ssid);
        WifiUtil.removeWifi(WifiSettingActivity.this, networkId);
        getScanResult();
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 接收wifi变化广播
     */
    class WifiActionReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null && intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                WifiInfo wifiInfo = WifiUtil.getConnectedWifiInfo(WifiSettingActivity.this);
                Log.d(TAG, "SCAN_RESULTS_AVAILABLE_ACTION" + ",wifiInfo.getSSID()=" + wifiInfo.getSSID() + "," + WifiDatabaseUtils.isExisted(wifiInfo.getSSID().replace("\"", "")));
                if (WifiDatabaseUtils.isExisted(wifiInfo.getSSID().replace("\"", ""))) {
                    lianjie_connet = "已连接";
                    mWifiAdaper.setLianjie_content(lianjie_connet);
//                    Log.d(TAG, "SCAN_RESULTS_AVAILABLE_ACTION" + ",wifiInfo.getSSID()=" + wifiInfo.getSSID());
                }
                getScanResult();
            }
            if (intent.getAction() != null && intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                Log.d(TAG, "NETWORK_STATE_CHANGED_ACTION");
                NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if (info.getState() == NetworkInfo.State.DISCONNECTED) {
                    Log.d(TAG, "断开连接");
                    lianjie_connet = "断开连接";
                    updateWifiUI();
                } else if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 这个时候可以显示正在连接
                    lianjie_connet = "正在连接...";
                    mWifiAdaper.setLianjie_content(lianjie_connet);
                    updateWifiUI();
                    WifiInfo wifiInfo = WifiUtil.getConnectedWifiInfo(WifiSettingActivity.this);
                    if (wifiInfo != null) {
                        if (wifiInfo.getSSID() != null
                                && !wifiInfo.getSSID().equals(mLastConnectingWifi.getSsid())
                                && !wifiInfo.getSSID().equals("\"" + mLastConnectingWifi.getSsid() + "\"")) {
                            lianjie_connet = "已连接";
                            mWifiAdaper.setLianjie_content(lianjie_connet);
                            updateWifiUI();
                            return;
                        }
                        Log.d(TAG, "CONNECTED");
                        if (mLastConnectingWifi != null && mLastConnectingWifi.getSsid() != null) {
//                            if (WifiDatabaseUtils.find(mLastConnectingWifi.getSsid()) != null) {
//                                return;
//                            }
                            if (MmkvUtils.getInstance().get_common_json(mLastConnectingWifi.getSsid(), Wifi.class) != null) {
                                return;
                            }
//                            WifiDatabaseUtils.save(mLastConnectingWifi);// 这个时候可以显示已连接
                            MmkvUtils.getInstance().set_common_json2(mLastConnectingWifi.getSsid(), mLastConnectingWifi);
                        }
                        lianjie_connet = "已连接";
                        mWifiAdaper.setLianjie_content(lianjie_connet);
                        updateWifiUI();
                    }
                    sendBroadcast(new Intent("com.haiersmart.app.network_connected"));
                }
            }
        }
    }

    /**
     * 获取scanresult
     */
    class ScanResultTask extends AsyncTask<Void, Void, List<ScanResult>> {

        @Override
        protected List<ScanResult> doInBackground(Void... params) {
            return WifiUtil.getWifiScanResult(WifiSettingActivity.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(List<ScanResult> result) {
            if (result != null) {
                refreshList(result);
                Log.d(TAG, "result:::: " + result.toString());
                updateWifiUI();
            }
            super.onPostExecute(result);
        }
    }

    /**
     * 只添加增多的wifi
     *
     * @param mResult
     */
    public void refreshList(List<ScanResult> mResult) {
        if (mScanResults != null && mResult != null) {
            mScanResults.clear();
            mScanResults.addAll(mResult);
            for (int i = 0; i < mScanResults.size(); i++) {
                for (int j = mScanResults.size() - 1; j > i; j--) {
                    if (mScanResults.get(i).SSID.equals(mScanResults.get(j).SSID)) {
                        if (mScanResults.get(i).level > mScanResults.get(j).level) {
                            mScanResults.remove(j);
                        } else mScanResults.remove(i);
                    }
                }
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getScanResult();
        if (mWifiActionReceiver != null && mFilter1 != null)
            registerReceiver(mWifiActionReceiver, mFilter1);

        if (mWifiActionReceiver != null && mFilter2 != null)
            registerReceiver(mWifiActionReceiver, mFilter2);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mWifiActionReceiver != null)
            unregisterReceiver(mWifiActionReceiver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mScanRunnable != null) {
            mHandler.removeCallbacks(mScanRunnable);
            mHandler.removeMessages(0x11);
            mHandler.removeMessages(REFERSH_WIFILIST);
        }
    }
}
