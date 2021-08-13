package com.fosung.xuanchuanlan.setting.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.fosung.xuanchuanlan.setting.adapter.WifiListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.fosung.xuanchuanlan.R;

public class WifiListActivity extends AppCompatActivity {

    private ListView listView;
    private WifiListAdapter adapter;

    private List<android.net.wifi.ScanResult> getWifiList() {

        List<android.net.wifi.ScanResult> wifiList = new ArrayList<>();


        //使用兼容库就无需判断系统版本
        int hasWriteStoragePermission = ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_FINE_LOCATION);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(WifiListActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        List<android.net.wifi.ScanResult> scanWifiList = wifiManager.getScanResults();

        if (scanWifiList != null && scanWifiList.size() > 0) {
            HashMap<String, Integer> signalStrength = new HashMap<String, Integer>();
            for (int i = 0; i < scanWifiList.size(); i++) {
                android.net.wifi.ScanResult scanResult = scanWifiList.get(i);
                if (!scanResult.SSID.isEmpty()) {
                    String key = scanResult.SSID + " " + scanResult.capabilities;
                    if (!signalStrength.containsKey(key)) {
                        signalStrength.put(key, i);
                        wifiList.add(scanResult);
                    }
                }
            }
        }
        return wifiList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_list);

        List<ScanResult> arrayList =  getWifiList();

        listView = (ListView) findViewById(R.id.listView);

        adapter = new WifiListAdapter(WifiListActivity.this,R.layout.item_wifistateview
        ,arrayList);
        listView.setAdapter(adapter);

    }
}
