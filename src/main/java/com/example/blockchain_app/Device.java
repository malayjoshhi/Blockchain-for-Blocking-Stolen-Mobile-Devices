package com.example.blockchain_app;

public class Device {
    private String imei;
    private String ownerName;
    private boolean isLost;

    public Device(String imei, String ownerName) {
        this.imei = imei;
        this.ownerName = ownerName;
        this.isLost = false;
    }

    public String getImei() {
        return imei;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public boolean isLost() {
        return isLost;
    }

    public void setLost(boolean lost) {
        isLost = lost;
    }
}
