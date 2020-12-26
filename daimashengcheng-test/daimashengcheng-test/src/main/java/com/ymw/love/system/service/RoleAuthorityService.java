package com.ymw.love.system.service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ymw.love.system.common.Resource;
import com.ymw.love.system.config.excep.MissRequiredParamException;
import com.ymw.love.system.dto.BaseEntity;
import com.ymw.love.system.dto.BasicDTO;
import com.ymw.love.system.entity.AUser;
import com.ymw.love.system.entity.SysDict;
import com.ymw.love.system.entity.authority.SysRole;
import com.ymw.love.system.entity.authority.SysRoleAuthority;
import com.ymw.love.system.immobile.HintTitle;
import com.ymw.love.system.mapper.AUserMapper;
import com.ymw.love.system.mapper.sys.SysAuthorityMapper;
import com.ymw.love.system.mapper.sys.SysRoleAuthorityMapper;
import com.ymw.love.system.mapper.sys.SysRoleMapper;
import com.ymw.love.system.util.SnowFlakeUtil;
import com.ymw.love.system.util.StringUtils;
import com.ymw.love.system.vo.AuthorityVO;
import com.ymw.love.system.vo.RoleDetailsVO;

/**
 * @author 作者 ：suhua
 * @date 创建时间：2019年8月2日 上午10:09:51 类说明：权限与角色
 */
@Service
public class RoleAuthorityService extends BaseService {


	

	/**
	 * 获取全部角色
	 * 
	 * @return
	 */
	public IPage<SysRole>  findSysRoleList(BaseEntity arg) {
		Page page = new Page<>(arg.getPageNum(), arg.getPageSize());
		IPage<SysRole> list =  mf.getSysRoleMapper().selectPage(page, new QueryWrapper<SysRole>().eq("state", 2));
		return list;
	}


	/**
	 * 获取角色详情权限 带勾选
	 * 
	 * @param code   All：获取后台权限列表
	 * @return
	 */
	public RoleDetailsVO findRoleAuthority(String code) {


		RoleDetailsVO vo = new RoleDetailsVO();
		
		if(StringUtils.isNotEmpty(code)) {//获取角色权限
			
			List<SysRole> list = mf.getSysRoleMapper().selectList(new QueryWrapper<SysRole>().eq("code", code));
			if (list == null || list.size() <= 0) {
				throw new MissRequiredParamException(HintTitle.System.error_type);
			}
			vo.setName(list.get(0).getName());
			vo.setDepict(list.get(0).getDepict());
			vo.setDataTier(list.get(0).getDataTier());	
		}else {
			code="All";//获取全部权限
		}
		
		List<AuthorityVO> li = mf.getSysAuthorityMapper().findALL(null, code);
		vo.setAuthorit(li);
		return vo;
	}

	/**
	 * 修改角色权限
	 * 
	 * @param dc
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public String updateRoleAuthority(BasicDTO dc) {
		if (StringUtils.isEmpty(dc.getCode()) || StringUtils.isEmpty(dc.getDataTier())
				|| StringUtils.isEmpty(dc.getName())) {
			throw new MissRequiredParamException(HintTitle.System.parameter_is_null);
		}
		//

		SysRole sysRole = new SysRole();
		sysRole.setName(dc.getName());
		sysRole.setDepict(dc.getDepict());
		sysRole.setDataTier(dc.getDataTier());
		int is = mf.getSysRoleMapper().update(sysRole, new UpdateWrapper<SysRole>().eq("code", dc.getCode()));
		if (is <= 0) {
			throw new MissRequiredParamException(HintTitle.System.failed);
		}

		// 删除角色权限
		mf.getSysRoleAuthorityMapper().delete(new QueryWrapper<SysRoleAuthority>().eq("role_code", dc.getCode()));

		if (!StringUtils.isEmpty(dc.getAuthoritCode())) {
			// 插入权限
			String[] st = dc.getAuthoritCode().split(",");
			mf.getSysRoleAuthorityMapper().insertList(dc.getCode(), Arrays.asList(st));
		}

		return null;
	}

	/**
	 * 新增
	 * 
	 * @param dc
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public String insertRoleAuthority(BasicDTO dc) {
		if ( StringUtils.isEmpty(dc.getDataTier()) || StringUtils.isEmpty(dc.getName())) {
			throw new MissRequiredParamException(HintTitle.System.parameter_is_null);
		}
		// 生成编号
		String code = SnowFlakeUtil.getStochastic(10);
		SysRole sysRole = new SysRole();
		sysRole.setCode(code);
		sysRole.setSign(Resource.SystemSign.signManage);
		sysRole.setCreatesTime(new Date());
		sysRole.setName(dc.getName());
		sysRole.setDepict(dc.getDepict());
		sysRole.setDataTier(dc.getDataTier());
		mf.getSysRoleMapper().insert(sysRole);

		// 添加权限
		if (!StringUtils.isEmpty(dc.getAuthoritCode())) {
			String[] st = dc.getAuthoritCode().split(",");
			mf.getSysRoleAuthorityMapper().insertList(code, Arrays.asList(st));
		}
		return null;
	}

	/**
	 * 删除角色
	 * 
	 * @param code
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public String deleteRoleAuthority(String code) {
		if (StringUtils.isEmpty(code)) {
			throw new MissRequiredParamException(HintTitle.System.parameter_is_null);
		}
		
		SysRole sysRole = new SysRole();
		sysRole.setState(3);
		int i = mf.getSysRoleMapper().update(sysRole, new UpdateWrapper<SysRole>().eq("code", code));
		
		if (i <= 0) {
			throw new MissRequiredParamException(HintTitle.System.failed);
		}

		//删除角色权限
		mf.getSysRoleAuthorityMapper().delete(new QueryWrapper<SysRoleAuthority>().eq("role_code", code));
		
	    //把用户关联角色设为0
	    AUser au =new AUser();
	    au.setRoleCode("0");
	    mf.getAUserMapper().update(au, new UpdateWrapper<AUser>().eq("role_code", code));
	    return null;
	}

	/**
	 * 获取用户对应权限集合
	 * @param roleCode
	 * @return
	 */
	public Map<String, String> findUserAuthority(String roleCode){
		
		if (StringUtils.isEmpty(roleCode)) {
			throw new MissRequiredParamException(HintTitle.System.parameter_is_null);
		}
	
		List<Map<String, String>> list=	 mf.getSysRoleAuthorityMapper().findUserAuthority(roleCode);
		Map<String, String> map =new HashMap();
		
		for (int i = 0; i < list.size(); i++) {
			if(StringUtils.isNotEmpty(list.get(i).get("url"))) {
				map.put(list.get(i).get("url"), list.get(i).get("code"));
			}
			
		}
		
		
		
		return map;
	}
	
	/**
	 * 用户权限菜单
	 * @param roleCode
	 * @return
	 */
	public List<AuthorityVO> findUserAuthoritys(String roleCode){
		return mf.getSysRoleAuthorityMapper().findRole(null, roleCode);
	}
	
	
	
	
}
