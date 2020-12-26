package com.ymw.love.system.entity.message.input.push;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("消息推送参数封装类")
@Getter
@Setter
public class MessagePushInput {

    @ApiModelProperty("小类操作 20、救助券消息 21、爱心券消息 " +
            "22、爱心券激活消息 23、提现通知 24、预约成功 " +
            "25、取消预约 26、预约过期 27、问答回复 " +
            "28.评论回复 29获得点赞")
    private Integer option;

    @ApiModelProperty("被推送方用户ID")
    private String destUid;

    @ApiModelProperty("业务ID")
    private String businessId;

    @ApiModelProperty("系统消息推送参数")
    private SystemMessageInput systemInput;

    @ApiModelProperty("动态消息推送参数")
    private DynamicMessageInput dynamicInput;

//    @ApiModelProperty("链接详情")
//    private String link;
    
}
