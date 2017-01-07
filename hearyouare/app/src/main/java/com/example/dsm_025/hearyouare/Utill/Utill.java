package com.example.dsm_025.hearyouare.Utill;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by 10102김동규 on 2017-01-06.
 */

public class Utill {
    public static void setGlobalFont(Context context, View view){
        if (view != null) {
            if (view instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) view;
                int len = vg.getChildCount();
                for (int i = 0; i < len; i++) {
                    View v = vg.getChildAt(i);
                    if (v instanceof TextView) {
                        ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "a나무M.ttf"));
                    }
                    setGlobalFont(context, v);
                }
            }
        } else {
            Log.i("This is null","이것은 null 입니다.");
        }
    }
}
