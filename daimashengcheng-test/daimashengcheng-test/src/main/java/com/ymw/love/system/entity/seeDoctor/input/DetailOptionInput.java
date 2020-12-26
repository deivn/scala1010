package com.ymw.love.system.entity.seeDoctor.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("用户操作参数封装")
@Getter
@Setter
public class DetailOptionInput {

    @ApiModelProperty("操作 2 取消预约  4去评价")
    private String option;

    @ApiModelProperty("订单号")
    private String orderId;

    @ApiModelProperty("去评价操作")
    private EvaluationOption evaluationOption;

}
