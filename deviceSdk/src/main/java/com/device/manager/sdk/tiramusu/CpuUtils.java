package com.device.manager.sdk.tiramusu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.device.manager.sdk.bean.CPUDetail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CpuUtils {

    public static final String SOC_MANUFACTURER = "ro.soc.manufacturer";
    public static final String TAG = "CpuUtils";
    public static final String SOC_AW = "Allwinner";
    public static Map<String, String> getCpuInfo() {
        Map<String, String> cpuInfo = new HashMap<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader("/proc/cpuinfo"));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("Hardware")) {
                    cpuInfo.put("model", line.split(":\\s+", 2)[1]);
                }
                if (line.startsWith("CPU revision")) {
                    cpuInfo.put("revision", line.split(":\\s+", 2)[1]);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cpuInfo;
    }

    private static int getCpuCoreCount() {
        try {
            File dir = new File("/sys/devices/system/cpu/");
            File[] files = dir.listFiles((file, name) -> name.matches("cpu[0-9]+"));
            if (files != null) {
                return files.length;
            } else {
                return Runtime.getRuntime().availableProcessors();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Runtime.getRuntime().availableProcessors();
        }
    }

    private static Double readFrequencyFromFile(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                String content = new String(Files.readAllBytes(Paths.get(path))).trim();
                return Double.parseDouble(content);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    private static String getMaxCpuFreq() {
        int coreCount = getCpuCoreCount();
        Double maxFreq = null;

        for (int i = 0; i < coreCount; i++) {
            String maxFreqPath = "/sys/devices/system/cpu/cpu" + i + "/cpufreq/cpuinfo_max_freq";
            Double freq = readFrequencyFromFile(maxFreqPath);
            if (freq != null && (maxFreq == null || freq > maxFreq)) {
                maxFreq = freq;
            }
        }
        if (maxFreq == null) {
            return "";
        }
        double cpuMaxFreq = maxFreq / 1000 / 1000;
        DecimalFormat df = new DecimalFormat("#.0");
        String cpuFreq = df.format(cpuMaxFreq) + "GHz";
        Log.d(TAG, "getCpuMaxFreq: maxFreq= " + maxFreq
                + "\ncpuFreq=" + cpuFreq
        );


        return cpuFreq;
    }

    public static String getminfreq(int cpu) {
        String result = "";
        try {
            FileReader fr = new FileReader("/sys/devices/system/cpu/cpu" + cpu + "/cpufreq/cpuinfo_min_freq");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            result = text.trim();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static String getmaxfreq(int cpu) {
        String result = "";
        try {
            FileReader fr = new FileReader("/sys/devices/system/cpu/cpu" + cpu + "/cpufreq/cpuinfo_max_freq");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            result = text.trim();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static float getCpuTemperature() {
        try {
            String filePath = "/sys/class/thermal/thermal_zone0/temp";
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line = br.readLine();
            if (line != null) {
                float temperatureCelsius = Float.parseFloat(line) / 1000; // 转换为摄氏度
                Log.d(TAG,"getCpuTemperature temperatureCelsius: " + temperatureCelsius);
                return temperatureCelsius;
            }
        } catch (Exception e) {
            Log.d(TAG,"getCpuTemperature error: " + e.getMessage());
            e.printStackTrace();
        }
        return 0.0f;
    }

    public static List<CPUDetail.CPUItem> getCpuClusters(Context context) {
        int cpuIndex = 0;
        List<CPUDetail.CPUItem> cpus = new ArrayList<>();
        while (true) {
            String path = "/sys/devices/system/cpu/cpu" + cpuIndex + "/topology/physical_package_id";
            try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                String line = br.readLine();

                CPUDetail.CPUItem cpuItem = new CPUDetail.CPUItem();
                cpuItem.frequency = getCurrentCpuFreq(cpuIndex);
                cpuItem.minfreq = getminfreq(cpuIndex);
                cpuItem.maxfreq = getmaxfreq(cpuIndex);
                cpuItem.frequencyList = getfrequencyList(cpuIndex);
              //  cpuItem.mode = getmode(cpuIndex,context);
                cpuItem.modeList = getmodeList(cpuIndex);
                cpuItem.online = getonline(cpuIndex);
                cpus.add(cpuItem);
            } catch (IOException e) {
                // Break the loop if no more CPU information is available
                break;
            }
            cpuIndex++;
        }
        return cpus;
    }


    private static String getonline(int cpu) {
        String result = "";
        try {
            FileReader fr = new FileReader("/sys/devices/system/cpu/cpu" + cpu + "/online");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            result = text.trim();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String getmodeList(int cpu) {
        String result = "";
        try {
            FileReader fr = new FileReader("/sys/devices/system/cpu/cpu" + cpu + "/cpufreq/scaling_available_governors");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            result = text.trim();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("getCurrentCpuFreq", result);
        return result;
    }



    public static String getfrequencyList(int cpu) {
        String result = "";
        try {
            FileReader fr = new FileReader("/sys/devices/system/cpu/cpu" + cpu + "/cpufreq/scaling_available_frequencies");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            result = text.trim();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static String getCPURateDesc_All() {
        String path = "/proc/stat";// 系统CPU信息文件
        long totalJiffies[] = new long[2];
        long totalIdle[] = new long[2];
        int firstCPUNum = 0;//设置这个参数，这要是防止两次读取文件获知的CPU数量不同，导致不能计算。这里统一以第一次的CPU数量为基准
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        Pattern pattern = Pattern.compile(" [0-9]+");
        for (int i = 0; i < 2; i++) {
            totalJiffies[i] = 0;
            totalIdle[i] = 0;
            try {
                fileReader = new FileReader(path);
                bufferedReader = new BufferedReader(fileReader, 8192);
                int currentCPUNum = 0;
                String str;
                while ((str = bufferedReader.readLine()) != null && (i == 0 || currentCPUNum < firstCPUNum)) {
                    if (str.toLowerCase().startsWith("cpu")) {
                        currentCPUNum++;
                        int index = 0;
                        Matcher matcher = pattern.matcher(str);
                        while (matcher.find()) {
                            try {
                                long tempJiffies = Long.parseLong(matcher.group(0).trim());
                                totalJiffies[i] += tempJiffies;
                                if (index == 3) {//空闲时间为该行第4条栏目
                                    totalIdle[i] += tempJiffies;
                                }
                                index++;
                            } catch (NumberFormatException e) {
                                e.printStackTrace();

                            }
                        }
                    }
                    if (i == 0) {
                        firstCPUNum = currentCPUNum;
                        try {//暂停50毫秒，等待系统更新信息。
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();

                        }
                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }
            }
        }
        double rate = -1;
        if (totalJiffies[0] > 0 && totalJiffies[1] > 0 && totalJiffies[0] != totalJiffies[1]) {
            rate = 1.0 * ((totalJiffies[1] - totalIdle[1]) - (totalJiffies[0] - totalIdle[0])) / (totalJiffies[1] - totalJiffies[0]);
        }

        Log.d(TAG, "getCPURateDesc_All CpuUtils " + "zrx---- cpu_rate:" + rate);
        if (rate <= 0) {
            Log.d(TAG, "getCPURateDesc_All" + "cpu_rate is < 0: " + rate);
            return 1 + "%";
        }
        long a = Math.round(rate * 100);
        Log.d(TAG, "getCPURateDesc_All " + " rate :" + a);
        if (a <= 0) {
            a = 1;
        } else if (a >= 100) {
            a = 99;
        }
        return a + "%";
    }

    public static String getCurrentCpuFreq(int cpu) {
        String result = "";
        try {
            FileReader fr = new FileReader("/sys/devices/system/cpu/cpu" + cpu + "/cpufreq/scaling_cur_freq");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            result = text.trim();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获得CPU最大赫兹
     */
    public static String getCpuMaxFreq() {
        String maxCpuFreq = getMaxCpuFreq();
        if (!TextUtils.isEmpty(maxCpuFreq)) {
            return maxCpuFreq;
        }
        String cpuFreq = "";
        /*String cpuFreq = SystemPropManager.getCpuMaxFreq();

        if (!TextUtils.isEmpty(cpuFreq) && !"0".equals(cpuFreq)) {

            return cpuFreq;
        }*/
        try {
            String manufacturer = getSystemProperties(SOC_MANUFACTURER);
            Log.d(TAG, "getCpuMaxFreq: manufacturer= " + manufacturer);
            String cpuMaxFreqPathFile = "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq";
            if (manufacturer.equalsIgnoreCase(SOC_AW)) {
                cpuMaxFreqPathFile = "/sys/devices/system/cpu/cpu7/cpufreq/cpuinfo_max_freq";
            }
            BufferedReader br = new BufferedReader(new FileReader(cpuMaxFreqPathFile));
            String text;
            while ((text = br.readLine()) != null) {
                text = text.trim();
                if (!"".equals(text.trim())) {
                    double cpuMaxFreq = Double.parseDouble(text.trim()) / 1000 / 1000;
                    DecimalFormat df = new DecimalFormat("#.0");
                    cpuFreq = df.format(cpuMaxFreq);
                }
                break;
            }
            br.close();
            br = null;
        } catch (Exception e) {

            e.printStackTrace();
        }

        return cpuFreq + "GHz";
    }

    private static String getSystemProperties(String property) {
        String value ="xxx";
        try {
            Class clazz = Class.forName("android.os.SystemProperties");
            Method getter = clazz.getDeclaredMethod("get", String.class);
            value = (String) getter.invoke(null, property);
        } catch (Exception e) {
            Log.d(TAG, "Unable to read system properties");
        }
        return value;
    }

    public static String getmode(int cpu, Context context) {
        String result = "";
        /*try {
            //FileReader fr = new FileReader("/sys/devices/system/cpu/cpu" + cpu + "/cpufreq/scaling_governor");
            FileReader fr = new FileReader("/sys/devices/system/cpu/cpufreq/policy0/scaling_governor");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            result = text.trim();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        try {
            result = (String) opIminServiceNoParas(context,"getCpuModel", null, null);
        } catch (Exception e) {
            e.printStackTrace();
          //  Log.e(TAG,"getCpuModel error: " + e.getMessage());
        }
        return result;
    }

    public static Object opIminServiceNoParas(Context context, String methodStr, Class<?>[] parameterTypes, Object[] params) {
        Object obj = null;
        try {
            @SuppressLint("WrongConstant")
            Object iMinServiceManager = context.getSystemService("iminservice");
            if (iMinServiceManager != null) {
                Class<?> c = Class.forName("android.app.iMinServiceManager");
                Method method = null;
                if (parameterTypes == null) {
                    method = c.getMethod(methodStr);
                } else {
                    method = c.getMethod(methodStr, parameterTypes);
                }

                if (params == null) {
                    obj = method.invoke(iMinServiceManager);
                } else {
                    obj = method.invoke(iMinServiceManager, params);
                }

                return obj;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
    }
}
