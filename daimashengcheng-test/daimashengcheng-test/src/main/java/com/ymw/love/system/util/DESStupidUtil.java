package com.ymw.love.system.util;

import java.security.InvalidKeyException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.springframework.util.Base64Utils;

/**
* @author  作者 ：suhua
* @date 创建时间：2019年8月6日 下午6:26:23
*类说明：
*/
public class DESStupidUtil {
	 private DESStupidUtil(){}
     /**
      * 根据键值进行加密
      * @param data
      * @param key 8位数
      * @return
      * @throws Exception
      */
     public static String encrypt(String data, String key) throws Exception {
          byte[] bt = encrypt(data.getBytes(), key.getBytes());
          return new String(Base64Utils.encode(bt));
     }
     /**
      * 根据键值进行解密
      * @param data
      * @param key
      * @return
      * @throws Exception
      */
     public static String decrypt(String data, String key) throws Exception {
          if (data == null || data.length() < 1) {
              return "";
          }
          byte[] bt = Base64Utils.decode(data.getBytes());
          return new String(decrypt(bt, key.getBytes()));
     }
     /**
      * 根据键值进行加密
      * @param data
      * @param key    加密键byte数组
      * @return
      * @throws InvalidKeyException
      */
     private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
          // 生成一个可信任的随机数源
          SecureRandom sr = new SecureRandom();
          // 从原始密钥数据创建DESKeySpec对象
          DESKeySpec dks = new DESKeySpec(key);
          
          // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
          SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
          SecretKey secretKey = keyFactory.generateSecret(dks);
          
          // Ciper 对象完成加密操作
          Cipher cipher = Cipher.getInstance("DES");
          // 用密钥初始化Cipher对象
          cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);
          
          return cipher.doFinal(data);
     }
     /**
      * 解密
      * @param data
      * @param key
      * @return
      * @throws Exception
      */
     private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
          // 生成一个可信任的随机数源
          SecureRandom sr = new SecureRandom();
          // 从原始迷药数据创建DESKeySpec对象
          DESKeySpec dks = new DESKeySpec(key);
          
          // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
          SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
          SecretKey secretKey = keyFactory.generateSecret(dks);
          
          // Cipher 对象实际完成解密操作
          Cipher cipher = Cipher.getInstance("DES");
          // 用密钥初始化Cipher对象
          cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);
          
          return cipher.doFinal(data);
     }
     
     public static void main(String[] args) throws Exception {
          String key = "12345678";
          String data = "xiaowunai1991";
          //DXv6uueWpXel/4ldXFyokQ==
          String encodeStr = encrypt(data, key);
          System.out.println(encodeStr);
          System.out.println(decrypt(encodeStr, key));
     }
}
