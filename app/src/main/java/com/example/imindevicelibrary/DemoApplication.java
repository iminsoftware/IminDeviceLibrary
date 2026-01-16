package com.example.imindevicelibrary;

import android.app.Application;

import com.device.manager.sdk.DeviceManager;


public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DeviceManager.initialize(this);
    }
}
