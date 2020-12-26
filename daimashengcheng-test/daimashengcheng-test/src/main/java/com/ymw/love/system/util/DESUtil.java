package com.ymw.love.system.util;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;

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
public class DESUtil {
    /**
     * 系统DES密钥相关常量
     */
    public static final String DES_KEY_DEFAULT = "ksljfei1";
	
    
    
    /**
     * DES方法 0为加密,1为解密
     *
     * @param str  待加密字符串
     * @param type 0为加密,1为解密
     * @return DES Str
     */
    public static String getDES(String str, int type,String keys) {
        Cipher encryptCipher = null;
        Cipher decryptCipher = null;
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        try {
           if(StringUtils.isEmpty(keys)) {
        	   return null;
           }
        	
            Key key = getKey(keys.getBytes("UTF-8"));
            encryptCipher = Cipher.getInstance("DES");
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);
            decryptCipher = Cipher.getInstance("DES");
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
            if (type == 0) { // 0为加密
                return byteArr2HexStr(encryptCipher.doFinal(str
                        .getBytes("UTF-8")));
            } else {
                return new String(decryptCipher.doFinal(hexStr2ByteArr(str)),
                        "UTF-8");
            }
        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
    }
    
    
    
    /**
     * DES
     *
     * @param arrBTmp
     * @return
     * @throws Exception
     */
    private static Key getKey(byte[] arrBTmp) throws Exception {
        byte[] arrB = new byte[8];// 创建一个空的8位字节数组（默认值为0）
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) { // 将原始字节数组转换为8位
            arrB[i] = arrBTmp[i];
        }
        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");// 生成密钥
        return key;
    }

    /**
     * DES
     *
     * @param arrB
     * @return
     * @throws Exception
     */
    private static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    /**
     * DES
     *
     * @param strIn
     * @return
     * @throws Exception
     */
    private static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes("UTF-8");
        int iLen = arrB.length;

        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }
     public static void main(String[] args) throws Exception {
          String key = "12345678";
          String data = "0d7bfabae796a577a5ff895d5c5ca891";
          System.out.println( getDES(data, 1, key));
          //DXv6uueWpXel/4ldXFyokQ==
       
     }
     
    
}
