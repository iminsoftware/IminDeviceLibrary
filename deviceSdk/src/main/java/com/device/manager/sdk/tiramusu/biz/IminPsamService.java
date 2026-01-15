package com.device.manager.sdk.tiramusu.biz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * Psam sdk 调用
 */
public class IminPsamService {
    private static final String TAG = "IminPsamService";
    /**
     * 普通PSAM卡通道
     */
    public static final byte NORMAL_SLOT = 0x1;
    /**
     * 高速PSAM卡通道(City卡)
     */
    public static final byte FAST_SLOT = 0x41;
    /**
     * 指定卡片供电电压值 5V
     */
    public static final byte VCC_5V_MODE = 1;
    /**
     * 指定卡片供电电压值 3V
     */
    public static final byte VCC_3V_MODE = 2;
    /**
     * 指定卡片供电电压值 1.8V
     */
    public static final byte VCC_1P8V_MODE = 3;

    public static final byte[] SLOT_ARRAY = new byte[]{FAST_SLOT, NORMAL_SLOT};
    public static final byte[] MODE_ARRAY = new byte[]{VCC_5V_MODE, VCC_3V_MODE, VCC_1P8V_MODE};

    private volatile static IminPsamService mInstance = null;

    public static IminPsamService getInstance() {
        if (mInstance == null) {
            synchronized (IminPsamService.class) {
                if (mInstance == null) {
                    mInstance = new IminPsamService();
                }
            }
        }
        return mInstance;
    }

    /**
     * 卡片开启/复位
     *
     * @param slot    卡通道号
     *                NORMAL_SLOT－普通PSAM卡通道
     *                FAST_SLOT－高速PSAM卡通道(City卡)
     * @param vccMode 指定卡片供电电压值
     *                VCC_5V_MODE ---5V
     *                VCC_3V_MODE ---3V
     *                VCC_1P8V_MODE ---1.8V
     * @param ATR     卡片复位应答（至少需要32+1bytes的空间），
     *                其内容为长度(1字节)+复位应答内容
     * @return (0)      成功
     * (-1001)	发送错误
     * (-1002)	接收错误
     * (-2403)	通道号错误
     * (-2405)	卡拔出或无卡
     * (-2404)	协议错误
     * (-2500)	IC卡复位的电压模式错误
     * (-2503)	通信失败
     */
    public int openPsam(Context context, byte slot, byte vccMode, byte[] ATR) {
        try {
            @SuppressLint("WrongConstant")
            Object iMinServiceManager = context.getSystemService("iminservice");
            if (iMinServiceManager != null) {
                Class<?> c = Class.forName("android.app.iMinServiceManager");
                Method method = c.getMethod("iccOpen", int.class, int.class, byte[].class);
                Log.d(TAG, "iMinServiceManager is " + iMinServiceManager);
                Object obj = method.invoke(iMinServiceManager, slot, vccMode, ATR);
                Log.d(TAG, "openPsam: " + obj);
                if (obj instanceof Integer) {
                    return (Integer) obj;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -2503;
    }

    /**
     * 卡片关闭
     * @param slot 卡通道号
     *             NORMAL_SLOT－普通PSAM卡通道
     *             FAST_SLOT－高速PSAM卡通道(City卡)
     * @return
     * (0)    执行成功
     * (-1001)	发送错误
     * (-1002)	接收错误
     * 其它	失败
     *
     */
    public int closePsam(Context context, byte slot) {
        try {
            @SuppressLint("WrongConstant")
            Object iMinServiceManager = context.getSystemService("iminservice");
            if (iMinServiceManager != null) {
                Class<?> c = Class.forName("android.app.iMinServiceManager");
                Method method = c.getMethod("iccClose", int.class);
                Log.d(TAG, "iMinServiceManager is " + iMinServiceManager);
                Object obj = method.invoke(iMinServiceManager, slot);
                Log.d(TAG, "closePsam: " + obj);
                if (obj instanceof Integer) {
                    return (Integer) obj;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -2503;
    }

    /**
     *
     * @param slot 卡通道号
     *             NORMAL_SLOT－普通PSAM卡通道
     *             FAST_SLOT－高速PSAM卡通道(City卡)
     * @param apduSend 发送给卡片的apdu
     * @param apduRecv 接收到卡片返回的apdu
     * @return
     * (0)    成功
     * (-1001)	发送错误
     * (-1002)	接收错误
     * (-2503)	通信超时
     * (-2405)	交易中卡被拨出
     * (-2401)	奇偶错误
     * (-2403)	选择通道错误
     * (-2400)	发送数据太长（LC）
     * (-2404)	卡片协议错误（不为T＝0或T＝1）
     * (-2406)	没有复位卡片
     */
    public int commandPsam(Context context, byte slot,byte[] apduSend,
                           byte[] apduRecv) {
        try {
            @SuppressLint("WrongConstant")
            Object iMinServiceManager = context.getSystemService("iminservice");
            if (iMinServiceManager != null) {
                Class<?> c = Class.forName("android.app.iMinServiceManager");
                Method method = c.getMethod("iccCommand", int.class, byte[].class, byte[].class);
                Log.d(TAG, "iMinServiceManager is " + iMinServiceManager);
                Object obj = method.invoke(iMinServiceManager, slot, apduSend, apduRecv);
                Log.d(TAG, "openPsam: " + obj);
                if (obj instanceof Integer) {
                    return (Integer) obj;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -2503;
    }

    /**
     *
     * @param slot 卡通道号
     *             NORMAL_SLOT－普通PSAM卡通道
     *             FAST_SLOT－高速PSAM卡通道(City卡)
     * @param apduSend 发送给卡片的apdu
     * @param apduRecv 接收到卡片返回的apdu
     * @return
     * (0)    成功
     * (-1001)	发送错误
     * (-1002)	接收错误
     * (-2503)	通信超时
     * (-2405)	交易中卡被拨出
     * (-2401)	奇偶错误
     * (-2403)	选择通道错误
     * (-2400)	发送数据太长（LC）
     * (-2404)	卡片协议错误（不为T＝0或T＝1）
     * (-2406)	没有复位卡片
     */
    public int commandPsamNew(Context context, byte slot,byte[] apduSend,
                           byte[] apduRecv) {
        try {
            @SuppressLint("WrongConstant")
            Object iMinServiceManager = context.getSystemService("iminservice");
            if (iMinServiceManager != null) {
                Class<?> c = Class.forName("android.app.iMinServiceManager");
                Method method = c.getMethod("iccCommandNew", int.class, byte[].class, byte[].class);
                Log.d(TAG, "iMinServiceManager is " + iMinServiceManager);
                Object obj = method.invoke(iMinServiceManager, slot, apduSend, apduRecv);
                Log.d(TAG, "openPsam: " + obj);
                if (obj instanceof Integer) {
                    return (Integer) obj;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -2503;
    }


    public int iccDevParaSet(Context context, byte slot,byte clkSel, byte mode,
                           byte pps) {
        try {
            @SuppressLint("WrongConstant")
            Object iMinServiceManager = context.getSystemService("iminservice");
            if (iMinServiceManager != null) {
                Class<?> c = Class.forName("android.app.iMinServiceManager");
                Method method = c.getMethod("iccDevParaSet", int.class, int.class, int.class, int.class);
                Log.d(TAG, "iMinServiceManager is " + iMinServiceManager);
                Object obj = method.invoke(iMinServiceManager, slot, clkSel, mode, pps);
                Log.d(TAG, "openPsam: " + obj);
                if (obj instanceof Integer) {
                    return (Integer) obj;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -2503;
    }
}
