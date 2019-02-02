package com.yang.util;

import java.io.UnsupportedEncodingException;

/**
 * Created by rio on 2018/11/21.
 */
public class ByteHexStrConvertUtil {
	/**
	 * 将byte[]数组转化为0xXX格式的16进制字符串
	 *
	 * @param ba
	 * @return
	 */
	public static String getHexStrFromByteArray(byte[] ba) {
		StringBuilder sb = new StringBuilder();
		for (byte b : ba) {
			sb.append("0x");
			String temp = Integer.toHexString(b & 0xFF);
			if (temp.length() < 2) {
				sb.append("0");
			}
			sb.append(temp);
		}
		// System.out.println(sb.toString());
		return sb.toString();
	}

	/**
	 * 将0xXX格式的16进制数据组成的字符串（即一个byte拆成两个0xXX格式字符串，低或高四位的值如果小于16则在高位补0）转化为byte[]
	 *
	 * @param hexStr
	 * @return
	 */
	public static byte[] getByteArrayFromHexStr(String hexStr) {
		if (hexStr == null || "".equals(hexStr.trim()) || hexStr.length() % 4 != 0) {
			return new byte[] {};
		}
		StringBuilder tempSB = new StringBuilder();
		char[] ca = hexStr.toUpperCase().toCharArray();
		byte[] target = new byte[hexStr.length() / 4];
		String utilStr = "0123456789ABCDEF";
		for (int i = 0; i < ca.length - 1;) {
			byte tb = (byte) ((utilStr.indexOf(ca[i + 2]) << 4) | utilStr.indexOf(ca[i + 3]));
			target[i / 4] = tb;
			tempSB.append(tb);
			i = i + 4;
		}
		// System.out.println(tempSB.toString());
		return target;
	}

	/**
	 * 将int值转换为0xXX格式的16进制数据组成的字符串（对Integer.toHexString的结果进行处理 达到以下效果:120->
	 * 0x010x20,ff->0xff,1->0x01,d1f->0x0d0x1f）
	 *
	 * @param sourceStr
	 * @return
	 */
	public static String getByteUnitHexStrFromNumber(String sourceStr) {
		String sourHex = Integer.toHexString(new Integer(sourceStr));
		System.out.println(sourHex);
		boolean oddFlag = sourHex.length() % 2 == 0 ? false : true;
		StringBuilder sourSB = new StringBuilder();
		if (oddFlag) {
			sourSB.append("0x0").append(sourHex.charAt(0));
			for (int i = 1; i < sourHex.length() - 1;) {
				sourSB.append("0x").append(sourHex.charAt(i++)).append(sourHex.charAt(i++));
			}
		} else {
			for (int i = 0; i < sourHex.length() - 1;) {
				sourSB.append("0x").append(sourHex.charAt(i++)).append(sourHex.charAt(i++));
			}
		}
		return sourSB.toString();
	}

	public static String getKTRS4CheckSum(String sourceStr) {
		try {
			int checkSumValue = 0;
			byte[] target = new byte[2];
			byte[] bs = getByteArrayFromHexStr(sourceStr);
			for (byte b : bs) {
				checkSumValue += b & 0xff;
			}

			target[0] = (byte) (checkSumValue >>> 8);
			target[1] = (byte) checkSumValue;

			String hexStr = getHexStrFromByteArray(target);

			/*
			 * logger.info("UpdataToTjanjiServer getKTRS4CheckSum checkSum :" +
			 * checkSumValue + " ; HexStr : " + hexStr);
			 */

			return hexStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "0x00";
	}
	
	public static String getGBKStringFromHexStr(String hexStr){
		byte[] tempByteArr = ByteHexStrConvertUtil.getByteArrayFromHexStr(hexStr);
		String resultStr = null;
		try {
			resultStr = new String(tempByteArr, "GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultStr;
	}
}
