package com.ymw.love.system.config.secret;

import java.util.ArrayList;
import java.util.List;


import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;


/**
* @author  作者 ：suhua
* @date 创建时间：2019年3月29日 下午2:35:52
*类说明：把原生json解析换成 阿里的json解析器
*/
@Configuration
public class ResponseBodyConfig {
	@Bean
	public HttpMessageConverters fastJsonHttpMessageConverters() {
		FastJsonHttpMessageConverter oFastConverter = new FastJsonHttpMessageConverter();
		FastJsonConfig oFastJsonConfig = new FastJsonConfig();
		oFastJsonConfig.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect);
		oFastConverter.setFastJsonConfig(oFastJsonConfig);
		// 处理中文乱码问题
		List<MediaType> oFastMediaTypeList = new ArrayList<>();
		oFastMediaTypeList.add(MediaType.APPLICATION_JSON_UTF8);
		oFastConverter.setSupportedMediaTypes(oFastMediaTypeList);

		HttpMessageConverter<?> oConverter = oFastConverter;
		return new HttpMessageConverters(oConverter);
	}
}
