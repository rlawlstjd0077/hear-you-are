package com.example.dsm_025.hearyouare.Fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.dsm_025.hearyouare.Adapter.ScanAdapter;
import com.example.dsm_025.hearyouare.WifiData;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsm_025 on 2016-12-01.
 */

public class SpeakerScanFragment extends ListFragment {
    private int count = 0;
    private int scanCount = 0;
    private WifiManager wifiManager;
    private Context mContext;
    private List<ScanResult> scanResult;
    private ArrayList<WifiData> datas;
    private ListView listView;
    private ScanAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private BroadcastReceiver mWifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if(action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)){
                getWIFIScanResult();
                wifiManager.startScan();
                if(count++ == 0){
                    mAdapter = new ScanAdapter();
                    mAdapter.setData(datas);
                    setListAdapter(mAdapter);
                }
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
            WifiData wifiData = new WifiData();
            wifiData.setSSID(result.SSID);
            wifiData.setLevel(result.level);
            datas.add(wifiData);
        }
    }
    public void initWIFIScan(){
        wifiManager = (WifiManager)mContext.getSystemService(Context.WIFI_SERVICE);
        scanCount = 0;
        final IntentFilter filter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        mContext.registerReceiver(mWifiScanReceiver, filter);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        initWIFIScan();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.fragment_wifi_list, container, false);
//        listView = (ListView) v.findViewById(R.id.list_view);

//        return v;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
