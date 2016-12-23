package com.example.dsm_025.hearyouare.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dsm_025.hearyouare.R;
import com.example.dsm_025.hearyouare.WifiData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsm_025 on 2016-12-01.
 */

public class ScanAdapter extends BaseAdapter{

    List<WifiData> list;
    LayoutInflater inflater;
    Activity activity;
    public ScanAdapter(){

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_list, parent, false);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            convertView.setLayoutParams(layoutParams);
        }
        TextView ssid = (TextView)convertView.findViewById(R.id.ssid);
        ssid.setText(list.get(position).getSSID());

        TextView level = (TextView)convertView.findViewById(R.id.level);
        level.setText(list.get(position).getLevel() + "");

        return convertView;
    }
    public void setData(ArrayList<WifiData> list){
        this.list = list;
    }
}
