package com.ymw.love.system.entity.seeDoctor;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.ymw.love.system.entity.seeDoctor.input.EvaluationOption;
import com.ymw.love.system.util.DateUtil;
import com.ymw.love.system.util.SnowFlakeUtil;
import com.ymw.love.system.vo.UUserVO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 患者就诊评价表
 * </p>
 *
 * @author 
 * @since 2019-08-17
 */
@TableName("u_patient_evaluation")
@Getter
@Setter
public class UPatientEvaluation extends Model<UPatientEvaluation> {

    private static final long serialVersionUID = 1L;

    public UPatientEvaluation(){}

    public UPatientEvaluation(EvaluationOption evaluationOption, UUserVO webUser, String hospitalId, String treatmentDate){
		SnowFlakeUtil snowFlakeUtil = new SnowFlakeUtil(0, 0);
    	this.id = String.valueOf(snowFlakeUtil.nextId());
    	this.userId = webUser.getId();
    	this.hospitalId = hospitalId;
    	this.treatmentDate = treatmentDate;
    	this.evaluationGrade = evaluationOption.getEvaluationGrade();
    	this.evaluationLabel = evaluationOption.getEvaluationLabel();
    	this.evaluationDetail = evaluationOption.getEvaluationDetail();
    	this.createTime = DateUtil.parseDateToStr(new Date(), DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
    	this.dateTime = DateUtil.parseDateToStr(new Date(), DateUtil.DATE_FORMAT_YYYY_MM_DD);
	}

	private String id;
    /**
     * 医院ID
     */
	@TableField("hospital_id")
	private String hospitalId;

	/**
	 * 用户ID
	 */
	@TableField("user_id")
	private String userId;
    /**
     * 就诊评分
     */
	@TableField("evaluation_grade")
	private Integer evaluationGrade;
    /**
     * 服务评价标签，多个评价，以逗号分割（对应sys_dict配置里的值）
     */
	@TableField("evaluation_label")
	private String evaluationLabel;
    /**
     * 评价内容
     */
	@TableField("evaluation_detail")
	private String evaluationDetail;

	/**
	 * 就诊日期（年-月-日）
	 */
	@TableField("treatment_date")
	private String treatmentDate;
    /**
     * 评价时间
     */
	@TableField("create_time")
	private String createTime;

	/**
	 * 评价时间 yyyy-MM-dd
	 */
	@TableField("date_time")
	private String dateTime;

	/**
	 * 1 正常 2删除
	 */
	@TableField("is_delete")
	private Integer isDelete;


	/**
	 * 操作人ID
	 */
	@TableField("option_uid")
	private String optionUid;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
