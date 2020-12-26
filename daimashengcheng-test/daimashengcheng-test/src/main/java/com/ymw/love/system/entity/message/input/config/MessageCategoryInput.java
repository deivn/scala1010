package com.ymw.love.system.entity.message.input.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("消息类别添加参数")
@Getter
@Setter
public class MessageCategoryInput {

    @ApiModelProperty("类别ID")
    private String id;

    @ApiModelProperty("消息类别名")
    private String name;
}
