package com.example.dsm_025.hearyouare;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dsm_025.hearyouare.Data.DBHelper;

/**
 * Created by 10102김동규 on 2016-12-25.
 */

public class NicknameActivity extends AppCompatActivity{
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        final DBHelper dbHelper = new DBHelper(getApplicationContext(),"userinfo.db",null,1);
        setContentView(R.layout.activity_nickname);
        final EditText editText = (EditText) findViewById(R.id.editText);
        Button nameInputButton = (Button) findViewById(R.id.nameInputButton);
        nameInputButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Toast.makeText(getApplicationContext(), "눌린 값 : " + editText.getText().toString(), Toast.LENGTH_SHORT).show();
                dbHelper.insert(editText.getText().toString(),0);
                finish();
            }
        });
    }
}
