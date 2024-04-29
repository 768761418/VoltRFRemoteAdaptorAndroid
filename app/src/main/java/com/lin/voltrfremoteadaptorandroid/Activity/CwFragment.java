package com.lin.voltrfremoteadaptorandroid.Activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(getContext());
        int currentText = sharedPreferencesUtils.loadIntData(ApplicationSetting.PRESUPPOSE_CW,2200);
        String strCurrentText = currentText + "k";
        fragmentCwBinding.cwCurrentText.setText(strCurrentText);
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
                MessageUtils.sendMessageForTemperature(current);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void presupposeClick(){
        fragmentCwBinding.cwPresuppose1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragmentCwBinding.cwTemperatureSeekbar.setProgress(2200,true);
                ColorUtils.UtilsChangePresuppose(getContext().getColor(R.color.cw_presuppose1), fragmentCwBinding.cwPresuppose5,false);
                sharedPreferencesUtils.saveIntData(ApplicationSetting.PRESUPPOSE_CW,2200);
            }
        });
        fragmentCwBinding.cwPresuppose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragmentCwBinding.cwTemperatureSeekbar.setProgress(2700,true);
                ColorUtils.UtilsChangePresuppose(getContext().getColor(R.color.cw_presuppose2), fragmentCwBinding.cwPresuppose5,false);
                sharedPreferencesUtils.saveIntData(ApplicationSetting.PRESUPPOSE_CW,2700);
            }
        });
        fragmentCwBinding.cwPresuppose3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragmentCwBinding.cwTemperatureSeekbar.setProgress(3000,true);
                ColorUtils.UtilsChangePresuppose(getContext().getColor(R.color.cw_presuppose3), fragmentCwBinding.cwPresuppose5,false);
                sharedPreferencesUtils.saveIntData(ApplicationSetting.PRESUPPOSE_CW,3000);
            }
        });
        fragmentCwBinding.cwPresuppose4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragmentCwBinding.cwTemperatureSeekbar.setProgress(4000,true);
                ColorUtils.UtilsChangePresuppose(getContext().getColor(R.color.cw_presuppose4), fragmentCwBinding.cwPresuppose5,false);
                sharedPreferencesUtils.saveIntData(ApplicationSetting.PRESUPPOSE_CW,4000);
            }
        });
    }

}