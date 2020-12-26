package com.ymw.love.system.controller.admin;

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
 * @date 创建时间：2019年8月17日 类说明：用户登录
 */
@Controller
@RequestMapping("/admin/user")
@ResponseBody
@Api(tags = { "用户/登录  admin" })
public class AUerLogin extends BaseService {



	@ApiOperation(value = "登录", notes = "name：用户账户、手机号码 ，password")
	@PostMapping("/fin/login")
	public Result userLogin(@RequestBody UsetBasicDTO dto) {
		String token = sf.getAUserService().aUerLogin(dto);
		return new Result().setToken(token);
	}

	// 退出
	@Authority(value=Resource.enter.admin_user,holdUp=false)
	@GetMapping("/del/exit")
	@ApiOperation(value = "退出", notes = "退出")
	public Result exit() {
		sf.getAUserService().UserExit(null);
		return new Result();
	}

}
