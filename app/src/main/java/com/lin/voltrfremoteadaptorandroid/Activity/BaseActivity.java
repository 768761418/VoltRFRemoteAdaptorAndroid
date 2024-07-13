package com.lin.voltrfremoteadaptorandroid.Activity;

import android.os.Bundle;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lin.voltrfremoteadaptorandroid.Utils.PermissionUtils;

//用以设置全局效果
public class BaseActivity extends AppCompatActivity {

    private PermissionUtils permissionUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //获取权限
        permissionUtils = new PermissionUtils();
        permissionUtils.checkPermission(this);
    }
}
