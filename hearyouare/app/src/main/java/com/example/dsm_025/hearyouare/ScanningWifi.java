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
    private ArrayList<HashMap<String,String>> list;
    private Context mContext;
    private int scanCount = 0;
    String result = "";
    StringBuilder sb = new StringBuilder();

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
        for (int i = 0; i < scanResult.size(); i++) {
            ScanResult result = scanResult.get(i);
            Log.d(TAG, ((i + 1) + ". SSID : " + result.SSID.toString() + "\t\t RSSI : " + result.level + " dBm\n"));
        }
    }
    public void initWIFIScan(){
        wifiManager = (WifiManager)mContext.getSystemService(Context.WIFI_SERVICE);
        scanCount = 0;
        final IntentFilter filter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        mContext.registerReceiver(mWifiScanReceiver, filter);
        wifiManager.startScan();
    }


    public ScanningWifi(Context context){
        mContext = context;
        initWIFIScan();
    }

    public void getWifi(){
        list = new ArrayList<HashMap<String, String>>();
        wifiManager = (WifiManager)mContext.getSystemService(Context.WIFI_SERVICE);
        connManager = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if(wifiManager.isWifiEnabled()){
        }else{
            Toast.makeText(mContext, "wifi 가 꺼져 있습니다.", Toast.LENGTH_SHORT).show();
            return;
        }



        mContext.registerReceiver(mWifiScanReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();
    }
    public void startScan(){
        wifiManager.startScan();
    }
    public void stopScan(){
        mContext.unregisterReceiver(mWifiScanReceiver);
    }
}
