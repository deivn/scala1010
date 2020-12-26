package com.ymw.love.system.config.intercept;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ymw.love.system.common.Resource;
import com.ymw.love.system.common.SystemEnum;
import com.ymw.love.system.config.excep.MissRequiredParamException;
import com.ymw.love.system.immobile.HintTitle;
import com.ymw.love.system.redis.RedisService;
import com.ymw.love.system.util.StringUtils;
import com.ymw.love.system.vo.AUserVO;
import com.ymw.love.system.vo.UUserVO;

/**
* @author  作者 ：suhua
* @date 创建时间：2019年8月5日 上午9:35:15
*类说明：用户登录后信息
*/
@Service
public class UserLogInInfo {
	
	@Autowired
	private HttpServletRequest httpServletRequest;
	
	@Autowired
	private  RedisService redisService; 
	
	
	/**
	 * 获取登录用户信息
	 * @return
	 */
	public UUserVO getWebUser() {
		Object o =httpServletRequest.getAttribute(Resource.enter.web_user);
		if(o==null) {
			return null;
		}
		return (UUserVO) o;
	}
	
	/**
	 * 获取后台登录用户信息
	 * @return
	 */
	public AUserVO getAdminUser() {
		Object o =httpServletRequest.getAttribute(Resource.enter.admin_user);
		if(o==null) {
			return null;
		}
		return (AUserVO) o;
	}
	
	
	
	
	/**
	 * 访问来源
	 * 1.pc,2.wap,3.ios,4.Android 
	 * @return
	 */
    public Integer getClientSource() {
    	String sign= httpServletRequest.getHeader("SIGN");
    	if(StringUtils.isNotEmpty(sign)) {
    		return  Integer.parseInt(sign);
    	}
    	return null;
    }	
	
	/**
	 * 验证    普通用户
	 * @param token
	 * @param Integer  类型 1：必须登录   2：非必须登录
	 */
	public void verifyTokenWebUser(String token,Integer type) {
		
		if(StringUtils.isEmpty(token) && type==2) {
			return ;
		}
		
		if(StringUtils.isEmpty(token)) {
			throw new MissRequiredParamException( SystemEnum.NO_AUTHORITY, HintTitle.System.Please_login);
		}
		
		
		Object o =	redisService.get(Resource.enter.web_user+token);
		if(o==null && type==1) {
			throw new MissRequiredParamException( SystemEnum.TOKEN_FAIL, HintTitle.System.token_fail);
		}
		
		
		if(o!=null) {
			httpServletRequest.setAttribute(Resource.enter.web_user, (UUserVO)o);			
		}
		
		
	}
	
	
	/**
	 * 获取当前访问用户token
	 * @return
	 */
	public String getToken() {
		 return httpServletRequest.getHeader("token");
	}
	
	/**
	 * 验证 后台用户
	 * @param token
	 */
	public void verifyTokenAdminbUser(String token) {
		if(StringUtils.isEmpty(token)) {
			throw new MissRequiredParamException( SystemEnum.NO_AUTHORITY, HintTitle.System.Please_login);
		}
		
		Object o =	redisService.get(Resource.enter.admin_user+token);
		if(o==null ) {
			throw new MissRequiredParamException( SystemEnum.TOKEN_FAIL, HintTitle.System.token_fail);
		}
		
		if(o!=null) {
			httpServletRequest.setAttribute(Resource.enter.admin_user, (AUserVO)o);			
		}
		
		
	}
	
	/**
	 * 验证 后台、 普通用户 是否登录状态
	 * @param token
	 */
	public void verifyUserLoginState(String token) {
		if(StringUtils.isEmpty(token)) {
			throw new MissRequiredParamException( SystemEnum.NO_AUTHORITY, HintTitle.System.Please_login);
		}
		
		Object o1 =	redisService.get(Resource.enter.web_user+token);
		if(StringUtils.isNotEmpty(o1)) {
			return ;
		}
		
		Object o2 =	redisService.get(Resource.enter.admin_user+token);
		
		if(StringUtils.isNotEmpty(o2)) {
			return ;
		}
		
		throw new MissRequiredParamException( SystemEnum.FAIL, HintTitle.System.Please_login);
		
	}
	
	

}
