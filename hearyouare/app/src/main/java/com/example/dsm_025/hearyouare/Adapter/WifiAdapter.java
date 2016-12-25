package com.example.dsm_025.hearyouare.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dsm_025.hearyouare.R;
import com.example.dsm_025.hearyouare.WifiData;
import com.example.dsm_025.hearyouare.dialog.InputWifiPasswordDialogFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsm_025 on 2016-12-24.
 */

public class WifiAdapter extends RecyclerView.Adapter<WifiAdapter.ViewHolder>{

    private ArrayList<WifiData> list;
    private Context context;



    public WifiAdapter(Context context, ArrayList<WifiData> list){
        this.context = context;
        if (list == null) this.list = new ArrayList<>();
        else this.list = list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
        final RelativeLayout hClickLayout = holder.mClickLayout;

        holder.mSSIDTV.setText(list.get(position).getSSID());
        holder.mLevelTV.setText(Integer.toString(list.get(position).getLevel()));

        hClickLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputWifiPasswordDialogFragment dialog = InputWifiPasswordDialogFragment.newInstance(new InputWifiPasswordDialogFragment.PasswordInputListener() {
                    @Override
                    public void onPassWordInputComplete(String name) {
                        if(name != null){

                        }else{

                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public final RelativeLayout mClickLayout;
        public final TextView mSSIDTV;
        public final TextView mLevelTV;

        public ViewHolder(View itemView) {
            super(itemView);

            mClickLayout = (RelativeLayout) itemView.findViewById(R.id.layout_wifi_click);
            mSSIDTV = (TextView) itemView.findViewById(R.id.tv_ssid);
            mLevelTV = (TextView) itemView.findViewById(R.id.tv_level);
        }
        public String toString(){
            return super.toString() + " '" + mSSIDTV.getText() + "'";
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }
}
