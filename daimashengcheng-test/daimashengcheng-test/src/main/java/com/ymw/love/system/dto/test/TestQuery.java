package com.ymw.love.system.dto.test;

import java.io.Serializable;

import com.ymw.love.system.dto.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "测试请求体")
@Data
public class TestQuery extends BaseEntity implements Serializable {

    @ApiModelProperty(value = "名称", example = "11")
    private String name;

    @ApiModelProperty("性别")
    private Integer sex;
}
