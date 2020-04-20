package com.mars.utils;

import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * AES加密工具类
 * @author yuye
 *
 */
public class MarsAesUtil {

	/**
	 * 加密函数
	 * 
	 * @param content
	 *            加密的内容
	 * @param strKey
	 *            密钥
	 * @return 返回二进制字符数组
	 * @throws Exception 错误
	 */
	public static byte[] enCrypt(String content, String strKey) throws Exception {
		KeyGenerator keygen;
		SecretKey desKey;
		Cipher c;
		byte[] cByte;
		String str = content;

		keygen = KeyGenerator.getInstance("AES");
		
		SecureRandom random=SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(strKey.getBytes());
		keygen.init(128, random);

		desKey = keygen.generateKey();
		c = Cipher.getInstance("AES");

		c.init(Cipher.ENCRYPT_MODE, desKey);

		cByte = c.doFinal(str.getBytes("UTF-8"));

		return cByte;
	}

	/**
	 * 解密函数
	 * 
	 * @param src
	 *            加密过的二进制字符数组
	 * @param strKey
	 *            密钥
	 * @return 数据
	 * @throws Exception 异常
	 */
	public static String deCrypt(byte[] src, String strKey) throws Exception {
		KeyGenerator keygen;
		SecretKey desKey;
		Cipher c;
		byte[] cByte;

		keygen = KeyGenerator.getInstance("AES");
		
		SecureRandom random=SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(strKey.getBytes());
        
		keygen.init(128, random);

		desKey = keygen.generateKey();
		c = Cipher.getInstance("AES");

		c.init(Cipher.DECRYPT_MODE, desKey);

		cByte = c.doFinal(src);

		return new String(cByte, "UTF-8");
	}

	/**
	 * 2进制转化成16进制
	 * 
	 * @param buf buf
	 * @return 数据
	 */
	public static String parseByte2HexStr(byte buf[]) {
		BASE64Encoder base64encoder = new BASE64Encoder();
		String encode=new String(base64encoder.encode(buf));
		return encode;
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr 参数
	 * @return 数据
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		
		try {
			BASE64Decoder base64decoder = new BASE64Decoder();
			byte[] encodeByte = base64decoder.decodeBuffer(hexStr);
			return encodeByte;
		} catch (IOException e) {
			return null;
		}
	}
}
