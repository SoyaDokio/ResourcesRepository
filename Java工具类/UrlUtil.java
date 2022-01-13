package cn.soyadokio.java;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class UrlUtil {
    
    private static final String[] ENCODING_MAPPING = new String[256];
    
    static {
        for (int i = 0; i < 256; i++) {
            if ('0' <= i  && i <= '9' ||
                    'A' <= i  && i <= 'Z' ||
                    'a' <= i  && i <= 'z' ||
                    i == '_' ||
                    i == '-' ||
                    i == '.'
                    ) {
                ENCODING_MAPPING[i] = String.valueOf((char) i);
            } else {
                /**
                 *  %[flags][width][conversion]
                 *      flags '0' - The result will be zero-padded
                 *      width 2
                 *      conversion 'X' - The result is formatted as a hexadecimal integer, uppercase
                 */
                ENCODING_MAPPING[i] = "%" + String.format("%02X", i).toUpperCase();
            }
        }
    }
    
    private UrlUtil() {
    }
    
    public static String encode(String input) throws Exception {
        // 有效值验证
        if (input == null) {
            throw new Exception("Invalid input string.");
        }
        
        return doEncode1(input);
    }
    
    private static String doEncode1(String input) throws UnsupportedEncodingException {
        return URLEncoder.encode(input, "UTF-8");
    }
    
    private static String doEncode2(String input) {
        StringBuilder sbuf = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            /**
             *  The 8-bit byte, which is signed in Java, is sign-extended to 
             *  a 32-bit int. To effectively undo this sign extension, one 
             *  can mask the byte with 0xFF
             */
            sbuf.append(ENCODING_MAPPING[input.charAt(i) & 0xFF]);
        }
        return sbuf.toString();
    }
    
    public static String decode(String input) throws Exception {
        // 有效值验证
        if (input == null) {
            throw new Exception("Invalid input string.");
        }
        
        return doDecode1(input);
    }
    
    private static String doDecode1(String input) throws UnsupportedEncodingException {
        return URLDecoder.decode(input, "UTF-8");
    }
    
    private static String doDecode2(String input) {
        StringBuilder sbuf = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '%' && i != input.length() - 1) {
                if (input.charAt(i + 1) == '%') {
                    sbuf.append('%');
                    i += 1;
                } else {
                    String hex = input.charAt(i + 1) + "" + input.charAt(i + 2);
                    sbuf.append((char) Integer.valueOf(hex, 16).intValue());
                    i += 2;
                }
            } else {
                sbuf.append(c);
            }
        }
        
        return sbuf.toString();
    }
    
}
