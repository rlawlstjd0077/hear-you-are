package com.example.dsm_025.hearyouare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dsm_025.hearyouare.Manager.DBHelper;

/**
 * Created by 10102김동규 on 2016-12-25.
 */

public class NicknameActivity extends AppCompatActivity{
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nickname);
        final EditText editText = (EditText) findViewById(R.id.editText);
        Button nameInputButton = (Button) findViewById(R.id.nameInputButton);

        final DBHelper dbHelper = new DBHelper(getApplicationContext(),"userinfo.db",null,1);
        nameInputButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String NickName = editText.getText().toString();
                dbHelper.insert(editText.getText().toString(),0);
                Intent intent = new Intent();
                intent.putExtra("result_msg","결과가 넘어간다.");
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
