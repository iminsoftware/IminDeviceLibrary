package com.example.imindevicelibrary.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class DeviceControlBean implements MultiItemEntity {
    public String name;
    public String infoTips;
    public int type;
    public List<DeviceControl> deviceControls;

    @Override
    public int getItemType() {
        return type;
    }

    public static class DeviceControl{
        public String name;
        public String controlTips;
    }
}
