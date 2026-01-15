package com.device.manager.sdk.bean;

import java.util.List;

public class NFCDetail {
    public String type="";
    public String model="";
    public List<TechDetail> techSupportList;
    public static class  TechDetail{
        public TechDetail(String techName,String techInfo){
            this.techName = techName;
            this.techInfo = techInfo;
        }
        public String techName="";
        public String techInfo="";
    }
}
