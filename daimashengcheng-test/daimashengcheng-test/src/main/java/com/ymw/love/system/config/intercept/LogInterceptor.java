package com.ymw.love.system.config.intercept;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

/**
 * 记录日志拦截器
 * @author Administrator
 *
 */
@CrossOrigin
@Slf4j
public class LogInterceptor implements HandlerInterceptor{
	
	

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
	       
		    log.info("======================================================================");
	        //log.info("IP---------------------》" + request.getRemoteAddr());
		    // log.info("方法---------------------》" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
	        Date date = new Date();
	         long  mi=   System.currentTimeMillis();
	         log.info("开始时间:"+ (new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date))+"  URL:" + request.getRequestURL().toString()+" 标注号:"+mi );
            request.setAttribute("dateS", date.getTime());
            request.setAttribute("dateSmi", mi);
	        
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {	
		  long date=  Long.parseLong(request.getAttribute("dateS").toString());
	      String mi=request.getAttribute("dateSmi").toString();
	      Date afterDate = new Date();
          log.info("结束时间:" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(afterDate))+"  运行时长:"+(afterDate.getTime() - date)+"ms  标注号:"+mi);
	}
	
	
}
