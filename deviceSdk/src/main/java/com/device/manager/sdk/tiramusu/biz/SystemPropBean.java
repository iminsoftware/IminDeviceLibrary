package com.device.manager.sdk.tiramusu.biz;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.ArrayList;

import static com.device.manager.sdk.tiramusu.biz.IminDeviceUtils.PRODUCT_NEO_MODEL;

public class SystemPropBean {
    private static String TAG = "SystemPropUtils";
    private  String model=null;
    private double mInch = 0;
    private boolean isUseImimPrintLibs = false;
    private volatile static SystemPropBean mInstance = null;
    private ArrayList<String> notSuuperTouch = new ArrayList<>();
    private boolean isSupperSecondTouch = false;
    private SystemPropBean(){
        model = getModel();
    }
    public static SystemPropBean getInstance(){
        if (mInstance == null){
            synchronized (SystemPropBean.class){
                if (mInstance == null){
                    mInstance = new SystemPropBean();
                }
            }
        }
        return mInstance;
    }


    public  String getSn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if("".equals(getSystemProperties("ro.serialno"))){
                return getSystemProperties("persist.sys.imin.sn");
            }
            return getSystemProperties("ro.serialno");
        }
        return Build.SERIAL;
    }

    public  String getModel(){
        if (model != null) {
            return model;
        }
        /*if (Build.VERSION.SDK_INT >= 30) {
             return Build.MODEL;
        }*/
        if (Build.VERSION.SDK_INT >= 33) {
            String model = getSystemProperties("persist.sys.model");
            if(TextUtils.isEmpty(model)) {
                model = Build.MODEL;
            }
            return model;
        }
        model = "";
        isSupperSecondTouch = false;
        String plaform =getPlaform();

        if (!TextUtils.isEmpty(plaform) && plaform.startsWith("mt")){
            model= getSystemProperties("ro.neostra.imin_model");
        }else if (!TextUtils.isEmpty(plaform) && plaform.startsWith("ums512")){
            model = Build.MODEL;
        } else if (!TextUtils.isEmpty(plaform) && plaform.startsWith("sp9863a")){
            model = Build.MODEL;
            if(model.equals("I22M01")){
                model = "MS1-11";
            }
        }else {
             model = getSystemProperties("sys.neostra_oem_id");
             Log.d(TAG,"model "+model);
            if (!TextUtils.isEmpty(model) && model.length() > 4) {
                model = filterModel(model.substring(0, 5));
                String oemId = getSystemProperties("sys.neostra_oem_id");
                if(oemId.length() > 27 && oemId.startsWith("W26MP")){
                    String num28 = String.valueOf(oemId.charAt(27));
                    if("S".equalsIgnoreCase(num28)){
                        model = "D3-510";
                    }
                }
            } else {
                model = getSystemProperties("ro.neostra.imin_model");
            }
            if("".equals(model)){
                model = Build.MODEL;
                if(model.equals("I22D01")){
                    model = "DS1-11";
                }
            }

        }
        return model;
    }

    public  String getBrand(){
        String brand = Build.BRAND;
        if(Build.VERSION.SDK_INT >= 33){
            brand =  getSystemProperties("persist.sys.romtype");
            String roBrand = Build.BRAND;
            if(!TextUtils.isEmpty(roBrand) && !roBrand.equalsIgnoreCase("imin")){
                brand = roBrand;
            }
        }
        if(brand.equalsIgnoreCase("yimin")){
            return "YiMin";
        }
        if (!TextUtils.isEmpty(model)){
            if(isIminDevie()){
                return "iMin";
            }
        }
        return brand;
    }

    private boolean isIminDevie(){
        if (!TextUtils.isEmpty(model)){
            if(model.contains("D3-504")||model.contains("D3-505")||model.contains("D3-506")
                    || model.contains("D1-Pro")|| model.equals("D1")|| model.contains("D1-503")
                    || model.contains("D1-501")|| model.contains("D1p-602")|| model.contains("D1p-603")
                    || model.contains("D2-401")|| model.contains("D2-402")|| model.contains("D2 Pro")
                    || model.contains("D1w-703")|| model.contains("D1w-702")|| model.contains("D1w-701")
                    || model.contains("D4-501")|| model.contains("D4-502")|| model.contains("D4-503")
                    || model.contains("D4-504")|| model.contains("D4-505")|| model.contains("K2-201")
                    || model.contains("K1-101")|| model.contains("R1-202")|| model.contains("R1-201")
                    || model.contains("S1-701")|| model.contains("S1-702")|| model.contains("M2-Max")
                    || model.contains("M2-Pro")|| model.contains("M2-203")|| model.contains("M2-202")
                    || model.contains("TF1-11")|| model.contains("IF22-1")|| model.contains("Swift 1")
                    || model.contains("Swan 1")|| model.contains("Lark 1")|| model.contains("DS1-11")
                    || model.contains("Falcon 1")) {
                return true;
            }
        }
        return false;
    }

    public  String getCup(){
        if ("D4-505".equals(model)){
            String id = getSystemProperties("sys.neostra_oem_id");
            if (!TextUtils.isEmpty(id) && id.startsWith("W27DP")){
                return "Rockchip 6 Core\n" +
                        "Dual-core Cortex-A72\n" +
                        "Quad-core Cortex-A53";
            }
        }
        switch (model){
            case "D3-504":
            case "D3-505":
            case "D3-506":
            case "K2-201":
            case "D4-501":
            case "D4-502":
            case "D4-503":
            case "D4-504":
            case "D4-505"://Rockchip,6
            case "K1-101":
            case "S1-701":
            case "S1-702":
                if("W17PX".equals(getModel())){
                    return "Quad-Core ARM Cortex-A55";
                }
                return "Quad-core ARM Cortex-A17";
            case "R2-301":
                return "Rockchip 6 core \n" +
                        "Dual-Core Cortex-A72 UP to 1.8GHz\n" +
                        "Qual-Core Cortex-A53 UP to 1.4GHz";
            case "D2-402":
            case "D2-401":
            case "D2 Pro":
                String id = getSystemProperties("sys.neostra_oem_id");
                if(!TextUtils.isEmpty(id) ){
                    if(id.startsWith("V1GPX") || id.startsWith("V1PXX")){
                        return "Quad-core Cortex-A55";
                    }
                }
                return "Quad-core ARM Cortex-A53";
            case "M2-Max":
            case "M2-Pro":
            case "D1":
            case "D1-Pro":
                return "8-core \n" +
                        "Dual*A75 UP to 1.8GHz\n" +
                        "Hexa*A55 UP to 1.8GHz";
            case "MS1-11":
            case "Swift 1":
                return "8-core ARM Cortex-A55";
            case "DS1-11":
            case "Swan 1":
                return "Quad-core ARM Cortex-A55";
            case "FI22-1":
            case "IF22-1":
            case "Lark 1":
                return "8-Core Arm Cortex-A55";
            case "M2-202":
                return "4-Core, Quad*Cortex-A35, 1.3GHz";
            case "M2-203":
                return "Quad-core ARM Cortex-A53 1.28GHz";
            default:
                break;
        }
        return "";
    }
    public  String getCpuMaxFreq(){
        if ("D4-505".equals(model)){
            String id = getSystemProperties("sys.neostra_oem_id");
            if (!TextUtils.isEmpty(id) && id.startsWith("W27DP")){
                 return "1.8GHz";
            }
        }
        switch (model){
            case "R2-301":
                return "0";//display in cup
            case "D2-402":
            case "D2-401":
            case "D2 Pro":
                String id = getSystemProperties("sys.neostra_oem_id");
                if(!TextUtils.isEmpty(id) ){
                    if(id.startsWith("V1GPX") || id.startsWith("V1PXX")){
                        return "1.8GHz";
                    }
                }
                return "1.5GHz";

            case "MS1-11":
            case "Swift 1":
            case "FI22-1":
            case "IF22-1":
            case "Lark 1":
                return "1.6GHz";
            case "DS1-11":
            case "Swan 1":
                return "2.0GHz";
            case "S1-701":
                return "2.0GHZ";
            default:
                break;
        }
        return "";
    }
    public String getDualscreenProp(){
        return getSystemProperties("sys.dualscreen");
    }


    public  boolean isSupperSecondTouch(){
        return isSupperSecondTouch;
    }

    public boolean isSupperSweep() {
        switch (model){
            case "R2-301":
            case "FI22-1":
            case "IF22-1":
            case "Lark 1":
                return false;
            default:
                return true;
        }
    }
    public boolean isSupperCashBox(){
        String pModel = model;
        //Android13判断钱箱
        if(Build.VERSION.SDK_INT >= 33){
            pModel =  getSystemProperties(PRODUCT_NEO_MODEL);
            return IminDeviceUtils.isSupportCashBox(pModel);
        }
        switch (pModel){
            case "R2-301":
            case "M2-Max":
            case "M2-Pro":
            case "MS1-11":
            case "Swift 1":
            case "FI22-1":
            case "IF22-1":
            case "Lark 1":
            case "S1-701":
                return false;
            default:
                return true;
        }
    }
    public  long fakeStorageSize(long privateTotalBytes) {
        long flashSize = privateTotalBytes/1024/1024/1024;
        if(flashSize<=0)
            flashSize=0;
        else if(flashSize<=1)
            flashSize=1;
        else if(flashSize<=2)
            flashSize=2;
        else if(flashSize<=4)
            flashSize=4;
        else if(flashSize<=8)
            flashSize=8;
        else if(flashSize<=16)
            flashSize=16;
        else if(flashSize<=32)
            flashSize=32;
        else if(flashSize<=64)
            flashSize=64;
        else if(flashSize<=128)
            flashSize=128;
        else if(flashSize<=256)
            flashSize=256;
        privateTotalBytes = flashSize*1024*1024*1024;

        return privateTotalBytes;
    }
    /*
    public static String getOEM(){
        return getSystemProperties("ro.fota.oem");
    }

    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String getIminVersion(){
        return getSystemProperties("ro.neostra.imin_version]");
    }
    public static String dd(){
        return getSystemProperties("ro.fota.version");
    }*/
    public  String getPlaform(){
        if (Build.VERSION.SDK_INT >= 33) {
            return Build.HARDWARE;
        }
       return getSystemProperties("ro.board.platform");
    }
    public  String getSystemProperties(String property) {
        String value ="";
        try {
            Class clazz = Class.forName("android.os.SystemProperties");
            Method getter = clazz.getDeclaredMethod("get", String.class);
             value = (String) getter.invoke(null, property);
        } catch (Exception e) {
            Log.d(TAG, "Unable to read system properties");
        }
        return value;
    }
    public double getScreenSize(){
        return mInch;
    }
    public boolean isUseImimPrintLibs(){
        return isUseImimPrintLibs;
    }

    private  String filterModel(String str) {
        switch (str) {
            case "W21XX":
                return "D1-501";
            case "W21MX":
                return "D1-502";
            case "W21DX":
                return "D1-503";
            case "W22XX":
                return "D1p-601";
            case "W22MX":
                return "D1p-602";
            case "W22DX":
                return "D1p-603";
            case "W22DC":
                return "D1p-604";
            case "W23XX":
                return "D1w-701";
            case "W23MX":
                return "D1w-702";
            case "W23DX":
                return "D1w-703";
            case "W23DC":
                return "D1w-704";
            case "V1GXX":
            case "V1GPX":
                return "D2-401";
            case "V1XXX":
            case "V1PXX":
                return "D2-402";
            case "V2BXX":
                return "D2 Pro";
            case "1824P":
                if(getSystemProperties("persist.sys.customername").equals("ZKSY")){
                    return "ZKSY-301";
                }else if(getSystemProperties("persist.sys.customername").equals("K3")){
                    return "K3";
                }
                return "D3-501";//yimin
			case "P24MP":
                String customerName = getSystemProperties("persist.sys.customername");
                if(customerName.equals("2Dfire")){
                    return "P10M";
                }else if(customerName.equals("ZKSY")){
                    return "ZKSY-302";
                }else if(customerName.equalsIgnoreCase("Bestway")){
                    return "V5-1824M Plus";
                }else if(customerName.equalsIgnoreCase("idiotehs")){
                    return "CTA-D3M";
                }else {
                    return "D3-503";//yimin
                }
			case "P24XP":
                return "D3-502";
            case "W26XX":
            case "W26PX":
                return "D3-504";
            case "W26MX":
            case "W26MP":
                return "D3-505";
            case "W27LX":
                return "D4-501";
            case "W27LD":
                return "D4-502";
            case "W27XX":
            case "W27PX":
                return "D4-503";
            case "W27MX":
            case "W27MP":
                return "D4-504";
            case "W27DX":
                return "D4-505";
            case "1824M":
                return "1824M";
            case "1824D":
                return "1824D";
            case "K21XX":
                return "K1-101";
            case "D20XX":
                return "R1-201";
            case "D20TX":
                return "R1-202";
            case "W17BX":
                mInch = 21.5;
                isUseImimPrintLibs = true;
                return "S1-702";
            case "W17XX":
            case "W17PX"://rk3566,android11
                mInch = 21.5;
                isUseImimPrintLibs = true;
                return "S1-701";
            case "W26HX":
                return "D3-504";
            case "W26HM":
                return "D3-505";
            case "W26HD":
                return "D3-506";
            case "W26HG":
            case "W26GP":
                return "K2-201";
            case "D224G":
                return "R2-301";//D224GM04SXXT3PXW3E1MXV110CDXXX
            case "D22XX":
                return "R2-301";// error ?
            case "D22TX":
                return "R2-302";
            case "W27DP":
                return "D4-505";
            case "K21PX":
                return "K1-101";
            case "W23PX":
                return "D1w-701";
            case "W23MP":
                return "D1w-702";
            case "W23DP":
                return "D1w-703";
            case "W28XX":
                return "Swan 1";//yimin device name
            case "W28MX":
                customerName = getSystemProperties("persist.sys.customername");
                if(customerName.equals("2Dfire")) {
                    return "P5";
                }else if("Dingjian".equals(customerName)){
                    return "DJ-P28";
                }else if("baohuoli".equalsIgnoreCase(customerName)){
                    return "FS-5216";
                }else {
                    return "Swan 1";//yimin device name
                }
            case "W28GX":
                String w28gxCustomerName = getSystemProperties("persist.sys.customername");
                if(w28gxCustomerName.equals("2Dfire")){
                    return "P5K";
                }else if("Dingjian".equals(w28gxCustomerName)){
                    return "DJ-P28K";
                }else if("baohuoli".equalsIgnoreCase(w28gxCustomerName)){
                    return "FS-5216";
                }else {
                    return "Swan 1k";//yimin device name
                }
            case "W26DP":
                return "D3-506";
            case "26PXX":
                return "P10CS";//yimin device name
            case "26MPX":
                return "P10DS";//yimin device name
            default:
                break;
        }
        return "";
    }


    public static void setProperties(String key, String defvalue) {
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method set = c.getMethod("set", String.class, String.class);
            set.invoke(c, key, defvalue);
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e);
            e.printStackTrace();
        }
    }
}
