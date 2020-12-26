package com.ymw.love.system.entity.message.input.push;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("动态消息推送参数")
@Getter
@Setter
public class DynamicMessageInput {

    @ApiModelProperty("回复问答/评论的用户名")
    private String replyName;

    @ApiModelProperty("评论/回复内容")
    private String content;
}
