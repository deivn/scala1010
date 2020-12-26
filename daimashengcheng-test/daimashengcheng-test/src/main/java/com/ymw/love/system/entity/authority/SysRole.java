package com.ymw.love.system.entity.authority;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author 
 * @since 2019-08-02
 */
@TableName("sys_role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRole {


	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 编号
     */
	private String code;
    /**
     * 名字
     */
	private String name;
    /**
     * 平台标识：1互动平台，2后台管理
     */
	private Integer sign;
    /**
     * 等级：小到大
     */
	private Integer rank;
    /**
     * 状态：1未启用，2已启用 ，3 删除
     */
	private Integer state;
    /**
     * 创建时间
     */
	@TableField("creates_time")
	private Date createsTime;
    /**
     * 排序
     */
	private Integer sort;
	
	/**
	 * 数据级别：1全部，2部分
	 */
	@TableField("data_tier")
	private Integer dataTier;
	
	/**
	 * 描述
	 */
	private String depict;
	
	
}
