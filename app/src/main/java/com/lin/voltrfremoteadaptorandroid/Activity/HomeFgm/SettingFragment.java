package com.lin.voltrfremoteadaptorandroid.Activity.HomeFgm;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lin.voltrfremoteadaptorandroid.Bean.SettingBean;
import com.lin.voltrfremoteadaptorandroid.Adapter.common.CommonAdapter;
import com.lin.voltrfremoteadaptorandroid.Adapter.common.CommonViewHolder;
import com.lin.voltrfremoteadaptorandroid.R;
import com.lin.voltrfremoteadaptorandroid.databinding.FragmentSettingBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentSettingBinding fragmentSettingBinding;
    private CommonAdapter<SettingBean> commonAdapter;
    private List<SettingBean> settingBeans;
    private String TAG = "SettingFragment";
    private String version;

    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentSettingBinding = fragmentSettingBinding.inflate(inflater,container,false);
        initData();
        initUI();
        return fragmentSettingBinding.getRoot();
    }

    private void initData(){
        settingBeans = new ArrayList<>();
        settingBeans.add(new SettingBean("联系电话","13543006552"));
        settingBeans.add(new SettingBean("242","333"));

    }

    private void initUI(){
        Log.d(TAG, "initUI: " + settingBeans);
        commonAdapter = new CommonAdapter<SettingBean>(getContext(),settingBeans,R.layout.item_setting) {
            @Override
            public void bindData(CommonViewHolder holder, SettingBean data, int position) {
                holder.setText(R.id.setting_item_name, data.getName());
                holder.setText(R.id.setting_item_value, data.getValue());
                holder.setCommonClickListener(new CommonViewHolder.OnCommonItemEventListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(getContext(),data.getName() +": " + data.getValue(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemLongClick(int viewId, int position) {

                    }
                });


            }
        };
        //禁止滑动  布局管理器
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext()){
            //禁止竖向滑动 RecyclerView 为垂直状态（VERTICAL）
            @Override
            public boolean canScrollVertically() {
                return false;
            }
            //禁止横向滑动 RecyclerView 为水平状态（HORIZONTAL）
            /*@Override
            public boolean canScrollHorizontally() {
                return false;
            }*/
        };
        fragmentSettingBinding.settingList.setLayoutManager(linearLayoutManager);
        fragmentSettingBinding.settingList.setAdapter(commonAdapter);
        fragmentSettingBinding.settingList.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));



        try {
            version = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (version!= null){
            fragmentSettingBinding.versionNumber.setText(version);
        }
    }
}