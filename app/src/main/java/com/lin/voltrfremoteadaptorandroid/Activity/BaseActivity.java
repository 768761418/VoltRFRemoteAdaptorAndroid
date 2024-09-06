package com.lin.voltrfremoteadaptorandroid.Activity;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.lin.voltrfremoteadaptorandroid.MyApplication;
import com.lin.voltrfremoteadaptorandroid.Utils.PermissionUtils;

import java.util.List;

//用以设置全局效果
public class BaseActivity extends AppCompatActivity {

    private PermissionUtils permissionUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        去除顶部紫色栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //获取权限
//        permissionUtils = new PermissionUtils();
//        permissionUtils.checkPermission(this);
        initPermission();
    }


    private void initPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            XXPermissions.with(this)
//                    .permission(Permission.ACCESS_COARSE_LOCATION)
//                蓝牙权限
                    .permission(Permission.Group.BLUETOOTH)
                    // 申请多个权限
//                    .permission(Permission.READ_MEDIA_IMAGES)
//                    .permission(Permission.READ_MEDIA_VIDEO)
//                    .permission(Permission.READ_MEDIA_AUDIO)

                    // 设置权限请求拦截器（局部设置）
                    //.interceptor(new PermissionInterceptor())
                    // 设置不触发错误检测机制（局部设置）
                    //.unchecked()
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                            if (!allGranted) {
//                            toast("获取部分权限成功，但部分权限未正常授予");
                                return;
                            }
//                        toast("获取录音和日历权限成功");
                        }

                        @Override
                        public void onDenied(@NonNull List<String> permissions, boolean doNotAskAgain) {
                            if (doNotAskAgain) {
//                            toast("被永久拒绝授权，请手动授予录音和日历权限");
                                // 如果是被永久拒绝就跳转到应用权限系统设置页面
                                XXPermissions.startPermissionActivity(BaseActivity.this, permissions);
                            } else {
//                            toast("获取录音和日历权限失败");
                            }
                        }
                    });
        }else {
            XXPermissions.with(this)
//                    .permission(Permission.ACCESS_COARSE_LOCATION)
//                蓝牙权限
                    .permission(Permission.Group.BLUETOOTH)
                    // 申请多个权限
//                    .permission(Permission.MANAGE_EXTERNAL_STORAGE)
//                    .permission(Permission.Group.STORAGE)

                    // 设置权限请求拦截器（局部设置）
                    //.interceptor(new PermissionInterceptor())
                    // 设置不触发错误检测机制（局部设置）
                    //.unchecked()
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                            if (!allGranted) {
//                            toast("获取部分权限成功，但部分权限未正常授予");
                                return;
                            }
//                        toast("获取录音和日历权限成功");
                        }

                        @Override
                        public void onDenied(@NonNull List<String> permissions, boolean doNotAskAgain) {
                            if (doNotAskAgain) {
//                            toast("被永久拒绝授权，请手动授予录音和日历权限");
                                // 如果是被永久拒绝就跳转到应用权限系统设置页面
                                XXPermissions.startPermissionActivity(BaseActivity.this, permissions);
                            } else {
//                            toast("获取录音和日历权限失败");
                            }
                        }
                    });
        }

    }

}
