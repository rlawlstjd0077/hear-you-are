package com.example.dsm_025.hearyouare.Manager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.dsm_025.hearyouare.Data.MusicDto;

/**
 * Created by 10102김동규 on 2016-12-27.
 */
public class DatabaseManager{
    public static final String DB_NAME = "hya.db";
    public static final int DB_VERSION = 2;

    public static final String INFO_TABLE = "userinfo";
    public static final String NICKNAME = "nickname";
    public static final String PLAY_COUNT = "playcount";

    public static final String MUSIC_TABLE = "music";
    public static final String MUSIC_ID = "music_id";
    public static final String TITLE = "title";
    public static final String ARTIST = "artist";
    public static final String ALBUM = "album";
    public static final String PLAY_TIME = "play_time";
    public static final String IMAGE = "image";
    private Context mContext;
    private DBHelper dbHelper;

    public DatabaseManager(Context context){
        mContext = context;
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String db_name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, db_name, factory, version);
        }

            @Override
            public void onCreate(SQLiteDatabase db) {
                String sql = "CREATE TABLE BASICDATA(NickName TEXT PRIMARY KEY, PlayCount INTEGER)";
                db.execSQL(sql);

                sql = "CREATE TABLE " + MUSIC_TABLE + " ("
                        + MUSIC_ID + " INT NOT NULL PRIMARY KEY UNIQUE, "
                        + TITLE + " TEXT NOT NULL, "
                        + ARTIST + " TEXT, "
                        + ALBUM + " TEXT, "
                        + PLAY_TIME + "INT, "
                        + IMAGE + " BLOB);";
                db.execSQL(sql);
            }

            public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
                //DB 업그레이드를 위해 버전 변경시 호출되는 함수
                //버전 업그레이드시 있던 테이블 삭제 후 다시 생성
                String sql = "drop table if exists BASICDATA";
                DB.execSQL(sql);
                onCreate(DB);
            }
        }
        public void create(){
            dbHelper = new DBHelper(mContext, DB_NAME, null, DB_VERSION);
        }

        public void insertMusic(MusicDto musicData){
            SQLiteDatabase DB = dbHelper.getWritableDatabase();
            String sql = "INSERT INTO " + MUSIC_TABLE +" ("
                    + MUSIC_ID + ", " + TITLE + ", " + ARTIST + ", " + ALBUM
                    + ", " + PLAY_TIME + ", " + IMAGE + ") "
                    + "VALUSE (" +  musicData.getId().toString() + ", '" + musicData.getTitle() + "', '"
                    + musicData.getArtist() + "', '" + musicData.getAlbum() + "', "
                    + musicData.getPlayTime() + ", '" + musicData.getImage() + ");";
            DB.execSQL(sql);
        }



        public void insert(String NickName,int PlayCount) {
            //읽고 쓰기가 가능하게 DB 열기
            SQLiteDatabase DB = dbHelper.getWritableDatabase();
            PlayCount = 0;
            //DB에 입력한 값으로 행 추가
            String sql = "INSERT INTO BASICDATA VALUES('" + NickName + "'," + PlayCount + ");";
            Log.i("insert: ", sql);
            DB.execSQL(sql);
            DB.close();
        }
            public void updataNickName(String nickname){
            //읽고 쓰기가 가능하게 DB 열기
            SQLiteDatabase DB = dbHelper.getWritableDatabase();
            //입력한 항목의 행의 정보 수정
            String sql = "UPDATE BASICDATA SET NickName='"+nickname+"';";
            DB.execSQL(sql);
            DB.close();
        }

        public void addPlayCount(){
            //읽고 쓰기가 가능하게 DB 열기
            SQLiteDatabase DB = dbHelper.getWritableDatabase();
            //입력한 항목의 행의 정보 수정
            String sql = "UPDATE BASICDATA SET PlayCount=PlayCount+1;";
            DB.execSQL(sql);
            DB.close();
        }

        public String selectNickName(){
            SQLiteDatabase DB = dbHelper.getReadableDatabase();
            String NickName = null;
            Cursor cursor = DB.query("BASICDATA",null,null,null,null,null,null);
            while(cursor.moveToNext()){
                NickName = cursor.getString(cursor.getColumnIndex("NickName"));
            }
            return NickName;
        }

        public int selectPlayCount() {
            SQLiteDatabase DB = dbHelper.getReadableDatabase();
            Cursor cursor = DB.query("BASICDATA", null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                int PlayCount = cursor.getInt(cursor.getColumnIndex("PlayCount"));
            }
            return cursor.getInt(cursor.getColumnIndex("PlayCount"));
        }
}