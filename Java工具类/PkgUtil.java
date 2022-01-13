package cn.soyadokio.java;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * Class Name : PkgUtil
 * 
 * Description: A tool pack/unpack .pkg file(A Tencent's image package format)
 * 
 * @author		SoyaDokio
 * @date		2018-03-06 00:04:43
 * @since		JDK 1.8.0_144
 */
public class PkgUtil {
    
    public static void pack(String inputPath) {
        // implement later
    }
    
    public static void unpack(String inputPath) {
        if (!validateInputPath(inputPath)) {
            return;
        }
        
        String outputDir = new File(inputPath).getParent();
        unpack(inputPath, outputDir);
    }
    
    public static void unpack(String inputPath, String outputDir) {
        if (!validateInputPath(inputPath)) {
            return;
        }
        
        if (outputDir == null || "".equals(outputDir.trim())) {
            outputDir = new File(inputPath).getParent();
        }
        if (!validateOutputDir(outputDir)) {
            return;
        }
        
        unpack(readFile(inputPath), outputDir);
    }
    
    public static void unpack(byte[] data, String outputDir) {
        if (data.length > 3 && (data[0] != 100 || data[1] != 0 || data[2] != 0 || data[3] != 0)) {
            System.err.println("invalid .pkg file!");
            return;
        }
        
        if (!validateOutputDir(outputDir)) {
            return;
        }
        
        // size of filelist
        int filelistSize = byte2dec(data[4], data[5], data[6], data[7]);
        
        // offset of block of filelist
        int filelistOffset = byte2dec(data[8], data[9], data[10], data[11]);
        
        // length of block of filelist (useless when unpack)
//        String filelistLen = byte2hex(data[12], data[13], data[14], data[15]);
//        System.out.println(filelistLen);
        
        System.out.println();
        
        int pointer = filelistOffset;
        for (int i = 0; i < filelistSize; i++) {
            int filePathLen = byte2dec(data[pointer], data[pointer + 1]);
//            System.out.println("filePathLen: \t" + filePathLen);
            
            StringBuffer sbuf = new StringBuffer();
            pointer = pointer + 2;
            int filePathEnd = pointer + filePathLen;
            while (pointer < filePathEnd) {
                sbuf.append((char) data[pointer++]);
            }
            String filePath = sbuf.toString();
//            System.out.println("filePath: \t" + filePath);
            
            String locationMark = byte2hex(data[pointer], data[pointer + 1], data[pointer + 2], data[pointer + 3]);
            if (!"00000000".equals(locationMark)) {
                System.err.println("signature block wrong!");
                return;
            }
            
            // image offset
            int fileOffset = byte2dec(data[pointer + 4], data[pointer + 5], data[pointer + 6], data[pointer + 7]);
            // image size (useless when unpack)
//            int fileSize = byte2dec(data[pointer + 8], data[pointer + 9], data[pointer + 10], data[pointer + 11]);
            // size of image compressed by zlib
            int fileZlibSize = byte2dec(data[pointer + 12], data[pointer + 13], data[pointer + 14], data[pointer + 15]);
            
            pointer = pointer + 16;
            
            byte[] fileZlib = new byte[fileZlibSize];
            for (int j = 0; j < fileZlibSize; j++) {
                fileZlib[j] = data[fileOffset + j];
            }
            
            byte[] file = uncompress(fileZlib);
            
            String outputPath = new File(outputDir).getAbsolutePath() + File.separator + filePath;
            writeFile(file, outputPath);
            System.out.printf("[%d/%d] %s" + System.lineSeparator(), i + 1, filelistSize, outputPath);
            
        }
        
    }
    
    private static boolean validateInputPath(String inputPath) {
        File file = new File(inputPath);
        
        if (!file.exists() || !file.isFile()) {
            System.err.println("invalid path or file not exist!");
            return false;
        }
        
        String fileFNm = file.getName();
        int index = fileFNm.lastIndexOf(".");
        if (index == -1) {
            System.err.println("invalid file type!");
            return false;
        }
        String fileEx = fileFNm.substring(index + 1);
        if (!"pkg".equals(fileEx)) {
            System.err.println("invalid file type!");
            return false;
        }
        
        return true;
    }
    
    private static boolean validateOutputDir(String outputDir) {
        File file = new File(outputDir);
        
        if (!file.exists() || !file.isDirectory()) {
            System.err.println("invalid directory!");
            return false;
        }
        
        return true;
    }
    
    private static byte[] compress(final byte[] input) {
        int compressedDataLength = 0;
        Deflater compresser = new Deflater();
        compresser.setInput(input);
        compresser.finish();
        ByteArrayOutputStream o = new ByteArrayOutputStream(input.length);
        byte[] result = new byte[1024];
        try {
            while (!compresser.finished()) {
                compressedDataLength = compresser.deflate(result);
                o.write(result, 0, compressedDataLength);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                o.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        compresser.end();
        return o.toByteArray();
    }
    
    private static byte[] uncompress(final byte[] input) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Inflater decompressor = new Inflater();
        try {
            decompressor.setInput(input);
            final byte[] buf = new byte[2048];
            while (!decompressor.finished()) {
                int count = 0;
                try {
                    count = decompressor.inflate(buf);
                } catch (DataFormatException e) {
                    e.printStackTrace();
                }
                bos.write(buf, 0, count);
            }
        } finally {
            decompressor.end();
        }
        return bos.toByteArray();
    }
    
    private static byte[] readFile(String inputPath) {
        List<Byte> tmp = new ArrayList<Byte>();
        InputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(inputPath));
            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = inputStream.read(buffer)) != -1) {
                for (int i = 0; i < read; i++) {
                    tmp.add(buffer[i]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        byte[] result = new byte[tmp.size()];
        for (int i = 0; i < tmp.size(); i++) {
            result[i] = tmp.get(i);
        }
        
        return result;
    }
    
    private static void writeFile(byte[] data, String outputPath) {
        File outputDir = new File(outputPath).getParentFile();
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
        
        OutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(outputPath));
            outputStream.write(data);
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private static String byte2hex(byte... bytes) {
        List<Byte> blist = new ArrayList<Byte>();
        for (byte b : bytes) {
            blist.add(b);
        }
        
        StringBuffer sbuf = new StringBuffer();
        for (int i = blist.size() - 1; i >= 0; i--) {
            String hex = Integer.toHexString(blist.get(i)).toUpperCase();
            if (hex.length() < 2) {
                hex = "0" + hex;
            } else if (hex.length() > 2) {
                hex = hex.substring(hex.length() - 2);
            }
            
            sbuf.append(hex);
        }
        
        return sbuf.toString();
    }
    
    private static int byte2dec(byte... bytes) {
        return Integer.parseInt(byte2hex(bytes), 16);
    }
    
}
