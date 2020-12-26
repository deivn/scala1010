package com.ymw.love.system.controller.admin.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ymw.love.system.common.Resource;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.config.excep.MissRequiredParamException;
import com.ymw.love.system.config.intercept.Authority;
import com.ymw.love.system.dto.BasicDTO;
import com.ymw.love.system.dto.UsetBasicDTO;
import com.ymw.love.system.entity.AUser;
import com.ymw.love.system.immobile.HintTitle;
import com.ymw.love.system.service.BaseService;
import com.ymw.love.system.util.StringUtils;
import com.ymw.love.system.vo.AUserVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
* @author  作者 ：suhua
* @date 创建时间：2019年8月19日 下午5:44:20
*类说明：
*/
@Controller
@RequestMapping("/admin")
@ResponseBody
@Api(tags = { "用户与员工  admin" })
public class AUserController extends BaseService {
	


	@Authority(Resource.enter.admin_user)
	@PostMapping("/find/uuser/list")
	@ApiOperation(value = "获取用户列表", notes = "timeType:1今天 、2昨天、3最近3天、4最近7天、5最近1个月      ,name,state:0:全部 1：正常 2：禁用 ,startTime:开始时间,closeTime：结束时间 分页")
    private Result findUUserList(@RequestBody BasicDTO arg) {
		return new Result(sf.getUUserService().findUserList(arg)).setRemove(new String[] {"orders","searchCount"});
    }
	
	@Authority(value=Resource.enter.admin_user,holdUp=false)
	@PostMapping("/find/uuser/friend")
	@ApiOperation(value = "获取用户推荐人列表", notes = "ids")
	private Result fidnUUserfriend(@RequestBody BasicDTO arg) {
		return new Result(sf.getUUserService().fidnUserfriend(arg.getIds(),arg.getPageNum() , arg.getPageSize(),"xi")).setRemove(new String[] {"orders","searchCount"});
	}
	
	
	@Authority(value=Resource.enter.admin_user,content="用户禁用操作")
	@PostMapping("/upd/uuser/state")
	@ApiOperation(value = "用户禁用操作", notes = "ids,state：1：正常 2：禁用,depict:描述")
	private Result updateUUserState(@RequestBody BasicDTO arg) {
		sf.getUUserService().updateUserState(arg.getIds(), arg.getState(), arg.getDepict());
		return new Result();
	}
	
	@Authority(Resource.enter.admin_user)
	@PostMapping("/find/auser/list")
	@ApiOperation(value = "获取员工列表", notes = "name 分页")
	private Result findAuserList(@RequestBody BasicDTO arg) {
		AUserVO au=	sf.getUserLogInInfo().getAdminUser();
		//设置数据权限
		String hospitalId=au.getDataTier()==2?au.getHospitalId():null;
		return new Result(sf.getAUserService().findAUserList(arg,hospitalId));
	}
	
	@Authority(value=Resource.enter.admin_user,holdUp=false)
	@PostMapping("/find/auser/details")
	@ApiOperation(value = "员工详情", notes = "ids")
	private Result findAuserDetails(@RequestBody BasicDTO arg) {
		return new Result(sf.getAUserService().findAUserId(arg.getIds())).setRemove(new String[] {"password","loginTime","createsTime","state"});
	}
	
	@Authority(value=Resource.enter.admin_user,content="修改员工信息")
	@PostMapping("/upd/auser")
	@ApiOperation(value = "修改员工信息", notes = "")
	 private Result updateAuser(@RequestBody AUser arg) {
		
		if(StringUtils.isEmpty(arg.getId())) {
			throw new MissRequiredParamException(HintTitle.System.parameter_is_null);
		}
		
		sf.getAUserService().saveAUser(arg);
		
		 return new Result();
	 }
	
	@Authority(value=Resource.enter.admin_user,content="删除员工信息")
	@PostMapping("/del/auser")
	@ApiOperation(value = "删除员工信息", notes = "ids")
	 private Result deleteAUser(@RequestBody BasicDTO arg) {
		AUser auser =new AUser();
		auser.setState(3);//设置删除状态
		auser.setId(arg.getIds());
		sf.getAUserService().updateAUser(auser);
		 return new Result(); 
	 }
	

	
	@Authority(value=Resource.enter.admin_user,content="新增员工信息")
	@PostMapping("/ins/auser")
	@ApiOperation(value = "新增员工信息", notes = "")
	private Result  insertAUser(@RequestBody AUser arg) {
		if(StringUtils.isNotEmpty(arg.getId())) {
			throw new MissRequiredParamException(HintTitle.System.failed);
		}
		sf.getAUserService().saveAUser(arg);
		return new Result(); 	
	}
	
	//获取当前登录权限与基本信息
	@Authority(value=Resource.enter.admin_user,holdUp=false)
	@GetMapping("/auser/basics")
	@ApiOperation(value = "获取登录用户基本信息与权限", notes = "")
	public Result findUserBasics() {
		AUserVO au=	sf.getUserLogInInfo().getAdminUser();
	   return new Result(sf.getAUserService().findUserDetails(au.getId())).setRemove(new String[] {"fatherId","optionCode"});
	}
	
	
	@Authority(value=Resource.enter.admin_user,holdUp=false)
	@PostMapping("/upd/auser/password")
	@ApiOperation(value = "修复用户密码", notes = "newPassword,oldPassword")
	public Result updateAuser(@RequestBody UsetBasicDTO arg) {
		AUserVO au=	sf.getUserLogInInfo().getAdminUser();
		sf.getAUserService().updateAUserPassword(au.getId(), arg.getNewPassword(), arg.getOldPassword());
		 return new Result();
	}
	
	
}
