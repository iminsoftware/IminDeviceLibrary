package com.example.imindevicelibrary.utils;



import com.example.imindevicelibrary.bean.DeviceInfoDisplayBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DeviceInfoUtils {
    public static final List<DeviceInfoDisplayBean> getDeviceInfoList(String data) {
        List<DeviceInfoDisplayBean> result = new ArrayList<>();
        if (data.startsWith("[") && data.contains("{")) {
            DeviceInfoDisplayBean info = new DeviceInfoDisplayBean();
            result = getAppInfo(data);
            return result;
        }
        // 将JSON字符串转换为JSONObject
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(data);
            // 创建一个空的HashMap
            Map<String, String> map = new HashMap<>();

            // 获取JSONObject中的所有键
            Iterator<String> keys = jsonObject.keys();

            // 将键值对存入map
            while (keys.hasNext()) {
                DeviceInfoDisplayBean info = new DeviceInfoDisplayBean();
                info.key = keys.next();
                //info.name = DeviceInfoDisplayUtils.getItemDisplay(info.key);
                info.name = info.key;
                info.valve = jsonObject.getString(info.key);
                if (info.valve.startsWith("[") && info.valve.contains("{")) {
                   // result.addAll(getAppInfo(info.valve));
                } else if (info.valve.startsWith("{")) {
                    info.datas = getItemObject(info.valve);
                }
                result.add(info);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<DeviceInfoDisplayBean> getAppInfo(String datas) {
        List<DeviceInfoDisplayBean> result = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(datas);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                try {
                    // 创建一个空的HashMap
                    Map<String, String> map = new HashMap<>();

                    // 获取JSONObject中的所有键
                    Iterator<String> keys = jsonObject.keys();

                    // 将键值对存入map
                    while (keys.hasNext()) {
                        DeviceInfoDisplayBean info = new DeviceInfoDisplayBean();
                        info.key = keys.next();
                        //info.name = DeviceInfoDisplayUtils.getItemDisplay(info.key);
                        info.name = info.key;
                        info.valve = jsonObject.getString(info.key);
                        if (info.valve.startsWith("[") && info.valve.contains("{")) {
                            // result.addAll(getAppInfo(info.valve));
                        } else if (info.valve.startsWith("{")) {
                            info.datas = getItemObject(info.valve);
                        }
                        result.add(info);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // 获取JSONObject中的所有键
                /*Iterator<String> keys = jsonObject.keys();
                DeviceInfoDisplayBean bean = new DeviceInfoDisplayBean();
                try {
                    bean.name = jsonObject.getString("displayName");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                bean.datas = new ArrayList<>();
                // 将键值对存入map
                while (keys.hasNext()) {
                    DeviceInfoDisplayBean.InfoBean info = new DeviceInfoDisplayBean.InfoBean();
                    info.key = keys.next();
                //    info.name = DeviceInfoDisplayUtils.getItemDisplay(info.key);
                    info.name = info.key;
                    info.valve = jsonObject.getString(info.key);
                    bean.datas.add(info);
                    if (TextUtils.isEmpty(bean.name)) {
                        bean.name = info.name;
                    }
                }
                result.add(bean);*/
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }



    public static List<DeviceInfoDisplayBean.InfoBean> getItemObject(String datas) {
        List<DeviceInfoDisplayBean.InfoBean> result = new ArrayList<>();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(datas);
            // 创建一个空的HashMap
            Map<String, String> map = new HashMap<>();

            // 获取JSONObject中的所有键
            Iterator<String> keys = jsonObject.keys();

            // 将键值对存入map
            while (keys.hasNext()) {
                DeviceInfoDisplayBean.InfoBean info = new DeviceInfoDisplayBean.InfoBean();
                info.key = keys.next();
              //  info.name = DeviceInfoDisplayUtils.getItemDisplay(info.key);
                info.name = info.key;
                info.valve = jsonObject.getString(info.key);
                result.add(info);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
