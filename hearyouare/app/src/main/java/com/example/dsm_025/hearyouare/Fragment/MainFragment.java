package com.example.dsm_025.hearyouare.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dsm_025.hearyouare.Activity.ReserveListActivity;
import com.example.dsm_025.hearyouare.R;
import com.example.dsm_025.hearyouare.Utill.SocketListener;
import com.example.dsm_025.hearyouare.Utill.SocketManager;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutionException;

/**
 * Created by 10102김동규 on 2016-11-25.
 */
public class MainFragment extends Fragment {
    private Context mContent;
    private Handler mainHandler;
    private SocketListener sl;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_main, container, false);
        mContent = v.getContext();
        ImageView iv;
        iv = (ImageView)v.findViewById(R.id.speaker);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("onClick: ", "clicked");
                WifiManager wifiManager = (WifiManager)getContext().getSystemService(getContext().WIFI_SERVICE);
//                if(wifiManager.isWifiEnabled()) {
//                    Connector connector = new Connector(getContext());
//                    try {
//                        sl = new SocketListener(getContext(), mainHandler);
//                        connector.execute().get();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    } catch (ExecutionException e) {
//                        Toast.makeText(getContext(), "연결에 실패 하였습니다.", Toast.LENGTH_SHORT).show();
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    Toast.makeText(getContext(), "연결에 성공했습니다",Toast.LENGTH_SHORT).show();
//
//                    Intent intent = new Intent(MainFragment.this.getActivity(), ReserveListActivity.class);
//                    startActivity(intent);
//                }else{
//                    Toast.makeText(getContext(), "Wifi가 꺼져 있습니다.", Toast.LENGTH_SHORT).show();
//                }
                Intent intent = new Intent(MainFragment.this.getActivity(), ReserveListActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }
    class Connector extends AsyncTask<Void, Void, Void>{
        private ProgressDialog mDlg;
        private Context mContext;
        public Connector(Context context){
            mContext = context;
        }
        protected void onPreExecute() {
//            mDlg = new ProgressDialog(mContext);
//            mDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            mDlg.setMessage("연결 중입니다");
//            mDlg.show();
//            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Thread r = new Thread(){
                @Override
                public void run(){
                    try{
                        SocketManager.getSocket();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            r.start();
            try {
                r.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            WifiManager mng = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = mng.getConnectionInfo();
            String mac = info.getMacAddress();
            sl.setMsg("/REGISTER_NICKNAME:JinSeong:" + mac);
            sl.start();
            try {
                sl.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
//            mDlg.dismiss();
            super.onPostExecute(aVoid);
        }
    }
}
