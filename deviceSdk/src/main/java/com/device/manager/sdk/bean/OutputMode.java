package com.device.manager.sdk.bean;

/**
 * @Author: Mark
 * @date: 2024/4/25 Time：15:24
 * @description:
 */

public class OutputMode {

    private Integer interval;
    private String mode;
    public void setInterval(Integer interval) {
        this.interval = interval;
    }
    public Integer getInterval() {
        return interval;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
    public String getMode() {
        return mode;
    }

    @Override
    public String toString() {
        return "OutputMode{" +
                "interval=" + interval +
                ", mode='" + mode + '\'' +
                '}';
    }
}