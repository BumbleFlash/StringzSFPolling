package com.example.sudarshan.stringzsfpolling.models;


import android.content.Context;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class ProgressBarSettings {
    public static void showProgressBarOnButton(Button button, ProgressBar progressBar, Context context, int color, boolean show, String buttonText) {
        if(show) {
            showProgressBar(progressBar, context, color, true);
            button.setText("");
            button.setEnabled(false);
        } else {
            showProgressBar(progressBar, context, color, false);
            button.setText(buttonText);
            button.setEnabled(true);
        }
    }
    private static void showProgressBar(ProgressBar progressBar,Context context,int color,boolean show) {
        setProgressBarColor(color, progressBar, context);
        if (show)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);
    }
    private static void setProgressBarColor(int color, ProgressBar progressBar,Context context)
    {
        progressBar.getIndeterminateDrawable().setColorFilter(context.getResources().getColor(color), PorterDuff.Mode.MULTIPLY);
    }

}

