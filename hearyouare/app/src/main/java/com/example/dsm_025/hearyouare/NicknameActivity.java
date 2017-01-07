package com.example.dsm_025.hearyouare;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dsm_025.hearyouare.Manager.DatabaseManager;
import com.example.dsm_025.hearyouare.Utill.Utill;
import com.sdsmdg.tastytoast.TastyToast;
import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

/**
 * Created by 10102김동규 on 2016-12-25.
 */

public class NicknameActivity extends AppCompatActivity{
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "Hero.otf"))
                .addBold(Typekit.createFromAsset(this, "a나무M.ttf"));

        final DatabaseManager DB = new DatabaseManager(getApplicationContext());
        setContentView(R.layout.activity_nickname);
        final EditText editText = (EditText) findViewById(R.id.editText);
        Button nameInputButton = (Button) findViewById(R.id.nameInputButton);
        nameInputButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String NickName = null;
                int Count = 0;
                NickName = editText.getText().toString();
                DB.insertInfo("insert into userinfo values('"+NickName+"', "+Count+");");
                TastyToast.makeText(getApplicationContext(), "닉네임 : " + DB.selectNickName(), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                setResult(1);
                finish();
            }
        });
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.activity_nickname,container,false);
        TextView textView;
        textView = (TextView) v.findViewById(R.id.firstViewTitle);
        Utill.setGlobalFont(getApplicationContext(), v);
        return v;
    }



    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
