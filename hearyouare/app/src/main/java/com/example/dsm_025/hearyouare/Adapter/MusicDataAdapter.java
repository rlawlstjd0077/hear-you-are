package com.example.dsm_025.hearyouare.Adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dsm_025.hearyouare.Data.MusicDto;
import com.example.dsm_025.hearyouare.R;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GSD on 2016-12-30.
 */

public class MusicDataAdapter extends RecyclerView.Adapter<MusicDataAdapter.ViewHolder> {
    private ArrayList<MusicDto> list;
    private Context context;

    public MusicDataAdapter(Context context, ArrayList<MusicDto> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_listview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final LinearLayout hClickLayout = holder.mClickLayout;

        holder.mSongTV.setText(list.get(position).getTitle());
        holder.mArtistAlbumTV.setText(list.get(position).getArtist() + " / " + list.get(position).getAlbum());
        holder.mAlbumImageIV.setImageBitmap(getAlbumImage(context, Integer.parseInt((list.get(position)).getAlbumId()), 170));

        hClickLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("clicked", "item");
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final LinearLayout mClickLayout;
        public final TextView mSongTV;
        public final TextView mArtistAlbumTV;
        public final ImageView mAlbumImageIV;

        public ViewHolder(View itemView){
            super(itemView);

            mClickLayout = (LinearLayout) itemView.findViewById(R.id.layout_music_list);
            mSongTV = (TextView) itemView.findViewById(R.id.tv_title);
            mArtistAlbumTV = (TextView) itemView.findViewById(R.id.tv_artist_album);
            mAlbumImageIV = (ImageView) itemView.findViewById(R.id.iv_album);
        }

    }
    @Override
    public void onBindViewHolder(MusicDataAdapter.ViewHolder holder, int position, List<Object> payloads) {
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
    private static final BitmapFactory.Options options = new BitmapFactory.Options();
    private static Bitmap getAlbumImage(Context context, int album_id, int MAX_IMAGE_SIZE) {
        // NOTE: There is in fact a 1 pixel frame in the ImageView used to
        // display this drawable. Take it into account now, so we don't have to
        // scale later.
        ContentResolver res = context.getContentResolver();
        Uri uri = Uri.parse("content://media/external/audio/albumart/" + album_id);
        if (uri != null) {
            ParcelFileDescriptor fd = null;
            try {
                fd = res.openFileDescriptor(uri, "r");


                // Compute the closest power-of-two scale factor
                // and pass that to sBitmapOptionsCache.inSampleSize, which will
                // result in faster decoding and better quality

                //크기를 얻어오기 위한옵션 ,
                //inJustDecodeBounds값이 true로 설정되면 decoder가 bitmap object에 대해 메모리를 할당하지 않고, 따라서 bitmap을 반환하지도 않는다.
                // 다만 options fields는 값이 채워지기 때문에 Load 하려는 이미지의 크기를 포함한 정보들을 얻어올 수 있다.
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFileDescriptor(
                        fd.getFileDescriptor(), null, options);
                int scale = 0;
                if (options.outHeight > MAX_IMAGE_SIZE || options.outWidth > MAX_IMAGE_SIZE) {
                    scale = (int) Math.pow(2, (int) Math.round(Math.log(MAX_IMAGE_SIZE / (double) Math.max(options.outHeight, options.outWidth)) / Math.log(0.5)));
                }
                options.inJustDecodeBounds = false;
                options.inSampleSize = scale;



                Bitmap b = BitmapFactory.decodeFileDescriptor(
                        fd.getFileDescriptor(), null, options);

                if (b != null) {
                    // finally rescale to exactly the size we need
                    if (options.outWidth != MAX_IMAGE_SIZE || options.outHeight != MAX_IMAGE_SIZE) {
                        Bitmap tmp = Bitmap.createScaledBitmap(b, MAX_IMAGE_SIZE, MAX_IMAGE_SIZE, true);
                        b.recycle();
                        b = tmp;
                    }
                }

                return b;
            } catch (FileNotFoundException e) {
            } finally {
                try {
                    if (fd != null)
                        fd.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }
}
