package com.lin.voltrfremoteadaptorandroid.Activity.colorControl;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.lin.voltrfremoteadaptorandroid.Activity.BaseActivity;
import com.lin.voltrfremoteadaptorandroid.Adapter.FgmAdapter;
import com.lin.voltrfremoteadaptorandroid.Utils.MessageUtils;
import com.lin.voltrfremoteadaptorandroid.Utils.SharedPreferencesUtils;
import com.lin.voltrfremoteadaptorandroid.setting.ApplicationSetting;
import com.lin.voltrfremoteadaptorandroid.setting.ColorSetting;
import com.lin.voltrfremoteadaptorandroid.view.TopBarModule;
import com.lin.voltrfremoteadaptorandroid.R;
import com.lin.voltrfremoteadaptorandroid.Receiver.OtgReceiver;
import com.lin.voltrfremoteadaptorandroid.Utils.PermissionUtils;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.autosize.AutoSizeCompat;

public class RgbCwControlActivity extends BaseActivity {
    private String TAG = "RgbCwControlActivity";

    private OtgReceiver otgReceiver;
    private IntentFilter filter;
    private TopBarModule topBarModule;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> tabLayoutData = new ArrayList<>();
    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    private SharedPreferencesUtils sharedPreferencesUtils;

    private boolean isClose = true;

    @Override
    public Resources getResources() {
        AutoSizeCompat.autoConvertDensityOfGlobal((super.getResources()));//如果没有自定义需求用这个方法
        return super.getResources();
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == PermissionUtils.REQUEST_CODE){
//            for (int i = 0; i < grantResults.length; i++) {
////                如果没请求成功，在这写
//                if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
////                    弹出提示
//                    Toast.makeText(RgbCwControlActivity.this, "Please grant sufficient permissions to ensure the proper functioning of the app", Toast.LENGTH_SHORT).show();
//                }
////                如果请求成功在这写
//                else {
//
//                }
//            }
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_rgb_cw_control);
        sharedPreferencesUtils =SharedPreferencesUtils.getInstance(RgbCwControlActivity.this);
        //初始化预设
        initPresuppose();
        //初始化fragement
        initFgmData();
        //注册otg广播
        initSerialReceiver();

        int status = otgReceiver.getResultCode();
        Log.d(TAG, "注册了" + status);

    }

    private  void initFgmData(){
//        初始化顶部栏
        topBarModule= findViewById(R.id.index_top_bar);
        topBarModule.initTopBar(getString(R.string.title_rgb),true);
        topBarModule.setIconLeftImg(R.drawable.icon_remote_intent);
        topBarModule.setIconMidImg(R.drawable.icon_switch_off);
//        设置启动旧遥控的点击事件
        topBarModule.setOnLeftIconOnClickListener(new TopBarModule.OnLeftIconOnClickListener() {
            @Override
            public void leftIconOnClick() {
                Intent intent = new Intent(RgbCwControlActivity.this,RemoteActivity.class);
                startActivity(intent);
            }
        });
        topBarModule.setOnMidIconOnClickListener(new TopBarModule.OnMidIconOnClickListener() {
            @Override
            public void midIconOnClick() {
                if (isClose){
                    topBarModule.setIconMidImg(R.drawable.icon_switch_on);
                    isClose = false;
                    MessageUtils.sendMessageForSwitch(true);
                }else{
                    topBarModule.setIconMidImg(R.drawable.icon_switch_off);
                    isClose = true;
                    MessageUtils.sendMessageForSwitch(false);
                }
            }
        });






//        绑定viewpager和切换栏
        viewPager2 = findViewById(R.id.index_viewPager);
        tabLayout  = findViewById(R.id.index_tab_layout);
        FgmAdapter fgmAdapter = new FgmAdapter(getSupportFragmentManager(),getLifecycle(),fragments);
        viewPager2.setAdapter(fgmAdapter);
        fragments.add(RgbFragment.newInstance("RGB","0"));
        fragments.add(CwFragment.newInstance("CW","1"));
        tabLayoutData.add("RGB");
        tabLayoutData.add("CW");

//        绑定tabLayout和viewpager2，实现点击切换
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabLayoutData.get(position));
            }
        }).attach();

//        监听界面切换获取索引来修改顶部栏内容
//        0:rgb界面
//        1：cw界面
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 0 ){
                    topBarModule.initTopBar(getString(R.string.title_rgb),true);
                }else if (position == 1){
                    topBarModule.initTopBar(getString(R.string.title_cw),true);
                }
            }
        });
//        不允许滑动切换
        viewPager2.setUserInputEnabled(false);
    }


    private void initPresuppose(){
        //        如果初次使用,就用初始化颜色
        Boolean isFirstTime = sharedPreferencesUtils.loadBooleanData("isFirstTime",true);
        if (isFirstTime){
            // 将颜色值存储到 SharedPreferences 中
            int presuppose[] = {
                    Color.HSVToColor(new float[]{41, 0.8f, ColorSetting.colorV}),
                    Color.HSVToColor(new float[]{24, 0.8f, ColorSetting.colorV}),
                    Color.HSVToColor(new float[]{58, 0.8f, ColorSetting.colorV}),
                    Color.HSVToColor(new float[]{147, 0.8f, ColorSetting.colorV}),
                    Color.HSVToColor(new float[]{178, 0.8f, ColorSetting.colorV}),
                    Color.HSVToColor(new float[]{178, 0.8f, ColorSetting.colorV}),

            };
            String presupposeName = "presuppose";
            for(int i= 0;i<presuppose.length;i++){
                sharedPreferencesUtils.saveIntData(presupposeName+(i+1), presuppose[i]);
            }
            sharedPreferencesUtils.saveIntData(ApplicationSetting.PRESUPPOSE_CW,2200);
            sharedPreferencesUtils.saveBooleanData("isFirstTime",false);
        }
    }

    private void initSerialReceiver() {
        otgReceiver = new OtgReceiver();
        filter = new IntentFilter("com.android.usb.USB_PERMISSION");
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        registerReceiver(otgReceiver,filter);
    }



}

