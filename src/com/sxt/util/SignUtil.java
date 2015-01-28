package com.sxt.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class SignUtil {
	
	
	private static String token = "lovePurple";
	
	public static boolean checkSignature(String signature, String timestamp, String nonce){
		
		
		String[] arr = new String[] { token, timestamp, nonce };
		// ��token��timestamp��nonce�������������ֵ�������
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		
		MessageDigest md = null;
		String tmpStr = null;
		
		
		try {
			md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		content = null;
		System.out.println(tmpStr+"  ========temStr");
		// ��sha1���ܺ���ַ�������signature�Աȣ���ʶ��������Դ��΢��
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false; 
	}

	/*
	 * ���ֽ�����ת��Ϊʮ�������ַ���
	 */
	private static String byteToStr(byte[] digest) {
		String strDigest = ""; 
		for (int i = 0; i < digest.length; i++) {
			strDigest += byteToHexStr(digest[i]);
		}
		return strDigest;
	}

	
	/*
	 * ���ֽ�ת��Ϊʮ�������ַ��� 
	 * 
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];
		
		String s = new String(tempArr);
		return s;
	}
}
