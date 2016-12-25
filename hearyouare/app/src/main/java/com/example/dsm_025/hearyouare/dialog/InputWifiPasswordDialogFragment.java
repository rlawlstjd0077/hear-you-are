package com.example.dsm_025.hearyouare.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;

import com.example.dsm_025.hearyouare.R;

/**
 * Created by 김진성 on 2016-12-25.
 */

public class InputWifiPasswordDialogFragment extends DialogFragment{
    private PasswordInputListener listener;

    public static InputWifiPasswordDialogFragment newInstance(PasswordInputListener listener){
        InputWifiPasswordDialogFragment fragment = new InputWifiPasswordDialogFragment();
        fragment.listener = listener;
        return fragment;
    }

    public interface PasswordInputListener{
        void  onPassWordInputComplete(String name);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setTitle(R.string.dialog_input_wifi);
        final EditText password = new EditText(getActivity());
        builder.setView(password);

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onPassWordInputComplete(password.getText().toString());
            }
        }).setNegativeButton("취소", null);
        return builder.create();
    }
}
