package com.ymw.love.system.dto;

import com.ymw.love.system.entity.UUser;

import lombok.Getter;
import lombok.Setter;

/**
* @author  作者 ：suhua
* @date 创建时间：2019年8月5日 下午2:26:30
*类说明：
*/
@Setter
@Getter
public class UsetBasicDTO extends UUser {

	/**
	 * 验证码类型
	 */
	private  Integer codeType;
	
	/**
	 * 验证码
	 */
	private String code;
	
	/**
	 * 收款号
	 */
	private String number;

	/**
	 * 新密码
	 */
	private String newPassword;
	
	/**
	 * 旧密码
	 */
	private String oldPassword;
	
}
