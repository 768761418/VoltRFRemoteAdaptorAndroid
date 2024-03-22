package com.lin.voltrfremoteadaptorandroid.module;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import com.lin.voltrfremoteadaptorandroid.R;
import com.lin.voltrfremoteadaptorandroid.databinding.ModuleLuminanceBinding;

import java.text.DecimalFormat;

public class LuminanceModule extends ConstraintLayout {
    ModuleLuminanceBinding moduleLuminanceBinding;
    private String TAG = "LuminanceModule";


    public LuminanceModule(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        moduleLuminanceBinding = DataBindingUtil.inflate(layoutInflater,R.layout.module_luminance,this,true);
        setSeekBar();
    }

    private void setSeekBar(){
        moduleLuminanceBinding.luminanceSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                int progressMax = seekBar.getMax();
                float percentage =  (float)progress / progressMax * 100;
                // 格式化百分比值，保留两位小数
                DecimalFormat decimalFormat = new DecimalFormat("0");
                String percentageString = decimalFormat.format(percentage);

                moduleLuminanceBinding.luminanceCurrentProgress.setText(percentageString + "%");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
