package com.lin.voltrfremoteadaptorandroid.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.lin.voltrfremoteadaptorandroid.R;

public class ColorChooseDialog extends Dialog {
    ImageView cancel,done;
    CancelOnclickListener cancelOnclickListener;
    DoneOnclickListener doneOnclickListener;
    int color;

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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_color_choose);
        initUi();
        ColorPickerView colorPickerView = findViewById(R.id.dialog_color_picker);
        colorPickerView.externalChangePresuppose(color);
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

    public void setCancelOnclickListener(CancelOnclickListener cancelOnclickListener) {
        this.cancelOnclickListener = cancelOnclickListener;
    }

    public void setDoneOnclickListener(DoneOnclickListener doneOnclickListener) {
        this.doneOnclickListener = doneOnclickListener;
    }
}
