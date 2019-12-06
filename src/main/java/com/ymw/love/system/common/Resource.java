package com.ymw.love.system.common;

import lombok.Getter;
import lombok.Setter;

/**
* @author  作者 ：suhua
* @date 创建时间：2019年8月2日 
*类说明：固定静态资源
*/
public interface Resource {
	
	/**
	 * 
	 * 默认用户注册角色编号
	 *
	 */
	public interface userRole{
		
		String roleCode="QWE145";
		
	}
      
	/**
	 * 平台标识
	 */
	public interface SystemSign{
		/**
		 * 互助平台
		 */
		 Integer signLove=1;
		 /**
		  * 后台管理平台
		  */
		 Integer signManage=2;
	}
	
	/**
	 * 访问该接口需要登录基本权限
	 */
    public interface enter{
    	
    	/**
    	 * web 用户
    	 */
    	String web_user="webUser";
    	
    	/**
    	 * 后台 用户
    	 */
    	String admin_user="adminUser"; 
    	
    	/**
    	 * web 用户 非登录状态
    	 */
    	String web_user_dispensable="webUserDispensable";
    	
    	/**
    	 * 判断是否登录状态（平台全部用户）
    	 */
    	String user_login_state="UserLoginState";
    	
    	/**
    	 * 不需要登录状态
    	 */
    	String no_login_state="noLoginState";
    	
    	/**
    	 * 判断是否禁用24小时
    	 */
    	String user_forbidden_24="user24";
    }
	
    /**
     *各种验证码开头标识 
     */
	public enum verificationFront{
		/**
		 * 注册
		 */
		ENROL(1,"enroll_"),
		
		/**
		 * 登录
		 */
		LOG_IN(2,"logIn_"),
		/**
		 * 找回密码
		 */
		FIND_PASSWORD(3,"find_password_"),
		
		/**
		 * 助力验证码
		 */
	    SICK_AID(4,"sick_aid_")
		;
		
		
		@Getter
		@Setter
	   private String  value;
		
		@Getter
		@Setter
	   private Integer key;
	   
	   verificationFront(Integer key,String  value){
		   this.key=key;
		   this.value=value;
	   }
		
	
		
		
	}
	
}
