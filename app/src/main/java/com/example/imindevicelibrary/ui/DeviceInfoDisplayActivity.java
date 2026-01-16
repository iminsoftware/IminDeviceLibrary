package com.example.imindevicelibrary.ui;

import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.device.manager.sdk.DeviceManager;
import com.device.manager.server.aidl.IAsyncCallback;
import com.example.imindevicelibrary.R;
import com.example.imindevicelibrary.adapter.DeviceInfoAdapter;
import com.example.imindevicelibrary.bean.DeviceInfoDisplayBean;
import com.example.imindevicelibrary.utils.DeviceInfoUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeviceInfoDisplayActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = this.getClass().getName();
    private DeviceManager iDeviceService;
    private DeviceInfoAdapter adapter;
    private RecyclerView rvView;
    private List<DeviceInfoDisplayBean> mDatas = new ArrayList<>();
    private LinearLayout llProgress;
    private String infoTag, title;
    private TextView titles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info_display);
        iDeviceService = DeviceManager.getDeviceManager(this);
        infoTag = getIntent().getStringExtra("infoTag");
        if (TextUtils.isEmpty(infoTag)) {
            infoTag = "DEVICE_INFO_HW_INFO";
        }
        title = getIntent().getStringExtra("title");
        if (TextUtils.isEmpty(title)) {
            title = "DEVICE_INFO_HW_INFO";
        }
        initView();
        initData();
    }

    private void initView() {
        adapter = new DeviceInfoAdapter(new ArrayList<>());
        rvView = findViewById(R.id.rvView);
        llProgress = findViewById(R.id.llProgress);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvView.setLayoutManager(linearLayoutManager);
        rvView.setAdapter(adapter);
        titles = findViewById(R.id.titles);
    }

    private void initData() {
        titles.setText(title);
        if (!iDeviceService.isInitialized()) {
            toast("服务未绑定");
            return;
        }
        try {
            iDeviceService.getDeviceInfoAsyn(infoTag, new IAsyncCallback.Stub() {
                @Override
                public void onResult(String result) throws RemoteException {
                    Log.d(TAG, "result: "+result);

                    runOnUiThread(() -> {
                        if(TextUtils.isEmpty(result)){
                            toast("No data");
                        }
                        llProgress.setVisibility(View.GONE);
                        rvView.setVisibility(View.VISIBLE);
                        mDatas.clear();
                        mDatas.addAll(DeviceInfoUtils.getDeviceInfoList(result));
                        Collections.reverse(mDatas);
                        adapter.setNewData(mDatas);
                    });
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

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
