package com.device.manager.sdk.bean;

import java.util.List;

public class CPUDetail {
    public String model="";
    public String manufacturer="";
    public String revision="";
    public String cores="";
    public String totalCPU="";
    public String usedCPU="";
    public Double cpuTemperatureAlert=0.0;
    public String architecture="";

    public List<CPUDetail.CPUItem> cpu;

    public static class CPUItem{
        public String mode="";
        public String modeList="";
        public String online="";
        public String frequency="";
        public String minfreq="";
        public String maxfreq="";
        public String frequencyList="";
    }
}
