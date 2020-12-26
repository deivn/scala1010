package com.ymw.love.system.dto.test;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@ApiModel(description = "请求实体")
@Data
public class TestRequest {

    @ApiModelProperty(value = "名称", example = "11")
    @NotNull(message = "名称不能为空！")
    private String name;

    @ApiModelProperty("性别0（女）或1（男）")
    @NotNull(message = "性别不能为空！")
    @Range(min = 0, max = 1, message = "请输入0（女）或1（男）")
    private Integer sex;

    @ApiModelProperty("年龄")
    @NotNull(message = "年龄不能为空！")
    @Range(min = 1, max = 100, message = "年龄输入1~100之间的数字")
    private Integer age;
}
