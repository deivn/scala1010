package com.ymw.love.system.entity.seeDoctor.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("号源查询列表结果封装")
@Getter
@Setter
public class TakeNumberQueryResult {

    @ApiModelProperty("约定日期")
    private String appointDate;

    @ApiModelProperty("1星期一 2星期二 3星期三 4星期4 5星期五 6星期六 7星期天 ")
    private Integer weekDay;

    @ApiModelProperty("上午未使用数")
    private Integer amUnuse;

    @ApiModelProperty("上午是否已满 0满  1未满")
    private Integer amFull;

    @ApiModelProperty("下午未使用数")
    private Integer pmUnuse;

    @ApiModelProperty("下午是否已满 0满  1未满")
    private Integer pmFull;
}
