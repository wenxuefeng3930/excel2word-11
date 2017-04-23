package com.bonc.common.decode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class DecodeTool {

	/**
	 * 解密 使用对称密钥进行解密 keyFilePath 密钥存放路径 text 要解密的字节数组 解密后返回一个字节数组
	 * 
	 * @throws IOException
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public static byte[] getDecode(String keyFilePath, byte[] text) throws IOException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

		InputStream fis = DecodeTool.class.getResourceAsStream(keyFilePath);
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100];
		int rc = 0; // 所有读取的内容都使用temp接收
		while ((rc = fis.read(buff, 0, 100)) > 0) {
			swapStream.write(buff, 0, rc);
		}
		byte[] key = swapStream.toByteArray();
		fis.close();

		SecretKeySpec sKeySpec = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance("AES");

		cipher.init(Cipher.DECRYPT_MODE, sKeySpec);
		byte[] decode = cipher.doFinal(text);
		return decode;
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

}
