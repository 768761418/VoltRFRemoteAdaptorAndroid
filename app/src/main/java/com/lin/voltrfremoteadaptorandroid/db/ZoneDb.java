package com.lin.voltrfremoteadaptorandroid.db;

import android.util.Log;

import com.lin.voltrfremoteadaptorandroid.ConfigData;
import com.orm.SugarRecord;

import java.util.List;

public class ZoneDb extends SugarRecord{

    private String zoneName;

    public ZoneDb() {
    }

    public ZoneDb(String zoneName) {
        this.zoneName = zoneName;
    }



    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }


    // 获取所有数据
    public static List<ZoneDb> getAllZones() {
        return ZoneDb.listAll(ZoneDb.class);
    }


    // 静态方法，通过id查询ZoneDb记录
    public static ZoneDb findZoneById(Long id) {
        return ZoneDb.findById(ZoneDb.class, id);
    }

//    通过id删除ZoneDb记录
    public static void deleteZoneById(Long id) {
        ZoneDb zone = ZoneDb.findById(ZoneDb.class, id);
        if (zone != null) {
            zone.delete(); // 删除该记录
            ZoneBleDb.deleteById(id, ConfigData.TYPE_ZONE);
        }
    }



}
