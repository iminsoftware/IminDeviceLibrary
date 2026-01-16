package com.example.imindevicelibrary.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.device.manager.sdk.DeviceManager;
import com.device.manager.sdk.tiramusu.CMD;
import com.device.manager.server.aidl.IAsyncCallback;
import com.example.imindevicelibrary.R;


/**
 * A13获取设备相关信息界面
 *
 */
public class A13LibsDeviceInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private DeviceManager iDeviceService;
    private static final String TAG = "A13DeviceInfoActivity";

    private TextView a13_plaform;
    private TextView a13_model;

    private TextView a13_brand;
    private TextView a13_sn;
    private TextView a13_cpu;
    private TextView a13_dualscreen_prop;
    private TextView a13_is_supper_cashbox;
    private TextView a13_hw_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a13_libs_devices_info);
        iDeviceService = DeviceManager.getDeviceManager(this);
        initListener();
        getData();
    }

    private void getData() {
        if (!iDeviceService.isInitialized()) {
            toast("服务未绑定");
            return;
        }
        try {
            iDeviceService.getDeviceInfoAsyn(CMD.GET_PLATFORM, new IAsyncCallback.Stub() {
                @Override
                public void onResult(String result) throws RemoteException {
                    Log.d(TAG, result);
                    runOnUiThread(() -> {
                        a13_plaform.setText(result);
                    });
                }
            });

            iDeviceService.getDeviceInfoAsyn(CMD.GET_MODEL, new IAsyncCallback.Stub() {
                @Override
                public void onResult(String result) throws RemoteException {
                    Log.d(TAG, result);
                    runOnUiThread(() -> {
                        a13_model.setText(result);
                    });
                }
            });

            iDeviceService.getDeviceInfoAsyn(CMD.GET_SN, new IAsyncCallback.Stub() {
                @Override
                public void onResult(String result) throws RemoteException {
                    Log.d(TAG, result);
                    runOnUiThread(() -> {
                        a13_sn.setText(result);
                    });
                }
            });

            iDeviceService.getDeviceInfoAsyn(CMD.GET_BRAND, new IAsyncCallback.Stub() {
                @Override
                public void onResult(String result) throws RemoteException {
                    Log.d(TAG, result);
                    runOnUiThread(() -> {
                        a13_brand.setText(result);
                    });
                }
            });



            iDeviceService.getDeviceInfoAsyn(CMD.DEVICE_INFO_CPU_INFO, new IAsyncCallback.Stub() {
                @Override
                public void onResult(String result) throws RemoteException {
                    Log.d(TAG, result);
                    runOnUiThread(() -> {
                        a13_cpu.setText(result);
                    });
                }
            });


            iDeviceService.getDeviceInfoAsyn(CMD.GET_DUALSCREEN_PROP, new IAsyncCallback.Stub() {
                @Override
                public void onResult(String result) throws RemoteException {
                    Log.d(TAG, result);
                    runOnUiThread(() -> {
                        a13_dualscreen_prop.setText(result);
                    });
                }
            });



            iDeviceService.getDeviceInfoAsyn(CMD.IS_SUPPER_CASHBOX, new IAsyncCallback.Stub() {
                @Override
                public void onResult(String result) throws RemoteException {
                    Log.d(TAG, result);
                    runOnUiThread(() -> {
                        a13_is_supper_cashbox.setText(result);
                    });
                }
            });

            iDeviceService.getDeviceInfoAsyn(CMD.DEVICE_INFO_HW_INFO, new IAsyncCallback.Stub() {
                @Override
                public void onResult(String result) throws RemoteException {
                    runOnUiThread(() -> {
                        a13_hw_info.setText(result);
                    });
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
    }

    private void initListener() {
        a13_plaform = findViewById(R.id.a13_plaform);
        a13_model = findViewById(R.id.a13_model);
        a13_brand = findViewById(R.id.a13_brand);
        a13_sn = findViewById(R.id.a13_sn);
        a13_cpu = findViewById(R.id.a13_cpu);
        a13_cpu = findViewById(R.id.a13_cpu);
        a13_dualscreen_prop = findViewById(R.id.a13_dualscreen_prop);
        a13_is_supper_cashbox = findViewById(R.id.a13_is_supper_cashbox);
        a13_hw_info = findViewById(R.id.a13_hw_info);

    }


    @SuppressLint("DefaultLocale")
    @Override
    public void onClick(View v) {


    }

    private void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iDeviceService = null;
    }

}