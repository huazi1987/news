package com.news.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtil {

	private static final String charset = "UTF-8";

	/**
	 * BASE64解密,处理/参数，将所有\转换为／
	 * @Description: 
	 * @author wanghz
	 * @date 2015年12月8日
	 * @param key
	 * @return
	 * @throws IOException 
	 */
	public static String decodeBASE64(String key) throws IOException {
		return new String(Base64.getDecoder().decode(key.replace("-", "/")), charset);
	}
	
	/**
	 * BASE64加密
	 * @Description: 
	 * @author wanghz
	 * @date 2015年12月8日
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encodeBASE64(String key) throws IOException {
		return encodeBASE64(key.getBytes(charset));
	}
	
	public static String encodeBASE64(byte[] src) throws IOException {
		return Base64.getEncoder().encodeToString(src);
	}

	/**
	 * MD5加密
	 * @Description: 
	 * @author wanghz
	 * @date 2015年12月8日
	 * @param origin
	 * @return
	 */
	public static String encodeMD5(String origin) {
		String resultString = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");  
			byte[] bytes = md5.digest(origin.getBytes(charset));
			StringBuffer stringBuffer = new StringBuffer();  
			for (byte b : bytes){  
				int bt = b&0xff;
				if (bt < 16){  
					stringBuffer.append(0);  
				}   
				stringBuffer.append(Integer.toHexString(bt));  
			}
			resultString = stringBuffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultString.toLowerCase();
	}
	
	/**
	 * 获取文件MD5值
	 * @Description: 
	 * @author wanghz
	 * @date 2016年7月29日
	 * @param file
	 * @return
	 */
	public static String encodeMD5(File file){  
	      // 缓冲区大小（这个可以抽出一个参数）  
	      int bufferSize = 256 * 1024;  
	      FileInputStream fileInputStream = null;  
	      DigestInputStream digestInputStream = null;  
	      try {  
	         // 拿到一个MD5转换器（同样，这里可以换成SHA1）  
	         MessageDigest messageDigest =MessageDigest.getInstance("MD5");  
	         // 使用DigestInputStream  
	         fileInputStream = new FileInputStream(file);  
	         digestInputStream = new DigestInputStream(fileInputStream,messageDigest);  
	         // read的过程中进行MD5处理，直到读完文件  
	         byte[] buffer =new byte[bufferSize];  
	         while (digestInputStream.read(buffer) > 0);  
	         // 获取最终的MessageDigest  
	         messageDigest= digestInputStream.getMessageDigest();  
	         // 拿到结果，也是字节数组，包含16个元素  
	         byte[] resultByteArray = messageDigest.digest();  
	         // 同样，把字节数组转换成字符串 
	         return byteArrayToHex(resultByteArray);  
	      } catch (Exception e) {  
	         e.printStackTrace();
	      } finally {  
	            try {
					digestInputStream.close();
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}  
	      }
	      return null;
	   }
	
	/**
	 * AES加密
	 * @Description: 
	 * @author wanghz
	 * @date 2015年12月8日
	 * @param sSrc
	 * @param sKey(必须16位字符)
	 * @return
	 */
	public static String encodeAES(String sSrc, String sKey){
		if (sKey == null) {
			return null;
		}
		if (sKey.length() != 16) {
			return null;
		}
		try {
			byte[] raw = sKey.getBytes(charset);
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/补码方式"
			IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes(charset));//使用CBC模式，需要一个向量iv，可增加加密算法的强度
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(sSrc.getBytes(charset));
			return byte2hex(encrypted).toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * AES解密
	 * @Description: 
	 * @author wanghz
	 * @date 2015年12月8日
	 * @param sSrc
	 * @param sKey
	 * @return
	 */
	public static String decodeAES(String sSrc, String sKey){
		if (sKey == null) {
			return null;
		}
		if (sKey.length() != 16) {
			return null;
		}
		try {
			byte[] raw = sKey.getBytes(charset);
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes(charset));
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted1 = hex2byte(sSrc);
			byte[] original = cipher.doFinal(encrypted1);
			return new String(original, charset);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * DES加密
	 * @Description: 
	 * @author wanghz
	 * @date 2015年12月8日
	 * @param sSrc
	 * @param skey
	 * @return
	 */
	public static String encodeDES(String sSrc, String skey) {
	    // DES算法要求有一个可信任的随机数源
	    SecureRandom sr = new SecureRandom();
	    try {
			// 从原始密匙数据创建DESKeySpec对象
			DESKeySpec dks = new DESKeySpec(skey.getBytes(charset));
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(dks);
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance("DES");
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
			// 执行加密操作
			byte[] encrypted = cipher.doFinal(sSrc.getBytes(charset));
			return byte2hex(encrypted).toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return null;
	}
	
	/**
	 * DES解密
	 * @Description: 
	 * @author wanghz
	 * @date 2015年12月8日
	 * @param sSrc
	 * @param skey
	 * @return
	 */
	public static String decodeDES(String sSrc, String skey) {
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建一个DESKeySpec对象
        try {
			DESKeySpec dks = new DESKeySpec(skey.getBytes(charset));
			// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(dks);
			// Cipher对象实际完成解密操作
			Cipher cipher = Cipher.getInstance("DES");
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
			// 正式执行解密操作
			byte[] encrypted1 = hex2byte(sSrc);
			byte[] original = cipher.doFinal(encrypted1);
			return new String(original, charset);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
    }
	
	private static byte[] hex2byte(String strhex) {
		if (strhex == null) {
			return null;
		}
		int l = strhex.length();
		if (l % 2 == 1) {
			return null;
		}
		byte[] b = new byte[l / 2];
		for (int i = 0; i != l / 2; i++) {
			b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2),16);
		}
		return b;
	}

	private static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}
	
	private static String byteArrayToHex(byte[] byteArray) {  
		// 首先初始化一个字符数组，用来存放每个16进制字符  
		char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F' };  
		// new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））  
		char[] resultCharArray =new char[byteArray.length * 2];  
		// 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去  
		int index = 0;  
		for (byte b : byteArray) {  
			resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];  
			resultCharArray[index++] = hexDigits[b& 0xf];  
		}
		// 字符数组组合成字符串返回  
		return new String(resultCharArray);
	}
	
	public static String toHexString(String s) {  
		String str = "";  
		for (int i = 0; i < s.length(); i++) {  
			int ch = (int) s.charAt(i);  
			String s4 = Integer.toHexString(ch);  
			str = str + s4;  
		}  
		return str;  
	}
	
	// 转化十六进制编码为字符串  
	public static String toStringHex1(String s) {  
		byte[] baKeyword = new byte[s.length() / 2];  
		for (int i = 0; i < baKeyword.length; i++) {  
			try {  
				baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));  
			} catch (Exception e) {  
				e.printStackTrace();  
			}  
		}  
		try {  
			s = new String(baKeyword, "utf-8");// UTF-16le:Not  
		} catch (Exception e1) {  
			e1.printStackTrace();  
		}  
		return s;  
	}
	
	public static void main(String[] args) throws Exception {
		String cKey = "123456tr9yhc91";
		System.out.println(EncryptUtil.encodeMD5(cKey));
//        // 需要加密的字串
//        String cSrc = "Email : arix04@xxx.com";
//        System.out.println(cSrc);
//        // AES加密
//        long lStart = System.currentTimeMillis();
//        String enString = EncryptUtil.encodeAES(cSrc, cKey);
//        System.out.println("AES加密后的字串是：" + enString);
//
//        long lUseTime = System.currentTimeMillis() - lStart;
//        System.out.println("加密耗时：" + lUseTime + "毫秒");
//        // AES解密
//        lStart = System.currentTimeMillis();
//        String DeString = EncryptUtil.decodeAES(enString, cKey);
//        System.out.println("AES解密后的字串是：" + DeString);
//        lUseTime = System.currentTimeMillis() - lStart;
//        System.out.println("解密耗时：" + lUseTime + "毫秒");
//        
//        // DES加密
//        lStart = System.currentTimeMillis();
//        enString = EncryptUtil.encodeDES(cSrc, cKey);
//        System.out.println("DES加密后的字串是：" + enString);
//
//        lUseTime = System.currentTimeMillis() - lStart;
//        System.out.println("加密耗时：" + lUseTime + "毫秒");
//        // DES解密
//        lStart = System.currentTimeMillis();
//        DeString = EncryptUtil.decodeDES(enString, cKey);
//        System.out.println("DES解密后的字串是：" + DeString);
//        lUseTime = System.currentTimeMillis() - lStart;
//        System.out.println("解密耗时：" + lUseTime + "毫秒");
//        //文件MD5
//        NFile file = new NFile("F:\\sync_qiniu\\driver_antvr_32.dll");
//        String DeString = EncryptUtil.encodeMD5(file);
//        System.out.println("该文件的MD5值是：" + DeString.toLowerCase());
//        System.out.println(StringUtil.GenerateIdentifier());
        
//        String s = "ANTVR_Monitor_APP_V106_20160716";
//        System.out.println(EncryptUtil.byteArrayToHex(s.getBytes()));
//        System.out.println(EncryptUtil.toHexString(s));
//        System.out.println(EncryptUtil.toStringHex1(toHexString(s)));
//		System.out.println(StringUtil.getStochastic(16));
	}
}
