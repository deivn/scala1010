package com.ymw.love.system.config;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.util.StringUtils;

/**
 * @author 作者 ：suhua
 * @date 创建时间：2019年3月29日 下午3:09:48 类说明：拦截@ResponseBody
 */
@ControllerAdvice
public class JsonViewResponseBodyAdvice implements ResponseBodyAdvice {

	@Override
	public boolean supports(MethodParameter returnType, Class converterType) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
		if (body instanceof Result) {
             return filterParameter((Result) body);
		}

		return body;
	}

	private Object filterParameter(Result data) {
		PropertyFilter profilter = null;
		if (StringUtils.isNotEmpty(data.getRemove()) || StringUtils.isNotEmpty(data.getRetain())) {
			boolean de= StringUtils.isNotEmpty(data.getRetain()) ;
			String[] r =de? data.getRetain() : data.getRemove();
			List list = Arrays.asList(r);
			profilter = new PropertyFilter() {
				@Override
				public boolean apply(Object object, String name, Object value) {	
					if(list.contains(name)) {
						return de;
					}				 
					return !de;
				}
			};
			
			data.setRemove(null);
			data.setRetain(null);
			data.setData(JSON.parse(JSON.toJSONString(data.getData(),profilter)));
		}

		return data;
	}

}
