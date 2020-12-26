package com.ymw.love.system.entity.message;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.ymw.love.system.immobile.Constant;
import com.ymw.love.system.util.DateUtil;
import com.ymw.love.system.util.SnowFlakeUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 消息推送记录表
 * </p>
 *
 * @author 
 * @since 2019-08-24
 */
@TableName("u_message_push")
@Getter
@Setter
public class UMessagePush extends Model<UMessagePush> {

    private static final long serialVersionUID = 1L;

	public UMessagePush(){}

	public UMessagePush(Integer jumpType, Integer subType, String bussinessId, String destUid, String title, String content){
//		SnowFlakeUtil snowFlakeUtil = new SnowFlakeUtil(0, 0);
//		this.id = String.valueOf(snowFlakeUtil.nextId());
		this.type = jumpType;
		this.subType = subType;
		this.businessId = bussinessId;
		this.destUid = destUid;
		this.title = title;
		this.content = content;
		this.createTime = DateUtil.parseDateToStr(new Date(), DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
		this.updateTime = DateUtil.parseDateToStr(new Date(), DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
	}


    /**
     * 消息主键
     */
    
//	@TableField("id")
	@TableId(value="id", type= IdType.ID_WORKER_STR)
	private String id;
    /**
     * 消息分类 1.系统消息 2活动消息 3.动态消息
     */
	@TableField("type")
	private Integer type;
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
     * 被推送用户ID
     */
	@TableField("dest_uid")
	private String destUid;
    /**
     * 1 正常 2删除
     */
	@TableField("is_delete")
	private Integer isDelete;
    /**
     * 1 未读  2已读
     */
	@TableField("is_read")
	private Integer isRead;
    /**
     * 版本控制
     */
	@TableField("version")
	private Integer version;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private String createTime;
    /**
     * 更新时间
     */
	@TableField("update_time")
	private String updateTime;

	/**
	 * 消息大类下的小类20.救助券消息 21.爱心券消息 22.爱心券激活
	 * 23.提现通知 24.预约成功 25.取消预约 26.预约过期 27.问答回复
	 * 28.评论回复 29.获得点赞
	 */
	@TableField("sub_type")
	private Integer subType;

	/**
	 * 业务ID
	 */
	@TableField("business_id")
	private String businessId;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
