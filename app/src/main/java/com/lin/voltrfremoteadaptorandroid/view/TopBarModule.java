package com.lin.voltrfremoteadaptorandroid.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.lin.voltrfremoteadaptorandroid.Activity.RemoteActivity;
import com.lin.voltrfremoteadaptorandroid.R;
import com.lin.voltrfremoteadaptorandroid.databinding.ModuleTopBarBinding;

public class TopBarModule extends LinearLayout {

    ModuleTopBarBinding moduleTopBarBinding;
    private OnComeBackOnClickListener onComeBackOnClickListener;
    private int switchStatus = 0;


    public interface OnComeBackOnClickListener{
        void comeBackOnClick();
    }


    public void setOnComeBackOnClickListener(OnComeBackOnClickListener onComeBackOnClickListener){
        this.onComeBackOnClickListener = onComeBackOnClickListener;
    }
    public TopBarModule(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        moduleTopBarBinding = DataBindingUtil.inflate(layoutInflater, R.layout.module_top_bar,this,true);
    }



//    currentIndex用来确定当前位置，以确定需要显示什么样式的顶部栏
//    参数：0  CW和RGB
//    参数：1  Remote controller
    public void currentIndexForTop(Context context,int currentIndex,String title){
        Activity activity= (Activity) context;;
        if (currentIndex == 0){
//            使用rgb顶部
            moduleTopBarBinding.topRgbAndCw.setVisibility(VISIBLE);
            moduleTopBarBinding.topRemote.setVisibility(GONE);
//            设置标题
            moduleTopBarBinding.titleRgbAndCw.setText(title);
//            开关绑定事件
//            0为关闭状态
//            1为开启状态
            moduleTopBarBinding.rgbAndCwSwitch.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (switchStatus == 0){
                        switchStatus = 1;
                        moduleTopBarBinding.rgbAndCwSwitch.setImageResource(R.drawable.icon_switch_on);
                    } else if (switchStatus ==1 ) {
                        switchStatus = 0;
                        moduleTopBarBinding.rgbAndCwSwitch.setImageResource(R.drawable.icon_switch_off);
                    }

                }
            });
//            启动遥控器界面
            moduleTopBarBinding.rgbAndCwToRemote.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                        Intent intent = new Intent(activity, RemoteActivity.class);
                        activity.startActivity(intent);
                }
            });
        }else if(currentIndex == 1){
//            使用遥控器的顶部栏
            moduleTopBarBinding.topRgbAndCw.setVisibility(GONE);
            moduleTopBarBinding.topRemote.setVisibility(VISIBLE);
            moduleTopBarBinding.titleRemote.setText(title);

            // 设置返回logo点击事件,
            moduleTopBarBinding.comeBack.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.finish();
                }
            });
        }


    }





}
