package com.example.dsm_025.hearyouare.Manager;

import com.example.dsm_025.hearyouare.Data.MusicDto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dsm_025 on 2017-01-03.
 */

public class JsonManager {
    public static ArrayList<MusicDto> jsonParser(String jsonData) throws JSONException {
        MusicDto musicDto = new MusicDto();
        ArrayList<MusicDto> arrayList = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonData);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = (JSONObject) jsonArray.get(i);
            musicDto.setId(object.getInt("id"));
            musicDto.setTitle(object.get("song").toString());
            musicDto.setAlbum(object.get("album").toString());
            musicDto.setArtist(object.get("artist").toString());
            musicDto.setPath(object.get("playtime").toString());
            arrayList.add(musicDto);
        }
        return arrayList;
    }
}
