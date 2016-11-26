package com.example.dsm_025.hearyouare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.Space;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by 10102김동규 on 2016-11-25.
 */
public class MainFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        ImageView iv;

        iv = (ImageView)v.findViewById(R.id.speaker);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"버튼을 눌렀습니다.",Toast.LENGTH_LONG).show();
                SpeakerScanFragment speakerScanFragment = SpeakerScanFragment.newInstance();
                speakerScanFragment.show(((AppCompatActivity)getContext()).getSupportFragmentManager(), null);
            }
        });
        return v;
    }

}
