package com.example.dsm_025.hearyouare.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dsm_025.hearyouare.Adapter.ScanAdapter;
import com.example.dsm_025.hearyouare.Adapter.WifiAdapter;
import com.example.dsm_025.hearyouare.Component.MyRecyclerView;
import com.example.dsm_025.hearyouare.R;
import com.example.dsm_025.hearyouare.WifiData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsm_025 on 2016-12-24.
 */

public class WifiListFragment extends Fragment{
    private int count = 0;
    private Context mContext;
    private WifiManager wifiManager;

    private WifiAdapter mAdapter;
    private ArrayList<WifiData> datas;
    private List<ScanResult> scanResult;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mAdapter = new WifiAdapter(mContext, datas );
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wifi_list, container, false);

        MyRecyclerView recyclerView = (MyRecyclerView) v.findViewById(R.id.recyclerview_wifi);
        recyclerView.setHasFixedSize(true);

        View emptyView = v.findViewById(R.id.view_action_progress);
        recyclerView.setEmptyView(emptyView);

        recyclerView.setAdapter(mAdapter);
        return v;
    }
    private BroadcastReceiver mWifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if(action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)){
                getWIFIScanResult();
                wifiManager.startScan();
                if(count++ == 0){
//                    mAdapter = new ScanAdapter();
//                    mAdapter.setData(datas);
//                    setListAdapter(mAdapter);
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
    public WifiAdapter getmAdapter(){
        return mAdapter;
    }
}
