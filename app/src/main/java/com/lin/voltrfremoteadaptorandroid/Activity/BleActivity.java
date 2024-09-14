package com.lin.voltrfremoteadaptorandroid.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.lin.voltrfremoteadaptorandroid.Adapter.common.CommonAdapter;
import com.lin.voltrfremoteadaptorandroid.Adapter.common.CommonViewHolder;
import com.lin.voltrfremoteadaptorandroid.ConfigData;
import com.lin.voltrfremoteadaptorandroid.R;
import com.lin.voltrfremoteadaptorandroid.databinding.LayoutBleBinding;
import com.lin.voltrfremoteadaptorandroid.db.BleDb;
import com.lin.voltrfremoteadaptorandroid.view.LoadingDialog;
import com.vise.baseble.ViseBle;
import com.vise.baseble.callback.IConnectCallback;
import com.vise.baseble.callback.scan.IScanCallback;
import com.vise.baseble.callback.scan.ScanCallback;
import com.vise.baseble.core.DeviceMirror;
import com.vise.baseble.exception.BleException;
import com.vise.baseble.model.BluetoothLeDevice;
import com.vise.baseble.model.BluetoothLeDeviceStore;

import java.util.ArrayList;
import java.util.List;


public class BleActivity extends BaseActivity{
    private LayoutBleBinding layoutBleBinding;
    private final String TAG = "BleActivity";
    private List<BluetoothLeDevice>  bleList = new ArrayList<>();
    private CommonAdapter<BluetoothLeDevice> commonAdapter;
    private LoadingDialog loadingDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBleBinding = DataBindingUtil.setContentView(BleActivity.this, R.layout.layout_ble);
        loadingDialog = new LoadingDialog(this,R.drawable.gif_loading,"Bluetooth is connecting");
        if ( XXPermissions.isGranted(this, Permission.Group.BLUETOOTH)){
            initData();
            initUI();
        }else {
            initPermission();
        }

        layoutBleBinding.demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClickble: " + ViseBle.getInstance().getDeviceMirrorPool().getDeviceList());
            }
        });
    }


    private void initData(){

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
                        loadingDialog.show();
                        bleConnect(data);
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
//                     TODO 判断是否在蓝牙连接池中，如果在的话就不显示


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
                Toast.makeText(BleActivity.this,"Bluetooth scan complete",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScanTimeout() {

            }
        }));

//        MeshManagerApi mMeshManagerApi = new MeshManagerApi(context);
    }

    private void initUI(){
        layoutBleBinding.topBar.initTopBar("Bluetooth",true);
        layoutBleBinding.bleList.setAdapter(commonAdapter);
    }


    private void bleConnect(BluetoothLeDevice bluetoothLeDevice){
        Log.d(TAG, "bleConnect: ");


        ViseBle.getInstance().connect(bluetoothLeDevice, new IConnectCallback() {
            @Override
            public void onConnectSuccess(DeviceMirror deviceMirror) {
                BleDb bleDb = new BleDb(
                        deviceMirror.getBluetoothLeDevice().getName(),
                        deviceMirror.getBluetoothLeDevice().getAddress(),
                        deviceMirror.getBluetoothLeDevice().getBluetoothDeviceClassName(),
                        deviceMirror.getBluetoothLeDevice().getBluetoothDeviceMajorClassName()
                );
                bleDb.save();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(BleActivity.this,getString(R.string.ble_connect_message),Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
//                        发送广播，通知ble界面更新数据
                        Intent intent = new Intent(ConfigData.BROADCAST_BLE_UPDATE);
                        LocalBroadcastManager.getInstance(BleActivity.this).sendBroadcast(intent);
//                        finish();
                    }
                });

            }

            @Override
            public void onConnectFailure(BleException exception) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(BleActivity.this,"Bluetooth connect fail" + exception,Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
                    }
                });

            }

            @Override
            public void onDisconnect(boolean isActive) {

            }
        });
    }


}
