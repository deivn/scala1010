package com.ymw.love.system.config.intercept;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ymw.love.system.common.SystemEnum;
import com.ymw.love.system.config.HintTitle;
import com.ymw.love.system.config.excep.MissRequiredParamException;
import com.ymw.love.system.mq.sender.SysAdminSender;
import com.ymw.love.system.util.StringUtils;


import lombok.extern.slf4j.Slf4j;

/**
 * @author 作者 ：suhua
 * @date 创建时间：2019年7月30日 上午10:36:06
 * 
 *       类说明：验证后台权限
 */
@CrossOrigin
@Slf4j
public class AuthorityAdminIntercept implements HandlerInterceptor {

	@Autowired
	private UserLogInInfo userLogInInfo;
	
	@Autowired
	private SysAdminSender sysAdminSender;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			Authority authority = method.getAnnotation(Authority.class);
			//String key  = request.getRequestURI();
			String url  =	request.getServletPath();
			//String contextPath= request.getContextPath();
			
			if (StringUtils.isEmpty(authority) || !authority.holdUp()) {
				return true;
			} else {
				
				//使用@pathVariable
				if(authority.pathVariable()) {
					 Map<String, Object> maps =  (Map<String, Object>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
					    for (String keys : maps.keySet()) {
					    	url=url.replace("/"+maps.get(keys).toString(), "/*");
						}
				}
				
				// 验证用户权限
				Map<String, String> map = userLogInInfo.getAdminUser().getAuthoriUrl();
				if (!map.containsKey(url)) {
					throw new MissRequiredParamException( SystemEnum.NO_AUTHORITY, HintTitle.System.no_authority_error);
				}	
				
				if(StringUtils.isNotEmpty(authority.content())) {
					request.setAttribute("AUSERID", userLogInInfo.getAdminUser().getId());
					request.setAttribute("CONTENT", authority.content());
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
		
		//操作日志
       if(StringUtils.isNotEmpty(request.getAttribute("AUSERID"))) {
    	   sysAdminSender.sysAdminLog(request.getAttribute("AUSERID").toString(), request.getAttribute("CONTENT").toString());    	   
       }
	}

}
