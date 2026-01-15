package com.device.manager.sdk.bean;

/**
 * @Author: Mark
 * @date: 2024/4/25 Time：15:24
 * @description:
 */

public class RemoveCharacters {

    private Integer before;
    private Integer after;
    public void setBefore(Integer before) {
        this.before = before;
    }
    public Integer getBefore() {
        return before;
    }

    public void setAfter(Integer after) {
        this.after = after;
    }
    public Integer getAfter() {
        return after;
    }

    @Override
    public String toString() {
        return "RemoveCharacters{" +
                "before=" + before +
                ", after=" + after +
                '}';
    }
}
