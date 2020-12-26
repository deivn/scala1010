package com.ymw.love.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("医院评价查询结果封装")
@Getter
@Setter
public class EvaluationQueryResultVO {

    @ApiModelProperty("序列号")
    private Integer seq;

    @ApiModelProperty("评价ID")
    private String evaluationId;

    @ApiModelProperty("评价内容")
    private String evaluationDetail;

    @ApiModelProperty("评价用户名")
    private String patientName;

    @ApiModelProperty("发布时间")
    private String dateTime;

    @ApiModelProperty("医院名")
    private String hospitalName;

}
