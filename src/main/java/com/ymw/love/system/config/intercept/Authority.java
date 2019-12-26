package com.ymw.love.system.config.intercept;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Authority {
	String value() default "";

	String content() default "";// 操作内容

	String method() default "";// 操作方法名

	String url() default "";// 操作方法名

	/**
	 * 是否要权限访问 true：需要， false:不需要权限
	 * 
	 * @return
	 */
	boolean holdUp() default true;

	/**
	 * 接口参数是否要加密： true：需要， false:不需要
	 * 
	 * @return
	 */
	boolean des() default false;

	/**
	 * 接口参数是否要签名： true：需要， false:不需要
	 * 
	 * @return
	 */
	boolean sign() default false;
	
	/**
	 * 是否使用 PathVariable  默认false：不使用，true使用
	 * @return
	 */
	boolean pathVariable() default false;
}
