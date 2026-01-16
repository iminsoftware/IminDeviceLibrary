package com.example.imindevicelibrary.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.device.manager.sdk.DeviceManager;
import com.device.manager.server.aidl.IAsyncCallback;
import com.example.imindevicelibrary.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


public class A13LibsApiActivity extends AppCompatActivity implements View.OnClickListener {
    private DeviceManager iDeviceService;
    private static final String TAG = "A13LibTestActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a13_libs_api);
        iDeviceService = DeviceManager.getDeviceManager(this);
        initListener();

    }


    private void initListener() {
        findViewById(R.id.a13_enable_wifi).setOnClickListener(this);
        findViewById(R.id.a13_disabled_wifi).setOnClickListener(this);
        findViewById(R.id.a13_enable_bluetooth).setOnClickListener(this);
        findViewById(R.id.a13_disabled_bluetooth).setOnClickListener(this);
        findViewById(R.id.a13_usb_set).setOnClickListener(this);
        findViewById(R.id.a13_openCash).setOnClickListener(this);
        findViewById(R.id.a13_setcashbox_1).setOnClickListener(this);
        findViewById(R.id.a13_setcashbox_2).setOnClickListener(this);
        findViewById(R.id.a13_setcashbox_3).setOnClickListener(this);
        findViewById(R.id.a13_get_device_info).setOnClickListener(this);
        findViewById(R.id.goLightDevice).setOnClickListener(this);
        findViewById(R.id.goToPsamApi).setOnClickListener(this);
    }


    @SuppressLint({"DefaultLocale", "NonConstantResourceId"})
    @Override
    public void onClick(View v) {
        if (!iDeviceService.isInitialized()) {
            toast("服务未绑定");
            return;
        }
        try {
            JsonObject controlBean = new JsonObject();
            JsonObject peripheral = new JsonObject();
            controlBean.add("peripheralConfig", peripheral);
            JsonObject OEMConfig = new JsonObject();
            controlBean.add("oemConfig", OEMConfig);

            switch (v.getId()) {
                //wifi
                case R.id.a13_enable_wifi:
                    OEMConfig.addProperty("setWifiSwitch", true);
                    break;
                case R.id.a13_disabled_wifi:
                    OEMConfig.addProperty("setWifiSwitch", false);
                    break;

                // bluetooth
                case R.id.a13_enable_bluetooth:
                    OEMConfig.addProperty("setBluetoothSwitch", true);
                    break;
                case R.id.a13_disabled_bluetooth:
                    OEMConfig.addProperty("setBluetoothSwitch", false);
                    break;

                case R.id.a13_usb_set:
                    EditText editTextUsb = findViewById(R.id.a13_et_usb_package);
                    String txtUsb = editTextUsb.getText().toString();
                    if(TextUtils.isEmpty(txtUsb)){
                        toast("please write app package !");
                        return;
                    }
                    OEMConfig.addProperty("enableUSBPermission", txtUsb);
                    break;

                //CashBox
                case R.id.a13_openCash:
                    peripheral.addProperty("openCashBox", true);
                    break;


                case R.id.a13_get_device_info:
                    startActivity(new Intent(this,A13LibsDeviceInfoActivity.class));
                    return;

                case R.id.goLightDevice:
                    startActivity(new Intent(A13LibsApiActivity.this, LightDeviceActivity.class));
                    return;

                case R.id.goToPsamApi:
                    startActivity(new Intent(A13LibsApiActivity.this, PsamApiActivity.class));
                    return;


            }

            iDeviceService.sendAMCommandAsyn(new Gson().toJson(controlBean), new IAsyncCallback.Stub() {
                @Override
                public void onResult(String result) throws RemoteException {
                    Log.d(TAG, result);
                    toastMain(result);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }

    }


    private void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void toastMain(String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(A13LibsApiActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iDeviceService = null;
    }

}