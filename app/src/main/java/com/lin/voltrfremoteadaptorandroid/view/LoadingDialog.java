package com.lin.voltrfremoteadaptorandroid.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.lin.voltrfremoteadaptorandroid.R;

import pl.droidsonroids.gif.GifImageView;

public class LoadingDialog {

    private Dialog dialog;
    private int resourceId;
    private String text;


    public LoadingDialog(Context context,int resourceId,String text) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false); // Set this to false if you don't want to allow cancellation
        this.text = text;
        this.resourceId = resourceId;
    }

    public void show() {
        View view = LayoutInflater.from(dialog.getContext()).inflate(R.layout.dialog_loading, null);
        GifImageView gifImageView = view.findViewById(R.id.loading_gif);
        // You can set a GIF programmatically if needed
         gifImageView.setImageResource(R.drawable.gif_loading);

        dialog.setContentView(view);
        dialog.show();
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}

