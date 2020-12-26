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
 * 权限
 * </p>
 *
 * @author 
 * @since 2019-08-02
 */
@TableName("sys_authority")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysAuthority {


	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 平台标识：1互动平台，2后台管理
     */
	private Integer sign;
	private String title;
    /**
     * 编号
     */
	private String code;
    /**
     * 标识  0不区分    1，菜单 、 2 功能
     */
	@TableField("menu_type")
	private Integer menuType;
    /**
     * 父id
     */
	@TableField("father_id")
	private Integer fatherId;
    /**
     * 拦截url地址
     */
	private String url;
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
	
	


}
