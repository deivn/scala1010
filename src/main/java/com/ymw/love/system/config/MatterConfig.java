package com.ymw.love.system.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * Copyright (C), 2018-2018, ChengDu Lovo info. Co., Ltd.
 *
 * @BelongsPackage: 
 * @CreateDate: 2018/12/01/14:32
 * @version: 1.0
 * @Description: 手机号短信发送配置类
 */
@Component

@PropertySource(value= {"classpath:/matter/secret.properties"},encoding="UTF-8")
public class MatterConfig {
   
    

}
