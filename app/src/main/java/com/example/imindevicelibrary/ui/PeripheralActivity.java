package com.example.imindevicelibrary.ui;

import android.annotation.SuppressLint;
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


public class PeripheralActivity extends AppCompatActivity implements View.OnClickListener {
    private DeviceManager iDeviceService;
    private static final String TAG = "PeripheralActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peripheral);
        iDeviceService = DeviceManager.getDeviceManager(this);
        initListener();
    }

    private void initListener() {
        findViewById(R.id.openCash).setOnClickListener(this);
        findViewById(R.id.screenBrightnessSubmit).setOnClickListener(this);
        findViewById(R.id.subScreenBrightnessSubmit).setOnClickListener(this);
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
            switch (v.getId()) {
                case R.id.openCash:
                    peripheral.addProperty("openCashBox", true);
                    break;

                case R.id.screenBrightnessSubmit:
                    EditText editText = findViewById(R.id.screenBrightnessTxt);
                    String text = editText.getText().toString();
                    if(TextUtils.isEmpty(text)){
                        return;
                    }
                    peripheral.addProperty("screenBrightness", text);
                    break;

                case R.id.subScreenBrightnessSubmit:
                    EditText subEditText = findViewById(R.id.subScreenBrightnessTxt);
                    String subText = subEditText.getText().toString();
                    if(TextUtils.isEmpty(subText)){
                        return;
                    }
                    peripheral.addProperty("subScreenBrightness", subText);
                    break;


            }

            iDeviceService.sendAMCommandAsyn(new Gson().toJson(controlBean), new IAsyncCallback.Stub() {
                @Override
                public void onResult(String result) throws RemoteException {
                    toastMain(result);
                    Log.d(TAG, result);
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
        iDeviceService = null;
    }

}