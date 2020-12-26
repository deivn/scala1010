package com.ymw.love.system.entity.message;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.ymw.love.system.entity.message.input.config.MessageBaseInput;
import com.ymw.love.system.util.CommonUtil;
import com.ymw.love.system.util.DateUtil;
import com.ymw.love.system.util.SnowFlakeUtil;
import com.ymw.love.system.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 消息配置表
 * </p>
 *
 * @author 
 * @since 2019-08-23
 */
@TableName("u_message_config")
@Getter
@Setter
public class UMessageConfig extends Model<UMessageConfig> {

    private static final long serialVersionUID = 1L;

	public UMessageConfig(){}
	
    public UMessageConfig(MessageBaseInput baseInput, String optionUid, String activityInfo){
		SnowFlakeUtil snowFlakeUtil = new SnowFlakeUtil(0, 0);
		this.id = String.valueOf(snowFlakeUtil.nextId());
		this.categoryId = baseInput.getCategoryId();
		this.title = baseInput.getTitle();
		this.content = baseInput.getContent();
		this.activityInfo = StringUtils.isEmpty(activityInfo)?"":activityInfo;
		this.optionUid = optionUid;
		this.addTime = DateUtil.parseDateToStr(new Date(), DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
		this.editTime = DateUtil.parseDateToStr(new Date(), DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
	}

	public UMessageConfig(String title, String content, String categoryId, String activityInfo, String optionUid){
		this.categoryId = categoryId;
		this.title = title;
		this.content = content;
		this.activityInfo = StringUtils.isEmpty(activityInfo)?"":activityInfo;
		this.optionUid = optionUid;
		this.editTime = DateUtil.parseDateToStr(new Date(), DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
	}

    /**
     * 消息ID
     */
	@TableField("id")
	private String id;

	/**
	 * 类别ID外键
	 */
	@TableField("category_id")
	private String categoryId;
    /**
     * 消息标题
     */
	@TableField("title")
	private String title;
    /**
     * 消息内容
     */
	@TableField("content")
	private String content;

	/**
	 * 活动内容（不带富文本标签）
	 */
	@TableField("activity_info")
	private String activityInfo;
    /**
     * 1正常 2删除
     */
	@TableField("is_delete")
	private Integer isDelete;
    /**
     * 1待发送  2已发送
     */
	@TableField("option_status")
	private Integer optionStatus;
    /**
     * 操作人ID
     */
	@TableField("option_uid")
	private String optionUid;
    /**
     * 添加时间
     */
	@TableField("add_time")
	private String addTime;
    /**
     * 编辑时间
     */
	@TableField("edit_time")
	private String editTime;

	/**
	 * 发送时间
	 */
	@TableField("send_time")
	private String sendTime;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
