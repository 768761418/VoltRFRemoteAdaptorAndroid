package com.lin.voltrfremoteadaptorandroid.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.lin.voltrfremoteadaptorandroid.R;
import com.lin.voltrfremoteadaptorandroid.Utils.MessageUtils;
import com.lin.voltrfremoteadaptorandroid.Utils.SharedPreferencesUtils;
import com.lin.voltrfremoteadaptorandroid.databinding.FragmentRgbBinding;
import com.lin.voltrfremoteadaptorandroid.setting.ApplicationSetting;
import com.lin.voltrfremoteadaptorandroid.view.ColorChooseDialog;
import com.lin.voltrfremoteadaptorandroid.view.ColorPickerView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RgbFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RgbFragment extends Fragment {

    private final String TAG = "RgbFragment";
    private FragmentRgbBinding fragmentRgbBinding;
    // 声明PopupWindow
    private PopupWindow popupWindow;

    // 声明PopupWindow对应的视图
    private View popupView;

    // 声明平移动画
    private TranslateAnimation animation;
    private ColorChooseDialog colorChooseDialog;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private int presuppose1,presuppose2,presuppose3,presuppose4,presuppose5,presuppose6;


    public RgbFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RgbFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RgbFragment newInstance(String param1, String param2) {
        RgbFragment fragment = new RgbFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(getContext());
        presuppose1 = sharedPreferencesUtils.loadIntData(ApplicationSetting.PRESUPPOSE_ONE,0);
        presuppose2 = sharedPreferencesUtils.loadIntData(ApplicationSetting.PRESUPPOSE_TWO,0);
        presuppose3 = sharedPreferencesUtils.loadIntData(ApplicationSetting.PRESUPPOSE_THREE,0);
        presuppose4 = sharedPreferencesUtils.loadIntData(ApplicationSetting.PRESUPPOSE_FOUR,0);
        presuppose5 = sharedPreferencesUtils.loadIntData(ApplicationSetting.PRESUPPOSE_FIVE,0);
        presuppose6 = sharedPreferencesUtils.loadIntData(ApplicationSetting.PRESUPPOSE_SIX,0);

        int[] colors = {
                    presuppose1,
                    presuppose2,
                    presuppose3,
                    presuppose4,
                    presuppose5,
                    presuppose6
        };
        ImageView[] imageViews = {
                fragmentRgbBinding.rgbPresuppose1,
                fragmentRgbBinding.rgbPresuppose2,
                fragmentRgbBinding.rgbPresuppose3,
                fragmentRgbBinding.rgbPresuppose4,
                fragmentRgbBinding.rgbPresuppose5,
                fragmentRgbBinding.rgbPresuppose6
        };
        // 遍历数组，依次修改颜色
        for (int i = 0; i < Math.min(colors.length, imageViews.length); i++) {
            boolean isLast = (i == Math.min(colors.length, imageViews.length) - 1);
            if (isLast){
                initColorPresuppose(colors[i], imageViews[i],true);
            }else {
                initColorPresuppose(colors[i], imageViews[i],false);
            }
        }


    }

//    初始化颜色预设
    private void initColorPresuppose(int color, ImageView imageview,Boolean isCurrent){
        //创建Drawable对象
        GradientDrawable drawable=new GradientDrawable();
        if (isCurrent){
            //设置圆角大小
            drawable.setCornerRadius(10);
        }else {
            //设置shape形状
            drawable.setShape(GradientDrawable.OVAL);
        }
        //设置背景色
        drawable.setColor(color);
        imageview.setBackground(drawable);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        fragmentRgbBinding = fragmentRgbBinding.inflate(inflater,container,false);
        colorChooseDialog = new ColorChooseDialog(getContext());

//        修改当前选择的函数
        changeSelectColor();
//        预设的点击事件
        presupposeClick();
//        预设长按修改事件
        presupposeLongClick();


        return fragmentRgbBinding.getRoot();
    }

    private void changeSelectColor(){
//        实时修改选中颜色
        fragmentRgbBinding.rgbColorPicker.setColorListener(new ColorPickerView.SelectListener() {
            @Override
            public void selectedColor(int color) {
                presuppose6 = color;
                initColorPresuppose(presuppose6,fragmentRgbBinding.rgbPresuppose6,true);
            }
        });
//        抬起修改选中预设
        fragmentRgbBinding.rgbColorPicker.setKeyUpListener(new ColorPickerView.KeyUpListener() {
            @Override
            public void saveColor(int color) {
                sharedPreferencesUtils.saveIntData(ApplicationSetting.PRESUPPOSE_SIX,color);
            }
        });
    }


//    预设按钮长按事件
    private void presupposeLongClick(){
        fragmentRgbBinding.rgbPresuppose1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                colorChooseDialog.show();
                colorChooseDialog.setColor(presuppose1);


                return true;
            }
        });
        fragmentRgbBinding.rgbPresuppose2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                colorChooseDialog.setColor(presuppose2);
                colorChooseDialog.show();

                return true;
            }
        });
    }



    private void presupposeClick(){
        fragmentRgbBinding.rgbPresuppose1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               usePresupposeOnClick(presuppose1);


            }
        });
        fragmentRgbBinding.rgbPresuppose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usePresupposeOnClick(presuppose2);
            }
        });
        fragmentRgbBinding.rgbPresuppose3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usePresupposeOnClick(presuppose3);
            }
        });
        fragmentRgbBinding.rgbPresuppose4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usePresupposeOnClick(presuppose4);
            }
        });
        fragmentRgbBinding.rgbPresuppose5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usePresupposeOnClick(presuppose5);
            }
        });

        fragmentRgbBinding.rgbPresuppose6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usePresupposeOnClick(presuppose6,true);
            }
        });


    }


//    辅助方法 预设按钮点击触发事件
    private void usePresupposeOnClick(int color){
        fragmentRgbBinding.rgbColorPicker.externalClickPresuppose(color);
        initColorPresuppose(color,fragmentRgbBinding.rgbPresuppose6,true);
        sharedPreferencesUtils.saveIntData(ApplicationSetting.PRESUPPOSE_SIX,color);
        int hue = useColorToHue(color);
        MessageUtils.sendMessageForSetColor(hue);
    }

    private void usePresupposeOnClick(int color ,boolean isCurrent){
        if(isCurrent){
            fragmentRgbBinding.rgbColorPicker.externalClickPresuppose(color);

            int hue = useColorToHue(color);
            Log.d(TAG, "usePresupposeOnClick: " + hue);
            MessageUtils.sendMessageForSetColor(hue);
        }
    }

//    辅助方法  获取当前颜色的hue值
    private int useColorToHue(int color){
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        float hue = hsv[0];
        int hueInt = (int)(hue +0.5f);
        return hueInt;
    }


}