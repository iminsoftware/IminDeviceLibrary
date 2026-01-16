package com.example.imindevicelibrary.ui;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.device.manager.sdk.DeviceManager;
import com.device.manager.server.aidl.IAsyncCallback;
import com.example.imindevicelibrary.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class OEMConfigActivity extends AppCompatActivity implements View.OnClickListener {
    private DeviceManager mDeviceManager;
    private static final String TAG = "OEMConfigActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oem_config);
        mDeviceManager = DeviceManager.getDeviceManager(this);
        initListener();

    }


    /**
     * 指定包名应用开机后启动
     */
    private void setAppStartOnBoot() {
        JsonObject controlBean = new JsonObject();
        JsonArray OEMApplicationPolicy = new JsonArray();
        controlBean.add("OEMApplicationPolicy", OEMApplicationPolicy);
        JsonObject item = new JsonObject();
        item.addProperty("packageName", "com.example.iminlibsdemo");
        item.addProperty("setAppStartOnBoot", true);
        OEMApplicationPolicy.add(item);
        try {
            mDeviceManager.sendAMCommandAsyn(new Gson().toJson(controlBean), new IAsyncCallback.Stub() {
                @Override
                public void onResult(String result) throws RemoteException {
                    Log.d(TAG, "sendAMCommandAsyn  "+result);
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }


    /**
     * 授权
     * type ：  GRANT --> 授权
     *          DENY  --> 拒绝
     */
    private void requestAllRuntimePermission(String type) {
        JsonObject controlBean = new JsonObject();
        JsonArray OEMApplicationPolicy = new JsonArray();
        controlBean.add("OEMApplicationPolicy", OEMApplicationPolicy);
        JsonObject item = new JsonObject();
       // item.addProperty("packageName", "com.example.iminlibsdemo");
        //写入包名
        item.addProperty("packageName", "com.device.manager.server");
        item.addProperty("allRuntimePermissionPolicy", type);
        OEMApplicationPolicy.add(item);

        try {
            mDeviceManager.sendAMCommandAsyn(new Gson().toJson(controlBean), new IAsyncCallback.Stub() {
                @Override
                public void onResult(String result) throws RemoteException {
                    Log.d(TAG, "sendAMCommandAsyn  "+result);
                     boolean flag = (ContextCompat.checkSelfPermission(OEMConfigActivity.this, "android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED);
                    Log.d(TAG, "sendAMCommandAsyn  checkSelfPermission  : " + flag );
                    toastMain(result);
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }



    private void requestPermission(boolean request) {
        JsonObject controlBean = new JsonObject();
        JsonObject oemConfig = new JsonObject();
        controlBean.add("oemConfig", oemConfig);
        JsonObject permissionPolicy = new JsonObject();
        permissionPolicy.addProperty("permissionStatus", request);
       // permissionPolicy.addProperty("permissionPkg", "com.example.iminlibsdemo");
        permissionPolicy.addProperty("permissionName", "android.permission.WRITE_EXTERNAL_STORAGE");
        permissionPolicy.addProperty("permissionPkg", "com.device.manager.server");
        //permissionPolicy.addProperty("permissionName", "android.permission.SET_TIME_ZONE");
        oemConfig.add("defaultPermissionPolicy", permissionPolicy);
        try {
            mDeviceManager.sendAMCommandAsyn(new Gson().toJson(controlBean), new IAsyncCallback.Stub() {
                @Override
                public void onResult(String result) throws RemoteException {
                    Log.d(TAG, "sendAMCommandAsyn  "+result);

                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void requestPermission2(){
        JsonObject controlBean = new JsonObject();
        JsonArray OEMApplicationPolicy = new JsonArray();
        controlBean.add("OEMApplicationPolicy", OEMApplicationPolicy);
        JsonObject item = new JsonObject();
        item.addProperty("packageName", "com.device.manager.server");
        item.addProperty("allRuntimePermissionPolicy", "GRANT");
        OEMApplicationPolicy.add(item);

        try {
            mDeviceManager.sendAMCommandAsyn(new Gson().toJson(controlBean), new IAsyncCallback.Stub() {
                @Override
                public void onResult(String result) throws RemoteException {
                    Log.d(TAG, "sendAMCommandAsyn xxxaaa "+result);
                    toastMain(result);


                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void requestPermission6() {
        JsonObject controlBean3 = new JsonObject();
        JsonObject oemConfig3 = new JsonObject();
        controlBean3.add("oemConfig", oemConfig3);
        JsonObject permissionPolicy3 = new JsonObject();
        permissionPolicy3.addProperty("permissionStatus", true);
        permissionPolicy3.addProperty("permissionPkg", "com.device.manager.server");
        permissionPolicy3.addProperty("permissionName", "android.permission.SET_TIME");
        oemConfig3.add("defaultPermissionPolicy", permissionPolicy3);
        try {
            mDeviceManager.sendAMCommandAsyn(new Gson().toJson(controlBean3), new IAsyncCallback.Stub() {
                @Override
                public void onResult(String result) throws RemoteException {
                    Log.d(TAG, "xxx sendAMCommandAsyn  "+result);
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }


        JsonObject controlBean2 = new JsonObject();
        JsonObject oemConfig2 = new JsonObject();
        controlBean2.add("oemConfig", oemConfig2);
        JsonObject permissionPolicy2 = new JsonObject();
        permissionPolicy2.addProperty("permissionStatus", true);
        permissionPolicy2.addProperty("permissionPkg", "com.device.manager.server");
        permissionPolicy2.addProperty("permissionName", "android.permission.SET_TIME_ZONE");
        oemConfig2.add("defaultPermissionPolicy", permissionPolicy2);
        try {
            mDeviceManager.sendAMCommandAsyn(new Gson().toJson(controlBean2), new IAsyncCallback.Stub() {
                @Override
                public void onResult(String result) throws RemoteException {
                    Log.d(TAG, "xxx sendAMCommandAsyn  "+result);
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    /**
     * 权限授权
     */
    private void requestPermission() {
        JsonObject controlBean = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        controlBean.add("applications", jsonArray);

        JsonObject item = new JsonObject();
        jsonArray.add(item);

        item.addProperty("packageName", "com.device.manager.server");
        JsonArray permissionArray = new JsonArray();
        item.add("permissionGrants", permissionArray);

        JsonObject permission1 = new JsonObject();
        permission1.addProperty("permission", "android.permission.SET_TIME");
        permission1.addProperty("policy", "GRANT");

        JsonObject permission2 = new JsonObject();
        permission2.addProperty("permission", "android.permission.SET_TIME_ZONE");
        permission2.addProperty("policy", "GRANT");

        permissionArray.add(permission1);
        permissionArray.add(permission2);


        try {
            mDeviceManager.sendAMCommandAsyn(new Gson().toJson(controlBean), new IAsyncCallback.Stub() {
                @Override
                public void onResult(String result) throws RemoteException {

                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }






    private void initListener() {
        findViewById(R.id.blue_setting).setOnClickListener(this);
        findViewById(R.id.screentime_setting).setOnClickListener(this);
        findViewById(R.id.alertWindowPermissionSetting).setOnClickListener(this);
        findViewById(R.id.virtualBluetooth_setting).setOnClickListener(this);
        findViewById(R.id.setBlueToothSwitchFalse).setOnClickListener(this);
        findViewById(R.id.setBlueToothSwitchTrue).setOnClickListener(this);
        findViewById(R.id.setWifiSwitchTrue).setOnClickListener(this);
        findViewById(R.id.setWifiSwitchFalse).setOnClickListener(this);


        findViewById(R.id.install).setOnClickListener(this);
        findViewById(R.id.requestPermissionFalse).setOnClickListener(this);
        findViewById(R.id.requestPermissionTrue).setOnClickListener(this);
        findViewById(R.id.allGrant).setOnClickListener(this);
        findViewById(R.id.allDeny).setOnClickListener(this);
        findViewById(R.id.shanghai).setOnClickListener(this);
        findViewById(R.id.newYork).setOnClickListener(this);

    }

    public static void longLog(String tag, String message) {
        if (message == null) {
            Log.d(tag, "null");
            return;
        }

        // 如果文本不超过限制，直接打印
        if (message.length() <= 4000) {
            Log.d(tag, message);
            return;
        }

        // 分段打印
        int start = 0;
        int segmentNum = 1;
        int totalLength = message.length();

        while (start < totalLength) {
            int end = Math.min(start + 4000, totalLength);
            String segment = message.substring(start, end);
            // 添加分段信息：[当前段号/总段数]
            Log.d(tag, "[" + segmentNum + "/" +
                    (int)Math.ceil((double)totalLength/4000) + "] " + segment);

            start = end;
            segmentNum++;
        }
}

    private void installApk() {

        JsonObject controlBean = new JsonObject();
        JsonObject oemConfig = new JsonObject();
        controlBean.add("oemConfig", oemConfig);
        //安装启动包名
        oemConfig.addProperty("installApkPackage","cn.wch.usbdemo");
        //传入apk路径
        oemConfig.addProperty("installApkPath","/sdcard/usbTest.apk");
        //是否安装后启动
        oemConfig.addProperty("isLaunchAfterInstallation",true);

        try {
            mDeviceManager.sendAMCommandAsyn(new Gson().toJson(controlBean), new IAsyncCallback.Stub() {
                @Override
                public void onResult(String result) {

                    Log.d(TAG, "sendAMCommandAsyn install : " + result);
                }
            });
        } catch (RemoteException e) {

        }

    }
    /**
     * 设置时间
     * setTimeZone需使用 TimeZone.getAvailableIDs() 里面的值
     */
    private void setShanghaiTime() {
        boolean flag = (ContextCompat.checkSelfPermission(OEMConfigActivity.this, "android.permission.SET_TIME") == PackageManager.PERMISSION_GRANTED);
        boolean flag2 = (ContextCompat.checkSelfPermission(OEMConfigActivity.this, "android.permission.SET_TIME_ZONE") == PackageManager.PERMISSION_GRANTED);
                    Log.d(TAG, "xxx  checkSelfPermission  : " + flag  +" "+flag2);
        //一敏版本没有权限，先授权
        requestPermission();

        boolean flag3 = (ContextCompat.checkSelfPermission(OEMConfigActivity.this, "android.permission.SET_TIME") == PackageManager.PERMISSION_GRANTED);
        boolean flag4 = (ContextCompat.checkSelfPermission(OEMConfigActivity.this, "android.permission.SET_TIME_ZONE") == PackageManager.PERMISSION_GRANTED);
        Log.d(TAG, "xxx  requestPermission后777 : " + flag3  +" "+flag4);

        //setTimeZone需使用 TimeZone.getAvailableIDs() 里面的值
        JsonObject controlBean = new JsonObject();
        JsonObject oemConfig = new JsonObject();
        controlBean.add("oemConfig", oemConfig);
        oemConfig.addProperty("setTime",1767086226000L);
        oemConfig.addProperty("setTimeZone","Asia/Shanghai");

        try {
            mDeviceManager.sendAMCommandAsyn(new Gson().toJson(controlBean), new IAsyncCallback.Stub() {
                @Override
                public void onResult(String result) {

                }
            });
        } catch (RemoteException e) {
            Log.d(TAG, "xxx  RemoteException  xxxx: " + flag  +" "+flag2);
        }

    }
    /**
     * 设置时间
     * setTimeZone需使用 TimeZone.getAvailableIDs() 里面的值
     */
    private void setNewYorkTime() {

        JsonObject controlBean = new JsonObject();
        JsonObject oemConfig = new JsonObject();
        controlBean.add("oemConfig", oemConfig);
        oemConfig.addProperty("setTime",1767086226000L);
        //setTimeZone需使用 TimeZone.getAvailableIDs() 里面的值
        oemConfig.addProperty("setTimeZone","America/New_York");

        try {
            mDeviceManager.sendAMCommandAsyn(new Gson().toJson(controlBean), new IAsyncCallback.Stub() {
                @Override
                public void onResult(String result) {

                    Log.d(TAG, "sendAMCommandAsyn setTime end: " + result);
                }
            });
        } catch (RemoteException e) {

        }

    }




    @SuppressLint({"DefaultLocale", "NonConstantResourceId"})
    @Override
    public void onClick(View v) {
        if (!mDeviceManager.isInitialized()) {
            toast("服务未绑定");
            return;
        }
        try {
            JsonObject controlBean = new JsonObject();
            JsonObject oemConfig = new JsonObject();
            controlBean.add("oemConfig", oemConfig);
            switch (v.getId()) {
                case R.id.blue_setting:
                    EditText editTextBlue = findViewById(R.id.blue_txt);
                    String txtBlue = editTextBlue.getText().toString();
                    oemConfig.addProperty("setBTName", txtBlue);
                    break;
                case R.id.virtualBluetooth_setting:
                    EditText VirtualBluetooth_txt = findViewById(R.id.VirtualBluetooth_txt);
                    String txtVirtualBluetooth = VirtualBluetooth_txt.getText().toString();
                    oemConfig.addProperty("setDeviceVirtualBluetoothName", txtVirtualBluetooth);
                    break;
                case R.id.screentime_setting:
                    EditText edScreenTime = findViewById(R.id.screentime_txt);
                    String txtScreenTime = edScreenTime.getText().toString();
                    oemConfig.addProperty("screenTimeout", txtScreenTime);
                    break;
                case R.id.alertWindowPermissionSetting:
                    EditText AlertWindowPermiss = findViewById(R.id.AlertWindowPermiss_txt);
                    String txtAlertWindowPermiss = AlertWindowPermiss.getText().toString();
                    oemConfig.addProperty("setAppsHaveAlertWindowPermiss", txtAlertWindowPermiss);
                    break;

                //设置wifi开关
                case R.id.setWifiSwitchTrue:
                    oemConfig.addProperty("setWifiSwitch", true);
                    break;

                case R.id.setWifiSwitchFalse:
                    oemConfig.addProperty("setWifiSwitch", false);
                    break;

                //设置蓝牙开关
                case R.id.setBlueToothSwitchTrue:
                    oemConfig.addProperty("setBluetoothSwitch", true);
                    break;

                case R.id.setBlueToothSwitchFalse:
                    oemConfig.addProperty("setBluetoothSwitch", false);
                    break;


                case R.id.install:
                    installApk();
                    break;

                case R.id.requestPermissionTrue:
                    requestPermission(true);
                    return;

                case R.id.requestPermissionFalse:
                    requestPermission(false);
                    return;

                case R.id.allGrant:
                    //授权
                    requestAllRuntimePermission("GRANT");
                    return;

                case R.id.allDeny:

                    requestAllRuntimePermission("DENY");
                    return;

                case R.id.shanghai:

                    setShanghaiTime();
                    return;

                case R.id.newYork:
                    setNewYorkTime();
                    return;

            }

            mDeviceManager.sendAMCommandAsyn(new Gson().toJson(controlBean), new IAsyncCallback.Stub() {
                @Override
                public void onResult(String result) {
                    toastMain(result);
                    Log.d(TAG, "OEMConfigManager return: " + result);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }

    }

    private void toastMain(String result) {
        runOnUiThread(() -> toast(result));
    }

    private void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDeviceManager = null;
    }

}