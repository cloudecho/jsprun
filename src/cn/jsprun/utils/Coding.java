package cn.jsprun.utils;
import java.io.UnsupportedEncodingException;
public final class Coding {
	public static String bin2hex(String s,String charset) {
		char[] digital = "0123456789abcdef".toCharArray();
		StringBuffer sb = new StringBuffer();
		byte[] bs=null;
		try {
			bs = s.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		int bit;
		for (int i = 0; i < bs.length; i++) {
			bit = (bs[i] & 0x0f0) >> 4;
			sb.append(digital[bit]);
			bit = bs[i] & 0x0f;
			sb.append(digital[bit]);
		}
		return sb.toString();
	}
	public static String hex2bin(String hex) {
		String digital = "0123456789abcdef";
		char[] hex2char = hex.toCharArray();
		byte[] bytes = new byte[hex.length() / 2];
		int temp;
		for (int i = 0; i < bytes.length; i++) {
			temp = digital.indexOf(hex2char[2 * i]) * 16;
			temp += digital.indexOf(hex2char[2 * i + 1]);
			bytes[i] = (byte) (temp & 0xff);
		}
		return new String(bytes);
	}
	public static String byte2hex(byte[] b) { 
		StringBuffer hs=new StringBuffer();
		String tmp = null;
		for (int n = 0; n < b.length; n++) {
			tmp = (Integer.toHexString(b[n] & 0XFF));
			if (tmp.length() == 1) {
				hs.append("0" + tmp);
			} else {
				hs.append(tmp);
			}
		}
		tmp = null;
		return hs.toString().toUpperCase(); 
	}
	public static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0) {
			throw new IllegalArgumentException("byte count is not even");
		}
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			b2[n / 2] = (byte) Integer.parseInt( new String(b, n, 2), 16);
		}
		return b2;
	}
}