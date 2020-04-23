package com.mars.cp.users.util;

import java.security.MessageDigest;

/**
 * MD5工具类
 */
public class MarsMD5Util {

    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * 获取加密后的MD5码
     * @param resultString 参数
     * @param charsetname 参数
     * @return 数据
     */
    public static String MD5Encode(String resultString, String charsetname) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
        } catch (Exception exception) {
        }
        return resultString;
    }

    /**
     * 获取加密后的MD5码
     * @param origin 参数
     * @return 数据
     */
    public static String MD5Encode(String origin) {
        return MD5Encode(origin,null);
    }

}
