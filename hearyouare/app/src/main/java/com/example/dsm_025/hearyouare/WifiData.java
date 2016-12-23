package com.example.dsm_025.hearyouare;

/**
 * Created by dsm_025 on 2016-12-01.
 */

public class WifiData {
    private String ssid;
    private int level;



    public void setSSID(String ssid){
        this.ssid = ssid;
    }
    public void setLevel(int level){
        this.level = level;
    }
    public String getSSID(){
        return this.ssid;
    }
    public int getLevel(){
        return this.level;
    }
}
