package com.mars.utils;

import java.util.Arrays;

/**
 * 图片工具类
 */
public class MarsImageUtil {
    /** 
     * File extensions. 
     */  
    private static final String[] FILE_EXTS = {"JPG", "PNG", "GIF"};  
    /** 
     * Magic bytes in a file with above extension. 
     */  
    private static final byte[][] FILE_MAGS = new byte[][] {  
        new byte[] {(byte)0xFF, (byte)0xD8, (byte)0xFF, (byte)0xE0}, //JPG  
   
   
        new byte[] {(byte)0x89, (byte)0x50, (byte)0x4E, (byte)0x47}, //PNG  
        new byte[] {(byte)0x47, (byte)0x49, (byte)0x46, (byte)0x38}  //GIF  
    };  

    /** 
     * 获取图片的格式名称
     * @param contents file contents 
     * @return file format, null if unsupported. 
     */  
    public static String getFileFormat(byte[] contents) {  
        try {
			for (int i = 0; i < FILE_MAGS.length; i++) {  
			    byte[] mag = FILE_MAGS[i];  
			    if (contents.length >= mag.length) {  
			        if (Arrays.equals(Arrays.copyOf(contents, mag.length), mag)) {  
			            return FILE_EXTS[i];  
			        }  
			    }  
			}
		} catch (Exception e) {
			return "JPEG";
		}  
        return "JPEG";  
    }  
}
