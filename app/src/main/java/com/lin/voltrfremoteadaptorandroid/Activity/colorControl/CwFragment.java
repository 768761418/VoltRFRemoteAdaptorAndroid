package com.lin.voltrfremoteadaptorandroid.Activity.colorControl;

import android.graphics.Color;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.lin.voltrfremoteadaptorandroid.ApplicationData;
import com.lin.voltrfremoteadaptorandroid.R;
import com.lin.voltrfremoteadaptorandroid.Utils.ColorUtils;
import com.lin.voltrfremoteadaptorandroid.Utils.MessageUtils;
import com.lin.voltrfremoteadaptorandroid.Utils.SharedPreferencesUtils;
import com.lin.voltrfremoteadaptorandroid.databinding.FragmentCwBinding;
import com.lin.voltrfremoteadaptorandroid.setting.ApplicationSetting;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CwFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CwFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int presuppose1,presuppose2,presuppose3,presuppose4,presuppose5;
    private FragmentCwBinding fragmentCwBinding;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private final static String TAG = "CwFragment";


    public CwFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CwFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CwFragment newInstance(String param1, String param2) {
        CwFragment fragment = new CwFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onPrimaryNavigationFragmentChanged(boolean isPrimaryNavigationFragment) {
        super.onPrimaryNavigationFragmentChanged(isPrimaryNavigationFragment);

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
        fragmentCwBinding.luminanceAndZone.setProgress(ApplicationData.luminanceData,true);
        fragmentCwBinding.cwTemperatureSeekbar.setProgress(ApplicationData.temperatureData);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        初始化cw界面的预设
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(getContext());
        ApplicationData.temperatureData = sharedPreferencesUtils.loadIntData(ApplicationSetting.PRESUPPOSE_CW,2200);
        String strCurrentText = ApplicationData.temperatureData + "k";
        fragmentCwBinding.cwCurrentText.setText(strCurrentText);
        fragmentCwBinding.cwTemperatureSeekbar.setProgress(ApplicationData.temperatureData);
        useChangeCwPresuppose5(ApplicationData.temperatureData);
        
        ColorUtils.UtilsChangePresuppose(getContext().getColor(R.color.cw_presuppose1), fragmentCwBinding.cwPresuppose1,false);
        ColorUtils.UtilsChangePresuppose(getContext().getColor(R.color.cw_presuppose2), fragmentCwBinding.cwPresuppose2,false);
        ColorUtils.UtilsChangePresuppose(getContext().getColor(R.color.cw_presuppose3), fragmentCwBinding.cwPresuppose3,false);
        ColorUtils.UtilsChangePresuppose(getContext().getColor(R.color.cw_presuppose4), fragmentCwBinding.cwPresuppose4,false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentCwBinding = fragmentCwBinding.inflate(inflater,container,false);

        changeTemperature();
//        预设点击事件
        presupposeClick();
        return fragmentCwBinding.getRoot();
    }

    private void changeTemperature(){
        fragmentCwBinding.cwTemperatureSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int current, boolean b) {
                String strCurrentText = current + "k";
                fragmentCwBinding.cwCurrentText.setText(strCurrentText);
                ApplicationData.temperatureData = current;
                useChangeCwPresuppose5(ApplicationData.temperatureData);
                MessageUtils.sendMessageForTemperature(current);
                Log.d(TAG, "onProgressChanged: " + ApplicationData.temperatureData );
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                sharedPreferencesUtils.saveIntData(ApplicationSetting.PRESUPPOSE_CW,seekBar.getProgress());
            }
        });
    }

    private void presupposeClick(){
        fragmentCwBinding.cwPresuppose1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplicationData.temperatureData = 2200;
                fragmentCwBinding.cwTemperatureSeekbar.setProgress(ApplicationData.temperatureData,true);
                sharedPreferencesUtils.saveIntData(ApplicationSetting.PRESUPPOSE_CW,ApplicationData.temperatureData);
                MessageUtils.sendMessageForTemperature(ApplicationData.temperatureData);
            }
        });
        fragmentCwBinding.cwPresuppose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplicationData.temperatureData = 2700;
                fragmentCwBinding.cwTemperatureSeekbar.setProgress(ApplicationData.temperatureData,true);
                sharedPreferencesUtils.saveIntData(ApplicationSetting.PRESUPPOSE_CW,ApplicationData.temperatureData);
                MessageUtils.sendMessageForTemperature(ApplicationData.temperatureData);
            }
        });
        fragmentCwBinding.cwPresuppose3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplicationData.temperatureData = 3000;
                fragmentCwBinding.cwTemperatureSeekbar.setProgress(ApplicationData.temperatureData,true);
                sharedPreferencesUtils.saveIntData(ApplicationSetting.PRESUPPOSE_CW,ApplicationData.temperatureData);
                MessageUtils.sendMessageForTemperature(ApplicationData.temperatureData);
            }
        });
        fragmentCwBinding.cwPresuppose4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplicationData.temperatureData = 4000;
                fragmentCwBinding.cwTemperatureSeekbar.setProgress(ApplicationData.temperatureData,true);
                sharedPreferencesUtils.saveIntData(ApplicationSetting.PRESUPPOSE_CW,ApplicationData.temperatureData);
                MessageUtils.sendMessageForTemperature(ApplicationData.temperatureData);
            }
        });

        fragmentCwBinding.cwPresuppose5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageUtils.sendMessageForTemperature(ApplicationData.temperatureData);
            }
        });
    }





    private void useChangeCwPresuppose5(int current){
        float radio = useGetRadio(current);
        int color = useGetCurrentTemperatureColor(radio);
        ColorUtils.UtilsChangePresuppose(color, fragmentCwBinding.cwPresuppose5,false);
    }
    private float useGetRadio(int current){
        float radio = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            radio = (float) (current-fragmentCwBinding.cwTemperatureSeekbar.getMin())/(fragmentCwBinding.cwTemperatureSeekbar.getMax() -fragmentCwBinding.cwTemperatureSeekbar.getMin());
        }
        return radio;
    }

    private int useGetCurrentTemperatureColor(float radio){
        int mStartColor = getContext().getColor(R.color.cw_presuppose1);
        int mEndColor = getContext().getColor(R.color.cw_presuppose4);
        int mCenterColor =getContext().getColor(R.color.cw_presuppose3);

        int redStart = Color.red(mStartColor);
        int blueStart = Color.blue(mStartColor);
        int greenStart = Color.green(mStartColor);

        int redCenter = Color.red(mCenterColor);
        int blueCenter = Color.blue(mCenterColor);
        int greenCenter = Color.green(mCenterColor);

        int redEnd = Color.red(mEndColor);
        int blueEnd = Color.blue(mEndColor);
        int greenEnd = Color.green(mEndColor);
        int red = (int) (redCenter + ((redEnd - redCenter) * radio + 0.5));
        int greed = (int) (greenCenter + ((greenEnd - greenCenter) * radio + 0.5));
        int blue = (int) (blueCenter + ((blueEnd - blueCenter) * radio + 0.5));

        if (radio < 0.5f){
            radio = radio*2f;
            red = (int) (redStart + ((redCenter - redStart) * radio + 0.5));
            greed = (int) (greenStart + ((greenCenter - greenStart) * radio + 0.5));
            blue = (int) (blueStart + ((blueCenter - blueStart) * radio + 0.5));
        }
        return Color.argb(255,red, greed, blue);
    }
}