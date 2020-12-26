package com.ymw.love.system.entity.message.input.push;

import com.ymw.love.system.dto.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("消息推送查询")
@Getter
@Setter
public class  MessageQueryInput extends BaseEntity {

    @ApiModelProperty("消息分类 1.系统消息 2活动消息 3.动态消息")
    private Integer type;
}
