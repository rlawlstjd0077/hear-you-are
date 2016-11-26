package com.example.dsm_025.hearyouare;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 김진성 on 2016-11-26.
 */
public class SpeakerScanDialogFragment extends DialogFragment {

    private static final String TAG = "SpeakerScanDialogFragment";

    private Context mContext;
    private ImageButton refreshBtn;
    private WifiManager wifiManager;
    private ConnectivityManager connManager;
    private List<ScanResult> scanResult;
    private SimpleAdapter adapter;
    private ArrayList<HashMap<String,String>> list;
    private int scanCount = 0;
    String result = "";
    StringBuilder sb = new StringBuilder();
    TextView test_tv;
    public static SpeakerScanDialogFragment newInstance(){

        SpeakerScanDialogFragment fragment = new SpeakerScanDialogFragment();

        return fragment;
    }


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
        test_tv.setText("");
        for (int i = 0; i < scanResult.size(); i++) {
            ScanResult result = scanResult.get(i);
            test_tv.append((i + 1) + ". SSID : " + result.SSID.toString()
                    + "\t\t RSSI : " + result.level + " dBm\n");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.dialog_speakerscan, container, false);
        mContext = v.getContext();
        initWIFIScan();
        test_tv = (TextView)v.findViewById(R.id.test_tv);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        LinearLayout scanList = (LinearLayout)v.findViewById(R.id.layout_content);

        return v;
    }
    public void initWIFIScan(){
        wifiManager = (WifiManager)mContext.getSystemService(Context.WIFI_SERVICE);
        scanCount = 0;
        final IntentFilter filter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        mContext.registerReceiver(mWifiScanReceiver, filter);
        wifiManager.startScan();
    }

}
