package com.example.imindevicelibrary.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.device.manager.sdk.DeviceManager;
import com.device.manager.server.aidl.IAsyncCallback;
import com.example.imindevicelibrary.R;
import com.example.imindevicelibrary.bean.ControlBean;
import com.example.imindevicelibrary.bean.DisplaySettings;
import com.example.imindevicelibrary.databinding.ActivityDisplaysettingsBinding;
import com.google.gson.Gson;


public class DisplaySettingsActivity extends AppCompatActivity implements View.OnClickListener {
    private DeviceManager iDeviceService;
    private static final String TAG = "DisplaySettingsActivity";
    private ActivityDisplaysettingsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDisplaysettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        iDeviceService = DeviceManager.getDeviceManager(this);
        initListener();
    }

    private void initListener() {
        binding.screenTimeoutUserChoice.setOnClickListener(this);
        binding.screenTimeoutEnforced.setOnClickListener(this);
    }

    private ControlBean controlBeans = new ControlBean();

    @SuppressLint({"DefaultLocale", "NonConstantResourceId"})
    @Override
    public void onClick(View v) {
        if (!iDeviceService.isInitialized()) {
            toast("服务未绑定");
            return;
        }
        try {
            controlBeans = new ControlBean();
            DisplaySettings displaySettings = new DisplaySettings();
            controlBeans.displaySettings = displaySettings;
            switch (v.getId()) {
                case R.id.screenTimeoutUserChoice:
                    displaySettings.screenTimeoutMode = "SCREEN_TIMEOUT_USER_CHOICE";
                    iDeviceService.sendAMCommandAsyn(new Gson().toJson(controlBeans), new IAsyncCallback.Stub() {
                        @Override
                        public void onResult(String result) throws RemoteException {
                            toastMain(result);
                            Log.d(TAG, result);
                        }
                    });
                    break;
                case R.id.screenTimeoutEnforced:
                    displaySettings.screenTimeoutMode = "SCREEN_TIMEOUT_ENFORCED";
                    String result = binding.etScreenTimeout.getText().toString().trim();
                    if (!TextUtils.isEmpty(result)) {
                        controlBeans.displaySettings.screenTimeout = result;
                    }
                    iDeviceService.sendAMCommandAsyn(new Gson().toJson(controlBeans), new IAsyncCallback.Stub() {
                        @Override
                        public void onResult(String result) throws RemoteException {
                            toastMain(result);
                            Log.d(TAG, result);
                        }
                    });
                    break;
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