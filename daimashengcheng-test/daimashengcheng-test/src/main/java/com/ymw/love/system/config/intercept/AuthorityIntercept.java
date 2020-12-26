package com.ymw.love.system.config.intercept;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.SysexMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ymw.love.system.common.Resource;
import com.ymw.love.system.common.Resource.userRole;
import com.ymw.love.system.config.excep.MissRequiredParamException;
import com.ymw.love.system.immobile.HintTitle;
import com.ymw.love.system.common.SystemEnum;
import com.ymw.love.system.redis.RedisService;
import com.ymw.love.system.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
* @author  作者 ：suhua
* @date 创建时间：2019年7月30日 上午10:36:06
* 
*类说明：权限拦截器、登录
*/
@CrossOrigin
@Slf4j
public class AuthorityIntercept implements HandlerInterceptor{
	
	@Autowired
	private UserLogInInfo userLogInInfo;
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
	  if(handler instanceof HandlerMethod) {
		  
		     HandlerMethod handlerMethod =(HandlerMethod) handler;
		     Method method = handlerMethod.getMethod();
	         Authority authority = method.getAnnotation(Authority.class);
		 if(StringUtils.isEmpty(authority)) {
			 return true;
		 } else {
			 
			// String sign= request.getHeader("SIGN");
			// if(StringUtils.isEmpty(sign)) {
			//		throw new MissRequiredParamException( SystemEnum.FAIL, HintTitle.System.sign_error);
			// }
			 
			  String token =request.getHeader("token");
			  //web用户
			 if(StringUtils.Equals(Resource.enter.web_user, authority.value())) {
				 userLogInInfo.verifyTokenWebUser(token, 1);
			 } else if(StringUtils.Equals(Resource.enter.web_user_dispensable, authority.value())) {//
				 userLogInInfo.verifyTokenWebUser(token, 2);
			 }else if(StringUtils.Equals(Resource.enter.admin_user, authority.value())) {
				 userLogInInfo.verifyTokenAdminbUser(token);
			 }else if(StringUtils.Equals(Resource.enter.user_login_state, authority.value())) {
				userLogInInfo.verifyUserLoginState(token);
			 }else {
				 throw new MissRequiredParamException( SystemEnum.PATH_ERROR, HintTitle.System.failed);
			 }
			 
			 return true;
		 } 
	         
		 
	         
	         
		  
	  }
	   
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

	
	
	
	
}
