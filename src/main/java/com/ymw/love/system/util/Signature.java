package com.ymw.love.system.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import com.alibaba.fastjson.JSON;
/**
 * 签名
* @author suhua  

* @date 
 */
public class Signature { 
    public static String getSign(Map<String,Object> map,String key){
        ArrayList<String> list = new ArrayList<String>();
        for(Map.Entry<String,Object> entry:map.entrySet()){
            if(entry.getValue()!=""){
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" +key;
        System.out.println(result);
        result = MD5.MD5Encode(result).toUpperCase();
        return result;
    }
    
    public static String getSign(String result,String key){
        result += "key=" +key;
        result = MD5.MD5Encode(result).toUpperCase();
        return result;
    }
    
    public static String getSignJson(String result,String key){
    	Map<String, Object> map= JSON.parseObject(result, Map.class);
        return getSign(map,key);
    }

    /**
     * 微信获取签名信息
     * @param noncestr  随机字符串
     * @param timestamp 时间戳
     * @param ticket 有效的jsapi_ticket
     * @param url 当前网页的URL
     * @return
     */
    public static Map<String, Object> sign(String noncestr, Long timestamp, String ticket, String url) {
        Map<String, Object> ret = new HashMap<String, Object>();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + ticket +
                "&noncestr=" + noncestr +
                "&timestamp=" + timestamp +
                "&url=" + url;
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        ret.put("url", url);
        ret.put("nonceStr", noncestr);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException {
    	System.out.println(getSignJson("{\"phone\":\"18211569847\",\"codeType\":1}","1234567890"));
    }

}
