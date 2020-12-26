package com.ymw.love.system.entity.seeDoctor;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.ymw.love.system.util.SnowFlakeUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 取号表
 * </p>
 *
 * @author 
 * @since 2019-08-17
 */
@TableName("u_take_number")
@Getter
@Setter
public class UTakeNumber extends Model<UTakeNumber> {

    private static final long serialVersionUID = 1L;

    public UTakeNumber(){}

	public UTakeNumber(String hospitalId, String date, Integer amSource, Integer pmSource){
		SnowFlakeUtil snowFlakeUtil = new SnowFlakeUtil(0, 0);
		this.id = String.valueOf(snowFlakeUtil.nextId());
		this.hospitalId = hospitalId;
		this.amSource = amSource == null ? 0 : amSource;
		this.pmSource = pmSource == null ? 0 : pmSource;
		this.totalSource = this.amSource + this.pmSource;
		this.amSourceUsed = 0;
		this.amSourceUnuse = this.amSource;
		this.pmSourceUsed = 0;
		this.pmSourceUnuse = this.pmSource;
		this.flag = 1;
		this.createTime = date;
	}


    /**
     * 就诊号源管理表ID
     */
	private String id;
    /**
     * 医院ID
     */
	@TableField("hospital_id")
	private String hospitalId;
    /**
     * 总号源数
     */
	@TableField("total_source")
	private Integer totalSource;
    /**
     * 上午总号源
     */
	@TableField("am_source")
	private Integer amSource;
    /**
     * 上午已用号源数
     */
	@TableField("am_source_used")
	private Integer amSourceUsed;
    /**
     * 上午未用号源
     */
	@TableField("am_source_unuse")
	private Integer amSourceUnuse;
    /**
     * 下午总号源
     */
	@TableField("pm_source")
	private Integer pmSource;
    /**
     * 下午已用号源
     */
	@TableField("pm_source_used")
	private Integer pmSourceUsed;
    /**
     * 下午未用号码
     */
	@TableField("pm_source_unuse")
	private Integer pmSourceUnuse;
    /**
     * 号源是否可用 1 可用  2.不可用
     */
	@TableField("flag")
	private Integer flag;

	@TableField("create_time")
	private String createTime;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
