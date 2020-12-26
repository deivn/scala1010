package com.ymw.love.system.entity.message.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("基本查询结果封装")
@Getter
@Setter
public class MessageAdminQueryResult {

    @ApiModelProperty("序号")
    private Integer seq;

    @ApiModelProperty("类别ID")
    private String id;

    @ApiModelProperty("类别名称")
    private String categoryName;

    @ApiModelProperty("添加时间")
    private String createTime;
}
