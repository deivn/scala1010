package com.ymw.love.system.entity.seeDoctor.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("线上线下操作参数封装")
@Getter
@Setter
public class TreadWayInput {

    @ApiModelProperty("订单ID")
    private String orderId;


    @ApiModelProperty("治疗方式1 线下 2线下")
    private Integer treatWay;

}
