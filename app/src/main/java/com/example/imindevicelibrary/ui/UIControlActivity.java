package com.example.imindevicelibrary.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.device.manager.sdk.DeviceManager;
import com.example.imindevicelibrary.R;

public class UIControlActivity extends AppCompatActivity implements View.OnClickListener {
    private DeviceManager iDeviceService;
    private static final String TAG = "OEMConfigActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_control);
        iDeviceService = DeviceManager.getDeviceManager(this);
        initListener();

    }

    private void initListener() {

        findViewById(R.id.setHideNavigationBarFalse).setOnClickListener(this);
        findViewById(R.id.setHideNavigationBarTrue).setOnClickListener(this);
        findViewById(R.id.setHideStatusBarFalse).setOnClickListener(this);
        findViewById(R.id.setHideStatusBarTrue).setOnClickListener(this);

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

                //显示隐藏状态栏
                case R.id.setHideStatusBarFalse:
                    iDeviceService.setHideStatusBar(UIControlActivity.this,false);
                    return;

                case R.id.setHideStatusBarTrue:
                    iDeviceService.setHideStatusBar(UIControlActivity.this,true);
                    return;

                //显示隐藏导航栏
                case R.id.setHideNavigationBarFalse:
                    iDeviceService.setHideNavigationBar(UIControlActivity.this,false);
                    return;

                case R.id.setHideNavigationBarTrue:
                    iDeviceService.setHideNavigationBar(UIControlActivity.this,true);
                    return;

            }


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
        iDeviceService = null;
    }



}