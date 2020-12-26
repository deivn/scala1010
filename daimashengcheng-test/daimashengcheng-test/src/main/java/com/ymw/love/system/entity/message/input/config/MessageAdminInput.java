package com.ymw.love.system.entity.message.input.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("后台消息配置参数封装")
@Getter
@Setter
public class MessageAdminInput {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("基本参数")
    private MessageBaseInput baseInput;


}
