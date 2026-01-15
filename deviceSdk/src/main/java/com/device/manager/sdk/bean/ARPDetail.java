package com.device.manager.sdk.bean;

public class ARPDetail {


    public String ip ="";

    public ARPDetail(String ip, String mac, String hostname) {
        this.ip = ip;
        this.mac = mac;
        this.hostname = hostname;
    }

    public String mac ="";
    public String hostname="";
}
