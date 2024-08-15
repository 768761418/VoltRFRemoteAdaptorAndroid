package com.lin.voltrfremoteadaptorandroid.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.lin.voltrfremoteadaptorandroid.Activity.colorControl.RemoteActivity;
import com.lin.voltrfremoteadaptorandroid.R;
import com.lin.voltrfremoteadaptorandroid.Utils.MessageUtils;
import com.lin.voltrfremoteadaptorandroid.databinding.ModuleTopBarBinding;

public class TopBarModule extends LinearLayout {

    ModuleTopBarBinding moduleTopBarBinding;
    private OnLeftIconOnClickListener onLeftIconOnClickListener;
    private OnMidIconOnClickListener onMidIconOnClickListener;
    private OnRightIconOnClickListener onRightIconOnClickListener;

    private int switchStatus = 0;



//    开放点击接口
    public interface  OnLeftIconOnClickListener{
        void leftIconOnClick();
    }
    public interface  OnMidIconOnClickListener{
        void midIconOnClick();
    }
    public interface  OnRightIconOnClickListener{
        void rightIconOnClick();
    }

    public void setOnLeftIconOnClickListener(OnLeftIconOnClickListener onLeftIconOnClickListener){
        this.onLeftIconOnClickListener = onLeftIconOnClickListener;
    }
    public void setOnMidIconOnClickListener(OnMidIconOnClickListener onMidIconOnClickListener){
        this.onMidIconOnClickListener = onMidIconOnClickListener;
    }
    public void setOnRightIconOnClickListener(OnRightIconOnClickListener onRightIconOnClickListener){
        this.onRightIconOnClickListener = onRightIconOnClickListener;
    }


    public TopBarModule(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        moduleTopBarBinding = DataBindingUtil.inflate(layoutInflater, R.layout.module_top_bar,this,true);
//        关闭按钮点击事件
        moduleTopBarBinding.comeBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)context).finish();
            }
        });
//        开放接口在其他Activity中设置点击事件
        moduleTopBarBinding.iconLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onLeftIconOnClickListener != null){
                    onLeftIconOnClickListener.leftIconOnClick();
                }
            }
        });
        moduleTopBarBinding.iconMid.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMidIconOnClickListener != null){
                    onMidIconOnClickListener.midIconOnClick();
                }
            }
        });
        moduleTopBarBinding.iconRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRightIconOnClickListener != null){
                    onRightIconOnClickListener.rightIconOnClick();
                }
            }
        });
    }


    public void setIconLeftImg(int imgId){
         moduleTopBarBinding.iconLeft.setVisibility(VISIBLE);
         moduleTopBarBinding.iconLeft.setImageResource(imgId);
    }
    public ImageView iconLeftImg(){
        return  moduleTopBarBinding.iconLeft;
    }
    public void hideIconLeftImg(){
        moduleTopBarBinding.iconLeft.setVisibility(INVISIBLE);
    }

    public void setIconMidImg(int imgId){
        moduleTopBarBinding.iconMid.setVisibility(VISIBLE);
        moduleTopBarBinding.iconMid.setImageResource(imgId);
    }
    public void hideIconMidImg(){
        moduleTopBarBinding.iconMid.setVisibility(INVISIBLE);
    }

    public void setIconRightImg(int imgId){
        moduleTopBarBinding.iconRight.setVisibility(VISIBLE);
        moduleTopBarBinding.iconRight.setImageResource(imgId);
    }
    public ImageView iconRightImg(){
        return moduleTopBarBinding.iconRight;
    }


    public void hideIconRightImg(){
        moduleTopBarBinding.iconRight.setVisibility(INVISIBLE);
    }


    public void initTopBar(String title){
        moduleTopBarBinding.title.setText(title);
    }

    public void initTopBar(String title,boolean needClose){
        moduleTopBarBinding.title.setText(title);
        if (needClose){
            moduleTopBarBinding.comeBack.setVisibility(VISIBLE);
        }
    }






}
