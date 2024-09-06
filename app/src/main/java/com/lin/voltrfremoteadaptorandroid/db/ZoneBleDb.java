package com.lin.voltrfremoteadaptorandroid.db;

import com.orm.SugarRecord;
import com.lin.voltrfremoteadaptorandroid.ConfigData;

import java.util.List;

public class ZoneBleDb extends SugarRecord {

    private long zoneId;
    private long bleId;

    public ZoneBleDb() {
    }

    public ZoneBleDb(long zoneId, long bleId) {
        this.zoneId = zoneId;
        this.bleId = bleId;
    }

    public static void deleteById(long id,int type){
        String sql;
        switch (type){
            case ConfigData.TYPE_BLE:
                sql = "ble_id = ?";
                break;
            case ConfigData.TYPE_ZONE:
                sql = "zone_id = ?";
                break;
            default:
//                数据不对直接返回
               return;
        }

        // 查询 bleId = id 的所有记录
        List<ZoneBleDb> myList = ZoneBleDb.find(ZoneBleDb.class, sql, String.valueOf(id));
        // 删除所有查询到的记录
        for (ZoneBleDb zoneBleDb : myList) {
            zoneBleDb.delete();
        }
    }



}
