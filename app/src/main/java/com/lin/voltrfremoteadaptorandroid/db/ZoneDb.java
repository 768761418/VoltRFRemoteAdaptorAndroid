package com.lin.voltrfremoteadaptorandroid.db;

import com.orm.SugarRecord;
import com.orm.dsl.Table;


@Table
public class ZoneDb {
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
}
