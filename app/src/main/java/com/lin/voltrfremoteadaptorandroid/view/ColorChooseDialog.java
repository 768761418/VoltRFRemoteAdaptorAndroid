package com.lin.voltrfremoteadaptorandroid.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.lin.voltrfremoteadaptorandroid.R;
import com.lin.voltrfremoteadaptorandroid.Utils.SharedPreferencesUtils;
import com.lin.voltrfremoteadaptorandroid.setting.ApplicationSetting;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class ColorChooseDialog extends Dialog {
    ImageView cancel,done;
    CancelOnclickListener cancelOnclickListener;
    DoneOnclickListener doneOnclickListener;
    private final String TAG = "ColorChooseDialog";
    private SharedPreferencesUtils sharedPreferencesUtils;

    int color;
    private ColorPickerView colorPickerView;

    public ColorChooseDialog(@NonNull Context context) {
        super(context);

    }

    public interface CancelOnclickListener{
        void onCancelOnclickListener();
    }
    public interface DoneOnclickListener{
        void onDoneOnclickListener();
    }

    public void setColor(int color){
        this.color =color;
        colorPickerView.setOnDrawCompletedCallback(new Runnable() {
            @Override
            public void run() {
                colorPickerView.setStartColor(color);
            }
        });
    }

    @Override
    public void dismiss(){
        super.dismiss();
        colorPickerView.setIsDraw(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_color_choose);
        initUi();
        colorPickerView = findViewById(R.id.dialog_color_picker);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cancelOnclickListener != null){
                    cancelOnclickListener.onCancelOnclickListener();
                }
                dismiss();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (doneOnclickListener != null){
                    doneOnclickListener.onDoneOnclickListener();
                }
                dismiss();
            }
        });
    }


    private void initUi(){
        cancel = findViewById(R.id.dialog_cancel);
        done = findViewById(R.id.dialog_done);
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(getContext());
        // 设置对话框的动画效果
        getWindow().setWindowAnimations(R.style.dialog_animation);
        // 设置对话框的宽度
        Window window = getWindow();
        // 把 DecorView 的默认 padding 取消，同时 DecorView 的默认大小也会取消
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        // 设置宽度
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setGravity(Gravity.BOTTOM);
        window.setAttributes(layoutParams);
        // 给 DecorView 设置背景颜色，很重要，不然导致 Dialog 内容显示不全，有一部分内容会充当 padding，上面例子有举出
        window.getDecorView().setBackgroundColor(Color.WHITE);
        window.setAttributes(layoutParams);
    }

    public void setNewPresuppose(int presupposeId){
        Log.d(TAG, "selectedColor: "+"klkl" +presupposeId);
       colorPickerView.setKeyUpListener(new ColorPickerView.KeyUpListener() {
           @Override
           public void saveColor(int color, int touchX, int touchY) {
               if (presupposeId == 1){
                   sharedPreferencesUtils.saveIntData(ApplicationSetting.PRESUPPOSE_ONE,color);
                   Log.d(TAG, "selectedColor: "+"klkl");
               }
           }
       });
    }


    public void setCancelOnclickListener(CancelOnclickListener cancelOnclickListener) {
        this.cancelOnclickListener = cancelOnclickListener;
    }

    public void setDoneOnclickListener(DoneOnclickListener doneOnclickListener) {
        this.doneOnclickListener = doneOnclickListener;
    }


}
