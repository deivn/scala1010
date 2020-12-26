package com.ymw.love.system.config.intercept;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authority {
    String value() default "";

    String content() default "";//操作内容

    String method() default "";//操作方法名

    String url() default "";//操作方法名
    
    boolean holdUp() default true; //是否要权限访问 true：需要，  false:不需要权限
}

