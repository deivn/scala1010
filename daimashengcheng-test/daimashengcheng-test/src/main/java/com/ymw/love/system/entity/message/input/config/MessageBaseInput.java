package com.ymw.love.system.entity.message.input.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("基本封装类")
@Getter
@Setter
public class MessageBaseInput {

    @ApiModelProperty("消息分类ID")
    private String categoryId;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("内容")
    private String content;

}
