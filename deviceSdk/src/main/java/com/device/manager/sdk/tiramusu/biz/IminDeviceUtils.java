package com.device.manager.sdk.tiramusu.biz;

import android.text.TextUtils;
import android.util.Log;


/**
 * @Author: Mark
 * @date: 2024/5/29 Time：17:50
 * @description:
 */
public class IminDeviceUtils {

    public static final String TAG = "IminDeviceUtils";
    public static final String PRODUCT_NEO_MODEL = "persist.sys.neo_model";
    public static final String PRODUCT_W27 = "W27";
    public static final String PRODUCT_W28 = "W28";
    public static final String PRODUCT_MS1_15 = "MS1-15";
    public static final String PRODUCT_MS2_11 = "MS2-11";
    public static final String PRODUCT_MS2_12 = "MS2P";
    public static final String PRODUCT_SC1 = "SC1";
    public static final String PRODUCT_LARK_1 = "ML1";
    public static final String PRODUCT_TF2 = "TF2";
    public static final String PRODUCT_DS2 = "DS2";

    public static boolean hasPrinter() {

        String model = SystemPropManager.getSystemProperties(PRODUCT_NEO_MODEL);
        String sn = SystemPropManager.getSn();
        Log.d(TAG, "hasPrinter: model= " + model + ", sn= " + sn);

        if (!TextUtils.isEmpty(model)) {

            if (model.contains(PRODUCT_DS2)) {// DS2
                String substring = sn.substring(4, 5);
                // DS2-2x, DS2-3x 带打印机
                return substring.equals("2") || substring.equals("3");
            }
        }

        return false;
    }

    public static boolean isSupportCashBox(String pModel) {
        return pModel.contains(PRODUCT_W27) || pModel.contains(PRODUCT_W28) ||
                pModel.contains(PRODUCT_SC1) || pModel.contains(PRODUCT_TF2)
                || pModel.contains(PRODUCT_DS2);
    }
}
