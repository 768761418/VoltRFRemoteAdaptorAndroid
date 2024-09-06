package com.lin.voltrfremoteadaptorandroid.db;

import com.lin.voltrfremoteadaptorandroid.ConfigData;
import com.orm.SugarRecord;

public class BleDb extends SugarRecord {
//    名称
    private String name;
//    地址
    private String address;
//    类别
    private String className;
//    服务类别
    private String majorClassName;
//    配对状态
    private String knownSupportedServices;

    public BleDb() {
    }

    public BleDb(String name, String address, String className, String majorClassName, String knownSupportedServices) {
        this.name = name;
        this.address = address;
        this.className = className;
        this.majorClassName = majorClassName;
        this.knownSupportedServices = knownSupportedServices;
    }

    // 静态方法，通过id查询ZoneDb记录
    public static BleDb findZoneById(Long id) {
        return BleDb.findById(BleDb.class, id);
    }

    //    通过id删除ZoneDb记录
    public static void deleteZoneById(Long id) {
        BleDb ble = BleDb.findById(BleDb.class, id);
        if (ble != null) {
            ble.delete(); // 删除该记录
            ZoneBleDb.deleteById(id, ConfigData.TYPE_BLE); //删除关系表中的数据
        }
    }
}
