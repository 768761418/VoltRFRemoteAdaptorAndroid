package com.lin.voltrfremoteadaptorandroid.Activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.lin.voltrfremoteadaptorandroid.module.TopBarModule;
import com.lin.voltrfremoteadaptorandroid.R;
import com.lin.voltrfremoteadaptorandroid.databinding.LayoutRemoteBinding;

public class RemoteActivity extends AppCompatActivity {
    LayoutRemoteBinding layoutRemoteBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        layoutRemoteBinding = DataBindingUtil.setContentView(this, R.layout.layout_remote);
//        初始化顶部栏
        layoutRemoteBinding.remoteTopBar.currentIndexForTop(RemoteActivity.this,1,getString(R.string.Remote_title));
    }




}
