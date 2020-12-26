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
@ConfigurationProperties(prefix="sms.messagemedia")
@PropertySource(value= {"classpath:/external/sms.properties","classpath:/external/outside.properties","classpath:/external/position.properties"},encoding="UTF-8")
@Getter
@Setter
public class SmsConfig {
    //--------飞鸽传信短信发送平台配置参数---------
    
    //account 飞鸽传信的账号
    private String account;
    //接口密钥
    private String pwd;
    //国际代码  美国1
    private String countryCode;

    //短信签名ID
    private String signId;
    //短信发送接口地址
    private String smsUrl;
    

}
