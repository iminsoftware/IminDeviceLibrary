package com.example.imindevicelibrary.bean;

import java.util.List;

public class DeviceInfoDisplayBean {
    public String key;
    public String name;
    public String valve;
    public List<InfoBean> datas;
    public static class InfoBean{
        public String name;
        public String valve;
        public String detialInfo;
        public String key;
        public boolean isTitle;
    }
}
