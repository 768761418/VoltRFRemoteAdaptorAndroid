package com.lin.voltrfremoteadaptorandroid.Activity.homeFgm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lin.voltrfremoteadaptorandroid.Adapter.common.CommonAdapter;
import com.lin.voltrfremoteadaptorandroid.Adapter.common.CommonViewHolder;
import com.lin.voltrfremoteadaptorandroid.ConfigData;
import com.lin.voltrfremoteadaptorandroid.R;
import com.lin.voltrfremoteadaptorandroid.databinding.FragmentNetworkBinding;
import com.lin.voltrfremoteadaptorandroid.db.BleDb;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NetworkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NetworkFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentNetworkBinding fragmentNetworkBinding;
//    广播管理器
    private LocalBroadcastManager localBroadcastManager;
//    广播
    private BroadcastReceiver broadcastReceiver;
//    蓝牙列表
    private List<BleDb> bleDbs;
    private CommonAdapter<BleDb> commonAdapter;


    public NetworkFragment() {
        // Required empty public constructor
    }

//    关闭的时候关闭广播
    @Override
    public void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(broadcastReceiver);
    }




    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NetworkFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NetworkFragment newInstance(String param1, String param2) {
        NetworkFragment fragment = new NetworkFragment();
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
//        注册广播事件
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
//                在主线程更新
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bleDbs.clear();
                        bleDbs = BleDb.getAllBle();
                        commonAdapter.updateData();
                    }
                });
            }
        };
//        初始化广播管理器
        localBroadcastManager= LocalBroadcastManager.getInstance(getContext());
//        设置过滤器
        IntentFilter filter = new IntentFilter(ConfigData.BROADCAST_BLE_UPDATE);
//        监听广播
        localBroadcastManager.registerReceiver(broadcastReceiver,filter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentNetworkBinding = fragmentNetworkBinding.inflate(inflater,container,false);
//        初始化界面
        initUI();
        return fragmentNetworkBinding.getRoot();
    }


    private void initUI(){
//        获取数据库里面的蓝牙信息
        bleDbs =BleDb.getAllBle();
        commonAdapter = new CommonAdapter<BleDb>(getContext(),bleDbs,R.layout.item_ble) {
            @Override
            public void bindData(CommonViewHolder holder, BleDb data, int position) {
                holder.setViewImageRes(R.id.ble_item_logo,R.drawable.icon_connect_ble);
                holder.setText(R.id.ble_item_name,data.getName());
                holder.setText(R.id.ble_item_mac,data.getAddress());
            }
        };
        fragmentNetworkBinding.connectBleList.setAdapter(commonAdapter);
    }
}