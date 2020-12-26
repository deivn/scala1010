package com.ymw.love.system.entity.seeDoctor.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("号源配置")
@Getter
@Setter
public class TakeNumberInput {

    @ApiModelProperty("医院ID")
    private String hospitalId;

    @ApiModelProperty("开始时间")
    private String startDate;

    @ApiModelProperty("结束时间")
    private String endDate;

//    private String dates;

    @ApiModelProperty("上午号源数量")
    private Integer amNumbers;

    @ApiModelProperty("下午号源数量")
    private Integer pmNumbers;

}
