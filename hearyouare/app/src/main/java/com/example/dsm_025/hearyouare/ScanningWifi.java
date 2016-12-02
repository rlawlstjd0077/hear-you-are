package com.example.dsm_025.hearyouare;

import android.content.Context;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 김진성 on 2016-11-26.
 */
public class ScanningWifi {

    private static final String TAG = "WIFIScanner";
    private WifiManager wifiManager;
    private ConnectivityManager connManager;
    private List<ScanResult> scanResult;
    private SimpleAdapter adapter;
    private Context mContext;
    private int scanCount = 0;
    private StringBuilder sb = new StringBuilder();
    public static ArrayList<String> datas;

    private BroadcastReceiver mWifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if(action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)){
                getWIFIScanResult();
                wifiManager.startScan();
            }else if(action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)){
                mContext.sendBroadcast(new Intent("wifi.ON_NETWORK_STATE_CHANGED"));
            }
        }
    };
    public void getWIFIScanResult(){
        scanResult = wifiManager.getScanResults();
        datas = new ArrayList<>();
        for (int i = 0; i < scanResult.size(); i++) {
            ScanResult result = scanResult.get(i);
            datas.add(i, result.SSID);
        }
    }
    public void initWIFIScan(){
        wifiManager = (WifiManager)mContext.getSystemService(Context.WIFI_SERVICE);
        scanCount = 0;
        final IntentFilter filter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        mContext.registerReceiver(mWifiScanReceiver, filter);
    }


    public ScanningWifi(Context context){
        mContext = context;
        initWIFIScan();
    }

    public void startScan(){
        wifiManager.startScan();
    }
    public void stopScan(){
        mContext.unregisterReceiver(mWifiScanReceiver);
    }
    public ArrayList<String> getDatas(){
        return datas;
    }
}
