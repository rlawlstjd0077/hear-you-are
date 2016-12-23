package com.example.dsm_025.hearyouare.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.example.dsm_025.hearyouare.Data.MusicDto;
import com.example.dsm_025.hearyouare.R;
import com.example.dsm_025.hearyouare.Utill.SocketListener;
import com.example.dsm_025.hearyouare.Utill.SocketManager;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_list);
        sl = new SocketListener(this, mainHandler);

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
