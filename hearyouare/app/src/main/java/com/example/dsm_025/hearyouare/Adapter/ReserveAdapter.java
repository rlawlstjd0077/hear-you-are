package com.example.dsm_025.hearyouare.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dsm_025.hearyouare.Data.MusicDto;
import com.example.dsm_025.hearyouare.R;

import java.util.ArrayList;

/**
 * Created by dsm_025 on 2016-12-25.
 */

public class ReserveAdapter extends RecyclerView.Adapter<ReserveAdapter.ViewHolder>{
    private ArrayList<MusicDto> list;
    private Context context;

    public ReserveAdapter(Context context, ArrayList<MusicDto> list){
        this.context = context;
        if (list == null) this.list = new ArrayList<>();
        else this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_listview_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final RelativeLayout hClickLayout = holder.mClickLayout;

        holder.mSongTV.setText(list.get(position).getTitle());
        holder.mArtistAlbumTV.setText(list.get(position).getArtist() + " | " +list.get(position).getAlbum());

        hClickLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        public final TextView mSongTV;
        public final TextView mArtistAlbumTV;


        public ViewHolder(View itemView) {
            super(itemView);

            mClickLayout = (RelativeLayout) itemView.findViewById(R.id.layout_wifi_click);
            mSongTV = (TextView) itemView.findViewById(R.id.tv_ssid);
            mArtistAlbumTV = (TextView) itemView.findViewById(R.id.tv_level);
        }
    }
}
