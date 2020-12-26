package com.ymw.love.system.entity.authority;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 角色与权限关系
 * </p>
 *
 * @author 
 * @since 2019-08-02
 */
@TableName("sys_role_authority")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleAuthority {

    private static final long serialVersionUID = 1L;

    /**
     * 角色编号
     */
	@TableField("role_code")
	private String roleCode;
    /**
     * 权限编号
     */
	@TableField("authority_code")
	private String authorityCode;

}
