package com.lin.voltrfremoteadaptorandroid.module;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import com.lin.voltrfremoteadaptorandroid.R;
import com.lin.voltrfremoteadaptorandroid.databinding.ModuleZoneBinding;


public class ZoneModule extends ConstraintLayout {
    private String TAG = "ZoneModule";
    ModuleZoneBinding moduleZoneBinding;
    public ZoneModule(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        moduleZoneBinding = DataBindingUtil.inflate(layoutInflater, R.layout.module_zone,this,true);
        setZoneMessage();
    }

    private void setZoneMessage(){
        moduleZoneBinding.zone1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "9898898");
            }
        });
    }

}
