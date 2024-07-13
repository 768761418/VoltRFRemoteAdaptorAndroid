package com.lin.voltrfremoteadaptorandroid.Activity.colorControl;

import android.graphics.Color;
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

import com.lin.voltrfremoteadaptorandroid.ApplicationData;
import com.lin.voltrfremoteadaptorandroid.Utils.ColorUtils;
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
    public void onResume() {
        super.onResume();
        fragmentRgbBinding.luminanceAndZone.setProgress(ApplicationData.luminanceData,true);
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
                ColorUtils.UtilsChangePresuppose(colors[i], imageViews[i],true);
            }else {
                ColorUtils.UtilsChangePresuppose(colors[i], imageViews[i],false);
            }
        }

        int touchX = sharedPreferencesUtils.loadIntData(ApplicationSetting.PRESUPPOSE_SIX_TOUCH_X,0);
        int touchY = sharedPreferencesUtils.loadIntData(ApplicationSetting.PRESUPPOSE_SIX_TOUCH_Y,0);
        if (touchX != 0 && touchY != 0) {
            fragmentRgbBinding.rgbColorPicker.setTouchX(touchX);
            fragmentRgbBinding.rgbColorPicker.setTouchY(touchY);

        }else {
            fragmentRgbBinding.rgbColorPicker.setOnDrawCompletedCallback(new Runnable() {
                @Override
                public void run() {
                    int[] resultXY = fragmentRgbBinding.rgbColorPicker.setStartColor(presuppose6);
                    fragmentRgbBinding.rgbColorPicker.setTouchX(resultXY[0]);
                    fragmentRgbBinding.rgbColorPicker.setTouchY(resultXY[1]);

                }
            });


        }
        fragmentRgbBinding.rgbColorPicker.setSelectedColor(presuppose6);


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
                ColorUtils.UtilsChangePresuppose(presuppose6,fragmentRgbBinding.rgbPresuppose6,true);
            }
        });
//        抬起修改选中预设
        fragmentRgbBinding.rgbColorPicker.setKeyUpListener(new ColorPickerView.KeyUpListener() {
            @Override
            public void saveColor(int color,int touchX,int touchY) {
                sharedPreferencesUtils.saveIntData(ApplicationSetting.PRESUPPOSE_SIX,color);
                sharedPreferencesUtils.saveIntData(ApplicationSetting.PRESUPPOSE_SIX_TOUCH_X,touchX);
                sharedPreferencesUtils.saveIntData(ApplicationSetting.PRESUPPOSE_SIX_TOUCH_Y,touchY);
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
                colorChooseDialog.setDoneOnclickListener(new ColorChooseDialog.DoneOnclickListener() {
                    @Override
                    public void onDoneOnclickListener() {
                        Log.d(TAG, "onDoneOnclickListener:  +klklx");
                        colorChooseDialog.setNewPresuppose(1);
//                        ColorUtils.UtilsChangePresuppose(colors[i], imageViews[i],false);
                    }
                });
                return true;
            }
        });
        fragmentRgbBinding.rgbPresuppose2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                colorChooseDialog.show();
                colorChooseDialog.setColor(presuppose2);
                return true;
            }
        });

        fragmentRgbBinding.rgbPresuppose3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                colorChooseDialog.show();
                colorChooseDialog.setColor(presuppose3);
                return true;
            }
        });

        fragmentRgbBinding.rgbPresuppose4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                colorChooseDialog.show();
                colorChooseDialog.setColor(presuppose4);
                return true;
            }
        });

        fragmentRgbBinding.rgbPresuppose5.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                colorChooseDialog.show();
                colorChooseDialog.setColor(presuppose5);
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
        int[] result = fragmentRgbBinding.rgbColorPicker.externalClickPresuppose(color);
        ColorUtils.UtilsChangePresuppose(color,fragmentRgbBinding.rgbPresuppose6,true);
        presuppose6 = color;
        sharedPreferencesUtils.saveIntData(ApplicationSetting.PRESUPPOSE_SIX,color);
        sharedPreferencesUtils.saveIntData(ApplicationSetting.PRESUPPOSE_SIX_TOUCH_X,result[0]);
        sharedPreferencesUtils.saveIntData(ApplicationSetting.PRESUPPOSE_SIX_TOUCH_Y,result[1]);
        int hue = useColorToHue(color);
        MessageUtils.sendMessageForSetColor(hue);
    }

    private void usePresupposeOnClick(int color ,boolean isCurrent){
        if(isCurrent){
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