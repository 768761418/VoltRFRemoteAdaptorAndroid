package com.lin.voltrfremoteadaptorandroid.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.lin.voltrfremoteadaptorandroid.ConfigData;
import com.lin.voltrfremoteadaptorandroid.R;

import pl.droidsonroids.gif.GifImageView;

public class PromptDialog {

    private Dialog dialog;
    private TextView titleView,textView,leftBtnView,rightBtnView;
    private View view;
    private LeftClickCallback leftClickCallback;
    private RightClickCallback rightClickCallback;



    //开放接口
    public interface LeftClickCallback{
        void onLeftClickCallback();
    }
    public interface RightClickCallback{
        void onRightClickCallback();
    }

    public void setLeftClickCallback(LeftClickCallback leftClickCallback){
        this.leftClickCallback = leftClickCallback;
    }
    public void setRightClickCallback(RightClickCallback rightClickCallback){
        this.rightClickCallback = rightClickCallback;
    }


//    构建函数
    public PromptDialog(Context context, String title, String text,String leftBtnText,String rightBtnText) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false); // Set this to false if you don't want to allow cancellation
        init( title, text,leftBtnText,rightBtnText);
    }
//    初始化
    private void init( String title, String text,String leftBtnText,String rightBtnText){
        view = LayoutInflater.from(dialog.getContext()).inflate(R.layout.dialog_prompt, null);
        titleView = view.findViewById(R.id.prompt_title);
        textView = view.findViewById(R.id.prompt_text);
        leftBtnView = view.findViewById(R.id.prompt_left_btn);
        rightBtnView = view.findViewById(R.id.prompt_right_btn);

//        设置文字
        titleView.setText(title);
        textView.setText(text);
        leftBtnView.setText(leftBtnText);
        rightBtnView.setText(rightBtnText);

//        交给其他Activity去定义点击事件
        leftBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (leftClickCallback != null){
                    leftClickCallback.onLeftClickCallback();
                }
                dismiss();
            }
        });
        rightBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rightClickCallback != null){
                    rightClickCallback.onRightClickCallback();
                }
                dismiss();
            }
        });
//        设置布局
        dialog.setContentView(view);
    }

//    设置文字颜色
    public void setColor(int type,int color){
        switch (type){
            case ConfigData.DIALOG_PROMPT_TITLE:
                titleView.setTextColor(color);
                break;
            case ConfigData.DIALOG_PROMPT_TEXT:
                textView.setTextColor(color);
                break;
            case ConfigData.DIALOG_PROMPT_LEFT_BTN:
                leftBtnView.setTextColor(color);
                break;
            case ConfigData.DIALOG_PROMPT_RIGHT_BTN:
                rightBtnView.setTextColor(color);
                break;
            default:
                return;
        }
    }


    public void show() {
        // 设置Dialog的背景为透明
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}

