package com.device.manager.server.aidl;

// Declare any non-default types here with import statements
import com.device.manager.server.aidl.IAsyncCallback;

interface IDeviceService {

         String getSystemProperties(String name);
         void getDeviceInfoAsyn(String name, IAsyncCallback callback);

         void sendAMCommandAsyn(String cmd, IAsyncCallback callback);
         void sendDeviceActionAsyn(String cmd,String pamars, IAsyncCallback callback);

         String getBarcodeScannerDetails();
         String getVideoDisplayDetails();
         String getEmmcEmbeddedMultimediaCardDetails();
         String getNFCDetails();
         String getBuiltinCellularDetails();
         String getCameraDetails();
         String getDeviceDetail();
         String getMemoryDetail();
         String getBatteryDetail();

         String getCPUDetail();
         String getWIFIDetails();
         String getApplicationDetails();
         String getPrinterDetails();
         String getStorageDetails();
}
