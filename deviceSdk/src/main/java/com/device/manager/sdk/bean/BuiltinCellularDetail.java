package com.device.manager.sdk.bean;

import java.util.List;

public class BuiltinCellularDetail {
    public String type="";
    public String firmwareVersion="";
    public String networkOperatorName="";
    public String deviceId="";
    public String countryIso="";
    public List<CellInfo> cellularDetails;

    public static class CellInfo{
        public String signalStrength="";
        public boolean isRegistered;
    }
}
