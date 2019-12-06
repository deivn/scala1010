package com.ymw.love.system.util;

import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author  作者 ：suhua
* @date 创建时间：2019年8月5日 下午2:49:35
*类说明：验证工具
*/
public class VerifyUtil {
	/**
	 *    <br>　　　　　2019年1月16日已知
    中国电信号段
        133,149,153,173,174,177,180,181,189,199
    中国联通号段
        130,131,132,145,146,155,156,166,175,176,185,186
    中国移动号段
        134(0-8),135,136,137,138,139,147,148,150,151,152,157,158,159,165,178,182,183,184,187,188,198
    上网卡专属号段（用于上网和收发短信，不能打电话）
        如中国联通的是145
    虚拟运营商
        电信：1700,1701,1702
        移动：1703,1705,1706
        联通：1704,1707,1708,1709,171
    卫星通信： 1349 <br>　　　　　未知号段：141、142、143、144、154
    */
	
  private  static String telRegex="^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$";
  
  
  //身份证18位
  private static String idCard18="^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
  
  //身份证15位
  private static String idCard15="^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$";
  
  //中文
  private static String zw="^[\\u4e00-\\u9fa5]*$";
  
  
  
	/**
	 * 手机号验证
	 * @param phone
	 * @return
	 */
	public static boolean  phoneVerify(String phone) {
		Pattern p = Pattern.compile(telRegex);
		Matcher m = p.matcher(phone);
		return m.matches();
	}
	
	
	 /**
     * MD5加密方法
     *
     * @param str            待加密字符串
     * @param encoding       default UTF-8
     * @param no_Lower_Upper 0：不区分大小写，1：小写，2：大写
     * @return MD5Str
     */
    public static String getMD5(String str, int no_Lower_Upper) {
      
        StringBuffer sb = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(str.getBytes("UTF-8"));
            for (int i = 0; i < array.length; i++) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).toUpperCase().substring(1, 3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (no_Lower_Upper == 0) {
            return sb.toString();
        }
        if (no_Lower_Upper == 1) {
            return sb.toString().toLowerCase();
        }
        if (no_Lower_Upper == 2) {
            return sb.toString().toUpperCase();
        }
        return null;
    }

    /**
     * 验证身份证是否有效
     * @param id
     * @return
     */
	public static boolean  IDCardVerify(String card) {
		if(StringUtils.isEmpty(card)) {
			return false;
		}
		
		if(card.length()==15 || card.length()==18) {
			Pattern	 p = Pattern.compile(card.length()==15?idCard15:idCard18);
			Matcher m = p.matcher(card);
			return m.matches();
		}
			return false;
	}
	
	/**
	 * 验证全中文
	 * @param arg
	 * @return
	 */
    public static boolean zwVerify(String arg) {
    	Pattern	 p = Pattern.compile(zw);
		Matcher m = p.matcher(arg);
		return m.matches();
    }
	/**
	 * 身份证获取出生日期
	 * @param card
	 * @return
	 */
    public static Date getDirthdate(String card) {
    	if(StringUtils.isEmpty(card)||(card.length()!=15 && card.length()!=18)) {
			return null;
		}
    	
    	try {
    		String d=null;
        	if(card.length()==15) {
                d="19"+card.substring(6, 12);
        	}else if(card.length()==18) {
        		d=card.substring(6, 14);
        	}
    		
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			return sdf.parse(d);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return null;
    }
    
    /**
     * 通过身份证获取性别
     * @param card
     * 15位身份证号，倒数第一位能看出性别，男单数女双数。 
     * 18位身份证号，倒数第二位能看出性别，男单数女双数。
     * @return
     */
    public  static Integer getSex(String card) {
    	if(StringUtils.isEmpty(card)||(card.length()!=15 && card.length()!=18)) {
			return null;
		}
    	  String usex=null;
        	if(card.length()==15) {
        		usex=card.substring(14, 15);
        	}else if(card.length()==18) {
        		usex=card.substring(16, 17);
        	}
        	if (Integer.parseInt(usex) % 2 == 0) {
    			 return 2;//女
    		} else {
    			 return 1;//男
    		}
    
    }
    
    
}
