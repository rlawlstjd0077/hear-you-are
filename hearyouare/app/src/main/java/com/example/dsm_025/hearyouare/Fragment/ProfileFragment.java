package com.example.dsm_025.hearyouare.Fragment;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dsm_025.hearyouare.Activity.MainActivity;
import com.example.dsm_025.hearyouare.Manager.DatabaseManager;
import com.example.dsm_025.hearyouare.NicknameActivity;
import com.example.dsm_025.hearyouare.R;
import com.example.dsm_025.hearyouare.Utill.Utill;
import com.sdsmdg.tastytoast.TastyToast;

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
        final DatabaseManager DB = new DatabaseManager(getContext());
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        Utill.setGlobalFont(getActivity(), v);
        setHasOptionsMenu(true);
        TextView textview;
        textview = (TextView) v.findViewById(R.id.text1);
        textview.setText("NickName : " + DB.selectNickName() + "");
        TextView textvieww;
        textvieww = (TextView) v.findViewById(R.id.text2);
        textvieww.setText("PlayCount : " + DB.selectPlayCount() + "");
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

//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
       final DatabaseManager DB = new DatabaseManager(getContext());
        switch (item.getItemId()){
            case R.id.profile_edit:
                final EditText editText = new EditText(getActivity());
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("닉네임 재설정");
                dialog.setMessage("메세지");
                dialog.setView(editText);
                dialog.setPositiveButton("입력", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = editText.getText().toString()+"";
                        DB.updateNickName("update userinfo set nickname = '"+value+"';");
                        TastyToast.makeText(getContext(), "닉네임 : " + DB.selectNickName(), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                        ((MainActivity)getActivity()).updateNickNameNav();
//                        Fragment frg = null;
//                        frg = getFragmentManager().findFragmentByTag("Your_Fragment_TAG");
//                        final FragmentTransaction ft = getFragmentManager().beginTransaction();
//                        ft.detach(frg);
//                        ft.attach(frg);
//                        ft.commit();

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
//        Fragment newFragment = new ProfileFragment();
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.replace(R.id.text1, newFragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
        return true;
    }
}