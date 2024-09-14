package com.lin.voltrfremoteadaptorandroid.Activity.homeFgm.zone;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.lin.voltrfremoteadaptorandroid.Activity.BaseActivity;
import com.lin.voltrfremoteadaptorandroid.R;
import com.lin.voltrfremoteadaptorandroid.databinding.LayoutAddMemberBinding;

public class AddMemberActivity extends BaseActivity {
    private LayoutAddMemberBinding layoutAddMemberBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutAddMemberBinding = DataBindingUtil.setContentView(this, R.layout.layout_add_member);
        initUI();
    }

    private void initUI(){
        layoutAddMemberBinding.topBar.initTopBar(getString(R.string.title_add_member),true);
    }
}
