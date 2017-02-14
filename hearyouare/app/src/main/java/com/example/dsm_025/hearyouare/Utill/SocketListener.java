package com.example.dsm_025.hearyouare.Utill;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by dsm_025 on 2016-12-20.
 */

public class SocketListener extends Thread {
    private InputStream im;
    private BufferedReader br;
    private String msg;
    Handler mHandler;

    Context context;

    public SocketListener(Context context, Handler handler) throws IOException, InterruptedException {
        this.mHandler = handler;
        this.context = context;

         im = SocketManager.getSocket().getInputStream();
         br = new BufferedReader(new InputStreamReader(im));
    }

    public void setMsg(String msg){
        this.msg = msg;
    }

    @Override
    public void run(){
        super.run();
        try{
            String receivedmsg;
            SocketManager.sendMsg(this.msg);
            receivedmsg = br.readLine();
            Log.e("SocketListener", "" + receivedmsg);
            Message msg = Message.obtain(mHandler, 0 ,0, 0, receivedmsg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
