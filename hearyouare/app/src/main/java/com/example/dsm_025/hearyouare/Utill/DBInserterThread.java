package com.example.dsm_025.hearyouare.Utill;

import android.content.Context;

import com.example.dsm_025.hearyouare.Data.MusicDto;
import com.example.dsm_025.hearyouare.Manager.DatabaseManager;

import java.util.ArrayList;

/**
 * Created by GSD on 2017-01-06.
 */

public class DBInserterThread extends Thread{
    DatabaseManager dbManager;
    ArrayList<MusicDto> list;
    public DBInserterThread(Context context, ArrayList<MusicDto> list){
        dbManager = new DatabaseManager(context);
        this.list = list;
    }

    @Override
    public void run() {
        for (MusicDto musicDto : list) {
            dbManager.insertMusic(musicDto);
        }
    }
}
