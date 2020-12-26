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
 * 字典基本表
 * </p>
 *
 * @author 
 * @since 2019-08-13
 */
@TableName("sys_dict")
@Data
public class SysDict  {

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 标题
     */
	private String title;
    /**
     * 描述
     */
	private String depict;
    /**
     * 值
     */
	private Integer value;
    /**
     * 类型：1积分类型
     */
	private Integer type;
    /**
     * 排序字段
     */
	private Integer sort;
    /**
     * 创建时间
     */
	@TableField("creates_time")
	private Date createsTime;
    /**
     * 状态：1启动，2暂停，3删除
     */
	private Integer state;


}
