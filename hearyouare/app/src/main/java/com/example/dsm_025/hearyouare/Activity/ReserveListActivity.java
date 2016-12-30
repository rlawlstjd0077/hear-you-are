package com.example.dsm_025.hearyouare.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ListView;

import com.example.dsm_025.hearyouare.Component.MyRecyclerView;
import com.example.dsm_025.hearyouare.Data.MusicDto;
import com.example.dsm_025.hearyouare.R;
import com.example.dsm_025.hearyouare.Utill.SocketListener;
import com.example.dsm_025.hearyouare.Utill.SocketManager;

import java.io.IOException;
import java.net.Socket;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_list);
        mContext = getApplicationContext();

        recyclerView = (MyRecyclerView) findViewById(R.id.recyclerview_reserve);
        recyclerView.setHasFixedSize(true);

        View emptyView = findViewById(R.id.view_action_progress);
        recyclerView.setEmptyView(emptyView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, 1));

        try {
            sl = new SocketListener(this, mainHandler);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReserveListActivity.this, MusicListActivity.class);
                startActivity(intent);
            }
        });
    }
}
