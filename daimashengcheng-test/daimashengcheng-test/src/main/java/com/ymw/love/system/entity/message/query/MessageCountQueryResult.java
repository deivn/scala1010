package com.ymw.love.system.entity.message.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("消息结果统计")
@Getter
@Setter
public class MessageCountQueryResult {

    @ApiModelProperty("消息分类 1.系统消息 2活动消息 3.动态消息")
    private Integer type;

    @ApiModelProperty("消息未读总数")
    private Integer messageCount;
}
