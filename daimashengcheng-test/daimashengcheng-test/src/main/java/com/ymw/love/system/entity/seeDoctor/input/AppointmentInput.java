package com.ymw.love.system.entity.seeDoctor.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("挂号就诊参数封装")
@Getter
@Setter
public class AppointmentInput {

    @ApiModelProperty("医院ID")
    private String hospitalId;

    @ApiModelProperty("就诊日期（年-月-日）")
    private String treatmentDate;

    @ApiModelProperty("就诊时间（1星期一 2星期二 3星期三 4星期4 5星期五 6星期六 7星期天 ）")
    private Integer weekday;

    @ApiModelProperty("就诊时间1 上午 2下午")
    private Integer dayHalfStatus;

    @ApiModelProperty("就诊人手机号")
    private String phone;

    @ApiModelProperty("就诊人真实姓名")
    private String realName;

    @ApiModelProperty("就诊人身份证号")
    private String idNumber;

}
