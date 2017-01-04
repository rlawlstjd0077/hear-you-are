package com.example.dsm_025.hearyouare.Utill;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by dsm_025 on 2016-12-20.
 */

public class SocketManager {
    public final static String HOST = "10.42.0.139";
    public static int PORT = 8805;

    private static Socket socket;
    private String message;
    private static InputStream im;
    private static BufferedReader br;

    public static Socket getSocket() throws IOException, InterruptedException {
        if(socket == null)
            socket = new Socket();

        if(!socket.isConnected()) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        socket.connect(new InetSocketAddress(HOST, PORT), 5000);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
            thread.join();
        }
        return socket;
    }
    public static void closeSocket()throws IOException{
        if(socket!=null)
            socket.close();
    }
    public static String receiveMsg() throws IOException, InterruptedException {
        im = SocketManager.getSocket().getInputStream();
        br = new BufferedReader(new InputStreamReader(im));
        return br.readLine();
    }
    public static void sendMsg(String msg) throws IOException, InterruptedException {
        getSocket().getOutputStream().write((msg + "\n").getBytes());
    }
    public static void sendFile(byte[] file) throws IOException, InterruptedException {
        while(file != null) {
            getSocket().getOutputStream().write(file);
            getSocket().getOutputStream().flush();
        }
    }
}
