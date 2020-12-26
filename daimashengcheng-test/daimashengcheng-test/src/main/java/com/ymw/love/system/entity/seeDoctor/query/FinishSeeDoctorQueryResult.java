package com.ymw.love.system.entity.seeDoctor.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("已就诊结果封装")
@Getter
@Setter
public class FinishSeeDoctorQueryResult extends  BaseQuery{

    @ApiModelProperty("操作人")
    private String optionUser;
}
