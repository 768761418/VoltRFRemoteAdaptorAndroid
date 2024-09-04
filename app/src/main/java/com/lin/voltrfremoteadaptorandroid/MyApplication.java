package com.lin.voltrfremoteadaptorandroid;

import android.util.Log;

import com.orm.SugarContext;
import com.vise.baseble.ViseBle;


/**
 * 应用入口
 * 　　　　　　　　┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━┛┻┓
 * 　　　　　　　┃　　　　　　　┃
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
//        AopArms.init(this);
        Log.d(TAG, "onCreate:上课 ");
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

//    private void initBle() {
//        Ble.options()
//                .setLogBleEnable(true)//设置是否输出打印蓝牙日志
//                .setThrowBleException(true)//设置是否抛出蓝牙异常
//                .setLogTAG("AndroidBLE")//设置全局蓝牙操作日志TAG
//                .setAutoConnect(false)//设置是否自动连接
//                .setIgnoreRepeat(false)//设置是否过滤扫描到的设备(已扫描到的不会再次扫描)
//                .setConnectFailedRetryCount(3)//连接异常时（如蓝牙协议栈错误）,重新连接次数
//                .setConnectTimeout(10 * 1000)//设置连接超时时长
//                .setScanPeriod(12 * 1000)//设置扫描时长
//                .setMaxConnectNum(7)//最大连接数量
//                .setUuidService(UUID.fromString(UuidUtils.uuid16To128("fd00")))//设置主服务的uuid
//                .setUuidWriteCha(UUID.fromString(UuidUtils.uuid16To128("fd01")))//设置可写特征的uuid
//                .setUuidReadCha(UUID.fromString(UuidUtils.uuid16To128("fd02")))//设置可读特征的uuid （选填）
//                .setUuidNotifyCha(UUID.fromString(UuidUtils.uuid16To128("fd03")))//设置可通知特征的uuid （选填，库中默认已匹配可通知特征的uuid）
//                .setFactory(new BleFactory<BleRssiDevice>() {//实现自定义BleDevice时必须设置
//                    @Override
//                    public BleRssiDevice create(String address, String name) {
//                        return new BleRssiDevice(address, name);//自定义BleDevice的子类
//                    }
//                })
//                .setBleWrapperCallback(new MyBleWrapperCallback())
//                .create(mApplication, new Ble.InitCallback() {
//                    @Override
//                    public void success() {
//                        BleLog.e("MainApplication", "初始化成功");
//                    }
//
//                    @Override
//                    public void failed(int failedCode) {
//                        BleLog.e("MainApplication", "初始化失败：" + failedCode);
//                    }
//                });
//    }
}
