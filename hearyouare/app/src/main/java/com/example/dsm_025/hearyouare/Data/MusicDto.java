package com.example.dsm_025.hearyouare.Data;

/**
 * Created by dsm_025 on 2016-12-18.
 */


import java.io.Serializable;
import java.util.ArrayList;

public class MusicDto implements Serializable {
    private String id;
    private String albumId;
    private String title;
    private String artist;
    private String path;

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

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getTitle() {
        return title;
    }

    public String getPath() {return path;}
    @Override
    public String toString() {
        return "MusicDto{" +
                "id='" + id + '\'' +
                ", albumId='" + albumId + '\'' +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                '}';
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setPath(String path){this.path = path;}
}

