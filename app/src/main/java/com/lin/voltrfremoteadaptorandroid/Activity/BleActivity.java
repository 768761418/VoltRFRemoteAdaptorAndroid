package com.lin.voltrfremoteadaptorandroid.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.lin.voltrfremoteadaptorandroid.R;
import com.lin.voltrfremoteadaptorandroid.databinding.LayoutBleBinding;

public class BleActivity extends BaseActivity{
    private LayoutBleBinding layoutBleBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }
    private void init(){
        layoutBleBinding = DataBindingUtil.setContentView(BleActivity.this, R.layout.layout_ble);
        layoutBleBinding.topBar.initTopBar("Bluetooth",true);
    }
}
