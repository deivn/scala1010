package com.ymw.love.system.entity.seeDoctor.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("取消就诊结果封装")
@Getter
@Setter
public class CancelSeeDoctorQueryResult extends  BaseQuery{

    @ApiModelProperty("就诊取消时间")
    private String cancelTime;
}
