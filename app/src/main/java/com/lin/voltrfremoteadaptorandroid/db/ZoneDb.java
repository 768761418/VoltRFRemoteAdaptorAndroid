package com.lin.voltrfremoteadaptorandroid.db;

import com.orm.SugarRecord;

import java.util.List;

public class ZoneDb extends SugarRecord{

    private Long id;
    private String zoneName;
    private boolean deleted = false; // 默认为 false，表示未删除

    public ZoneDb() {
    }

    public ZoneDb(String zoneName) {
        this.zoneName = zoneName;
    }

    @Override
    public Long getId() {
        return id;
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

    // 获取所有数据
    public static List<ZoneDb> getAllZones() {
        return ZoneDb.find(ZoneDb.class, "deleted = ?", "0");
    }

//    软删除，保证数据统一性
    public void softDelete() {
        this.deleted = true;
        this.save(); // 保存更改
    }



}
