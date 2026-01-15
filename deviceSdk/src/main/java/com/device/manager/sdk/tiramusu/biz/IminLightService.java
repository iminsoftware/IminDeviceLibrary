package com.device.manager.sdk.tiramusu.biz;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.util.Log;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ：Chenjk
 * @version 1.0
 * @createTime ：2023/11/23 20:38
 **/
public class IminLightService {
    private String TAG = "IminLightService";
    public static int vId = 25747;
    public static int pId = 42912;
    private static UsbDevice mUsbDevice;
    private UsbInterface mUsbInterface;
    private UsbManager mUsbManager;
    private static IminLightService iminLightService;
    private UsbEndpoint mUsbEndPointIn;
    private UsbEndpoint mUsbEndPointOut;
    private UsbDeviceConnection mUsbDeviceConnection;
    private ThreadPoolExecutor executor; //线程池
    private Context context;

    private IminLightService(Context context) {
        mUsbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        this.context = context;
    }

    public static IminLightService getInstance(Context context) {
        synchronized (UsbDevice.class) {
            if (iminLightService == null)
                iminLightService = new IminLightService(context);
        }
        return iminLightService;
    }

    public UsbDevice getLightDevice() {

        Log.d(TAG, "Target device pid = " + pId + " vid = " + vId);
        HashMap<String, UsbDevice> deviceList = mUsbManager.getDeviceList();
        if (deviceList.isEmpty()) {
            Log.d(TAG, "openUsbDevice Cannot find any usb device");
            //mResponseResult.notFindDevice();
            return null;
        } else {
            Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
            int iIndex = 0;
            boolean isConnect = false;
            while (deviceIterator.hasNext()) {
                UsbDevice device = deviceIterator.next();
                iIndex++;
                String strValue = "UsbDevice " + iIndex + " DeviceClass:" + device.getDeviceClass() + "; DeviceId:" + device.getDeviceId() + "; DeviceName:" + device.getDeviceName() + "; VendorId:" + device.getVendorId() +
                        "; \r\nProductId:" + device.getProductId() + "; InterfaceCount:" + device.getInterfaceCount() + "; describeContents:" + device.describeContents() + ";\r\n" +
                        "DeviceProtocol:" + device.getDeviceProtocol() + ";DeviceSubclass:" + device.getDeviceSubclass() + ";\r\n";
                Log.d(TAG, "openUsbDevice usb device info : " + strValue);

                if ((device.getProductId() == pId && device.getVendorId() == vId)) {
                    Log.d(TAG, "openUsbDevice matching pid:" + device.getProductId() + " vid:" + device.getVendorId());
                    mUsbDevice = device;
                    break;
                }
            }
            return mUsbDevice;
        }
    }

    public boolean connectUsbDevice() {
        mUsbInterface = mUsbDevice.getInterface(0);
        int endpointCount = mUsbInterface.getEndpointCount();
        for (int i = 0; i < endpointCount; ++i) {
            UsbEndpoint usbEndpoint = mUsbInterface.getEndpoint(i);
            Log.d(TAG, "usbEndpoint.getDirection()=" + usbEndpoint.getDirection() +
                    ", usbEndpoint.getDirection()=" + usbEndpoint.getDirection());
            if (usbEndpoint.getDirection() == 128) {
                mUsbEndPointIn = usbEndpoint;
            } else if (usbEndpoint.getDirection() == 0) {
                mUsbEndPointOut = usbEndpoint;
            }
        }

        if (mUsbEndPointIn != null && mUsbEndPointOut != null) {
            Log.d(TAG, "connectUsbDevice mUsbEndPointIn != null");
            if (!mUsbManager.hasPermission(mUsbDevice)) {
                mUsbDevice = null;
                Log.d(TAG, "connectUsbDevice Permission denied");
            } else {
                mUsbDeviceConnection = mUsbManager.openDevice(mUsbDevice);
                if (mUsbDeviceConnection == null) {
                    Log.d(TAG, "connectUsbDevice unable to connect to device");
                } else {
                    if (!this.mUsbDeviceConnection.claimInterface(mUsbInterface, true)) {
                        Log.d(TAG, "connectUsbDevice unable to claim interface");
                    } else {
                        Log.d(TAG, "connectUsbDevice init success");
                        return true;
                    }
                }
            }
        } else {
            Log.d(TAG, "connectUsbDevice Missing usb endpoints");
        }
        return false;
    }


    /**
     * * describe: close USB device
     */
    public void closeUsbDevice() {
        Log.d(TAG, "closeUsbDevice");
        if (mUsbDevice == null) {
            return;
        }

        try {
            if (mUsbDeviceConnection != null && mUsbInterface != null) {
                mUsbDeviceConnection.releaseInterface(mUsbInterface);
                mUsbInterface = null;
                mUsbDeviceConnection.close();
                mUsbDeviceConnection = null;
                mUsbDevice = null;
                mUsbEndPointOut = null;
                mUsbEndPointIn = null;
            }
        } catch (Exception e) {
            Log.d(TAG, "closeUsbDevice exception: " + e.getMessage());
        }
    }

    public void turnOnGreenLight() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                setLedState(context, 0, 0);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setLedState(context, 1, 1);
            }
        });
    }


    public void turnOnRedLight() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                setLedState(context, 1, 0);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setLedState(context, 0, 1);
            }
        });
    }

    public void turnOffLight() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                setLedState(context, 1, 0);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setLedState(context, 0, 0);
            }
        });
    }

    /**
     * describe Set LED lights
     *
     * @param context
     * @param id      0:green light       1:red light
     * @param state   0:close             1:open
     */
    public void setLedState(final Context context, final int id, final int state) {

        byte[] sendCmd = new byte[15];
        int index = 0;
        sendCmd[index++] = (byte) 0xAA;
        sendCmd[index++] = (byte) 0xAA;
        sendCmd[index++] = (byte) 0x83;
        sendCmd[index++] = (byte) 0x02;
        sendCmd[index++] = (byte) id;
        sendCmd[index++] = (byte) state;
        sendCmd[index++] = (byte) 0x00;
        sendCmd[index++] = (byte) 0x00;
        sendCmd[index++] = (byte) 0x00;
        sendCmd[index++] = (byte) 0x00;
        sendCmd[index++] = (byte) 0x00;
        sendCmd[index++] = (byte) 0x00;
        sendCmd[index++] = (byte) 0x00;
        sendCmd[index++] = (byte) 0x00;
        sendCmd[index] = getCmdSum(sendCmd);

        if (mUsbDeviceConnection != null && mUsbEndPointOut != null) {
            int i = mUsbDeviceConnection.bulkTransfer(mUsbEndPointOut, sendCmd, sendCmd.length, 0);
            if (i > 0) {
                Log.d(TAG, "setLedState 成功 i = " + i + "  sendCmd:" + Arrays.toString(sendCmd));
            } else {
                Log.d(TAG, "setLedState 失败 i = " + i + "  sendCmd:" + Arrays.toString(sendCmd));
            }
        } else {
            Log.d(TAG, "mUsbDeviceConnection=" + mUsbDeviceConnection +
                    ", mUsbEndPointOut=" + mUsbEndPointOut);
        }
}

    private static byte getCmdSum(byte[] sendCmd) {
        int sum = 0;
        for (byte b : sendCmd) {
            sum += b;
        }
        return (byte) sum;
    }

    private int parseAssisData(byte[] mBytes) {
        int iValue = 0;
        if (mBytes.length == 8 && mBytes[0] == 0x02) {
            byte bValue = mBytes[1];
            iValue = byte2Int(bValue);
            Log.d(TAG, "parseAssisData bValue = " + bValue + "  iValue = " + iValue);
        }
        return iValue;
    }

    public static int byte2Int(byte b) {
        return b < 0 ? b & 255 : b;
    }
}
