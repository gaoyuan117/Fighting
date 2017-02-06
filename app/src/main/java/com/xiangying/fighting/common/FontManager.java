package com.xiangying.fighting.common;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by HJ on 2015/12/1.
 */
public class FontManager {
    public static Typeface tf;

    public static void changeFonts(ViewGroup root, Activity act) {

        if (tf == null) {
            tf = Typeface.createFromAsset(act.getAssets(),
                    "fonts/HYQiH18030F45TTF.ttf");
        }
        for (int i = 0; i < root.getChildCount(); i++) {

            View v = root.getChildAt(i);

            if (v instanceof TextView) {
                ((TextView) v).setTypeface(tf);
            } else if (v instanceof Button) {
                ((Button) v).setTypeface(tf);
            } else if (v instanceof EditText) {
                ((EditText) v).setTypeface(tf);
            } else if (v instanceof ViewGroup) {
                changeFonts((ViewGroup) v, act);
            }

        }

    }

}