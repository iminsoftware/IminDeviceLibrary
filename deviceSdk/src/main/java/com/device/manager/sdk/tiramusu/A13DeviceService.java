package com.device.manager.sdk.tiramusu;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;

import com.device.manager.server.aidl.IAsyncCallback;
import com.device.manager.server.aidl.IDeviceService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ：Chenjk
 * @version 1.0
 * @createTime ：2025/9/9 12:07
 **/
public class A13DeviceService implements IDeviceService {
    A13DeviceManager a13DeviceManager;

    public A13DeviceService(Context context){
        super();
        a13DeviceManager = new A13DeviceManager(context);
    }


    protected final ExecutorService executors = Executors.newSingleThreadExecutor();
    @Override
    public String getSystemProperties(String name) throws RemoteException {
        return "";
    }

    @Override
    public void getDeviceInfoAsyn(String name, IAsyncCallback callback) throws RemoteException {
        if(TextUtils.isEmpty(name)){
            return;
        }
        executors.submit(() -> {
            String result = "";
            try {
                result = a13DeviceManager.getDeviceInfo(name);
                callback.onResult(result.toString());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });

    }

    @Override
    public void sendAMCommandAsyn(String cmd, IAsyncCallback callback) throws RemoteException {
        if(cmd == null || cmd.isEmpty()){
            return;
        }

        executors.submit(() -> {
            JsonObject result = new JsonObject();
            try {
                JsonObject jsonCMD = new JsonParser().parse(cmd).getAsJsonObject();
                String deviceStatus = a13DeviceManager.sendAMCommand(jsonCMD);
                //applicationsManager.sendAMCommand(jsonCMD);
                result.addProperty("code", 0);
                result.addProperty("value", deviceStatus);
            } catch (Exception e) {
                e.printStackTrace();
                result.addProperty("code", -1);
            }

            try {
                callback.onResult(result.toString());

            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void sendDeviceActionAsyn(String type, String cmd, IAsyncCallback callback) throws RemoteException {
        executors.submit(() -> {
            String result = "";
            try {
                result = a13DeviceManager.sendDeviceActionAsyn(type,cmd);

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                callback.onResult(result);

            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public String getBarcodeScannerDetails() throws RemoteException {
        return "";
    }

    @Override
    public String getVideoDisplayDetails() throws RemoteException {
        return "";
    }

    @Override
    public String getEmmcEmbeddedMultimediaCardDetails() throws RemoteException {
        return "";
    }

    @Override
    public String getNFCDetails() throws RemoteException {
        return "";
    }

    @Override
    public String getBuiltinCellularDetails() throws RemoteException {
        return "";
    }

    @Override
    public String getCameraDetails() throws RemoteException {
        return "";
    }

    @Override
    public String getDeviceDetail() throws RemoteException {
        return "";
    }

    @Override
    public String getMemoryDetail() throws RemoteException {
        return "";
    }

    @Override
    public String getBatteryDetail() throws RemoteException {
        return "";
    }

    @Override
    public String getCPUDetail() throws RemoteException {
        return "";
    }

    @Override
    public String getWIFIDetails() throws RemoteException {
        return "";
    }

    @Override
    public String getApplicationDetails() throws RemoteException {
        return "";
    }

    @Override
    public String getPrinterDetails() throws RemoteException {
        return "";
    }

    @Override
    public String getStorageDetails() throws RemoteException {
        return "";
    }

    @Override
    public IBinder asBinder() {
        return null;
    }
}
