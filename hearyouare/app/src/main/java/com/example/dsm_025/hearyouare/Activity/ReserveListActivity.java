package com.example.dsm_025.hearyouare.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.InterpolatorRes;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ListView;

import com.example.dsm_025.hearyouare.Component.MyRecyclerView;
import com.example.dsm_025.hearyouare.Data.MusicDto;
import com.example.dsm_025.hearyouare.Manager.DatabaseManager;
import com.example.dsm_025.hearyouare.Manager.JsonManager;
import com.example.dsm_025.hearyouare.R;
import com.example.dsm_025.hearyouare.Utill.AlbumRequestThread;
import com.example.dsm_025.hearyouare.Utill.JsonRequestThread;
import com.example.dsm_025.hearyouare.Utill.SocketListener;
import com.example.dsm_025.hearyouare.Utill.SocketManager;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by dsm_025 on 2016-12-22.
 */

public class ReserveListActivity extends AppCompatActivity{
    private ListView listView;
    public static ArrayList<MusicDto> list;
    private Handler mainHandler;
    private SocketListener sl;
    private Context mContext;
    private MyRecyclerView recyclerView;
    private InputStream im;
    private BufferedReader br;
    private String firstData = "";
    private DatabaseManager databaseManager;
    private boolean reqState;
    private MusicDto currentPlayintInfo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_list);
        mContext = getApplicationContext();
        databaseManager = new DatabaseManager(mContext);
        recyclerView = (MyRecyclerView) findViewById(R.id.recyclerview_reserve);
        recyclerView.setHasFixedSize(true);

        View emptyView = findViewById(R.id.view_action_progress);
        recyclerView.setEmptyView(emptyView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, 1));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                while(reqState){
                    //progreebar 띄워준다.
                }
                Intent intent = new Intent(ReserveListActivity.this, MusicListActivity.class);
                startActivity(intent);
            }
        });
        JsonRequestThread firstReq = new JsonRequestThread("/FIRST_REQ:");   //FirstReq
        reqState = false;
        firstReq.run();
        try {
            firstReq.join();
            list = firstReq.getList();
            currentPlayintInfo = list.get(0);
            list.remove(0);
            AlbumRequestThread albumRequest = new AlbumRequestThread(list);
            albumRequest.start();
            albumRequest.join();
            list = albumRequest.getList();
            showResult();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void showResult(){
        if(firstData.equals("")){
            //데이터가 없을 때 예약 리스트가 비었을 때
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //ckeckconn 생성
        }else{
            TimmerThread timmerThread = new TimmerThread(list, currentPlayintInfo.getPlayTime());
            timmerThread.start();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //ckeckconn 생성
            //먼저 뿌려 주고 DB에 넣는다
        }
    }

    Thread checkConn = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                JsonRequestThread jsonRequestThread = new JsonRequestThread("");
                //마지막 ID 구해서 넣어준다.
                jsonRequestThread.start();
                try {
                    jsonRequestThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ArrayList<MusicDto> checkList = jsonRequestThread.getList();
                if(checkList != null) {
                    AlbumRequestThread albumReqThread = new AlbumRequestThread(checkList);
                    albumReqThread.start();
                    try {
                        albumReqThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    list.addAll(albumReqThread.getList());
                    //뿌려주고 DB에 넣어준다.
                } else {
                    //변한게 없다.
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });
    class TimmerThread extends Thread{
        private ArrayList<Integer> list;
        private int currentTime;
        private int totalTime = 0;

        public TimmerThread(ArrayList<MusicDto> musicDtos, int spent){
            list = new ArrayList<>();
            for(MusicDto temp : musicDtos){
                int time = temp.getPlayTime();
                list.add(time);
                totalTime += time;
            }
            currentTime = spent;
        }
        public void addList(ArrayList<MusicDto> musicDtos){
            ArrayList<Integer> tempList = new ArrayList<>();
            for(MusicDto temp : musicDtos){
                int time = temp.getPlayTime();
                list.add(time);
                totalTime += time;
            }
        }
        @Override
        public void run() {
            for(int i = 0; i < list.size(); i++){
                while(list.get(i) == currentTime){
                    try {
                        currentTime++;
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                currentTime = 0;
                //노래 하나가 끝남.
            }
        }
    }
}