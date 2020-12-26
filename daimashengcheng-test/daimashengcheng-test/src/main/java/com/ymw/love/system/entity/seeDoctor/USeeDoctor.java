package com.ymw.love.system.entity.seeDoctor;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.ymw.love.system.entity.seeDoctor.input.AppointmentInput;
import com.ymw.love.system.immobile.Constant;
import com.ymw.love.system.util.DateUtil;
import com.ymw.love.system.util.SnowFlakeUtil;
import com.ymw.love.system.vo.UUserVO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 就诊表
 * </p>
 *
 * @author 
 * @since 2019-08-17
 */
@TableName("u_see_doctor")
@Getter
@Setter
public class USeeDoctor extends Model<USeeDoctor> {

    private static final long serialVersionUID = 1L;

    public USeeDoctor(){}


    public USeeDoctor(AppointmentInput input, UUserVO webUser, String takePassword){
		SnowFlakeUtil snowFlakeUtil = new SnowFlakeUtil(0, 0);
    	this.orderId = String.valueOf(snowFlakeUtil.nextId());
    	this.userId = webUser.getId();
    	this.hospitalId = input.getHospitalId();
    	this.patientName = input.getRealName();
    	this.patientPhone = input.getPhone();
    	this.patientIdNumber = input.getIdNumber();
    	this.treatmentDate = input.getTreatmentDate();
    	this.weekday = input.getWeekday();
    	this.dayHalfStatus = input.getDayHalfStatus();
    	this.takePassword = takePassword;
    	this.optionStatus = Constant.PRE_SEE_DOCTOR;
    	this.createTime = DateUtil.parseDateToStr(new Date(), DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
	}

    /**
     * 医院订单表
     */
	@TableField("order_id")
	private String orderId;

	/**
	 * 医院ID外键
	 */
	@TableField("hospital_id")
	private String hospitalId;
    /**
     * 患者用户ID
     */
	@TableField("user_id")
	private String userId;
    /**
     * 就诊人
     */
	@TableField("patient_name")
	private String patientName;
    /**
     * 就诊人联系电话
     */
	@TableField("patient_phone")
	private String patientPhone;
    /**
     * 就诊人身份证号
     */
	@TableField("patient_id_number")
	private String patientIdNumber;
    /**
     * 就诊日期（年-月-日）
     */
	@TableField("treatment_date")
	private String treatmentDate;
	/**
	 * 1星期一 2星期二 3星期三 4星期4 5星期五 6星期六 7星期天
	 */
	@TableField("weekday")
	private Integer weekday;

	/**
	 * 1 上午 2下午
	 */
	@TableField("day_half_status")
	private Integer dayHalfStatus;
    /**
     * 取号密码
     */
	@TableField("take_password")
	private String takePassword;
    /**
     * 就诊操作  1 待就诊 2 已就诊  3已失效
     */
	@TableField("option_status")
	private Integer optionStatus;

	/**
	 * 就诊方式1 线下  2线上
	 */
	@TableField("treat_way")
	private Integer treatWay;

	/**
	 * 操作人ID
	 */
	@TableField("act_uid")
	private String actUid;

	/**
	 * 取消就诊时间（年-月-日 时:分:秒）
	 */
	@TableField("cancel_time")
	private String cancelTime;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private String createTime;


	@Override
	protected Serializable pkVal() {
		return this.orderId;
	}

}
