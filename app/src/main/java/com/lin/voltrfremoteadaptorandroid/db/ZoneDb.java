package com.lin.voltrfremoteadaptorandroid.db;

import android.util.Log;

import com.orm.SugarRecord;

import java.util.List;

public class ZoneDb extends SugarRecord{

    private String zoneName;
    private boolean deleted = false; // 默认为 false，表示未删除

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

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isDeleted() {
        return deleted;
    }

    // 获取所有数据
    public static List<ZoneDb> getAllZones() {
        return ZoneDb.find(ZoneDb.class, "deleted = ?", "0");
    }

    // 静态方法，通过id查询ZoneDb记录
    public static ZoneDb findZoneById(Long id) {
        return ZoneDb.findById(ZoneDb.class, id);
    }


    // 静态方法，传入id，根据id将deleted字段设置为true
    public static void deleteZoneById(Long id) {
        // 查找对应的记录
        ZoneDb zone = ZoneDb.findById(ZoneDb.class, id);
        Log.d("ZoneFragment", "查數據庫: " + id + zone);
        if (zone != null) {
            Log.d("ZoneFragment", "deleteZoneById: " + zone.getZoneName());
            // 更新deleted字段为true
            zone.setDeleted(true);
            // 保存更改
            zone.save();
        }
    }


}
