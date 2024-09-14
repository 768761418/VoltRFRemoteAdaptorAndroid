package com.lin.voltrfremoteadaptorandroid.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.lin.voltrfremoteadaptorandroid.ConfigData;
import com.lin.voltrfremoteadaptorandroid.R;

public class InputDialog {

    private Dialog dialog;
    private TextView titleView,textView,leftBtnView,rightBtnView;
    private View view;
    private EditText editText;
    private LeftClickCallback leftClickCallback;
    private RightClickCallback rightClickCallback;



    //开放接口
    public interface LeftClickCallback{
        void onLeftClickCallback(String editMessage);
    }
    public interface RightClickCallback{
        void onRightClickCallback(String editMessage);
    }

    public void setLeftClickCallback(LeftClickCallback leftClickCallback){
        this.leftClickCallback = leftClickCallback;
    }
    public void setRightClickCallback(RightClickCallback rightClickCallback){
        this.rightClickCallback = rightClickCallback;
    }


    /***
     * 全自定义的的构造函数
     ***/
    public InputDialog(Context context, String title, String text, String leftBtnText, String rightBtnText) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true); // Set this to false if you don't want to allow cancellation
        init( title, text,leftBtnText,rightBtnText,null);
    }
    /***
     * 全自定义的的构造函数
     * 携带edit默认内容的参数
     ***/
    public InputDialog(Context context, String title, String text, String leftBtnText, String rightBtnText,String edit) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true); // Set this to false if you don't want to allow cancellation
        init( title, text,leftBtnText,rightBtnText,edit);
    }


    /***
     * 默认左按钮为 cancel
     * 右按钮为 save
     * 的构造函数
    ***/
    public InputDialog(Context context, String title, String text) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true); // Set this to false if you don't want to allow cancellation
        init( title, text,null,null,null);
    }

    /***
     * 默认左按钮为 cancel
     * 右按钮为 save
     * 且携带edit默认内容的参数
     * 的构造函数
     ***/
    public InputDialog(Context context, String title, String text,String edit) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true); // Set this to false if you don't want to allow cancellation
        init( title, text,null,null,edit);
    }



//    初始化
    private void init( String title, String text,String leftBtnText,String rightBtnText,String edit){
        view = LayoutInflater.from(dialog.getContext()).inflate(R.layout.dialog_input, null);
        titleView = view.findViewById(R.id.input_title);
        textView = view.findViewById(R.id.input_text);
        leftBtnView = view.findViewById(R.id.input_left_btn);
        rightBtnView = view.findViewById(R.id.input_right_btn);
        editText = view.findViewById(R.id.input_message);

//        设置文字
        titleView.setText(title);
        textView.setText(text);
        if (edit != null){
            editText.setText(edit);
        }
        if (leftBtnText != null){
            leftBtnView.setText(leftBtnText);
        }
        if (rightBtnText != null){
            rightBtnView.setText(rightBtnText);
        }

//        交给其他Activity去定义点击事件
        leftBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                获取输入框的内容，如果有内容就修改没内容就直接消失
                String editMessage = editText.getText().toString();
                if (leftClickCallback != null){
                    leftClickCallback.onLeftClickCallback(editMessage);
                }
                dismiss();
            }
        });
        rightBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editMessage = editText.getText().toString();
                if (rightClickCallback != null){
                    rightClickCallback.onRightClickCallback(editMessage);
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

