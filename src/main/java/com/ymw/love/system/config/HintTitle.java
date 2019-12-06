package com.ymw.love.system.config;
/**
* @author  作者 ：suhua
* @date 创建时间：2019年12月6日 上午10:23:53
*类说明：
*/
public interface HintTitle {

	public interface Constant{
	    public  String QUERY_PARAM_INVALID = "日期选择参数不合法!";
	    public  String ENDDATE_INVALID = "结束时间不能小于起始时间!";
	}
	
	
	public interface System{
		 /**
		  * 操作失败
		  */
		 String failed="操作失败";
		 
		 /**
		  * token 失效
		  */
		 String token_fail="token失效";
		 
		 /**
		  * 请登录
		  */
		 String Please_login="请登录";
		
		   /**
			 * 解析错误
			 */
		String  parse_error="解析错误";
		
		/**
		 * sign不能为空
		 */
		String sign_error="sign不能为空";
		
		/**
		 * 权限不足
		 */
		String no_authority_error="权限不足";
		
		
		/**
		 * 传参格式不规范
		 */
		String parameter_format_error="传参格式不规范";
		
		/**
		 * 解密异常
		 */
		String decode_error="解密异常";
		
		/**
		 * 签名异常
		 */
		String signature_error="签名异常";
		
		/**
		 * 缺少加密参数
		 */
		String encrypt_error="缺少加密参数";
		/**
		 *该接口不需要加密
		 */
		String no_encrypt_error="该接口不需要加密";
		
		/**
		 * 设备异常
		 */
		String plant_error="设备异常";
		
	
		
	}
}
