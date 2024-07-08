package com.lin.voltrfremoteadaptorandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import com.lin.voltrfremoteadaptorandroid.ApplicationData;
import com.lin.voltrfremoteadaptorandroid.R;
import com.lin.voltrfremoteadaptorandroid.Utils.MessageUtils;
import com.lin.voltrfremoteadaptorandroid.Utils.SharedPreferencesUtils;
import com.lin.voltrfremoteadaptorandroid.databinding.ModuleLuminanceAndZoneBinding;
import com.lin.voltrfremoteadaptorandroid.setting.ApplicationSetting;

import java.text.DecimalFormat;

public class LuminanceAndZoneModule extends ConstraintLayout {
    ModuleLuminanceAndZoneBinding moduleLuminanceAndZoneBinding;
    private String TAG = "LuminanceAndZoneModule";
    private int progressMax = ApplicationData.LUMINANCE_MAX;
    private SharedPreferencesUtils sharedPreferencesUtils;


    public LuminanceAndZoneModule(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        moduleLuminanceAndZoneBinding = DataBindingUtil.inflate(layoutInflater,R.layout.module_luminance_and_zone,this,true);
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(getContext());
        setSeekBar();
        luminanceClick();
        zoneClick();
    }

    private void setSeekBar(){
        ApplicationData.luminanceData = sharedPreferencesUtils.loadIntData(ApplicationSetting.LUMINANCE,ApplicationData.luminanceData);
        moduleLuminanceAndZoneBinding.luminanceSeekbar.setProgress(ApplicationData.luminanceData);
        moduleLuminanceAndZoneBinding.luminanceSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                setProgress(progress,false);
//                发信息
                MessageUtils.sendMessageForSetLuminance(progress);
                ApplicationData.luminanceData = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                ApplicationData.luminanceData = seekBar.getProgress();
                sharedPreferencesUtils.saveIntData(ApplicationSetting.LUMINANCE,ApplicationData.luminanceData);
            }
        });
    }

    public void setProgress(int progress,boolean isOutUse){
        float percentage =  (float)progress / progressMax * 100;
        // 格式化百分比值，保留两位小数
        DecimalFormat decimalFormat = new DecimalFormat("0");
        String percentageString = decimalFormat.format(percentage);
        moduleLuminanceAndZoneBinding.luminanceCurrentProgress.setText(percentageString + "%");
        if(isOutUse){
            moduleLuminanceAndZoneBinding.luminanceSeekbar.setProgress(progress);
        }
    }

//    调亮度
    private void luminanceClick(){
        moduleLuminanceAndZoneBinding.luminance25.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int progress = (int)(progressMax*0.25);
                moduleLuminanceAndZoneBinding.luminanceSeekbar.setProgress(progress,true);
                moduleLuminanceAndZoneBinding.luminanceCurrentProgress.setText("25%");
                ApplicationData.luminanceData =progress;
                sharedPreferencesUtils.saveIntData(ApplicationSetting.LUMINANCE,ApplicationData.luminanceData);
            }
        });

        moduleLuminanceAndZoneBinding.luminance50.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int progress = (int)(progressMax*0.5);
                moduleLuminanceAndZoneBinding.luminanceSeekbar.setProgress(progress,true);
                moduleLuminanceAndZoneBinding.luminanceCurrentProgress.setText("50%");
                ApplicationData.luminanceData =progress;
                sharedPreferencesUtils.saveIntData(ApplicationSetting.LUMINANCE,ApplicationData.luminanceData);
            }
        });

        moduleLuminanceAndZoneBinding.luminance75.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int progress = (int)(progressMax*0.75);
                moduleLuminanceAndZoneBinding.luminanceSeekbar.setProgress(progress,true);
                moduleLuminanceAndZoneBinding.luminanceCurrentProgress.setText("75%");
                ApplicationData.luminanceData =progress;
                sharedPreferencesUtils.saveIntData(ApplicationSetting.LUMINANCE,ApplicationData.luminanceData);
            }
        });
    }
    
//    分组事件
    private void zoneClick(){
        moduleLuminanceAndZoneBinding.zone1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageUtils.sendMessageForZone(1);
            }
        });

        moduleLuminanceAndZoneBinding.zone2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageUtils.sendMessageForZone(2);
            }
        });

        moduleLuminanceAndZoneBinding.zone3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageUtils.sendMessageForZone(3);
            }
        });

        moduleLuminanceAndZoneBinding.zone4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageUtils.sendMessageForZone(4);
            }
        });

        moduleLuminanceAndZoneBinding.zone5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageUtils.sendMessageForZone(5);
            }
        });

        moduleLuminanceAndZoneBinding.zone6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageUtils.sendMessageForZone(6);
            }
        });


    }
}
