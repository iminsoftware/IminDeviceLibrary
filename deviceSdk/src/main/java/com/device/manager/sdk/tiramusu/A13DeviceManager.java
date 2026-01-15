package com.device.manager.sdk.tiramusu;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.device.manager.sdk.bean.CPUDetail;
import com.device.manager.sdk.tiramusu.biz.IminSDKManager;
import com.device.manager.sdk.tiramusu.biz.SystemPropManager;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Map;


/**
 * @author ：Chenjk
 * @version 1.0
 * @CreateTime ：2025/9/12 10:45
 **/
public class A13DeviceManager {
    private Context context;

    public A13DeviceManager(Context context) {
        this.context = context;
    }

    public String sendAMCommand(JsonElement cmd) {
        String result = "";
        try {
            if (cmd.isJsonObject()) {

                JsonObject jsonRoot = cmd.getAsJsonObject();
                if (cmd.isJsonObject() && jsonRoot.has(CMD.CONFIG_OEM_CONFIG)) {
                    JsonObject jsonCmd = jsonRoot.getAsJsonObject(CMD.CONFIG_OEM_CONFIG);
                    if (jsonCmd.has(CMD.CONFIG_WIFI_SWITCH)) {
                        boolean asBoolean = jsonCmd.get(CMD.CONFIG_WIFI_SWITCH).getAsBoolean();
                        IminSDKManager.setWifiEnable(context, asBoolean);
                    } if(jsonCmd.has(CMD.CONFIG_BLUETOOTH_SWITCH)){
                        boolean asBoolean = jsonCmd.get(CMD.CONFIG_BLUETOOTH_SWITCH).getAsBoolean();
                        IminSDKManager.setBluetoothEnable(context, asBoolean);
                    }
                    //usb
                    if(jsonCmd.has(CMD.CONFIG_ENABLE_USB_PERMISSION)){
                        String packageName = jsonCmd.get(CMD.CONFIG_ENABLE_USB_PERMISSION).getAsString();
                        IminSDKManager.setAppsHaveUsbPermissions(context,packageName);
                    }

                }

                if (cmd.isJsonObject() && jsonRoot.has(CMD.CONFIG_PERIPHERAL_CONFIG)) {
                    JsonObject jsonCmd = jsonRoot.getAsJsonObject(CMD.CONFIG_PERIPHERAL_CONFIG);

                    if (jsonCmd.has(CMD.CONFIG_IS_CASHBOX_OPEN)) {
                        result = String.valueOf(IminSDKManager.isCashBoxOpen(context));
                    }else if (jsonCmd.has(CMD.CONFIG_OPENCASHBOX)) {
                        IminSDKManager.opencashBox(context);
                    }else if (jsonCmd.has(CMD.CONFIG_SET_CASHBOX_KEYVALUE)) {
                        result = String.valueOf(IminSDKManager.setCashBoxKeyValue(context,jsonCmd.get(CMD.CONFIG_SET_CASHBOX_KEYVALUE).getAsString()));
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        //return null;
        return result;
    }

    /**
     * 通过name 获取对应的值
     *
     */
    public String getDeviceInfo(String name){
        switch (name){
            case CMD.GET_MODEL:
                return SystemPropManager.getModel();
            case CMD.GET_PLATFORM:
                return SystemPropManager.getPlaform();
            case CMD.GET_BRAND:
                return SystemPropManager.getBrand();
            case CMD.GET_SN:
                return SystemPropManager.getSn();
            case CMD.DEVICE_INFO_CPU_INFO:
                return getCPUInfo();

            case CMD.GET_DUALSCREEN_PROP:
                return SystemPropManager.getDualscreenProp(context);

            case CMD.IS_SUPPER_CASHBOX:
                return String.valueOf(SystemPropManager.isSupperCashBox());

            case CMD.DEVICE_INFO_HW_INFO:
                return getHWInfo();

        }

        return "";
    }

    private String getCPUInfo() {
        Gson gson = new Gson();
        CPUDetail eDetail = new CPUDetail();
        try {

            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            activityManager.getMemoryInfo(memoryInfo);
            eDetail.manufacturer = Build.MANUFACTURER;
            Map<String, String> cpus = CpuUtils.getCpuInfo();
            eDetail.model = CpuUtils.getmode(0,context);
            eDetail.totalCPU = CpuUtils.getCpuMaxFreq();
            eDetail.usedCPU = CpuUtils.getCPURateDesc_All();
            eDetail.revision = cpus.get("revision");
            eDetail.cores = Runtime.getRuntime().availableProcessors() + "";

            eDetail.cpu = CpuUtils.getCpuClusters(context);
            /*double numberTemperature = (double) CpuUtils.getCpuTemperature();
            BigDecimal bd = new BigDecimal(numberTemperature).setScale(1, RoundingMode.HALF_UP);
            eDetail.cpuTemperatureAlert = bd.doubleValue();*/

        } catch (Exception e) {
            e.printStackTrace();
        }

        return gson.toJson(eDetail);
    }


    private String getHWInfo() {
        JsonObject result = new JsonObject();
        result.addProperty("model", SystemPropManager.getModel());
        result.addProperty("brand", SystemPropManager.getBrand());
        result.addProperty("serialNumber", SystemPropManager.getSn());
        result.addProperty("hardware", SystemPropManager.getPlaform());
        result.addProperty("dualscreenProp", SystemPropManager.getDualscreenProp(context));
        return result.toString();
    }


    public String sendDeviceActionAsyn(String type, String cmd) {
        String result = "";

        if (CMD.CONFIG_IS_CASHBOX_OPEN.equals(type)) {
            result = String.valueOf(IminSDKManager.isCashBoxOpen(context));
        }else if (CMD.CONFIG_CASHBOX_EN.equals(type)) {
            if(!TextUtils.isEmpty(cmd)){
                result = String.valueOf(IminSDKManager.setCashBoxKeyValue(context,cmd));
            }

        }
        return result;
    }
}
