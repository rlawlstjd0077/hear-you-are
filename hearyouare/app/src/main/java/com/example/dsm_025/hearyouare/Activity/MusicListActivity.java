package com.example.dsm_025.hearyouare.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.dsm_025.hearyouare.Adapter.MusicAdapter;
import com.example.dsm_025.hearyouare.Adapter.MusicDataAdapter;
import com.example.dsm_025.hearyouare.Adapter.WifiAdapter;
import com.example.dsm_025.hearyouare.Component.MyRecyclerView;
import com.example.dsm_025.hearyouare.Data.MusicDto;
import com.example.dsm_025.hearyouare.R;
import com.example.dsm_025.hearyouare.Utill.SocketFileListener;
import com.example.dsm_025.hearyouare.Utill.SocketListener;
import com.example.dsm_025.hearyouare.Utill.SocketManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by dsm_025 on 2016-12-22.
 */

public class MusicListActivity extends ActionBarActivity{
    private ListView listView;
    public static ArrayList<MusicDto> list;
    private Handler mainHandler;
    private SocketListener sl;
    private SocketFileListener sf;
    private Context mContext;
    private MyRecyclerView recyclerView;
    private MusicDataAdapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        mContext = getApplicationContext();

        try {
            sl = new SocketListener(mContext, mainHandler);
            sf = new SocketFileListener(mContext, mainHandler);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getMusicList();

        mAdapter = new MusicDataAdapter(mContext, list);
        mAdapter.setmOnMyItemClicked(new MusicDataAdapter.OnMyItemClicked() {
            @Override
            public void onItemClicked(int position) {
                FileSender sender = new FileSender(position, list, mContext);
                try {
                    sender.execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        });

        recyclerView = (MyRecyclerView) findViewById(R.id.recyclerview_music_list);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, 1));

        recyclerView.setAdapter(mAdapter);


    }
    public void getMusicList(){
        list = new ArrayList<MusicDto>();
        String[] projection = {MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA
        };

        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, null);

        while(cursor.moveToNext()){
            MusicDto musicDto = new MusicDto();
            musicDto.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
            musicDto.setId(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
            musicDto.setAlbum(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
            musicDto.setAlbumId(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
            musicDto.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            musicDto.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
            list.add(musicDto);
        }
        cursor.close();
    }
    class FileSender extends AsyncTask<Void, Void, Void> {
        int position;
        ArrayList<MusicDto> list;
        MusicDto musicDto;
        private ProgressDialog mDlg;
        private Context mContext;

        public FileSender(int position, ArrayList<MusicDto> list, Context context){
            this.position = position;
            this.list = list;
            this.mContext = context;
        }

//        @Override
//        protected void onPreExecute() {
//            mDlg = new ProgressDialog(mContext);
//            mDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            mDlg.setMessage("데이터 전송 중입니다.");
//            mDlg.show();
//        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mDlg.dismiss();
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            {
                /* 음악 전송 시작 및 음원 정보 전송 */
                sl.setMsg("/SEND_MUSIC:");
                sl.start();
                try {
                    sl.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    sl = new SocketListener(getApplicationContext(), mainHandler);
                    String msg = list.get(position).jsonBinder();
                    Log.d("MUSIC_I: ", msg);
                    sl.setMsg("/MUSIC_INFO:" + msg);
                    sl.start();
                    sl.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            {
                /* 앨범 이미지 전송 */
                musicDto = list.get(position);
                Bitmap bitmap = BitmapFactory.decodeFile(getCoverArtPath(Long.parseLong(musicDto.getAlbumId()), getApplication()));
                byte[] image = bitmapToByteArray(bitmap);
                byte[] copyarray;
                try {
                    int i;
                    for (i = 0; i < image.length / 1024; i++) {
                        copyarray = new byte[1024];
                        System.arraycopy(image, i * 1024, copyarray, 0, 1024);
                        sf.setFile(copyarray);
                        sf.start();
                        sf.join();
                        sf.stop();
                    }
                    copyarray = new byte[1024];
                    System.arraycopy(image, i * 1024, copyarray, 0, image.length - i * 1024);
                    sf.setFile(copyarray);
                    sf.start();
                    sf.join();
                    sf.stop();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            {
                mDlg.setMessage("음악 파일을 보내고 있습니다.");
                /* 음악 데이터 전송 */
                FileInputStream fileInputStream = null;
                File file = new File(musicDto.getPath());
                byte[] bytes = new byte[1024];
                int data = 0;
                try {
                    fileInputStream = new FileInputStream(file);
                    while ((fileInputStream.read(bytes)) > 0) {
                        data++;
                    }
                    for (; data > 0; data--) {
                        fileInputStream.read(bytes);
                        SocketManager.sendFile(bytes);
                    }
                    fileInputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }



    private static String getCoverArtPath(long albumId, Context context) {

        Cursor albumCursor = context.getContentResolver().query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Albums.ALBUM_ART},
                MediaStore.Audio.Albums._ID + " = ?",
                new String[]{Long.toString(albumId)},
                null
        );
        boolean queryResult = albumCursor.moveToFirst();
        String result = null;
        if (queryResult) {
            result = albumCursor.getString(0);
        }
        albumCursor.close();
        return result;
    }
    public byte[] bitmapToByteArray( Bitmap $bitmap ) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream() ;
        $bitmap.compress( Bitmap.CompressFormat.JPEG, 100, stream) ;
        byte[] byteArray = stream.toByteArray() ;
        return byteArray ;
    }
}
