package com.device.manager.sdk.tiramusu.biz;


import android.content.Context;
import android.hardware.display.DisplayManager;
import android.view.Display;

public class SystemPropManager {

    public static String getModel(){
        return SystemPropBean.getInstance().getModel();
    }
    public static String getPlaform(){
        return SystemPropBean.getInstance().getPlaform();
    }

    public static String getBrand(){
        return SystemPropBean.getInstance().getBrand();
    }
    public static String getSn() {
        return SystemPropBean.getInstance().getSn();
    }

    public static String getCup(){

        return SystemPropBean.getInstance().getCup();
    }
    public static String getCpuMaxFreq(){
        return SystemPropBean.getInstance().getCpuMaxFreq();
    }
    public static String getDualscreenProp(Context context){
        DisplayManager displayManager = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
        Display[] displays = displayManager.getDisplays();
        return String.valueOf(displays.length > 1);
        //return SystemPropBean.getInstance().getDualscreenProp();
    }
    public static long fakeStorageSize(long privateTotalBytes){
        return SystemPropBean.getInstance().fakeStorageSize(privateTotalBytes);
    }
    public static boolean isSupperSecondTouch(){
        return SystemPropBean.getInstance().isSupperSecondTouch();
    }
    public static boolean isSupperSweep(){
        return SystemPropBean.getInstance().isSupperSweep();
    }
    public static boolean isSupperCashBox(){
        return SystemPropBean.getInstance().isSupperCashBox();
    }
    public static double getScreenSize(){
        return SystemPropBean.getInstance().getScreenSize();
    }
    public static boolean isUseImimPrintLibs(){
        return SystemPropBean.getInstance().isUseImimPrintLibs();
    }
    public static String getSystemProperties(String property) {
        return SystemPropBean.getInstance().getSystemProperties(property);
    }

}
