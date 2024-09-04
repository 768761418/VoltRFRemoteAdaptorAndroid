package com.lin.voltrfremoteadaptorandroid.db;

import com.orm.SugarRecord;

public class ZoneBleDb extends SugarRecord {

    private Long id;
    private long zoneId;
    private long bleId;
    private boolean deleted = false; // 默认为 false，表示未删除

    public ZoneBleDb() {
    }

    public ZoneBleDb(long zoneId, long bleId) {
        this.zoneId = zoneId;
        this.bleId = bleId;
    }



    //    软删除，保证数据统一性
    public void softDelete() {
        this.deleted = true;
        this.save(); // 保存更改
    }

}
