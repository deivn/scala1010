package com.ymw.love.system.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * <p>
 * 系统日志表
 * </p>
 *
 * @author 
 * @since 2019-08-15
 */
@TableName("sys_log")
@Data
public class SysLog implements  Serializable {
	private static final long serialVersionUID = -4552896125583606816L;
	
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 类型：1后台操作日志
     */
	private Integer type;
    /**
     * 用户id
     */
	@TableField("auser_id")
	private String auserId;
    /**
     * 描述
     */
	@TableField("action_describe")
	private String actionDescribe;
    /**
     * 创建时间
     */
	@TableField("creates_time")
	private Date createsTime;


}
