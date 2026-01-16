package com.example.imindevicelibrary.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.imindevicelibrary.R;
import com.example.imindevicelibrary.databinding.ActivityHomeBinding;

import java.lang.reflect.Method;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "HomeActivity";

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initListener();

    }



    private void initListener() {

        if(Build.VERSION.SDK_INT <= 33){
            binding.goToDeviceInfo.setVisibility(View.GONE);
            binding.goToPeripheralPolicy.setVisibility(View.GONE);
            binding.goToOemConfig.setVisibility(View.GONE);
            binding.goToDisplaySettings.setVisibility(View.GONE);
            binding.goToPsamApi.setVisibility(View.GONE);
            binding.goLightDevice.setVisibility(View.GONE);
        }else{
            binding.goToA13LibsTestPage.setVisibility(View.GONE);
            if(isShowPSam()){
                binding.goToPsamApi.setVisibility(View.VISIBLE);
            }
        }


        binding.goToDeviceInfo.setOnClickListener(this);
        binding.goToPeripheralPolicy.setOnClickListener(this);
        binding.goToOemConfig.setOnClickListener(this);

        binding.goToA13LibsTestPage.setOnClickListener(this);
        binding.goToPsamApi.setOnClickListener(this);
        binding.goLightDevice.setOnClickListener(this);
        binding.goToUIControl.setOnClickListener(this);
        binding.goToDisplaySettings.setOnClickListener(this);
    }

    private boolean isShowPSam(){
        //暂时只支持ML1
        String systemProperties = getSystemProperties("ro.rom.neo_model");
        Log.d("initListener","systemProperties "+systemProperties);//
        return !TextUtils.isEmpty(systemProperties) && systemProperties.contains("ML1");

    }

    public static String getSystemProperties(String property) {
        String value = "";
        try {
            Class clazz = Class.forName("android.os.SystemProperties");
            Method getter = clazz.getDeclaredMethod("get", String.class);
            value = (String) getter.invoke(null, property);
        } catch (Exception e) {
            Log.d("TAG", "Unable to read system properties");
        }
        return value;
    }


    @SuppressLint({"DefaultLocale", "NonConstantResourceId"})
    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.goToDeviceInfo:
                    startActivity(new Intent(HomeActivity.this, DeviceDataActivity.class));
                    break;
                case R.id.goToOemConfig:
                    startActivity(new Intent(HomeActivity.this, OEMConfigActivity.class));
                    break;
                case R.id.goToDisplaySettings:
                    startActivity(new Intent(HomeActivity.this, DisplaySettingsActivity.class));
                    break;

                case R.id.goToPeripheralPolicy:
                    startActivity(new Intent(HomeActivity.this, PeripheralActivity.class));
                    break;

                case R.id.goToA13LibsTestPage:
                    startActivity(new Intent(HomeActivity.this, A13LibsApiActivity.class));
                    break;

                case R.id.goToPsamApi:
                    startActivity(new Intent(HomeActivity.this, PsamApiActivity.class));
                    break;

                case R.id.goLightDevice:
                    startActivity(new Intent(HomeActivity.this, LightDeviceActivity.class));
                    break;
                case R.id.goToUIControl:
                    startActivity(new Intent(HomeActivity.this, UIControlActivity.class));
                    break;

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
    }

}