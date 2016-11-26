package com.example.dsm_025.hearyouare;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
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

    private WifiManager wifiManager;
    private ConnectivityManager connManager;
    private List<ScanResult> scanResult;
    private SimpleAdapter adapter;
    private ArrayList<HashMap<String,String>> list;
    private Context mContext;

    public void ScanningWifi(Context context){
        mContext = context;
    }

    public void getWifi(){
        list = new ArrayList<HashMap<String, String>>();
        wifiManager = (WifiManager)mContext.getSystemService(Context.WIFI_SERVICE);
        connManager = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if(wifiInfo.isAvailable()){

        }else{
            Toast.makeText(mContext, "wifi 사용불가", Toast.LENGTH_SHORT).show();

        }

    }
    public void refresh(){
        scanResult = wifiManager.getScanResults();
        
    }
}
