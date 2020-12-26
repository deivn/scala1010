package com.ymw.love.system.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * <p>
 * 后台用户
 * </p>
 *
 * @author 
 * @since 2019-08-15
 */
@TableName("a_user")
@Data
public class AUser  {


	@TableId(type = IdType.ID_WORKER_STR)
	private String id;
    /**
     * 名字
     */
	private String name;
    /**
     * 用户名
     */
	@TableField("user_name")
	private String userName;
    /**
     * 手机号码
     */
	private String phone;
    /**
     * 密码
     */
	private String password;
    /**
     * 职位 关联字典
     */
	@TableField("position_id")
	private Integer positionId;
    /**
     * 部门 关联字典表
     */
	@TableField("sector_id")
	private Integer sectorId;
    /**
     * 医院id
     */
	@TableField("hospital_id")
	private String hospitalId;
    /**
     * 角色编号
     */
	@TableField("role_code")
	private String roleCode;
    /**
     * 状态：1正常，2禁用，3删除
     */
	private Integer state;
    /**
     * 创建时间
     */
	@TableField("creates_time")
	private Date createsTime;
	
	/**
	 * 登录时间
	 */
	@TableField("login_time")
	private Date loginTime;



}
