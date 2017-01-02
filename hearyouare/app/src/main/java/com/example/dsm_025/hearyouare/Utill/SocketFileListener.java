package com.example.dsm_025.hearyouare.Utill;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by GSD on 2016-12-30.
 */

public class SocketFileListener extends Thread {
    private InputStream im;
    private BufferedReader br;
    private byte[] file;
    Handler mHandler;

    Context context;

    public SocketFileListener(Context context, Handler handler) throws IOException, InterruptedException {
        this.mHandler = handler;
        this.context = context;

        im = SocketManager.getSocket().getInputStream();
        br = new BufferedReader(new InputStreamReader(im));
    }
    public void setFile(byte[] file){
        this.file = file;
    }
    @Override
    public void run(){
        super.run();



        try{
            String receivedmsg;
            SocketManager.sendFile(file);
            receivedmsg = br.readLine();
            Log.e("SocketListener", receivedmsg);
            Message msg = Message.obtain(mHandler, 0 ,0, 0, receivedmsg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
