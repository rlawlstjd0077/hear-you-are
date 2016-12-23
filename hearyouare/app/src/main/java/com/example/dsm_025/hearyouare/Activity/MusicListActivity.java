package com.example.dsm_025.hearyouare.Activity;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.dsm_025.hearyouare.Adapter.MusicAdapter;
import com.example.dsm_025.hearyouare.Data.MusicDto;
import com.example.dsm_025.hearyouare.R;
import com.example.dsm_025.hearyouare.Utill.SocketListener;
import com.example.dsm_025.hearyouare.Utill.SocketManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by dsm_025 on 2016-12-22.
 */

public class MusicListActivity extends AppCompatActivity{
    private ListView listView;
    public static ArrayList<MusicDto> list;
    private Handler mainHandler;
    private SocketListener sl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_list);
        getMusicList();

        listView = (ListView)findViewById(R.id.list_view);
        MusicAdapter musicAdapter = new MusicAdapter(this, list);
        listView.setAdapter(musicAdapter);

        sl = new SocketListener(getApplicationContext(), mainHandler);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                FileSender sender = new FileSender(position, list);
                try {
                    sender.execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void getMusicList(){
        list = new ArrayList<MusicDto>();

        String[] projection = {MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA
        };

        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, null);

        while(cursor.moveToNext()){

            MusicDto musicDto = new MusicDto();
            musicDto.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
            musicDto.setId(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
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
        public FileSender(int position, ArrayList<MusicDto> list){
            this.position = position;
            this.list = list;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            sl.setMsg("/SEND_MUSIC:");
            sl.start();
            try {
                sl.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sl = new SocketListener(getApplicationContext(), mainHandler);
            sl.setMsg("/MUSIC_INFO:{\"album\": \"\", \"playtime\": \"\", \"singer\": \"\", \"name\": \"\"}");
            sl.start();
            try {
                sl.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            musicDto = list.get(position);
            FileInputStream fileInputStream = null;
            File file = new File(musicDto.getPath());
            byte[] bytes = new byte[(int)file.length()];
            try{
                fileInputStream = new FileInputStream(file);
                fileInputStream.read(bytes);
                fileInputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                SocketManager.sendFile(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
