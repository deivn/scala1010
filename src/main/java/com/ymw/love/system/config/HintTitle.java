package com.ymw.love.system.config;


/**
* @author  作者 ：suhua
* @date 创建时间：2019年12月6日 上午10:23:53
*类说明：
*/
public interface HintTitle {

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
		 * 传参不能为空
		 */
		String parameter_is_null="传参不能为空";

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
		 * 文件数量超出范围
		 */
		String file_scope_error="文件数量超出范围";

		/**
		 * 设备异常
		 */
		String plant_error="设备异常";

		/**
		 * 视频格式错误
		 */
		String  video_format_error="视频格式错误";

		/**
		 * 图片格式错误
		 */
		String  img_format_error="图片格式错误";
		
		/**
		 * 微信授权失败
		 */
		String wei_xin_error="微信授权失败，请重新再授权";
		/**
		 * 请勿重复提交
		 */
		String no_repeat="请勿重复提交";
		
	}
}
