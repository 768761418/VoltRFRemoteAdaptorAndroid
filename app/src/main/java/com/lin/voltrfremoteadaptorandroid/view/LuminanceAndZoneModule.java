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

import com.lin.voltrfremoteadaptorandroid.R;
import com.lin.voltrfremoteadaptorandroid.Utils.MessageUtils;
import com.lin.voltrfremoteadaptorandroid.databinding.ModuleLuminanceAndZoneBinding;

import java.text.DecimalFormat;

public class LuminanceAndZoneModule extends ConstraintLayout {
    ModuleLuminanceAndZoneBinding moduleLuminanceAndZoneBinding;
    private String TAG = "LuminanceAndZoneModule";


    public LuminanceAndZoneModule(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        moduleLuminanceAndZoneBinding = DataBindingUtil.inflate(layoutInflater,R.layout.module_luminance_and_zone,this,true);
        setSeekBar();
        luminanceClick();
        zoneClick();
    }

    private void setSeekBar(){
        moduleLuminanceAndZoneBinding.luminanceSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                int progressMax = seekBar.getMax();
                float percentage =  (float)progress / progressMax * 100;
                // 格式化百分比值，保留两位小数
                DecimalFormat decimalFormat = new DecimalFormat("0");
                String percentageString = decimalFormat.format(percentage);
                moduleLuminanceAndZoneBinding.luminanceCurrentProgress.setText(percentageString + "%");
//                发信息
                MessageUtils.sendMessageForSetLuminance(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

//    调亮度
    private void luminanceClick(){
        moduleLuminanceAndZoneBinding.luminance25.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int progress = (int)(255*0.25);
                moduleLuminanceAndZoneBinding.luminanceSeekbar.setProgress(progress,true);
                moduleLuminanceAndZoneBinding.luminanceCurrentProgress.setText("25%");

            }
        });

        moduleLuminanceAndZoneBinding.luminance50.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int progress = (int)(255*0.5);
                moduleLuminanceAndZoneBinding.luminanceSeekbar.setProgress(progress,true);
                moduleLuminanceAndZoneBinding.luminanceCurrentProgress.setText("50%");
            }
        });

        moduleLuminanceAndZoneBinding.luminance75.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int progress = (int)(255*0.75);
                moduleLuminanceAndZoneBinding.luminanceSeekbar.setProgress(progress,true);
                moduleLuminanceAndZoneBinding.luminanceCurrentProgress.setText("75%");
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
