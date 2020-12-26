package com.ymw.love.system.entity.seeDoctor.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("号源管理结果封装")
@Getter
@Setter
public class NumberQueryResult {

    @ApiModelProperty("序列号")
    private Integer seq;

    @ApiModelProperty("号源ID")
    private String id;

    @ApiModelProperty("医院名称")
    private String hospitalTitle;

    @ApiModelProperty("就诊日期")
    private String treatmentDate;

    @ApiModelProperty("总号源数")
    private Integer totalSource;

    @ApiModelProperty("上午已用")
    private Integer amSourceUsed;

    @ApiModelProperty("上午剩余")
    private Integer amSourceUnuse;

    @ApiModelProperty("下午已用")
    private Integer pmSourceUsed;

    @ApiModelProperty("下午剩余")
    private Integer pmSourceUnuse;

}
