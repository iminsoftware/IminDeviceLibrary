package com.device.manager.sdk.tiramusu.biz;

import android.content.Context;
import android.hardware.usb.UsbDevice;



public class IminSDKManager {

    public static int openPsam(Context context, byte slot, byte vccMode, byte[] ATR) {
        return IminPsamService.getInstance().openPsam(context, slot, vccMode, ATR);
    }

    public static int closePsam(Context context, byte slot) {
        return IminPsamService.getInstance().closePsam(context, slot);
    }

    public static int commandPsam(Context context, byte slot,byte[] apduSend,
                           byte[] apduRecv) {
        return IminPsamService.getInstance().commandPsam(context, slot, apduSend, apduRecv);
    }

    public static int commandPsamNew(Context context, byte slot,byte[] apduSend,
                                  byte[] apduRecv) {
        return IminPsamService.getInstance().commandPsamNew(context, slot, apduSend, apduRecv);
    }

    public static int iccDevParaSet(Context context, byte slot,byte clkSel, byte mode,
                                    byte pps) {
        return IminPsamService.getInstance().iccDevParaSet(context, slot, clkSel, mode, pps);
    }

    public static void opencashBox(){
        IminDeviceService.getInstance().opencashBox();
    }

    public static void opencashBox(Context context){
        IminDeviceService.getInstance().opencashBox(context);
    }

    public static boolean setCashBoxKeyValue(Context context,String vol){
        return IminDeviceService.getInstance().setCashBoxKeyValue(context,vol);
    }

    public static boolean isCashBoxOpen(Context context){
        return IminDeviceService.getInstance().isCashBoxOpen(context);
    }

    public static UsbDevice getLightDevice(Context context){
        return IminLightService.getInstance(context).getLightDevice();
    }

    public static boolean connectLightDevice(Context context){
        return IminLightService.getInstance(context).connectUsbDevice();
    }

    public static void turnOnGreenLight(Context context){
        IminLightService.getInstance(context).turnOnGreenLight();
    }

    public static void turnOnRedLight(Context context){
        IminLightService.getInstance(context).turnOnRedLight();
    }

    public static void turnOffLight(Context context){
        IminLightService.getInstance(context).turnOffLight();
    }

    public static void disconnectLightDevice(Context context){
        IminLightService.getInstance(context).closeUsbDevice();
    }

    public static void setAppsHaveUsbPermissions(Context context, String pkgName) {
        IminDeviceService.getInstance().setAppsHaveUsbPermissions(context, pkgName);
    }

    public static boolean setWifiEnable(Context context,boolean isOpen){
        return IminDeviceService.getInstance().setWifiEnable(context,isOpen);
    }

    public static boolean setBluetoothEnable(Context context, boolean isOpen) {
        return IminDeviceService.getInstance().setBluetoothEnable(context,isOpen);
    }
}
