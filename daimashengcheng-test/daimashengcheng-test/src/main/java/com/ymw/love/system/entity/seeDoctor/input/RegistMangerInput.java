package com.ymw.love.system.entity.seeDoctor.input;

import com.ymw.love.system.dto.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("后台接口查询参数封装")
@Getter
@Setter
public class RegistMangerInput extends BaseEntity {

    @ApiModelProperty("开始时间")
    private String startDate;

    @ApiModelProperty("结束时间")
    private String endDate;

    @ApiModelProperty("1 今天  2 明天 3 未来3天 4 未来7天 5 未来1个月")
    private Integer dateValue;

    @ApiModelProperty("医院ID")
    private String hospitalId;

    @ApiModelProperty("用户名/手机号")
    private String patientDesc;

//    @ApiModelProperty("状态判断 就诊操作  1 待就诊 2 已就诊  3已取消 4.已失效 5.号源管理")
//    private Integer optionStatus;
}
