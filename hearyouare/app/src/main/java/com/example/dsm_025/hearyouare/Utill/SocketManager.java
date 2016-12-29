package com.example.dsm_025.hearyouare.Utill;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by dsm_025 on 2016-12-20.
 */

public class SocketManager {
    public final static String HOST = "192.168.75.1";
    public static int PORT = 8801;

    private static Socket socket;

    public static Socket getSocket() throws IOException, InterruptedException {
        if(socket == null)
            socket = new Socket();

        if(!socket.isConnected()) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        socket.connect(new InetSocketAddress(HOST, PORT), 10000);
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
    public static void sendMsg(String msg) throws IOException, InterruptedException {
        getSocket().getOutputStream().write((msg + "\n").getBytes());
    }
    public static void sendFile(byte[] file) throws IOException, InterruptedException {
        while(file != null) {
            getSocket().getOutputStream().write(file, 0, file.length);
            getSocket().getOutputStream().flush();
        }
    }
}
