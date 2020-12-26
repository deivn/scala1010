package com.ymw.love.system.entity.seeDoctor.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@ApiModel("就诊记录结束查询封装")
@Setter
@Getter
public class SeeDoctorQueryResult {

    @ApiModelProperty("订单号")
    private String orderId;

    @ApiModelProperty("就诊操作  1 待就诊 2 已就诊  3已失效")
    private Integer optionStatus;

    @ApiModelProperty("就诊人")
    private String patientName;

    @ApiModelProperty("就诊日期")
    private String treatmentDate;

    @ApiModelProperty("就诊时间 1星期一 2星期二 3星期三 4星期4 5星期五 6星期六 7星期天")
    private Integer weekday;

    @ApiModelProperty("1 上午 2下午")
    private Integer dayHalfStatus;

    @ApiModelProperty("取号密码")
    private String takePassword;

    @ApiModelProperty("医院首图")
    private String imageUrl;

    @ApiModelProperty("医院标题")
    private String title;

    @ApiModelProperty("医院地址")
    private String site;

    @ApiModelProperty("医院电话，多个电话用逗号分割")
    private String phone;

    @ApiModelProperty("附加操作 2 取消预约 3 在线咨询 4去评价")
    private String additionalActions;


}
