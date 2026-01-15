package com.device.manager.sdk.bean;

/**
 * @Author: Mark
 * @date: 2024/4/25 Time：15:23
 * @description:
 */

public class ScannerType {

    private String zebra = "";
    private String totinfo = "";
    public void setZebra(String zebra) {
        this.zebra = zebra;
    }
    public String getZebra() {
        return zebra;
    }

    public void setTotinfo(String totinfo) {
        this.totinfo = totinfo;
    }
    public String getTotinfo() {
        return totinfo;
    }

    @Override
    public String toString() {
        return "ScannerType{" +
                "zebra='" + zebra + '\'' +
                ", totinfo='" + totinfo + '\'' +
                '}';
    }
}