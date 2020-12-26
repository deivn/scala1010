package com.ymw.love.system.entity.message;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.ymw.love.system.util.DateUtil;
import com.ymw.love.system.util.SnowFlakeUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 消息类别表
 * </p>
 *
 * @author 
 * @since 2019-08-23
 */
@TableName("u_message_category")
@Getter
@Setter
public class UMessageCategory extends Model<UMessageCategory> {

    private static final long serialVersionUID = 1L;

    public UMessageCategory(){}

    public UMessageCategory(String name, String optionUid){
		SnowFlakeUtil snowFlakeUtil = new SnowFlakeUtil(0, 0);
		this.id = String.valueOf(snowFlakeUtil.nextId());
    	this.name = name;
    	this.optionUid = optionUid;
    	this.createTime = DateUtil.parseDateToStr(new Date(), DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
	}

	public UMessageCategory(String optionUid){
    	this.editTime = DateUtil.parseDateToStr(new Date(), DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
    	this.optionUid = optionUid;
	}

    /**
     * 类别ID
     */
	@TableField("id")
	private String id;
    /**
     * 类别名
     */
	@TableField("name")
	private String name;
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
     * 添加时间
     */
	@TableField("create_time")
	private String createTime;

	/**
	 * 编辑时间
	 */
	@TableField("edit_time")
	private String editTime;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
