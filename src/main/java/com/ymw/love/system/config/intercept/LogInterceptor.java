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
	         log.info("开始时间:"+ (new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date))+"  URL:" + request.getRequestURL().toString()+" 标注号:"+mi +" IP:"+getIPAddress(request)+" 终端:"+request.getHeader("SIGN")+" 版本:"+request.getHeader("version"));
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
	
	public  String getIPAddress(HttpServletRequest request) {
		String ip = null;

		// X-Forwarded-For：Squid 服务代理
		String ipAddresses = request.getHeader("X-Forwarded-For");

		if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
			// Proxy-Client-IP：apache 服务代理
			ipAddresses = request.getHeader("Proxy-Client-IP");
		}

		if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
			// WL-Proxy-Client-IP：weblogic 服务代理
			ipAddresses = request.getHeader("WL-Proxy-Client-IP");
		}

		if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
			// HTTP_CLIENT_IP：有些代理服务器
			ipAddresses = request.getHeader("HTTP_CLIENT_IP");
		}

		if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
			// X-Real-IP：nginx服务代理
			ipAddresses = request.getHeader("X-Real-IP");
		}

		// 有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
		if (ipAddresses != null && ipAddresses.length() != 0) {
			ip = ipAddresses.split(",")[0];
		}

		// 还是不能获取到，最后再通过request.getRemoteAddr();获取
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
