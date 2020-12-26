package com.ymw.love.system.mapper.sys;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ymw.love.system.entity.authority.SysRoleAuthority;
import com.ymw.love.system.vo.AuthorityVO;

/**
 * <p>
  * 角色与权限关系 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2019-08-02
 */
public interface SysRoleAuthorityMapper extends BaseMapper<SysRoleAuthority> {

	int insertList(@Param("roleCode")String roleCode,@Param("list")List<String> list);
	
	List<Map<String, String>> findUserAuthority(@Param("roleCode")String roleCode);
	
	/**
	 * 获取当前角色权限    只显示选择权限
	 * @param id设置为空
	 * @param roleCode
	 * @return
	 */
	public List<AuthorityVO> findRole(@Param("id") Integer id,@Param("roleCode") String roleCode);
	
	
	
	
}