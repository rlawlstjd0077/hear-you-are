package com.example.dsm_025.hearyouare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.dsm_025.hearyouare.Activity.MainActivity;

import static com.example.dsm_025.hearyouare.R.layout.splashscreen;

/**
 * Created by 10102김동규 on 2017-01-07.
 */

public class Splash extends AppCompatActivity {
    private static final int SPLASH_DISPLAY_LENGTH = 5000;
    @Override
    public  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                overridePendingTransition(0, android.R.anim.fade_in);
                startActivity(new Intent(Splash.this, MainActivity.class));
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

//        Handler handler = new Handler(){
//            @Override
//            public  void handleMessage(Message msg){
//                Intent intent = new Intent(Splash.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        };
//        handler.sendEmptyMessageDelayed(0,SPLASH_DISPLAY_LENGTH);
    }
}
