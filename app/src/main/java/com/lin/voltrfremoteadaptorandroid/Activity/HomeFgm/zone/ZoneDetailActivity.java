package com.lin.voltrfremoteadaptorandroid.Activity.homeFgm.zone;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.lin.voltrfremoteadaptorandroid.Activity.BaseActivity;
import com.lin.voltrfremoteadaptorandroid.R;
import com.lin.voltrfremoteadaptorandroid.databinding.LayoutZoneDetailsBinding;

public class ZoneDetailActivity extends BaseActivity {
    private LayoutZoneDetailsBinding layoutZoneDetailsBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutZoneDetailsBinding = DataBindingUtil.setContentView(this, R.layout.layout_zone_details);
        initUI();
    }



    private void initUI(){
        String title = getIntent().getStringExtra("zoneName");
        layoutZoneDetailsBinding.topBar.initTopBar(title,true);
    }
}
