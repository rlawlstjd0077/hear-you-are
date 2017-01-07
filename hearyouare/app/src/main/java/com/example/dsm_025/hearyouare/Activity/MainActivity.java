package com.example.dsm_025.hearyouare.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.dsm_025.hearyouare.Fragment.MainFragment;
import com.example.dsm_025.hearyouare.Manager.DatabaseManager;
import com.example.dsm_025.hearyouare.NavigationDrawerCallbacks;
import com.example.dsm_025.hearyouare.Fragment.NavigationDrawerFragment;
import com.example.dsm_025.hearyouare.Fragment.ProfileFragment;
import com.example.dsm_025.hearyouare.NicknameActivity;
import com.example.dsm_025.hearyouare.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity implements NavigationDrawerCallbacks {


    private Toolbar mToolbar;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    MainFragment mainFragment;
    ProfileFragment profileFragment;
    private DatabaseManager DB;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            updateNickNameNav();
        }
    }

    public void updateNickNameNav() {
        mNavigationDrawerFragment.updateNickname(DB.selectNickName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DB = new DatabaseManager(getApplicationContext());
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "Hero.otf"));
        setContentView(R.layout.activity_main_topdrawer);


        mToolbar = (Toolbar)findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        deleteDatabase("hya.db");
        SharedPreferences preference = getSharedPreferences("a",MODE_PRIVATE);
        int firstviewshow = preference.getInt("First",0);
        if (firstviewshow != 1){
            Intent intent = new Intent(MainActivity.this,NicknameActivity.class);
            startActivityForResult(intent, 1);
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
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            }

        };
        new TedPermission(this)
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
        new TedPermission(this)
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(android.Manifest.permission.ACCESS_COARSE_LOCATION)
                .check();
        new TedPermission(this)
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_PHONE_STATE)
                .check();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
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
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
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