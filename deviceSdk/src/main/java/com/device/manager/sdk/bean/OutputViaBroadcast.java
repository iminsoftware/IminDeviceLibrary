package com.device.manager.sdk.bean;

/**
 * @Author: Mark
 * @date: 2024/4/25 Time：15:23
 * @description:
 */

public class OutputViaBroadcast {

    private String scanResultBroadcastName;
    private String keyName;
    private String orginalByteName;
    private Boolean isOpen;

    public String getScanResultBroadcastName() {
        return scanResultBroadcastName;
    }

    public void setScanResultBroadcastName(String scanResultBroadcastName) {
        this.scanResultBroadcastName = scanResultBroadcastName;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getOrginalByteName() {
        return orginalByteName;
    }

    public void setOrginalByteName(String orginalByteName) {
        this.orginalByteName = orginalByteName;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    @Override
    public String toString() {
        return "OutputViaBroadcast{" +
                "scanResultBroadcastName='" + scanResultBroadcastName + '\'' +
                ", keyName='" + keyName + '\'' +
                ", orginalByteName='" + orginalByteName + '\'' +
                ", isOpen=" + isOpen +
                '}';
    }
}
