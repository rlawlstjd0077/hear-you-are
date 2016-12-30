package com.example.dsm_025.hearyouare.Data;

/**
 * Created by dsm_025 on 2016-12-18.
 */


import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class MusicDto implements Serializable {
    private String id;
    private String albumId;
    private String title;
    private String artist;
    private String path;
    private String album;
    private int playTime;

    public MusicDto() {
    }

    public MusicDto(String id, String albumId, String title, String artist) {
        this.id = id;
        this.albumId = albumId;
        this.title = title;
        this.artist = artist;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlbumId() {
        return albumId;
    }

    public String getTitle() {
        return title;
    }

    public String getPath() {return path;}

    public String getAlbum(){return album;}

    public String getArtist() {
        return artist;
    }

    public int getPlayTime(){return playTime;}
    @Override
    public String toString() {
        return "MusicDto{" +
                "id='" + id + '\'' +
                ", albumId='" + albumId + '\'' +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                '}';
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setPath(String path){this.path = path;}

    public void setAlbum(String albeum){this.album = albeum;}

    public void setPlayTime(int time){
        this.playTime = time;
    }

    public String jsonBinder() {
        JSONObject musicData = new JSONObject();
        try {
            musicData.put("name", title);
            musicData.put("singer", artist);
            musicData.put("album", album);
            musicData.put("playtime", playTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return musicData.toString();
    }

    public void jsonParser(String jsonData){
        try {
            JSONObject musicData = new JSONObject(jsonData);
            this.title = (musicData.get("song").toString());
            this.album = (musicData.get("album").toString());
            this.artist = (musicData.get("artist").toString());
            this.playTime = (Integer.parseInt(musicData.get("playtime").toString()));
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
}