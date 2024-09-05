package com.lin.voltrfremoteadaptorandroid.Activity.homeFgm.zone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.lin.voltrfremoteadaptorandroid.Activity.BaseActivity;
import com.lin.voltrfremoteadaptorandroid.Activity.colorControl.RgbCwControlActivity;
import com.lin.voltrfremoteadaptorandroid.ConfigData;
import com.lin.voltrfremoteadaptorandroid.MyApplication;
import com.lin.voltrfremoteadaptorandroid.R;
import com.lin.voltrfremoteadaptorandroid.databinding.LayoutZoneDetailsBinding;
import com.lin.voltrfremoteadaptorandroid.db.ZoneDb;

public class ZoneDetailActivity extends BaseActivity {
    private LayoutZoneDetailsBinding layoutZoneDetailsBinding;
    private long zoneId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutZoneDetailsBinding = DataBindingUtil.setContentView(this, R.layout.layout_zone_details);
        initUI();
    }



    private void initUI(){
        String title = getIntent().getStringExtra("zoneName");
        zoneId = getIntent().getLongExtra("zoneId",0);
//        初始化表头
        layoutZoneDetailsBinding.topBar.initTopBar(title,true);
//        点击事件
        layoutZoneDetailsBinding.zoneDetailsColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setColor();
            }
        });

        layoutZoneDetailsBinding.zoneDetailsAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               addMember();
            }
        });

        layoutZoneDetailsBinding.zoneDetailsDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteZone();
            }
        });

    }




    private void addMember(){

    }

    private void deleteZone(){
        ZoneDb.deleteZoneById(zoneId);
        setResult(ConfigData.UPDATE_RESULT_CODE);
        finish();
    }

    private void setColor(){
        Intent intent = new Intent(ZoneDetailActivity.this, RgbCwControlActivity.class);
        startActivity(intent);
    }
}
