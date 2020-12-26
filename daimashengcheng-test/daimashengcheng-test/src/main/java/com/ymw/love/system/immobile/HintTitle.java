package com.ymw.love.system.immobile;
/**
* @author  作者 ：suhua
* @date 创建时间：2019年8月2日 下午7:14:48
*类说明：提示语
*/
public interface HintTitle {
	
	
	
	/**
	 * 用户
	 *
	 */
	public interface User{
		String user_account_exist="该用户已注册过";	
		/**
		 * 该手机号码尚未注册
		 */
		String user_phone_no_enroll="该手机号码尚未注册";	
		
		/**
		 * 邀请码有误
		 */
		String user_no_invite_code="邀请码有误";
	   
		/**
		 * 验证码已失效
		 */
		String verification_code_past="验证码已失效";
		
		/**
		 * 验证错误
		 */
		String verification_code_error="验证码错误";
		
		/**
		 * 密码位数
		 */
		String password_digit_error="密码位数错误";
		
		/**
		 * 账号或者密码错误
		 */
		String  account_password_error="账号或者密码错误";
		/**
		 * 账号或者验证码错误
		 */
		String  account_code_error="账号或者验证码错误";
		
		/**
		 * 身份证或者真实名字校验有误
		 */
		String id_card_name_error="身份证或者真实名字校验有误";
		
		/**
		 * 账号被禁用
		 */
		String  user_jy_error="账号被禁用";
		
		/**
		 * 密码错误
		 */
		String password_error="密码错误";
	}
	
	/**
	 *
	 */
	public interface System{
		/**
		 * 短信模块异常
		 */
		String System_505_SMS="短信模块异常";
		/**
		 * 传参不能为空
		 */
		 String parameter_is_null="传参不能为空";
		 /**
		  * 手机号码不规范
		  */
		 String phone_no_norm="手机号码不规范";
		 /**
		  * 错误类型
		  */
		 String error_type="错误类型";
		 
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
		 * 图片格式错误
		 */
		String  img_format_error="图片格式错误";
		
		/**
		 * 文件数量超出范围
		 */
		String file_scope_error="文件数量超出范围";
		/**
		 * 视频格式错误
		 */
		String  video_format_error="视频格式错误";
	}

}
