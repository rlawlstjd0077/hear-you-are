package com.example.dsm_025.hearyouare;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by 김진성 on 2016-11-26.
 */
public class SpeakerScanDialogFragment extends DialogFragment {

    public Context mContext;
    public static SpeakerScanDialogFragment newInstance(){

        SpeakerScanDialogFragment fragment = new SpeakerScanDialogFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.dialog_speakerscan, container, false);

        mContext = v.getContext();

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        LinearLayout scanList = (LinearLayout)v.findViewById(R.id.layout_content);
        TextView view1 = createTV("Test1 ");


        LinearLayout.LayoutParams ip = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view1.setLayoutParams(ip);

        scanList.addView(view1);

        return v;
    }
    public TextView createTV(String txt){
        TextView view = new TextView(mContext);
        view.setText(txt);
        view.setTextSize(16);

        view.setTextColor(Color.BLACK);
        return view;
    }
}
