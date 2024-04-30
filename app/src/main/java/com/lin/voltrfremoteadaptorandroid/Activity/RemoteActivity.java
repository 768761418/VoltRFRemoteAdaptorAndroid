package com.lin.voltrfremoteadaptorandroid.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.lin.voltrfremoteadaptorandroid.R;
import com.lin.voltrfremoteadaptorandroid.Utils.MessageUtils;
import com.lin.voltrfremoteadaptorandroid.databinding.LayoutRemoteBinding;

public class RemoteActivity extends BaseActivity {
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
        luminanceClick();
    }

    private void luminanceClick(){
        layoutRemoteBinding.remoteLuminance25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutRemoteBinding.remoteIndicator.setImageResource(R.drawable.icon_remote_light_on);
                int luminance = (int) (255*0.25);
                MessageUtils.sendMessageForSetLuminance(luminance);
                layoutRemoteBinding.remoteIndicator.setImageResource(R.drawable.icon_remote_light_off);
                // 延迟一段时间后切换为关灯图标
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        layoutRemoteBinding.remoteIndicator.setImageResource(R.drawable.icon_remote_light_off);
                    }
                }, 1000); // 延迟1秒
            }
        });

        layoutRemoteBinding.remoteLuminance50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        layoutRemoteBinding.remoteLuminance75.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        layoutRemoteBinding.remoteLuminance100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }




}
