package com.example.dsm_025.hearyouare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.security.Permission;
import java.security.Permissions;
import java.util.ArrayList;
import java.util.jar.Manifest;

public class MainActivity extends ActionBarActivity implements NavigationDrawerCallbacks {


    private Toolbar mToolbar;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    MainFragment mainFragment;
    ProfileFragment profileFragment;
    final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_topdrawer);
        mToolbar = (Toolbar)findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        deleteDatabase("userinfo.db");
        final DBHelper dbHelper = new DBHelper(getApplicationContext(),"userinfo.db",null,1);
        SharedPreferences preference = getSharedPreferences("a",MODE_PRIVATE);
        int firstviewshow = preference.getInt("First",0);
        if (firstviewshow != 1){
            Intent intent = new Intent(MainActivity.this,NicknameActivity.class);
            startActivityForResult(intent,REQUEST_CODE);
        }

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        mNavigationDrawerFragment.closeDrawer();


        mainFragment = new MainFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, mainFragment)
                .commit();
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }

        };
        new TedPermission(this)
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(android.Manifest.permission.ACCESS_COARSE_LOCATION)
                .check();



    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        final DBHelper dbHelper = new DBHelper(getApplicationContext(),"userinfo.db",null,1);
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode != RESULT_OK){
            Toast.makeText(MainActivity.this,"결과가 성공이 아님",Toast.LENGTH_SHORT).show();
            return;
        }

        if(requestCode == REQUEST_CODE){
            Toast.makeText(getApplicationContext(), "닉네임 : " + dbHelper.selectNickName(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this,"REQUEST_CODE가 성공이 아님",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Toast.makeText(this, "Menu item selected -> " + position, Toast.LENGTH_SHORT).show();
        switch (position) {
            case 0:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new MainFragment())
                        .commit();
                        break;
            case 1:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new ProfileFragment())
                        .commit();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }
    public void allowPerfmission(){

    }

}
