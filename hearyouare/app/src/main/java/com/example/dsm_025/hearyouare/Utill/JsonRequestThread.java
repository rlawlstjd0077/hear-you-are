package com.example.dsm_025.hearyouare.Utill;

import com.example.dsm_025.hearyouare.Data.MusicDto;
import com.example.dsm_025.hearyouare.Manager.JsonManager;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by dsm_025 on 2017-01-04.
 */

public class JsonRequestThread extends Thread{
    private String msg;
    private InputStream im;
    private BufferedReader br;
    private String jsonData;
    private ArrayList<MusicDto> list;

    public JsonRequestThread(String msg){
        this.msg = msg;
    }

    public void run(){
        while(true){
            try {
                SocketManager.sendMsg(msg);
                im = SocketManager.getSocket().getInputStream();
                br = new BufferedReader(new InputStreamReader(im));
                char[] str = new char[1024];
                br.read(str);
                jsonData.concat(String.valueOf(str));
                if(jsonData.equals(""))
                    break;
                try{
                    list = JsonManager.jsonParser(jsonData);
                } catch (JSONException e) {
                    continue;
                }
                break;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public ArrayList<MusicDto> getList(){
        return this.list;
    }
}
