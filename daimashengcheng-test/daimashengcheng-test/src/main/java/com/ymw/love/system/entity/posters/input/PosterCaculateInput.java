package com.ymw.love.system.entity.posters.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("海报分享统计参数封装")
@Getter
@Setter
public class PosterCaculateInput {

    @ApiModelProperty("海报ID")
    private String postersId;

    @ApiModelProperty("被分享的APP标识 1.微信 2.朋友圈 3.QQ")
    private Integer appSign;
}
