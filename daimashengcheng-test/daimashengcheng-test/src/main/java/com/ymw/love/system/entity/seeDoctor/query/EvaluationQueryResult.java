package com.ymw.love.system.entity.seeDoctor.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("医院评价查询")
@Getter
@Setter
public class EvaluationQueryResult {

    @ApiModelProperty("用户头像")
    private String imageUrl;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("用户评价打分")
    private Integer evaluationGrade;

    @ApiModelProperty("用户评价标签")
    private String evaluationLabel;

    @ApiModelProperty("评价内容")
    private String evaluationDetail;

    @ApiModelProperty("就诊时间")
    private String dateTime;
}
