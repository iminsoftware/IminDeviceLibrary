package com.example.imindevicelibrary.ui;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.device.manager.sdk.DeviceManager;
import com.example.imindevicelibrary.R;


public class LightDeviceActivity extends AppCompatActivity implements View.OnClickListener {
    private DeviceManager iDeviceService;
    private static final String TAG = "LightDeviceActivity";
    private static final String ACTION_USB_PERMISSION = "android.permission.USB_PERMISSION";
    private static final String ACTION_USB_DEVICE_ATTACHED = "android.hardware.usb.action.USB_DEVICE_ATTACHED";
    private static final String ACTION_USB_DEVICE_DETACHED = "android.hardware.usb.action.USB_DEVICE_DETACHED";

    private UsbManager mUsbManager;
    private UsbDevice usbDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_device);
        iDeviceService = DeviceManager.getDeviceManager(this);
        initListener();
    }


    private void initListener() {

        mUsbManager = (UsbManager) this.getSystemService(Context.USB_SERVICE);

        findViewById(R.id.a13_connectLightDevice).setOnClickListener(this);
        findViewById(R.id.a13_disconnectLightDevice).setOnClickListener(this);
        findViewById(R.id.a13_turnOnGreenLight).setOnClickListener(this);
        findViewById(R.id.a13_turnOnRedLight).setOnClickListener(this);
        findViewById(R.id.a13_turnOffLight).setOnClickListener(this);

    }


    @SuppressLint({"DefaultLocale", "NonConstantResourceId"})
    @Override
    public void onClick(View v) {
        if (!iDeviceService.isInitialized()) {
            toast("服务未绑定");
            return;
        }
        try {

            switch (v.getId()) {

                case R.id.a13_connectLightDevice:
                    //连接
                    connectLightDevice();
                    return;
                case R.id.a13_disconnectLightDevice:
                    iDeviceService.disconnectLightDevice(LightDeviceActivity.this);

                    break;
                case R.id.a13_turnOnGreenLight:
                    iDeviceService.turnOnGreenLight(LightDeviceActivity.this);
                    break;
                case R.id.a13_turnOnRedLight:
                    iDeviceService.turnOnRedLight(LightDeviceActivity.this);
                    break;
                case R.id.a13_turnOffLight:
                    iDeviceService.turnOffLight(LightDeviceActivity.this);
                    break;

            }


        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }

    }


    private void connectLightDevice() {
        usbDevice = iDeviceService.getLightDevice(LightDeviceActivity.this);

        toastMain(usbDevice != null ? "Device: "+usbDevice.getDeviceName() : "Device not found");
        boolean isConnect = requestPermission(usbDevice);

    }

    public boolean requestPermission(UsbDevice usbDevice) {
        Log.d(TAG,"requestPermission============");
        if (usbDevice == null){
            return false;
        }

        if (mUsbManager.hasPermission(usbDevice)) {
            boolean isConnect =  iDeviceService.connectLightDevice(LightDeviceActivity.this);
            toastMain("connect: "+isConnect);
            Log.d(TAG,"usb is connect:"+isConnect);
            return true;
        } else {

            PendingIntent pendingIntent;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            } else {
                pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
            }

            IntentFilter intentFilter = new IntentFilter(ACTION_USB_PERMISSION);
            intentFilter.addAction(ACTION_USB_DEVICE_ATTACHED);
            intentFilter.addAction(ACTION_USB_DEVICE_DETACHED);
            this.registerReceiver(mUsbDeviceReceiver, intentFilter);
            mUsbManager.requestPermission(usbDevice, pendingIntent);
            return mUsbManager.hasPermission(usbDevice);
        }
    }

    public BroadcastReceiver mUsbDeviceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG,"UsbDeviceReceiver action = " + action);
            if (ACTION_USB_PERMISSION.equals(action)) {
                UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                Log.d(TAG,"UsbDeviceReceiver device = " + action);
//                if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
//                    if (device != null) {

                boolean isConnect =  iDeviceService.connectLightDevice(LightDeviceActivity.this);
               // handlerOpenCloseLight(isConnect);
                Log.d(TAG,"是否已连接"+isConnect +"mUsbDeviceReceiver");
//                    }
//                }
            }else if(ACTION_USB_DEVICE_ATTACHED.equals(action)){
                //openUsbDevice();
            }else if(ACTION_USB_DEVICE_DETACHED.equals(action)){
                //TODO closeUsbDevice();
            }
        }
    };





    private void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void toastMain(String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LightDeviceActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iDeviceService = null;
    }

}