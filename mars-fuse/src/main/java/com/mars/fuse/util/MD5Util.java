package com.mars.fuse.util;

import java.security.MessageDigest;

/**
 * MD5工具类
 */
public class MD5Util {

    /**
     * MD5加密，32位
     * @param plainText
     *            明文
     * @return 32位密文
     */
    public static String encryption(String plainText) throws Exception {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            return buf.toString();

        } catch (Exception e) {
            throw e;
        }
    }
}
