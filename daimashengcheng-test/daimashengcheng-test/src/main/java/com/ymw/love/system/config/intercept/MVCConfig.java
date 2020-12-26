package com.ymw.love.system.config.intercept;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 
 * @author Administrator
 *
 */
@SpringBootConfiguration
@Order(1)
public class MVCConfig extends WebMvcConfigurerAdapter {

	 @Bean
    public AuthorityIntercept authorityIntercept() {
    	return new AuthorityIntercept();
    }
	
    @Bean
    public LogInterceptor logInterceptor() {
        return new LogInterceptor();
    }
    
    @Bean
    public AuthorityAdminIntercept getAuthorityAdminIntercept() {
    	return new AuthorityAdminIntercept();
    }
    
    
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	 registry.addInterceptor(authorityIntercept()).addPathPatterns("/**")
         .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
    	
        registry.addInterceptor(logInterceptor()).addPathPatterns("/**")
        .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
        
        registry.addInterceptor(getAuthorityAdminIntercept()).addPathPatterns("/admin/**")
        .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
        
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
