package com.example.dsm_025.hearyouare.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.example.dsm_025.hearyouare.Component.MyRecyclerView;
import com.example.dsm_025.hearyouare.Data.MusicDto;
import com.example.dsm_025.hearyouare.Manager.DatabaseManager;
import com.example.dsm_025.hearyouare.R;
import com.example.dsm_025.hearyouare.Utill.AlbumRequestThread;
import com.example.dsm_025.hearyouare.Utill.JsonRequestThread;
import com.example.dsm_025.hearyouare.Utill.SocketListener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.ArrayList;

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
    private Toolbar toolbar;
    private View no_list_view;
    private View req_list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_list);
        toolbar = (Toolbar)findViewById(R.id.toolbar_reserve);
        toolbar.setTitle("예약 목록");

        mContext = getApplicationContext();
        databaseManager = new DatabaseManager(mContext);
        recyclerView = (MyRecyclerView) findViewById(R.id.recyclerview_reserve);
        recyclerView.setHasFixedSize(true);

        no_list_view = findViewById(R.id.view_no_reserve_list);
        req_list = findViewById(R.id.view_req_list);
        recyclerView.setEmptyView(req_list);

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
//        JsonRequestThread firstReq = new JsonRequestThread("/FIRST_REQ:");   //FirstReq
//        reqState = false;
//        firstReq.run();
//        try {
//            firstReq.join();
//            list = firstReq.getList();
//            currentPlayintInfo = list.get(0);
//            list.remove(0);
//            AlbumRequestThread albumRequest = new AlbumRequestThread(list);
//            albumRequest.start();
//            albumRequest.join();
//            list = albumRequest.getList();
//            showResult();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public void showResult(){
        if(firstData.equals("")){
            changeEmptyView(false);
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
    public void changeEmptyView(boolean state){
        //true : req_view
        //false : no_reserve_view
        if(state){
            no_list_view.setVisibility(View.INVISIBLE);
            recyclerView.setEmptyView(req_list);
        }else{
            req_list.setVisibility(View.INVISIBLE);
            recyclerView.setEmptyView(no_list_view);
        }
    }
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