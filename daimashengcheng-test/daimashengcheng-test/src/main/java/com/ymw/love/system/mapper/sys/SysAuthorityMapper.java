package com.ymw.love.system.mapper.sys;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ymw.love.system.entity.authority.SysAuthority;
import com.ymw.love.system.vo.AuthorityVO;

/**
 * <p>
  * 权限 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2019-08-02
 */
public interface SysAuthorityMapper extends BaseMapper<SysAuthority> {
	/**
	 * 
	 * @param id设置为空
	 * @param roleCode 不为空情况出现该角色选择对应权限
	 * @return
	 */
	public List<AuthorityVO> findALL(@Param("id") Integer id,@Param("roleCode") String roleCode);
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}