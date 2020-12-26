package com.ymw.love.system.service;

import com.ymw.love.system.mapper.MapperFactory;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 业务实现基本类， 业务实现类需要继承该类继承
 */
@Getter
public class BaseService {

    @Autowired
    protected ServiceFactory sf;

    @Autowired
    protected MapperFactory mf;

    public static String getCurrentToken() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        if (attributes == null) {
            return null;
        }
        HttpServletRequest request = attributes.getRequest();
        return request.getHeader("token");
    }
}
