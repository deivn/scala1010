package com.ymw.love.system.util;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

/**
* @author  作者 ：suhua
* @date 创建时间：2019年10月21日 下午5:06:12
*类说明：
*/
public class RSAEncrypt {
	private static Map<Integer, String> keyMap = new HashMap<Integer, String>();  //用于封装随机产生的公钥与私钥
	public static void main(String[] args) throws Exception {
		//生成公钥和私钥
		genKeyPair();
		//加密字符串
		String message = "{\"codeType\": 2,\"key\": \"1886074950\",\"phone\": \"15475464754\",\"codeType\": 2,\"key\": \"1886074950\",\"phone\": \"15475464754\"}";
		System.out.println("随机生成的公钥为:" + keyMap.get(0));
		System.out.println("随机生成的私钥为:" + keyMap.get(1));
		
		//String messageEn = encrypt(message,keyMap.get(0));
		//System.out.println(message + "\t加密后的字符串为:" + messageEn);
		//String messageDe = decrypt(messageEn,keyMap.get(1));
		//System.out.println("还原后的字符串为:" + messageDe);
		
		//lpyXKHHuwueDg7a3DKqC/K3Djt4ukaA64nOWZqDC7Ukz1dvyfmxFVm3CcfJwWuJWBg8JX5PA4asH3oC752SKHQ==
		
		

		
		String messageEns="hZKdyGtxHLSyJv/M+iI1xh033fHv0PEaVMvqPVGwRaSdm5D+9bcm+F22ZW9gHw0wCFI9KEnwPsqPgYCj+Yl3CiuwiDgOOfBFhnWwULo2vlxkNeWY7pq2n0F29g4M1Hnphu+Afuqf7jdmxxE2acTFuU6iR4RLWFm1bOcx5DIbr3c=";
		
		//String k="MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIiR1/kBqyoUC3Jv2Fp3HepSqp6ReU1JN9qENQ9BRWi4zp5E5PH0KApXyPdBYs7BY4Swjl7LZ/DHOAZY4o9oGtECAwEAAQ==";
		String v="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJbbo+GaTyxNhtuQAhrKAaoQoI6GMicLFBzJmGh4HBpDRuS5wd22BR0IoXsbn4uZzFPIInZ2Rfg/tCz6ywvLSHKbUg27RuoVW+3D62MlwVAReZPV82nXm1/g92MEaYPKkZri2UtJEZNxOd8d5VmFzNNOwI/y/q8DT6oNgjA3tA9/AgMBAAECgYEAlnK/kovA7871clzBLJRPapqgdOMyeyuaSEdovq8iX7K+rfbvd7xChz0HRm47BA9C7j9G11uG1lg5yS3od3+pZE82zs0xRijxv/E+omanq8qQEbW9PyZqDlXgWeLtMeOzMny37O4Z4DEEb9lrqw8WiEJsk6GGODh8B42jCqz2JcECQQDglpnCnrqPlwp4WkNMCO4mU6nCOuajrhqoW84EfgEb9vhTIbq6bSxVDAIhfft7R8dTs2jRkpK5JQ0lAKK3ziLhAkEAq/Ue08meSSIN0vor1beK5D5HppwdrhKEOTxUYPaGe1YZ/V/4WGATk4fBRWJPEGiG6heXSt7q7FR70fZe9LbeXwJAT6v9VfZbNm8djr5umz2bxkrtsPKWOUCdQlZ3Ck7s88+0HkGb7WCnVnBRHTKFESHQi1fc+mOl2zpSpGrKTqw44QJBAIHOR7cYO6R5UHYy42QxaWdinDPpJX6B+844/Hk1n8768COUjqQn4p87m2oFDCoWvcF4jXy3Sh+cptuYX5XbdYECQC3p7RNmb2JyfrggppOKxQ374kzaeISFS0TiMPRWt2zw8HXH5Oe1+Kql2QA10P4tGVccTQRm0Qi/4ZBs1g+SlHM=";
		
		//String messageEns = encrypt(message,k);
		//System.out.println(message + "\t加密后的字符串为:" + messageEns);
	String messageDes = decrypt(messageEns,v);
	  System.out.println("还原后的字符串为:" + messageDes);
		
	
	}

	/** 
	 * 随机生成密钥对 
	 * @throws NoSuchAlgorithmException 
	 */  
	public static void genKeyPair() throws NoSuchAlgorithmException {  
		// KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象  
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");  
		// 初始化密钥对生成器，密钥大小为96-1024位  
		keyPairGen.initialize(1024,new SecureRandom());  
		// 生成一个密钥对，保存在keyPair中  
		KeyPair keyPair = keyPairGen.generateKeyPair();  
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥  
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥  
		String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));  
		// 得到私钥字符串  
		String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));  
		// 将公钥和私钥保存到Map
		keyMap.put(0,publicKeyString);  //0表示公钥
		keyMap.put(1,privateKeyString);  //1表示私钥
	}  
	/** 
	 * RSA公钥加密 
	 *  
	 * @param str 
	 *            加密字符串
	 * @param publicKey 
	 *            公钥 
	 * @return 密文 
	 * @throws Exception 
	 *             加密过程中的异常信息 
	 */  
	public static String encrypt( String str, String publicKey ) throws Exception{
		//base64编码的公钥
		byte[] decoded = Base64.decodeBase64(publicKey);
		RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
		//RSA加密
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
		return outStr;
	}

	/** 
	 * RSA私钥解密
	 *  
	 * @param str 
	 *            加密字符串
	 * @param privateKey 
	 *            私钥 
	 * @return 铭文
	 * @throws Exception 
	 *             解密过程中的异常信息 
	 */  
	public static String decrypt(String str, String privateKey) throws Exception{
		//64位解码加密后的字符串
		byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
		//base64编码的私钥
		byte[] decoded = Base64.decodeBase64(privateKey);  
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));  
		//RSA解密
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, priKey);
		String outStr = new String(cipher.doFinal(inputByte));
		return outStr;
	}

}
