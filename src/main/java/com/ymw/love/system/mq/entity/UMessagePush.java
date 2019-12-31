package com.ymw.love.system.mq.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
public class UMessagePush extends Model<UMessagePush> implements Serializable{

    private static final long serialVersionUID = 1L;

	public UMessagePush(){}

	public UMessagePush(Integer jumpType, Integer subType, String bussinessId, String destUid, String title, String content){
		this.type = jumpType;
		this.subType = subType;
		this.businessId = bussinessId;
		this.destUid = destUid;
		this.title = title;
		this.content = content;
		this.createTime = new Date();
		this.updateTime = new Date();
	}

	public UMessagePush(Integer jumpType, String bussinessId){
		this.type = jumpType;
		this.businessId = bussinessId;
		this.createTime = new Date();
		this.updateTime = new Date();
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
	private Date createTime;
    /**
     * 更新时间
     */
	@TableField("update_time")
	private Date updateTime;

	/**
	 * 消息大类下的小类20.救助券消息 21.爱心券消息 22.爱心券激活
	 * 23.提现通知 24.预约成功 25.取消预约 26.预约过期 27.问答回复
	 * 28.评论回复 29.获得点赞 30.助力审核通过 31.助力审核拒绝
	 * 32.有人助力 33.海报发布 34.banner发布 36.被投诉人（后台处理结果为：投诉无效，违规警告，禁用24小时，永久禁用四种是地，处理结果都可收到）（系统消息）
	 * 37.抵扣成功 38.抵扣失败
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
