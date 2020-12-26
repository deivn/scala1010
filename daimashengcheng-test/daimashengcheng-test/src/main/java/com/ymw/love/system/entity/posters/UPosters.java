package com.ymw.love.system.entity.posters;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.ymw.love.system.entity.posters.input.BaseInput;
import com.ymw.love.system.util.DateUtil;
import com.ymw.love.system.util.SnowFlakeUtil;
import com.ymw.love.system.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 海报
 * </p>
 *
 * @author 
 * @since 2019-08-22
 */
@TableName("u_posters")
@Getter
@Setter
public class UPosters extends Model<UPosters> {

    private static final long serialVersionUID = 1L;

    public UPosters(){}

	public UPosters(String optionUid){
    	this.optionUid = optionUid;
	}

    public UPosters(BaseInput baseInput, String optionUid){
		SnowFlakeUtil snowFlakeUtil = new SnowFlakeUtil(0, 0);
		this.id = String.valueOf(snowFlakeUtil.nextId());
		this.postersName = baseInput.getPostersName();
		this.postersImg = baseInput.getPostersImg();
		this.postersDesc = StringUtils.isEmpty(baseInput.getDescription())? "": baseInput.getDescription();
		this.qrCodePosition = "";
		this.optionUid = optionUid;
		this.publishTime = DateUtil.parseDateToStr(new Date(), DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
		this.createDate = DateUtil.parseDateToStr(new Date(), DateUtil.DATE_FORMAT_YYYY_MM_DD);
    }

    /**
     * 海报ID
     */
	@TableField("id")
	private String id;
    /**
     * 海报名称
     */
	@TableField("posters_name")
	private String postersName;
    /**
     * 海报图片
     */
	@TableField("posters_img")
	private String postersImg;
    /**
     * 海报描述
     */
	@TableField("posters_desc")
	private String postersDesc;
    /**
     * 二维码坐标信息
     */
	@TableField("qr_code_position")
	private String qrCodePosition;
    /**
     * 发布状态 1.待发布 2.已发布
     */
	@TableField("publish_status")
	private Integer publishStatus;
    /**
     * 1正常 2删除
     */
	@TableField("is_delete")
	private Integer isDelete;
	/**
	 * 操作人ID
	 */
	@TableField("option_uid")
	private String optionUid;

    /**
     * 发布时间（年月日-时分秒）
     */
	@TableField("publish_time")
	private String publishTime;
    /**
     * 创建日期(yyyy-MM-dd)
     */
	@TableField("create_date")
	private String createDate;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
