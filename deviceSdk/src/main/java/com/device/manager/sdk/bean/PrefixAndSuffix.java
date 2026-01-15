package com.device.manager.sdk.bean;

/**
 * @Author: Mark
 * @date: 2024/4/25 Time：15:23
 * @description:
 */

public class PrefixAndSuffix {

    private String prefix;
    private String suffix;
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    public String getPrefix() {
        return prefix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
    public String getSuffix() {
        return suffix;
    }

    @Override
    public String toString() {
        return "PrefixAndSuffix{" +
                "prefix='" + prefix + '\'' +
                ", suffix='" + suffix + '\'' +
                '}';
    }
}
