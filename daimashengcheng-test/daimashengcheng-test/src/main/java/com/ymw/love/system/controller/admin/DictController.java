package com.ymw.love.system.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ymw.love.system.common.Resource;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.common.SysDictBase;
import com.ymw.love.system.config.excep.MissRequiredParamException;
import com.ymw.love.system.config.intercept.Authority;
import com.ymw.love.system.dto.BasicDTO;
import com.ymw.love.system.entity.SysDict;
import com.ymw.love.system.immobile.HintTitle;
import com.ymw.love.system.service.BaseService;
import com.ymw.love.system.util.StringUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
* @author  作者 ：suhua
* @date 创建时间：2019年8月15日 下午1:59:11
*类说明：
*/
@Controller
@RequestMapping("/admin/dict")
@ResponseBody
@Api(tags = { "字典" })
public class DictController extends BaseService  {
   
	
	
	@Authority(Resource.enter.admin_user)
	@ApiOperation(value = "获取部门", notes = "分页")
	@PostMapping("/sector/list")
	public Result findSectorList(@RequestBody BasicDTO be) {
		
	   Result result=	new Result();
		IPage<SysDict> sd=	sf.getDictService().findSysDictList(be, SysDictBase.SysType.sector);
		result.setData(sd);
		return result.setRemove(new String[] {"value","sort","createsTime","state","type","searchCount","orders"});
	}
	
	
	@Authority(Resource.enter.admin_user)
	@ApiOperation(value = "获取职称", notes = "分页")
	@PostMapping("/position/list")
	public Result findPositionList(@RequestBody  BasicDTO be) {
	   Result result=	new Result();
		IPage<SysDict> sd=	sf.getDictService().findSysDictList(be, SysDictBase.SysType.position);
		result.setData(sd);
		return result.setRemove(new String[] {"value","sort","createsTime","state","type","searchCount","orders"});
	}
	
	
	/**
	 * 不需要权限获取
	 * @param be
	 * @return
	 */
	@Authority(value=Resource.enter.admin_user,holdUp=false)
	@ApiOperation(value = "获取部门,职称", notes = "不需要权限，type 2职称，3部门 ")
	@PostMapping("/find/type/list")
	public Result findList(@RequestBody  BasicDTO be) {
		
		if(StringUtils.isEmpty(be.getType()) || (be.getType()!=3 && be.getType()!=2)) {
			throw new MissRequiredParamException(HintTitle.System.error_type);
		}
		return new Result(sf.getDictService().findSysDictListAll(be.getType())).setRetain(new String[]{"title","id"});
	}
	
	
	
	@Authority(value=Resource.enter.admin_user,content="修改部门")
	@ApiOperation(value = "修改部门", notes = "")
	@PostMapping("/upd/sector")
	public Result updateSector(@RequestBody SysDict sd) {
		sd.setType(SysDictBase.SysType.sector);
		sf.getDictService().updateSysDict(sd);
		return new Result();
	}
	
	
	@Authority(value=Resource.enter.admin_user,content="修改职称")
	@ApiOperation(value = "修改职称", notes = "")
	@PostMapping("/upd/position")
	public Result updatePosition(@RequestBody SysDict sd) {
		sd.setType(SysDictBase.SysType.position);
		sf.getDictService().updateSysDict(sd);
		return new Result();
	}
	
	@Authority(value=Resource.enter.admin_user,content="删除部门")
	@ApiOperation(value = "删除部门", notes = "id")
	@PostMapping("/del/sector")
	public Result deleteSector(@RequestBody BasicDTO arg  ) {
		if(arg.getId()==null) {
			throw new MissRequiredParamException(HintTitle.System.parameter_is_null);
		}
		sf.getDictService().deleteSysDict(arg.getId(), SysDictBase.SysType.sector);
		return new Result();
	}
	
	
	@Authority(value=Resource.enter.admin_user,content="删除职称")
	@ApiOperation(value = "删除职称", notes = "")
	@PostMapping("/del/position")
	public Result deletePosition(@RequestBody BasicDTO arg ) {
		if(arg.getId()==null) {
			throw new MissRequiredParamException(HintTitle.System.parameter_is_null);
		}
		sf.getDictService().deleteSysDict(arg.getId(), SysDictBase.SysType.position);
		return new Result();
	}
	
	
	@Authority(value=Resource.enter.admin_user,content="新增部门")
	@ApiOperation(value = "新增部门", notes = "")
	@PostMapping("/ins/sector")
	public Result insertSector( @RequestBody SysDict sd ) {
		sd.setType(SysDictBase.SysType.sector);
		sf.getDictService().insertSysDict(sd);
		return new Result();
	}
	
	@Authority(value=Resource.enter.admin_user,content="新增职位")
	@ApiOperation(value = "新增职位", notes = "")
	@PostMapping("/ins/position")
	public Result insertPosition(@RequestBody SysDict sd) {
		sd.setType(SysDictBase.SysType.position);
		sf.getDictService().insertSysDict(sd);
		return new Result();
	}
	
	
	
	
}
