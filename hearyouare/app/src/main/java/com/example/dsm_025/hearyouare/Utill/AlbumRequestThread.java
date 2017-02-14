package com.example.dsm_025.hearyouare.Utill;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.example.dsm_025.hearyouare.Data.MusicDto;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by dsm_025 on 2017-01-04.
 */

public class AlbumRequestThread extends Thread{
    private ArrayList<MusicDto> list;
    private InputStream im;
    private BufferedReader br;
    private ArrayList<byte[]> recievedDatas;

    public AlbumRequestThread(ArrayList<MusicDto> list){
        this.list = list;
    }

    public ArrayList<MusicDto> getList(){
        return this.list;
    }

    public void run(){
        String recieveMsg = null;
        for(int i = 0; i < list.size(); i++) {
            while (true) {
                try {
                    SocketManager.sendMsg("" + list.get(i).getId());
                    im = SocketManager.getSocket().getInputStream();
                    byte[] temp = new byte[1024];
                    im.read(temp);
                    String msg = new String(temp);
                    if(temp.toString().equals("")){
                        break;
                    }
                    recievedDatas.add(temp);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            ByteArrayOutputStream outputStream = null;
            for(int j = 0; j < list.size(); j++){
                outputStream = new ByteArrayOutputStream( );
                try {
                    outputStream.write(recievedDatas.get(j));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            list.get(i).setImage(getPathFromSavedImage(outputStream.toByteArray(), list.get(i).getId() + ""));
        }
    }
    public String getPathFromSavedImage(byte[] image, String name){
        OutputStream outStream = null;
        String extStorageDirectory =
                Environment.getExternalStorageDirectory().toString();

        File file = new File(extStorageDirectory, name);
        try{
            outStream = new FileOutputStream(file);
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getPath();
    }
}
