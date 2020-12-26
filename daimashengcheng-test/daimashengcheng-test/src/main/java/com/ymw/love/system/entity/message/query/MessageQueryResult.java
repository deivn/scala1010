package com.ymw.love.system.entity.message.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("基本查询结果封装")
@Getter
@Setter
public class MessageQueryResult {

    @ApiModelProperty("序号")
    private Integer seq;

    @ApiModelProperty("消息ID")
    private String id;

    @ApiModelProperty("消息标题")
    private String title;

    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty("消息类别")
    private String categoryName;

    @ApiModelProperty("时间")
    private String dateTime;

//    @ApiModelProperty("可操作标识 6发送 3删除")
//    private Integer operable;

}
