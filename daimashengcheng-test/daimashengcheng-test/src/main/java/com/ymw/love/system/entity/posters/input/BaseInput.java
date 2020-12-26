package com.ymw.love.system.entity.posters.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("基本参数封装")
@Getter
@Setter
public class BaseInput {

    @ApiModelProperty("海报名")
    private String postersName;

    @ApiModelProperty("海报描述")
    private String description;

    @ApiModelProperty("海报图片")
    private String postersImg;

}
