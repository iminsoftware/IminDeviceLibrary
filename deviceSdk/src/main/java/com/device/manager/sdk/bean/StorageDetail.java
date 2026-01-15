package com.device.manager.sdk.bean;

import java.util.List;

public class StorageDetail {
    public String id="";
    public String availableStorage="";
    public String totalStorage="";
    public String usedStorage="";
    public List<DiskInfo> disks;
    public List<StorageVolume> volumes;
    public static class DiskInfo{
        public String size="";
        public String label;
        public String sysPath;
    }
    public static class StorageVolume{
        public String type="";
        public String diskId="";
        public String state="";
        public String fsType="";
        public String fsUuid="";
        public String path="";
    }
}
