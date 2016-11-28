package com.example.dsm_025.hearyouare;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by 10102김동규 on 2016-11-25.
 */
public class MainFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        ImageView iv;

        iv = (ImageView)v.findViewById(R.id.speaker);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WifiManager wifiManager = (WifiManager)getContext().getSystemService(getContext().WIFI_SERVICE);
                if(wifiManager.isWifiEnabled()) {
                    SpeakerScanDialogFragment speakerScanDialogFragment = SpeakerScanDialogFragment.newInstance();
                    speakerScanDialogFragment.show(((AppCompatActivity) getContext()).getSupportFragmentManager(), null);
                }else{
                    Toast.makeText(getContext(), "Wifi가 꺼져 있습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }
}
