package com.lin.voltrfremoteadaptorandroid.Activity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.lin.voltrfremoteadaptorandroid.Adapter.common.CommonAdapter;
import com.lin.voltrfremoteadaptorandroid.Adapter.common.CommonViewHolder;
import com.lin.voltrfremoteadaptorandroid.R;
import com.lin.voltrfremoteadaptorandroid.databinding.LayoutBleBinding;
import com.vise.baseble.ViseBle;
import com.vise.baseble.callback.scan.IScanCallback;
import com.vise.baseble.callback.scan.ScanCallback;
import com.vise.baseble.model.BluetoothLeDevice;
import com.vise.baseble.model.BluetoothLeDeviceStore;

import java.util.ArrayList;
import java.util.List;

public class BleActivity extends BaseActivity{
    private LayoutBleBinding layoutBleBinding;
    private final String TAG = "BleActivity";
    private List<BluetoothLeDevice> bleList ;
    private CommonAdapter<BluetoothLeDevice> commonAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBleBinding = DataBindingUtil.setContentView(BleActivity.this, R.layout.layout_ble);
        initData();
        initUI();
    }


    private void initData(){
        bleList = new ArrayList<>();

        commonAdapter = new CommonAdapter<BluetoothLeDevice>(BleActivity.this,bleList,R.layout.item_ble) {
            @Override
            public void bindData(CommonViewHolder holder, BluetoothLeDevice data, int position) {
//                初始化内容
                holder.setText(R.id.ble_item_name,"name:" + data.getName());
                holder.setText(R.id.ble_item_mac,"mac:" + data.getAddress());
                holder.setViewImageRes(R.id.ble_item_logo,R.drawable.icon_ble_item);
//                点击事件
                holder.setCommonClickListener(new CommonViewHolder.OnCommonItemEventListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemLongClick(int viewId, int position) {

                    }
                });

            }
        };


        ViseBle.getInstance().startScan(new ScanCallback(new IScanCallback() {
            @Override
            public void onDeviceFound(BluetoothLeDevice bluetoothLeDevice) {
                Log.d(TAG, "onDeviceFound: "  + bluetoothLeDevice.getName());
                if (bluetoothLeDevice.getName() != null){
                    // 检查列表中是否已存在该设备
                    boolean deviceExists = false;
                    for (BluetoothLeDevice device : bleList) {
                        if (device.getAddress().equals(bluetoothLeDevice.getAddress())) {
                            deviceExists = true;
                            break;
                        }
                    }

                    // 如果设备不存在于列表中，则添加
                    if (!deviceExists) {
                        bleList.add(bluetoothLeDevice);
                        commonAdapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onScanFinish(BluetoothLeDeviceStore bluetoothLeDeviceStore) {
                Log.d(TAG, "onScanFinish: " +bleList);
            }

            @Override
            public void onScanTimeout() {

            }
        }));
    }

    private void initUI(){
        layoutBleBinding.topBar.initTopBar("Bluetooth",true);
        layoutBleBinding.bleList.setAdapter(commonAdapter);
    }


}
