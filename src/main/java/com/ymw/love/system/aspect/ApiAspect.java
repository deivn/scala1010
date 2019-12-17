package com.ymw.love.system.aspect;

import com.ymw.love.system.common.Result;
import com.ymw.love.system.config.excep.MissRequiredParamException;
import com.ymw.love.system.config.intercept.ApiToken;
import com.ymw.love.system.redis.RedisService;
import com.ymw.love.system.util.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author zsy
 * @version 1.1  controller
 * @date
 */
@Aspect
@Component
public class ApiAspect {

    @Autowired
    private RedisService redisService;

    @Pointcut("@annotation(com.ymw.love.system.config.intercept.ApiToken)")
    public void apiToken() {

    }

    // 环绕通知
    @Around("apiToken()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        ApiToken apiToken = methodSignature.getMethod().getDeclaredAnnotation(ApiToken.class);
        if (apiToken != null) {
            HttpServletRequest request = getRequest();
            String token = request.getParameter("token");

            if (StringUtils.isEmpty(token)) {
                throw new MissRequiredParamException("请求参数错误");
            }
            boolean isToken = Optional.ofNullable(redisService.get(token)).isPresent();

            if (!isToken) {
                throw new MissRequiredParamException("请勿重复提交");
            }

        }
        // 放行
        Object proceed = proceedingJoinPoint.proceed();
        return proceed;
    }


    public HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        return request;
    }
}
