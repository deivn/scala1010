package com.ymw.love.system.entity.seeDoctor.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("评价操作")
@Getter
@Setter
public class EvaluationOption {

    @ApiModelProperty("就诊评分")
    private Integer evaluationGrade;

    @ApiModelProperty("服务评价标签")
    private String evaluationLabel;

    @ApiModelProperty("评价内容")
    private String evaluationDetail;


}
