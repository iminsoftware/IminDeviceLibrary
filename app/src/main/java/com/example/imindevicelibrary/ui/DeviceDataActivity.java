package com.example.imindevicelibrary.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.device.manager.sdk.DeviceManager;
import com.device.manager.sdk.tiramusu.CMD;
import com.device.manager.server.aidl.IAsyncCallback;
import com.example.imindevicelibrary.R;
import com.example.imindevicelibrary.databinding.ActivityDeviceDataBinding;

public class DeviceDataActivity extends AppCompatActivity implements View.OnClickListener {
    private DeviceManager iDeviceService;
    private static final String TAG = "MainActivity";
    private ActivityDeviceDataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeviceDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        iDeviceService = DeviceManager.getDeviceManager(this);
        initListener();
        binding.dualscreen.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        },1000);

    }


    private void getData() {
        if(!iDeviceService.isInitialized()){
            toast("Service not bound");
            return;
        }
        try {
            //设备的硬件平台
            iDeviceService.getDeviceInfoAsyn(CMD.GET_PLATFORM, new IAsyncCallback.Stub() {
                @Override
                public void onResult(String s) throws RemoteException {
                    runOnUiThread(() -> {
                        if(!TextUtils.isEmpty(s)){
                            binding.getPlatform.setText(s);
                        }

                    });
                }


            });

            //获取设备型号
            iDeviceService.getDeviceInfoAsyn(CMD.GET_MODEL, new IAsyncCallback.Stub() {
                @Override
                public void onResult(String s) throws RemoteException {
                    if(!TextUtils.isEmpty(s)){
                        binding.getModel.setText(s);
                    }
                }


            });

            //获取设备SN
            iDeviceService.getDeviceInfoAsyn(CMD.GET_SN, new IAsyncCallback.Stub() {
                @Override
                public void onResult(String s) throws RemoteException {
                    if(!TextUtils.isEmpty(s)){
                        binding.getSn.setText(s);
                    }
                }


            });

            //获取品牌
            iDeviceService.getDeviceInfoAsyn(CMD.GET_BRAND, new IAsyncCallback.Stub() {
                @Override
                public void onResult(String s) throws RemoteException {
                    if(!TextUtils.isEmpty(s)){
                        binding.getBrand.setText(s);
                    }
                }


            });

            //设备是否为双屏
            iDeviceService.getDeviceInfoAsyn(CMD.GET_DUALSCREEN_PROP, new IAsyncCallback.Stub() {
                @Override
                public void onResult(String s) throws RemoteException {
                    if(!TextUtils.isEmpty(s)){
                        binding.dualscreen.setText(s);
                    }
                }


            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void initListener() {

        binding.getDeviceInfoHW.setOnClickListener(this);
        binding.getDeviceInfoNetInfo.setOnClickListener(this);
        binding.getDeviceInfoSW.setOnClickListener(this);
        binding.getDeviceInfoDsiplay.setOnClickListener(this);
        binding.getDeviceInfoMemoryInfo.setOnClickListener(this);

        binding.getMemoryDetailInfo.setOnClickListener(this);
        binding.getStorageInfo.setOnClickListener(this);
        binding.getCpuInfo.setOnClickListener(this);


    }


    @SuppressLint({"DefaultLocale", "NonConstantResourceId"})
    @Override
    public void onClick(View v) {
        if (!iDeviceService.isInitialized()) {
            toast("服务未绑定");
            return;
        }
        try {
            Intent intent = new Intent(DeviceDataActivity.this, DeviceInfoDisplayActivity.class);
            intent.putExtra("title",(((TextView)v).getText().toString()));
            switch (v.getId()) {

                case R.id.getDeviceInfoHW:
                    intent.putExtra("infoTag","DEVICE_INFO_HW_INFO");
                    startActivity(intent);
                    break;
                case R.id.getDeviceInfoNetInfo:
                    intent.putExtra("infoTag","DEVICE_INFO_NETWORK_INFO");
                    startActivity(intent);
                    break;
                case R.id.getDeviceInfoSW:
                    intent.putExtra("infoTag","DEVICE_INFO_SW_INFO");
                    startActivity(intent);
                    break;
                case R.id.getDeviceInfoDsiplay:
                    intent.putExtra("infoTag","DEVICE_INFO_DISPLAY_INFO");
                    startActivity(intent);
                    break;
                case R.id.getDeviceInfoMemoryInfo:
                    intent.putExtra("infoTag","DEVICE_INFO_MEMORY_INFO");
                    startActivity(intent);
                    break;

                case R.id.getMemoryDetailInfo:
                    intent.putExtra("infoTag","DEVICE_INFO_MEMORY_DETIAL_INFO");
                    startActivity(intent);
                    break;
                case R.id.getStorageInfo:
                    intent.putExtra("infoTag","DEVICE_INFO_STORAGE_INFO");
                    startActivity(intent);
                    break;
                case R.id.getCpuInfo:
                    intent.putExtra("infoTag","DEVICE_INFO_CPU_INFO");
                    startActivity(intent);
                    break;
               /* case R.id.DEVICE_INFO_BATTERY_INFO:
                    intent.putExtra("infoTag","DEVICE_INFO_BATTERY_INFO");
                    startActivity(intent);
                    break;*/

            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }

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