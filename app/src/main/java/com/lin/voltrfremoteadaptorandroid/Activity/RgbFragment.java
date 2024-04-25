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

import com.lin.voltrfremoteadaptorandroid.databinding.FragmentRgbBinding;
import com.lin.voltrfremoteadaptorandroid.setting.ApplicationSetting;


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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


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
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(ApplicationSetting.sharedPreferencesFileName, Context.MODE_PRIVATE);
        int[] colors = {sharedPreferences.getInt("presuppose1",0),
                        sharedPreferences.getInt("presuppose2",0),
                        sharedPreferences.getInt("presuppose3",0),
                        sharedPreferences.getInt("presuppose4",0),
                        sharedPreferences.getInt("presuppose5",0),
                        sharedPreferences.getInt("presuppose6",0)
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
                changeColorPresuppose(colors[i], imageViews[i],true);
            }else {
                changeColorPresuppose(colors[i], imageViews[i],false);
            }

        }


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        fragmentRgbBinding = fragmentRgbBinding.inflate(inflater,container,false);



        return fragmentRgbBinding.getRoot();
    }

//    修改初始颜色预设
    private void changeColorPresuppose(int color, ImageView imageview,Boolean isCurrent){
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




}