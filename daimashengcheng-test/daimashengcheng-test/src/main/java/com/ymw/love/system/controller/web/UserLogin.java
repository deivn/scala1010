package com.ymw.love.system.controller.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ymw.love.system.common.Resource;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.config.intercept.Authority;
import com.ymw.love.system.dto.UsetBasicDTO;
import com.ymw.love.system.service.BaseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author 作者 ：suhua
 * @date 创建时间：2019年8月2日  类说明：用户登录、注册、找回密码
 */
@Controller
@RequestMapping("/web/user")
@ResponseBody
@Api(tags = { "普通用户/登录、注册 等" })
public class UserLogin extends BaseService {

	/**
	 * 用户注册
	 * 
	 * @param dto
	 * @return
	 */
	@ApiOperation(value = "用户注册", notes = "code: 邀请码，phone：，password inviteCode:邀请码")
	@PostMapping("/ins/enroll")
	private Result enroll(@RequestBody UsetBasicDTO dto) throws Exception {
		Map<String, String> map = sf.getUUserLoginService().userEnroll(dto.getCode(), dto.getInviteCode(), dto.getPhone(),
				dto.getPassword());
		// 设置登录token
		Result r = new Result();
		r.setToken(map.get("token"));
		map.remove("token");
		r.setData(map);
		return r;
	}

	/**
	 * 验证邀请码是否有效
	 * 
	 * @param code
	 * @return
	 */
	@PostMapping("/fin/invite/code")
	@ApiOperation(value = "验证邀请码是否有效", notes = "code:手机号码或者邀请码")
	private Result inviteCode(@RequestBody UsetBasicDTO dto) {
		String invite = sf.getUUserLoginService().inviteCode(dto.getCode());
		Map<String, String> map = new HashMap();
		map.put("inviteCode", invite);// 返回推荐码
		return new Result(map);
	}

	/**
	 * 退出
	 * 
	 * @return
	 */
	@Authority(Resource.enter.web_user)
	@GetMapping("/del/exit")
	@ApiOperation(value = "退出", notes = "退出")
	private Result exit() {
		sf.getUUserLoginService().UserExit(null);
		return new Result();
	}

	/**
	 * 登录/验证码登录
	 * 
	 * @param dto
	 * @return
	 */
	@ApiOperation(value = "用户登录", notes = "优先使用密码登录    code: 验证码 ，phone，password")
	@PostMapping("/fin/login")
	private Result logIn(@RequestBody UsetBasicDTO dto) {
		Map<String, String> map = sf.getUUserLoginService().logIn(dto.getCode(), dto.getPhone(), dto.getPassword());
		Result r = new Result();
		r.setToken(map.get("token"));
		map.remove("token");
		r.setData(map);
		return r;
	}

	/**
	 * 找回密码修改密码
	 * 
	 * @param dto
	 * @return
	 */
	@ApiOperation(value = "用户找回密码、修改密码", notes = "用户找回密码、修改密码    code: 验证码 ，phone，password")
	@PostMapping("/upd/restore/password")
	private Result restore(@RequestBody UsetBasicDTO dto) {
		sf.getUUserLoginService().findRestorePassword(dto.getCode(), dto.getPhone(), dto.getPassword());
		return new Result();
	}

	/**
	 * 发送验证码
	 */
	@PostMapping("/send/code")
	@ApiOperation(value = "发送验证码", notes = "codeType: 1注册，2登录，3找回密码")
	private Result sendCode(@RequestBody UsetBasicDTO dto) {
		sf.getUUserLoginService().sendCode(dto.getPhone(), dto.getCodeType());
		return new Result();
	}

}
