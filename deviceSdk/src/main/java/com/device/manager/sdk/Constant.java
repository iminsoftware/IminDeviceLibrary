package com.device.manager.sdk;

/**
 * create at 2023/8/3
 * description: 指令
 */
public class Constant {
    //    DEVICE_INFO_ALL 所有信息
    public static final String DEVICE_INFO_ALL = "DEVICE_INFO_ALL";
    //    DEVICE_INFO_HW_INFO 硬件设备信息
    public static final String DEVICE_INFO_HW_INFO = "DEVICE_INFO_HW_INFO";
    //    DEVICE_INFO_NETWORK_INFO 网络信息
    public static final String DEVICE_INFO_NETWORK_INFO = "DEVICE_INFO_NETWORK_INFO";
    //    DEVICE_INFO_SW_INFO 软件信息
    public static final String DEVICE_INFO_SW_INFO = "DEVICE_INFO_SW_INFO";
    //    DEVICE_INFO_DISPLAY_INFO 设备显示信息
    public static final String DEVICE_INFO_DISPLAY_INFO = "DEVICE_INFO_DISPLAY_INFO";
    //    DEVICE_INFO_MEMORY_INFO 有关设备内存和存储空间的信息
    public static final String DEVICE_INFO_MEMORY_INFO = "DEVICE_INFO_MEMORY_INFO";
    //    DEVICE_INFO_APPLICATION_REPORT 报告与已安装的应用相关的信息
    public static final String DEVICE_INFO_APPLICATION_REPORT = "DEVICE_INFO_APPLICATION_REPORT";
    //    DEVICE_INFO_DEVICE_SETTINGS 设备上与安全性相关的设备设置的相关信息
    public static final String DEVICE_INFO_DEVICE_SETTINGS = "DEVICE_INFO_DEVICE_SETTINGS";
    //    DEVICE_INFO_PASSWORD_REQUIREMENTS 对用于解锁设备的密码的要求
    public static final String DEVICE_INFO_PASSWORD_REQUIREMENTS = "DEVICE_INFO_PASSWORD_REQUIREMENTS";
    //    DEVICE_INFO_SECURITY_POSTURE 设备的安全状况，由当前设备状态和所应用的政策确定。
    public static final String DEVICE_INFO_SECURITY_POSTURE = "DEVICE_INFO_SECURITY_POSTURE";

    //打开钱箱
    public static final String Operation_Cashbox_Open = "Operation_Cashbox_Open";
    //设置开机默认启动应用
    public static final String Operation_Set_AutoStart = "Operation_Set_AutoStart";
    //取消开机默认启动应用
    public static final String Operation_Set_AutoStart_Cance = "Operation_Set_AutoStart_Cance";
    //设置霸屏应用
    public static final String Operation_Set_Kiosk = "Operation_Set_Kiosk";
    //取消霸屏应用
    public static final String Operation_Set_Kiosk_Cance = "Operation_Set_Kiosk_Cance";
    //设置防卸载应用
    public static final String Operation_Set_UnInstall = "Operation_Set_UnInstall";
    //取消防卸载应用
    public static final String Operation_Set_UnInstall_Cance = "Operation_Set_UnInstall_Cance";
    //设置霸屏应用密码
    public static final String Operation_Set_Kiosk_Pwd = "Operation_Set_Kiosk_Pwd";
    //设置免ubs动态权限
    public static final String Operation_Allow_Usb_Permissions = "Operation_Allow_Usb_Permissions";
    //取消免usb动态权限
    public static final String Operation_Allow_Usb_Permissions_Cance = "Operation_Allow_Usb_Permissions_Cance";
}
