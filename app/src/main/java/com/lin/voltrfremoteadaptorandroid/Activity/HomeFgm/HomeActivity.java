package com.lin.voltrfremoteadaptorandroid.Activity.HomeFgm;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.lin.voltrfremoteadaptorandroid.Activity.BaseActivity;
import com.lin.voltrfremoteadaptorandroid.Activity.HomeFgm.NetworkFragment;
import com.lin.voltrfremoteadaptorandroid.Activity.HomeFgm.SettingFragment;
import com.lin.voltrfremoteadaptorandroid.Activity.HomeFgm.ZoneFragment;
import com.lin.voltrfremoteadaptorandroid.Adapter.FgmAdapter;
import com.lin.voltrfremoteadaptorandroid.R;
import com.lin.voltrfremoteadaptorandroid.databinding.LayoutHomeBinding;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.autosize.AutoSizeCompat;

public class HomeActivity extends BaseActivity {
    private LayoutHomeBinding layoutHomeBinding;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> tabLayoutData = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    @Override
    public Resources getResources() {
        AutoSizeCompat.autoConvertDensityOfGlobal((super.getResources()));//如果没有自定义需求用这个方法
        return super.getResources();
    }

    private void initUI(){
//        初始化表头
        layoutHomeBinding = DataBindingUtil.setContentView(this,R.layout.layout_home);
        layoutHomeBinding.topBar.initTopBar(getString(R.string.title_network));
        layoutHomeBinding.topBar.setIconRightImg(R.drawable.icon_add);

//        初始化tab layout
        fragments.add(NetworkFragment.newInstance("network","0"));
        fragments.add(ZoneFragment.newInstance("Zone","1"));
        fragments.add(SettingFragment.newInstance("Setting","2"));
//        设置适配器
        FgmAdapter fgmAdapter = new FgmAdapter(getSupportFragmentManager(),getLifecycle(),fragments);
        layoutHomeBinding.viewPager.setAdapter(fgmAdapter);
//        添加内容
        tabLayoutData.add("Network");
        tabLayoutData.add("Zone");
        tabLayoutData.add("Setting");
        //        绑定tabLayout和viewpager2，实现点击切换
        new TabLayoutMediator(layoutHomeBinding.tabLayout, layoutHomeBinding.viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabLayoutData.get(position));
            }
        }).attach();


        //        监听界面切换获取索引来修改顶部栏内容
        layoutHomeBinding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 0 ){
                    layoutHomeBinding.topBar.initTopBar(getString(R.string.title_network));
                    layoutHomeBinding.topBar.setIconRightImg(R.drawable.icon_add);
                }else if (position == 1){
                    layoutHomeBinding.topBar.initTopBar(getString(R.string.title_zone));
                    layoutHomeBinding.topBar.hideIconRightImg();
                } else if (position == 2) {
                    layoutHomeBinding.topBar.initTopBar(getString(R.string.title_setting));
                    layoutHomeBinding.topBar.hideIconRightImg();

                }
            }
        });
//        不允许滑动切换
        layoutHomeBinding.viewPager.setUserInputEnabled(false);
    }
}
