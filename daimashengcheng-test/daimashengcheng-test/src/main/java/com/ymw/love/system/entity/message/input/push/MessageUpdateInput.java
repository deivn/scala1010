package com.ymw.love.system.entity.message.input.push;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageUpdateInput {

    @ApiModelProperty("批量修改记录值")
    private String ids;

}
