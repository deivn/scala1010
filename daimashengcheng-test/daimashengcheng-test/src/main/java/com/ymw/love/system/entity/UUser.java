package com.ymw.love.system.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author
 * @since 2019-08-02
 */
@TableName("u_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UUser {

	private static final long serialVersionUID = 1L;

	private String id;
	/**
	 * 昵称
	 */
	@TableField("nick_name")
	private String nickName;
	/**
	 * 用户相片
	 */
	@TableField("image_url")
	private String imageUrl;
	/**
	 * 用户角色
	 */
	@TableField("role_code")
	private String roleCode;
	
	/**
	 * 邀请码
	 */
	@TableField("invite_code")
	private String inviteCode;
	/**
	 * 手机号码
	 */
	private String phone;
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 微信号
	 */
	//private String weixin;
	
	/**
	 * 微信号图片
	 */
	//@TableField("weixin_url")
	//private String weixinUrl;
	
	/**
	 * 支付宝
	 */
	//private String zfb;
	
	
	/**
	 * 身份证
	 */
	private String idcard;
	/**
	 * 真实姓名
	 */
	private String name;
	
	/**
	 * 性别：1男 2女 
	 */
	private Integer sex;
	
	/**
	 * 出生日期
	 */
	private Date birthdate;
	
	/**
	 * 创建时间
	 */
	@TableField("creates_time")
	private Date createsTime;
	/**
	 * 身份证验证时间
	 */
	@TableField("idcard_time")
	private Date idcardTime;
	/**
	 * 环信id
	 */
	@TableField("huanxin_id")
	private String huanxinId;
	/**
	 * 极光id
	 */
	@TableField("jpush_id")
	private String jpushId;
	
	/**
	 * 用户登录最后时间
	 */
	@TableField("login_time")
	private Date loginTime;
	
	/**
	 * 1：正常 2：禁用
	 */
	private Integer state;
	
	/**
	 * 设置通知状态 ：1 启动，2关闭
	 */
	@TableField("notify_state")
	private Integer notifyState;
	
	/**
	 * 禁用原因
	 */
	private String cause;
	

	// ==========扩展表===============
	/**
	 * 用户id
	 */
	@TableField(exist = false)
	private Integer uId;

	/**
	 * 发布文章次数
	 */
	@TableField(exist = false)
	private Integer essayCount;

	/**
	 * 积分
	 */
	@TableField(exist = false)
	private Integer score;

	/**
	 * 累计金额
	 */
	@TableField(exist = false)
	private Double totalMoney;

	/**
	 * 可提现金额
	 */
	@TableField(exist = false)
	private Double money;

	/**
	 * 问答次数
	 */
	@TableField(exist = false)
	private Integer faqsCount;
	
	/**
	 * 好友总数
	 */
	@TableField(exist = false)
	private Integer friendCount;
	
	
	
	
	
}
