package com.device.manager.sdk.tiramusu.biz;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;

import static com.device.manager.sdk.tiramusu.biz.SystemPropManager.getSystemProperties;


public class IminDeviceService {
    private volatile static IminDeviceService mInstance = null;
    public static IminDeviceService getInstance() {
        if (mInstance == null){
            synchronized (IminDeviceService.class){
                if (mInstance == null){
                    mInstance = new IminDeviceService();
                }
            }
        }
        return mInstance;
    }

    public  void opencashBox(){
        String tag =  getSystemProperties("persist.sys.device");
        String open = "1";
        OutputStream out = null;
        //RK平台，获取设置的电压
        if(getSystemProperties("ro.system.build.fingerprint").contains("rockchip/rk35")){
            String defValue = getSystemProperties("persist.sys.CASHBOX_ENABLE");
            Log.d("iminLib","获取电压："+defValue);
            if(!TextUtils.isEmpty(defValue)){
                open = defValue;
            }
        }
        String cmd = "echo "+open+" > /sys/class/neostra_gpioctl/dev/gpioctl " + "\n";
        String model = SystemPropBean.getInstance().getModel();
        if(model.equals("D1") || (model.equals("D1-Pro"))
                || (model.equals("Falcon 1"))|| (model.equals("I22T01"))||(model.equals("TF1-11"))
                || SystemPropManager.getPlaform().equalsIgnoreCase("ums512")){
            cmd = "echo "+open+" > /sys/extcon-usb-gpio/cashbox_en " + "\n";
        }else if(tag.contains("D4")
                && tag.contains("Pro")){
            cmd = "echo 1 > "+"/sys/devices/platform/112b1000.usb/cashbox_en" + " \n";
        }/*else if(model.equals("Swan 1") || model.equals("DS1-11")){
            cmd = "echo cash_en:0 > /sys/devices/platform/gpio_ctrl/switch_gpio " + "\n";
        }*/
        try {
            Process exeEcho = Runtime.getRuntime().exec("sh");
            out = exeEcho.getOutputStream();
            out.write(cmd.getBytes());
            out.flush();
            Log.d("iminLib", " " + cmd);
            /*if(model.equals("Swan 1") || model.equals("DS1-11")){
                Thread.sleep(500);
                String cmd2 = "echo cash_en:1 > /sys/devices/platform/gpio_ctrl/switch_gpio " + "\n";
                out.write(cmd2.getBytes());
                out.flush();
            }*/
        } catch (Exception e) {
            //e.printStackTrace();
            Log.d("iminLib", "cmdGpioPwrOn faild :" + e.getMessage());
        }finally {
            if(out != null){
                try {
                   out.close();
                }catch (Exception e){
                    //e.printStackTrace();
                    Log.d("iminLib", "close stream faild :" + e.getMessage());
                }
            }
        }
    }


    private static final String TAG = "IminDeviceService";
    public  void opencashBox(Context context) {
        if(Build.VERSION.SDK_INT < 33){
            opencashBox();
            return;
        }
        try {
            @SuppressLint("WrongConstant")
            Object iMinServiceManager = context.getSystemService("iminservice");
            Class<?> c = Class.forName("android.app.iMinServiceManager");
            Method method = c.getMethod("isCashBoxOpen");
            boolean state = (boolean) method.invoke(iMinServiceManager);
            Log.d(TAG, "iMinServiceManager is " + iMinServiceManager);
            method = c.getMethod("openCashBox");
            method.invoke(iMinServiceManager);
            Log.d(TAG, "openCashBox: ok");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 钱箱是否开启
     * */
    public boolean isCashBoxOpen(Context context) {
        try {
            if(Build.VERSION.SDK_INT < 33){
                return getOldDevCashBoxOpen();
            }
            @SuppressLint("WrongConstant")
            Object iMinServiceManager = context.getSystemService("iminservice");
            Class<?> c = Class.forName("android.app.iMinServiceManager");
            Method method = c.getMethod("isCashBoxOpen");
            boolean state = (boolean) method.invoke(iMinServiceManager);
            Log.d(TAG, "openCashBox: state1=" + state);

            return state;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private boolean getOldDevCashBoxOpen() {
        String tag =   getSystemProperties("persist.sys.device");
        boolean isOpen = false;
        OutputStream out = null;
        String cmd = "cat /sys/class/neostra_gpioctl/dev/gpioctl_sns " + "\n";
        String model = SystemPropBean.getInstance().getModel();
        if(model.equals("D1") || (model.equals("D1-Pro"))
                || (model.equals("Falcon 1"))|| (model.equals("I22T01"))|| (model.equals("TF1-11"))
                || SystemPropManager.getPlaform().equalsIgnoreCase("ums512")){
            cmd = "cat /sys/extcon-usb-gpio/cashbox_state " + "\n";
        }else if(tag.contains("D4")
                && tag.contains("Pro")){
            cmd = "cat /sys/devices/platform/112b1000.usb/cashbox_state" + " \n";
        }/*else if(model.equals("Swan 1") || model.equals("DS1-11")){
            cmd = "echo cash_en:0 > /sys/devices/platform/gpio_ctrl/switch_gpio " + "\n";
        }*/
        try {
            Process exeEcho = Runtime.getRuntime().exec(cmd);
//            out = exeEcho.getOutputStream();
//            out.write(cmd.getBytes());
//            out.flush();
            BufferedReader mReader = new BufferedReader(new InputStreamReader(exeEcho.getInputStream()));
            StringBuffer mRespBuff = new StringBuffer();
            char[] buff = new char[1024];
            int ch = 0;
            while ((ch = mReader.read(buff)) != -1) {
                mRespBuff.append(buff, 0, ch);
            }
            mReader.close();
            Log.i("nioTag2","执行shell2脚本成功 "+mRespBuff.toString());//结果
            if(model.equals("D1") || (model.equals("D1-Pro"))
                    || (model.equals("Falcon 1"))|| (model.equals("I22T01"))|| (model.equals("TF1-11"))
                    || SystemPropManager.getPlaform().equalsIgnoreCase("ums512")){
                isOpen = "true".equals(mRespBuff.toString()) || "1".equals(mRespBuff.toString().trim());
            }else {
                isOpen = "true".equals(mRespBuff.toString()) || "0".equals(mRespBuff.toString().trim());
            }
            Log.d("iminLib", " " + cmd + " isOpen" + isOpen);
            /*if(model.equals("Swan 1") || model.equals("DS1-11")){
                Thread.sleep(500);
                String cmd2 = "echo cash_en:1 > /sys/devices/platform/gpio_ctrl/switch_gpio " + "\n";
                out.write(cmd2.getBytes());
                out.flush();
            }*/
        } catch (Exception e) {
            //e.printStackTrace();
            Log.d("iminLib", "cmdGpioPwrOn faild :" + e.getMessage());
        }finally {
            if(out != null){
                try {
                    out.close();
                }catch (Exception e){
                    //e.printStackTrace();
                    Log.d("iminLib", "close stream faild :" + e.getMessage());
                }
            }
            return isOpen;
        }
    }

    public boolean setCashBoxKeyValue(Context context, String vol) {
        if(Build.VERSION.SDK_INT < 33){
            return setA11CashBoxKeyValue(context,vol);
        }
        try {
            if("1".equals(vol) || "2".equals(vol) || "3".equals(vol)) {
                @SuppressLint("WrongConstant")
                Object iMinServiceManager = context.getSystemService("iminservice");
                Class<?> c = Class.forName("android.app.iMinServiceManager");
                Method method = c.getMethod("setCashBoxKeyValue", String.class);
                method.invoke(iMinServiceManager, vol);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置android11的电压值
     * */
    private boolean setA11CashBoxKeyValue(Context context, String vol) {
        if("1".equals(vol) || "2".equals(vol) || "3".equals(vol)) {
            //兼容历史设备，3默认为12v，所以只能改变顺序，2作为9v,1作为24v,去使用
            switch (vol){
                case "1":
                    vol = "2";
                    break;
                case "2":
                    vol = "3";
                    break;
                case "3":
                    vol = "1";
                    break;
            }
            SystemPropBean.setProperties("persist.sys.CASHBOX_ENABLE", vol);
            writeCashBoxSysFile(vol);
            return true;
        }
        return false;
    }

    /**
     * 写入钱箱电压的节点 针对RK设备
     * */
    private void writeCashBoxSysFile(String vol) {
        String path = "/sys/class/neostra_gpioctl/dev/cashbox_ui_val";
        OutputStream out = null;
        try {
            String cmd =  "echo "+ vol +" > "+ path + "\n";
            Process exeEcho = Runtime.getRuntime().exec("sh");
            out = exeEcho.getOutputStream();
            out.write(cmd.getBytes());
            out.flush();
            Log.d("iminLib", " " + cmd);
        }catch (Exception e){
            Log.d(TAG,e.toString());
        }
    }

    /**
     * @description: app default allow to use usb
     * @param pkgName: the name of app package to be authorized
     * @param context
     */
    public  void setAppsHaveUsbPermissions(Context context, String pkgName) {
        try {
            @SuppressLint("WrongConstant")
            Object iMinServiceManager = context.getSystemService("iminservice");
            Class<?> c = Class.forName("android.app.iMinServiceManager");
            Method method = c.getMethod("setAppsHaveUsbPermissions", String.class);

            method.invoke(iMinServiceManager, pkgName);
            Log.d(TAG, "setAppsHaveUsbPermissions: " + pkgName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean setWifiEnable(Context context, boolean isOpen) {
        if (Build.VERSION.SDK_INT >= 33) {
            try {
                Object iMinServiceManager = context.getSystemService("iminservice");
                Class<?> c = Class.forName("android.app.iMinServiceManager");
                Method method = c.getMethod("setWifiEnabled",  boolean.class);
                method.invoke(iMinServiceManager, isOpen);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            try {
                try {
                    ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
                    Class<?> c = Class.forName("android.app.ActivityManager");
                    Method method = c.getMethod("setBtWifi", int.class, int.class);
                    method.invoke(am, 1, isOpen ? 1 : 0);
                    Log.d(TAG, "setBtWifi : " + 1  + ", " + (isOpen ? 1 : 0));
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public boolean setBluetoothEnable(Context context, boolean isOpen) {
        if (Build.VERSION.SDK_INT >= 33) {
            try {
                Object iMinServiceManager = context.getSystemService("iminservice");
                Class<?> c = Class.forName("android.app.iMinServiceManager");
                Method method = c.getMethod("setBluetoothEnabled",  boolean.class);
                method.invoke(iMinServiceManager, isOpen);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            try {
                try {
                    ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
                    Class<?> c = Class.forName("android.app.ActivityManager");
                    Method method = c.getMethod("setBtWifi", int.class, int.class);
                    method.invoke(am, 0, isOpen ? 1 : 0);
                    Log.d(TAG, "setBtWifi : " + 1  + ", " + (isOpen ? 1 : 0));
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}
