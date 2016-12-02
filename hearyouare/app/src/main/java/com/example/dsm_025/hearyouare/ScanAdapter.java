package com.example.dsm_025.hearyouare;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dsm_025 on 2016-12-01.
 */

public class ScanAdapter extends RecyclerView.Adapter<ScanAdapter.ViewHolder>{
    private ArrayList<String> mDataset;
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtHeader;
        public TextView txtFooter;

        public ViewHolder(View v){
            super(v);
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
        }
    }

    public void add(int position, String item){
        mDataset.add(position, item);
        notifyItemRemoved(position);
    }
    public void remove(String item){
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    public ScanAdapter (ArrayList<String> myDataset){
        mDataset = myDataset;
    }

    @Override
    public ScanAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String name = mDataset.get(position);
        holder.txtHeader.setText(mDataset.get(position));
        holder.txtHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove(name);
            }
        });
        holder.txtFooter.setText("Footer : " + mDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
