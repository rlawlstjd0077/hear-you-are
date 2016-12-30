package com.example.dsm_025.hearyouare.Fragment;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dsm_025.hearyouare.Data.DBHelper;
import com.example.dsm_025.hearyouare.R;

import static android.support.v4.app.ActivityCompat.invalidateOptionsMenu;
import static com.example.dsm_025.hearyouare.R.menu.profile_menu;

/**
 * Created by 10102김동규 on 2016-11-26.
 */

public class ProfileFragment extends Fragment {
    private Toolbar toolbar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final DBHelper dbHelper = new DBHelper(getContext(),"userinfo.db",null,1);
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        setHasOptionsMenu(true);
        TextView textview;
        textview = (TextView) v.findViewById(R.id.text1);
        textview.setText("NickName : "+dbHelper.selectNickName()+"");
        TextView textview2;
        textview2 = (TextView) v.findViewById(R.id.text2);
        textview2.setText("PlayCount : "+dbHelper.selectPlayCount()+"");
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(profile_menu,menu);
        MenuItem Mitem  = menu.findItem(R.id.menu_search);
        Mitem.setVisible(false);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        final DBHelper dbHelper = new DBHelper(getContext(),"userinfo.db",null,1);
        switch (item.getItemId()){
            case R.id.profile_edit:
                //Intent intent = new Intent(ProfileFragment.this.getActivity(), NicknameActivity.class);
                //startActivity(intent);
                final EditText editText = new EditText(getActivity());
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("닉네임 재설정");
                dialog.setMessage("메세지");
                dialog.setView(editText);
                dialog.setPositiveButton("입력", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = editText.getText().toString()+"";
                        dbHelper.updateNickName(value);
                        Toast.makeText(getContext(), "닉네임 : " + dbHelper.selectNickName(), Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setNegativeButton("취소", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int whichButton){
                    }
                });
                dialog.show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

}