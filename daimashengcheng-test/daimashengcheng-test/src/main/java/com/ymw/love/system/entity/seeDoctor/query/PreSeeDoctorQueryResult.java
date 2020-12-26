package com.ymw.love.system.entity.seeDoctor.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("待就诊结果封装")
@Getter
@Setter
public class PreSeeDoctorQueryResult extends  BaseQuery{
    
    @ApiModelProperty("取号密码")
    private String takePassword;
}
