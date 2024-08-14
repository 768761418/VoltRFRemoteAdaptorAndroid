package com.lin.voltrfremoteadaptorandroid.Bean;

public class ZoneBean {
    private int logoId;
    private String zoneName;

    public ZoneBean(int logoId, String zoneName) {
        this.logoId = logoId;
        this.zoneName = zoneName;
    }

    public int getLogoId() {
        return logoId;
    }

    public void setLogoId(int logoId) {
        this.logoId = logoId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }
}
