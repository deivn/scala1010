package com.ymw.love.system.entity.posters.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("海报操作参数输入封装")
@Getter
@Setter
public class PosterInput {

    @ApiModelProperty("海报ID")
    private String postersId;

    @ApiModelProperty("基本参数封装")
    private BaseInput baseInput;

}
