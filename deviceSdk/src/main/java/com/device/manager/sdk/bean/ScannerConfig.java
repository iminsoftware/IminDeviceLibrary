package com.device.manager.sdk.bean;

import java.util.List;

/**
 * @author ：Chenjk
 * @version 1.0
 * @createTime ：2023/11/10 19:03
 **/
public class ScannerConfig {
    public Integer configVersion;
    public List<IdCode> id_code;
    public OutputEncode output_encode;
    public BarcodeData barcode_data;
    public OutputType output_type;
    public Sound sound;
    public String scannerType = "";

    public static final int OUTPUT_TYPE_KEYBOARD_NONE = 0; //无输出
    public static final int OUTPUT_TYPE_KEYBOARD_MONI = 1; //模拟键盘输出
    public static final int OUTPUT_TYPE_KEYBOARD_BLOCK = 2; //直接填充



    public static final String OUTPUT_TYPE_FILL_NONE = ""; //不填充
    public static final String OUTPUT_TYPE_FILL_TAB = "0"; //填充TAB
    public static final String OUTPUT_TYPE_FILL_ENTER = "1"; //填充回车
    public static final String OUTPUT_TYPE_FILL_SPACE = "2"; //填充空格
    public static final String OUTPUT_TYPE_REPLACE_ALL = "3"; //覆盖替换

    public static final int OUTPUT_TYPE_BROADCAST_OPEN = 1; //广播方式开启
    public static final int OUTPUT_TYPE_BROADCAST_CLOSE = 0; //广播方式关闭

    public static class OutputEncode {
        public String current;
        public String defaultX;
    }

    public static class BarcodeData {
        public Prefix prefix;
        public Suffix suffix;
        public RemoveChar remove_char;

        public static class Prefix {
            public boolean defaultX;
            public boolean current;
            public String text;
        }

        public static class Suffix {
            public boolean defaultX;
            public boolean current;
            public String text;
        }

        public static class RemoveChar {
            public boolean defaultX;
            public boolean current;
            public String currentPrefix;
            public String currentSuffix;
        }
    }

    public static class OutputType {
        public Keyboard keyboard;
        public Broadcast broadcast;

        public static class Keyboard {
            public int defaultX;
            public int current;
            public int interval;
            public String moniSupplementType;
            public String blockSupplementType;
        }

        public static class Broadcast {
            public int defaultX;
            public int current;
            public String defaultAction;
            public String defaultExtraStr;
            public String defaultExtraByte;
            public String currentAction;
            public String currentExtraStr;
            public String currentExtraByte;
            public boolean writeFactoryConfig;
        }
    }

    public static class IdCode {
        public String code;
        public String checkOrder;
        public String enableOrder;
        public String unableOrder;
        public String defaultX;
        public String current;
    }

    public static class Sound {
        public String setParamNoSound;
        public String highOrder;
        public String midOrder;
        public String lowOrder;
        public String muteOrder;
        public String defaultX;
        public String checkOrder;
        public String current;
    }
}
