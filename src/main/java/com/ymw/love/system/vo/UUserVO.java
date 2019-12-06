package com.ymw.love.system.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
* @author  作者 ：suhua
* @date 用户登录信息
*类说明：
*/
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UUserVO {
	private String id;
	
	/**
	 * 昵称
	 */
	private String nickName;
	
	/**
	 * 用户角色
	 */
	private String roleCode;
	
	/**
	 * 邀请码
	 */
	private String inviteCode;
	
	/**
	 * 手机号码
	 */
	private String phone;
	
	/**
	 * 真实姓名
	 */
	private String name;
	
	/**
	 * 环信id
	 */
	private String huanxinId;
	
	/**
	 * 极光id
	 */
	private String jpushId;
	
	/**
	 * 1：正常 2：黑名单
	 */
	private Integer state;
	
	/**
	 * 登录设备
	 * 1.pc,2.wap,3.ios,4.Android 
	 */
	private Integer sign;
}
