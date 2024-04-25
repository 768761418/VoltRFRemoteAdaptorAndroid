package com.lin.voltrfremoteadaptorandroid.view;

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
import com.lin.voltrfremoteadaptorandroid.databinding.ModuleAllOnOffBinding;


public class AllOnOffModule extends ConstraintLayout {
    private String TAG = "AllOnOffModule";

    ModuleAllOnOffBinding moduleAllOnOffBinding;
    public AllOnOffModule(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        moduleAllOnOffBinding = DataBindingUtil.inflate(layoutInflater, R.layout.module_all_on_off,this,true);
        setClick();
    }

    private void setClick(){
      moduleAllOnOffBinding.allOn.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View view) {
              Log.d(TAG, "onClick:on ");
          }
      });

      moduleAllOnOffBinding.allOff.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View view) {
              Log.d(TAG, "onClick:off ");
          }
      });
    }
}
