package com.example.imindevicelibrary.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.imindevicelibrary.R;
import com.example.imindevicelibrary.bean.DeviceInfoDisplayBean;

import java.util.List;

public class DeviceInfoItemAdapter extends BaseQuickAdapter<DeviceInfoDisplayBean.InfoBean, BaseViewHolder> {
    public DeviceInfoItemAdapter(List<DeviceInfoDisplayBean.InfoBean> data) {
        super(R.layout.item_device_item, data);
    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, DeviceInfoDisplayBean.InfoBean deviceInfoDisplayBean) {
        baseViewHolder.setText(R.id.tvName, deviceInfoDisplayBean.name).setText(R.id.tvValue, deviceInfoDisplayBean.valve)
                .setText(R.id.tvKey, deviceInfoDisplayBean.key);;
    }
}
