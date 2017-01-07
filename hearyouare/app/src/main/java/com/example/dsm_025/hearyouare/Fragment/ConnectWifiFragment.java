package com.example.dsm_025.hearyouare.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.dsm_025.hearyouare.R;
import com.example.dsm_025.hearyouare.Utill.Utill;

/**
 * Created by 10102김동규 on 2017-01-06.
 */

public class ConnectWifiFragment extends Fragment {
    private Toolbar toolbar;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_connect_wifi,container,false);
        Utill.setGlobalFont(getActivity(), v);
        TextView textview;
        textview = (TextView) v.findViewById(R.id.infotv1);
        TextView textvieww;
        textvieww = (TextView) v.findViewById(R.id.infotv2);
        TextView textviewww;
        textviewww = (TextView) v.findViewById(R.id.infotv3);
        return v;
    }
}
