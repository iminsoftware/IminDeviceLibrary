package com.device.manager.sdk.tiramusu;

/**
 * @author ：Chenjk
 * @version 1.0
 * @createTime ：2025/9/12 12:15
 **/
public class CMD {
    public static String CONFIG_OEM_CONFIG = "oemConfig";

    /**
     * 设置wifi开关
     */
    public static String CONFIG_WIFI_SWITCH = "setWifiSwitch";

    /**
     * 设置蓝牙开关
     */
    public static String CONFIG_BLUETOOTH_SWITCH = "setBluetoothSwitch";


    public static final String CONFIG_ENABLE_USB_PERMISSION = "enableUSBPermission";


    public static final String CONFIG_PERIPHERAL_CONFIG = "peripheralConfig";
    public static final String CONFIG_IS_CASHBOX_OPEN = "isCashBoxOpen";
    public static final String CONFIG_OPENCASHBOX = "openCashBox";
    public static final String CONFIG_SET_CASHBOX_KEYVALUE = "setCashBoxKeyValue";
    public static final String CONFIG_CASHBOX_EN = "cashbox_en";


    /**
     * 获取设备型号
     */
    public static final String GET_MODEL = "DEVICE_INFO_GET_MODEL";
    /**
     * 硬件相关信息
     */
    public static final String DEVICE_INFO_HW_INFO = "DEVICE_INFO_HW_INFO";
    /**
     * cpu信息
     */
    public static final String DEVICE_INFO_CPU_INFO = "DEVICE_INFO_CPU_INFO";


    /**
     * 设备的硬件平台/芯片组型号
     */
    public static final String GET_PLATFORM = "DEVICE_INFO_GET_PLATFORM";


    /**
     * 获取设备品牌
     */
    public static final String GET_BRAND = "DEVICE_INFO_GET_BRAND";


    /**
     * 获取设备SN
     */
    public static final String GET_SN = "DEVICE_INFO_GET_SN";


    /**
     * 设备是否为双屏
     */
    public static final String GET_DUALSCREEN_PROP = "DEVICE_INFO_GET_DUALSCREEN";



    public static final String IS_SUPPER_CASHBOX = "DEVICE_INFO_ISSUPPERCASHBOX";




}
