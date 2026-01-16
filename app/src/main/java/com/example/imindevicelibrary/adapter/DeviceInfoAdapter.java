package com.example.imindevicelibrary.adapter;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.imindevicelibrary.R;
import com.example.imindevicelibrary.bean.DeviceInfoDisplayBean;

import java.util.List;

public class DeviceInfoAdapter extends BaseQuickAdapter<DeviceInfoDisplayBean, BaseViewHolder> {
    public DeviceInfoAdapter(List<DeviceInfoDisplayBean> data) {
        super(R.layout.item_device_info, data);
    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, DeviceInfoDisplayBean deviceInfoDisplayBean) {
        baseViewHolder.setText(R.id.tvName, deviceInfoDisplayBean.name);
        if (deviceInfoDisplayBean.datas != null && deviceInfoDisplayBean.datas.size() > 0) {
            baseViewHolder.setVisible(R.id.tvValue,false);
            RecyclerView recyclerView = baseViewHolder.getView(R.id.rvItem);
            recyclerView.setVisibility(View.GONE);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            DeviceInfoItemAdapter adapter = new DeviceInfoItemAdapter(deviceInfoDisplayBean.datas);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(adapter);
        }else {
            baseViewHolder.setText(R.id.tvValue, deviceInfoDisplayBean.valve).setVisible(R.id.tvValue,true);
            baseViewHolder.getView(R.id.rvItem).setVisibility(View.GONE);
        }
    }
}
