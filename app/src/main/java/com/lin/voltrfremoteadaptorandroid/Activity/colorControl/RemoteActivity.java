package com.lin.voltrfremoteadaptorandroid.Activity.colorControl;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.lin.voltrfremoteadaptorandroid.Activity.BaseActivity;
import com.lin.voltrfremoteadaptorandroid.ApplicationData;
import com.lin.voltrfremoteadaptorandroid.R;
import com.lin.voltrfremoteadaptorandroid.Utils.MessageUtils;
import com.lin.voltrfremoteadaptorandroid.Utils.SharedPreferencesUtils;
import com.lin.voltrfremoteadaptorandroid.databinding.LayoutRemoteBinding;
import com.lin.voltrfremoteadaptorandroid.setting.ApplicationSetting;
import com.lin.voltrfremoteadaptorandroid.setting.ColorSetting;

public class RemoteActivity extends BaseActivity {
    LayoutRemoteBinding layoutRemoteBinding;
    private int progressMax = ApplicationData.LUMINANCE_MAX;
    private SharedPreferencesUtils sharedPreferencesUtils;
    View.OnTouchListener touchListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        layoutRemoteBinding = DataBindingUtil.setContentView(this, R.layout.layout_remote);
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(RemoteActivity.this);
//        初始化顶部栏
        layoutRemoteBinding.remoteTopBar.currentIndexForTop(RemoteActivity.this,1,getString(R.string.Remote_title));

        // 创建一个共享的触摸事件监听器
        touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 按下时切换按钮的状态
                        layoutRemoteBinding.remoteIndicator.setImageResource(R.drawable.icon_remote_light_on);
                        break;
                    case MotionEvent.ACTION_UP:
                        // 抬起时切换按钮的状态
                        layoutRemoteBinding.remoteIndicator.setImageResource(R.drawable.icon_remote_light_off);
                        break;
                }
                return false; // 返回 false 表示继续传递触摸事件，返回 true 表示拦截触摸事件
            }
        };


        setColorPicker();
        luminanceClick();
        zoneClick();
        temperatureClick();
        colorClick();
        otherClick();
        setTouchListener();
    }

//    设置反馈灯
    private void setTouchListener(){
        layoutRemoteBinding.remoteTop.allOn().setOnTouchListener(touchListener);
        layoutRemoteBinding.remoteTop.allOff().setOnTouchListener(touchListener);

        layoutRemoteBinding.remoteColorPicker.setOnTouchListener(touchListener);

        layoutRemoteBinding.remoteRed.setOnTouchListener(touchListener);
        layoutRemoteBinding.remoteGreen.setOnTouchListener(touchListener);
        layoutRemoteBinding.remoteBlue.setOnTouchListener(touchListener);
        layoutRemoteBinding.remoteLast.setOnTouchListener(touchListener);

        layoutRemoteBinding.remoteLuminance25.setOnTouchListener(touchListener);
        layoutRemoteBinding.remoteLuminance50.setOnTouchListener(touchListener);
        layoutRemoteBinding.remoteLuminance75.setOnTouchListener(touchListener);
        layoutRemoteBinding.remoteLuminance100.setOnTouchListener(touchListener);

        layoutRemoteBinding.remoteTemperature2200.setOnTouchListener(touchListener);
        layoutRemoteBinding.remoteTemperature2700.setOnTouchListener(touchListener);
        layoutRemoteBinding.remoteTemperature3000.setOnTouchListener(touchListener);
        layoutRemoteBinding.remoteTemperature4000.setOnTouchListener(touchListener);

        layoutRemoteBinding.remoteOtherFlash.setOnTouchListener(touchListener);
        layoutRemoteBinding.remoteOtherStrobe.setOnTouchListener(touchListener);
        layoutRemoteBinding.remoteOtherSmooth.setOnTouchListener(touchListener);
        layoutRemoteBinding.remoteOtherFade.setOnTouchListener(touchListener);



        layoutRemoteBinding.remoteZone1.setOnTouchListener(touchListener);
        layoutRemoteBinding.remoteZone2.setOnTouchListener(touchListener);
        layoutRemoteBinding.remoteZone3.setOnTouchListener(touchListener);
        layoutRemoteBinding.remoteZone4.setOnTouchListener(touchListener);
        layoutRemoteBinding.remoteZone5.setOnTouchListener(touchListener);
        layoutRemoteBinding.remoteZone6.setOnTouchListener(touchListener);

    }

    private void setColorPicker(){
        int touchX = sharedPreferencesUtils.loadIntData(ApplicationSetting.PRESUPPOSE_SIX_TOUCH_X,0);
        int touchY = sharedPreferencesUtils.loadIntData(ApplicationSetting.PRESUPPOSE_SIX_TOUCH_Y,0);
        int currentColor = sharedPreferencesUtils.loadIntData(ApplicationSetting.PRESUPPOSE_SIX,0);
        if (touchX != 0 && touchY != 0) {
            layoutRemoteBinding.remoteColorPicker.setTouchX(touchX);
            layoutRemoteBinding.remoteColorPicker.setTouchY(touchY);
        }else {

        }
        layoutRemoteBinding.remoteColorPicker.setSelectedColor(currentColor);
    }
//    亮度按钮
    private void luminanceClick(){
        layoutRemoteBinding.remoteLuminance25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int luminance = (int) (progressMax*0.25);
                ApplicationData.luminanceData = luminance;
                MessageUtils.sendMessageForSetLuminance(luminance);
                sharedPreferencesUtils.saveIntData(ApplicationSetting.LUMINANCE,ApplicationData.luminanceData);
            }
        });

        layoutRemoteBinding.remoteLuminance50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int luminance = (int) (progressMax*0.5);
                ApplicationData.luminanceData = luminance;
                MessageUtils.sendMessageForSetLuminance(luminance);
                sharedPreferencesUtils.saveIntData(ApplicationSetting.LUMINANCE,ApplicationData.luminanceData);
            }
        });

        layoutRemoteBinding.remoteLuminance75.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int luminance = (int) (progressMax*0.75);
                ApplicationData.luminanceData = luminance;
                MessageUtils.sendMessageForSetLuminance(luminance);
                sharedPreferencesUtils.saveIntData(ApplicationSetting.LUMINANCE,ApplicationData.luminanceData);
            }
        });

        layoutRemoteBinding.remoteLuminance100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplicationData.luminanceData = progressMax;
                MessageUtils.sendMessageForSetLuminance(progressMax);
                sharedPreferencesUtils.saveIntData(ApplicationSetting.LUMINANCE,ApplicationData.luminanceData);
            }
        });
    }
//    分组按钮
    private void zoneClick(){
        layoutRemoteBinding.remoteZone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageUtils.sendMessageForZone(1);
            }
        });

        layoutRemoteBinding.remoteZone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageUtils.sendMessageForZone(2);
            }
        });

        layoutRemoteBinding.remoteZone3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageUtils.sendMessageForZone(3);
            }
        });

        layoutRemoteBinding.remoteZone4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageUtils.sendMessageForZone(4);
            }
        });

        layoutRemoteBinding.remoteZone5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageUtils.sendMessageForZone(5);
            }
        });

        layoutRemoteBinding.remoteZone6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageUtils.sendMessageForZone(6);
            }
        });
    }
//    色温按钮
    private void temperatureClick(){
        layoutRemoteBinding.remoteTemperature2200.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplicationData.temperatureData = 2200;
                sharedPreferencesUtils.saveIntData(ApplicationSetting.PRESUPPOSE_CW,ApplicationData.temperatureData);
                MessageUtils.sendMessageForTemperature(ApplicationData.temperatureData);
            }
        });

        layoutRemoteBinding.remoteTemperature2700.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplicationData.temperatureData = 2700;
                sharedPreferencesUtils.saveIntData(ApplicationSetting.PRESUPPOSE_CW,ApplicationData.temperatureData);
                MessageUtils.sendMessageForTemperature(ApplicationData.temperatureData);
            }
        });

        layoutRemoteBinding.remoteTemperature3000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplicationData.temperatureData = 3000;
                sharedPreferencesUtils.saveIntData(ApplicationSetting.PRESUPPOSE_CW,ApplicationData.temperatureData);
                MessageUtils.sendMessageForTemperature(ApplicationData.temperatureData);
            }
        });

        layoutRemoteBinding.remoteTemperature4000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplicationData.temperatureData = 4000;
                sharedPreferencesUtils.saveIntData(ApplicationSetting.PRESUPPOSE_CW,ApplicationData.temperatureData);
                MessageUtils.sendMessageForTemperature(ApplicationData.temperatureData);
            }
        });
    }


    private void colorClick(){
        layoutRemoteBinding.remoteRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int red = Color.HSVToColor(new float[]{0, 0.8f, ColorSetting.colorV});
                layoutRemoteBinding.remoteColorPicker.externalClickPresuppose(red);
                MessageUtils.sendMessageForSetColor(0);
            }
        });

        layoutRemoteBinding.remoteBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int blue = Color.HSVToColor(new float[]{240, 0.8f, ColorSetting.colorV});
                layoutRemoteBinding.remoteColorPicker.externalClickPresuppose(blue);
                MessageUtils.sendMessageForSetColor(240);
            }
        });

        layoutRemoteBinding.remoteGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int green = Color.HSVToColor(new float[]{120, 0.8f, ColorSetting.colorV});
                layoutRemoteBinding.remoteColorPicker.externalClickPresuppose(green);
                MessageUtils.sendMessageForSetColor(120);
            }
        });

        layoutRemoteBinding.remoteLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    private void otherClick(){
        layoutRemoteBinding.remoteOtherFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageUtils.sendMessageForRemoteOther("flash");
            }
        });

        layoutRemoteBinding.remoteOtherStrobe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageUtils.sendMessageForRemoteOther("strobe");
            }
        });

        layoutRemoteBinding.remoteOtherFade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageUtils.sendMessageForRemoteOther("fade");
            }
        });

        layoutRemoteBinding.remoteOtherSmooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageUtils.sendMessageForRemoteOther("smooth");
            }
        });
    }
}
