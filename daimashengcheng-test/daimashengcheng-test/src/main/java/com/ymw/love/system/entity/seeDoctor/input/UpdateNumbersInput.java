package com.ymw.love.system.entity.seeDoctor.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("编辑号源封装类")
@Getter
@Setter
public class UpdateNumbersInput {

    @ApiModelProperty("号源ID")
    private String id;

    @ApiModelProperty("预约日期")
    private String appointDate;

    @ApiModelProperty("上午的号码数")
    private Integer amNumbers;


    @ApiModelProperty("下午的号源数")
    private Integer pmNumbers;
}
