package com.lin.voltrfremoteadaptorandroid.Activity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.lin.voltrfremoteadaptorandroid.Adapter.FgmAdapter;
import com.lin.voltrfremoteadaptorandroid.module.TopBarModule;
import com.lin.voltrfremoteadaptorandroid.R;
import com.lin.voltrfremoteadaptorandroid.Receiver.OtgReceiver;
import com.lin.voltrfremoteadaptorandroid.Utils.PermissionUtils;

import java.util.ArrayList;
import java.util.List;

public class IndexActivity extends AppCompatActivity {
    private String TAG = "IndexActivity";
    private PermissionUtils permissionUtils = new PermissionUtils();
    private OtgReceiver otgReceiver;
    IntentFilter filter;
    private TopBarModule topBarModule;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> tabLayoutData = new ArrayList<>();
    private ViewPager2 viewPager2;
    private TabLayout tabLayout;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtils.REQUEST_CODE){
            for (int i = 0; i < grantResults.length; i++) {
//                如果没请求成功，在这写
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED){

                }
//                如果请求成功在这写
                else {

                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_index);

        permissionUtils.checkPermission(this);

        initFgmData();
        registerSerialReceiver();

        registerReceiver(otgReceiver,filter);
//        this.unregisterReceiver(otgReceiver);
        int status = otgReceiver.getResultCode();


        Log.d(TAG, "注册了" + status);

    }

    private  void initFgmData(){
//        初始化顶部栏
        topBarModule= findViewById(R.id.index_top_bar);
        topBarModule.currentIndexForTop(IndexActivity.this,0,getString(R.string.Rgb_title));

//        绑定viewpager和切换栏
        viewPager2 = findViewById(R.id.index_viewPager);
        tabLayout  = findViewById(R.id.index_tab_layout);
        FgmAdapter fgmAdapter = new FgmAdapter(getSupportFragmentManager(),getLifecycle(),fragments);
        viewPager2.setAdapter(fgmAdapter);
        fragments.add(RgbFragment.newInstance("RGB","1"));
        fragments.add(CwFragment.newInstance("CW","2"));
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
                    topBarModule.currentIndexForTop(IndexActivity.this,0,getString(R.string.Rgb_title));
                }else if (position == 1){
                    topBarModule.currentIndexForTop(IndexActivity.this,0,getString(R.string.Cw_title));
                }
            }
        });
        viewPager2.setUserInputEnabled(true);
    }




    private void registerSerialReceiver() {
        otgReceiver = new OtgReceiver();
        filter = new IntentFilter("com.android.usb.USB_PERMISSION");
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
    }



}

