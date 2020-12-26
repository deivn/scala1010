package com.ymw.love.system.entity.seeDoctor.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("基本查询结果参数封装")
@Getter
@Setter
public class BaseQuery {

    @ApiModelProperty("序列号")
    private Integer seq;

    @ApiModelProperty("订单ID")
    private String orderId;

    @ApiModelProperty("用户昵称")
    private String userNickName;

    @ApiModelProperty("联系电话")
    private String patientPhone;

    @ApiModelProperty("患者的真实名字")
    private String patientName;

    @ApiModelProperty("身份证号")
    private String patientIdNumber;

    @ApiModelProperty("预约就诊日期")
    private String treatmentDate;

    @ApiModelProperty("1上午 2下午")
    private Integer dayHalfStatus;

    @ApiModelProperty("医院名称")
    private String hospitalTitle;

    @ApiModelProperty("就诊状态")
    private Integer optionStatus;
}
