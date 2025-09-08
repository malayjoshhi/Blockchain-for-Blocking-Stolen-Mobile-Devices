package com.example.blockchain_app;


import java.util.*;

public class Blockchain {
    private static List<Device> blockchain = new ArrayList<>();

    public static void addDevice(Device device) {
        blockchain.add(device);
    }

    public static List<Device> getAllDevices() {
        return blockchain;
    }

    public static Device findDeviceByImei(String imei) {
        for (Device device : blockchain) {
            if (device.getImei().equals(imei)) {
                return device;
            }
        }
        return null;
    }
}



