package com.ymw.love.system.common;

/**
 * 系统统一返回编码
 * 
 * @author Administrator
 *
 */
public interface SystemEnum {

	/**
	 * 成功
	 */
	Integer SUCCESS = 0;

	/***
	 * 失败
	 */
	Integer FAIL = 1;
	
	
	/**
	 * 用户禁用
	 */
	Integer FAIL_USER_JY= 100;
	
	/***
	 * 失败 账号或者密码错误
	 */
	Integer FAIL_USER_PASSWORD_ERROR= 101;
	
	
	
	/**
	 * token 失效
	 */
	Integer TOKEN_FAIL=2;

	/**
	 * 权限不足
	 */
	Integer NO_AUTHORITY = 403;

	
	/**
	 * 系统繁忙
	 */
	Integer SYSTEM_ERROR = 500;
	
	/**
	 * 路径错误
	 */
	Integer PATH_ERROR = 404;

	

}
