package com.example.dsm_025.hearyouare.Fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dsm_025.hearyouare.Manager.DatabaseManager;
import com.example.dsm_025.hearyouare.R;

/**
 * Created by 10102김동규 on 2016-11-26.
 */

public class ProfileFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        setHasOptionsMenu(true);
        return v;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        final DatabaseManager dbHelper = new DatabaseManager(getContext());
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        switch (item.getItemId()){
            case R.id.menu_search:
                //Intent intent = new Intent(ProfileFragment.this.getActivity(), NicknameActivity.class);
                //startActivity(intent);
                alert.setTitle("닉네임 재설정");
                alert.setMessage("메세지");
                final EditText input = new EditText(getActivity());
                alert.setView(input);

                alert.setPositiveButton("입력", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = input.getText().toString();
                        value.toString();
//                        dbHelper.updateNickName(value);
                        Toast.makeText(getContext(), "닉네임 : " + dbHelper.selectNickName(), Toast.LENGTH_SHORT).show();
                    }
                });
                alert.setNegativeButton("취소", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int whichButton){
                    }
                });
                alert.show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

}