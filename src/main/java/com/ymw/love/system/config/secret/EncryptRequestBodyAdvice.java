package com.ymw.love.system.config.secret;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import com.ymw.love.system.config.intercept.Authority;

/**
 * @author 作者 ：suhua
 * @date 创建时间：2019年10月24日 上午10:59:52 类说明：
 * 拦截RequestBody数据执行解密、签名验证
 */

@ControllerAdvice
public class EncryptRequestBodyAdvice implements RequestBodyAdvice {

	// 私钥
	@Value("${private.key}")
	private String privateKey;

	@Value("${sign.rule}") // 签名取key规则
	private String rule;

	@Autowired
	private HttpServletRequest httpServletRequest;

	@Override
	public boolean supports(MethodParameter methodParameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
		// TODO Auto-generated method stub
		Method method = parameter.getMethod();
		Authority authority = method.getAnnotation(Authority.class);
		if (authority == null || (!authority.des() && !authority.sign())) {
			return inputMessage;
		}
		
          //解密、签名
		return new DecryptHttpInputMessage(inputMessage, authority,httpServletRequest,privateKey,rule);
	}

	@Override
	public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) {
		// TODO Auto-generated method stub
		return body;
	}

	@Override
	public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
			Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
		// TODO Auto-generated method stub
		return body;
	}

}
