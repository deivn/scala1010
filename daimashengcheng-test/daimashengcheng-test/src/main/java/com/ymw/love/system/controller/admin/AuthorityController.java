package com.ymw.love.system.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ymw.love.system.common.Resource;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.config.intercept.Authority;
import com.ymw.love.system.dto.BaseEntity;
import com.ymw.love.system.dto.BasicDTO;
import com.ymw.love.system.service.BaseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
* @author  作者 ：suhua
* @date 创建时间：2019年7月30日 
*类说明：后台权限
*/
@Controller
@RequestMapping("/admin/authority")
@ResponseBody
@Api(tags = { "权限与角色" })
public class AuthorityController  extends BaseService {
   

	
	@Authority(Resource.enter.admin_user)
	@ApiOperation(value = "获取后台角色列表")
	@PostMapping("/fin/role/list")
	public Result  findSysRoleList(@RequestBody BaseEntity arg) {
		return new Result(sf.getRoleAuthorityService().findSysRoleList(arg)).setRemove(new String[] {"createsTime","id","rank","sign","sort","state","orders","searchCount"});
	}
	
	
	
	@Authority(Resource.enter.admin_user)
	@ApiOperation(value = "获取角色权限 带选择",notes="code为空不带选择")
	@PostMapping("/fin/role")
	public Result findRoleAuthority(@RequestBody BasicDTO dc) {
		return new Result(sf.getRoleAuthorityService().findRoleAuthority(dc.getCode())).setRemove(new String[] {"fatherId"});
	}
	
	@Authority(value=Resource.enter.admin_user,content="修改角色权限")
	@ApiOperation(value = "修改角色权限",notes="code,depict,name,dataTier,authoritCode:权限选择编码，逗号隔开")
	@PostMapping("/upd/role")
	public Result  updateRoleAuthority(@RequestBody BasicDTO dc) {
		sf.getRoleAuthorityService().updateRoleAuthority(dc);
		return new Result();
	}
	
	
	
	@Authority(value=Resource.enter.admin_user,content="添加角色")
	@ApiOperation(value = "添加角色",notes="depict,name,dataTier,authoritCode:权限选择编码，逗号隔开")
	@PostMapping("/ins/role")
	public Result insertRoleAuthority(@RequestBody BasicDTO dc) {
		sf.getRoleAuthorityService().insertRoleAuthority(dc);
		return new Result();
	}
	
	
	
	@Authority(value=Resource.enter.admin_user,content="删除角色")
	@ApiOperation(value = "删除角色",notes="code")
	@PostMapping("/del/role")
	public Result deleteRoleAuthority(@RequestBody BasicDTO dc) {
		sf.getRoleAuthorityService().deleteRoleAuthority(dc.getCode());
		return new Result();
	}
	
	
	
	
}
