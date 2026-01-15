package com.device.manager.sdk;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.usb.UsbDevice;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.device.manager.sdk.bean.ApplicationDetail;
import com.device.manager.sdk.bean.BarcodeScannerDetail;
import com.device.manager.sdk.bean.BatteryDetail;
import com.device.manager.sdk.bean.BuiltinCellularDetail;
import com.device.manager.sdk.bean.CPUDetail;
import com.device.manager.sdk.bean.CameraDetail;
import com.device.manager.sdk.bean.DeviceDetail;
import com.device.manager.sdk.bean.EmmcEmbeddedMultimediaCardDetail;
import com.device.manager.sdk.bean.MemoryDetail;
import com.device.manager.sdk.bean.NFCDetail;
import com.device.manager.sdk.bean.PrinterDetail;
import com.device.manager.sdk.bean.StorageDetail;
import com.device.manager.sdk.bean.VideoDisplayDetail;
import com.device.manager.sdk.bean.WIFIDetail;
import com.device.manager.sdk.tiramusu.A13DeviceService;
import com.device.manager.sdk.tiramusu.biz.IminLightService;
import com.device.manager.sdk.tiramusu.biz.IminPsamService;
import com.device.manager.server.aidl.IAsyncCallback;
import com.device.manager.server.aidl.IDeviceService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Description:
 * Data: 2024/8/9
 * Created by john.huang
 */
public class DeviceManager {
    private static final String TAG = "DeviceManager";
    private static final String BIND_SERVICE_ACTION = "android.intent.action.RESPOND_AIDL_MESSAGE";
    private IDeviceService iDeviceService;
    private static DeviceManager deviceManager = null;
    public static final String DEVICE_INFO_ALL = "DEVICE_INFO_ALL";
    private Gson mGson = new Gson();
    /**
     * 硬件设备信息
     */
    public static final String DEVICE_INFO_HW_INFO = "DEVICE_INFO_HW_INFO";
    /**
     * 网络信息
     */
    public static final String DEVICE_INFO_NETWORK_INFO = "DEVICE_INFO_NETWORK_INFO";
    /**
     * 软件信息
     */
    public static final String DEVICE_INFO_SW_INFO = "DEVICE_INFO_SW_INFO";
    /**
     * 设备显示信息
     */
    public static final String DEVICE_INFO_DISPLAY_INFO = "DEVICE_INFO_DISPLAY_INFO";
    /**
     * 有关设备内存和存储空间的信息
     */
    public static final String DEVICE_INFO_MEMORY_INFO = "DEVICE_INFO_MEMORY_INFO";
    /**
     * 报告与已安装的应用相关的信息
     */
    public static final String DEVICE_INFO_APPLICATION_REPORT = "DEVICE_INFO_APPLICATION_REPORT";
    /**
     * 设备上与安全性相关的设备设置的相关信息
     */
    public static final String DEVICE_INFO_DEVICE_SETTINGS = "DEVICE_INFO_DEVICE_SETTINGS";
    /**
     * 对用于解锁设备的密码的要求
     */
    public static final String DEVICE_INFO_PASSWORD_REQUIREMENTS = "DEVICE_INFO_PASSWORD_REQUIREMENTS";
    /**
     * 设备的安全状况，由当前设备状态和所应用的政策确定。
     */
    public static final String DEVICE_INFO_SECURITY_POSTURE = "DEVICE_INFO_SECURITY_POSTURE";


    private final ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iDeviceService = IDeviceService.Stub.asInterface(service);
            Log.d(TAG, "onServiceConnected: " + iDeviceService);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ");
            iDeviceService = null;
        }
    };

    private DeviceManager(Context context) {
        this(context, true);
    }

    private DeviceManager(Context context, boolean needDeviceOwner) {
        if(Build.VERSION.SDK_INT <= 33){
            iDeviceService = new A13DeviceService(context);
        }else {
            Intent mServiceIntent = new Intent();
            mServiceIntent.setAction(BIND_SERVICE_ACTION);
            mServiceIntent.putExtra("needDeviceOwner", needDeviceOwner);
            mServiceIntent.setComponent(new ComponentName("com.device.manager.server", "com.device.manager.server.DeviceManagerService"));
            context.bindService(mServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    /**
     * 获取设备管理器Manager
     * @param context
     * @return
     */
    public static DeviceManager getDeviceManager(Context context) {
        if (deviceManager == null) {
            deviceManager = new DeviceManager(context);
        }

        return deviceManager;
    }

    public static DeviceManager getDeviceManager(Context context, boolean needDeviceOwner) {
        if (deviceManager == null) {
            deviceManager = new DeviceManager(context, needDeviceOwner);
        }

        return deviceManager;
    }

    /**
     * 初始化SDK，在Application内初始化
     * @param context
     */
    public static void initialize(Context context) {
        if (deviceManager == null) {
            deviceManager = new DeviceManager(context);
        }
    }

    /**
     * 获取初始化状态，需要初始化后才能调用API
     * @return
     */
    public boolean isInitialized(){
        return deviceManager != null && iDeviceService != null;
    }


    public String getProp(String name) throws RemoteException {
        Log.d(TAG, "getProp");
        if (iDeviceService != null) {
            return iDeviceService.getSystemProperties(name);
        }

        return "";
    }


    public void getDeviceInfoAsyn(String name, IAsyncCallback callback) throws RemoteException {
        Log.d(TAG, "getDeviceInfoAsyn");
        if (iDeviceService != null) {
            iDeviceService.getDeviceInfoAsyn(name, callback);
        }
    }



    public void sendAMCommandAsyn(String cmd, IAsyncCallback callback) throws RemoteException {
        Log.d(TAG, "sendAMCommandAsyn");
        if (iDeviceService != null) {
            iDeviceService.sendAMCommandAsyn(cmd, callback);
        }
    }

    public void sendDeviceActionAsyn(String cmd, String pamars, IAsyncCallback callback) throws RemoteException {
        Log.d(TAG, "sendDeviceActionAsyn");
        if (iDeviceService != null) {
            iDeviceService.sendDeviceActionAsyn(cmd, pamars, callback);
        }
    }

    public List<BarcodeScannerDetail> getBarcodeScannerDetails() throws RemoteException {
        Log.d(TAG, "getBarcodeScannerDetails");
        List<BarcodeScannerDetail> barcodeScannerDetailList = null;
        if (iDeviceService != null) {
            String result = iDeviceService.getBarcodeScannerDetails();
            Type personListType = new TypeToken<List<BarcodeScannerDetail>>() {
            }.getType();
            barcodeScannerDetailList = mGson.fromJson(result, personListType);
        }
        return barcodeScannerDetailList;
    }

    public List<VideoDisplayDetail> getVideoDisplayDetails() throws RemoteException {
        Log.d(TAG, "getVideoDisplayDetails");
        List<VideoDisplayDetail> videoDisplayDetails = null;
        if (iDeviceService != null) {
            String result = iDeviceService.getVideoDisplayDetails();
            Type personListType = new TypeToken<List<VideoDisplayDetail>>() {
            }.getType();
            videoDisplayDetails = mGson.fromJson(result, personListType);
        }
        return videoDisplayDetails;
    }

    public EmmcEmbeddedMultimediaCardDetail getEmmcEmbeddedMultimediaCardDetails() throws RemoteException {
        Log.d(TAG, "getEmmcEmbeddedMultimediaCardDetails");
        EmmcEmbeddedMultimediaCardDetail embeddedMultimediaCardDetail = null;
        if (iDeviceService != null) {
            String result = iDeviceService.getEmmcEmbeddedMultimediaCardDetails();
            embeddedMultimediaCardDetail = mGson.fromJson(result, EmmcEmbeddedMultimediaCardDetail.class);
        }
        return embeddedMultimediaCardDetail;
    }
    public NFCDetail getNFCDetails() throws RemoteException {
        Log.d(TAG, "getEmmcEmbeddedMultimediaCardDetails");
        NFCDetail detail = null;
        if (iDeviceService != null) {
            String result = iDeviceService.getNFCDetails();
            detail = mGson.fromJson(result, NFCDetail.class);
        }
        return detail;
    }

    public BuiltinCellularDetail getBuiltinCellularDetails() throws RemoteException {
        Log.d(TAG, "getEmmcEmbeddedMultimediaCardDetails");
        BuiltinCellularDetail detail = null;
        if (iDeviceService != null) {
            String result = iDeviceService.getBuiltinCellularDetails();
            detail = mGson.fromJson(result, BuiltinCellularDetail.class);
        }
        return detail;
    }
    public  List<CameraDetail>  getCameraDetails() throws RemoteException {
        Log.d(TAG, "getEmmcEmbeddedMultimediaCardDetails");
        List<CameraDetail> detail = null;
        if (iDeviceService != null) {
            String result = iDeviceService.getCameraDetails();
            Type userListType = new TypeToken<List<CameraDetail>>(){}.getType();
            detail = new Gson().fromJson(result, userListType);
        }
        return detail;
    }
    public DeviceDetail getDeviceDetail() throws RemoteException {
        Log.d(TAG, "getEmmcEmbeddedMultimediaCardDetails");
        DeviceDetail detail = null;
        if (iDeviceService != null) {
            String result = iDeviceService.getDeviceDetail();
            detail = mGson.fromJson(result, DeviceDetail.class);
        }
        return detail;
    }
    public MemoryDetail getMemoryDetail() throws RemoteException {
        Log.d(TAG, "getEmmcEmbeddedMultimediaCardDetails");
        MemoryDetail detail = null;
        if (iDeviceService != null) {
            String result = iDeviceService.getMemoryDetail();
            detail = mGson.fromJson(result, MemoryDetail.class);
        }
        return detail;
    }
    public BatteryDetail getBatteryDetail() throws RemoteException {
        Log.d(TAG, "getEmmcEmbeddedMultimediaCardDetails");
        BatteryDetail detail = null;
        if (iDeviceService != null) {
            String result = iDeviceService.getBatteryDetail();
            detail = mGson.fromJson(result, BatteryDetail.class);
        }
        return detail;
    }
    public CPUDetail getCPUDetail() throws RemoteException {
        Log.d(TAG, "getEmmcEmbeddedMultimediaCardDetails");
        CPUDetail detail = null;
        if (iDeviceService != null) {
            String result = iDeviceService.getCPUDetail();
            detail = mGson.fromJson(result, CPUDetail.class);
        }
        return detail;
    }
    public WIFIDetail getWIFIDetails() throws RemoteException {
        Log.d(TAG, "getWIFIDetails");
        WIFIDetail detail = null;
        if (iDeviceService != null) {
            String result = iDeviceService.getWIFIDetails();
            detail = mGson.fromJson(result, WIFIDetail.class);
        }
        return detail;
    }

    public List<PrinterDetail> getPrinterDetails() throws RemoteException {
        Log.d(TAG, "getWIFIDetails");
       List<PrinterDetail>  detail = null;
        if (iDeviceService != null) {
            String result = iDeviceService.getPrinterDetails();
            Type userListType = new TypeToken<List<PrinterDetail>>(){}.getType();
            detail = new Gson().fromJson(result, userListType);
        }
        return detail;
    }
    public StorageDetail getStorageDetails() throws RemoteException {
        Log.d(TAG, "getWIFIDetails");
        StorageDetail detail = null;
        if (iDeviceService != null) {
            String result = iDeviceService.getStorageDetails();
            detail = mGson.fromJson(result, StorageDetail.class);
        }
        return detail;
    }
    public List<ApplicationDetail> getApplicationDetails() throws RemoteException {
        Log.d(TAG, "getWIFIDetails");
        List<ApplicationDetail> detail = null;
        if (iDeviceService != null) {
            String result = iDeviceService.getApplicationDetails();
            Type userListType = new TypeToken<List<ApplicationDetail>>(){}.getType();
            detail = new Gson().fromJson(result, userListType);
        }
        return detail;
    }

    public void getARPList(IAsyncCallback callback) throws RemoteException {
        Log.d(TAG, "getARPList");
        List<ApplicationDetail> detail = null;
        if (iDeviceService != null) {
            String result = iDeviceService.getApplicationDetails();
            Type userListType = new TypeToken<List<ApplicationDetail>>(){}.getType();
            detail = new Gson().fromJson(result, userListType);
        }
    }


    /**
     * 卡片开启/复位
     *
     * @param slot    卡通道号
     *                NORMAL_SLOT－普通PSAM卡通道
     *                FAST_SLOT－高速PSAM卡通道(City卡)
     * @param vccMode 指定卡片供电电压值
     *                VCC_5V_MODE ---5V
     *                VCC_3V_MODE ---3V
     *                VCC_1P8V_MODE ---1.8V
     * @param ATR     卡片复位应答（至少需要32+1bytes的空间），
     *                其内容为长度(1字节)+复位应答内容
     * @return (0)      成功
     * (-1001)	发送错误
     * (-1002)	接收错误
     * (-2403)	通道号错误
     * (-2405)	卡拔出或无卡
     * (-2404)	协议错误
     * (-2500)	IC卡复位的电压模式错误
     * (-2503)	通信失败
     */
    public int openPsam(Context context, byte slot, byte vccMode, byte[] ATR) {
        return IminPsamService.getInstance().openPsam(context, slot, vccMode, ATR);
    }

    /**
     * 卡片关闭
     * @param slot 卡通道号
     *             NORMAL_SLOT－普通PSAM卡通道
     *             FAST_SLOT－高速PSAM卡通道(City卡)
     * @return
     * (0)    执行成功
     * (-1001)	发送错误
     * (-1002)	接收错误
     * 其它	失败
     *
     */
    public int closePsam(Context context, byte slot) {
        return IminPsamService.getInstance().closePsam(context, slot);
    }

    /**
     *
     * @param slot 卡通道号
     *             NORMAL_SLOT－普通PSAM卡通道
     *             FAST_SLOT－高速PSAM卡通道(City卡)
     * @param apduSend 发送给卡片的apdu
     * @param apduRecv 接收到卡片返回的apdu
     * @return
     * (0)    成功
     * (-1001)	发送错误
     * (-1002)	接收错误
     * (-2503)	通信超时
     * (-2405)	交易中卡被拨出
     * (-2401)	奇偶错误
     * (-2403)	选择通道错误
     * (-2400)	发送数据太长（LC）
     * (-2404)	卡片协议错误（不为T＝0或T＝1）
     * (-2406)	没有复位卡片
     */
    public int commandPsam(Context context, byte slot,byte[] apduSend,
                                  byte[] apduRecv) {
        return IminPsamService.getInstance().commandPsam(context, slot, apduSend, apduRecv);
    }

    /**
     *
     * @param slot 卡通道号
     *             NORMAL_SLOT－普通PSAM卡通道
     *             FAST_SLOT－高速PSAM卡通道(City卡)
     * @param apduSend 发送给卡片的apdu
     * @param apduRecv 接收到卡片返回的apdu
     * @return
     * (0)    成功
     * (-1001)	发送错误
     * (-1002)	接收错误
     * (-2503)	通信超时
     * (-2405)	交易中卡被拨出
     * (-2401)	奇偶错误
     * (-2403)	选择通道错误
     * (-2400)	发送数据太长（LC）
     * (-2404)	卡片协议错误（不为T＝0或T＝1）
     * (-2406)	没有复位卡片
     */
    public int commandPsamNew(Context context, byte slot,byte[] apduSend,
                                     byte[] apduRecv) {
        return IminPsamService.getInstance().commandPsamNew(context, slot, apduSend, apduRecv);
    }

    public int iccDevParaSet(Context context, byte slot,byte clkSel, byte mode,
                                    byte pps) {
        return IminPsamService.getInstance().iccDevParaSet(context, slot, clkSel, mode, pps);
    }


    public UsbDevice getLightDevice(Context context){
        return IminLightService.getInstance(context).getLightDevice();
    }

    public boolean connectLightDevice(Context context){
        return IminLightService.getInstance(context).connectUsbDevice();
    }

    public void turnOnGreenLight(Context context){
        IminLightService.getInstance(context).turnOnGreenLight();
    }

    public void turnOnRedLight(Context context){
        IminLightService.getInstance(context).turnOnRedLight();
    }

    public void turnOffLight(Context context){
        IminLightService.getInstance(context).turnOffLight();
    }

    public void disconnectLightDevice(Context context){
        IminLightService.getInstance(context).closeUsbDevice();
    }

    /**
     * 显示隐藏状态栏
     * @param context
     * @param hide true ：隐藏
     */
    public void setHideStatusBar(Context context,boolean hide){
        try {
            Log.d(TAG," setHideStatusBar");
            @SuppressLint("WrongConstant")
            Object iMinServiceManager = context.getSystemService("iminservice");
            Class<?> c = Class.forName("android.app.iMinServiceManager");
            Method method = c.getMethod("setHideStatusBarforiMin", boolean.class);
            method.invoke(iMinServiceManager,hide);

        } catch (NoSuchMethodException | ClassNotFoundException e) {
            Log.d(TAG," setHideStatusBar sendBroadcast");
           // e.printStackTrace();
            String HIDE_STATUS_BAR = "android.intent.action.HIDE_STATUS_BAR";
            Intent intent2 = new Intent(HIDE_STATUS_BAR);
            intent2.putExtra("isHide_statusbar", hide + "");
            intent2.setPackage("com.android.systemui");
            context.sendBroadcast(intent2);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 显示隐藏导航栏
     * @param context
     * @param hide true ：隐藏
     */
    public void setHideNavigationBar(Context context,boolean hide){
        try {
            Log.d(TAG," setHideNavigationBar ");
            @SuppressLint("WrongConstant")
            Object iMinServiceManager = context.getSystemService("iminservice");
            Class<?> c = Class.forName("android.app.iMinServiceManager");
            Method method = c.getMethod("setHideNavigationBarforiMin", boolean.class);
            method.invoke(iMinServiceManager,hide);
        } catch (NoSuchMethodException | ClassNotFoundException e) {
          //  e.printStackTrace();
            Log.d(TAG," setHideNavigationBar sendBroadcast ");
            Intent intent = new Intent("android.intent.action.HIDE_NAV_BAR");
            intent.putExtra("isHide_nav", hide + "");
            intent.setPackage("com.android.systemui");
            context.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }





}
