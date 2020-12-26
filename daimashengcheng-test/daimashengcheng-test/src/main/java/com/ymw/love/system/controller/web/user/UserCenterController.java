package com.ymw.love.system.controller.web.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ymw.love.system.common.Resource;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.config.intercept.Authority;
import com.ymw.love.system.dto.BaseEntity;
import com.ymw.love.system.dto.UsetBasicDTO;
import com.ymw.love.system.entity.UUser;
import com.ymw.love.system.service.BaseService;
import com.ymw.love.system.vo.UUserVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author 作者 ：suhua
 * @date 创建时间：2019年8月7日 下午2:41:15 类说明：用户个人中心模块
 */
@Api(tags = { "用户个人中心模块" })
@Controller
@RequestMapping("/web/user/center")
@ResponseBody
public class UserCenterController  extends BaseService {

	

	@ApiOperation(value = "用户个人中心首页", notes = "")
	@GetMapping("/fin/count")
	@Authority(Resource.enter.web_user)
	private Result findUserCount() {
		UUserVO vo = sf.getUserLogInInfo().getWebUser();
		return new Result(sf.getUUserService().findUUserCount(vo.getId()));
	}

	@ApiOperation(value = "用户个人中详情", notes = "")
	@GetMapping("/fin/details")
	@Authority(Resource.enter.web_user)
	private Result findUserDetails() {
		UUserVO vo = sf.getUserLogInInfo().getWebUser();
		return new Result(sf.getUUserService().findUserDetails(vo.getId()));
	}

	@ApiOperation(value = "添加身份证", notes = "参数：Idcard，name")
	@PostMapping("/ins/idcard")
	@Authority(Resource.enter.web_user)
	private Result insertIdcard(@RequestBody UsetBasicDTO dto) {
		UUserVO vo = sf.getUserLogInInfo().getWebUser();
		sf.getUUserService().updateIdCard(vo.getId(), dto.getIdcard(), dto.getName());
		return new Result();
	}

	@ApiOperation(value = "更新收款方方式", notes = "参数：Number，ImageUrl")
	@PostMapping("/ins/save/payee")
	@Authority(Resource.enter.web_user)
	private Result updateUserPayee(@RequestBody UsetBasicDTO dto) {
		UUserVO vo = sf.getUserLogInInfo().getWebUser();
		sf.getUUserService().updateUserPayee(vo.getId(), dto.getNumber(), dto.getImageUrl());
		return new Result();
	}

	@ApiOperation(value = "更新昵称、头像", notes = "参数：nickName，ImageUrl")
	@PostMapping("/upd/details")
	@Authority(Resource.enter.web_user)
	private Result updateUserDetails(@RequestBody UsetBasicDTO dto) {
		UUserVO vo = sf.getUserLogInInfo().getWebUser();
		sf.getUUserService().updateUserDetails(vo.getId(), dto.getNickName(), dto.getImageUrl());
		return new Result();
	}

	
	@ApiOperation(value = "推荐好友", notes = "分页参数")
	@PostMapping("/find/friend/list")
	@Authority(Resource.enter.web_user)
	public Result findfriendList(@RequestBody BaseEntity be) {
		UUserVO vo = sf.getUserLogInInfo().getWebUser();
		IPage<UUser> list = sf.getUUserService().fidnUserfriend(vo.getId(), be.getPageNum(),be.getPageSize(),null);
		return new Result(list).setRemove(new  String[] {"idcard","state","nickName"});
	}

	
	
	
}
