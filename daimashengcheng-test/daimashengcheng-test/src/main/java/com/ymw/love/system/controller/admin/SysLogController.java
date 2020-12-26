package com.ymw.love.system.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ymw.love.system.common.Resource;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.config.intercept.Authority;
import com.ymw.love.system.dto.BasicDTO;
import com.ymw.love.system.service.BaseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
* @author  作者 ：suhua
* @date 创建时间：2019年8月26日 
*类说明：操作日志
*/

@Controller
@RequestMapping("/admin/log")
@ResponseBody
@Api(tags = { "操作日志" })
public class SysLogController extends BaseService  {


	
	@PostMapping("/list")
	@Authority(Resource.enter.admin_user)
	@ApiOperation(value="日志列表",notes="分页，name，timeType，startTime，closeTime")
	public Result  findLogList(@RequestBody BasicDTO arg) {
		return new Result(sf.getSysLogService().findList(arg)); 
	}
	
}
