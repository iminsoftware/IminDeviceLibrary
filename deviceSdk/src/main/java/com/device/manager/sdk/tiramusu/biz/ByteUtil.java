package com.device.manager.sdk.tiramusu.biz;

import android.app.Instrumentation;
import android.view.KeyEvent;

public class ByteUtil {
    private ByteUtil() {
        System.out.println("ByteUtil Constructor");
    }

    public static String byteArraytoString(byte[] b,int len,int offset){
    	byte data[] = new byte[len];
    	System.arraycopy(b, offset, data, 0, len);
    	
		return new String(data);
    	
    }
    public static String bytearrayToHexStringNoSpace(byte[] b, int leng) {
        StringBuffer strbuf = new StringBuffer();

        for (int i = 0; i < leng; i++) {
            strbuf.append("0123456789ABCDEF".charAt(((byte) ((b[i] & 0xf0) >> 4))));
            strbuf.append("0123456789ABCDEF".charAt((byte) (b[i] & 0x0f)));
        }
        return strbuf.toString();
    }
    public static String bytesToString(byte[] b) {
        StringBuffer result = new StringBuffer("");
        int length = b.length;

        for (int i = 0; i < length; i++) {
            char ch = (char)(b[i] & 0xff);
            if (ch == 0) {
                break;
            }

            result.append(ch);
        }
        return result.toString();
    }
    public static String bytearrayToHexStringWithSpace(byte[] b, int leng) {
        StringBuffer strbuf = new StringBuffer();

        for (int i = 0; i < leng; i++) {
            strbuf.append("0123456789ABCDEF".charAt(((byte) ((b[i] & 0xf0) >> 4))));
            strbuf.append("0123456789ABCDEF".charAt((byte) (b[i] & 0x0f)));
            strbuf.append(" ");
        }
        return strbuf.toString();
    }
    
    public static String bytearrayToHexString(byte[] b, int leng) {
        StringBuffer strbuf = new StringBuffer();

        for (int i = 0; i < leng; i++) {
            strbuf.append("0123456789ABCDEF".charAt(((byte) ((b[i] & 0xf0) >> 4))));
            strbuf.append("0123456789ABCDEF".charAt((byte) (b[i] & 0x0f)));
        }
        return strbuf.toString();
    }

    public static byte[] stringToBytes(String s) {
        return s.getBytes();
    }

    public static void ShortToBytes(byte[] b, short x, int offset) {
        //byte[] b = new byte[2];

        if (b.length-offset >= 2) {
            b[offset + 1] = (byte) (x >> 8);
            b[offset + 0] = (byte) (x >> 0);
        }

        //return b;
    }

    public static short BytesToShort(byte[] b, int offset) {
        short x = 0;
        if (b.length-offset >= 2) {
            x = (short) (((b[offset + 1] << 8) | b[offset + 0] & 0xff));
        }

        return x;
    }

    public static String byteToHexString(byte b) {

        StringBuffer sbBuffer = new StringBuffer();
        sbBuffer.append("0123456789ABCDEF".charAt((int) ((b >> 4) & 0x0F)));
        sbBuffer.append("0123456789ABCDEF".charAt((int) (b & 0x0F)));
        return sbBuffer.toString();
    }
    public static String byteToCharString(byte b) {

        StringBuffer sbBuffer = new StringBuffer();
        sbBuffer.append((char)b);
        return sbBuffer.toString();
    }
    public static void IntToBytes(byte[] b, int x, int offset) {
        //byte[] b = new byte[4];

        if (b.length-offset >= 4) {
            b[offset + 3] = (byte) (x >> 24);
            b[offset + 2] = (byte) (x >> 16);
            b[offset + 1] = (byte) (x >> 8);
            b[offset + 0] = (byte) (x >> 0);
        }

    }

    public static int BytesToInt(byte[] b, int offset) {
        int x = 0;
        if (b.length-offset >= 4) {
            x = (int) ((((b[offset + 3] & 0xff) << 24)
                    | ((b[offset + 2] & 0xff) << 16)
                    | ((b[offset + 1] & 0xff) << 8) | ((b[offset + 0] & 0xff) << 0)));
        }
        return x;
    }

    public static void LongToBytes(byte[] b, long x, int offset) {
        if (b.length-offset >= 8) {
            b[offset + 7] = (byte) (x >> 56);
            b[offset + 6] = (byte) (x >> 48);
            b[offset + 5] = (byte) (x >> 40);
            b[offset + 4] = (byte) (x >> 32);
            b[offset + 3] = (byte) (x >> 24);
            b[offset + 2] = (byte) (x >> 16);
            b[offset + 1] = (byte) (x >> 8);
            b[offset + 0] = (byte) (x >> 0);
        }

        //return b;
    }

    public static long BytesToLong(byte[] b, int offset) {
        long x = 0;
        if (b.length-offset >= 8) {
            x = ((((long) b[offset + 7] & 0xff) << 56)
                    | (((long) b[offset + 6] & 0xff) << 48)
                    | (((long) b[offset + 5] & 0xff) << 40)
                    | (((long) b[offset + 4] & 0xff) << 32)
                    | (((long) b[offset + 3] & 0xff) << 24)
                    | (((long) b[offset + 2] & 0xff) << 16)
                    | (((long) b[offset + 1] & 0xff) << 8) | (((long) b[offset + 0] & 0xff) << 0));
        }

        return x;
    }

    public static void CharToBytes(byte[] b, char ch, int offset) {
        // byte[] b = new byte[2];

        if (b.length-offset >= 2) {
            int temp = (int) ch;
            for (int i = 0; i < 2; i ++ ) {
                b[offset + i] = new Integer(temp & 0xff).byteValue();
                temp = temp >> 8;
            }
        }
    }

    public static char BytesToChar(byte[] b, int offset) {
        int s = 0;

        if (b.length-offset >= 2) {
            if (b[offset + 1] > 0)
                s += b[offset + 1];
            else
                s += 256 + b[offset + 0];
            s *= 256;
            if (b[offset + 0] > 0)
                s += b[offset + 1];
            else
                s += 256 + b[offset + 0];
        }

        char ch = (char) s;
        return ch;
    }

    public static void FloatToBytes(byte[] b, float x, int offset) {
        if (b.length-offset >= 4) {
            int l = Float.floatToIntBits(x);
            for (int i = 0; i < 4; i++) {
                b[offset + i] = new Integer(l).byteValue();
                l = l >> 8;
            }
        }
    }

    public static float BytesToFloat(byte[] b, int offset) {
        int l = 0;

        if (b.length-offset >= 4) {
            l = b[offset + 0];
            l &= 0xff;
            l |= ((long) b[offset + 1] << 8);
            l &= 0xffff;
            l |= ((long) b[offset + 2] << 16);
            l &= 0xffffff;
            l |= ((long) b[offset + 3] << 24);
        }

        return Float.intBitsToFloat(l);
    }

    public static void DoubleToBytes(byte[] b, double x, int offset) {
        //byte[] b = new byte[8];

        if (b.length-offset >= 8) {
            long l = Double.doubleToLongBits(x);
            for (int i = 0; i < 4; i++) {
                b[offset + i] = new Long(l).byteValue();
                l = l >> 8;
            }
        }

        //return b;
    }

    public static double BytesToDouble(byte[] b, int offset) {
        long l = 0;

        if (b.length-offset >= 8) {
            l = b[0];
            l &= 0xff;
            l |= ((long) b[1] << 8);
            l &= 0xffff;
            l |= ((long) b[2] << 16);
            l &= 0xffffff;
            l |= ((long) b[3] << 24);
            l &= 0xffffffffl;
            l |= ((long) b[4] << 32);
            l &= 0xffffffffffl;
            l |= ((long) b[5] << 40);
            l &= 0xffffffffffffl;
            l |= ((long) b[6] << 48);
            l &= 0xffffffffffffffl;
            l |= ((long) b[7] << 56);
        }

        return Double.longBitsToDouble(l);
    }
    public static short toLH(short n) {
        byte[] b = new byte[2];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);

        short ret = BytesToShort(b, 0);
        return ret;
    }

    public static short toHL(short n) {
        byte[] b = new byte[2];
        b[1] = (byte) (n & 0xff);
        b[0] = (byte) (n >> 8 & 0xff);

        short ret = BytesToShort(b, 0);
        return ret;
    }

    public static int toLH(int n) {
        byte[] b = new byte[4];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        b[2] = (byte) (n >> 16 & 0xff);
        b[3] = (byte) (n >> 24 & 0xff);

        int ret = BytesToInt(b, 0);
        return ret;
    }

    public static int toHL(int n) {
        byte[] b = new byte[4];
        b[3] = (byte) (n & 0xff);
        b[2] = (byte) (n >> 8 & 0xff);
        b[1] = (byte) (n >> 16 & 0xff);
        b[0] = (byte) (n >> 24 & 0xff);

        int ret = BytesToInt(b, 0);
        return ret;
    }

    public static long toLH(long n) {
        byte[] b = new byte[8];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        b[2] = (byte) (n >> 16 & 0xff);
        b[3] = (byte) (n >> 24 & 0xff);
        b[4] = (byte) (n >> 32 & 0xff);
        b[5] = (byte) (n >> 40 & 0xff);
        b[6] = (byte) (n >> 48 & 0xff);
        b[7] = (byte) (n >> 56 & 0xff);

        long ret = BytesToLong(b, 0);
        return ret;
    }


    public static long toHL(long n) {
        byte[] b = new byte[8];
        b[7] = (byte) (n & 0xff);
        b[6] = (byte) (n >> 8 & 0xff);
        b[5] = (byte) (n >> 16 & 0xff);
        b[4] = (byte) (n >> 24 & 0xff);
        b[3] = (byte) (n >> 32 & 0xff);
        b[2] = (byte) (n >> 40 & 0xff);
        b[1] = (byte) (n >> 48 & 0xff);
        b[0] = (byte) (n >> 56 & 0xff);

        long ret = BytesToLong(b, 0);
        return ret;
    }


    public static void encodeOutputBytes(byte[] b, short sLen) {
        if (b.length >= sLen+2) {
            System.arraycopy(b, 0, b, 2, sLen);
            byte[] byShort = new byte[2];
            ShortToBytes(byShort, sLen, 0);
            System.arraycopy(byShort, 0, b, 0, byShort.length);
        }
    }


    public static short decodeOutputBytes(byte[] b) {
        byte[] byShort = new byte[2];
        System.arraycopy(b, 0, byShort, 0, byShort.length);
        short sLen = BytesToShort(byShort, 0);

        System.arraycopy(b, 2, b, 0, sLen);

        return sLen;
    }
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));

        }
        return d;
    }
    
//    public static int CheckBCD(byte str)
//    {
//        byte ch;
//        ch=(byte) (str & 0x0f);
//        if(ch>=0x0a) return ReturnValue.TIME_FORMAT_ERR;
//        ch=(byte) (str & 0xf0);
//        if(ch>=0xa0) return ReturnValue.TIME_FORMAT_ERR;
//        return 0;
//    }
//
//    public static int CheckTime(byte []str)
//    {
//        int ret=0;
//
//        if(CheckBCD(str[0])!=0) return ReturnValue.TIME_YEAR_ERR;        
//        if(CheckBCD(str[1])!=0) return ReturnValue.TIME_MONTH_ERR;        
//        if(CheckBCD(str[2])!=0) return ReturnValue.TIME_DAY_ERR;          
//        if(CheckBCD(str[3])!=0) return ReturnValue.TIME_HOUR_ERR;         
//        if(CheckBCD(str[4])!=0) return ReturnValue.TIME_MINUTE_ERR;       
//        if(CheckBCD(str[5])!=0) return ReturnValue.TIME_SECOND_ERR;       
//
//        if(str[1]==0 || str[1]>0x12) return ReturnValue.TIME_MONTH_ERR; 
//        if(str[2]==0 || str[2]>0x31) return ReturnValue.TIME_DAY_ERR;  
//        if(str[3]>0x23) return ReturnValue.TIME_HOUR_ERR;              
//        if(str[4]>0x59) return ReturnValue.TIME_MINUTE_ERR;           
//        if(str[5]>0x59) return ReturnValue.TIME_SECOND_ERR;           
//
//        return ret;
//    }

    public static  int keyReport(int keyValue){
		int keycode = 0;
		String upKeycode = "";
		int up = 0;
		switch(keyValue){
		case 32:
			keycode = KeyEvent.KEYCODE_SPACE;
			break;
		case 33:
			up = 65;
			upKeycode = "!";
			break;
		case 34:
			up = 65;
			upKeycode = "\"";
			break;
		case 35:
			keycode = KeyEvent.KEYCODE_POUND;
			break;
		case 36:
			up = 65;
			upKeycode = "$";
			break;
		case 37:
			up = 65;
			upKeycode = "%";
			break;
		case 38:
			up = 65;
			upKeycode = "&";
			break;
		case 39:
			up = 65;
			upKeycode = "`";
			break;
		case 40:
			up = 65;
			upKeycode = "(";
			break;
		case 41:
			up = 65;
			upKeycode = ")";
			break;
	
		case 42:
			keycode = KeyEvent.KEYCODE_STAR;
			break;
		case 43:
			keycode = KeyEvent.KEYCODE_PLUS;
			break;
		case 44:
			keycode = KeyEvent.KEYCODE_COMMA;
			break;
		case 45:
			keycode = KeyEvent.KEYCODE_MINUS;
			break;
		case 46:
			keycode = KeyEvent.KEYCODE_PERIOD;
			break;
		case 47:
			keycode = KeyEvent.KEYCODE_SLASH;
			break;
		case 48://0
			keycode = KeyEvent.KEYCODE_0;
			break;
		case 49:
			keycode = KeyEvent.KEYCODE_1;
			break;
		case 50:
			keycode = KeyEvent.KEYCODE_2;
			break;
		case 51:
			keycode = KeyEvent.KEYCODE_3;
			break;
		case 52:
			keycode = KeyEvent.KEYCODE_4;
			break;
		case 53:
			keycode = KeyEvent.KEYCODE_5;
			break;
		case 54:
			keycode = KeyEvent.KEYCODE_6;
			break;
		case 55:
			keycode = KeyEvent.KEYCODE_7;
			break;
		case 56:
			keycode = KeyEvent.KEYCODE_8;
			break;
		case 57://9
			keycode = KeyEvent.KEYCODE_9;
			break;
		case 58://:
			up = 65;
			upKeycode = ":";
			break;
		case 59:
			keycode = KeyEvent.KEYCODE_SEMICOLON;
			break;
		case 60:
			up = 65;
			upKeycode = "<";
			break;
		case 61:
			keycode = KeyEvent.KEYCODE_EQUALS;
			break;
		case 62:
			up = 65;
			upKeycode = ">";
			break;
		case 63:
			up = 65;
			upKeycode = "?";
			break;
		case 64:
			keycode = KeyEvent.KEYCODE_AT;
			break;
		case 65:
			up = 65;
			upKeycode = "A";
			break;
		case 97:
			keycode = KeyEvent.KEYCODE_A;
			break;
		case 66:
			up = 66;
			upKeycode = "B";
			break;
		case 98:
			keycode = KeyEvent.KEYCODE_B;
			break;
		case 67:
			up = 67;
			upKeycode = "C";
			break;
		case 99:
			keycode = KeyEvent.KEYCODE_C;
			break;
		case 68:
			up = 68;
			upKeycode = "D";
			break;
		case 100:
			keycode = KeyEvent.KEYCODE_D;
			break;
		case 69:
			up = 69;
			upKeycode = "E";
			break;
		case 101:
			keycode = KeyEvent.KEYCODE_E;
			break;
		case 70:
			up = 70;
			upKeycode = "F";
			break;
		case 102:
			keycode = KeyEvent.KEYCODE_F;
			break;
		case 71:
			up = 71;
			upKeycode = "G";
			break;
		case 103:
			keycode = KeyEvent.KEYCODE_G;
			break;
		case 72:
			up = 72;
			upKeycode = "H";
			break;
		case 104:
			keycode = KeyEvent.KEYCODE_H;
			break;
		case 73:
			up = 73;
			upKeycode = "I";
			break;
		case 105:
			keycode = KeyEvent.KEYCODE_I;
			break;
		case 74:
			up = 74;
			upKeycode = "J";
			break;
		case 106:
			keycode = KeyEvent.KEYCODE_J;
			break;
		case 75:
			up = 75;
			upKeycode = "K";
			break;
		case 107:
			keycode = KeyEvent.KEYCODE_K;
			break;
		case 76:
			up = 76;
			upKeycode = "L";
			break;
		case 108:
			keycode = KeyEvent.KEYCODE_L;
			break;
		case 77:
			up = 77;
			upKeycode = "M";
			break;
		case 109:
			keycode = KeyEvent.KEYCODE_M;
			break;
		case 78:
			up = 78;
			upKeycode = "N";
			break;
		case 110:
			keycode = KeyEvent.KEYCODE_N;
			break;
		case 79:
			up = 79;
			upKeycode = "O";
			break;
		case 111:
			keycode = KeyEvent.KEYCODE_O;
			break;
		case 80:
			up = 80;
			upKeycode = "P";
			break;
		case 112:
			keycode = KeyEvent.KEYCODE_P;
			break;
		case 81:
			up = 81;
			upKeycode = "Q";
			break;
		case 113:
			keycode = KeyEvent.KEYCODE_Q;
			break;
		case 82:
			up = 82;
			upKeycode = "R";
			break;
		case 114:
			keycode = KeyEvent.KEYCODE_R;
			break;
		case 83:
			up = 83;
			upKeycode = "S";
			break;
		case 115:
			keycode = KeyEvent.KEYCODE_S;
			break;
		case 84:
			up = 84;
			upKeycode = "T";
			break;
		case 116:
			keycode = KeyEvent.KEYCODE_T;
			break;
		case 85:
			up = 85;
			upKeycode = "U";
			break;
		case 117:
			keycode = KeyEvent.KEYCODE_U;
			break;
		case 86:
			up = 86;
			upKeycode = "V";
			break;
		case 118:
			keycode = KeyEvent.KEYCODE_V;
			break;
		case 87:
			up = 87;
			upKeycode = "W";
			break;
		case 119:
			keycode = KeyEvent.KEYCODE_W;
			break;
		case 88:
			up = 88;
			upKeycode = "X";
			break;
		case 120:
			keycode = KeyEvent.KEYCODE_X;
			break;
		case 89:
			up = 89;
			upKeycode = "Y";
			break;
		case 121:
			keycode = KeyEvent.KEYCODE_Y;
			break;
		case 90:
			up = 90;
			upKeycode = "Z";
			break;
		case 122:
			keycode = KeyEvent.KEYCODE_Z;
			break;
		 case 91:
			keycode = KeyEvent.KEYCODE_LEFT_BRACKET;
			break;
		case 92:
			keycode = KeyEvent.KEYCODE_BACKSLASH;
			break;
		case 93:
			keycode = KeyEvent.KEYCODE_RIGHT_BRACKET;
			break;			
		case -77://function
			keycode = KeyEvent.KEYCODE_F1;	
			break;
		case -78://up
			keycode = KeyEvent.KEYCODE_DPAD_UP;
			break;
		case -72://down
			keycode = KeyEvent.KEYCODE_DPAD_DOWN;
			break;
		case -76://left
			keycode = KeyEvent.KEYCODE_DPAD_LEFT;	
			break;
		case -74://right
			keycode = KeyEvent.KEYCODE_DPAD_RIGHT;	
			break;
		case -71://menu
			keycode = KeyEvent.KEYCODE_MENU;
			break;
		case -73://letter
			keycode = KeyEvent.KEYCODE_NUM;
			break;	
		case 5://cancel
			keycode = KeyEvent.KEYCODE_BACK;
			break;
		case 27://
			keycode = KeyEvent.KEYCODE_DEL;
			break;
		case 13://enter
			keycode = KeyEvent.KEYCODE_ENTER;
			break;
		case 2://
			keycode = KeyEvent.KEYCODE_F10;
			break;
		case 3://
			keycode = KeyEvent.KEYCODE_F11;
			break;
		case 4:
			keycode = KeyEvent.KEYCODE_F12;
			break;
		default:
			keycode = 0;
			break;
		}
	
	    try { 
	         Instrumentation inst=new Instrumentation(); 
	         if(65 <= up&& up <=90){
	        	 inst.sendStringSync(upKeycode);
	         }else{
	        	  inst.sendKeyDownUpSync(keycode);
	         }
	         
//	         Log.i("unifou", "sendKeyDownUpSync");
	       
	    } catch(Exception e) { 
	    } 	
		return 0;
	}
    
    public static int crc_cal_by_bit(byte [] ptr, int len) 
    {
    	int crc = 0;
    	int j = 0;
    	while(len-- != 0) 
    	{
    		for(int i = 128; i != 0; i /= 2) 
    		{
    			crc *= 2; 
    			if((crc&0x10000) !=0)
    				crc ^= 0x11021; 
    			if((ptr[j]&i) != 0)
    				crc ^= 0x1021; 
    		}
    		j++; 
    	}		
    	return crc; 
    }   
    
    public static void memset(byte[] data, int dataLen)
	{
		for(int i = 0; i < dataLen; i++)
		{
			data[i] = 0x00;
		}
	}
    
    public static int checkLenth(byte []str)
    {
    	int len = 0;
    	while(true)
    	{
    		if((str[len]==(byte)0x0D) &&(str[len+1]==(byte)0x0A)) 
    		{
    			break;
    		}
    		len++;
    	}
    	return len+2;
    	
    }

}
