package cn.soyadokio.java;

import java.util.Base64;

public class Base64Util {
    
    private static final String BASE64_ENCODING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    
    private static final String javaVersion;
    private static final boolean isJava8;
    
    static {
        javaVersion = System.getProperty("java.version");
        isJava8 = javaVersion.startsWith("1.8.") ? true : false;
    }
    
    private Base64Util() {
    }
    
    public static String encode(String input) throws Exception {
        // 有效值验证
        if (input == null) {
            throw new Exception("Invalid input string.");
        }
        // 版本选择
        if (isJava8) {
            return Base64.getEncoder().encodeToString(input.getBytes());
        } else {
            return encode_old(input);
        }
    }
    
    private static String encode_old(String input) {
        // 将原字串转为二进制字串
        StringBuilder asciiBinSB = new StringBuilder();
        char[] srcCh = input.toCharArray();
        for (int i = 0; i < srcCh.length; i++) {
            String asciiBin = Integer.toBinaryString((int) srcCh[i]);
            while (asciiBin.length() < 8) {
                asciiBin = "0" + asciiBin;
            }
            asciiBinSB.append(asciiBin);
        }
        
        // 按6位(bit)一组分段，若最后一组不足6位(bit)则在其尾部补“0”
        while (asciiBinSB.length() % 6 != 0) {
            asciiBinSB.append("0");
        }
        String asciiBinStr = asciiBinSB.toString();
        
        // 二进制字串转为字符(char)，再根据对应表进行Base64编码
        char[] codeCh = new char[asciiBinStr.length() / 6];
        int index = 0;
        for (int i = 0; i < codeCh.length; i++) {
            String bin6 = asciiBinStr.substring(6 * i, 6 * (i + 1));
            index = Integer.parseInt(bin6, 2);
            codeCh[i] = BASE64_ENCODING.charAt(index);
        }
        StringBuffer code = new StringBuffer(String.valueOf(codeCh));
        
        // 若原始数据字节长度除3余1，则在输出数据末尾加2个“=”；若原始数据长度除3余2，则在输出数据末尾加1个“=”
        if (input.length() % 3 == 1) {
            code.append("==");
        } else if (input.length() % 3 == 2) {
            code.append("=");
        }
        
        // 密文每76个字符数据后加一个换行符（CRLF），结尾加换行符
        int i = 76;
        while (i < code.length()) {
            code.insert(i, "\r\n");
            i += 76;
        }
        
        return code.toString();
    }
    
    public static String decode(String input) throws Exception {
        // 有效值验证
        if (input == null) {
            throw new Exception("Invalid input string.");
        }
        // 版本选择
        if (isJava8) {
            byte[] decoded = Base64.getDecoder().decode(input);
            return new String(decoded);
        } else {
            return decode_old(input);
        }
    }
    
    private static String decode_old(String input) {
        // 获取原字串长度对3求余所得余数
        int remainder = 0;
        if (input.endsWith("==")) {
            remainder = 1;
        } else if (input.endsWith("=")) {
            remainder = 2;
        }
        
        // 删除密文中的“=”和换行符
        input = input.replace("=", "");
        input = input.replace("\r\n", "");
        
        // 根据对应表，进行Base64解码
        StringBuilder indexBinSB = new StringBuilder();
        char[] srcCh = input.toCharArray();
        for (int i = 0; i < srcCh.length; i++) {
            int bcd = BASE64_ENCODING.indexOf((int) srcCh[i]);// 二进制编码的十进制(Binary Coded Decimal)
            String indexBin = Integer.toBinaryString(bcd);
            // 按6位(bit)一组组装成整个字串，若某组不足6位(bit)则在其头部补“0”
            while (indexBin.length() < 6) {
                indexBin = "0" + indexBin;
            }
            indexBinSB.append(indexBin);
        }
        
        // 删除因原明文长度不为3的倍数而在编码时在尾部加的“0”
        if (remainder == 1) {
            indexBinSB.delete(indexBinSB.length() - 4, indexBinSB.length());
        } else if (remainder == 2) {
            indexBinSB.delete(indexBinSB.length() - 2, indexBinSB.length());
        }
        String asciiBinStr = indexBinSB.toString();
        
        // 将二进制字串按8位(bit)一组分段，每段转为字符，所有组合成原字串
        char[] ascii = new char[asciiBinStr.length() / 8];
        for (int i = 0; i < ascii.length; i++) {
            String asciiBin = asciiBinStr.substring(8 * i, 8 * (i + 1));
            ascii[i] = (char) Integer.parseInt(asciiBin, 2);
        }
        
        return String.valueOf(ascii);
    }
    
}
