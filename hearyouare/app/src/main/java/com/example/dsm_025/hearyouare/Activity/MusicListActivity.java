package com.example.dsm_025.hearyouare.Activity;

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
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.dsm_025.hearyouare.Adapter.MusicAdapter;
import com.example.dsm_025.hearyouare.Data.MusicDto;
import com.example.dsm_025.hearyouare.R;
import com.example.dsm_025.hearyouare.Utill.SocketListener;
import com.example.dsm_025.hearyouare.Utill.SocketManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
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
        setContentView(R.layout.activity_music_list);
        getMusicList();

        listView = (ListView)findViewById(R.id.list_view);
        MusicAdapter musicAdapter = new MusicAdapter(this, list);
        listView.setAdapter(musicAdapter);

        try {
            sl = new SocketListener(getApplicationContext(), mainHandler);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                FileSender sender = new FileSender(position, list);
//                try {
//                    sender.execute().get();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
            }
        });
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
            try {
                sl = new SocketListener(getApplicationContext(), mainHandler);
                sl.setMsg(list.get(position).jsonBinder());
                sl.start();
                sl.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            musicDto = list.get(position);
            Bitmap bitmap = BitmapFactory.decodeFile(getCoverArtPath(Long.parseLong(musicDto.getAlbumId()),getApplication()));
            byte[] image = bitmapToByteArray(bitmap);
            byte[] copyarray;
            FileInputStream fileInputStream = null;
            try{
                int i;
                for(i = 0; i < image.length / 1024; i++){
                    copyarray = new byte[1024];
                    System.arraycopy(image, i*1024, copyarray, 0, 1024);
                    //copyarray를 보내준다.
                }
                copyarray = new byte[1024];
                System.arraycopy(image, i*1024, copyarray, 0, image.length - i*1024);
                SocketManager.sendFile(image);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            File file = new File(musicDto.getPath());
            byte[] bytes = new byte[1024];
            int len = 0;
            int data = 0;
            try{
                fileInputStream = new FileInputStream(file);
                while((len = fileInputStream.read(bytes))>0){
                    data++;
                }

                for(;data > 0; data--) {
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
