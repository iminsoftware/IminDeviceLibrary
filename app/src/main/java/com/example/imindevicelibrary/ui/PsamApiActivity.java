package com.example.imindevicelibrary.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.device.manager.sdk.DeviceManager;
import com.device.manager.sdk.tiramusu.biz.ByteUtil;
import com.example.imindevicelibrary.R;

import java.util.Arrays;


public class PsamApiActivity extends AppCompatActivity implements View.OnClickListener {
    private DeviceManager iDeviceService;
    private static final String TAG = "PsamApiActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psam_api);
        iDeviceService = DeviceManager.getDeviceManager(this);
        initListener();

    }


    private void initListener() {

        findViewById(R.id.a13PsamTest).setOnClickListener(this);

    }


    @SuppressLint("DefaultLocale")
    @Override
    public void onClick(View v) {
        if (!iDeviceService.isInitialized()) {
            toast("服务未绑定");
            return;
        }

        switch (v.getId()) {
            case R.id.a13PsamTest:
                try {
                    psamTest();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG,"psamTest: "+ e.getMessage());
                }
                return;
        }


    }

    private boolean testing = false;
    public void psamTest() {
        if (testing)
            return;
        testing = true;
        Toast.makeText(this, "reading...", Toast.LENGTH_SHORT).show();
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String readResult = readPsam();
                    toastMain(readResult);
                    testing = false;
                }catch (Throwable e){
                    e.printStackTrace();
                }
            }
        }.start();

    }

    public String readPsam() {
        Log.d(TAG, "readPsam..");
        int iRet = -1;
        for (int i = 0; i < 9; i++) {
            byte[] atr = new byte[40];
            byte[] apduSend = new byte[600];
            byte[] apduRecv = new byte[600];
            byte slot = 0x01;
            if (i / 3 == 0) {
                iDeviceService.iccDevParaSet(PsamApiActivity.this, slot, (byte) 0, (byte) 0, (byte) 0);
                iRet = iDeviceService.openPsam(PsamApiActivity.this, slot, (byte) (i % 3 + 1), atr);
            } else if (i / 3 == 1) {
                iDeviceService.iccDevParaSet(PsamApiActivity.this, slot, (byte) 0, (byte) 0, (byte) 0);
                iRet = iDeviceService.openPsam(PsamApiActivity.this, (byte) (slot | 0x40), (byte) (i % 3 + 1), atr);
            } else if (i / 3 == 2) {
                iDeviceService.iccDevParaSet(PsamApiActivity.this, slot, (byte) 1, (byte) 1, (byte) 12);
                iRet = iDeviceService.openPsam(PsamApiActivity.this, (byte) slot, (byte) (i % 3 + 1), atr);
            }
            if (iRet == 0)
                Log.e(TAG, "icc ICC reset (" + i + ")OK,atr:" + ByteUtil.bytearrayToHexString(atr, atr[0]) + "\r\n");
            else {
                Log.e(TAG, "icc ICC reset failed(" + i + "), return value:" + Integer.toString(iRet) + "\r\n");
                iDeviceService.closePsam(PsamApiActivity.this, slot);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                continue;
            }

            apduSend[0] = (byte) 0x00;
            apduSend[1] = (byte) 0xa4;
            apduSend[2] = (byte) 0x04;
            apduSend[3] = (byte) 0x00;
            apduSend[4] = (byte) 0x00;
            apduSend[5] = (byte) 0x0e;

            System.arraycopy("1PAY.SYS.DDF01".getBytes(), 0, apduSend, 6, 14);
            apduSend[6 + 14] = (byte) 0x01;
            apduSend[7 + 14] = (byte) 0x00;
            Log.d(TAG, "apduSend: " + Arrays.toString(apduSend));
            iRet = iDeviceService.commandPsamNew(PsamApiActivity.this, slot, apduSend, apduRecv);
            if (iRet == 0) {
                int lenout = apduRecv[0] * 256 + apduRecv[1];
                byte[] dataout = new byte[512];
                byte[] sw = new byte[2];

                Log.e(TAG, "icc Command Exchange Success" + "\r\n");
                Log.e(TAG, "icc datalen:" + Integer.toString(lenout) + "\r\n");
                System.arraycopy(apduRecv, 2, dataout, 0, lenout);
                String dataOutStr = ByteUtil.bytearrayToHexString(dataout, lenout);
                Log.e(TAG, "icc dataout:" + dataOutStr + "\r\n");
                System.arraycopy(apduRecv, 2 + lenout, sw, 0, 2);
                Log.e(TAG, "icc SWA,SWB:" + ByteUtil.bytearrayToHexString(sw, 2) + "\r\n");
                iDeviceService.closePsam(PsamApiActivity.this, slot);
                return "ICC Check successful:" + dataOutStr;
            } else {
                Log.e(TAG, "icc Command Exchange fail(" + i + "),return value:" + Integer.toString(iRet) + "\r\n");
                iDeviceService.closePsam(PsamApiActivity.this, slot);
            }
        }
        return "ICC Check failed, result:" + iRet;
    }



    private void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void toastMain(String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(PsamApiActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iDeviceService = null;
    }

}