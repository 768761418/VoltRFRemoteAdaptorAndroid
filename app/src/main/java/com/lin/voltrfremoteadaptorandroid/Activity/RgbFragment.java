package com.lin.voltrfremoteadaptorandroid.Activity;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.PopupWindow;
import com.lin.voltrfremoteadaptorandroid.R;
import com.lin.voltrfremoteadaptorandroid.databinding.FragmentRgbBinding;
import com.lin.voltrfremoteadaptorandroid.module.ColorChooseModule;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RgbFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RgbFragment extends Fragment {

    FragmentRgbBinding fragmentRgbBinding;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
       fragmentRgbBinding = fragmentRgbBinding.inflate(inflater,container,false);




       return fragmentRgbBinding.getRoot();
    }



}