package com.lin.voltrfremoteadaptorandroid;

import android.Manifest;
import android.util.Log;

import androidx.annotation.NonNull;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.orm.SugarContext;
import com.vise.baseble.ViseBle;

import java.util.List;

import no.nordicsemi.android.mesh.MeshManagerApi;
import no.nordicsemi.android.mesh.MeshManagerCallbacks;
import no.nordicsemi.android.mesh.MeshNetwork;
import no.nordicsemi.android.mesh.provisionerstates.UnprovisionedMeshNode;


/**
 * 应用入口
 * 　　　　　　　  ┏┓　　┏┓
 * 　　　　　　　┏┛┻━━━┛┻━━━━┓
 * 　　　　　　　┃　　　　　　   ┃
 * 　　　　　　　┃　　　━　　　┃
 * 　　　　　　　┃　＞　　　＜　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃...　⌒　...　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃   神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┗━━━┓
 * 　　　　　　　　　┃　　　　　　　┣┓
 * 　　　　　　　　　┃　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛
 */

public class MyApplication extends android.app.Application {
    private static MyApplication mApplication;
    private final String TAG = "MyApplication";

    public static MyApplication getInstance() {
        return mApplication;
    }




    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;

        // 初始化 SugarORM
        SugarContext.init(this);

//        初始化蓝牙
        initBle();

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        // 终止 SugarORM
        SugarContext.terminate();
    }

    private void initBle(){
        //蓝牙相关配置修改
        ViseBle.config()
                .setScanTimeout(5000)//扫描超时时间，这里设置为永久扫描
                .setConnectTimeout(10 * 1000)//连接超时时间
                .setOperateTimeout(5 * 1000)//设置数据操作超时时间
                .setConnectRetryCount(3)//设置连接失败重试次数
                .setConnectRetryInterval(1000)//设置连接失败重试间隔时间
                .setOperateRetryCount(3)//设置数据操作失败重试次数
                .setOperateRetryInterval(1000)//设置数据操作失败重试间隔时间
                .setMaxConnectCount(3);//设置最大连接设备数量
        //蓝牙信息初始化，全局唯一，必须在应用初始化时调用
        ViseBle.getInstance().init(this);
        Log.d(TAG, "initBle: 初始化蓝牙成功");
    }

    private void initMesh(){
        MeshManagerApi mMeshManagerApi = new MeshManagerApi(mApplication);
//        mMeshManagerApi.setMeshManagerCallbacks(new MeshManagerCallbacks() {
//            @Override
//            public void onNetworkLoaded(MeshNetwork meshNetwork) {
//
//            }
//
//            @Override
//            public void onNetworkUpdated(MeshNetwork meshNetwork) {
//
//            }
//
//            @Override
//            public void onNetworkLoadFailed(String error) {
//
//            }
//
//            @Override
//            public void onNetworkImported(MeshNetwork meshNetwork) {
//
//            }
//
//            @Override
//            public void onNetworkImportFailed(String error) {
//
//            }
//
//            @Override
//            public void sendProvisioningPdu(UnprovisionedMeshNode meshNode, byte[] pdu) {
//
//            }
//
//            @Override
//            public void onMeshPduCreated(byte[] pdu) {
//
//            }
//
//            @Override
//            public int getMtu() {
//                return 0;
//            }
//        });
//        mMeshManagerApi.setProvisioningStatusCallbacks(this);
//        mMeshManagerApi.setMeshStatusCallbacks(this);
        mMeshManagerApi.loadMeshNetwork();
//        mMeshManagerApi.ide

    }



}
