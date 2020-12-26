package com.ymw.love.system.entity.posters.input;

import com.ymw.love.system.dto.BaseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostersQueryInput extends BaseEntity {

    @ApiModelProperty("开始时间")
    private String startDate;

    @ApiModelProperty("结束时间")
    private String endDate;

    @ApiModelProperty("1 今天  2 昨天 3 最近3天 4 最近7天 5 最近1个月")
    private Integer dateValue;
}
