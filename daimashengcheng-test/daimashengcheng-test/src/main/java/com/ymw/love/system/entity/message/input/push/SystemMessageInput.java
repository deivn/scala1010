package com.ymw.love.system.entity.message.input.push;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("系统消息参数传入封装")
@Getter
@Setter
public class SystemMessageInput {
    /**
     * 救助券消息  欢迎加入类风湿互助社区，你的100元救助券已发送。   查看详情（跳转至我的券包）
     * 爱心券已激活  你的200元爱心券已激活，可在规定时间内申请提现。查看详情（跳转至可提现金额详情页面）
     *
     */

    public SystemMessageInput(){}

    public SystemMessageInput(String appointDetail){
        this.appointDetail = appointDetail;
    }

    //爱心券消息
    @ApiModelProperty("手机号")
    private String phone;

    //提现通知
    @ApiModelProperty("微信号")
    private String weChartAccount;

    @ApiModelProperty("金额")
    private String amount;

    @ApiModelProperty("预约详情(湖南分部2019年08月16日（星期五）上午8：00-12：00)")
    private String appointDetail;

    public SystemMessageInput(String phone, String amount,String weChartAccount) {
        this.phone = phone;
        this.amount = amount;
        this.weChartAccount=weChartAccount;
    }
    public SystemMessageInput(String phone, String amount) {
        this.phone = phone;
        this.amount = amount;
    }
}
