package com.example.imindevicelibrary.bean;

import java.util.Set;

public class DeviceConnectivityManagement {
    public String usbDataAccess;
    public boolean configureWifi;
    public Boolean wifiDirectDisallow;
    public String tetheringSettings;
    public WifiSsidPolicy wifiSsidPolicy;
    public static class WifiSsidPolicy{
        public String wifiSsidPolicyType;
        public Set<String> wifiSsids;
    }
}

