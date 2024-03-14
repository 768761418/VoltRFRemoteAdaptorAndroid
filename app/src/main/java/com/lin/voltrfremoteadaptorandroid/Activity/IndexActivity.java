package com.lin.voltrfremoteadaptorandroid.Activity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.Fade;
import androidx.viewpager2.widget.ViewPager2;

import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbManager;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.lin.voltrfremoteadaptorandroid.Adapter.FgmAdapter;
import com.lin.voltrfremoteadaptorandroid.R;
import com.lin.voltrfremoteadaptorandroid.Receiver.SerialReceiver;
import com.lin.voltrfremoteadaptorandroid.Utils.PermissionUtils;

import java.util.ArrayList;
import java.util.List;

public class IndexActivity extends AppCompatActivity {
    private String TAG = "IndexActivity";
    private PermissionUtils permissionUtils = new PermissionUtils();
    private SerialReceiver serialReceiver;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    private List<Fragment> fragments = new ArrayList<>();
    private List<String> tabLayoutData = new ArrayList<>();

    ViewPager2 viewPager2;
    TabLayout tabLayout;


    TextView rgb ;
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
        init();
        registerSerialReceiver();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterSerialReceiver();
    }

    private  void initFgmData(){
        serialReceiver = new SerialReceiver();
//        绑定viewpager和切换栏
        viewPager2 = findViewById(R.id.index_viewPager);
        tabLayout  = findViewById(R.id.index_tab_layout);
        FgmAdapter fgmAdapter = new FgmAdapter(getSupportFragmentManager(),getLifecycle(),fragments);
        viewPager2.setAdapter(fgmAdapter);
        fragments.add(RgbFragment.newInstance("RGB","1"));
        fragments.add(CwFragment.newInstance("CW","2"));
        tabLayoutData.add("1");
        tabLayoutData.add("2");

//        绑定tabLayout和viewpager2，实现点击切换
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabLayoutData.get(position));
            }
        }).attach();

        viewPager2.setUserInputEnabled(false);





    }


    private void init(){






    }

    private void registerSerialReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(UsbManager.ACTION_USB_ACCESSORY_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_ACCESSORY_DETACHED);
        this.registerReceiver(serialReceiver, filter);
        Log.d(TAG, "registerSerialReceiver: sssssw");
    }

    private void unregisterSerialReceiver() {
        unregisterReceiver(serialReceiver);
    }


}