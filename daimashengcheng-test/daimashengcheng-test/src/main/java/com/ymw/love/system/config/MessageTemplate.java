package com.ymw.love.system.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Copyright (C), 2018-2018, ChengDu Lovo info. Co., Ltd.
 *
 * @BelongsPackage: 
 * @CreateDate: 2018/12/01/14:32
 * @version: 1.0
 * @Description: 手机号短信发送配置类
 */
@Component
@ConfigurationProperties(prefix="message")
@PropertySource(value= {"classpath:/external/message.properties"},encoding="UTF-8")
@Getter
@Setter
public class MessageTemplate {

    private String salvationTitle;

    private String salvationContent;

    private String loveTitle;

    private String loveContent;

    private String loveActivationTitle;

    private String loveActivationContent;

    private String withdrawTitle;

    private String withdrawContent;

    private String appointSuccessTitle;

    private String appointSuccessContent;

    private String appointCancelTitle;

    private String appointCancelContent;

    private String outDateTitle;

    private String outDateContent;

    private String questionReplyTitle;

    private String questionReplyContent;

    private String commentReplyTitle;

    private String commentReplyContent;

    private String likeTitle;

    private String likeContent;
    

}
