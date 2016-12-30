package com.example.dsm_025.hearyouare.Data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

/**
 * Created by 10102김동규 on 2016-12-27.
 */
public class DBHelper extends SQLiteOpenHelper{
    public DBHelper(Context context, String db_name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,db_name,factory,version);
        //DBHelper의 생성자로 관리할 DB 이름과 버전 정보를 받는다.
    }

    @Override
    public void onCreate(SQLiteDatabase DB){
        String sql = "CREATE TABLE BASICDATA(NickName TEXT PRIMARY KEY NOT NULL, PlayCount INTEGER AUTOINCREMENT)";
        DB.execSQL(sql);
        //새로운 테이블 생성
        //테이블 이름은 BASICDATA
        //NickName 문자열 컬럼과, 자동으로 값이 증가하는 PlayCount 정수형 기본키 컬럼 생성
    }

    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion){
        //DB 업그레이드를 위해 버전 변경시 호출되는 함수
        //버전 업그레이드시 있던 테이블 삭제 후 다시 생성
        String sql = "drop table if exists BASICDATA";
        DB.execSQL(sql);
        onCreate(DB);
    }

    public void insert(String NickName,int PlayCount){
        //읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase DB = getWritableDatabase();
        PlayCount = 0;
        //DB에 입력한 값으로 행 추가
        String sql = "INSERT INTO BASICDATA (nickname) VALUES('" + NickName + "');";
        Log.i("insert: ", sql);
        DB.execSQL(sql);
        DB.close();
    }

    public void updateNickName(String NickName){
        //읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase DB = getWritableDatabase();
        //입력한 항목의 행의 정보 수정
        try {
            String sql = "UPDATE BASICDATA SET NickName='"+NickName+"' where playcount = 1";
            DB.execSQL(sql);
        }catch (Exception e){
            Log.i("알림", "제대로 처리되지 않음");
        }
        DB.close();
    }

    public void addPlayCount(){
        //읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase DB = getWritableDatabase();
        //입력한 항목의 행의 정보 수정
        try {
            String sql = "UPDATE BASICDATA SET PlayCount=PlayCount+1;";
            DB.execSQL(sql);
        }catch (Exception e){
            Log.i("알림", "제대로 처리되지 않음");
        }
        DB.close();
    }

    public String selectNickName(){
        SQLiteDatabase DB = getReadableDatabase();
        String NickName = null;
        String id = null;
        /*
        Cursor cursor = DB.query("BASICDATA",null,null,null,null,null,null);
        while(cursor.moveToNext()){
            NickName = cursor.getString(cursor.getColumnIndex("NickName"));
            id = cursor.getString(cursor.getColumnIndex("playcount"));
        }

        cursor.close();
        DB.close();
        Log.i("TAG", "selectNickName: " + NickName + id);
        return NickName;
        */
        String sql = "select * from basicdata where playcount = 1";
        Cursor result = DB.rawQuery(sql, null);
        if (result.moveToFirst()) {
            NickName = result.getString(0);
            id = result.getString(1);
            Log.i("TAG", "selectNickName: " + NickName + "  " + id);
        }

        result.close();
        return NickName;
    }

    public String selectPlayCount(){
        String PlayCount=null;
        SQLiteDatabase DB = getReadableDatabase();
        Cursor cursor = DB.query("BASICDATA",null,null,null,null,null,null);
        while(cursor.moveToNext()){
            PlayCount = ""+cursor.getInt(cursor.getColumnIndex("PlayCount"));
        }
        return PlayCount;
    }
}