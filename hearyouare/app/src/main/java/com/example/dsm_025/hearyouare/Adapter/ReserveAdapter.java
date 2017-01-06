package com.example.dsm_025.hearyouare.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dsm_025.hearyouare.Data.MusicDto;
import com.example.dsm_025.hearyouare.R;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

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
    public interface OnMyItemClicked{
        public void onItemClicked(int position);
    }
    private OnMyItemClicked mOnMyItemClicked;

    public void setmOnMyItemClicked(OnMyItemClicked onMyItemClicked){
        this.mOnMyItemClicked = onMyItemClicked;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_listview_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final RelativeLayout hClickLayout = holder.mClickLayout;

        holder.mSongTV.setText(list.get(position).getTitle());
        holder.mArtistAlbumTV.setText(list.get(position).getArtist() + " | " +list.get(position).getAlbum());
        holder.mAlbumImageIV.setImageBitmap(getAlbumImage(list.get(position).getImage(), 170));
        //imageView 초기화 필요
        hClickLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnMyItemClicked.onItemClicked(position);
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
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
        public final ImageView mAlbumImageIV;


        public ViewHolder(View itemView) {
            super(itemView);

            mClickLayout = (RelativeLayout) itemView.findViewById(R.id.layout_wifi_click);
            mSongTV = (TextView) itemView.findViewById(R.id.tv_ssid);
            mArtistAlbumTV = (TextView) itemView.findViewById(R.id.tv_level);
            mAlbumImageIV = (ImageView) itemView.findViewById(R.id.iv_album);
        }
    }
    private static final BitmapFactory.Options options = new BitmapFactory.Options();
    private static Bitmap getAlbumImage(String path, int MAX_IMAGE_SIZE){
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            int scale = 0;
            if (options.outHeight > MAX_IMAGE_SIZE || options.outWidth > MAX_IMAGE_SIZE) {
                scale = (int) Math.pow(2, (int) Math.round(Math.log(MAX_IMAGE_SIZE / (double) Math.max(options.outHeight, options.outWidth)) / Math.log(0.5)));
            }
            options.inJustDecodeBounds = false;
            options.inSampleSize = scale;

            Bitmap b = BitmapFactory.decodeFile(path, options);

            if(b != null){
                if (options.outWidth != MAX_IMAGE_SIZE || options.outHeight != MAX_IMAGE_SIZE) {
                    Bitmap tmp = Bitmap.createScaledBitmap(b, MAX_IMAGE_SIZE, MAX_IMAGE_SIZE, true);
                    b.recycle();
                    b = tmp;
                }
                return b;
            }
        return null;
    }
}
